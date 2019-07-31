package moxproxy.rules;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import moxproxy.consts.MoxProxyConts;
import moxproxy.enums.MoxProxyAction;
import moxproxy.interfaces.MoxProxyDatabase;
import moxproxy.interfaces.MoxProxyRuleProcessor;
import moxproxy.model.MoxProxyHeader;
import moxproxy.model.MoxProxyHttpRuleDefinition;
import moxproxy.model.MoxProxyRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MoxProxyRuleProcessorImpl implements MoxProxyRuleProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(MoxProxyRuleProcessorImpl.class);

    private MoxProxyDatabase moxProxyDatabase;

    public MoxProxyRuleProcessorImpl(MoxProxyDatabase moxProxyDatabase){
        this.moxProxyDatabase = moxProxyDatabase;
    }

    @Override
    public MoxProxyRuleProcessingResult processRequest(List<MoxProxyRule> rules, HttpObject request) {

        MoxProxyRuleProcessingResult ruleProcessingResult = new MoxProxyRuleProcessingResult();

        List<MoxProxyRule> respondRules = getResponseRules(rules);
        if(respondRules.size() > 0){
            ruleProcessingResult.setMoxProxyProcessingResultType(MoxProxyProcessingResultType.RESPOND);
            MoxProxyRule latestResponseRule = getLatestResponseRule(respondRules);
            ruleProcessingResult.setResponse(createDefaultResponse(latestResponseRule.getHttpObject()));
            LOG.info("Processed request response");
            latestResponseRule.handleInvoke();

            return ruleProcessingResult;
        }

        List<MoxProxyRule> modificationRules = getModificationRules(rules);

        for(MoxProxyRule rule : modificationRules){
            request = modifyRequest(rule, request);
            ruleProcessingResult.setRequest(request);
            ruleProcessingResult.setMoxProxyProcessingResultType(MoxProxyProcessingResultType.PROCESS);
            LOG.info("Processed request modification");
            rule.handleInvoke();
        }

        List<MoxProxyRule> deleteRules = getDeleteRules(rules);

        for(MoxProxyRule rule : deleteRules){
            request = deleteFromRequest(rule, request);
            ruleProcessingResult.setRequest(request);
            ruleProcessingResult.setMoxProxyProcessingResultType(MoxProxyProcessingResultType.PROCESS);
            LOG.info("Processed request elements removal");
            rule.handleInvoke();
        }

        return ruleProcessingResult;
    }

    private HttpObject modifyRequest(MoxProxyRule rule, HttpObject request) {
        FullHttpRequest httpRequest = (FullHttpRequest)request;
        MoxProxyHttpRuleDefinition ruleDefinition = rule.getHttpObject();

        if(ruleDefinition.getHeaders() != null && !ruleDefinition.getHeaders().isEmpty()){
            for(MoxProxyHeader header : ruleDefinition.getHeaders()){
                if(httpRequest.headers().contains(header.getName())){
                    httpRequest.headers().remove(header.getName());
                }
                httpRequest.headers().add(header.getName(), header.getValue());
                LOG.info("Setting request header: {} value {} for path {} and method {}", header.getName(), header.getValue(),
                        httpRequest.uri(), httpRequest.method());
            }
        }

        if(ruleDefinition.getBody() != null){
            ByteBuf contentBuf = httpRequest.content();
            ByteBuf byteBuf = Unpooled.copiedBuffer(ruleDefinition.getBody(), CharsetUtil.UTF_8);
            contentBuf.clear().writeBytes(byteBuf);
            HttpUtil.setContentLength(httpRequest, ruleDefinition.getBody().length());
            LOG.info("Setting request body: {} for path {} and method {}", ruleDefinition.getBody(),
                    httpRequest.uri(), httpRequest.method());
        }

        return httpRequest;
    }

    private HttpObject deleteFromRequest(MoxProxyRule rule, HttpObject request){
        FullHttpRequest httpRequest = (FullHttpRequest)request;
        MoxProxyHttpRuleDefinition ruleDefinition = rule.getHttpObject();

        if(ruleDefinition.getHeaders() != null && !ruleDefinition.getHeaders().isEmpty()){
            for(MoxProxyHeader header : ruleDefinition.getHeaders()){
                if(httpRequest.headers().contains(header.getName())){
                    httpRequest.headers().remove(header.getName());
                    LOG.info("Removing request header: {} for path {} and method {}", header.getName(),
                            httpRequest.uri(), httpRequest.method());
                }
            }
        }

        if(ruleDefinition.getBody() != null && ruleDefinition.getBody().equals(MoxProxyConts.DELETE_BODY_INDICATOR)){

            ByteBuf contentBuf = httpRequest.content();
            ByteBuf byteBuf = Unpooled.copiedBuffer(MoxProxyConts.EMPTY_STRING, CharsetUtil.UTF_8);
            contentBuf.clear().writeBytes(byteBuf);
            HttpUtil.setContentLength(httpRequest, ruleDefinition.getBody().length());
            LOG.info("Removing request body for path {} and method {}", httpRequest.uri(), httpRequest.method());
        }

        return httpRequest;
    }



    private FullHttpResponse createDefaultResponse(MoxProxyHttpRuleDefinition ruleDefinition){
        HttpResponseStatus responseCode =  HttpResponseStatus.valueOf(ruleDefinition.getStatusCode());

        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, responseCode);

        if(ruleDefinition.getHeaders() != null && !ruleDefinition.getHeaders().isEmpty()){
            for(MoxProxyHeader header : ruleDefinition.getHeaders()){
                response.headers().add(header.getName(), header.getValue());
            }
        }

        if(ruleDefinition.getBody() != null){

            ByteBuf contentBuf = response.content();
            ByteBuf byteBuf = Unpooled.copiedBuffer(ruleDefinition.getBody(), CharsetUtil.UTF_8);
            contentBuf.clear().writeBytes(byteBuf);
            HttpUtil.setContentLength(response, ruleDefinition.getBody().length());
        }

        return response;
    }

    @Override
    public MoxProxyRuleProcessingResult processResponse(List<MoxProxyRule> rules, HttpObject response) {

        FullHttpResponse fullHttpResponse = (FullHttpResponse)response;
        MoxProxyRuleProcessingResult ruleProcessingResult = new MoxProxyRuleProcessingResult();

        List<MoxProxyRule> modificationRules = getModificationRules(rules);

        for(MoxProxyRule rule : modificationRules){
            fullHttpResponse = modifyResponse(rule, fullHttpResponse);
            ruleProcessingResult.setResponse(fullHttpResponse);
            ruleProcessingResult.setMoxProxyProcessingResultType(MoxProxyProcessingResultType.PROCESS);
            LOG.info("Processed response modification");
            rule.handleInvoke();
        }

        List<MoxProxyRule> deleteRules = getDeleteRules(rules);

        for(MoxProxyRule rule : deleteRules){
            fullHttpResponse = deleteFromResponse(rule, fullHttpResponse);
            ruleProcessingResult.setResponse(fullHttpResponse);
            ruleProcessingResult.setMoxProxyProcessingResultType(MoxProxyProcessingResultType.PROCESS);
            LOG.info("Processed response elements removal");
            rule.handleInvoke();
        }

        return ruleProcessingResult;
    }

    @Override
    public void postProcessRules(List<MoxProxyRule> rules) {
        List<MoxProxyRule> forCleanup = rules.stream().filter(x -> x.getInvokeLimit() == 0).collect(Collectors.toList());
        forCleanup.forEach(x -> {
            String ruleId = x.getId();
            moxProxyDatabase.cleanRule(ruleId);
            LOG.info("Removed expired rule: {}", ruleId);
        });
    }

    private FullHttpResponse modifyResponse(MoxProxyRule rule, FullHttpResponse response) {

        MoxProxyHttpRuleDefinition ruleDefinition = rule.getHttpObject();

        if(ruleDefinition.getHeaders() != null && !ruleDefinition.getHeaders().isEmpty()){
            for(MoxProxyHeader header : ruleDefinition.getHeaders()){
                if(response.headers().contains(header.getName())){
                    response.headers().remove(header.getName());
                }
                response.headers().add(header.getName(), header.getValue());
                LOG.info("Setting response header: {} value {}", header.getName(), header.getValue());
            }
        }

        if(ruleDefinition.getBody() != null){

            ByteBuf contentBuf = response.content();
            ByteBuf byteBuf = Unpooled.copiedBuffer(ruleDefinition.getBody(), CharsetUtil.UTF_8);
            contentBuf.clear().writeBytes(byteBuf);
            HttpUtil.setContentLength(response, ruleDefinition.getBody().length());
            LOG.info("Setting response body: {}", ruleDefinition.getBody());
        }

        return response;
    }

    private FullHttpResponse deleteFromResponse(MoxProxyRule rule, FullHttpResponse response){

        MoxProxyHttpRuleDefinition ruleDefinition = rule.getHttpObject();

        if(ruleDefinition.getHeaders() != null && !ruleDefinition.getHeaders().isEmpty()){
            for(MoxProxyHeader header : ruleDefinition.getHeaders()){
                if(response.headers().contains(header.getName())){
                    response.headers().remove(header.getName());
                    LOG.info("Removing response header: {}", header.getName());
                }
            }
        }

        if(ruleDefinition.getBody() != null && ruleDefinition.getBody().equals(MoxProxyConts.DELETE_BODY_INDICATOR)){

            ByteBuf contentBuf = response.content();
            ByteBuf byteBuf = Unpooled.copiedBuffer(MoxProxyConts.EMPTY_STRING, CharsetUtil.UTF_8);
            contentBuf.clear().writeBytes(byteBuf);
            HttpUtil.setContentLength(response, ruleDefinition.getBody().length());
            LOG.info("Removing response body");
        }

        return response;
    }

    private List<MoxProxyRule> getResponseRules(List<MoxProxyRule> rules){
        return rules.stream().filter(x->x.getAction() == MoxProxyAction.RESPOND).collect(Collectors.toList());
    }

    private List<MoxProxyRule> getModificationRules(List<MoxProxyRule> rules){
         return rules.stream().filter(x->x.getAction() == MoxProxyAction.MODIFY).collect(Collectors.toList());
    }

    private List<MoxProxyRule> getDeleteRules(List<MoxProxyRule> rules){
        return rules.stream().filter(x->x.getAction() == MoxProxyAction.DELETE).collect(Collectors.toList());
    }

    private MoxProxyRule getLatestResponseRule(List<MoxProxyRule> rules){
        rules.sort(Comparator.comparing(MoxProxyRule::getTimestamp));
        return rules.get(rules.size()-1);
    }
}
