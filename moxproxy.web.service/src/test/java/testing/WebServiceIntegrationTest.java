package testing;

import client.WebServiceIntegrationTestClient;
import moxproxy.dto.*;
import moxproxy.enums.MoxProxyAction;
import moxproxy.enums.MoxProxyDirection;
import moxproxy.interfaces.IMoxProxyDatabase;
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
public class WebServiceIntegrationTest {

    @Autowired
    private WebServiceIntegrationTestClient client;

    @Autowired
    private IMoxProxyDatabase database;

    private static String defaultId = "123";
    private static String unknown = "unknown";
    private static int defaultStatusCode = 200;
    private static String defaultBody = "{\"hello\":\"world\"}";
    private static String defaultMethod = "Get";
    private static String defaultUrl= "https://hello.world.com/api/rest";

    private static String defaultPathPattern= "hello\\.world";

    @AfterEach
    private void cleanup(){
        database.cleanAllRules();
        database.cleanAllProcessedTraffic();
    }

    @Test
    public void givenRequestTraffic_whenGetAll_ThenTrafficReturned(){
        var traffic1 = createDefaultFullyFilledTrafficEntity();
        var traffic2 = createDefaultFullyFilledTrafficEntity();
        var traffic3 = createDefaultFullyFilledTrafficEntity();
        traffic3.setSessionId(unknown);

        database.addProcessedRequest(traffic1);
        database.addProcessedRequest(traffic2);
        database.addProcessedRequest(traffic3);

        var results = Lists.newArrayList(client.getAllRequestTraffic());

        assertEquals(results.size(), 3, "Number of all returned requests should be correct");
    }

    @Test
    public void givenResponsesTraffic_whenGetAll_ThenTrafficReturned(){
        var traffic1 = createDefaultFullyFilledTrafficEntity();
        var traffic2 = createDefaultFullyFilledTrafficEntity();
        var traffic3 = createDefaultFullyFilledTrafficEntity();
        traffic3.setSessionId(unknown);

        database.addProcessedResponse(traffic1);
        database.addProcessedResponse(traffic2);
        database.addProcessedResponse(traffic3);

        var results = Lists.newArrayList(client.getAllResponseTraffic());

        assertEquals(results.size(), 3, "Number of all returned responses should be correct");
    }

    @Test
    public void givenRequestTraffic_whenBySessionId_ThenTrafficReturned(){
        var traffic1 = createDefaultFullyFilledTrafficEntity();
        var traffic2 = createDefaultFullyFilledTrafficEntity();
        var traffic3 = createDefaultFullyFilledTrafficEntity();
        traffic3.setSessionId(unknown);

        database.addProcessedRequest(traffic1);
        database.addProcessedRequest(traffic2);
        database.addProcessedRequest(traffic3);

        var results = Lists.newArrayList(client.getSessionRequestTraffic(unknown));

        assertEquals(results.size(), 1, "Number of returned requests should be correct");
        assertThat(results.get(0)).isEqualToComparingFieldByFieldRecursively(traffic3);
    }

    @Test
    public void givenResponseTraffic_whenBySessionId_ThenTrafficReturned(){
        var traffic1 = createDefaultFullyFilledTrafficEntity();
        var traffic2 = createDefaultFullyFilledTrafficEntity();
        var traffic3 = createDefaultFullyFilledTrafficEntity();
        traffic3.setSessionId(unknown);

        database.addProcessedResponse(traffic1);
        database.addProcessedResponse(traffic2);
        database.addProcessedResponse(traffic3);

        var results = Lists.newArrayList(client.getSessionResponseTraffic(unknown));

        assertEquals(results.size(), 1, "Number of returned responses should be correct");
        assertThat(results.get(0)).isEqualToComparingFieldByFieldRecursively(traffic3);
    }

    @Test
    public void givenRule_whenCreate_thenRuleCreated(){
        var rule = createDefaultFullyFilledRule();

        client.createRule(rule);

        MoxProxyRule created = Lists.newArrayList(database.getAllRules()).get(0);
        assertThat(created).isEqualToComparingFieldByFieldRecursively(rule);
    }

    @Test
    public void givenTrafficAndRules_whenClearAll_thenAllCleared() throws Exception {

        database.addProcessedRequest(createDefaultFullyFilledTrafficEntity());
        database.addProcessedRequest(createDefaultFullyFilledTrafficEntity());
        database.addProcessedResponse(createDefaultFullyFilledTrafficEntity());
        database.addProcessedResponse(createDefaultFullyFilledTrafficEntity());
        database.addRule(createDefaultFullyFilledRule());
        database.addRule(createDefaultFullyFilledRule());

        client.clearAllSessionEntries();

        var requests = Lists.newArrayList(database.getProcessedRequestTraffic());
        var responses = Lists.newArrayList(database.getProcessedResponseTraffic());
        var rules = Lists.newArrayList(database.getAllRules());

        assertEquals(0, requests.size());
        assertEquals(0, responses.size());
        assertEquals(0, rules.size());
    }

