package testing.builders;

import moxproxy.builders.MoxProxyHttpObjectBuilder;
import moxproxy.builders.MoxProxyRuleBuilder;
import moxproxy.enums.MoxProxyDirection;
import moxproxy.dto.MoxProxyRule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class MoxProxyRuleBuilderTest {

    @Test
    void givenChildBuilder_whenBackToParent_thenParentReturned(){
        String sessionId = "1234";

        var builder = new MoxProxyRuleBuilder();
        builder.withDirection(MoxProxyDirection.REQUEST)
                .withSessionId(sessionId);

        MoxProxyHttpObjectBuilder childBuilder = builder.withHttpObject();
        MoxProxyRuleBuilder actual = childBuilder.backToParent();

        Assertions.assertEquals(builder, actual);
    }

    @Test
    void givenBuilder_whenBuild_thenAllBuilt(){
        String sessionId = "1234";
        String method = "POST";
        String path = "test/path";
        String body = "{something:\"some value\"}";
        String headerName = "firstHeader";
        String headerValue = "firstHeaderValue";
        int statusCode = 500;
        var builder = new MoxProxyRuleBuilder();
        MoxProxyRule actual = builder
                .withDirection(MoxProxyDirection.REQUEST)
                .withSessionId(sessionId)
                .withHttpObject()
                    .withMethod(method)
                    .withPath(path)
                    .withStatusCode(statusCode)
                    .withBody(body)
                    .havingHeaders()
                        .addChildItem()
                            .withName(headerName)
                            .withValue(headerValue)
                            .backToParent()
                        .backToParent()
                    .backToParent()
                .build();

        Assertions.assertEquals(sessionId, actual.getSessionId());
        Assertions.assertEquals(MoxProxyDirection.REQUEST, actual.getHttpDirection());
        Assertions.assertEquals(method, actual.getMoxProxyHttpObject().getMethod());
        Assertions.assertEquals(path, actual.getMoxProxyHttpObject().getPath());
        Assertions.assertEquals(statusCode, actual.getMoxProxyHttpObject().getStatusCode());
        Assertions.assertEquals(body, actual.getMoxProxyHttpObject().getBody());
        Assertions.assertEquals(headerName, actual.getMoxProxyHttpObject().getHeaders().iterator().next().getName());
        Assertions.assertEquals(headerValue, actual.getMoxProxyHttpObject().getHeaders().iterator().next().getValue());
    }
}
