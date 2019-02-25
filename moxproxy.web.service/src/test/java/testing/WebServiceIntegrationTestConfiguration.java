package testing;

import client.WebServiceIntegrationTestClient;
import moxproxy.configuration.MoxProxyClientConfiguration;
import moxproxy.configuration.MoxProxyClientConfigurationImpl;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
class WebServiceIntegrationTestConfiguration {

    @Bean
    WebServiceIntegrationTestClient webServiceTestClient(){
        return new WebServiceIntegrationTestClient();
    }

    @Bean
    MoxProxyClientConfiguration clientConfiguration(){
        return new MoxProxyClientConfigurationImpl();
    }
}
