package moxproxy.model;

public class MoxProxyMatchingStrategy {

    private boolean useSessionId;
    private boolean useMethod;
    private boolean usePath;

    public boolean isUseSessionId() {
        return useSessionId;
    }

    public void setUseSessionId(boolean useSessionId) {
        this.useSessionId = useSessionId;
    }

    public boolean isUseMethod() {
        return useMethod;
    }

    public void setUseMethod(boolean useMethod) {
        this.useMethod = useMethod;
    }

    public boolean isUsePath() {
        return usePath;
    }

    public void setUsePath(boolean usePath) {
        this.usePath = usePath;
    }
}
