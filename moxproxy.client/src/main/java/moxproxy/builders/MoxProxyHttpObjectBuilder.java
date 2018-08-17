package moxproxy.builders;

import moxproxy.dto.MoxProxyHeader;
import moxproxy.dto.MoxProxyHttpObject;
import moxproxy.validators.MoxProxyHttpObjectBuilderValidator;

public class MoxProxyHttpObjectBuilder extends BaseBuilder<MoxProxyRuleBuilder, MoxProxyHttpObjectBuilder, MoxProxyHttpObject, MoxProxyHttpObjectBuilderValidator> {

    private String method;
    private String path;
    private String body;
    private MoxProxyHeadersCollectionBuilder headersCollectionBuilder;
    private int statusCode;

    MoxProxyHttpObjectBuilder(MoxProxyRuleBuilder moxProxyRuleBuilder) {
        super(moxProxyRuleBuilder, new MoxProxyHttpObjectBuilderValidator());
        headersCollectionBuilder = new MoxProxyHeadersCollectionBuilder(this);
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

    public MoxProxyHttpObjectBuilder withBody(String body){
        this.body = body;
        return this;
    }

    public MoxProxyHeadersCollectionBuilder havingHeaders(){
        return headersCollectionBuilder;
    }


    @Override
    MoxProxyHttpObject performBuild() {
        Iterable<MoxProxyHeader> headers = headersCollectionBuilder.build();
        var httpObject = new MoxProxyHttpObject();
        httpObject.setMethod(method);
        httpObject.setPath(path);
        httpObject.setStatusCode(statusCode);
        httpObject.setBody(body);
        httpObject.setHeaders(headers);
        return httpObject;
    }

    @Override
    MoxProxyHttpObjectBuilder getCurrentBuilder() {
        return this;
    }

}
