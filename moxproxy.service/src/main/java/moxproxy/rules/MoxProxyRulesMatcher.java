package moxproxy.rules;

import moxproxy.adapters.IHttpTrafficAdapter;
import moxproxy.dto.MoxProxyRule;
import moxproxy.interfaces.IMoxProxyDatabase;
import moxproxy.interfaces.IMoxProxyRulesMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MoxProxyRulesMatcher implements IMoxProxyRulesMatcher {

    @Autowired
    private IMoxProxyDatabase database;
    
    @Override
    public List<MoxProxyRule> match(IHttpTrafficAdapter adapter) {
        var matched = new ArrayList<MoxProxyRule>();
        
        return matched;
    }
}
