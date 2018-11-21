package testing;

import com.fasterxml.jackson.databind.ObjectMapper;
import moxproxy.client.MoxProxyClient;
import moxproxy.configuration.IMoxProxyClientConfiguration;
import moxproxy.consts.MoxProxyConts;
import moxproxy.consts.MoxProxyRoutes;
import moxproxy.dto.MoxProxyProcessedTrafficEntry;
import moxproxy.interfaces.IMoxProxyDatabase;
import moxproxy.webservice.MoxProxyWebService;
import moxproxy.webservice.config.WebServiceBeanConfiguration;
import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MoxProxyWebService.class)
@AutoConfigureMockMvc
@Import({WebServiceE2ETestConfiguration.class, WebServiceBeanConfiguration.class})
public class WebServiceE2ETest {

    @Autowired
    MoxProxyClient moxProxyClient;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IMoxProxyDatabase database;

    @Autowired
    private IMoxProxyClientConfiguration configuration;

    private static ConfigurableApplicationContext context;

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
        Iterable<MoxProxyProcessedTrafficEntry> traffic = moxProxyClient.getAllRequestTraffic();
    }

    @Test
    public void intTest() throws Exception {

        database.addProcessedRequest(new MoxProxyProcessedTrafficEntry());

        ObjectMapper mapper = new ObjectMapper();
        String route = MoxProxyRoutes.API_ROUTE + MoxProxyRoutes.REQUESTS_ROUTE;

        String expected = "dupa";

        ResultActions result = mockMvc.perform(get(route).contentType(MediaType.APPLICATION_JSON).header(MoxProxyConts.AUTH_HEADER, "Basic " + encodeAuth())).andExpect(status().isOk());
        result.andDo(mockMvcResult -> {

           String json = mockMvcResult.getResponse().getContentAsString();
           List<MoxProxyProcessedTrafficEntry> proxyResponse = mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, MoxProxyProcessedTrafficEntry.class));

            assertEquals(expected, "dupa");
        });


    }

    private String encodeAuth(){
        var base64 = new Base64();
        String before = configuration.getUserName()+":"+configuration.getPassword();
        return new String(base64.encode(before.getBytes()));
    }
}
