package testing;

import client.WebServiceIntegrationTestClient;
import moxproxy.enums.MoxProxyAction;
import moxproxy.enums.MoxProxyDirection;
import moxproxy.interfaces.MoxProxyDatabase;
import moxproxy.model.*;
import moxproxy.webservice.MoxProxyWebService;
import moxproxy.webservice.config.WebServiceBeanConfiguration;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MoxProxyWebService.class)
@AutoConfigureMockMvc
@Import({WebServiceIntegrationTestConfiguration.class, WebServiceBeanConfiguration.class})
class WebServiceIntegrationTest {

    @Autowired
    private WebServiceIntegrationTestClient client;

    @Autowired
    private MoxProxyDatabase database;

    private static String defaultId = "123";
    private static String unknown = "unknown";
    private static int defaultStatusCode = 200;
    private static String defaultBody = "{\"hello\":\"world\"}";
    private static String defaultMethod = "Get";

    @AfterEach
    private void cleanup(){
        database.cleanAllRules();
        database.cleanAllProcessedTraffic();
    }

    @Test
    void givenRequestTraffic_whenGetAll_ThenTrafficReturned(){
        MoxProxyProcessedTrafficEntry traffic1 = createDefaultFullyFilledTrafficEntity();
        MoxProxyProcessedTrafficEntry traffic2 = createDefaultFullyFilledTrafficEntity();
        MoxProxyProcessedTrafficEntry traffic3 = createDefaultFullyFilledTrafficEntity(unknown);

        database.addProcessedRequest(traffic1);
        database.addProcessedRequest(traffic2);
        database.addProcessedRequest(traffic3);

        ArrayList<MoxProxyProcessedTrafficEntry> results = Lists.newArrayList(client.getAllRequestTraffic());

        assertEquals(results.size(), 3, "Number of all returned requests should be correct");
    }

    @Test
    void givenResponsesTraffic_whenGetAll_ThenTrafficReturned(){
        MoxProxyProcessedTrafficEntry traffic1 = createDefaultFullyFilledTrafficEntity();
        MoxProxyProcessedTrafficEntry traffic2 = createDefaultFullyFilledTrafficEntity();
        MoxProxyProcessedTrafficEntry traffic3 = createDefaultFullyFilledTrafficEntity(unknown);

        database.addProcessedResponse(traffic1);
        database.addProcessedResponse(traffic2);
        database.addProcessedResponse(traffic3);

        ArrayList<MoxProxyProcessedTrafficEntry> results = Lists.newArrayList(client.getAllResponseTraffic());

        assertEquals(results.size(), 3, "Number of all returned responses should be correct");
    }

    @Test
    void givenRequestTraffic_whenBySessionId_ThenTrafficReturned(){
        MoxProxyProcessedTrafficEntry traffic1 = createDefaultFullyFilledTrafficEntity();
        MoxProxyProcessedTrafficEntry traffic2 = createDefaultFullyFilledTrafficEntity();
        MoxProxyProcessedTrafficEntry traffic3 = createDefaultFullyFilledTrafficEntity(unknown);

        database.addProcessedRequest(traffic1);
        database.addProcessedRequest(traffic2);
        database.addProcessedRequest(traffic3);

        ArrayList<MoxProxyProcessedTrafficEntry> results = Lists.newArrayList(client.getSessionRequestTraffic(unknown));

        assertEquals(results.size(), 1, "Number of returned requests should be correct");
        assertThat(results.get(0)).isEqualToComparingFieldByFieldRecursively(traffic3);
    }

    @Test
    void givenResponseTraffic_whenBySessionId_ThenTrafficReturned(){
        MoxProxyProcessedTrafficEntry traffic1 = createDefaultFullyFilledTrafficEntity();
        MoxProxyProcessedTrafficEntry traffic2 = createDefaultFullyFilledTrafficEntity();
        MoxProxyProcessedTrafficEntry traffic3 = createDefaultFullyFilledTrafficEntity(unknown);

        database.addProcessedResponse(traffic1);
        database.addProcessedResponse(traffic2);
        database.addProcessedResponse(traffic3);

        ArrayList<MoxProxyProcessedTrafficEntry> results = Lists.newArrayList(client.getSessionResponseTraffic(unknown));

        assertEquals(results.size(), 1, "Number of returned responses should be correct");
        assertThat(results.get(0)).isEqualToComparingFieldByFieldRecursively(traffic3);
    }

    @Test
    void givenRule_whenCreate_thenRuleCreated(){
        MoxProxyRule rule = createDefaultFullyFilledRule();

        client.createRule(rule);

        MoxProxyRule created = Lists.newArrayList(database.getAllRules()).get(0);
        assertThat(created).isEqualToComparingFieldByFieldRecursively(rule);
    }

    @Test
    void givenTrafficAndRules_whenClearAll_thenAllCleared() {

        database.addProcessedRequest(createDefaultFullyFilledTrafficEntity());
        database.addProcessedRequest(createDefaultFullyFilledTrafficEntity());
        database.addProcessedResponse(createDefaultFullyFilledTrafficEntity());
        database.addProcessedResponse(createDefaultFullyFilledTrafficEntity());
        database.addRule(createDefaultFullyFilledRule());
        database.addRule(createDefaultFullyFilledRule());

        client.clearAllSessionEntries();

        ArrayList<MoxProxyProcessedTrafficEntry> requests = Lists.newArrayList(database.getProcessedRequestTraffic());
        ArrayList<MoxProxyProcessedTrafficEntry> responses = Lists.newArrayList(database.getProcessedResponseTraffic());
        ArrayList<MoxProxyRule> rules = Lists.newArrayList(database.getAllRules());

        assertEquals(0, requests.size());
        assertEquals(0, responses.size());
        assertEquals(0, rules.size());
    }

