package testing.e2e;

import moxproxy.builders.LocalMoxProxy;
import moxproxy.enums.MoxProxyAction;
import moxproxy.enums.MoxProxyDirection;
import moxproxy.interfaces.MoxProxy;
import moxproxy.model.MoxProxyProcessedTrafficEntry;
import moxproxy.model.MoxProxyRule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class LocalProxySessionMatchTest extends TestBase {

    private static final String MY_BODY = "MY_BODY";
    private static MoxProxy proxy;
    private WebDriver driver;
    private String sessionId;

    @BeforeAll
    static void beforeAll() {
        proxy = LocalMoxProxy.builder()
                .withPort(PROXY_PORT)
                .withRecorderWhiteList(Collections.singletonList(WIKIPEDIA))
                .withSessionIdMatchStrategy()
                .build();
    }

    @BeforeEach
    void before() {
        proxy.startServer();
        driver = new FirefoxDriver(setupFirefox());
        driver.get(WIKI_URL);
        sessionId = UUID.randomUUID().toString();
        Cookie cookie = new Cookie("MOXSESSIONID", sessionId);
        driver.manage().addCookie(cookie);
    }

    @AfterEach
    void after() {
        driver.quit();
        proxy.stopServer();
    }

    @Test
    void whenRuleWithoutSession_thenRuleNotApplied() throws InterruptedException {

        MoxProxyRule rule = MoxProxyRule.builder()
                .withDirection(MoxProxyDirection.REQUEST)
                .withAction(MoxProxyAction.RESPOND)
                .withHttpRuleDefinition()
                .withGetMethod()
                .withPathPattern(WIKIPEDIA_ORG_PATTERN)
                .withStatusCode(500)
                .withBody(MY_BODY)
                .havingHeaders()
                .withHeader("content-type", "text/html; charset=utf-8")
                .withHeader("content-length", MY_BODY.length())
                .backToParent()
                .backToParent().build();

        proxy.createRule(rule);

        driver.get(WIKI_URL);

        Thread.sleep(1000);

        assertThat(driver.getPageSource()).doesNotContain(MY_BODY);
    }

    @Test
    void whenRuleWithDifferentSession_thenRuleNotApplied() throws InterruptedException {

        MoxProxyRule rule = MoxProxyRule.builder()
                .withDirection(MoxProxyDirection.REQUEST)
                .withAction(MoxProxyAction.RESPOND)
                .withSessionId("12342342-sdfsdfs-1233123123")
                .withHttpRuleDefinition()
                .withGetMethod()
                .withPathPattern(WIKIPEDIA_ORG_PATTERN)
                .withStatusCode(500)
                .withBody(MY_BODY)
                .havingHeaders()
                .withHeader("content-type", "text/html; charset=utf-8")
                .withHeader("content-length", MY_BODY.length())
                .backToParent()
                .backToParent().build();

        proxy.createRule(rule);

        driver.get(WIKI_URL);

        Thread.sleep(1000);

        assertThat(driver.getPageSource()).doesNotContain(MY_BODY);
    }

    @Test
    void whenRuleWithSession_thenRuleApplied() throws InterruptedException {

        MoxProxyRule rule = MoxProxyRule.builder()
                .withDirection(MoxProxyDirection.REQUEST)
                .withAction(MoxProxyAction.RESPOND)
                .withSessionId(sessionId)
                .withHttpRuleDefinition()
                .withGetMethod()
                .withPathPattern(WIKIPEDIA_ORG_PATTERN)
                .withStatusCode(500)
                .withBody(MY_BODY)
                .havingHeaders()
                .withHeader("content-type", "text/html; charset=utf-8")
                .withHeader("content-length", MY_BODY.length())
                .backToParent()
                .backToParent().build();

        proxy.createRule(rule);

        driver.get(WIKI_URL);

        Thread.sleep(1000);

        assertThat(driver.getPageSource()).contains(MY_BODY);
    }

    @Test
    void whenRuleCanceled_thenRuleNotApplied() throws InterruptedException {

        MoxProxyRule rule = MoxProxyRule.builder()
                .withDirection(MoxProxyDirection.REQUEST)
                .withAction(MoxProxyAction.RESPOND)
                .withSessionId(sessionId)
                .withHttpRuleDefinition()
                .withGetMethod()
                .withPathPattern(WIKIPEDIA_ORG_PATTERN)
                .withStatusCode(500)
                .withBody(MY_BODY)
                .havingHeaders()
                .withHeader("content-type", "text/html; charset=utf-8")
                .withHeader("content-length", MY_BODY.length())
                .backToParent()
                .backToParent().build();

        String ruleId = proxy.createRule(rule);

        driver.get(WIKI_URL);

        Thread.sleep(1000);

        assertThat(driver.getPageSource()).contains(MY_BODY);

        proxy.cancelRule(ruleId);

        driver.get(WIKI_URL);

        Thread.sleep(1000);

        assertThat(driver.getPageSource()).doesNotContain(MY_BODY);
    }

    @Test
    void whenSessionRules_thenRuleNotApplied() throws InterruptedException {

        MoxProxyRule rule = MoxProxyRule.builder()
                .withDirection(MoxProxyDirection.REQUEST)
                .withAction(MoxProxyAction.RESPOND)
                .withSessionId(sessionId)
                .withHttpRuleDefinition()
                .withGetMethod()
                .withPathPattern(WIKIPEDIA_ORG_PATTERN)
                .withStatusCode(500)
                .withBody(MY_BODY)
                .havingHeaders()
                .withHeader("content-type", "text/html; charset=utf-8")
                .withHeader("content-length", MY_BODY.length())
                .backToParent()
                .backToParent().build();

        proxy.createRule(rule);

        driver.get(WIKI_URL);

        Thread.sleep(1000);

        assertThat(driver.getPageSource()).contains(MY_BODY);

        proxy.clearSessionRules(sessionId);

        driver.get(WIKI_URL);

        Thread.sleep(1000);

        assertThat(driver.getPageSource()).doesNotContain(MY_BODY);
    }

    @Test
    void whenCollectTraffic_thenSessionTrafficCollected() throws InterruptedException {
        driver.get(WIKI_URL);

        Thread.sleep(1000);

        List<MoxProxyProcessedTrafficEntry> requestTraffic = proxy.getSessionRequestTraffic(sessionId);
        List<MoxProxyProcessedTrafficEntry> responseTraffic = proxy.getSessionResponseTraffic(sessionId);

        assertThat(requestTraffic).isNotEmpty();
        assertThat(responseTraffic).isNotEmpty();
        assertThat(requestTraffic.stream().allMatch(x -> x.getUrl().contains(WIKIPEDIA))).isTrue();
        assertThat(responseTraffic.stream().allMatch(x -> x.getUrl().contains(WIKIPEDIA))).isTrue();
    }

    @Test
    void whenClearSessionTraffic_thenNoEntries() throws InterruptedException {
        driver.get(WIKI_URL);

        Thread.sleep(1000);

        List<MoxProxyProcessedTrafficEntry> requestTraffic = proxy.getSessionRequestTraffic(sessionId);
        List<MoxProxyProcessedTrafficEntry> responseTraffic = proxy.getSessionResponseTraffic(sessionId);

        assertThat(requestTraffic).isNotEmpty();
        assertThat(responseTraffic).isNotEmpty();

        proxy.clearSessionEntries(sessionId);

        requestTraffic = proxy.getSessionRequestTraffic(sessionId);
        responseTraffic = proxy.getSessionResponseTraffic(sessionId);

        assertThat(requestTraffic).isEmpty();
        assertThat(responseTraffic).isEmpty();
    }

    @Test
    void whenClearSessionEntries_thenNoEntriesAndRules() throws InterruptedException {

        MoxProxyRule rule = MoxProxyRule.builder()
                .withDirection(MoxProxyDirection.REQUEST)
                .withAction(MoxProxyAction.RESPOND)
                .withSessionId(sessionId)
                .withHttpRuleDefinition()
                .withGetMethod()
                .withPathPattern(WIKIPEDIA_ORG_PATTERN)
                .withStatusCode(500)
                .withBody(MY_BODY)
                .havingHeaders()
                .withHeader("content-type", "text/html; charset=utf-8")
                .withHeader("content-length", MY_BODY.length())
                .backToParent()
                .backToParent().build();

        proxy.createRule(rule);

        driver.get(WIKI_URL);

        Thread.sleep(1000);

        List<MoxProxyProcessedTrafficEntry> requestTraffic = proxy.getSessionRequestTraffic(sessionId);
        List<MoxProxyProcessedTrafficEntry> responseTraffic = proxy.getSessionResponseTraffic(sessionId);

        assertThat(requestTraffic).isNotEmpty();
        assertThat(responseTraffic).isNotEmpty();

        proxy.clearSessionEntries(sessionId);

        requestTraffic = proxy.getSessionRequestTraffic(sessionId);
        responseTraffic = proxy.getSessionResponseTraffic(sessionId);

        assertThat(requestTraffic).isEmpty();
        assertThat(responseTraffic).isEmpty();

        driver.get(WIKI_URL);

        Thread.sleep(1000);

        assertThat(driver.getPageSource()).doesNotContain(MY_BODY);
    }
}
