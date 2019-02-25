package moxproxy.interfaces;

import moxproxy.dto.MoxProxyProcessedTrafficEntry;

public interface MoxProxyTrafficRecorder {

    void recordRequest(MoxProxyProcessedTrafficEntry entry);
    void recordResponse(MoxProxyProcessedTrafficEntry entry);
}
