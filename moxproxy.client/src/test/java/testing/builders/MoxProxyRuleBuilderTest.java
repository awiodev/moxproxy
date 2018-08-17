package testing.builders;

import moxproxy.builders.MoxProxyHttpObjectBuilder;
import moxproxy.builders.MoxProxyRuleBuilder;
import moxproxy.enums.MoxProxyAction;
import moxproxy.enums.MoxProxyDirection;
import moxproxy.dto.MoxProxyRule;
import moxproxy.exceptions.BuilderValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
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
    void givenBuilder_whenBuild_thenAllBuilt() throws BuilderValidationException {
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
                .withAction(MoxProxyAction.RESPOND)
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

        Assertions.assertNotNull(actual.getId());
        Assertions.assertNotNull(actual.getDate());
        Assertions.assertEquals(DEFAULT_SESSION_ID, actual.getSessionId());
        Assertions.assertEquals(MoxProxyDirection.REQUEST, actual.getHttpDirection());
        Assertions.assertEquals(MoxProxyAction.RESPOND, actual.getAction());
        Assertions.assertEquals(method, actual.getMoxProxyHttpObject().getMethod());
        Assertions.assertEquals(path, actual.getMoxProxyHttpObject().getPath());
        Assertions.assertEquals(statusCode, actual.getMoxProxyHttpObject().getStatusCode());
        Assertions.assertEquals(body, actual.getMoxProxyHttpObject().getBody());
        Assertions.assertEquals(headerName, actual.getMoxProxyHttpObject().getHeaders().iterator().next().getName());
        Assertions.assertEquals(headerValue, actual.getMoxProxyHttpObject().getHeaders().iterator().next().getValue());
    }

    @DisplayName("Should throw validation exception")
    @ParameterizedTest(name = "{1}")
    @ArgumentsSource(InvalidBuildersProvider.class)
    void givenBuilder_WhenBuild_thenValidationException(MoxProxyRuleBuilder builder, String reason){
        Assertions.assertThrows(BuilderValidationException.class, builder::build);
    }
}
