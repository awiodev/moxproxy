package moxproxy.client;

import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.http.ContentType;
import moxproxy.builders.MoxProxyClientBuilder;
import moxproxy.configuration.MoxProxyClientConfiguration;
import moxproxy.consts.MoxProxyRoutes;
import moxproxy.exceptions.MoxProxyClientException;
import moxproxy.interfaces.MoxProxyService;
import moxproxy.model.*;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.*;

public class MoxProxyClient implements MoxProxyService {

    public MoxProxyClient(MoxProxyClientConfiguration configuration){
        baseURI = configuration.getBaseUrl();

        PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();
        authScheme.setUserName(configuration.getUserName());
        authScheme.setPassword(configuration.getPassword());
        authentication = authScheme;

    }

    @Override
    public List<MoxProxyProcessedTrafficEntry> getSessionRequestTraffic(String sessionId) {
        String route = MoxProxyRoutes.API_ROUTE + MoxProxyRoutes.REQUESTS_ROUTE_SESSION;

        return Arrays.asList(
                when()
                    .get(route, sessionId)
                .then()
                    .statusCode(200)
                    .extract()
                        .as(MoxProxyProcessedTrafficEntry[].class));
    }

    @Override
    public List<MoxProxyProcessedTrafficEntry> getAllRequestTraffic() {
        String route = MoxProxyRoutes.API_ROUTE + MoxProxyRoutes.REQUESTS_ROUTE;

        return Arrays.asList(
                when()
                    .get(route)
                .then()
                    .statusCode(200)
                    .extract()
                        .as(MoxProxyProcessedTrafficEntry[].class));
    }

    @Override
    public List<MoxProxyProcessedTrafficEntry> getSessionResponseTraffic(String sessionId) {
        String route = MoxProxyRoutes.API_ROUTE + MoxProxyRoutes.RESPONSES_ROUTE_SESSION;

        return Arrays.asList(
                when()
                    .get(route, sessionId)
                .then()
                    .statusCode(200)
                    .extract()
                        .as(MoxProxyProcessedTrafficEntry[].class));
    }

    @Override
    public List<MoxProxyProcessedTrafficEntry> getAllResponseTraffic() {
        String route = MoxProxyRoutes.API_ROUTE + MoxProxyRoutes.RESPONSES_ROUTE;

        return Arrays.asList(
                when()
                    .get(route)
                .then()
                    .statusCode(200)
                    .extract()
                        .as(MoxProxyProcessedTrafficEntry[].class));
    }

    @Override
    public void cancelRule(String ruleId) {
        String route = MoxProxyRoutes.API_ROUTE + MoxProxyRoutes.RULES_ROUTE_ID;

        MoxProxyStatusResponse response =
                when()
                    .delete(route, ruleId)
                .then().statusCode(200)
                    .extract()
                        .as(MoxProxyStatusResponse.class);

        proxyStatusMessageShouldBe(response, MoxProxyStatusMessage.DELETED);
    }

    @Override
    public void clearSessionRules(String sessionId) {
        String route = MoxProxyRoutes.API_ROUTE + MoxProxyRoutes.RULES_SESSION_ID_ROUTE;

        MoxProxyStatusResponse response =
                when()
                    .delete(route, sessionId)
                .then().statusCode(200)
                    .extract()
                        .as(MoxProxyStatusResponse.class);

        proxyStatusMessageShouldBe(response, MoxProxyStatusMessage.DELETED);
    }

    @Override
    public void clearSessionEntries(String sessionId) {
        String route = MoxProxyRoutes.API_ROUTE + MoxProxyRoutes.SESSION_ID_ROUTE;

        MoxProxyStatusResponse response =
                when()
                    .delete(route, sessionId)
                .then().statusCode(200)
                    .extract()
                        .as(MoxProxyStatusResponse.class);

        proxyStatusMessageShouldBe(response, MoxProxyStatusMessage.DELETED);
    }

    @Override
    public void clearAllSessionEntries() {
        String route = MoxProxyRoutes.API_ROUTE + MoxProxyRoutes.SESSION_ROUTE;

        MoxProxyStatusResponse response =
                when()
                    .delete(route)
                .then().statusCode(200)
                    .extract()
                        .as(MoxProxyStatusResponse.class);

        proxyStatusMessageShouldBe(response, MoxProxyStatusMessage.DELETED);
    }

    @Override
    public String createRule(MoxProxyRule moxProxyRule) {
        String route = MoxProxyRoutes.API_ROUTE + MoxProxyRoutes.RULES_ROUTE;

        MoxProxyStatusResponse response =
                given()
                    .contentType(ContentType.JSON)
                    .body(moxProxyRule)
                    .post(route)
                .then()
                    .statusCode(201)
                    .extract()
                        .as(MoxProxyStatusResponse.class);

        proxyStatusMessageShouldBe(response, MoxProxyStatusMessage.CREATED);
        return response.getEntityId();
    }

    @Override
    public void modifySessionMatchingStrategy(MoxProxySessionIdMatchingStrategy matchingStrategy) {
        String route = MoxProxyRoutes.API_ROUTE + MoxProxyRoutes.SESSION_ROUTE_MATCH_STRATEGY;

        MoxProxyStatusResponse response =
                when()
                    .post(route, matchingStrategy)
                .then()
                    .statusCode(200)
                    .extract()
                        .as(MoxProxyStatusResponse.class);

        proxyStatusMessageShouldBe(response, MoxProxyStatusMessage.MODIFIED);
    }

    @Override
    public MoxProxySessionIdMatchingStrategy getSessionMatchingStrategy() {
        String route = MoxProxyRoutes.API_ROUTE + MoxProxyRoutes.SESSION_ROUTE_MATCH_STRATEGY;

        return
            when()
                .get(route)
            .then()
                .statusCode(200)
                .extract()
                    .as(MoxProxySessionIdMatchingStrategy.class);
    }

    private void proxyStatusMessageShouldBe(MoxProxyStatusResponse statusResponse, String expectedMessage){
        if(!statusResponse.getMessage().equals(expectedMessage)){
            throw new MoxProxyClientException("MoxProxy response message should be " + expectedMessage + " ,but was " + statusResponse.getMessage());
        }
    }

    public static MoxProxyClientBuilder builder(){
        return new MoxProxyClientBuilder();
    }
}
