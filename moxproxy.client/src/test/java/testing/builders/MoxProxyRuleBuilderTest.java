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

    private static final String DEFAULT_SESSION_ID = "1234";

    @Test
    void givenChildBuilder_whenBackToParent_thenParentReturned(){

        var builder = new MoxProxyRuleBuilder();
        builder.withDirection(MoxProxyDirection.REQUEST)
                .withSessionId(DEFAULT_SESSION_ID);

        MoxProxyHttpObjectBuilder childBuilder = builder.withHttpObject();
        MoxProxyRuleBuilder actual = childBuilder.backToParent();

        Assertions.assertEquals(builder, actual);
    }

    @Test
    void givenBuilder_whenBuild_thenAllBuilt(){
        String method = "POST";
        String path = "test/path";
        String body = "{something:\"some value\"}";
        String headerName = "firstHeader";
        String headerValue = "firstHeaderValue";
        int statusCode = 500;
        var builder = new MoxProxyRuleBuilder();
        MoxProxyRule actual = builder
                .withDirection(MoxProxyDirection.REQUEST)
                .withSessionId(DEFAULT_SESSION_ID)
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

        Assertions.assertEquals(DEFAULT_SESSION_ID, actual.getSessionId());
        Assertions.assertEquals(MoxProxyDirection.REQUEST, actual.getHttpDirection());
        Assertions.assertEquals(method, actual.getMoxProxyHttpObject().getMethod());
        Assertions.assertEquals(path, actual.getMoxProxyHttpObject().getPath());
        Assertions.assertEquals(statusCode, actual.getMoxProxyHttpObject().getStatusCode());
        Assertions.assertEquals(body, actual.getMoxProxyHttpObject().getBody());
        Assertions.assertEquals(headerName, actual.getMoxProxyHttpObject().getHeaders().iterator().next().getName());
        Assertions.assertEquals(headerValue, actual.getMoxProxyHttpObject().getHeaders().iterator().next().getValue());
    }
}
