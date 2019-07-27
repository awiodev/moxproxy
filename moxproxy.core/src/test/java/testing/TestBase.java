package testing;

import moxproxy.model.MoxProxyProcessedTrafficEntry;
import moxproxy.model.MoxProxyRule;

public class TestBase {

    protected static final String UNKNOWN = "UNKNOWN";

    protected MoxProxyProcessedTrafficEntry createDefaultTrafficEntry(String sessionId){
        return new MoxProxyProcessedTrafficEntry(sessionId, null, null, null, null);
    }

    protected MoxProxyProcessedTrafficEntry createDefaultTrafficEntry(){
        return createDefaultTrafficEntry("123");
    }

    protected MoxProxyRule createDefaultRule(){
        MoxProxyRule rule = new MoxProxyRule();
        rule.setSessionId("456");
        return rule;
    }
}
