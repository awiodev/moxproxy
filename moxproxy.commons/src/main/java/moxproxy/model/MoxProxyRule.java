package moxproxy.model;

import moxproxy.builders.MoxProxyRuleBuilder;
import moxproxy.enums.MoxProxyAction;
import moxproxy.enums.MoxProxyDirection;

import java.util.Date;
import java.util.UUID;

public class MoxProxyRule {

    private String sessionId;
    private String id;
    private MoxProxyDirection direction;
    private MoxProxyHttpRuleDefinition moxProxyHttpObject;
    private MoxProxyAction moxProxyAction;
    private Date date;
    //private MoxProxyMatchingStrategy moxProxyMatchingStrategy;

    public MoxProxyRule(){
        id = UUID.randomUUID().toString();
        date = new Date();
    }

    public Date getDate() {
        return date;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getId() {
        return id;
    }

    public MoxProxyDirection getHttpDirection() {
        return direction;
    }

    public void setHttpDirection(MoxProxyDirection htmlObjectType) {
        this.direction = htmlObjectType;
    }

    public MoxProxyHttpRuleDefinition getMoxProxyHttpObject() {
        return moxProxyHttpObject;
    }

    public void setMoxProxyHttpObject(MoxProxyHttpRuleDefinition moxProxyHttpObject) {
        this.moxProxyHttpObject = moxProxyHttpObject;
    }

    public MoxProxyAction getAction() {
        return moxProxyAction;
    }

    public void setAction(MoxProxyAction moxProxyAction) {
        this.moxProxyAction = moxProxyAction;
    }

    public static MoxProxyRuleBuilder builder(){
        return new MoxProxyRuleBuilder();
    }
}
