package testing.database;

import moxproxy.interfaces.IMoxProxyDatabase;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import moxproxy.services.MoxProxyDatabase;

@TestConfiguration
class DatabaseTestConfiguration {

    @Bean
    IMoxProxyDatabase moxProxyDatabase(){
        return new MoxProxyDatabase();
    }
}