    @Test
    void givenTrafficAnRules_whenClearBySession_thenSessionEntriesCleared(){
        database.addProcessedRequest(createDefaultFullyFilledTrafficEntity());
        MoxProxyProcessedTrafficEntry request = createDefaultFullyFilledTrafficEntity(unknown);
        database.addProcessedRequest(request);
        database.addProcessedResponse(createDefaultFullyFilledTrafficEntity());
        MoxProxyProcessedTrafficEntry response = createDefaultFullyFilledTrafficEntity(unknown);
        database.addProcessedResponse(response);
        database.addRule(createDefaultFullyFilledRule());
        MoxProxyRule rule = createDefaultFullyFilledRule();
        rule.setSessionId(unknown);
        database.addRule(rule);

        client.clearSessionEntries(defaultId);

        ArrayList<MoxProxyProcessedTrafficEntry> requests = Lists.newArrayList(database.getProcessedRequestTraffic());
        ArrayList<MoxProxyProcessedTrafficEntry> responses = Lists.newArrayList(database.getProcessedResponseTraffic());
        ArrayList<MoxProxyRule> rules = Lists.newArrayList(database.getAllRules());

        assertEquals(1, requests.size());
        assertEquals(1, responses.size());
        assertEquals(1, rules.size());

        assertThat(requests.get(0)).isEqualToComparingFieldByFieldRecursively(request);
        assertThat(responses.get(0)).isEqualToComparingFieldByFieldRecursively(response);
        assertThat(rules.get(0)).isEqualToComparingFieldByFieldRecursively(rule);
    }

    @Test
    void givenRule_whenCancel_thenRuleRemoved(){
        MoxProxyRule rule1 = createDefaultFullyFilledRule();
        MoxProxyRule rule2 = createDefaultFullyFilledRule();
        rule2.setSessionId(unknown);

        database.addRule(rule1);
        database.addRule(rule2);

        client.cancelRule(rule1.getId());

        ArrayList<MoxProxyRule> rulesList = Lists.newArrayList(database.getAllRules());
        assertEquals(1, rulesList.size(), "Number of rules should be correct");
        assertThat(rulesList.get(0)).isEqualToComparingFieldByFieldRecursively(rule2);
    }

    @Test
    void givenRules_whenClearBySessionId_thenRulesRemoved(){
        MoxProxyRule rule1 = createDefaultFullyFilledRule();
        MoxProxyRule rule2 = createDefaultFullyFilledRule();
        MoxProxyRule rule3 = createDefaultFullyFilledRule();
        rule3.setSessionId(unknown);

        database.addRule(rule1);
        database.addRule(rule2);
        database.addRule(rule3);

        client.clearSessionRules(rule1.getSessionId());

        ArrayList<MoxProxyRule> rulesList = Lists.newArrayList(database.getAllRules());
        assertEquals(1, rulesList.size(), "Number of rules should be correct");
        assertThat(rulesList.get(0)).isEqualToComparingFieldByFieldRecursively(rule3);
    }

    @Test
    void givenSessionIdStrategy_whenTurnOn_thenStrategyModified(){

        MoxProxySessionIdMatchingStrategy strategy = new MoxProxySessionIdMatchingStrategy();
        strategy.setIncludeSessionIdMatch(true);
        client.modifySessionMatchingStrategy(strategy);

        MoxProxySessionIdMatchingStrategy actual =  client.getSessionMatchingStrategy();

        assertThat(actual).isEqualToComparingFieldByField(strategy);
    }


    private MoxProxyProcessedTrafficEntry createDefaultFullyFilledTrafficEntity(String sessionId){
        String defaultUrl = "https://hello.world.com/api/rest";
        return new MoxProxyProcessedTrafficEntry(sessionId, defaultMethod, defaultUrl, defaultBody, defaultHeaders(), defaultStatusCode);
    }

    private MoxProxyProcessedTrafficEntry createDefaultFullyFilledTrafficEntity(){
        return createDefaultFullyFilledTrafficEntity(defaultId);
    }

    private MoxProxyRule createDefaultFullyFilledRule(){
        MoxProxyRule rule = new MoxProxyRule();
        rule.setSessionId(defaultId);
        rule.setHttpDirection(MoxProxyDirection.REQUEST);
        rule.setAction(MoxProxyAction.RESPOND);

        String defaultPathPattern = "hello\\.world";
        MoxProxyHttpRuleDefinition definition = new MoxProxyHttpRuleDefinition(defaultMethod, defaultPathPattern, defaultBody, defaultHeaders(), defaultStatusCode);

        rule.setMoxProxyHttpObject(definition);
        return rule;
    }

    private List<MoxProxyHeader> defaultHeaders(){
        ArrayList<MoxProxyHeader> headers = new ArrayList<>();
        MoxProxyHeader header1 = new MoxProxyHeader("first-header", "firstValue");
        MoxProxyHeader header2 = new MoxProxyHeader("second header", "second value");

        headers.add(header1);
        headers.add(header2);

        return headers;
    }
}
