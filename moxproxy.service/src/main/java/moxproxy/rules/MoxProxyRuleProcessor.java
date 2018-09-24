package moxproxy.rules;

import io.netty.handler.codec.http.HttpObject;
import moxproxy.dto.MoxProxyRule;
import moxproxy.interfaces.IHttpRequestAdapter;
import moxproxy.interfaces.IHttpResponseAdapter;
import moxproxy.interfaces.IMoxProxyRuleProcessor;

import java.util.List;

public class MoxProxyRuleProcessor implements IMoxProxyRuleProcessor {

    @Override
    public MoxProxyRuleProcessingResult processRequest(List<MoxProxyRule> rules, IHttpRequestAdapter requestAdapter, HttpObject request) {
        return null;
    }

    @Override
    public MoxProxyRuleProcessingResult processResponse(List<MoxProxyRule> rules, IHttpResponseAdapter requestAdapter, HttpObject response) {
        return null;
    }
}
