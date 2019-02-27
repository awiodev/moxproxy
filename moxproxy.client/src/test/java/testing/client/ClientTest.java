package testing.client;

import com.google.common.collect.Lists;
import moxproxy.client.MoxProxyClient;
import moxproxy.configuration.MoxProxyClientConfigurationImpl;
import moxproxy.enums.MoxProxyAction;
import moxproxy.enums.MoxProxyDirection;
import moxproxy.model.MoxProxyProcessedTrafficEntry;
import moxproxy.model.MoxProxyRule;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class ClientTest {


    //@Test
    public void testClient(){
        var config = new MoxProxyClientConfigurationImpl();
        var client = new MoxProxyClient(config);

        Iterable<MoxProxyProcessedTrafficEntry> traffic =  client.getAllRequestTraffic();
        var list = Lists.newArrayList(traffic);

        String body = "[\"proxy\",[\"Only MoxProxy!\"],[\"https://moxproxy.com\"]]";
        int contentLen = body.length();

        int statusCode = 200;
        MoxProxyRule actual = MoxProxyRule.builder()
                .withDirection(MoxProxyDirection.REQUEST)
                .withSessionId("qw")
                .withAction(MoxProxyAction.RESPOND)
                /*.withMatchingStrategy()
                    .useMethod()
                    .backToParent()*/
                .withHttpRuleDefinition()
                .withMethod("Get")
                .withPathPattern("search=proxy")
                .withStatusCode(statusCode)
                .withBody(body)
                .havingHeaders()
/*                .addItem()
                    .withHeader("Access-Control-Allow-Credentials", "true")
                .addItem()
                    .withHeader("Access-Control-Allow-Origin", "https://library.test.abb.com")*/
                    .withHeader("Content-Type", "application/json; charset=utf-8")
                    .withHeader("Content-Length", contentLen)
                .backToParent()
                .backToParent()
                .build();

        String ruleid = client.createRule(actual);

        //client.cancelRule(actual.getId());
    }

}
