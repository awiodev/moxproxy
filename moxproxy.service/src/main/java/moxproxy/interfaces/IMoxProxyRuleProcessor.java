package moxproxy.interfaces;

import io.netty.handler.codec.http.HttpObject;
import moxproxy.dto.MoxProxyRule;
import moxproxy.rules.MoxProxyRuleProcessingResult;

import java.util.List;

public interface IMoxProxyRuleProcessor {

    MoxProxyRuleProcessingResult processRequest(List<MoxProxyRule> rules, IHttpRequestAdapter requestAdapter, HttpObject request);
    MoxProxyRuleProcessingResult processResponse(List<MoxProxyRule> rules, IHttpResponseAdapter requestAdapter, HttpObject response);
}
