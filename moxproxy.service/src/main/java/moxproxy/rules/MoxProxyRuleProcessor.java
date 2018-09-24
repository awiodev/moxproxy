package moxproxy.rules;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;
import moxproxy.dto.MoxProxyHeader;
import moxproxy.dto.MoxProxyHttpRuleDefinition;
import moxproxy.dto.MoxProxyRule;
import moxproxy.enums.MoxProxyAction;
import moxproxy.interfaces.IMoxProxyRuleProcessor;

import java.nio.charset.Charset;
import java.util.List;

public class MoxProxyRuleProcessor implements IMoxProxyRuleProcessor {

    @Override
    public MoxProxyRuleProcessingResult processRequest(List<MoxProxyRule> rules, HttpObject request) {

        var ruleProcessingResult = new MoxProxyRuleProcessingResult();

        for(MoxProxyRule rule : rules){
            if(rule.getAction() == MoxProxyAction.RESPOND){
                ruleProcessingResult.setRespond(true);
                ruleProcessingResult.setResponse(createDefaultResponse(rule));
                break;
            }
        }

        return ruleProcessingResult;
    }

    @Override
    public MoxProxyRuleProcessingResult processResponse(List<MoxProxyRule> rules, HttpObject response) {
        var ruleProcessingResult = new MoxProxyRuleProcessingResult();

        return ruleProcessingResult;
    }

    private FullHttpResponse createDefaultResponse(MoxProxyRule rule){
        MoxProxyHttpRuleDefinition httpObject = rule.getMoxProxyHttpObject();
        HttpResponseStatus responseCode =  HttpResponseStatus.valueOf(httpObject.getStatusCode());

        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, responseCode);

        if(httpObject.getHeaders() != null && !httpObject.getHeaders().isEmpty()){
            for(MoxProxyHeader header : httpObject.getHeaders()){
                response.headers().add(header.getName(), header.getValue());
            }
        }

        if(httpObject.getBody() != null){
            response = response.replace(Unpooled.copiedBuffer(httpObject.getBody().getBytes(Charset.forName("UTF-8"))));
        }

        return response;
    }
}
