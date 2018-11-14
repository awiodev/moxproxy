package moxproxy.webservice;

import moxproxy.configuration.ServiceBeanConfiguration;
import moxproxy.interfaces.IMoxProxyServer;
import moxproxy.webservice.config.WebServiceBeanConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

@Component
@Import({ServiceBeanConfiguration.class, WebServiceBeanConfiguration.class})
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    IMoxProxyServer proxyServer;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        proxyServer.startServer();
    }
}
