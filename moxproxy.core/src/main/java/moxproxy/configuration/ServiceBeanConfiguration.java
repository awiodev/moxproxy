package moxproxy.configuration;

import moxproxy.converters.EntityConverterImpl;
import moxproxy.interfaces.*;
import moxproxy.interfaces.MoxProxyRulesMatcher;
import moxproxy.rules.MoxProxyRuleProcessorImpl;
import moxproxy.rules.MoxProxyRulesMatcherImpl;
import moxproxy.services.*;
import moxproxy.services.MoxProxyImpl;
import moxproxy.services.MoxProxyServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceBeanConfiguration {

    @Bean
    MoxProxyDatabase moxProxyDatabase(){
        return new MoxProxyDatabaseImpl();
    }

    @Bean
    MoxProxyService moxProxyService(){
        return new MoxProxyServiceImpl();
    }

    @Bean
    MoxProxyRulesMatcher moxProxyRules(){
        return new MoxProxyRulesMatcherImpl();
    }

    @Bean
    MoxProxyTrafficRecorder moxProxyTrafficRecorder(){
        return new MoxProxyTrafficRecorderImpl();
    }

    @Bean
    MoxProxy moxProxyServer(){
        return  new MoxProxyImpl();
    }

    @Bean(name = BeanNames.CONFIGURATION_BEAN_NAME)
    MoxProxyServiceConfiguration moxProxyServiceConfiguration(){
        return new MoxProxyServiceConfigurationImpl();
    }

    @Bean
    EntityConverter entityConverter(){
        return new EntityConverterImpl();
    }

    @Bean
    MoxProxyRuleProcessor moxProxyRuleProcessor(){
        return new MoxProxyRuleProcessorImpl();
    }
}
