package testing.database;

import com.google.common.collect.Lists;
import moxproxy.configuration.MoxProxyServiceConfigurationImpl;
import moxproxy.di.DaggerServiceComponent;
import moxproxy.di.ServiceModule;
import moxproxy.interfaces.MoxProxyDatabase;
import moxproxy.model.MoxProxyProcessedTrafficEntry;
import moxproxy.model.MoxProxyRule;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import testing.TestBase;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest extends TestBase {

    private MoxProxyDatabase database;

    @BeforeEach
    void beforeEachSetup(){
        database = DaggerServiceComponent.builder().serviceModule(new ServiceModule(new MoxProxyServiceConfigurationImpl())).build().getMoxProxyDatabase();
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
        MoxProxyProcessedTrafficEntry entry3 = createDefaultTrafficEntry(UNKNOWN);
        database.addProcessedRequest(entry1);
        database.addProcessedRequest(entry2);
        database.addProcessedRequest(entry3);
        database.addProcessedResponse(entry1);
        database.addProcessedResponse(entry2);
        database.addProcessedResponse(entry3);

        database.cleanProcessedTraffic(entry1.getSessionId());

        List<MoxProxyProcessedTrafficEntry> found = Lists.newArrayList(database.getProcessedRequestTraffic());
        assertEquals(1, found.size());
        MoxProxyProcessedTrafficEntry first;

        found = Lists.newArrayList(database.getProcessedResponseTraffic());
        assertEquals(1, found.size());
        first = found.get(0);
        Assertions.assertThat(first).isEqualToIgnoringGivenFields(entry3, FIELDS_TO_IGNORE);
    }

    @Test
    void givenTrafficEntries_whenCleanByDate_thenEntriesCleared(){
        MoxProxyProcessedTrafficEntry entry1 = createDefaultTrafficEntry();
        MoxProxyProcessedTrafficEntry entry2 = createDefaultTrafficEntry();
        MoxProxyProcessedTrafficEntry entry3 = createDefaultTrafficEntry();
        database.addProcessedRequest(entry1);
        database.addProcessedRequest(entry2);
        database.addProcessedRequest(entry3);

        database.cleanProcessedTraffic(OffsetDateTime.now(ZoneOffset.UTC).plusMinutes(1));

        List<MoxProxyProcessedTrafficEntry> found = Lists.newArrayList(database.getProcessedRequestTraffic());
        assertEquals(0, found.size());
    }

    @Test
    void givenRequestTrafficEntries_whenFindBySessionId_thenEntriesFound(){
        MoxProxyProcessedTrafficEntry entry1 = createDefaultTrafficEntry();
        MoxProxyProcessedTrafficEntry entry2 = createDefaultTrafficEntry();
        MoxProxyProcessedTrafficEntry entry3 = createDefaultTrafficEntry(UNKNOWN);
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
        MoxProxyProcessedTrafficEntry entry3 = createDefaultTrafficEntry(UNKNOWN);
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
        MoxProxyProcessedTrafficEntry entry3 = createDefaultTrafficEntry(UNKNOWN);
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
        String ruleId = database.addRule(rule);

        MoxProxyRule found = database.findRuleById(ruleId);
        Assertions.assertThat(found).isEqualToIgnoringGivenFields(rule, FIELDS_TO_IGNORE);
    }

    @Test
    void givenRule_whenCleanById_thenRuleNotFound(){
        MoxProxyRule rule = createDefaultRule();
        String ruleId = database.addRule(rule);

        database.cleanRule(ruleId);
        MoxProxyRule found = database.findRuleById(ruleId);
        assertNull(found);
    }

    @Test
    void givenRules_whenAddAndFindBySessionId_ThenRulesFound(){
        MoxProxyRule rule1 = createDefaultRule();
        MoxProxyRule rule2 = createDefaultRule();
        MoxProxyRule rule3 = createDefaultRule(UNKNOWN);

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
        MoxProxyRule rule3 = createDefaultRule(UNKNOWN);

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
        MoxProxyRule rule3 = createDefaultRule(UNKNOWN);

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
        MoxProxyRule rule3 = createDefaultRule(UNKNOWN);

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

        database.addRule(rule1);
        database.addRule(rule2);
        database.addRule(rule3);

        database.cleanRules(OffsetDateTime.now(ZoneOffset.UTC).plusMinutes(1));
        List found = Lists.newArrayList(database.findRulesBySessionId(rule3.getSessionId()));
        assertEquals(0, found.size());
    }
}
