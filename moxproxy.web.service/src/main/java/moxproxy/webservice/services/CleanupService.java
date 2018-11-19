package moxproxy.webservice.services;

import moxproxy.interfaces.IMoxProxyScheduleFunctionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class CleanupService {

    private static final Logger LOG = LoggerFactory.getLogger(CleanupService.class);

    @Autowired
    @Qualifier("moxProxyScheduleService")
    IMoxProxyScheduleFunctionService moxProxyCleanupService;
    
    public void performCleanup(){
        LOG.info("Staring cleanup service");
        Date today = Calendar.getInstance().getTime();
        moxProxyCleanupService.cleanProcessedTraffic(today);
        moxProxyCleanupService.cleanRules(today);
        LOG.info("Cleanup service complete");
    }
}
