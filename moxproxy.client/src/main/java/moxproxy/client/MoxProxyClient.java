package moxproxy.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import moxproxy.configuration.IMoxProxyClientConfiguration;
import moxproxy.consts.MoxProxyConts;
import moxproxy.consts.MoxProxyRoutes;
import moxproxy.dto.MoxProxyProcessedTrafficEntry;
import moxproxy.dto.MoxProxyRule;
import moxproxy.dto.MoxProxyStatusMessage;
import moxproxy.dto.MoxProxyStatusResponse;
import moxproxy.exceptions.MoxProxyClientException;
import moxproxy.interfaces.IMoxProxyService;
import org.apache.commons.codec.binary.Base64;
import org.apache.cxf.jaxrs.client.WebClient;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class MoxProxyClient implements IMoxProxyService {

    private final IMoxProxyClientConfiguration configuration;
    private final ObjectMapper objectMapper;

    public MoxProxyClient(IMoxProxyClientConfiguration configuration){
        this.configuration = configuration;
        objectMapper = new ObjectMapper();
    }

    @Override
    public Iterable<MoxProxyProcessedTrafficEntry> getSessionRequestTraffic(String sessionId) {
        String route = MoxProxyRoutes.API_ROUTE + MoxProxyRoutes.REQUESTS_ROUTE_SESSION;
        var result = (Iterable<MoxProxyProcessedTrafficEntry>)createClient(route, sessionId).getCollection(MoxProxyProcessedTrafficEntry.class);
        return result;
    }

    @Override
    public Iterable<MoxProxyProcessedTrafficEntry> getAllRequestTraffic() {
        String route = MoxProxyRoutes.API_ROUTE + MoxProxyRoutes.REQUESTS_ROUTE;
        var result = (Iterable<MoxProxyProcessedTrafficEntry>)createClient(route).getCollection(MoxProxyProcessedTrafficEntry.class);
        return result;
    }

    @Override
    public Iterable<MoxProxyProcessedTrafficEntry> getSessionResponseTraffic(String sessionId) {
        String route = MoxProxyRoutes.API_ROUTE + MoxProxyRoutes.RESPONSES_ROUTE_SESSION;
        var result = (Iterable<MoxProxyProcessedTrafficEntry>)createClient(route, sessionId).getCollection(MoxProxyProcessedTrafficEntry.class);
        return result;
    }

    @Override
    public Iterable<MoxProxyProcessedTrafficEntry> getAllResponseTraffic() {
        String route = MoxProxyRoutes.API_ROUTE + MoxProxyRoutes.RESPONSES_ROUTE;
        var result = (Iterable<MoxProxyProcessedTrafficEntry>)createClient(route).getCollection(MoxProxyProcessedTrafficEntry.class);
        return result;
    }

    @Override
    public void cancelRule(String ruleId) {
        String route = MoxProxyRoutes.API_ROUTE + MoxProxyRoutes.RULES_ROUTE_ID;
        Response response = createClient(route, ruleId).delete();
        verifyResponse(response, MoxProxyStatusMessage.DELETED);
    }

    @Override
    public void clearSessionRules(String sessionId) {
        String route = MoxProxyRoutes.API_ROUTE + MoxProxyRoutes.RULES_SESSION_ID_ROUTE;
        Response response = createClient(route, sessionId).delete();
        verifyResponse(response, MoxProxyStatusMessage.DELETED);
    }

    @Override
    public void clearSessionEntries(String sessionId) {
        String route = MoxProxyRoutes.API_ROUTE + MoxProxyRoutes.SESSION_ID_ROUTE;
        Response response = createClient(route, sessionId).delete();
        verifyResponse(response, MoxProxyStatusMessage.DELETED);
    }

    @Override
    public void clearAllSessionEntries() {
        String route = MoxProxyRoutes.API_ROUTE + MoxProxyRoutes.SESSION_ROUTE;
        Response response = createClient(route).delete();
        verifyResponse(response, MoxProxyStatusMessage.DELETED);
    }

    @Override
    public String createRule(MoxProxyRule moxProxyRule) {
        String route = MoxProxyRoutes.API_ROUTE + MoxProxyRoutes.RULES_ROUTE;
        Response response = createClient(route).post(moxProxyRule);
        MoxProxyStatusResponse moxStatus = verifyResponse(response, MoxProxyStatusMessage.CREATED);
        return moxStatus.getEntityId();
    }

    @Override
    public void enableSessionIdMatchingStrategy() {
        String route = MoxProxyRoutes.API_ROUTE + MoxProxyRoutes.SESSION_ROUTE_ENABLE_ID_MATCH;
        Response response = createClient(route).post(MoxProxyConts.EMPTY_STRING);
        verifyResponse(response, MoxProxyStatusMessage.MODIFIED);
    }

    @Override
    public void disableSessionIdMatchingStrategy() {
        String route = MoxProxyRoutes.API_ROUTE + MoxProxyRoutes.SESSION_ROUTE_DISABLE_ID_MATCH;
        Response response = createClient(route).post(MoxProxyConts.EMPTY_STRING);
        verifyResponse(response, MoxProxyStatusMessage.MODIFIED);
    }

    private WebClient createClient(String path, Object... values) {
        List<Object> providers = new ArrayList();
        providers.add(new JacksonJaxbJsonProvider());
        WebClient client = WebClient.create(configuration.getBaseUrl(), providers).type(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON_TYPE).path(path, values);
        client.header(MoxProxyConts.AUTH_HEADER, "Basic " + encodeAuth());
        return client;
    }

    private String encodeAuth(){
        var base64 = new Base64();
        String before = configuration.getUserName()+":"+configuration.getPassword();
        return new String(base64.encode(before.getBytes()));
    }

    private void statusShouldBe(Response response, Response.Status status){
        Response.Status actual = response.getStatusInfo().toEnum();
        if(actual != status){
            throw new MoxProxyClientException("Response status should be " + status +  " ,but was " + actual);
        }
    }

    private void proxyStatusMessageShouldBe(MoxProxyStatusResponse statusResponse, String expectedMessage){
        if(!statusResponse.getMessage().equals(expectedMessage)){
            throw new MoxProxyClientException("MoxProxy response message should be " + expectedMessage + " ,but was " + statusResponse.getMessage());
        }
    }

    private MoxProxyStatusResponse verifyResponse(Response response, String moxProxyStatusMessage){
        statusShouldBe(response, Response.Status.OK);
        MoxProxyStatusResponse moxStatus = response.readEntity(MoxProxyStatusResponse.class);
        proxyStatusMessageShouldBe(moxStatus, moxProxyStatusMessage);
        response.close();
        return moxStatus;
    }
}
