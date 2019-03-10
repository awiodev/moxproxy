package moxproxy.webservice.config;

import moxproxy.builders.AuthorityBuilder;
import moxproxy.configuration.MoxProxyServiceConfigurationImpl;
import moxproxy.converters.EntityConverterImpl;
import moxproxy.interfaces.*;
import moxproxy.rules.MoxProxyRuleProcessorImpl;
import moxproxy.rules.MoxProxyRulesMatcherImpl;
import moxproxy.services.MoxProxyDatabaseImpl;
import moxproxy.services.MoxProxyImpl;
import moxproxy.services.MoxProxyServiceImpl;
import moxproxy.services.MoxProxyTrafficRecorderImpl;
import org.littleshoot.proxy.mitm.Authority;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

import java.util.Arrays;

@Configuration
public class WebServiceBeanConfiguration {

    @Value("${proxy.proxyPort}")
    private int proxyPort;

    @Value("${proxy.sessionIdMatchStrategy}")
    private boolean sessionIdStrategEnabled;

    @Value("${proxy.urlWhiteListForTrafficRecorder}")
    private String[] urlWhiteList;

    @Value("${service.cleanupDelayInSeconds}")
    private int cleanupDelay;

    @Value("${service.basicAuthUserName}")
    private String basicAuthUserName;

    @Value("${service.basicAuthPassword}")
    private String basicAuthPassword;

    @Value("${mitm.createOwn}")
    private boolean createOwn;

    @Value("${mitm.keyStoreDir}")
    private String keyStoreDir;

    @Value("${mitm.alias}")
    private String alias;

    @Value("${mitm.password}")
    private String password;

    @Value("${mitm.organization}")
    private String organization;

    @Value("${mitm.commonName}")
    private String commonName;

    @Value("${mitm.organizationalUnitName}")
    private String organizationalUnitName;

    @Value("${mitm.certOrganization}")
    private String certOrganization;

    @Value("${mitm.certOrganizationalUnitName}")
    private String certOrganizationalUnitName;

    @Bean
    WebServiceConfiguration webServiceConfiguration(){
        var webserviceConfiguration = new WebServiceConfiguration();
        webserviceConfiguration.setCleanupDelayInSeconds(cleanupDelay);
        webserviceConfiguration.setBasicAuthUserName(basicAuthUserName);
        webserviceConfiguration.setBasicAuthPassword(basicAuthPassword);
        return webserviceConfiguration;
    }

    @Bean
    MoxProxyServiceConfiguration moxProxyServiceConfiguration(){

        Authority authority;

        if(createOwn){
            authority = AuthorityBuilder.create().withKeyStoreDir(keyStoreDir)
                    .withAlias(alias)
                    .withPassword(password)
                    .withOrganization(organization)
                    .withCommonName(commonName)
                    .withOrganizationalUnitName(organizationalUnitName)
                    .withCertOrganization(certOrganization)
                    .withCertOrganizationalUnitName(certOrganizationalUnitName).build();
        }else {
            authority = AuthorityBuilder.create().build();
        }

        return new MoxProxyServiceConfigurationImpl(proxyPort,
                Arrays.asList(urlWhiteList),
                sessionIdStrategEnabled, authority);
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
