package moxproxy.rules;

import com.google.common.collect.Lists;
import moxproxy.enums.MoxProxyDirection;
import moxproxy.interfaces.HttpTrafficAdapter;
import moxproxy.interfaces.MoxProxyDatabase;
import moxproxy.interfaces.MoxProxyRulesMatcher;
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
    public List<MoxProxyRule> match(HttpTrafficAdapter adapter, MoxProxyDirection moxProxyDirection) {
        ArrayList<MoxProxyRule> matched = getRules(adapter, moxProxyDirection)
                .stream().filter(rule -> match(rule, adapter)).collect(Collectors.toCollection(ArrayList::new));

        return matched;
    }

    private List<MoxProxyRule> getRules(HttpTrafficAdapter adapter, MoxProxyDirection moxProxyDirection){
        ArrayList<MoxProxyRule> result = matchSessionId ? Lists.newArrayList(database.findRulesBySessionId(adapter.sessionId()))
                : Lists.newArrayList(database.getAllRules());
        return result.stream().filter(x -> x.getHttpDirection() == moxProxyDirection).collect(Collectors.toList());
    }

    private boolean match(MoxProxyRule rule, HttpTrafficAdapter adapter){
        return adapter.method().equalsIgnoreCase(rule.getMoxProxyHttpObject().getMethod())
                && isPathMatch(rule.getMoxProxyHttpObject().getPathPattern(), adapter.url());
    }

    private boolean isPathMatch(String pattern, String path){
        Pattern p = Pattern.compile(pattern);
        Matcher match = p.matcher(path);
        return match.find();
    }
}
