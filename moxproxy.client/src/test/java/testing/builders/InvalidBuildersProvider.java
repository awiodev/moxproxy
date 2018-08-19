package testing.builders;

import moxproxy.builders.MoxProxyRuleBuilder;
import moxproxy.enums.MoxProxyAction;
import moxproxy.enums.MoxProxyDirection;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class InvalidBuildersProvider implements ArgumentsProvider {

    private static final String defaultMethod = "GET";
    private static final String defaultPath = "some/path";
    private static final String defaultValue = "someValue";

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
        return Stream.of(
                Arguments.of(createWithoutAction(), "Action is required parameter", "Object field: ACTION member of: .* cannot be null."),
                Arguments.of(createWithoutDirection(), "Direction is required parameter", "Object field: DIRECTION member of: .* cannot be null."),
                Arguments.of(createWithEmptyHttpObjectPath(), "HttpObject method is required parameter", "Object field: METHOD member of: .* cannot be null."),
                Arguments.of(createWithEmptyHttpObjectHeaderName(MoxProxyAction.DELETE_BODY), "HttpObject Header name is required", "Object field: NAME member of: .* cannot be null."),
                Arguments.of(createBasicActionWithoutHeaders(MoxProxyAction.SET_HEADER), "HttpObject Headers are required for " + MoxProxyAction.SET_HEADER + " action",
                        "Object field: HEADERS member of: .* cannot be empty. Set at least one header when using action: SET_HEADER"),
                Arguments.of(createBasicActionWithoutHeaders(MoxProxyAction.DELETE_HEADER), "HttpObject Headers are required for " + MoxProxyAction.SET_HEADER + " action",
                        "Object field: HEADERS member of: .* cannot be empty. Set at least one header when using action: DELETE_HEADER"),
                Arguments.of(createBasicActionWithoutHeaders(MoxProxyAction.SET_BODY), "HttpObject body is required for " + MoxProxyAction.SET_BODY + " action",
                        "Object field: BODY member of: .* cannot be null. Set body when using action: SET_BODY"),
                Arguments.of(createWithEmptyHttpObjectHeaderName(MoxProxyAction.SET_HEADER), "HttpObject header name is required for " + MoxProxyAction.SET_HEADER,
                        "Object field: HEADER_NAME member of: .* cannot be null. Header name cannot be null when using action: SET_HEADER"),
                Arguments.of(createBasicActionWithoutHeaders(MoxProxyAction.RESPOND), "HttpObject status code is required for " + MoxProxyAction.RESPOND,
                        "Object field: STATUS_CODE member of: .* cannot be 0. Status code cannot be 0 when using action: RESPOND")
        );
    }

    private MoxProxyRuleBuilder createWithoutAction(){
        return createDefault().withDirection(MoxProxyDirection.REQUEST);
    }

    private MoxProxyRuleBuilder createWithoutDirection(){
        return createDefault().withAction(MoxProxyAction.SET_BODY).withHttpObject().withBody("").backToParent();
    }

    private MoxProxyRuleBuilder createWithEmptyHttpObjectPath(){
        return createDefault()
                .withAction(MoxProxyAction.DELETE_BODY)
                .withDirection(MoxProxyDirection.REQUEST);
    }

    private MoxProxyRuleBuilder createWithEmptyHttpObjectHeaderName(MoxProxyAction action){
        return createDefault()
                .withAction(action)
                .withDirection(MoxProxyDirection.REQUEST)
                .withHttpObject()
                .withMethod(defaultMethod)
                .withPath(defaultPath)
                .havingHeaders()
                    .addChildItem().withValue(defaultValue)
                        .backToParent()
                    .backToParent()
                .backToParent();
    }

    private MoxProxyRuleBuilder createBasicActionWithoutHeaders(MoxProxyAction action){
        return createDefault()
                .withAction(action)
                .withDirection(MoxProxyDirection.REQUEST)
                .withHttpObject()
                    .withMethod(defaultMethod)
                    .withPath(defaultPath)
                    .backToParent();
    }

    private MoxProxyRuleBuilder createDefault(){
        return new MoxProxyRuleBuilder();
    }
}
