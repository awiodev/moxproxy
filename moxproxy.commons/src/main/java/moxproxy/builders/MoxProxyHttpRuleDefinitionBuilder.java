package moxproxy.builders;

import moxproxy.consts.MoxProxyConts;
import moxproxy.model.MoxProxyHeader;
import moxproxy.model.MoxProxyHttpRuleDefinition;

import java.util.List;

public class MoxProxyHttpRuleDefinitionBuilder extends BaseBuilder<MoxProxyRuleBuilder, MoxProxyHttpRuleDefinitionBuilder, MoxProxyHttpRuleDefinition, MoxProxyHttpRuleDefinitionBuilderValidator> {

    private String method;
    private String pathPattern;
    private String body;
    private MoxProxyHeadersCollectionBuilder headersCollectionBuilder;
    private int statusCode;

    MoxProxyHttpRuleDefinitionBuilder(MoxProxyRuleBuilder moxProxyRuleBuilder) {
        super(moxProxyRuleBuilder, new MoxProxyHttpRuleDefinitionBuilderValidator());
        headersCollectionBuilder = new MoxProxyHeadersCollectionBuilder(this);
    }

    public MoxProxyHttpRuleDefinitionBuilder withMethod(String method){
        this.method = method;
        return this;
    }

    public MoxProxyHttpRuleDefinitionBuilder withGetMethod(){
        this.method = "Get";
        return this;
    }

    public MoxProxyHttpRuleDefinitionBuilder withPostMethod(){
        this.method = "Post";
        return this;
    }

    public MoxProxyHttpRuleDefinitionBuilder withDeleteMethod(){
        this.method = "Delete";
        return this;
    }

    public MoxProxyHttpRuleDefinitionBuilder withPutMethod(){
        this.method = "Put";
        return this;
    }

    public MoxProxyHttpRuleDefinitionBuilder withPathPattern(String path){
        this.pathPattern = path;
        return this;
    }

    public MoxProxyHttpRuleDefinitionBuilder withStatusCode(int statusCode){
        this.statusCode = statusCode;
        return this;
    }

    public MoxProxyHttpRuleDefinitionBuilder withBody(String body){
        this.body = body;
        return this;
    }

    public MoxProxyHttpRuleDefinitionBuilder withDeleteBody(){
        this.body = MoxProxyConts.DELETE_BODY_INDICATOR;
        return this;
    }

    public MoxProxyHeaderBuilder havingHeaders(){
        return headersCollectionBuilder.addItem();
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
    protected MoxProxyHttpRuleDefinition performBuild() {
        List<MoxProxyHeader> headers = headersCollectionBuilder.build();
        MoxProxyHttpRuleDefinition httpObject = new MoxProxyHttpRuleDefinition();
        httpObject.setMethod(method);
        httpObject.setPathPattern(pathPattern);
        httpObject.setStatusCode(statusCode);
        httpObject.setBody(body);
        httpObject.setHeaders(headers);
        return httpObject;
    }

    @Override
    protected MoxProxyHttpRuleDefinitionBuilder getCurrentBuilder() {
        return this;
    }

}
