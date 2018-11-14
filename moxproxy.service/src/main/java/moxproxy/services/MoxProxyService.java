package moxproxy.services;

import moxproxy.dto.MoxProxyProcessedTrafficEntry;
import moxproxy.dto.MoxProxyRule;
import moxproxy.interfaces.IMoxProxyDatabase;
import moxproxy.interfaces.IMoxProxyRulesMatcher;
import moxproxy.interfaces.IMoxProxyScheduleFunctionService;
import moxproxy.interfaces.IMoxProxyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class MoxProxyService implements IMoxProxyService, IMoxProxyScheduleFunctionService {

    private static final Logger LOG = LoggerFactory.getLogger(MoxProxyService.class);

    @Autowired
    protected IMoxProxyDatabase database;

    @Autowired
    protected IMoxProxyRulesMatcher matcher;

    @Override
    public Iterable<MoxProxyProcessedTrafficEntry> getSessionRequestTraffic(String sessionId) {
        LOG.info("Getting requests traffic for session id: {}", sessionId);
        return database.getProcessedRequestTraffic(sessionId);
    }

    @Override
    public Iterable<MoxProxyProcessedTrafficEntry> getAllRequestTraffic() {
        LOG.info("Getting all requests traffic");
        return database.getProcessedRequestTraffic();
    }

    @Override
    public Iterable<MoxProxyProcessedTrafficEntry> getSessionResponseTraffic(String sessionId) {
        LOG.info("Getting responses traffic for session id: {}", sessionId);
        return database.getProcessedResponseTraffic(sessionId);
    }

    @Override
    public Iterable<MoxProxyProcessedTrafficEntry> getAllResponseTraffic() {
        LOG.info("Getting all responses traffic");
        return database.getProcessedResponseTraffic();
    }

    @Override
    public void cancelRule(String ruleId) {
        LOG.info("Canceling rule: {}", ruleId);
        database.cleanRule(ruleId);
    }

    @Override
    public void clearSessionRules(String sessionId) {
        LOG.info("Clearing rules for session id: {}", sessionId);
        database.cleanRules(sessionId);
    }

    @Override
    public void clearSessionEntries(String sessionId) {
        LOG.info("Clearing entries for session id: {}", sessionId);
        database.cleanProcessedTraffic(sessionId);
        database.cleanRules(sessionId);
    }

    @Override
    public void clearAllSessionEntries() {
        LOG.info("Clearing all sessions entries");
        database.cleanAllProcessedTraffic();
        database.cleanAllRules();
    }

    @Override
    public String createRule(MoxProxyRule moxProxyRule) {
        LOG.info("Creating rule: {} for session id: {}", moxProxyRule.getId(), moxProxyRule.getSessionId());
        database.addRule(moxProxyRule);
        return moxProxyRule.getId();
    }

    @Override
    public void enableSessionIdMatchingStrategy() {
        LOG.info("Enabling session id matching strategy");
        matcher.useSessionIdMatchingStrategy(true);
    }

    @Override
    public void disableSessionIdMatchingStrategy() {
        LOG.info("Disabling session id matching strategy");
        matcher.useSessionIdMatchingStrategy(false);
    }

    @Override
    public void cleanProcessedTraffic(Date cleanBefore) {
        LOG.info("Cleaning processed traffic older than: {}", parseDate(cleanBefore));
        database.cleanProcessedTraffic(cleanBefore);
    }

    @Override
    public void cleanRules(Date cleanBefore) {
        LOG.info("Cleaning processed rules older than: {}", parseDate(cleanBefore));
        database.cleanRules(cleanBefore);
    }

    private String parseDate(Date date){
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        return df.format(date);
    }
}
