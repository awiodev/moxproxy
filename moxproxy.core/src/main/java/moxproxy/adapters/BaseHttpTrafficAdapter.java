package moxproxy.adapters;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import moxproxy.consts.MoxProxyConts;
import moxproxy.interfaces.HttpTrafficAdapter;
import moxproxy.model.MoxProxyHeader;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class BaseHttpTrafficAdapter implements HttpTrafficAdapter {

    private HttpObject httpObject;
    private List<MoxProxyHeader> headers;
    private String body;
    private String method;
    private String url;
    private String sessionId;
    private boolean isContentRead;
    String connectedUrl;
    HttpRequest originalRequest;
    boolean isSessionIdStrategy;

    BaseHttpTrafficAdapter(HttpObject httpObject, HttpRequest originalRequest, String connectedUrl, boolean isSessionIdStrategy, boolean isContentRead) {
        this.httpObject = httpObject;
        this.originalRequest = originalRequest;
        this.connectedUrl = connectedUrl;
        this.isSessionIdStrategy = isSessionIdStrategy;
        this.isContentRead = isContentRead;
        headers = transformToProxyHeaders(getHeaders());
        body = readContent();
        method = getMethod().name();
        url = getUrl();
        getSessionId();
    }

    HttpObject getHttpObject(){
        return httpObject;
    }

    protected abstract HttpHeaders getHeaders();
    protected abstract ByteBuf getContent();
    protected abstract HttpMethod getMethod();
    protected abstract String getUrl();

    protected abstract void getSessionId();

    List<MoxProxyHeader> headers() {
        return headers;
    }

    String body() {
        return body;
    }

    String method() {
        return method;
    }

    String url() {
        return url;
    }

    String sessionId() {
        return sessionId;
    }

    List<MoxProxyHeader> transformToProxyHeaders(HttpHeaders headers) {

        ArrayList<MoxProxyHeader> transformed = new ArrayList<>();
        headers.forEach(x -> transformed.add(transformToProxyHeader(x)));
        return transformed;
    }

    private MoxProxyHeader transformToProxyHeader(Map.Entry<String, String> header){
        return new MoxProxyHeader(header.getKey(), header.getValue());
    }

    private String readContent() {
        if(isContentRead){
            return readContent(getContent());
        }
        return null;
    }

    private String readContent(ByteBuf content) {
        if(null != content){
            return content.toString(StandardCharsets.UTF_8);
        }
        return MoxProxyConts.EMPTY_STRING;
    }

    void extractSessionId() {
        extractSessionId(headers);
    }

    void extractSessionId(List<MoxProxyHeader> headers) {
        sessionId = headers.stream().map(header -> extractSessionId(header.getValue().toString())).filter(Objects::nonNull).findFirst().orElse(sessionId);
    }

    private String extractSessionId(String value) {

        String pattern = "MOXSESSIONID=(.*?)(;|$)";
        Pattern regexPattern = Pattern.compile(pattern);
        Matcher matcher = regexPattern.matcher(value);
        if(matcher.find()){
            return matcher.group(1);
        }
        return null;
    }
}
