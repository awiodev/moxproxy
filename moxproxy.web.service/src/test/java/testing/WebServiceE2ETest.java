package testing;

import moxproxy.client.MoxProxyClient;
import moxproxy.enums.MoxProxyAction;
import moxproxy.enums.MoxProxyDirection;
import moxproxy.interfaces.MoxProxyService;
import moxproxy.model.MoxProxyProcessedTrafficEntry;
import moxproxy.model.MoxProxyRule;
import moxproxy.webservice.ApplicationShutdown;
import moxproxy.webservice.MoxProxyWebService;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WebServiceE2ETest extends TestBase {

    private static MoxProxyService moxProxyClient;
    private static ConfigurableApplicationContext ctx;

    private WebDriver driver;
    private String sessionId;

    @BeforeAll
    static void beforeAll(){
        moxProxyClient = MoxProxyClient.builder()
                .withBaseUrl("http://localhost:8081")
                .withUser("change-user")
                .withPassword("change-password").build();

        ctx = SpringApplication.run(MoxProxyWebService.class);
    }

    @AfterAll
    static void afterAll(){
        ctx.getBean(ApplicationShutdown.class);
        ctx.close();
    }

    @BeforeEach
    void before() {
        driver = new FirefoxDriver(setupFirefox());
        driver.get(WIKI_URL);
        sessionId = UUID.randomUUID().toString();
        Cookie cookie = new Cookie("MOXSESSIONID", sessionId);
        driver.manage().addCookie(cookie);
    }

    @AfterEach
    void after(){
        moxProxyClient.clearAllSessionEntries();
        driver.quit();
    }

    @Test
    void whenActionsPerformed_thenTrafficRecorded(){
        driver.get(WIKI_URL);
        List<MoxProxyProcessedTrafficEntry> requestTraffic = moxProxyClient.getAllRequestTraffic();
        List<MoxProxyProcessedTrafficEntry> responseTraffic = moxProxyClient.getAllResponseTraffic();
        assertThat(requestTraffic).isNotEmpty();
        assertThat(responseTraffic).isNotEmpty();
    }

    @Test
    void whenErrorResponseRule_thenErrorReturned() throws InterruptedException {

        String body = "TEST_ERROR";

        MoxProxyRule rule = MoxProxyRule.builder()
                .withSessionId(sessionId)
                .withDirection(MoxProxyDirection.REQUEST)
                .withAction(MoxProxyAction.RESPOND)
                .withHttpRuleDefinition()
                .withGetMethod()
                .withPathPattern(WIKIPEDIA_ORG_PATTERN)
                .withStatusCode(500)
                .withBody(body)
                .havingHeaders()
                .withHeader("content-type", "text/html; charset=utf-8")
                .backToParent()
                .backToParent().build();

        moxProxyClient.createRule(rule);

        driver.get(WIKI_URL);

        Thread.sleep(SLEEP_TIME);

        assertThat(driver.getPageSource()).contains(body);
    }

    @Test
    void whenResponseModified_thenModificationApplied() throws InterruptedException {

        String body = "[\"proxy\",[\"Only MoxProxy!\"],[\"https://moxproxy.com\"]]";

        MoxProxyRule rule = MoxProxyRule.builder()
                .withSessionId(sessionId)
                .withDirection(MoxProxyDirection.RESPONSE)
                .withAction(MoxProxyAction.MODIFY)
                .withHttpRuleDefinition()
                .withGetMethod()
                .withStatusCode(200)
                .withBody(body)
                .withPathPattern(SEARCH_PROXY)
                .backToParent().build();

        moxProxyClient.createRule(rule);

        driver.get(WIKI_URL);

        Thread.sleep(SLEEP_TIME);

        WebElement search = driver.findElement(BY_SEARCH);
        search.sendKeys(PROXY_TXT);

        Thread.sleep(SLEEP_TIME);

        WebElement suggestions = driver.findElement(By.className("suggestions-result"));
        String text = suggestions.getText();

        assertEquals("Only MoxProxy!", text);
    }

    @Test
    void whenRequestModified_thenModificationApplied() throws InterruptedException {

        String ipadAgent = "Mozilla/5.0 (iPad; CPU OS 5_1 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Version/5.1 Mobile/9B176 Safari/7534.48.3";
        String xpath = "//img[@src='/static/images/mobile/copyright/wikipedia-wordmark-en.png']";

        MoxProxyRule rule = MoxProxyRule.builder()
                .withSessionId(sessionId)
                .withDirection(MoxProxyDirection.REQUEST)
                .withAction(MoxProxyAction.MODIFY)
                .withHttpRuleDefinition()
                .withGetMethod()
                .withPathPattern(WIKIPEDIA_ORG_PATTERN)
                .havingHeaders()
                .withHeader("User-Agent", ipadAgent).backToParent()
                .backToParent().build();

        moxProxyClient.createRule(rule);

        driver.get(WIKI_URL);

        Thread.sleep(SLEEP_TIME);

        WebElement mobileMenu = driver.findElement(By.xpath(xpath));

        assertTrue(mobileMenu.isDisplayed());
    }
}
