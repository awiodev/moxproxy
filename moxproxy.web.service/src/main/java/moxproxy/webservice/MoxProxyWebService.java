package moxproxy.webservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class MoxProxyWebService {

    public static void main(String[] args) {
        SpringApplication.run(MoxProxyWebService.class, args);
    }
}
