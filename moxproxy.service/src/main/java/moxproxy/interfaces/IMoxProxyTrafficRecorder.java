package moxproxy.interfaces;

import moxproxy.dto.MoxProxyProcessedTrafficEntry;

public interface IMoxProxyTrafficRecorder {

    void recordRequest(MoxProxyProcessedTrafficEntry entry);
    void recordResponse(MoxProxyProcessedTrafficEntry entry);
}
