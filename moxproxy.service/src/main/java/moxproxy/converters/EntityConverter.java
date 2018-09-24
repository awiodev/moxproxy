package moxproxy.converters;

import moxproxy.dto.MoxProxyProcessedTrafficEntry;
import moxproxy.interfaces.IEntityConverter;
import moxproxy.interfaces.IHttpRequestAdapter;
import moxproxy.interfaces.IHttpResponseAdapter;
import moxproxy.interfaces.IHttpTrafficAdapter;

public class EntityConverter implements IEntityConverter {


    @Override
    public MoxProxyProcessedTrafficEntry fromRequestAdapter(IHttpRequestAdapter adapter) {
        return convertFromHttpTrafficAdapter(adapter);
    }

    @Override
    public MoxProxyProcessedTrafficEntry fromResponseAdapter(IHttpResponseAdapter adapter) {
        var fromAdapter = convertFromHttpTrafficAdapter(adapter);
        fromAdapter.setStatusCode(adapter.statusCode());
        return fromAdapter;
    }

    private MoxProxyProcessedTrafficEntry convertFromHttpTrafficAdapter(IHttpTrafficAdapter adapter){
        var processedRequest = new MoxProxyProcessedTrafficEntry();
        processedRequest.setSessionId(adapter.sessionId());
        processedRequest.setMethod(adapter.method());
        processedRequest.setUrl(adapter.url());
        processedRequest.setBody(adapter.body());
        processedRequest.setHeaders(adapter.headers());
        return processedRequest;
    }
}
