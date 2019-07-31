package moxproxy.adapters;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.util.AttributeKey;
import moxproxy.consts.MoxProxyConts;
import moxproxy.enums.MoxProxyDirection;
import moxproxy.interfaces.*;
import moxproxy.model.MoxProxyProcessedTrafficEntry;
import moxproxy.model.MoxProxyRule;
import moxproxy.rules.MoxProxyProcessingResultType;
import moxproxy.rules.MoxProxyRuleProcessingResult;
import org.littleshoot.proxy.HttpFiltersAdapter;

import java.util.List;

public class MoxProxyFiltersAdapter extends HttpFiltersAdapter {

    private static final AttributeKey<String> CONNECTED_URL = AttributeKey.valueOf("connected_url");

    private MoxProxyRulesMatcher matcher;
    private MoxProxyTrafficRecorder trafficRecorder;
    private MoxProxyRuleProcessor proxyRuleProcessor;
    private MoxProxyServiceConfiguration serviceConfiguration;

    public MoxProxyFiltersAdapter(HttpRequest originalRequest, ChannelHandlerContext ctx,
                                  MoxProxyRulesMatcher matcher, MoxProxyTrafficRecorder recorder, MoxProxyRuleProcessor proxyRuleProcessor, MoxProxyServiceConfiguration serviceConfiguration) {
        super(originalRequest, ctx);
        this.matcher = matcher;
        this.trafficRecorder = recorder;
        this.proxyRuleProcessor = proxyRuleProcessor;
        this.serviceConfiguration = serviceConfiguration;
    }

    @Override
    public HttpResponse clientToProxyRequest(HttpObject httpObject) {
        if(httpObject instanceof FullHttpRequest){
            handleConnectRequest();
            String connectedUrl = getConnectedUrl();
            HttpTrafficAdapter requestAdapter = new HttpRequestAdapterImpl(httpObject, originalRequest, connectedUrl, matcher.getSessionIdMatchingStrategy(), serviceConfiguration.isRecordBodies());

            MoxProxyProcessedTrafficEntry trafficEntry = requestAdapter.trafficEntry();

            System.out.println("Size before record: " + trafficRecorder.requestSize());
            System.out.println("Recording: " + trafficEntry.getUrl());
            System.out.println("Size after record: " + trafficRecorder.requestSize());

            trafficRecorder.recordRequest(trafficEntry);
            List<MoxProxyRule> result = matcher.match(trafficEntry, MoxProxyDirection.REQUEST);
            MoxProxyRuleProcessingResult processingResult = proxyRuleProcessor.processRequest(result, httpObject);
            if(processingResult.getMoxProxyProcessingResultType() == MoxProxyProcessingResultType.RESPOND){
                return processingResult.getResponse();
            }
            proxyRuleProcessor.postProcessRules(result);
        }
        return null;
    }

    @Override
    public HttpObject proxyToClientResponse(HttpObject httpObject) {
        if(httpObject instanceof FullHttpResponse){
            String connectedUrl = getConnectedUrl();
            HttpTrafficAdapter responseAdapter = new HttpResponseAdapterImpl(httpObject, originalRequest, connectedUrl, matcher.getSessionIdMatchingStrategy(), serviceConfiguration.isRecordBodies());

            MoxProxyProcessedTrafficEntry trafficEntry = responseAdapter.trafficEntry();

            trafficRecorder.recordResponse(trafficEntry);
            List<MoxProxyRule> result = matcher.match(trafficEntry, MoxProxyDirection.RESPONSE);
            MoxProxyRuleProcessingResult processingResult = proxyRuleProcessor.processResponse(result, httpObject);
            if(processingResult.getMoxProxyProcessingResultType() == MoxProxyProcessingResultType.PROCESS){
                return processingResult.getResponse();
            }
            proxyRuleProcessor.postProcessRules(result);
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
