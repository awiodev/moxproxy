package moxproxy.interfaces;

import moxproxy.model.MoxProxyProcessedTrafficEntry;

public interface MoxProxyTrafficRecorder {

    void recordRequest(MoxProxyProcessedTrafficEntry entry);
    void recordResponse(MoxProxyProcessedTrafficEntry entry);

    int requestSize();
}
