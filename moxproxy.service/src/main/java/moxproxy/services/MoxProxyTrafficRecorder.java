package moxproxy.services;

import moxproxy.dto.MoxProxyProcessedTrafficEntry;
import moxproxy.interfaces.IMoxProxyDatabase;
import moxproxy.interfaces.IMoxProxyTrafficRecorder;
import org.springframework.beans.factory.annotation.Autowired;

public class MoxProxyTrafficRecorder implements IMoxProxyTrafficRecorder {

    @Autowired
    IMoxProxyDatabase database;

    @Override
    public void recordRequest(MoxProxyProcessedTrafficEntry entry) {
        database.addProcessedRequest(entry);
    }

    @Override
    public void recordResponse(MoxProxyProcessedTrafficEntry entry) {
        database.addProcessedResponse(entry);
    }
}
