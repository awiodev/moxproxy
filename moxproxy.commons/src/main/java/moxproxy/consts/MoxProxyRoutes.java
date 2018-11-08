package moxproxy.consts;

public final class MoxProxyRoutes {

    public static final String API_ROUTE = "/api/rest";
    public static final String REQUESTS_ROUTE = "/requests";
    public static final String REQUESTS_ROUTE_SESSION = REQUESTS_ROUTE + "/{sessionId}";
    public static final String RESPONSES_ROUTE = "/responses";
    public static final String RESPONSES_ROUTE_SESSION = RESPONSES_ROUTE + "/{sessionId}";
    public static final String SESSION_ROUTE = "/session";
    public static final String SESSION_ROUTE_ENABLE_ID_MATCH = SESSION_ROUTE + "/idEnable";
    public static final String SESSION_ROUTE_DISABLE_ID_MATCH = SESSION_ROUTE + "/idDisable";
    public static final String SESSION_ID_ROUTE = SESSION_ROUTE + "/{sessionId}";
    public static final String RULES_ROUTE = "/rules";
    public static final String RULES_ROUTE_ID = RULES_ROUTE + "/{ruleId}";
    public static final String RULES_SESSION_ID_ROUTE = RULES_ROUTE + SESSION_ROUTE + "/{sessionId}";
}
