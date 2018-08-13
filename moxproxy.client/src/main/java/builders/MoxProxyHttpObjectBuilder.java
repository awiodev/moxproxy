package builders;

import dto.MoxProxyHeader;
import dto.MoxProxyHttpObject;

import java.util.List;

public class MoxProxyHttpObjectBuilder extends ChildBuilder<MoxProxyRuleBuilder, MoxProxyHttpObject> {

    private String method;
    private String path;
    private String body;
    private List<MoxProxyHeader> headers;
    private int statusCode;

    MoxProxyHttpObjectBuilder(MoxProxyRuleBuilder moxProxyRuleBuilder) {
        super(moxProxyRuleBuilder);
    }

    public MoxProxyHttpObjectBuilder withMethod(String method){
        this.method = method;
        return this;
    }

    public MoxProxyHttpObjectBuilder withPath(String path){
        this.path = path;
        return this;
    }

    public MoxProxyHttpObjectBuilder withStatusCode(int statusCode){
        this.statusCode = statusCode;
        return this;
    }

    @Override
    public MoxProxyHttpObject build() {
        var httpObject = new MoxProxyHttpObject();
        httpObject.setMethod(method);
        httpObject.setPath(path);
        httpObject.setStatusCode(statusCode);
        httpObject.setBody(body);
        //TODO collection build
        return httpObject;
    }
}
