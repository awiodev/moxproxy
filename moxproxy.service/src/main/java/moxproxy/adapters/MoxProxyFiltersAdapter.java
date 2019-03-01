package moxproxy.adapters;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.util.AttributeKey;
import moxproxy.consts.MoxProxyConts;
import moxproxy.enums.MoxProxyDirection;
import moxproxy.interfaces.*;
import moxproxy.model.MoxProxyRule;
import moxproxy.interfaces.MoxProxyRuleProcessor;
import moxproxy.interfaces.MoxProxyRulesMatcher;
import moxproxy.rules.MoxProxyProcessingResultType;
import moxproxy.rules.MoxProxyRuleProcessingResult;
import org.littleshoot.proxy.HttpFiltersAdapter;

import java.util.List;

public class MoxProxyFiltersAdapter extends HttpFiltersAdapter {

    private static final AttributeKey<String> CONNECTED_URL = AttributeKey.valueOf("connected_url");

    private MoxProxyRulesMatcher matcher;
    private MoxProxyTrafficRecorder trafficRecorder;
    private EntityConverter entityConverter;
    private MoxProxyRuleProcessor proxyRuleProcessor;

    public MoxProxyFiltersAdapter(HttpRequest originalRequest, ChannelHandlerContext ctx,
                                  MoxProxyRulesMatcher matcher, MoxProxyTrafficRecorder recorder,
                                  EntityConverter entityConverter, MoxProxyRuleProcessor proxyRuleProcessor) {
        super(originalRequest, ctx);
        this.matcher = matcher;
        this.trafficRecorder = recorder;
        this.entityConverter = entityConverter;
        this.proxyRuleProcessor = proxyRuleProcessor;
    }

    @Override
    public HttpResponse clientToProxyRequest(HttpObject httpObject) {
        if(httpObject instanceof FullHttpRequest){
            handleConnectRequest();
            String connectedUrl = getConnectedUrl();
            HttpRequestAdapter requestAdapter = new HttpRequestAdapterImpl(httpObject, originalRequest, connectedUrl);
            trafficRecorder.recordRequest(entityConverter.fromRequestAdapter(requestAdapter));
            List<MoxProxyRule> result = matcher.match(requestAdapter, MoxProxyDirection.REQUEST);
            MoxProxyRuleProcessingResult processingResult = proxyRuleProcessor.processRequest(result, httpObject);
            if(processingResult.getMoxProxyProcessingResultType() == MoxProxyProcessingResultType.RESPOND){
                return processingResult.getResponse();
            }
            if(processingResult.getMoxProxyProcessingResultType() == MoxProxyProcessingResultType.PROCESS){
                httpObject = processingResult.getRequest();
                super.clientToProxyRequest(httpObject);
            }
        }
        return null;
    }

    @Override
    public HttpObject proxyToClientResponse(HttpObject httpObject) {
        if(httpObject instanceof FullHttpResponse){
            String connectedUrl = getConnectedUrl();
            HttpResponseAdapter responseAdapter = new HttpResponseAdapterImpl(httpObject, originalRequest, connectedUrl);
            trafficRecorder.recordResponse(entityConverter.fromResponseAdapter(responseAdapter));
            List<MoxProxyRule> result = matcher.match(responseAdapter, MoxProxyDirection.RESPONSE);
            MoxProxyRuleProcessingResult processingResult = proxyRuleProcessor.processResponse(result, httpObject);
            if(processingResult.getMoxProxyProcessingResultType() == MoxProxyProcessingResultType.PROCESS){
                return processingResult.getResponse();
            }
        }
        return httpObject;
    }

    private void handleConnectRequest(){
        String uri = originalRequest.uri();
        if (originalRequest.method() == HttpMethod.CONNECT) {
            if (ctx != null) {
                String prefix = "https://" + uri.replaceFirst(":443$", "");
                ctx.channel().attr(CONNECTED_URL).set(prefix);
            }
        }
    }

    private String getConnectedUrl(){
        if(originalRequest.method() != HttpMethod.CONNECT){
            String connectedUrl = ctx.channel().attr(CONNECTED_URL).get();
            if(connectedUrl != null){
                return connectedUrl;
            }
        }
        return MoxProxyConts.EMPTY_STRING;
    }
}
