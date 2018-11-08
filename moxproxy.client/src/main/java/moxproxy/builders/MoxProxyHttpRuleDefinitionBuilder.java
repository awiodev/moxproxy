package moxproxy.builders;

import moxproxy.consts.MoxProxyConts;
import moxproxy.dto.MoxProxyHeader;
import moxproxy.dto.MoxProxyHttpRuleDefinition;

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
    MoxProxyHttpRuleDefinition performBuild() {
        List<MoxProxyHeader> headers = headersCollectionBuilder.build();
        var httpObject = new MoxProxyHttpRuleDefinition();
        httpObject.setMethod(method);
        httpObject.setPathPattern(pathPattern);
        httpObject.setStatusCode(statusCode);
        httpObject.setBody(body);
        httpObject.setHeaders(headers);
        return httpObject;
    }

    @Override
    MoxProxyHttpRuleDefinitionBuilder getCurrentBuilder() {
        return this;
    }

}
