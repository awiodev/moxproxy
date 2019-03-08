package moxproxy.webservice.config;

import moxproxy.configuration.MoxProxyServiceConfigurationImpl;
import moxproxy.converters.EntityConverterImpl;
import moxproxy.interfaces.*;
import moxproxy.rules.MoxProxyRuleProcessorImpl;
import moxproxy.rules.MoxProxyRulesMatcherImpl;
import moxproxy.services.MoxProxyDatabaseImpl;
import moxproxy.services.MoxProxyImpl;
import moxproxy.services.MoxProxyServiceImpl;
import moxproxy.services.MoxProxyTrafficRecorderImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

import java.util.Arrays;

@Configuration
public class WebServiceBeanConfiguration {

    @Value("${service.proxyPort}")
    private int proxyPort;

    @Value("${service.sessionIdMatchStrategy}")
    private boolean sessionIdStrategEnabled;

    @Value("${service.urlWhiteListForTrafficRecorder}")
    private String[] urlWhiteList;

    @Value("${service.cleanupDelayInSeconds}")
    private int cleanupDelay;

    @Value("${service.basicAuthUserName}")
    private String basicAuthUserName;

    @Value("${service.basicAuthPassword}")
    private String basicAuthPassword;

    @Bean
    WebServiceConfiguration webServiceConfiguration(){
        var webserviceConfiguration = new WebServiceConfiguration();
        webserviceConfiguration.setProxyPort(proxyPort);
        webserviceConfiguration.setUrlWhiteListForTrafficRecorder(Arrays.asList(urlWhiteList));
        webserviceConfiguration.setSessionIdMatchStrategy(sessionIdStrategEnabled);
        webserviceConfiguration.setCleanupDelayInSeconds(cleanupDelay);
        webserviceConfiguration.setBasicAuthUserName(basicAuthUserName);
        webserviceConfiguration.setBasicAuthPassword(basicAuthPassword);
        return webserviceConfiguration;
    }

    @Bean
    MoxProxyServiceConfiguration moxProxyServiceConfiguration(WebServiceConfiguration webServiceConfiguration){
        return new MoxProxyServiceConfigurationImpl(webServiceConfiguration.getProxyPort(),
                webServiceConfiguration.getUrlWhiteListForTrafficRecorder(),
                webServiceConfiguration.isSessionIdMatchStrategy());
    }

    @Bean
    MoxProxyDatabase moxProxyDatabase(){
        return new MoxProxyDatabaseImpl();
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
    TaskScheduler taskScheduler(){
        return new ConcurrentTaskScheduler();
    }

    @Bean
    MoxProxyRulesMatcher moxProxyRules(MoxProxyDatabase database){
        return new MoxProxyRulesMatcherImpl(database);
    }

    @Bean
    MoxProxyTrafficRecorder moxProxyTrafficRecorder(MoxProxyServiceConfiguration moxProxyServiceConfiguration, MoxProxyDatabase moxProxyDatabase){
        return new MoxProxyTrafficRecorderImpl(moxProxyServiceConfiguration, moxProxyDatabase);
    }

    @Bean(name = "moxProxyScheduleService")
    MoxProxyScheduleFunctionService moxProxyScheduleService(MoxProxyDatabase database, MoxProxyRulesMatcher matcher, MoxProxyServiceConfiguration configuration){
        return new MoxProxyServiceImpl(database, matcher, configuration);
    }

    @Bean(name = "moxProxyService")
    MoxProxyService moxProxyService(MoxProxyDatabase moxProxyDatabase, MoxProxyRulesMatcher matcher, MoxProxyServiceConfiguration configuration){
        return new MoxProxyServiceImpl(moxProxyDatabase, matcher, configuration);
    }

    @Bean(name = "moxProxyServer")
    MoxProxy moxProxyServer(MoxProxyServiceConfiguration configuration, MoxProxyTrafficRecorder trafficRecorder, EntityConverter converter,
                            MoxProxyRuleProcessor processor, MoxProxyDatabase database, MoxProxyRulesMatcher matcher){
        return new MoxProxyImpl(configuration, trafficRecorder, converter, processor, database, matcher);
    }

    @Bean
    AuthenticationEntryPoint authenticationEntryPoint(){
        return new AuthenticationEntryPoint();
    }
}
