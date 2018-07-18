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
    void givenRule_whenFindById_thenRuleFound(){
        MoxProxyRule rule = createDefaultRule();
        database.addRule(rule);

        MoxProxyRule found = database.findRuleByById(rule.getId());
        Assertions.assertEquals(rule, found);
    }

    @Test
    void givenRule_whenCleanById_thenRuleNotFound(){
        MoxProxyRule rule = createDefaultRule();
        database.addRule(rule);

        database.cleanRule(rule.getId());
        MoxProxyRule found = database.findRuleByById(rule.getId());
        Assertions.assertEquals(null, found);
    }

    @Test
    void givenRules_whenFindBySessionId_ThenRulesFound(){
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

    @Test
    void givenRules_whenCleanBySessionId_ThenRulesNotFound(){
        MoxProxyRule rule1 = createDefaultRule();
        MoxProxyRule rule2 = createDefaultRule();
        MoxProxyRule rule3 = createDefaultRule();
        rule3.setSessionId("unknown");

        database.addRule(rule1);
        database.addRule(rule2);
        database.addRule(rule3);

        database.cleanRules(rule1.getSessionId());
        List found = Lists.newArrayList(database.findRulesBySessionId(rule1.getSessionId()));
        Assertions.assertTrue(found.isEmpty());
    }

    @Test
    void givenRules_whenAllClean_ThenRulesNotFound(){
        MoxProxyRule rule1 = createDefaultRule();
        MoxProxyRule rule2 = createDefaultRule();
        MoxProxyRule rule3 = createDefaultRule();
        rule3.setSessionId("unknown");

        database.addRule(rule1);
        database.addRule(rule2);
        database.addRule(rule3);

        database.cleanAllRules();
        List found = Lists.newArrayList(database.getAllRules());
        Assertions.assertTrue(found.isEmpty());
    }

    private MoxProxyRule createDefaultRule(){
        String sessionId = "456";
        var rule = new MoxProxyRule();
        rule.setSessionId(sessionId);
        return rule;
    }
}
