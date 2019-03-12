package moxproxy.services;

import moxproxy.interfaces.MoxProxyDatabase;
import moxproxy.interfaces.MoxProxyServiceConfiguration;
import moxproxy.interfaces.MoxProxyTrafficRecorder;
import moxproxy.model.MoxProxyProcessedTrafficEntry;

import javax.inject.Inject;
import java.util.List;

public class MoxProxyTrafficRecorderImpl implements MoxProxyTrafficRecorder {

    private MoxProxyServiceConfiguration configuration;
    private MoxProxyDatabase database;

    @Inject
    public MoxProxyTrafficRecorderImpl(MoxProxyServiceConfiguration configuration, MoxProxyDatabase database){
        this.configuration = configuration;
        this.database = database;
    }

    @Override
    public void recordRequest(MoxProxyProcessedTrafficEntry entry) {
        if(shouldBeRecorded(entry.getUrl())){
            takeBodyRecordDecision(entry);
            database.addProcessedRequest(entry);
        }
    }

    @Override
    public void recordResponse(MoxProxyProcessedTrafficEntry entry) {
        if(shouldBeRecorded(entry.getUrl())){
            takeBodyRecordDecision(entry);
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

    private void takeBodyRecordDecision(MoxProxyProcessedTrafficEntry moxProxyProcessedTrafficEntry){
        if(!configuration.isRecordBodies()){
            moxProxyProcessedTrafficEntry.setBody(null);
        }
    }
}
