package testing;

import client.WebServiceTestClient;
import moxproxy.client.MoxProxyClient;
import moxproxy.configuration.IMoxProxyClientConfiguration;
import moxproxy.dto.MoxProxyProcessedTrafficEntry;
import moxproxy.interfaces.IMoxProxyDatabase;
import moxproxy.webservice.MoxProxyWebService;
import moxproxy.webservice.config.WebServiceBeanConfiguration;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MoxProxyWebService.class)
@AutoConfigureMockMvc
@Import({WebServiceIntegrationTestConfiguration.class, WebServiceBeanConfiguration.class})
public class WebServiceIntegrationTest {

    /*@Autowired
    MockMvc mockMvc;

    @Autowired
    MoxProxyClient moxProxyClient;

    @Autowired
    IMoxProxyClientConfiguration configuration;*/

    @Autowired
    private WebServiceTestClient client;

    @Autowired
    private IMoxProxyDatabase database;

    //private static ConfigurableApplicationContext context;

    /*@BeforeAll
    public static void startService(){
        context = SpringApplication.run(MoxProxyWebService.class);
    }

    @AfterAll
    public static void stopService(){
        context.close();
    }*/

    //@Test
    public void test(){
        //Iterable<MoxProxyProcessedTrafficEntry> traffic = moxProxyClient.getAllRequestTraffic();
    }

    @Test
    public void intTest() throws Exception {

        database.addProcessedRequest(new MoxProxyProcessedTrafficEntry());

        ArrayList<MoxProxyProcessedTrafficEntry> traffic = Lists.newArrayList(client.getAllRequestTraffic());
    }
}
