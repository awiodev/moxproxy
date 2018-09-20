package moxproxy.builders;

import moxproxy.dto.MoxProxyHeader;
import moxproxy.dto.MoxProxyHttpObject;

import java.util.List;

public class MoxProxyHttpObjectBuilder extends BaseBuilder<MoxProxyRuleBuilder, MoxProxyHttpObjectBuilder, MoxProxyHttpObject, MoxProxyHttpObjectBuilderValidator> {

    private String method;
    private String pathPattern;
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

    public MoxProxyHttpObjectBuilder withPathPattern(String path){
        this.pathPattern = path;
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

    String getMethod() {
        return method;
    }

    String getPathPattern() {
        return pathPattern;
    }

    String getBody() {
        return body;
    }

    MoxProxyHeadersCollectionBuilder getHeadersCollectionBuilder() {
        return headersCollectionBuilder;
    }

    int getStatusCode() {
        return statusCode;
    }

    @Override
    MoxProxyHttpObject performBuild() {
        List<MoxProxyHeader> headers = headersCollectionBuilder.build();
        var httpObject = new MoxProxyHttpObject();
        httpObject.setMethod(method);
        httpObject.setPath(pathPattern);
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
