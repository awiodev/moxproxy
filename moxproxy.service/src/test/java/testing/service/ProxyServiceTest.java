package testing.service;

import moxproxy.interfaces.IMoxProxyDatabase;
import moxproxy.interfaces.IMoxProxyService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import testing.TestBase;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@Import(ProxyServiceTestConfiguration.class)
public class ProxyServiceTest extends TestBase {

    @Autowired
    IMoxProxyDatabase database;

    @Autowired
    IMoxProxyService service;

    @BeforeEach
    void beforeEachSetup(){
        database.startDatabase();
    }

    @AfterEach
    void afterEachSetup(){
        database.stopDatabase();
    }

    @Test
    void givenRule_whenCreate_thenRuleCreated(){
        var rule = createDefaultRule();
        service.createRule(rule);
        var actual = database.findRuleByById(rule.getId());
        assertEquals(rule, actual);
    }
}
