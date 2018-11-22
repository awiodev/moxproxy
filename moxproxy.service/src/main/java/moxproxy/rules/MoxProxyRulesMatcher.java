package moxproxy.rules;

import com.google.common.collect.Lists;
import moxproxy.dto.MoxProxyRule;
import moxproxy.enums.MoxProxyDirection;
import moxproxy.interfaces.IHttpTrafficAdapter;
import moxproxy.interfaces.IMoxProxyDatabase;
import moxproxy.interfaces.IMoxProxyRulesMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class MoxProxyRulesMatcher implements IMoxProxyRulesMatcher {

    @Autowired
    private IMoxProxyDatabase database;

    private boolean matchSessionId;

    @Override
    public void useSessionIdMatchingStrategy(boolean value) {
        matchSessionId = value;
    }

    @Override
    public boolean getSessionIdMatchingStrategy() {
        return matchSessionId;
    }

    @Override
    public List<MoxProxyRule> match(IHttpTrafficAdapter adapter, MoxProxyDirection moxProxyDirection) {
        var matched = new ArrayList<MoxProxyRule>();
        for(var rule : getRules(adapter, moxProxyDirection)){
            if(match(rule, adapter)){
                matched.add(rule);
            }
        }

        return matched;
    }

    private List<MoxProxyRule> getRules(IHttpTrafficAdapter adapter, MoxProxyDirection moxProxyDirection){
        var result = matchSessionId ? Lists.newArrayList(database.findRulesBySessionId(adapter.sessionId()))
                : Lists.newArrayList(database.getAllRules());
        return result.stream().filter(x -> x.getHttpDirection() == moxProxyDirection).collect(Collectors.toList());
    }

    private boolean match(MoxProxyRule rule, IHttpTrafficAdapter adapter){
        if(adapter.method().equalsIgnoreCase(rule.getMoxProxyHttpObject().getMethod())
                && isPathMatch(rule.getMoxProxyHttpObject().getPathPattern(), adapter.url())){
            return true;
        }
        return false;
    }

    private boolean isPathMatch(String pattern, String path){
        Pattern p = Pattern.compile(pattern);
        Matcher match = p.matcher(path);
        if(match.find()){
            return true;
        }
        return false;
    }
}
