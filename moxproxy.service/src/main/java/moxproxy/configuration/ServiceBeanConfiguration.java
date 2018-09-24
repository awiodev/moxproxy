package moxproxy.configuration;

import moxproxy.converters.EntityConverter;
import moxproxy.interfaces.*;
import moxproxy.interfaces.IMoxProxyRulesMatcher;
import moxproxy.rules.MoxProxyRuleProcessor;
import moxproxy.rules.MoxProxyRulesMatcher;
import moxproxy.services.MoxProxyDatabase;
import moxproxy.services.MoxProxyServer;
import moxproxy.services.MoxProxyService;
import moxproxy.services.MoxProxyTrafficRecorder;
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
    IMoxProxyTrafficRecorder moxProxyTrafficRecorder(){
        return new MoxProxyTrafficRecorder();
    }

    @Bean
    IMoxProxyServer moxProxyServer(){
        return new MoxProxyServer();
    }

    @Bean
    IMoxProxyServiceConfiguration moxProxyServiceConfiguration(){
        return new MoxProxyServiceConfiguration();
    }

    @Bean
    IEntityConverter entityConverter(){
        return new EntityConverter();
    }

    @Bean
    IMoxProxyRuleProcessor moxProxyRuleProcessor(){
        return new MoxProxyRuleProcessor();
    }
}
