package moxproxy.webservice.config;

import moxproxy.interfaces.IMoxProxyScheduleFunctionService;
import moxproxy.services.MoxProxyService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebServiceBeanConfiguration {

    @Bean
    IMoxProxyScheduleFunctionService moxProxyService(){
        return new MoxProxyService();
    }
}
