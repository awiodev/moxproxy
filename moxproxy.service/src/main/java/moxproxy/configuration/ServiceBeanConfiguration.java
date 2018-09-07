package moxproxy.configuration;

import moxproxy.interfaces.*;
import moxproxy.rules.MoxProxyRulesMatcher;
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
    IMoxProxyRulesMatcher moxProxyRules(){
        return new MoxProxyRulesMatcher();
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
