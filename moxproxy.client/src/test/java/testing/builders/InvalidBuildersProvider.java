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
    private static final String defaultName = "someName";

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
        return Stream.of(
                Arguments.of(createWithoutAction(), "Action is required parameter", "Object field: ACTION member of: .* cannot be null"),
                Arguments.of(createWithoutDirection(), "Direction is required parameter", "Object field: DIRECTION member of: .* cannot be null"),
                Arguments.of(createWithEmptyHttpObjectPath(), "HttpObject method is required parameter", "Object field: METHOD member of: .* cannot be null"),
                Arguments.of(createWithEmptyHttpObjectHeaderName(), "HttpObject Header name is required", "Object field: NAME member of: .* cannot be null"),
                Arguments.of(createWithEmptyHttpObjectHeaderValue(), "HttpObject Header value is required", "Object field: VALUE member of: .* cannot be null"),
                Arguments.of(createBasicActionWithoutHeaders(MoxProxyAction.ADD_HEADER), "HttpObject Headers are required for " + MoxProxyAction.ADD_HEADER + " action",
                        "Object field: HEADERS member of: .* cannot be empty. Required when ADD_HEADER action is selected"),
                Arguments.of(createBasicActionWithoutHeaders(MoxProxyAction.MODIFY_HEADER), "HttpObject Headers are required for " + MoxProxyAction.ADD_HEADER + " action",
                        "Object field: HEADERS member of: .* cannot be empty. Required when MODIFY_HEADER action is selected"),
                Arguments.of(createBasicActionWithoutHeaders(MoxProxyAction.DELETE_HEADER), "HttpObject Headers are required for " + MoxProxyAction.ADD_HEADER + " action",
                        "Object field: HEADERS member of: .* cannot be empty. Required when DELETE_HEADER action is selected")
        );
    }

    private MoxProxyRuleBuilder createWithoutAction(){
        return createDefault().withDirection(MoxProxyDirection.REQUEST);
    }

    private MoxProxyRuleBuilder createWithoutDirection(){
        return createDefault().withAction(MoxProxyAction.MODIFY_BODY).withHttpObject().withBody("").backToParent();
    }

    private MoxProxyRuleBuilder createWithEmptyHttpObjectPath(){
        return createDefault()
                .withAction(MoxProxyAction.MODIFY_BODY)
                .withDirection(MoxProxyDirection.REQUEST);
    }

    private MoxProxyRuleBuilder createWithEmptyHttpObjectHeaderName(){
        return createDefault()
                .withAction(MoxProxyAction.MODIFY_BODY)
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

    private MoxProxyRuleBuilder createWithEmptyHttpObjectHeaderValue(){
        return createDefault()
                .withAction(MoxProxyAction.MODIFY_BODY)
                .withDirection(MoxProxyDirection.REQUEST)
                .withHttpObject()
                    .withMethod(defaultMethod)
                    .withPath(defaultPath)
                    .havingHeaders()
                        .addChildItem()
                        .withName(defaultName)
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
