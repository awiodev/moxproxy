package moxproxy.client;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import moxproxy.configuration.IMoxProxyClientConfiguration;
import moxproxy.consts.MoxProxyRoutes;
import moxproxy.dto.MoxProxyProcessedTrafficEntry;
import moxproxy.dto.MoxProxyRule;
import moxproxy.interfaces.IMoxProxyService;
import org.apache.cxf.jaxrs.client.WebClient;

import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

public class MoxProxyClient implements IMoxProxyService {

    private final WebClient client;

    public MoxProxyClient(IMoxProxyClientConfiguration configuration){
        List<Object> providers = new ArrayList();
        providers.add(new JacksonJaxbJsonProvider());

        client = WebClient.create(configuration.getBaseUrl(), providers).type(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON_TYPE);
    }

    @Override
    public Iterable<MoxProxyProcessedTrafficEntry> getSessionRequestTraffic(String sessionId) {
        String route = MoxProxyRoutes.API_ROUTE + MoxProxyRoutes.REQUESTS_ROUTE_SESSION;
        var result = (Iterable<MoxProxyProcessedTrafficEntry>)client.path(route, sessionId).getCollection(MoxProxyProcessedTrafficEntry.class);
        return result;
    }

    @Override
    public Iterable<MoxProxyProcessedTrafficEntry> getAllRequestTraffic() {
        String route = MoxProxyRoutes.API_ROUTE + MoxProxyRoutes.REQUESTS_ROUTE;
        var result = (Iterable<MoxProxyProcessedTrafficEntry>)client.path(route).getCollection(MoxProxyProcessedTrafficEntry.class);
        return result;
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
        //TODO Rule creation
        return moxProxyRule.getId();
    }

    @Override
    public void enableSessionIdMatchingStrategy() {

    }

    @Override
    public void disableSessionIdMatchingStrategy() {

    }
}
