package moxproxy.interfaces;

import moxproxy.adapters.IHttpTrafficAdapter;
import moxproxy.dto.MoxProxyRule;

import java.util.List;

public interface IMoxProxyRulesMatcher {

    List<MoxProxyRule> match(IHttpTrafficAdapter adapter);
}
