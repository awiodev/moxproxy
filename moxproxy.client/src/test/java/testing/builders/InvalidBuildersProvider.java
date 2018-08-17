package testing.builders;

import moxproxy.builders.MoxProxyRuleBuilder;
import moxproxy.enums.MoxProxyAction;
import moxproxy.enums.MoxProxyDirection;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class InvalidBuildersProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
        return Stream.of(
                Arguments.of(createWithoutAction(), "Action is required parameter", "Object field: ACTION member of: .* cannot be null"),
                Arguments.of(createWithoutDirection(), "Direction is required parameter", "Object field: DIRECTION member of: .* cannot be null"),
                Arguments.of(createWithEmptyHttpObjectPath(), "HttpObject method is required parameter", "Object field: METHOD member of: .* cannot be null"),
                Arguments.of(createWithEmptyHttpObjectHeaderName(), "HttpObject Header name is required", "Object field: NAME member of: .* cannot be null"),
                Arguments.of(createWithEmptyHttpObjectHeaderValue(), "HttpObject Header value is required", "Object field: VALUE member of: .* cannot be null")
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
                .withMethod("GET")
                .withPath("some/path")
                .havingHeaders()
                    .addChildItem().withValue("someval")
                        .backToParent()
                    .backToParent()
                .backToParent();
    }

    private MoxProxyRuleBuilder createWithEmptyHttpObjectHeaderValue(){
        return createDefault()
                .withAction(MoxProxyAction.MODIFY_BODY)
                .withDirection(MoxProxyDirection.REQUEST)
                .withHttpObject()
                .withMethod("GET")
                .withPath("some/path")
                .havingHeaders()
                    .addChildItem().withName("somename")
                        .backToParent()
                    .backToParent()
                .backToParent();
    }

    private MoxProxyRuleBuilder createDefault(){
        return new MoxProxyRuleBuilder();
    }
}
