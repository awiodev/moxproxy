package moxproxy.services;

import moxproxy.model.MoxProxyProcessedTrafficEntry;
import moxproxy.interfaces.MoxProxyDatabase;
import moxproxy.interfaces.MoxProxyServiceConfiguration;
import moxproxy.interfaces.MoxProxyTrafficRecorder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MoxProxyTrafficRecorderImpl implements MoxProxyTrafficRecorder {

    @Autowired
    MoxProxyServiceConfiguration configuration;

    @Autowired
    MoxProxyDatabase database;

    @Override
    public void recordRequest(MoxProxyProcessedTrafficEntry entry) {
        if(shouldBeRecorded(entry.getUrl())){
            database.addProcessedRequest(entry);
        }
    }

    @Override
    public void recordResponse(MoxProxyProcessedTrafficEntry entry) {
        if(shouldBeRecorded(entry.getUrl())){
            database.addProcessedResponse(entry);
        }
    }

    private boolean shouldBeRecorded(String url){
        List<String> configWhiteList = configuration.getUrlWhiteListForTrafficRecorder();
        if(configWhiteList != null && !configWhiteList.isEmpty()){
            return configWhiteList.stream().anyMatch(url::contains);
        }
        return true;
    }
}
