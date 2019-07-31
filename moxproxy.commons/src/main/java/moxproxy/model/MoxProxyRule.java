package moxproxy.model;

import moxproxy.builders.MoxProxyRuleBuilder;
import moxproxy.enums.MoxProxyAction;
import moxproxy.enums.MoxProxyDirection;

public class MoxProxyRule extends MoxProxyTransportEntity {

    private String sessionId;
    private MoxProxyDirection direction;
    private MoxProxyHttpRuleDefinition httpObject;
    private MoxProxyAction action;
    private int invokeLimit;

    public MoxProxyRule(){}

    public MoxProxyRule(String sessionId, MoxProxyDirection direction, MoxProxyHttpRuleDefinition httpObject, MoxProxyAction action, int invokeLimit) {
        this.sessionId = sessionId;
        this.direction = direction;
        this.httpObject = httpObject;
        this.action = action;
        this.invokeLimit = invokeLimit;
    }

    public String getSessionId() {
        return sessionId;
    }

    public MoxProxyDirection getDirection() {
        return direction;
    }

    public MoxProxyHttpRuleDefinition getHttpObject() {
        return httpObject;
    }

    public MoxProxyAction getAction() {
        return action;
    }

    public static MoxProxyRuleBuilder builder(){
        return new MoxProxyRuleBuilder();
    }

    public int getInvokeLimit() {
        return invokeLimit;
    }

    public void handleInvoke(){
        if(invokeLimit > 0){
            invokeLimit--;
        }
    }
}
