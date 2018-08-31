package moxproxy.factories;

import moxproxy.configuration.ServiceBeanConfiguration;
import moxproxy.interfaces.IMoxProxyServer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MoxProxyServiceFactory {

    public static IMoxProxyServer localServer(){
        ApplicationContext ctx = new AnnotationConfigApplicationContext(ServiceBeanConfiguration.class);
        return ctx.getBean(IMoxProxyServer.class);
    }
}
