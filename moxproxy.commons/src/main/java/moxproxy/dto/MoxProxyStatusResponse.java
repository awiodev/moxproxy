package moxproxy.dto;

public class MoxProxyStatusResponse {

    private String message;

    public MoxProxyStatusResponse(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
