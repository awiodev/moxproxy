package moxproxy.webservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import moxproxy.configuration.MoxProxyServiceConfiguration;
import moxproxy.converters.EntityConverter;
import moxproxy.interfaces.*;
import moxproxy.rules.MoxProxyRuleProcessor;
import moxproxy.rules.MoxProxyRulesMatcher;
import moxproxy.services.MoxProxyDatabase;
import moxproxy.services.MoxProxyServer;
import moxproxy.services.MoxProxyService;
import moxproxy.services.MoxProxyTrafficRecorder;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

@Configuration
public class WebServiceBeanConfiguration {

    private final String cfgFileName = "webServiceConfiguration.json";

    private WebServiceConfiguration webServiceConfiguration;

    public WebServiceBeanConfiguration() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = getConfigFile();
        webServiceConfiguration = objectMapper.readValue(file, WebServiceConfiguration.class);
    }

    @Bean
    WebServiceConfiguration webServiceConfiguration(){
        return webServiceConfiguration;
    }

    @Bean(name = "moxProxyScheduleService")
    IMoxProxyScheduleFunctionService moxProxyScheduleService(){
        return new MoxProxyService();
    }

    @Bean
    TaskScheduler taskScheduler(){
        return new ConcurrentTaskScheduler();
    }

    @Bean
    IMoxProxyDatabase moxProxyDatabase(){
        return new MoxProxyDatabase();
    }

    @Bean(name = "moxProxyService")
    IMoxProxyService moxProxyService(){
        return new MoxProxyService();
    }

    @Bean
    IMoxProxyRulesMatcher moxProxyRules(){
        return new MoxProxyRulesMatcher();
    }

    @Bean
    IMoxProxyTrafficRecorder moxProxyTrafficRecorder(){
        return new MoxProxyTrafficRecorder();
    }

    @Bean(name = "moxProxyServer")
    IMoxProxyServer moxProxyServer(){
        return new MoxProxyServer();
    }

    @Bean
    IMoxProxyServiceConfiguration moxProxyServiceConfiguration(){
        return new MoxProxyServiceConfiguration(webServiceConfiguration.getProxyPort());
    }

    @Bean
    IEntityConverter entityConverter(){
        return new EntityConverter();
    }

    @Bean
    IMoxProxyRuleProcessor moxProxyRuleProcessor(){
        return new MoxProxyRuleProcessor();
    }

    private File getConfigFile() throws FileNotFoundException {
        File configFile = null;
        configFile = new File(cfgFileName);
        if(configFile.exists()){
            return configFile;
        }
        configFile = new File(getClass().getClassLoader().getResource(cfgFileName).getFile());
        if(configFile.exists()){
            return configFile;
        }
        throw new FileNotFoundException("Configuration file " + cfgFileName + " not found in working directory neither resource directory");
    }

    @Component
    public class CustomSetup implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory>{

        @Override
        public void customize(ConfigurableServletWebServerFactory factory) {
            factory.setPort(webServiceConfiguration.getWebServicePort());
        }
    }
}
