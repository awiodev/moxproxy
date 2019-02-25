package moxproxy.interfaces;

import moxproxy.dto.MoxProxyRule;
import moxproxy.enums.MoxProxyDirection;

import java.util.List;

public interface MoxProxyRulesMatcher {

    void useSessionIdMatchingStrategy(boolean value);

    boolean getSessionIdMatchingStrategy();

    List<MoxProxyRule> match(HttpTrafficAdapter adapter, MoxProxyDirection moxProxyDirection);
}
