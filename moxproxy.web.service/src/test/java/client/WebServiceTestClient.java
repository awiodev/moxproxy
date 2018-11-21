package client;

import com.fasterxml.jackson.databind.ObjectMapper;
import moxproxy.configuration.IMoxProxyClientConfiguration;
import moxproxy.consts.MoxProxyConts;
import moxproxy.consts.MoxProxyRoutes;
import moxproxy.dto.MoxProxyProcessedTrafficEntry;
import moxproxy.dto.MoxProxyRule;
import moxproxy.interfaces.IMoxProxyService;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Component
public class WebServiceTestClient implements IMoxProxyService {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    IMoxProxyClientConfiguration configuration;



    private final ObjectMapper mapper;

    public WebServiceTestClient( ){
        mapper = new ObjectMapper();
    }

    @Override
    public Iterable<MoxProxyProcessedTrafficEntry> getSessionRequestTraffic(String sessionId) {
        return null;
    }

    @Override
    public Iterable<MoxProxyProcessedTrafficEntry> getAllRequestTraffic() {
        String route = MoxProxyRoutes.API_ROUTE + MoxProxyRoutes.REQUESTS_ROUTE;
        try{
            MvcResult mockMvcResult = mockMvc.perform(get(route).contentType(MediaType.APPLICATION_JSON).header(MoxProxyConts.AUTH_HEADER, "Basic " + encodeAuth())).andExpect(status().isOk()).andReturn();
            String json = mockMvcResult.getResponse().getContentAsString();
            List<MoxProxyProcessedTrafficEntry> proxyResponse = mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, MoxProxyProcessedTrafficEntry.class));
            return proxyResponse;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Iterable<MoxProxyProcessedTrafficEntry> getSessionResponseTraffic(String sessionId) {
        return null;
    }

    @Override
    public Iterable<MoxProxyProcessedTrafficEntry> getAllResponseTraffic() {
        return null;
    }

    @Override
    public void cancelRule(String ruleId) {

    }

    @Override
    public void clearSessionRules(String sessionId) {

    }

    @Override
    public void clearSessionEntries(String sessionId) {

    }

    @Override
    public void clearAllSessionEntries() {

    }

    @Override
    public String createRule(MoxProxyRule moxProxyRule) {
        return null;
    }

    @Override
    public void enableSessionIdMatchingStrategy() {

    }

    @Override
    public void disableSessionIdMatchingStrategy() {

    }

    private String encodeAuth(){
        var base64 = new Base64();
        String before = configuration.getUserName()+":"+configuration.getPassword();
        return new String(base64.encode(before.getBytes()));
    }
}
