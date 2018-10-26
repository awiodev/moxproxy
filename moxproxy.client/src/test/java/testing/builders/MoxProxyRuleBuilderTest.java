package testing.builders;

import moxproxy.builders.MoxProxyHttpRuleDefinitionBuilder;
import moxproxy.builders.MoxProxyRuleBuilder;
import moxproxy.dto.MoxProxyRule;
import moxproxy.enums.MoxProxyAction;
import moxproxy.enums.MoxProxyDirection;
import moxproxy.exceptions.BuilderValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.matchesPattern;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class MoxProxyRuleBuilderTest {

    private static final String DEFAULT_SESSION_ID = "1234";

    @Test
    void givenChildBuilder_whenBackToParent_thenParentReturned(){

        var builder = new MoxProxyRuleBuilder();
        builder.withDirection(MoxProxyDirection.REQUEST)
                .withSessionId(DEFAULT_SESSION_ID);

        MoxProxyHttpRuleDefinitionBuilder childBuilder = builder.withHttpObjectDefinition();
        MoxProxyRuleBuilder actual = childBuilder.backToParent();

        assertEquals(builder, actual);
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
            /*.withMatchingStrategy()
                .useMethod()
                .backToParent()*/
            .withHttpObjectDefinition()
                .withMethod(method)
                .withPathPattern(path)
                .withStatusCode(statusCode)
                .withBody(body)
                .havingHeaders()
                    .addItem()
                        .withHeader(headerName, headerValue)
                        .backToParent()
                    .backToParent()
                .backToParent()
            .build();

        assertNotNull(actual.getId());
        assertNotNull(actual.getDate());
        /*assertTrue(actual.getMoxProxyMatchingStrategy().isUseMethod());
        assertFalse(actual.getMoxProxyMatchingStrategy().isUseSessionId());
        assertFalse(actual.getMoxProxyMatchingStrategy().isUsePath());*/
        assertEquals(DEFAULT_SESSION_ID, actual.getSessionId());
        assertEquals(MoxProxyDirection.REQUEST, actual.getHttpDirection());
        assertEquals(MoxProxyAction.RESPOND, actual.getAction());
        assertEquals(method, actual.getMoxProxyHttpObject().getMethod());
        assertEquals(path, actual.getMoxProxyHttpObject().getPathPattern());
        assertEquals(statusCode, actual.getMoxProxyHttpObject().getStatusCode());
        assertEquals(body, actual.getMoxProxyHttpObject().getBody());
        assertEquals(headerName, actual.getMoxProxyHttpObject().getHeaders().iterator().next().getName());
        assertEquals(headerValue, actual.getMoxProxyHttpObject().getHeaders().iterator().next().getValue());
    }

    @DisplayName("Should throw validation exception")
    @ParameterizedTest(name = "{1}")
    @ArgumentsSource(InvalidBuildersProvider.class)
    void givenBuilder_WhenBuild_thenValidationException(MoxProxyRuleBuilder builder, String reason, String expectedMessage){
        BuilderValidationException exception = assertThrows(BuilderValidationException.class, builder::build, reason);
        assertThat(exception.getMessage().trim(), matchesPattern(expectedMessage));
    }
}
