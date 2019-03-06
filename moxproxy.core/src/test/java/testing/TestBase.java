package testing;

import moxproxy.model.MoxProxyProcessedTrafficEntry;
import moxproxy.model.MoxProxyRule;

public class TestBase {

    protected static final String UNKNOWN = "UNKNOWN";

    protected MoxProxyProcessedTrafficEntry createDefaultTrafficEntry(){
        var trafficEntry = new MoxProxyProcessedTrafficEntry();
        trafficEntry.setSessionId("123");
        return trafficEntry;
    }

    protected MoxProxyRule createDefaultRule(){
        var rule = new MoxProxyRule();
        rule.setSessionId("456");
        return rule;
    }
}