    @Test
    public void givenTrafficAnRules_whenClearBySession_thenSessionEntriesCleared(){
        database.addProcessedRequest(createDefaultFullyFilledTrafficEntity());
        var request = createDefaultFullyFilledTrafficEntity();
        request.setSessionId(unknown);
        database.addProcessedRequest(request);
        database.addProcessedResponse(createDefaultFullyFilledTrafficEntity());
        var response = createDefaultFullyFilledTrafficEntity();
        response.setSessionId(unknown);
        database.addProcessedResponse(response);
        database.addRule(createDefaultFullyFilledRule());
        var rule = createDefaultFullyFilledRule();
        rule.setSessionId(unknown);
        database.addRule(rule);

        client.clearSessionEntries(defaultId);

        var requests = Lists.newArrayList(database.getProcessedRequestTraffic());
        var responses = Lists.newArrayList(database.getProcessedResponseTraffic());
        var rules = Lists.newArrayList(database.getAllRules());

        assertEquals(1, requests.size());
        assertEquals(1, responses.size());
        assertEquals(1, rules.size());

        assertThat(requests.get(0)).isEqualToComparingFieldByFieldRecursively(request);
        assertThat(responses.get(0)).isEqualToComparingFieldByFieldRecursively(response);
        assertThat(rules.get(0)).isEqualToComparingFieldByFieldRecursively(rule);
    }

    @Test
    public void givenRule_whenCancel_thenRuleRemoved(){
        var rule1 = createDefaultFullyFilledRule();
        var rule2 = createDefaultFullyFilledRule();
        rule2.setSessionId(unknown);

        database.addRule(rule1);
        database.addRule(rule2);

        client.cancelRule(rule1.getId());

        var rulesList = Lists.newArrayList(database.getAllRules());
        assertEquals(1, rulesList.size(), "Number of rules should be correct");
        assertThat(rulesList.get(0)).isEqualToComparingFieldByFieldRecursively(rule2);
    }

    @Test
    public void givenRules_whenClearBySessionId_thenRulesRemoved(){
        var rule1 = createDefaultFullyFilledRule();
        var rule2 = createDefaultFullyFilledRule();
        var rule3 = createDefaultFullyFilledRule();
        rule3.setSessionId(unknown);

        database.addRule(rule1);
        database.addRule(rule2);
        database.addRule(rule3);

        client.clearSessionRules(rule1.getSessionId());

        var rulesList = Lists.newArrayList(database.getAllRules());
        assertEquals(1, rulesList.size(), "Number of rules should be correct");
        assertThat(rulesList.get(0)).isEqualToComparingFieldByFieldRecursively(rule3);
    }

    @Test
    public void givenSessionIdStrategy_whenTurnOn_thenStrategyModified(){

        var strategy = new MoxProxySessionIdMatchingStrategy();
        strategy.setIncludeSessionIdMatch(true);
        client.modifySessionMatchingStrategy(strategy);

        var actual =  client.getSessionMatchingStrategy();

        assertThat(actual).isEqualToComparingFieldByField(strategy);
    }



    private MoxProxyProcessedTrafficEntry createDefaultFullyFilledTrafficEntity(){
        var entity = new MoxProxyProcessedTrafficEntry();
        entity.setSessionId(defaultId);
        entity.setStatusCode(defaultStatusCode);
        entity.setBody(defaultBody);
        entity.setHeaders(defaultHeaders());
        entity.setMethod(defaultMethod);
        entity.setUrl(defaultUrl);
        return entity;
    }

    private MoxProxyRule createDefaultFullyFilledRule(){
        var rule = new MoxProxyRule();
        rule.setSessionId(defaultId);
        rule.setHttpDirection(MoxProxyDirection.REQUEST);
        rule.setAction(MoxProxyAction.RESPOND);

        var definition = new MoxProxyHttpRuleDefinition();
        definition.setBody(defaultBody);
        definition.setPathPattern(defaultPathPattern);
        definition.setHeaders(defaultHeaders());
        definition.setMethod(defaultMethod);
        definition.setStatusCode(defaultStatusCode);

        rule.setMoxProxyHttpObject(definition);
        return rule;
    }

    private List<MoxProxyHeader> defaultHeaders(){
        var headers = new ArrayList<MoxProxyHeader>();
        var header1 = new MoxProxyHeader();
        header1.setName("first-header");
        header1.setValue("firstValue");
        var header2 = new MoxProxyHeader();
        header2.setName("second header");
        header2.setValue("second value");

        headers.add(header1);
        headers.add(header2);

        return headers;
    }
}
