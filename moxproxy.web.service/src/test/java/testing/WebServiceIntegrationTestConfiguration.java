package testing;

import client.WebServiceIntegrationTestClient;
import moxproxy.configuration.IMoxProxyClientConfiguration;
import moxproxy.configuration.MoxProxyClientConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class WebServiceIntegrationTestConfiguration {

    @Bean
    WebServiceIntegrationTestClient webServiceTestClient(){
        return new WebServiceIntegrationTestClient();
    }

    @Bean
    IMoxProxyClientConfiguration clientConfiguration(){
        return new MoxProxyClientConfiguration();
    }
}
