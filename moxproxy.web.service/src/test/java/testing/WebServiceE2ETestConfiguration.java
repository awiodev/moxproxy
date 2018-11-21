package testing;

import moxproxy.client.MoxProxyClient;
import moxproxy.configuration.IMoxProxyClientConfiguration;
import moxproxy.configuration.MoxProxyClientConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class WebServiceE2ETestConfiguration {

    @Bean
    MoxProxyClient moxProxyClient(){
        IMoxProxyClientConfiguration clientConfiguration = new MoxProxyClientConfiguration();
        return new MoxProxyClient(clientConfiguration);
    }

    @Bean
    IMoxProxyClientConfiguration clientConfiguration(){
        return new MoxProxyClientConfiguration();
    }
}
