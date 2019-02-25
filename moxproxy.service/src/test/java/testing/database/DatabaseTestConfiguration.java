package testing.database;

import moxproxy.interfaces.MoxProxyDatabase;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import moxproxy.services.MoxProxyDatabaseImpl;

@TestConfiguration
class DatabaseTestConfiguration {

    @Bean
    MoxProxyDatabase moxProxyDatabase(){
        return new MoxProxyDatabaseImpl();
    }
}
