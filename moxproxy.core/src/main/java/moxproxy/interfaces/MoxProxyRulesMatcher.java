package moxproxy.interfaces;

import moxproxy.enums.MoxProxyDirection;
import moxproxy.model.MoxProxyProcessedTrafficEntry;
import moxproxy.model.MoxProxyRule;

import java.util.List;

public interface MoxProxyRulesMatcher {

    void useSessionIdMatchingStrategy(boolean value);

    boolean getSessionIdMatchingStrategy();

    List<MoxProxyRule> match(MoxProxyProcessedTrafficEntry adapter, MoxProxyDirection moxProxyDirection);
}
