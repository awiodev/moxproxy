package moxproxy.factories;

import moxproxy.configuration.ServiceBeanConfiguration;
import moxproxy.interfaces.MoxProxy;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MoxProxyServiceFactory {

    public static MoxProxy localServer(){
        ApplicationContext ctx = new AnnotationConfigApplicationContext(ServiceBeanConfiguration.class);
        return ctx.getBean(MoxProxy.class);
    }
}
