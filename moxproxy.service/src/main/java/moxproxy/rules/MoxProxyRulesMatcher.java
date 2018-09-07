package moxproxy.rules;

import moxproxy.dto.MoxProxyRule;
import moxproxy.interfaces.IMoxProxyDatabase;
import moxproxy.interfaces.IMoxProxyRulesMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MoxProxyRulesMatcher implements IMoxProxyRulesMatcher {

    @Autowired
    private IMoxProxyDatabase database;

}
