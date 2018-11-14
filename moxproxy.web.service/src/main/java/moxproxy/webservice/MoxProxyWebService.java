package moxproxy.webservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MoxProxyWebService {

    public static void main(String[] args) {
        SpringApplication.run(MoxProxyWebService.class, args);
    }
}
