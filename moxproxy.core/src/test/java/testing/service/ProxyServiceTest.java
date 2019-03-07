package testing.service;

import com.google.common.collect.Lists;
import moxproxy.configuration.MoxProxyServiceConfigurationImpl;
import moxproxy.di.DaggerServiceComponent;
import moxproxy.di.ServiceModule;
import moxproxy.interfaces.MoxProxyDatabase;
import moxproxy.interfaces.MoxProxyService;
import moxproxy.model.MoxProxyProcessedTrafficEntry;
import moxproxy.model.MoxProxyRule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import testing.TestBase;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ProxyServiceTest extends TestBase {

    private MoxProxyDatabase database;

    private MoxProxyService service;

    @BeforeEach
    void beforeEachSetup(){
        var component = DaggerServiceComponent.builder().serviceModule(new ServiceModule(new MoxProxyServiceConfigurationImpl())).build();
        database = component.getMoxProxyDatabase();
        service = component.getMoxProxyService();
        database.startDatabase();
    }

    @AfterEach
    void afterEachSetup(){
        database.stopDatabase();
    }

    @Test
    void givenRule_whenCreate_thenRuleCreated(){
        var rule = createDefaultRule();
        service.createRule(rule);
        var actual = database.findRuleByById(rule.getId());
        assertEquals(rule, actual);
    }

    @Test
    void givenRule_whenCancel_thenRuleRemoved(){
        var rule = createDefaultRule();
        service.createRule(rule);
        var actual = database.findRuleByById(rule.getId());
        assertEquals(rule, actual);
        service.cancelRule(rule.getId());
        actual = database.findRuleByById(rule.getId());
        assertNull(actual);
    }

    @Test
    void givenRules_whenClearSessionRules_thenRulesRemoved(){
        var rule1 = createDefaultRule();
        var rule2 = createDefaultRule();
        var rule3 = createDefaultRule();
        rule3.setSessionId(UNKNOWN);
        service.createRule(rule1);
        service.createRule(rule2);
        service.createRule(rule3);
        var actual = Lists.newArrayList(database.getAllRules());
        assertEquals(3, actual.size());
        service.clearSessionRules(rule1.getSessionId());
        actual = Lists.newArrayList(database.getAllRules());
        assertEquals(1, actual.size());
        MoxProxyRule found = actual.stream().findFirst().get();
        assertEquals(rule3.getSessionId(), found.getSessionId());
    }

    @Test
    void givenRulesAndTraffic_whenClearAll_thenAllCleared(){
        var rule1 = createDefaultRule();
        var rule2 = createDefaultRule();
        var traffic1 = createDefaultTrafficEntry();
        var traffic2 = createDefaultTrafficEntry();
        database.addRule(rule1);
        database.addRule(rule2);
        database.addProcessedRequest(traffic1);
        database.addProcessedRequest(traffic2);
        service.clearAllSessionEntries();
        ArrayList<MoxProxyProcessedTrafficEntry> actualTraffic = Lists.newArrayList(database.getProcessedRequestTraffic());
        assertEquals(0, actualTraffic.size());
        ArrayList<MoxProxyRule> actualRules = Lists.newArrayList(database.getAllRules());
        assertEquals(0, actualRules.size());
    }

    @Test
    void givenRulesAndTraffic_whenClearBySession_thenSessionEntriesCleared(){
        String sessionId = "987";

        var rule1 = createDefaultRule();
        rule1.setSessionId(sessionId);
        var rule2 = createDefaultRule();
        rule2.setSessionId(sessionId);
        var rule3 = createDefaultRule();
        rule3.setSessionId(UNKNOWN);
        var traffic1 = createDefaultTrafficEntry();
        traffic1.setSessionId(sessionId);
        var traffic2 = createDefaultTrafficEntry();
        traffic2.setSessionId(sessionId);
        var traffic3 = createDefaultTrafficEntry();
        traffic3.setSessionId(UNKNOWN);
        database.addRule(rule1);
        database.addRule(rule2);
        database.addRule(rule3);
        database.addProcessedRequest(traffic1);
        database.addProcessedRequest(traffic2);
        database.addProcessedRequest(traffic3);
        database.addProcessedResponse(traffic1);
        database.addProcessedResponse(traffic2);
        database.addProcessedResponse(traffic3);
        service.clearSessionEntries(sessionId);
        ArrayList<MoxProxyProcessedTrafficEntry> actualRequestTraffic = Lists.newArrayList(database.getProcessedRequestTraffic(rule1.getSessionId()));
        assertEquals(0, actualRequestTraffic.size());
        ArrayList<MoxProxyProcessedTrafficEntry> actualResponseTraffic = Lists.newArrayList(database.getProcessedResponseTraffic(rule1.getSessionId()));
        assertEquals(0, actualResponseTraffic.size());
        ArrayList<MoxProxyRule> actualRules = Lists.newArrayList(database.findRulesBySessionId(rule1.getSessionId()));
        assertEquals(0, actualRules.size());

        actualRequestTraffic = Lists.newArrayList(database.getProcessedRequestTraffic());
        actualResponseTraffic = Lists.newArrayList(database.getProcessedResponseTraffic());
        actualRules = Lists.newArrayList(database.getAllRules());

        assertEquals(1, actualRequestTraffic.size());
        assertEquals(1, actualResponseTraffic.size());
        assertEquals(1, actualRules.size());
    }

    @Test
    void givenRequestTraffic_whenGetAllRequestTraffic_thenAllTrafficReturned(){
        var traffic1 = createDefaultTrafficEntry();
        var traffic2 = createDefaultTrafficEntry();
        var traffic3 = createDefaultTrafficEntry();
        traffic3.setSessionId(UNKNOWN);
        database.addProcessedRequest(traffic1);
        database.addProcessedRequest(traffic2);
        database.addProcessedRequest(traffic3);

        ArrayList<MoxProxyProcessedTrafficEntry> traffic = Lists.newArrayList(service.getAllRequestTraffic());
        assertEquals(3, traffic.size());
    }

    @Test
    void givenResponseTraffic_whenGetAllResponseTraffic_thenAllTrafficReturned(){
        var traffic1 = createDefaultTrafficEntry();
        var traffic2 = createDefaultTrafficEntry();
        var traffic3 = createDefaultTrafficEntry();
        traffic3.setSessionId(UNKNOWN);
        database.addProcessedResponse(traffic1);
        database.addProcessedResponse(traffic2);
        database.addProcessedResponse(traffic3);

        ArrayList<MoxProxyProcessedTrafficEntry> traffic = Lists.newArrayList(service.getAllResponseTraffic());
        assertEquals(3, traffic.size());
    }

    @Test
    void givenRequestTraffic_whenGetSessionRequestTraffic_thenSessionTrafficReturned(){
        var traffic1 = createDefaultTrafficEntry();
        var traffic2 = createDefaultTrafficEntry();
        var traffic3 = createDefaultTrafficEntry();
        traffic3.setSessionId(UNKNOWN);
        database.addProcessedRequest(traffic1);
        database.addProcessedRequest(traffic2);
        database.addProcessedRequest(traffic3);

        ArrayList<MoxProxyProcessedTrafficEntry> traffic = Lists.newArrayList(service.getSessionRequestTraffic(traffic1.getSessionId()));
        assertEquals(2, traffic.size());
    }

    @Test
    void givenResponseTraffic_whenGetSessionResponseTraffic_thenSessionTrafficReturned(){
        var traffic1 = createDefaultTrafficEntry();
        var traffic2 = createDefaultTrafficEntry();
        var traffic3 = createDefaultTrafficEntry();
        traffic3.setSessionId(UNKNOWN);
        database.addProcessedResponse(traffic1);
        database.addProcessedResponse(traffic2);
        database.addProcessedResponse(traffic3);

        ArrayList<MoxProxyProcessedTrafficEntry> traffic = Lists.newArrayList(service.getSessionResponseTraffic(traffic1.getSessionId()));
        assertEquals(2, traffic.size());
    }
}
