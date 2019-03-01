package testing.builders;

import moxproxy.buildes.BaseBuilder;
import moxproxy.buildes.NullType;
import moxproxy.configuration.BeanNames;
import moxproxy.configuration.MoxProxyServiceConfigurationImpl;
import moxproxy.configuration.ServiceBeanConfiguration;
import moxproxy.exceptions.BuilderValidationException;
import moxproxy.interfaces.MoxProxy;
import moxproxy.interfaces.MoxProxyServiceConfiguration;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;

public class LocalMoxProxy extends BaseBuilder<NullType, LocalMoxProxy, MoxProxy, LocalMoxProxyServerBuilderValidator> {

    private int port;
    private boolean sessionIdMatchStrategy;
    private List<String> recorderWhiteList;

    private LocalMoxProxy() {
        super(null, new LocalMoxProxyServerBuilderValidator());
        recorderWhiteList = new ArrayList<>();
    }

    @Override
    protected MoxProxy performBuild() throws BuilderValidationException {
        MoxProxyServiceConfiguration configuration = new MoxProxyServiceConfigurationImpl(port, recorderWhiteList, sessionIdMatchStrategy);
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(ServiceBeanConfiguration.class);
        AutowireCapableBeanFactory definitionRegistry = ctx.getAutowireCapableBeanFactory();
        ((BeanDefinitionRegistry)definitionRegistry).removeBeanDefinition(BeanNames.CONFIGURATION_BEAN_NAME);
        ((SingletonBeanRegistry)definitionRegistry).registerSingleton(BeanNames.CONFIGURATION_BEAN_NAME, configuration);
        return ctx.getBean(MoxProxy.class);
    }

    @Override
    protected LocalMoxProxy getCurrentBuilder() {
        return this;
    }

    public static LocalMoxProxy builder(){
        return new LocalMoxProxy();
    }

    public LocalMoxProxy withPort(int port){
        this.port = port;
        return this;
    }

    public LocalMoxProxy withSessionIdMatchStrategy(){
        sessionIdMatchStrategy = true;
        return this;
    }

    public LocalMoxProxy withRecorderWhiteList(List<String> recorderWhiteList){
        this.recorderWhiteList = recorderWhiteList;
        return this;
    }

    int getPort() {
        return port;
    }
}
