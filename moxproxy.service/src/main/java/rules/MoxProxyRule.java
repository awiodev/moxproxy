package rules;

import dto.MoxProxyHttpObject;

import java.util.Date;
import java.util.UUID;

public class MoxProxyRule {

    String sessionId;
    String id;
    MoxProxyHtmlObjectType htmlObjectType;
    MoxProxyHttpObject moxProxyHttpObject;
    Date date;

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

    public MoxProxyHtmlObjectType getHtmlObjectType() {
        return htmlObjectType;
    }

    public void setHtmlObjectType(MoxProxyHtmlObjectType htmlObjectType) {
        this.htmlObjectType = htmlObjectType;
    }

    public MoxProxyHttpObject getMoxProxyHttpObject() {
        return moxProxyHttpObject;
    }

    public void setMoxProxyHttpObject(MoxProxyHttpObject moxProxyHttpObject) {
        this.moxProxyHttpObject = moxProxyHttpObject;
    }
}
