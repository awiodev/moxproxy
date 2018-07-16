package services;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import dto.MoxProxyProcessedTrafficEntry;
import interfaces.IMoxProxyDatabase;
import interfaces.IProxyServiceConfiguration;
import rules.MoxProxyRule;

import java.util.Date;

public class MoxProxyDatabase implements IMoxProxyDatabase {

    private final static String DB_NAME = "MOX_PROXY";
    private final static String RULES_COLLECTION_NAME = "RULES";
    private final static String TRAFFIC_COLLECTION_NAME = "TRAFFIC";

    private IProxyServiceConfiguration configuration;

    private MongoDatabase database;

    public MoxProxyDatabase(IProxyServiceConfiguration configuration){
        this.configuration = configuration;
    }

    @Override
    public void initDatabase() {
        var mongoClient = new MongoClient("localhost", configuration.getMongoDbPort());
        database = mongoClient.getDatabase(DB_NAME);
        database.createCollection(RULES_COLLECTION_NAME);
        database.createCollection(TRAFFIC_COLLECTION_NAME);
    }

    @Override
    public void stopDatabase() {
        database.drop();
    }

    @Override
    public void cleanProcessedTraffic(String sessionId) {

    }

    @Override
    public void cleanProcessedTraffic(Date olderThan) {

    }

    @Override
    public void cleanRule(String ruleId) {

    }

    @Override
    public void cleanAllProcessedTraffic() {

    }

    @Override
    public void cleanAllRules() {

    }

    @Override
    public void cleanRules(String sessionId) {

    }

    @Override
    public String addRule(MoxProxyRule moxProxyRule) {
        return null;
    }

    @Override
    public void addProcessedRequest(MoxProxyProcessedTrafficEntry moxProxyProcessedTrafficEntry) {

    }

    @Override
    public Iterable<MoxProxyProcessedTrafficEntry> getProcessedTraffic() {
        return null;
    }

    @Override
    public Iterable<MoxProxyProcessedTrafficEntry> getProcessedTraffic(String sessionId) {
        return null;
    }

    @Override
    public MoxProxyRule findRuleBySessionId(String sessionId) {
        return null;
    }

    @Override
    public MoxProxyRule findRuleByById(String id) {
        return null;
    }
}
