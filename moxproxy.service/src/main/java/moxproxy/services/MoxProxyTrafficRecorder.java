package moxproxy.services;

import com.google.common.collect.Lists;
import moxproxy.dto.MoxProxyProcessedTrafficEntry;
import moxproxy.interfaces.IMoxProxyDatabase;
import moxproxy.interfaces.IMoxProxyServiceConfiguration;
import moxproxy.interfaces.IMoxProxyTrafficRecorder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MoxProxyTrafficRecorder implements IMoxProxyTrafficRecorder {

    @Autowired
    IMoxProxyServiceConfiguration configuration;

    @Autowired
    IMoxProxyDatabase database;

    List<String> whiteList;

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
        if(whiteList == null){
            whiteList = Lists.newArrayList(configuration.getUrlWhiteListForTrafficRecorder());
        }
        if(whiteList.size() > 0){
            return whiteList.parallelStream().anyMatch(url::contains);
        }
        return true;
    }
}
