package moxproxy.adapters;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import moxproxy.interfaces.*;
import moxproxy.dto.MoxProxyRule;
import moxproxy.interfaces.IMoxProxyRuleProcessor;
import moxproxy.interfaces.IMoxProxyRulesMatcher;
import moxproxy.rules.MoxProxyRuleProcessingResult;
import org.littleshoot.proxy.HttpFiltersAdapter;

import java.util.List;

public class MoxProxyFiltersAdapter extends HttpFiltersAdapter {

    private IMoxProxyRulesMatcher matcher;
    private IMoxProxyTrafficRecorder trafficRecorder;
    private IEntityConverter entityConverter;
    private IMoxProxyRuleProcessor proxyRuleProcessor;

    public MoxProxyFiltersAdapter(HttpRequest originalRequest, ChannelHandlerContext ctx,
                                  IMoxProxyRulesMatcher matcher, IMoxProxyTrafficRecorder recorder,
                                  IEntityConverter entityConverter, IMoxProxyRuleProcessor proxyRuleProcessor) {
        super(originalRequest, ctx);
        this.matcher = matcher;
        this.trafficRecorder = recorder;
        this.entityConverter = entityConverter;
        this.proxyRuleProcessor = proxyRuleProcessor;
    }

    @Override
    public HttpResponse clientToProxyRequest(HttpObject httpObject) {
        if(httpObject instanceof FullHttpRequest){
            IHttpRequestAdapter requestAdapter = new HttpRequestAdapter(httpObject, originalRequest);
            trafficRecorder.recordRequest(entityConverter.fromRequestAdapter(requestAdapter));
            List<MoxProxyRule> result = matcher.match(requestAdapter);
            MoxProxyRuleProcessingResult processingResult = proxyRuleProcessor.processRequest(result, httpObject);
            if(processingResult.isRespond()){
                return processingResult.getResponse();
            }
        }
        return null;
    }

    @Override
    public HttpObject proxyToClientResponse(HttpObject httpObject) {
        if(httpObject instanceof FullHttpResponse){
            IHttpResponseAdapter responseAdapter = new HttpResponseAdapter(httpObject, originalRequest);
            trafficRecorder.recordResponse(entityConverter.fromResponseAdapter(responseAdapter));
            List<MoxProxyRule> result = matcher.match(responseAdapter);
            var processingResult = proxyRuleProcessor.processResponse(result, httpObject);
            if(processingResult.isRespond()){
                return processingResult.getResponse();
            }
        }
        return httpObject;
    }
}
