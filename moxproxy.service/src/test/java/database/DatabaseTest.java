package database;

import interfaces.IMoxProxyDatabase;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import rules.MoxProxyRule;

import java.util.List;

@ExtendWith(SpringExtension.class)
@Import(DatabaseTestConfiguration.class)
public class DatabaseTest {

    @Autowired
    IMoxProxyDatabase database;

    @BeforeEach
    void beforeEachSeatup(){
        database.initDatabase();
    }

    @AfterEach
    void afterEachSetup(){
        database.stopDatabase();
    }

    @Test
    void findRuleByRuleIdTest(){
        MoxProxyRule rule = createDefaultRule();
        database.addRule(rule);

        MoxProxyRule found = database.findRuleByById(rule.getId());
        Assertions.assertEquals(found, rule);
    }

    @Test
    void findRulesBySessionIdTest(){
        MoxProxyRule rule1 = createDefaultRule();
        MoxProxyRule rule2 = createDefaultRule();
        MoxProxyRule rule3 = createDefaultRule();
        rule3.setSessionId("unknown");

        database.addRule(rule1);
        database.addRule(rule2);
        database.addRule(rule3);

        List found = Lists.newArrayList(database.findRulesBySessionId(rule1.getSessionId()));
        Assertions.assertEquals(2, found.size());
    }

    private MoxProxyRule createDefaultRule(){
        String sessionId = "456";
        var rule = new MoxProxyRule();
        rule.setSessionId(sessionId);
        return rule;
    }
}
