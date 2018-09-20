package testing.service;

import moxproxy.interfaces.IMoxProxyDatabase;
import moxproxy.interfaces.IMoxProxyRulesMatcher;
import moxproxy.interfaces.IMoxProxyService;
import moxproxy.rules.MoxProxyRulesMatcher;
import moxproxy.services.MoxProxyDatabase;
import moxproxy.services.MoxProxyService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ProxyServiceTestConfiguration {

    @Bean
    IMoxProxyDatabase moxProxyDatabase(){
        return new MoxProxyDatabase();
    }

    @Bean
    IMoxProxyService moxProxyService(){
        return new MoxProxyService();
    }

    @Bean
    IMoxProxyRulesMatcher moxProxyMatcher(){
        return new MoxProxyRulesMatcher();
    }
}
