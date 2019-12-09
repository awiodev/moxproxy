package testing.builders;

import moxproxy.builders.MoxProxyHttpRuleDefinitionBuilder;
import moxproxy.builders.MoxProxyRuleBuilder;
import moxproxy.consts.MoxProxyConts;
import moxproxy.enums.MoxProxyAction;
import moxproxy.enums.MoxProxyDirection;
import moxproxy.exceptions.BuilderValidationException;
import moxproxy.model.MoxProxyRule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.matchesPattern;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MoxProxyRuleBuilderTest {

    private static final String DEFAULT_SESSION_ID = "1234";
    private static final String method = "POST";
    private static final String path = "test/path";
    private static final String body = "{something:\"some value\"}";
    private static final String headerName = "firstHeader";
    private static final String headerValue = "firstHeaderValue";

    @Test
    void givenChildBuilder_whenBackToParent_thenParentReturned(){

        MoxProxyRuleBuilder builder = new MoxProxyRuleBuilder();
        builder.withDirection(MoxProxyDirection.REQUEST)
                .withSessionId(DEFAULT_SESSION_ID);

        MoxProxyHttpRuleDefinitionBuilder childBuilder = builder.withHttpRuleDefinition();
        MoxProxyRuleBuilder actual = childBuilder.backToParent();

        assertEquals(builder, actual);
    }

    @Test
    void givenBuilder_whenBuild_thenAllBuilt() throws BuilderValidationException {
        int statusCode = 500;
        MoxProxyRuleBuilder builder = new MoxProxyRuleBuilder();
        MoxProxyRule actual = builder
            .withDirection(MoxProxyDirection.REQUEST)
            .withSessionId(DEFAULT_SESSION_ID)
            .withAction(MoxProxyAction.RESPOND)
            .withHttpRuleDefinition()
                .withMethod(method)
                .withPathPattern(path)
                .withStatusCode(statusCode)
                .withBody(body)
                .havingHeaders()
                        .withHeader(headerName, headerValue)
                .backToParent().backToParent()
            .build();

        assertEquals(DEFAULT_SESSION_ID, actual.getSessionId());
        assertEquals(MoxProxyDirection.REQUEST, actual.getDirection());
        assertEquals(MoxProxyAction.RESPOND, actual.getAction());
        assertEquals(method, actual.getHttpObject().getMethod());
        assertEquals(path, actual.getHttpObject().getPathPattern());
        assertEquals(statusCode, actual.getHttpObject().getStatusCode());
        assertEquals(body, actual.getHttpObject().getBody());
        assertEquals(headerName, actual.getHttpObject().getHeaders().iterator().next().getName());
        assertEquals(headerValue, actual.getHttpObject().getHeaders().iterator().next().getValue());
    }

    @Test
    void givenBuilderWithDeleteBody_whenBuild_thenIndicatorSet() throws BuilderValidationException {
        int statusCode = 500;
        MoxProxyRuleBuilder builder = new MoxProxyRuleBuilder();
        MoxProxyRule actual = builder
                .withDirection(MoxProxyDirection.RESPONSE)
                .withSessionId(DEFAULT_SESSION_ID)
                .withAction(MoxProxyAction.DELETE)
                /*.withMatchingStrategy()
                    .useMethod()
                    .backToParent()*/
                .withHttpRuleDefinition()
                .withMethod(method)
                .withPathPattern(path)
                .withStatusCode(statusCode)
                .withDeleteBody()
                .havingHeaders()
                    .withHeader(headerName, headerValue)
                    .withHeader("odert", headerValue)
                .backToParent()
                .backToParent()
                .build();

        assertEquals(actual.getHttpObject().getBody(), MoxProxyConts.DELETE_BODY_INDICATOR);
    }

    @DisplayName("Should throw validation exception")
    @ParameterizedTest(name = "{1}")
    @ArgumentsSource(InvalidBuildersProvider.class)
    void givenBuilder_WhenBuild_thenValidationException(MoxProxyRuleBuilder builder, String reason, String expectedMessage){
        BuilderValidationException exception = assertThrows(BuilderValidationException.class, builder::build, reason);
        assertThat(exception.getMessage().trim(), matchesPattern(expectedMessage));
    }
}
