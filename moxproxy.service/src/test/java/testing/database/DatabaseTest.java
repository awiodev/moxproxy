package testing.database;

import moxproxy.dto.MoxProxyProcessedTrafficEntry;
import moxproxy.dto.MoxProxyRule;
import moxproxy.interfaces.MoxProxyDatabase;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import testing.TestBase;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Import(DatabaseTestConfiguration.class)
class DatabaseTest extends TestBase {

    @Autowired
    private MoxProxyDatabase database;

    @BeforeEach
    void beforeEachSetup(){
        database.startDatabase();
    }

    @AfterEach
    void afterEachSetup(){
        database.stopDatabase();
    }

    @Test
    void givenTrafficEntries_whenAddRequestAndFind_thenRequestFound(){
        MoxProxyProcessedTrafficEntry entry1 = createDefaultTrafficEntry();
        MoxProxyProcessedTrafficEntry entry2 = createDefaultTrafficEntry();
        MoxProxyProcessedTrafficEntry entry3 = createDefaultTrafficEntry();
        database.addProcessedRequest(entry1);
        database.addProcessedRequest(entry2);
        database.addProcessedRequest(entry3);

        List<MoxProxyProcessedTrafficEntry> found = Lists.newArrayList(database.getProcessedRequestTraffic());
        assertEquals(3, found.size());
    }

    @Test
    void givenTrafficEntries_whenAddResponseAndFind_thenResponseFound(){
        MoxProxyProcessedTrafficEntry entry1 = createDefaultTrafficEntry();
        MoxProxyProcessedTrafficEntry entry2 = createDefaultTrafficEntry();
        MoxProxyProcessedTrafficEntry entry3 = createDefaultTrafficEntry();
        database.addProcessedResponse(entry1);
        database.addProcessedResponse(entry2);
        database.addProcessedResponse(entry3);

        List<MoxProxyProcessedTrafficEntry> found = Lists.newArrayList(database.getProcessedResponseTraffic());
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
        database.addProcessedResponse(entry1);
        database.addProcessedResponse(entry2);
        database.addProcessedResponse(entry3);

        database.cleanProcessedTraffic(entry1.getSessionId());

        List<MoxProxyProcessedTrafficEntry> found = Lists.newArrayList(database.getProcessedRequestTraffic());
        assertEquals(1, found.size());
        MoxProxyProcessedTrafficEntry first = found.get(0);
        assertEquals(entry3, first);

        found = Lists.newArrayList(database.getProcessedResponseTraffic());
        assertEquals(1, found.size());
        first = found.get(0);
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

        List<MoxProxyProcessedTrafficEntry> found = Lists.newArrayList(database.getProcessedRequestTraffic());
        assertEquals(0, found.size());
    }

    @Test
    void givenRequestTrafficEntries_whenFindBySessionId_thenEntriesFound(){
        MoxProxyProcessedTrafficEntry entry1 = createDefaultTrafficEntry();
        MoxProxyProcessedTrafficEntry entry2 = createDefaultTrafficEntry();
        MoxProxyProcessedTrafficEntry entry3 = createDefaultTrafficEntry();
        entry3.setSessionId(UNKNOWN);
        database.addProcessedRequest(entry1);
        database.addProcessedRequest(entry2);
        database.addProcessedRequest(entry3);

        List<MoxProxyProcessedTrafficEntry> found = Lists.newArrayList(database.getProcessedRequestTraffic(entry1.getSessionId()));
        assertEquals(2, found.size());
    }

    @Test
    void givenResponseTrafficEntries_whenFindBySessionId_thenEntriesFound(){
        MoxProxyProcessedTrafficEntry entry1 = createDefaultTrafficEntry();
        MoxProxyProcessedTrafficEntry entry2 = createDefaultTrafficEntry();
        MoxProxyProcessedTrafficEntry entry3 = createDefaultTrafficEntry();
        entry3.setSessionId(UNKNOWN);
        database.addProcessedResponse(entry1);
        database.addProcessedResponse(entry2);
        database.addProcessedResponse(entry3);

        List<MoxProxyProcessedTrafficEntry> found = Lists.newArrayList(database.getProcessedResponseTraffic(entry1.getSessionId()));
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
        database.addProcessedResponse(entry1);
        database.addProcessedResponse(entry2);
        database.addProcessedResponse(entry3);

        database.cleanAllProcessedTraffic();

        List<MoxProxyProcessedTrafficEntry> found = Lists.newArrayList(database.getProcessedRequestTraffic(entry1.getSessionId()));
        assertEquals(0, found.size());

        found = Lists.newArrayList(database.getProcessedResponseTraffic(entry1.getSessionId()));
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

    @Test
    void givenRules_whenCleanByDate_ThenRulesRemoved(){
        MoxProxyRule rule1 = createDefaultRule();
        MoxProxyRule rule2 = createDefaultRule();
        MoxProxyRule rule3 = createDefaultRule();

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MINUTE, 1);

        database.addRule(rule1);
        database.addRule(rule2);

        rule3.getDate().setTime(cal.getTimeInMillis());
        database.addRule(rule3);

        Date cleanDate = cal.getTime();

        database.cleanRules(cleanDate);
        List found = Lists.newArrayList(database.findRulesBySessionId(rule3.getSessionId()));
        assertEquals(1, found.size());
    }
}
