package moxproxy.adapters;

import moxproxy.dto.MoxProxyHeader;

import java.util.List;

public interface IHttpTrafficAdapter {

    List<MoxProxyHeader> headers();
    String body();
    String method();
    String url();
    String sessionId();
}
