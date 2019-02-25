package testing.factories;

import moxproxy.factories.MoxProxyServiceFactory;
import moxproxy.interfaces.MoxProxyServer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class ServiceFactoryTest {

    @Test
    void givenFactory_whenCreateLocal_ThenLocalServerCreated(){
        MoxProxyServer server = MoxProxyServiceFactory.localServer();
        assertNotNull(server);
    }
}
