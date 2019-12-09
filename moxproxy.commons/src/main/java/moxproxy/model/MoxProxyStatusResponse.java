package moxproxy.model;

public class MoxProxyStatusResponse {

    private String entityId;
    private String message;

    public MoxProxyStatusResponse(){}

    public MoxProxyStatusResponse(String entityId, String message) {
        this.entityId = entityId;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getEntityId() {
        return entityId;
    }
}
