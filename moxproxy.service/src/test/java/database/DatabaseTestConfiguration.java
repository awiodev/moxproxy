package database;

import interfaces.IMoxProxyDatabase;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import services.MoxProxyDatabase;

@TestConfiguration
public class DatabaseTestConfiguration {

    @Bean
    IMoxProxyDatabase moxProxyDatabase(){
        return new MoxProxyDatabase();
    }
}
