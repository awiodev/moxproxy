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
                Arguments.of(createWithoutAction(), "Action is required parameter"),
                Arguments.of(createWithoutDirection(), "Direction is required parameter"),
                Arguments.of(createWithEmptyHttpObjectPath(), "HttpObject method is required parameter")
        );
    }

    private MoxProxyRuleBuilder createWithoutAction(){
        return createDefault();
    }

    private MoxProxyRuleBuilder createWithoutDirection(){
        return createDefault().withAction(MoxProxyAction.MODIFY_BODY).withHttpObject().withBody("").backToParent();
    }

    private MoxProxyRuleBuilder createWithEmptyHttpObjectPath(){
        return createDefault()
                .withAction(MoxProxyAction.MODIFY_BODY)
                .withDirection(MoxProxyDirection.REQUEST);
    }

    private MoxProxyRuleBuilder createDefault(){
        return new MoxProxyRuleBuilder();
    }
}
