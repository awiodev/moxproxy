package moxproxy.rules;

import com.google.common.collect.Lists;
import moxproxy.enums.MoxProxyDirection;
import moxproxy.interfaces.MoxProxyDatabase;
import moxproxy.interfaces.MoxProxyRulesMatcher;
import moxproxy.model.MoxProxyProcessedTrafficEntry;
import moxproxy.model.MoxProxyRule;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MoxProxyRulesMatcherImpl implements MoxProxyRulesMatcher {

    private MoxProxyDatabase database;

    private boolean matchSessionId;

    @Inject
    public MoxProxyRulesMatcherImpl(MoxProxyDatabase database){
        this.database = database;
    }

    @Override
    public void useSessionIdMatchingStrategy(boolean value) {
        matchSessionId = value;
    }

    @Override
    public boolean getSessionIdMatchingStrategy() {
        return matchSessionId;
    }

    @Override
    public List<MoxProxyRule> match(MoxProxyProcessedTrafficEntry trafficEntry, MoxProxyDirection moxProxyDirection) {
        return getRules(trafficEntry, moxProxyDirection)
                .stream().filter(rule -> match(rule, trafficEntry)).collect(Collectors.toCollection(ArrayList::new));
    }

    private List<MoxProxyRule> getRules(MoxProxyProcessedTrafficEntry trafficEntry, MoxProxyDirection moxProxyDirection){
        ArrayList<MoxProxyRule> result = matchSessionId ? Lists.newArrayList(database.findRulesBySessionId(trafficEntry.getSessionId()))
                : Lists.newArrayList(database.getAllRules());
        return result.stream().filter(x -> x.getHttpDirection() == moxProxyDirection).collect(Collectors.toList());
    }

    private boolean match(MoxProxyRule rule, MoxProxyProcessedTrafficEntry trafficEntry){
        return trafficEntry.getMethod().equalsIgnoreCase(rule.getMoxProxyHttpObject().getMethod())
                && isPathMatch(rule.getMoxProxyHttpObject().getPathPattern(), trafficEntry.getUrl());
    }

    private boolean isPathMatch(String pattern, String path){
        Pattern p = Pattern.compile(pattern);
        Matcher match = p.matcher(path);
        return match.find();
    }
}
