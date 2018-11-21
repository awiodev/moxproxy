package testing;

import client.WebServiceTestClient;
import moxproxy.client.MoxProxyClient;
import moxproxy.configuration.IMoxProxyClientConfiguration;
import moxproxy.configuration.MoxProxyClientConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class WebServiceIntegrationTestConfiguration {

    @Bean
    WebServiceTestClient webServiceTestClient(){
        return new WebServiceTestClient();
    }

    @Bean
    IMoxProxyClientConfiguration clientConfiguration(){
        return new MoxProxyClientConfiguration();
    }
}
