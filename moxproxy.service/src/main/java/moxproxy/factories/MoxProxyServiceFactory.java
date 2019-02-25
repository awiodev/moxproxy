package moxproxy.factories;

import moxproxy.configuration.ServiceBeanConfiguration;
import moxproxy.interfaces.MoxProxyServer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MoxProxyServiceFactory {

    public static MoxProxyServer localServer(){
        ApplicationContext ctx = new AnnotationConfigApplicationContext(ServiceBeanConfiguration.class);
        return ctx.getBean(MoxProxyServer.class);
    }
}
