package moxproxy.model;

import java.time.OffsetDateTime;

public abstract class MoxProxyTransportEntity {
    private String id;
    private OffsetDateTime timestamp;

    public void updateEntity(String id, OffsetDateTime timestamp){
        this.id = id;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }
}
