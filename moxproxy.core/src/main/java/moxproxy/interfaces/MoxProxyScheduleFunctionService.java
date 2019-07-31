package moxproxy.interfaces;

import java.time.OffsetDateTime;

public interface MoxProxyScheduleFunctionService {

    void cleanProcessedTraffic(OffsetDateTime cleanBefore);
    void cleanRules(OffsetDateTime cleanBefore);
}
