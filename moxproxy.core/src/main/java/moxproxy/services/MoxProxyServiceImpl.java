package moxproxy.services;

import moxproxy.interfaces.*;
import moxproxy.model.MoxProxyProcessedTrafficEntry;
import moxproxy.model.MoxProxyRule;
import moxproxy.model.MoxProxySessionIdMatchingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MoxProxyServiceImpl implements MoxProxyService, MoxProxyScheduleFunctionService {

    private static final Logger LOG = LoggerFactory.getLogger(MoxProxyServiceImpl.class);

    MoxProxyDatabase database;

    MoxProxyRulesMatcher matcher;

    protected MoxProxyServiceConfiguration configuration;

    public MoxProxyServiceImpl(MoxProxyDatabase database, MoxProxyRulesMatcher matcher, MoxProxyServiceConfiguration configuration){
        this.database = database;
        this.matcher = matcher;
        this.configuration = configuration;
        this.matcher.useSessionIdMatchingStrategy(configuration.isMatchSessionIdStrategy());
    }

    @Override
    public List<MoxProxyProcessedTrafficEntry> getSessionRequestTraffic(String sessionId) {
        LOG.info("Getting requests trafficentry for session id: {}", sessionId);
        return database.getProcessedRequestTraffic(sessionId);
    }

    @Override
    public List<MoxProxyProcessedTrafficEntry> getAllRequestTraffic() {
        LOG.info("Getting all requests trafficentry");
        return database.getProcessedRequestTraffic();
    }

    @Override
    public List<MoxProxyProcessedTrafficEntry> getSessionResponseTraffic(String sessionId) {
        LOG.info("Getting responses trafficentry for session id: {}", sessionId);
        return database.getProcessedResponseTraffic(sessionId);
    }

    @Override
    public List<MoxProxyProcessedTrafficEntry> getAllResponseTraffic() {
        LOG.info("Getting all responses trafficentry");
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
    public void modifySessionMatchingStrategy(MoxProxySessionIdMatchingStrategy matchingStrategy) {
        matcher.useSessionIdMatchingStrategy(matchingStrategy.isIncludeSessionIdMatch());
        LOG.info("Session id match modified to: " + matchingStrategy.isIncludeSessionIdMatch());
    }

    @Override
    public MoxProxySessionIdMatchingStrategy getSessionMatchingStrategy() {
        MoxProxySessionIdMatchingStrategy strat = new MoxProxySessionIdMatchingStrategy();
        strat.setIncludeSessionIdMatch(matcher.getSessionIdMatchingStrategy());
        return strat;
    }

    @Override
    public void cleanProcessedTraffic(Date cleanBefore) {
        LOG.info("Cleaning processed trafficentry older than: {}", parseDate(cleanBefore));
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
