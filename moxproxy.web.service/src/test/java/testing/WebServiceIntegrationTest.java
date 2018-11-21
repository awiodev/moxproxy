package testing;

import client.WebServiceIntegrationTestClient;
import moxproxy.dto.MoxProxyHeader;
import moxproxy.dto.MoxProxyHttpRuleDefinition;
import moxproxy.dto.MoxProxyProcessedTrafficEntry;
import moxproxy.dto.MoxProxyRule;
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
    }

    @Test
    public void givenRule_whenCreate_thenRuleCreated(){
        var rule = createDefaultFullyFilledRule();

        client.createRule(rule);

        MoxProxyRule created = Lists.newArrayList(database.getAllRules()).get(0);
        assertThat(created).isEqualToComparingFieldByFieldRecursively(rule);
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
