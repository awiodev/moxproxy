package builders;

import dto.MoxProxyDirection;
import dto.MoxProxyRule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class MoxProxyRuleBuilderTest {

    @Test
    public void givenChildBuilder_whenThatsEnough_thenParentReturned(){
        String sessionId = "1234";

        var builder = new MoxProxyRuleBuilder();
        builder.withDirection(MoxProxyDirection.REQUEST)
                .withSessionId(sessionId);

        MoxProxyHttpObjectBuilder childBuilder = builder.withHtmlObject();

        MoxProxyRuleBuilder actual = childBuilder.backToParent();

        Assertions.assertEquals(builder, actual);
    }

    @Test
    public void givenBuilder_whenBuild_thenAllBuilt(){
        String sessionId = "1234";
        String method = "POST";
        String path = "test/path";
        int statuscode = 500;
        var builder = new MoxProxyRuleBuilder();
        MoxProxyRule model = builder
                .withDirection(MoxProxyDirection.REQUEST)
                .withSessionId(sessionId)
                .withHtmlObject()
                    .withMethod(method)
                    .withPath(path)
                    .withStatusCode(statuscode)
                    .backToParent()
                .build();

        Assertions.assertEquals(sessionId, model.getSessionId());
        Assertions.assertEquals(MoxProxyDirection.REQUEST, model.getHttpDirection());
        Assertions.assertEquals(method, model.getMoxProxyHttpObject().getMethod());
        Assertions.assertEquals(path, model.getMoxProxyHttpObject().getPath());
        Assertions.assertEquals(statuscode, model.getMoxProxyHttpObject().getStatusCode());
    }
}
