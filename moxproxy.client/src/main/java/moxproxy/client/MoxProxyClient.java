package moxproxy.client;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import moxproxy.configuration.IMoxProxyClientConfiguration;
import moxproxy.consts.MoxProxyConts;
import moxproxy.consts.MoxProxyRoutes;
import moxproxy.dto.MoxProxyProcessedTrafficEntry;
import moxproxy.dto.MoxProxyRule;
import moxproxy.interfaces.IMoxProxyService;
import org.apache.cxf.jaxrs.client.WebClient;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class MoxProxyClient implements IMoxProxyService {

    private final IMoxProxyClientConfiguration configuration;

    public MoxProxyClient(IMoxProxyClientConfiguration configuration){
        this.configuration = configuration;
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
        createClient(route, ruleId).delete();
    }

    @Override
    public void clearSessionRules(String sessionId) {
        String route = MoxProxyRoutes.API_ROUTE + MoxProxyRoutes.RULES_SESSION_ID_ROUTE;
        createClient(route, sessionId).delete();
    }

    @Override
    public void clearSessionEntries(String sessionId) {
        String route = MoxProxyRoutes.API_ROUTE + MoxProxyRoutes.SESSION_ID_ROUTE;
        createClient(route, sessionId).delete();
    }

    @Override
    public void clearAllSessionEntries() {
        String route = MoxProxyRoutes.API_ROUTE + MoxProxyRoutes.SESSION_ROUTE;
        createClient(route).delete();
    }

    @Override
    public String createRule(MoxProxyRule moxProxyRule) {
        String route = MoxProxyRoutes.API_ROUTE + MoxProxyRoutes.RULES_ROUTE;
        Response response = createClient(route).post(moxProxyRule);
        return moxProxyRule.getId();
    }

    @Override
    public void enableSessionIdMatchingStrategy() {
        String route = MoxProxyRoutes.API_ROUTE + MoxProxyRoutes.SESSION_ROUTE_ENABLE_ID_MATCH;
        var response = createClient(route).post(MoxProxyConts.EMPTY_STRING);
    }

    @Override
    public void disableSessionIdMatchingStrategy() {
        String route = MoxProxyRoutes.API_ROUTE + MoxProxyRoutes.SESSION_ROUTE_DISABLE_ID_MATCH;
        var response = createClient(route).post(MoxProxyConts.EMPTY_STRING);
    }

    private WebClient createClient(String path, Object... values){
        List<Object> providers = new ArrayList();
        providers.add(new JacksonJaxbJsonProvider());
        return WebClient.create(configuration.getBaseUrl(), providers).type(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON_TYPE).path(path, values);
    }
}
