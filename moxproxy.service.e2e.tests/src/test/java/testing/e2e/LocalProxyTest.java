package testing.e2e;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import moxproxy.enums.MoxProxyAction;
import moxproxy.enums.MoxProxyDirection;
import moxproxy.interfaces.MoxProxy;
import moxproxy.model.MoxProxyProcessedTrafficEntry;
import moxproxy.model.MoxProxyRule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import testing.builders.LocalMoxProxy;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LocalProxyTest {

    private static final String WIKI_URL = "https://en.wikipedia.org";
    private static final String WIKIPEDIA = "wikipedia";
    private static final int PROXY_PORT = 89;
    private WebDriver driver;

    @Autowired
    private static MoxProxy proxy;

    @BeforeAll
    static void beforeAll(){

        Logger root = (Logger)LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        root.setLevel(Level.INFO);

        proxy = LocalMoxProxy.builder()
                .withPort(PROXY_PORT)
                .withRecorderWhiteList(Collections.singletonList(WIKIPEDIA))
                .build();
    }


    @BeforeEach
    void before() {
        proxy.startServer();
        System.setProperty("webdriver.gecko.driver","D:\\webdriver\\geckodriver.exe");
        var profile = new FirefoxProfile();
        profile.setPreference("network.proxy.type", 1);
        profile.setPreference("network.proxy.http", "localhost");
        profile.setPreference("network.proxy.http_port", PROXY_PORT);
        profile.setPreference("network.proxy.ssl", "localhost");
        profile.setPreference("network.proxy.ssl_port", PROXY_PORT);
        profile.setPreference("network.proxy.socks", "localhost");
        profile.setPreference("network.proxy.socks_port", PROXY_PORT);
        profile.setAcceptUntrustedCertificates(true);
        profile.setAssumeUntrustedCertificateIssuer(false);
        var options = new FirefoxOptions();
        options.setProfile(profile);
        driver = new FirefoxDriver(options);
    }

    @AfterEach
    void after(){
        driver.quit();
        proxy.stopServer();
    }

    @Test
    void whenActionsPerformed_thenTrafficRecorded(){
        driver.get(WIKI_URL);
        List<MoxProxyProcessedTrafficEntry> requestTraffic = proxy.getAllRequestTraffic();
        List<MoxProxyProcessedTrafficEntry> responseTraffic = proxy.getAllResponseTraffic();
        assertThat(requestTraffic).isNotEmpty();
        assertThat(responseTraffic).isNotEmpty();
        assertThat(requestTraffic.stream().allMatch(x -> x.getUrl().contains(WIKIPEDIA))).isTrue();
        assertThat(responseTraffic.stream().allMatch(x -> x.getUrl().contains(WIKIPEDIA))).isTrue();
    }

    @Test
    void whenErrorResponseRule_thenErrorReturned(){

        String body = "TEST_ERROR";

        MoxProxyRule rule = MoxProxyRule.builder()
                .withDirection(MoxProxyDirection.REQUEST)
                .withAction(MoxProxyAction.RESPOND)
                .withHttpRuleDefinition()
                    .withGetMethod()
                    .withPathPattern("wikipedia\\.org")
                    .withStatusCode(500)
                    .withBody(body)
                    .havingHeaders()
                        .withHeader("content-type", "text/html; charset=utf-8")
                        .withHeader("content-length", body.length())
                        .backToParent()
                .backToParent().build();

        proxy.createRule(rule);

        driver.get(WIKI_URL);

        assertThat(driver.getPageSource()).contains(body);
    }

    @Test
    void whenResponseModified_thenModificationApplied() throws InterruptedException {

        String body = "[\"proxy\",[\"Only MoxProxy!\"],[\"https://moxproxy.com\"]]";

        MoxProxyRule rule = MoxProxyRule.builder()
                .withDirection(MoxProxyDirection.RESPONSE)
                .withAction(MoxProxyAction.MODIFY)
                .withHttpRuleDefinition()
                    .withGetMethod()
                    .withStatusCode(200)
                    .withBody(body)
                    .withPathPattern("search=proxy")
                    .havingHeaders()
                        .withHeader("content-length", body.length())
                        .backToParent()
                    .backToParent().build();

        proxy.createRule(rule);

        driver.get(WIKI_URL);

        WebElement search = driver.findElement(By.name("search"));
        search.sendKeys("proxy");

        Thread.sleep(3000);

        WebElement suggestions = driver.findElement(By.className("suggestions-result"));
        String text = suggestions.getText();

        assertEquals("Only MoxProxy!", text);
    }
}
