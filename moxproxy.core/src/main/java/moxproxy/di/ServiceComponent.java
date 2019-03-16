package moxproxy.di;

import dagger.Component;
import moxproxy.interfaces.MoxProxy;
import moxproxy.interfaces.MoxProxyDatabase;
import moxproxy.interfaces.MoxProxyService;

import javax.inject.Singleton;

@Singleton
@Component(modules = ServiceModule.class)
public interface ServiceComponent {

    MoxProxy getMoxProxy();

    MoxProxyDatabase getMoxProxyDatabase();

    MoxProxyService getMoxProxyService();
}
