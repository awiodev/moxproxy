package testing.service;

import moxproxy.configuration.MoxProxyServiceConfigurationImpl;
import moxproxy.interfaces.MoxProxyDatabase;
import moxproxy.interfaces.MoxProxyRulesMatcher;
import moxproxy.interfaces.MoxProxyService;
import moxproxy.interfaces.MoxProxyServiceConfiguration;
import moxproxy.rules.MoxProxyRulesMatcherImpl;
import moxproxy.services.MoxProxyDatabaseImpl;
import moxproxy.services.MoxProxyServiceImpl;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
class ProxyServiceTestConfiguration {

    @Bean
    MoxProxyDatabase moxProxyDatabase(){
        return new MoxProxyDatabaseImpl();
    }

    @Bean
    MoxProxyService moxProxyService(){
        return new MoxProxyServiceImpl();
    }

    @Bean
    MoxProxyRulesMatcher moxProxyMatcher(){
        return new MoxProxyRulesMatcherImpl();
    }

    @Bean
    MoxProxyServiceConfiguration moxProxyServiceConfiguration() {
        return new MoxProxyServiceConfigurationImpl();
    }
}
