package moxproxy.webservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import moxproxy.configuration.MoxProxyServiceConfigurationImpl;
import moxproxy.converters.EntityConverterImpl;
import moxproxy.interfaces.*;
import moxproxy.rules.MoxProxyRuleProcessorImpl;
import moxproxy.rules.MoxProxyRulesMatcherImpl;
import moxproxy.services.*;
import moxproxy.services.MoxProxyServerImpl;
import moxproxy.services.MoxProxyServiceImpl;
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
import java.util.Objects;

@Configuration
public class WebServiceBeanConfiguration {

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
    MoxProxyScheduleFunctionService moxProxyScheduleService(){
        return new MoxProxyServiceImpl();
    }

    @Bean
    TaskScheduler taskScheduler(){
        return new ConcurrentTaskScheduler();
    }

    @Bean
    MoxProxyDatabase moxProxyDatabase(){
        return new MoxProxyDatabaseImpl();
    }

    @Bean(name = "moxProxyService")
    MoxProxyService moxProxyService(){
        return new MoxProxyServiceImpl();
    }

    @Bean
    MoxProxyRulesMatcher moxProxyRules(){
        return new MoxProxyRulesMatcherImpl();
    }

    @Bean
    MoxProxyTrafficRecorder moxProxyTrafficRecorder(){
        return new MoxProxyTrafficRecorderImpl();
    }

    @Bean(name = "moxProxyServer")
    MoxProxyServer moxProxyServer(){
        return new MoxProxyServerImpl();
    }

    @Bean
    MoxProxyServiceConfiguration moxProxyServiceConfiguration(){
        return new MoxProxyServiceConfigurationImpl(webServiceConfiguration.getProxyPort(),
                webServiceConfiguration.getUrlWhiteListForTrafficRecorder(),
                webServiceConfiguration.isSessionIdMatchStrategy());
    }

    @Bean
    EntityConverter entityConverter(){
        return new EntityConverterImpl();
    }

    @Bean
    MoxProxyRuleProcessor moxProxyRuleProcessor(){
        return new MoxProxyRuleProcessorImpl();
    }

    @Bean
    AuthenticationEntryPoint authenticationEntryPoint(){
        return new AuthenticationEntryPoint();
    }

    private File getConfigFile() throws FileNotFoundException {
        File configFile;
        String cfgFileName = "webServiceConfiguration.json";
        configFile = new File(cfgFileName);
        if(configFile.exists()){
            return configFile;
        }
        configFile = new File(Objects.requireNonNull(getClass().getClassLoader().getResource(cfgFileName)).getFile());
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
