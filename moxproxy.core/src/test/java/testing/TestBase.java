package testing;

import moxproxy.model.MoxProxyProcessedTrafficEntry;
import moxproxy.model.MoxProxyRule;

public class TestBase {

    protected static final String[] FIELDS_TO_IGNORE = new String[]{"id", "timestamp"};
    protected static final String UNKNOWN = "UNKNOWN";

    protected MoxProxyProcessedTrafficEntry createDefaultTrafficEntry(String sessionId){
        return new MoxProxyProcessedTrafficEntry(sessionId, null, null, null, null, 0);
    }

    protected MoxProxyProcessedTrafficEntry createDefaultTrafficEntry(){
        return createDefaultTrafficEntry("123");
    }

    protected MoxProxyRule createDefaultRule(String sessionId){
        return new MoxProxyRule(sessionId, null, null, null, -1);
    }

    protected MoxProxyRule createDefaultRule(){
        return createDefaultRule("456");
    }
}
