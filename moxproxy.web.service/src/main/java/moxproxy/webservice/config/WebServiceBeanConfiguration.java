package moxproxy.webservice.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import moxproxy.configuration.MoxProxyServiceConfigurationImpl;
import moxproxy.converters.EntityConverterImpl;
import moxproxy.interfaces.*;
import moxproxy.rules.MoxProxyRuleProcessorImpl;
import moxproxy.rules.MoxProxyRulesMatcherImpl;
import moxproxy.services.MoxProxyDatabaseImpl;
import moxproxy.services.MoxProxyImpl;
import moxproxy.services.MoxProxyServiceImpl;
import moxproxy.services.MoxProxyTrafficRecorderImpl;
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
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Configuration
public class WebServiceBeanConfiguration {

    private static final String CFG_FILE = "application.conf";

    private WebServiceConfiguration webServiceConfiguration;

    public WebServiceBeanConfiguration() throws IOException {
        webServiceConfiguration = new WebServiceConfiguration();
        File configFile = getConfigFile();
        Config config = ConfigFactory.parseFile(configFile);
        Config proxy = config.getConfig("proxy").resolve();
        Config service = config.getConfig("web-service").resolve();
        webServiceConfiguration.setProxyPort(proxy.getInt("proxy-port"));
        webServiceConfiguration.setSessionIdMatchStrategy(proxy.getBoolean("use-proxy-session-id-matching-strat"));
        webServiceConfiguration.setUrlWhiteListForTrafficRecorder(proxy.getStringList("proxy-white-list"));
        webServiceConfiguration.setWebServicePort(service.getInt("service-port"));
        webServiceConfiguration.setBasicAuthUserName(service.getString("service-auth-user"));
        webServiceConfiguration.setBasicAuthPassword(service.getString("service-auth-password"));
        webServiceConfiguration.setCleanupDelayInSeconds(service.getInt("service-cleanup-delay-in-seconds"));
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
    MoxProxy moxProxyServer(){
        return new MoxProxyImpl();
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

    private File getConfigFile() throws IOException {

        URL resource = (getClass().getClassLoader().getResource(CFG_FILE));
        if(null != resource){
            return new File(resource.getFile());
        }

        String workingDir = System.getProperty("user.dir");
        Optional<Path> filePath = Files.walk(Paths.get(workingDir)).filter(x -> x.getFileName().endsWith(CFG_FILE)).findFirst();

        if(filePath.isPresent()){
            return new File(filePath.get().toString());
        }
        throw new FileNotFoundException("Configuration file " + CFG_FILE + " not found in working directory neither resource directory");
    }

    @Component
    public class CustomSetup implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory>{

        @Override
        public void customize(ConfigurableServletWebServerFactory factory) {
            factory.setPort(webServiceConfiguration.getWebServicePort());
        }
    }
}
