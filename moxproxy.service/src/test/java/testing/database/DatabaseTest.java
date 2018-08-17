package testing.database;

import moxproxy.dto.MoxProxyProcessedTrafficEntry;
import moxproxy.dto.MoxProxyRule;
import moxproxy.interfaces.IMoxProxyDatabase;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Import(DatabaseTestConfiguration.class)
class DatabaseTest {

    private static final String UNKNOWN = "UNKNOWN";

    @Autowired
    private IMoxProxyDatabase database;

    @BeforeEach
    void beforeEachSetup(){
        database.startDatabase();
    }

    @AfterEach
    void afterEachSetup(){
        database.stopDatabase();
    }

    @Test
    void givenTrafficEntries_whenAddAndFind_thenEntriesFound(){
        MoxProxyProcessedTrafficEntry entry1 = createDefaultTrafficEntry();
        MoxProxyProcessedTrafficEntry entry2 = createDefaultTrafficEntry();
        MoxProxyProcessedTrafficEntry entry3 = createDefaultTrafficEntry();
        database.addProcessedRequest(entry1);
        database.addProcessedRequest(entry2);
        database.addProcessedRequest(entry3);

        List<MoxProxyProcessedTrafficEntry> found = Lists.newArrayList(database.getProcessedTraffic());
        assertEquals(3, found.size());
    }

    @Test
    void givenTrafficEntries_whenCleanBySessionId_thenEntriesCleared(){
        MoxProxyProcessedTrafficEntry entry1 = createDefaultTrafficEntry();
        MoxProxyProcessedTrafficEntry entry2 = createDefaultTrafficEntry();
        MoxProxyProcessedTrafficEntry entry3 = createDefaultTrafficEntry();
        entry3.setSessionId(UNKNOWN);
        database.addProcessedRequest(entry1);
        database.addProcessedRequest(entry2);
        database.addProcessedRequest(entry3);

        database.cleanProcessedTraffic(entry1.getSessionId());

        List<MoxProxyProcessedTrafficEntry> found = Lists.newArrayList(database.getProcessedTraffic());
        assertEquals(1, found.size());
        MoxProxyProcessedTrafficEntry first = found.get(0);
        assertEquals(entry3, first);
    }

    @Test
    void givenTrafficEntries_whenCleanByDate_thenEntriesCleared(){
        MoxProxyProcessedTrafficEntry entry1 = createDefaultTrafficEntry();
        MoxProxyProcessedTrafficEntry entry2 = createDefaultTrafficEntry();
        MoxProxyProcessedTrafficEntry entry3 = createDefaultTrafficEntry();
        database.addProcessedRequest(entry1);
        database.addProcessedRequest(entry2);
        database.addProcessedRequest(entry3);

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MINUTE, 1);

        Date newDate = cal.getTime();

        database.cleanProcessedTraffic(newDate);

