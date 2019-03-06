package moxproxy.converters;

import moxproxy.model.MoxProxyProcessedTrafficEntry;
import moxproxy.interfaces.EntityConverter;
import moxproxy.interfaces.HttpRequestAdapter;
import moxproxy.interfaces.HttpResponseAdapter;
import moxproxy.interfaces.HttpTrafficAdapter;

public class EntityConverterImpl implements EntityConverter {


    @Override
    public MoxProxyProcessedTrafficEntry fromRequestAdapter(HttpRequestAdapter adapter) {
        return convertFromHttpTrafficAdapter(adapter);
    }

    @Override
    public MoxProxyProcessedTrafficEntry fromResponseAdapter(HttpResponseAdapter adapter) {
        var fromAdapter = convertFromHttpTrafficAdapter(adapter);
        fromAdapter.setStatusCode(adapter.statusCode());
        return fromAdapter;
    }

    private MoxProxyProcessedTrafficEntry convertFromHttpTrafficAdapter(HttpTrafficAdapter adapter){
        var processedRequest = new MoxProxyProcessedTrafficEntry();
        processedRequest.setSessionId(adapter.sessionId());
        processedRequest.setMethod(adapter.method());
        processedRequest.setUrl(adapter.url());
        processedRequest.setBody(adapter.body());
        processedRequest.setHeaders(adapter.headers());
        return processedRequest;
    }
}
