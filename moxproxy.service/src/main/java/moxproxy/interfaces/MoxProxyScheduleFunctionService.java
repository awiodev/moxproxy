package moxproxy.interfaces;

import java.util.Date;

public interface MoxProxyScheduleFunctionService {

    void cleanProcessedTraffic(Date cleanBefore);
    void cleanRules(Date cleanBefore);
}
