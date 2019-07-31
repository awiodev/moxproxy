package client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import moxproxy.configuration.MoxProxyClientConfiguration;
import moxproxy.consts.MoxProxyConts;
import moxproxy.consts.MoxProxyRoutes;
import moxproxy.exceptions.MoxProxyClientException;
import moxproxy.interfaces.MoxProxyService;
import moxproxy.model.*;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Component
public class WebServiceIntegrationTestClient implements MoxProxyService {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MoxProxyClientConfiguration configuration;

    private final ObjectMapper mapper;

    public WebServiceIntegrationTestClient( ){
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }

    @Override
    public List<MoxProxyProcessedTrafficEntry> getSessionRequestTraffic(String sessionId) {
        String route = MoxProxyRoutes.API_ROUTE + MoxProxyRoutes.REQUESTS_ROUTE_SESSION;
        try{
            MvcResult mockMvcResult = mockMvc.perform(get(route, sessionId).contentType(MediaType.APPLICATION_JSON).header(MoxProxyConts.AUTH_HEADER, authHeaderValue())).andExpect(status().isOk()).andReturn();
            String json = mockMvcResult.getResponse().getContentAsString();
            return mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, MoxProxyProcessedTrafficEntry.class));
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<MoxProxyProcessedTrafficEntry> getAllRequestTraffic() {
        String route = MoxProxyRoutes.API_ROUTE + MoxProxyRoutes.REQUESTS_ROUTE;
        try{
            MvcResult mockMvcResult = mockMvc.perform(get(route).contentType(MediaType.APPLICATION_JSON).header(MoxProxyConts.AUTH_HEADER, authHeaderValue())).andExpect(status().isOk()).andReturn();
            String json = mockMvcResult.getResponse().getContentAsString();
            return mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, MoxProxyProcessedTrafficEntry.class));
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<MoxProxyProcessedTrafficEntry> getSessionResponseTraffic(String sessionId) {
        String route = MoxProxyRoutes.API_ROUTE + MoxProxyRoutes.RESPONSES_ROUTE_SESSION;
        try{
            MvcResult mockMvcResult = mockMvc.perform(get(route, sessionId).contentType(MediaType.APPLICATION_JSON).header(MoxProxyConts.AUTH_HEADER, authHeaderValue())).andExpect(status().isOk()).andReturn();
            String json = mockMvcResult.getResponse().getContentAsString();
            return mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, MoxProxyProcessedTrafficEntry.class));
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<MoxProxyProcessedTrafficEntry> getAllResponseTraffic() {
        String route = MoxProxyRoutes.API_ROUTE + MoxProxyRoutes.RESPONSES_ROUTE;
        try{
            MvcResult mockMvcResult = mockMvc.perform(get(route).contentType(MediaType.APPLICATION_JSON).header(MoxProxyConts.AUTH_HEADER, authHeaderValue())).andExpect(status().isOk()).andReturn();
            String json = mockMvcResult.getResponse().getContentAsString();
            return mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, MoxProxyProcessedTrafficEntry.class));
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void cancelRule(String ruleId) {
        String route = MoxProxyRoutes.API_ROUTE + MoxProxyRoutes.RULES_ROUTE_ID;
        try{
            MvcResult mockMvcResult = mockMvc.perform(delete(route, ruleId).contentType(MediaType.APPLICATION_JSON).header(MoxProxyConts.AUTH_HEADER, authHeaderValue())).andExpect(status().isOk()).andReturn();
            String json = mockMvcResult.getResponse().getContentAsString();
            MoxProxyStatusResponse proxyResponse = mapper.readValue(json, MoxProxyStatusResponse.class);
            proxyStatusMessageShouldBe(proxyResponse, MoxProxyStatusMessage.DELETED);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clearSessionRules(String sessionId) {
        String route = MoxProxyRoutes.API_ROUTE + MoxProxyRoutes.RULES_SESSION_ID_ROUTE;
        try{
            MvcResult mockMvcResult = mockMvc.perform(delete(route, sessionId).contentType(MediaType.APPLICATION_JSON).header(MoxProxyConts.AUTH_HEADER, authHeaderValue())).andExpect(status().isOk()).andReturn();
            String json = mockMvcResult.getResponse().getContentAsString();
            MoxProxyStatusResponse proxyResponse = mapper.readValue(json, MoxProxyStatusResponse.class);
            proxyStatusMessageShouldBe(proxyResponse, MoxProxyStatusMessage.DELETED);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clearSessionEntries(String sessionId) {
        String route = MoxProxyRoutes.API_ROUTE + MoxProxyRoutes.SESSION_ID_ROUTE;
        try{
            MvcResult mockMvcResult = mockMvc.perform(delete(route, sessionId).contentType(MediaType.APPLICATION_JSON).header(MoxProxyConts.AUTH_HEADER, authHeaderValue())).andExpect(status().isOk()).andReturn();
            String json = mockMvcResult.getResponse().getContentAsString();
            MoxProxyStatusResponse proxyResponse = mapper.readValue(json, MoxProxyStatusResponse.class);
            proxyStatusMessageShouldBe(proxyResponse, MoxProxyStatusMessage.DELETED);
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    @Override
    public void clearAllSessionEntries() {
        String route = MoxProxyRoutes.API_ROUTE + MoxProxyRoutes.SESSION_ROUTE;
        try{
            MvcResult mockMvcResult = mockMvc.perform(delete(route).contentType(MediaType.APPLICATION_JSON).header(MoxProxyConts.AUTH_HEADER, authHeaderValue())).andExpect(status().isOk()).andReturn();
            String json = mockMvcResult.getResponse().getContentAsString();
            MoxProxyStatusResponse proxyResponse = mapper.readValue(json, MoxProxyStatusResponse.class);
            proxyStatusMessageShouldBe(proxyResponse, MoxProxyStatusMessage.DELETED);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public String createRule(MoxProxyRule moxProxyRule) {
        String route = MoxProxyRoutes.API_ROUTE + MoxProxyRoutes.RULES_ROUTE;
        try{
            String payload = mapper.writeValueAsString(moxProxyRule);
            MvcResult mockMvcResult = mockMvc.perform(post(route).contentType(MediaType.APPLICATION_JSON)
                    .content(payload)
                    .header(MoxProxyConts.AUTH_HEADER, authHeaderValue())).andExpect(status().isCreated()).andReturn();
            String json = mockMvcResult.getResponse().getContentAsString();
            MoxProxyStatusResponse proxyResponse = mapper.readValue(json, MoxProxyStatusResponse.class);
            proxyStatusMessageShouldBe(proxyResponse, MoxProxyStatusMessage.CREATED);
            return proxyResponse.getEntityId();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void modifySessionMatchingStrategy(MoxProxySessionIdMatchingStrategy matchingStrategy) {
        String route = MoxProxyRoutes.API_ROUTE + MoxProxyRoutes.SESSION_ROUTE_MATCH_STRATEGY;
        try{
            String payload = mapper.writeValueAsString(matchingStrategy);
            MvcResult mockMvcResult = mockMvc.perform(post(route).contentType(MediaType.APPLICATION_JSON)
                    .content(payload)
                    .header(MoxProxyConts.AUTH_HEADER, authHeaderValue())).andExpect(status().isOk()).andReturn();
            String json = mockMvcResult.getResponse().getContentAsString();
            MoxProxyStatusResponse proxyResponse = mapper.readValue(json, MoxProxyStatusResponse.class);
            proxyStatusMessageShouldBe(proxyResponse, MoxProxyStatusMessage.MODIFIED);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public MoxProxySessionIdMatchingStrategy getSessionMatchingStrategy() {
        String route = MoxProxyRoutes.API_ROUTE + MoxProxyRoutes.SESSION_ROUTE_MATCH_STRATEGY;
        try{
            MvcResult mockMvcResult = mockMvc.perform(get(route).contentType(MediaType.APPLICATION_JSON).header(MoxProxyConts.AUTH_HEADER, authHeaderValue())).andExpect(status().isOk()).andReturn();
            String json = mockMvcResult.getResponse().getContentAsString();
            return mapper.readValue(json, MoxProxySessionIdMatchingStrategy.class);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private String authHeaderValue(){
        return "Basic " + encodeAuth();
    }

    private String encodeAuth(){
        Base64 base64 = new Base64();
        String before = configuration.getUserName()+":"+configuration.getPassword();
        return new String(base64.encode(before.getBytes()));
    }

    private void proxyStatusMessageShouldBe(MoxProxyStatusResponse statusResponse, String expectedMessage){
        if(!statusResponse.getMessage().equals(expectedMessage)){
            throw new MoxProxyClientException("MoxProxy response message should be " + expectedMessage + " ,but was " + statusResponse.getMessage());
        }
    }
}
