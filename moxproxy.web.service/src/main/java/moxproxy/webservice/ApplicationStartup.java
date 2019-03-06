package moxproxy.webservice;

import moxproxy.interfaces.MoxProxy;
import moxproxy.webservice.config.WebServiceBeanConfiguration;
import moxproxy.webservice.config.WebServiceConfiguration;
import moxproxy.webservice.config.WebServiceSecurityConfig;
import moxproxy.webservice.services.CleanupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

@Component
@Import({WebServiceBeanConfiguration.class, WebServiceSecurityConfig.class})
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationStartup.class);

    @Autowired
    WebServiceConfiguration webServiceConfiguration;

    @Autowired
    @Qualifier("moxProxyServer")
    MoxProxy proxyServer;

    @Autowired
    CleanupService cleanupService;

    @Autowired
    TaskScheduler taskScheduler;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        try {
            proxyServer.startServer();
            int delay = webServiceConfiguration.getCleanupDelayInSeconds();
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.SECOND, delay);
            long millis = TimeUnit.SECONDS.toMillis(delay);
            taskScheduler.scheduleAtFixedRate(cleanup(), calendar.getTime(), millis);
        } catch (Exception e) {
            LOG.error("Not able to start proxy service", e);
            throw new RuntimeException(e);
        }
    }

    private Runnable cleanup(){
        return () -> cleanupService.performCleanup();
    }
}
