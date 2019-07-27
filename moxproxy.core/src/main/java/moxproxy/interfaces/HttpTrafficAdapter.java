package moxproxy.interfaces;

import moxproxy.model.MoxProxyProcessedTrafficEntry;

public interface HttpTrafficAdapter {
    MoxProxyProcessedTrafficEntry trafficEntry();
}
