package moxproxy.webservice.services;

import moxproxy.interfaces.MoxProxyScheduleFunctionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Component
public class CleanupService {

    private static final Logger LOG = LoggerFactory.getLogger(CleanupService.class);

    @Autowired
    @Qualifier("moxProxyScheduleService")
    private MoxProxyScheduleFunctionService moxProxyCleanupService;
    
    public void performCleanup(){
        LOG.info("Staring cleanup service");
        OffsetDateTime today = OffsetDateTime.now(ZoneOffset.UTC);
        moxProxyCleanupService.cleanProcessedTraffic(today);
        moxProxyCleanupService.cleanRules(today);
        LOG.info("Cleanup service complete");
    }
}
