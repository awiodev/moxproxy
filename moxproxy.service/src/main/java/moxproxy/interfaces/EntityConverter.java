package moxproxy.interfaces;

import moxproxy.dto.MoxProxyProcessedTrafficEntry;

public interface EntityConverter {

    MoxProxyProcessedTrafficEntry fromRequestAdapter(HttpRequestAdapter adapter);
    MoxProxyProcessedTrafficEntry fromResponseAdapter(HttpResponseAdapter adapter);
}
