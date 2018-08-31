package moxproxy.configuration;

import moxproxy.interfaces.*;
import moxproxy.rules.MoxProxyRules;
import moxproxy.services.MoxProxyDatabase;
import moxproxy.services.MoxProxyServer;
import moxproxy.services.MoxProxyService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceBeanConfiguration {

    @Bean
    IMoxProxyDatabase moxProxyDatabase(){
        return new MoxProxyDatabase();
    }

    @Bean
    IMoxProxyService moxProxyService(){
        return new MoxProxyService();
    }

    @Bean
    IMoxProxyRules moxProxyRules(){
        return new MoxProxyRules();
    }

    @Bean
    IMoxProxyServer moxProxyServer(){
        return new MoxProxyServer();
    }

    @Bean
    IMoxProxyServiceConfiguration moxProxyServiceConfiguration(){
        return new MoxProxyServiceConfiguration();
    }
}
