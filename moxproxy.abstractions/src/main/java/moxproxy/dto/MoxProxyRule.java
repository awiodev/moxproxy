package moxproxy.dto;

import moxproxy.enums.MoxProxyDirection;

import java.util.Date;
import java.util.UUID;

public class MoxProxyRule {

    private String sessionId;
    private String id;
    private MoxProxyDirection direction;
    private MoxProxyHttpObject moxProxyHttpObject;
    private Date date;

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

    public MoxProxyHttpObject getMoxProxyHttpObject() {
        return moxProxyHttpObject;
    }

    public void setMoxProxyHttpObject(MoxProxyHttpObject moxProxyHttpObject) {
        this.moxProxyHttpObject = moxProxyHttpObject;
    }
}
