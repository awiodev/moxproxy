package testing.e2e;

import moxproxy.builders.LocalMoxProxy;
import moxproxy.interfaces.MoxProxy;
import moxproxy.model.MoxProxyProcessedTrafficEntry;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class LocalProxyRecordingBodyTest extends TestBase {

    private WebDriver driver;

    private static MoxProxy proxy;

    @BeforeAll
    static void beforeAll(){
        proxy = LocalMoxProxy.builder()
                .withPort(PROXY_PORT)
                .withRecorderWhiteList(Collections.singletonList(WIKIPEDIA))
                .withRecordBodies()
                .build();
    }


    @BeforeEach
    void before() {
        proxy.startServer();
        driver = new FirefoxDriver(setupFirefox());
    }

    @AfterEach
    void after(){
        driver.quit();
        proxy.stopServer();
    }

    @Test
    void whenActionsPerformed_thenBodiesRecorded() {
        driver.get(WIKI_URL);
        List<MoxProxyProcessedTrafficEntry> requestTraffic = proxy.getAllRequestTraffic();
        List<MoxProxyProcessedTrafficEntry> responseTraffic = proxy.getAllResponseTraffic();
        assertThat(requestTraffic).isNotEmpty();
        assertThat(responseTraffic).isNotEmpty();
        assertThat(requestTraffic.stream().allMatch(x -> x.getBody() != null)).isTrue();
        assertThat(responseTraffic.stream().allMatch(x -> x.getBody() != null)).isTrue();
    }

}
