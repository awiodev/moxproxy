package moxproxy.di;

import dagger.Module;
import dagger.Provides;
import moxproxy.converters.EntityConverterImpl;
import moxproxy.interfaces.*;
import moxproxy.rules.MoxProxyRuleProcessorImpl;
import moxproxy.rules.MoxProxyRulesMatcherImpl;
import moxproxy.services.MoxProxyDatabaseImpl;
import moxproxy.services.MoxProxyImpl;
import moxproxy.services.MoxProxyServiceImpl;
import moxproxy.services.MoxProxyTrafficRecorderImpl;

import javax.inject.Singleton;

@Module
public class ServiceModule {

    private MoxProxyServiceConfiguration moxProxyServiceConfiguration;

    public ServiceModule(MoxProxyServiceConfiguration moxProxyServiceConfiguration){
        this.moxProxyServiceConfiguration = moxProxyServiceConfiguration;
    }

    @Provides
    @Singleton
    MoxProxyDatabase moxProxyDatabase(){
        return new MoxProxyDatabaseImpl();
    }

    @Provides
    @Singleton
    MoxProxyRuleProcessor moxProxyRuleProcessor(MoxProxyDatabase database){
        return new MoxProxyRuleProcessorImpl(database);
    }

    @Provides
    @Singleton
    EntityConverter entityConverter(){
        return new EntityConverterImpl();
    }

    @Provides
    @Singleton
    MoxProxyRulesMatcher moxProxyRulesMatcher(MoxProxyDatabase moxProxyDatabase){
        return new MoxProxyRulesMatcherImpl(moxProxyDatabase);
    }

    @Provides
    @Singleton
    MoxProxyTrafficRecorder moxProxyTrafficRecorder(MoxProxyDatabase database){
        return new MoxProxyTrafficRecorderImpl(moxProxyServiceConfiguration, database);
    }

    @Provides
    @Singleton
    MoxProxy moxProxy(MoxProxyTrafficRecorder recorder,
                      EntityConverter converter, MoxProxyRuleProcessor processor,
                      MoxProxyDatabase database, MoxProxyRulesMatcher matcher){
        return new MoxProxyImpl(moxProxyServiceConfiguration, recorder, converter, processor, database, matcher);
    }

    @Provides
    @Singleton
    MoxProxyService moxProxyService(MoxProxyDatabase database, MoxProxyRulesMatcher matcher){
        return new MoxProxyServiceImpl(database, matcher, moxProxyServiceConfiguration);
    }
}