        List<MoxProxyProcessedTrafficEntry> found = Lists.newArrayList(database.getProcessedTraffic());
        assertEquals(0, found.size());
    }

    @Test
    void givenTrafficEntries_whenFindBySessionId_thenEntriesFound(){
        MoxProxyProcessedTrafficEntry entry1 = createDefaultTrafficEntry();
        MoxProxyProcessedTrafficEntry entry2 = createDefaultTrafficEntry();
        MoxProxyProcessedTrafficEntry entry3 = createDefaultTrafficEntry();
        entry3.setSessionId(UNKNOWN);
        database.addProcessedRequest(entry1);
        database.addProcessedRequest(entry2);
        database.addProcessedRequest(entry3);

        List<MoxProxyProcessedTrafficEntry> found = Lists.newArrayList(database.getProcessedTraffic(entry1.getSessionId()));
        assertEquals(2, found.size());
    }

    @Test
    void givenTrafficEntries_whenCleanAll_thenEntriesCleared(){
        MoxProxyProcessedTrafficEntry entry1 = createDefaultTrafficEntry();
        MoxProxyProcessedTrafficEntry entry2 = createDefaultTrafficEntry();
        MoxProxyProcessedTrafficEntry entry3 = createDefaultTrafficEntry();
        entry3.setSessionId(UNKNOWN);
        database.addProcessedRequest(entry1);
        database.addProcessedRequest(entry2);
        database.addProcessedRequest(entry3);

        database.cleanAllProcessedTraffic();

        List<MoxProxyProcessedTrafficEntry> found = Lists.newArrayList(database.getProcessedTraffic(entry1.getSessionId()));
        assertEquals(0, found.size());
    }

    @Test
    void givenRule_whenAddAndFindById_thenRuleFound(){
        MoxProxyRule rule = createDefaultRule();
        database.addRule(rule);

        MoxProxyRule found = database.findRuleByById(rule.getId());
        assertEquals(rule, found);
    }

    @Test
    void givenRule_whenCleanById_thenRuleNotFound(){
        MoxProxyRule rule = createDefaultRule();
        database.addRule(rule);

        database.cleanRule(rule.getId());
        MoxProxyRule found = database.findRuleByById(rule.getId());
        assertNull(found);
    }

    @Test
    void givenRules_whenAddAndFindBySessionId_ThenRulesFound(){
        MoxProxyRule rule1 = createDefaultRule();
        MoxProxyRule rule2 = createDefaultRule();
        MoxProxyRule rule3 = createDefaultRule();
        rule3.setSessionId(UNKNOWN);

        database.addRule(rule1);
        database.addRule(rule2);
        database.addRule(rule3);

        List found = Lists.newArrayList(database.findRulesBySessionId(rule1.getSessionId()));
        assertEquals(2, found.size());
    }

    @Test
    void givenRules_whenCleanBySessionId_ThenRulesNotFound(){
        MoxProxyRule rule1 = createDefaultRule();
        MoxProxyRule rule2 = createDefaultRule();
        MoxProxyRule rule3 = createDefaultRule();
        rule3.setSessionId(UNKNOWN);

        database.addRule(rule1);
        database.addRule(rule2);
        database.addRule(rule3);

        database.cleanRules(rule1.getSessionId());
        List found = Lists.newArrayList(database.findRulesBySessionId(rule1.getSessionId()));
        assertTrue(found.isEmpty());
    }

    @Test
    void givenRules_whenCleanByOtherSessionId_ThenRuleFound(){
        MoxProxyRule rule1 = createDefaultRule();
        MoxProxyRule rule2 = createDefaultRule();
        MoxProxyRule rule3 = createDefaultRule();
        rule3.setSessionId(UNKNOWN);

        database.addRule(rule1);
        database.addRule(rule2);
        database.addRule(rule3);

        database.cleanRules(rule1.getSessionId());
        List found = Lists.newArrayList(database.findRulesBySessionId(rule3.getSessionId()));
        assertEquals(1, found.size());
    }

    @Test
    void givenRules_whenAllClean_ThenRulesNotFound(){
        MoxProxyRule rule1 = createDefaultRule();
        MoxProxyRule rule2 = createDefaultRule();
        MoxProxyRule rule3 = createDefaultRule();
        rule3.setSessionId(UNKNOWN);

        database.addRule(rule1);
        database.addRule(rule2);
        database.addRule(rule3);

        database.cleanAllRules();
        List found = Lists.newArrayList(database.getAllRules());
        assertTrue(found.isEmpty());
    }

    private MoxProxyProcessedTrafficEntry createDefaultTrafficEntry(){
        var trafficEntry = new MoxProxyProcessedTrafficEntry();
        trafficEntry.setSessionId("123");
        return trafficEntry;
    }

    private MoxProxyRule createDefaultRule(){
        var rule = new MoxProxyRule();
        rule.setSessionId("456");
        return rule;
    }

}
