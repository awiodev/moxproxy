package moxproxy.interfaces;

import io.netty.handler.codec.http.HttpObject;
import moxproxy.model.MoxProxyRule;
import moxproxy.rules.MoxProxyRuleProcessingResult;

import java.util.List;

public interface MoxProxyRuleProcessor {

    MoxProxyRuleProcessingResult processRequest(List<MoxProxyRule> rules, HttpObject request);
    MoxProxyRuleProcessingResult processResponse(List<MoxProxyRule> rules, HttpObject response);
    void postProcessRules(List<MoxProxyRule> rules);
}
