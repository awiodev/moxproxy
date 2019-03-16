package testing;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.slf4j.LoggerFactory;

class TestBase {

    static final int SLEEP_TIME = 2000;
    private static final int PROXY_PORT = 89;
    static final String WIKI_URL = "https://en.wikipedia.org";
    static final String WIKIPEDIA_ORG_PATTERN = "wikipedia\\.org";
    static final String SEARCH_PROXY = "search=proxy";
    static final String PROXY_TXT = "proxy";
    static final By BY_SEARCH = By.name("search");

    @BeforeAll
    static void beforeAllTests() {
        Logger root = (Logger) LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);
        root.setLevel(Level.ERROR);
        ((Logger)LoggerFactory.getLogger("org.littleshoot")).setLevel(Level.OFF);
        ((Logger)LoggerFactory.getLogger("org.bouncycastle")).setLevel(Level.OFF);
        ((Logger)LoggerFactory.getLogger("org.openqa.selenium")).setLevel(Level.OFF);
        System.setProperty("webdriver.gecko.driver", "D:\\webdriver\\geckodriver.exe");
    }

    FirefoxOptions setupFirefox() {
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
        options.setLogLevel(FirefoxDriverLogLevel.FATAL);
        return options;
    }
}
