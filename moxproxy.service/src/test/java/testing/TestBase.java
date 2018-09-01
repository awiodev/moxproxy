package testing;

import moxproxy.dto.MoxProxyProcessedTrafficEntry;
import moxproxy.dto.MoxProxyRule;

public class TestBase {

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
