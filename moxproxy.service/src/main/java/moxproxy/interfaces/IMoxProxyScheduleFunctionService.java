package moxproxy.interfaces;

import java.util.Date;

public interface IMoxProxyScheduleFunctionService {

    void cleanProcessedTraffic(Date cleanBefore);
    void cleanRules(Date cleanBefore);
}
