package moxproxy.adapters;

public interface IHttpResponseAdapter extends IHttpTrafficAdapter {

    int statusCode();
}