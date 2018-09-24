package moxproxy.interfaces;

import moxproxy.dto.MoxProxyProcessedTrafficEntry;

public interface IEntityConverter {

    MoxProxyProcessedTrafficEntry fromRequestAdapter(IHttpRequestAdapter adapter);
    MoxProxyProcessedTrafficEntry fromResponseAdapter(IHttpResponseAdapter adapter);
}
