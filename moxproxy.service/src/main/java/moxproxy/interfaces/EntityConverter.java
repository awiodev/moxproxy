package moxproxy.interfaces;

import moxproxy.model.MoxProxyProcessedTrafficEntry;

public interface EntityConverter {

    MoxProxyProcessedTrafficEntry fromRequestAdapter(HttpRequestAdapter adapter);
    MoxProxyProcessedTrafficEntry fromResponseAdapter(HttpResponseAdapter adapter);
}
