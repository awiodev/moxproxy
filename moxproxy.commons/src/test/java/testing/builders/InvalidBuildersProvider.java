package testing.builders;

import moxproxy.enums.MoxProxyAction;
import moxproxy.enums.MoxProxyDirection;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class InvalidBuildersProvider implements ArgumentsProvider {

    private static final String defaultMethod = "GET";
    private static final String defaultPath = "some/path";
    private static final int defaultStatusCode = 200;

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
        return Stream.of(
                Arguments.of(createWithoutAction(), "Action is required parameter", "Object field: ACTION member of: .* cannot be null."),
                Arguments.of(createWithoutDirection(), "Direction is required parameter", "Object field: DIRECTION member of: .* cannot be null."),
                Arguments.of(createWithEmptyHttpObjectMethod(), "HttpObject method is required parameter", "Object field: METHOD member of: .* cannot be null."),
                Arguments.of(createWithoutPathPattern(), "HttpObject path pattern is required parameter", "Object field: PATH_PATTERN member of: .* cannot be null."),
                Arguments.of(createBasicActionWithoutHeaders(), "HttpObject status code is required for " + MoxProxyAction.RESPOND,
                        "Object field: STATUS_CODE member of: .* cannot be 0. Status code cannot be 0 when using action: RESPOND"),
                Arguments.of(createRespondActionWithResponseDirection(), "Respond cannot be applied to Response direction",
                        "ACTION: RESPOND cannot be applied to DIRECTION: RESPONSE")
        );
    }

    private MoxProxyRuleBuilder createWithoutAction(){
        return createDefault()
                .withDirection(MoxProxyDirection.REQUEST);
    }

    private MoxProxyRuleBuilder createWithoutDirection(){
        return createDefault()
                .withAction(MoxProxyAction.MODIFY)
                .withHttpRuleDefinition()
                .withBody("").backToParent();
    }

    private MoxProxyRuleBuilder createWithEmptyHttpObjectMethod(){
        return createDefault()
                .withAction(MoxProxyAction.MODIFY)
                .withDirection(MoxProxyDirection.REQUEST);
    }

    private MoxProxyRuleBuilder createBasicActionWithoutHeaders(){
        return createDefault()
                .withAction(MoxProxyAction.RESPOND)
                .withDirection(MoxProxyDirection.RESPONSE)
                .withHttpRuleDefinition()
                    .withMethod(defaultMethod)
                    .withPathPattern(defaultPath)
                    .backToParent();
    }

    private MoxProxyRuleBuilder createWithoutPathPattern(){
        return createDefault()
                .withDirection(MoxProxyDirection.REQUEST)
                .withAction(MoxProxyAction.RESPOND)
                .withHttpRuleDefinition()
                .withMethod(defaultMethod)
                .withStatusCode(defaultStatusCode)
                .backToParent();
    }

    private MoxProxyRuleBuilder createRespondActionWithResponseDirection(){
        return createDefault()
                .withDirection(MoxProxyDirection.RESPONSE)
                .withAction(MoxProxyAction.RESPOND)
                .withHttpRuleDefinition()
                .withMethod(defaultMethod)
                .withStatusCode(defaultStatusCode)
                .backToParent();
    }

    private MoxProxyRuleBuilder createDefault(){
        return new MoxProxyRuleBuilder();
    }
}
