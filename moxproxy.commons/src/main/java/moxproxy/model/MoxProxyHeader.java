package moxproxy.model;

public class MoxProxyHeader {

    private String name;
    private Object value;

    public MoxProxyHeader() {}

    public MoxProxyHeader(String name, Object value){
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

}
