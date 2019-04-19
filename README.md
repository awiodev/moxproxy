# Moxproxy

Moxproxy is java library written on the top of [LittleProxy](https://github.com/adamfisk/LittleProxy) library to support automated testing by controlling network traffic between http client and server or application components.
 

It supports:
* http traffic recording
* modifications of requests and responses
    * headers
    * status codes
    * contents
    
# Lets start!

* [Local proxy](#local-proxy)
* [Standalone proxy](#standalone-proxy)
    * [Configuration](#standalone-configuration)
    * [Webservice client](#standalone-client)
* [Http client setup example](#client-setup)
    * [Session matching strategy](#session-matching)
* [Traffic recording](#traffic-recording)
    * [Retrieving recorded traffic](#get-traffic)    
    * [Recorded traffic and rules cleanup](#clean-traffic)
* [Request/Response modification](#traffic-modification)    
    * [Responding](#responding)
    * [Modifications](#modifications)
    * [Fields removal](#removal) 
* [More examples](#examples)           


# <a name="local-proxy"></a>Local proxy

To start local MoxProxy service, add **moxproxy.core** dependecy to your pom.xml.
```xml
<dependency>
  <groupId>com.moxproxy</groupId>
  <artifactId>moxproxy.core</artifactId>
  <version>1.0.4</version>
</dependency>
```
Setup proxy using LocalMoxProxy builder.

Builder provides setup for:
* **proxy port**
* **recorder whitelist** to record traffic only for specific domains (e.g. wikipedia.org)
* **content recording** to record traffic bodies (content recording is disabled by default)
* **session matching strategy** to enable http clients session identification (useful for concurrent tests execution)
* **authority** to specify custom **man in the middle** authority (if not specified then default certificate and keystore will be generated on first proxy startup) 

```java
public class LocalProxyExample {
     
    public static void main(String[] args) {
         
         MoxProxy proxy = LocalMoxProxy.builder()
                         .withPort(89)
                         .withRecorderWhiteList(Collections.singletonList("wikipedia.org"))
                         .withContentRecording()
                         .withSessionIdMatchStrategy()
                         .withAuthority()
                             .withAlias("your-alias")
                             .withKeyStoreDir(".")
                             .withPassword("your-password")
                             .withOrganization("your-organization")
                             .withCommonName("your-mitm, test proxy")
                             .withOrganizationalUnitName("Certificate Authority")
                             .withCertOrganization("your-mitm")
                             .withCertOrganizationalUnitName("MoxProxy-mitm, test automation purpose")
                             .backToParent()
                         .build();
         
         proxy.startServer();
         
         //...
         
         proxy.stopServer();
         
     }   
}
```

# <a name="standalone-proxy"></a>Standalone proxy

Standalone proxy is 2 in 1 application which contains proxy server and MoxProxy webservice to configure and control network traffic. 
MoxProxy webservice binary can be downloaded from [releases](https://github.com/lukasz-aw/moxproxy/releases) and run as regular java application.

```sh
$ java -jar moxproxy.web.service-1.0.2.jar
```

### <a name="standalone-configuration"></a>Configuration

Webservice is configured through **application.yml** file distributed with binary.

It provides configuration for:
* **logging** - see [spring](https://springframework.guru/using-yaml-in-spring-boot-to-configure-logback/) logging configuration
* **server** to configure webservice port - see [spring](https://docs.spring.io/spring-boot/docs/current/reference/html/howto-properties-and-configuration.html) properties configuration  
* **proxy** as described in [Local proxy](#local-proxy) section
* **mitm** as described in [Local proxy](#local-proxy) section
* **service** 
    * **cleanupDelayInSeconds** to configure proxy to clean old traffic and rules (modifications) after specified time
    * **basicAuthUserName** and **basicAuthPassword** to set basic authentication username and password for the webservice. Basic authentication is **required**

```yaml
logging:
  file: logs/app.log
  pattern:
    console: "%d %-5level %logger : %msg%n"
    file: "%d %-5level [%thread] %logger : %msg%n"
  level:
    ROOT: INFO
    org.springframework: ERROR
    org.littleshoot: FATAL
    org.bouncycastle: FATAL
server:
  port: 8081
proxy:
  proxyPort: 89
  sessionIdMatchStrategy: true
  recordContent: false
  urlWhiteListForTrafficRecorder: []
mitm:
  createOwn: false
  keyStoreDir: .
  alias: moxproxy-mitm
  password: doItOnlyForTesting
  organization: MoxProxy-mitm
  commonName: MoxProxy-mitm, test proxy
  organizationalUnitName: Certificate Authority
  certOrganization: MoxProxy-mitm
  certOrganizationalUnitName:  MoxProxy-mitm, test automation purpose
service:
  cleanupDelayInSeconds : 300
  basicAuthUserName: change-user
  basicAuthPassword: change-password
```

During startup standalone proxy will display MoxProxy banner and information about proxy port.

```sh
 ______                 ______
|  ___ \               (_____ \
| | _ | |  ___   _   _  _____) )  ____   ___   _   _  _   _
| || || | / _ \ ( \ / )|  ____/  / ___) / _ \ ( \ / )| | | |
| || || || |_| | ) X ( | |      | |    | |_| | ) X ( | |_| |
|_||_||_| \___/ (_/ \_)|_|      |_|     \___/ (_/ \_) \__  |
                                                     (____/
2019-03-23 20:25:53,482 INFO  moxproxy.webservice.MoxProxyWebService : Starting MoxProxyWebService on MACHINE with PID 2780 
2019-03-23 20:25:55,619 INFO  org.apache.catalina.core.StandardService : Starting service [Tomcat]
2019-03-23 20:25:55,619 INFO  org.apache.catalina.core.StandardEngine : Starting Servlet engine: [Apache Tomcat/9.0.16]
2019-03-23 20:25:56,499 INFO  org.apache.catalina.core.ContainerBase.[Tomcat].[localhost].[/] : Initializing Spring embedded WebApplicationContext
2019-03-23 20:25:57,332 INFO  moxproxy.webservice.MoxProxyWebService : Started MoxProxyWebService in 4.453 seconds (JVM running for 5.561)
2019-03-23 20:25:58,651 INFO  moxproxy.services.MoxProxyImpl : Starting MoxProxy on port 89
2019-03-23 20:25:58,917 INFO  moxproxy.services.MoxProxyImpl : MoxProxy server started
```

### <a name="standalone-client"></a>Webservice client

To communicate with webservice add **moxproxy.client** dependency to your pom.xml.

```xml
<dependency>
  <groupId>com.moxproxy</groupId>
  <artifactId>moxproxy.client</artifactId>
  <version>1.0.4</version>
</dependency>
```
Setup proxy client using MoxProxyClient builder.

Builder provides setup for:
* **base url** - standalone proxy ip or domain with webservice port
* **basic authentication** credentials set up for standalone proxy - see [configuration](#standalone-configuration) section 

```java
public class StandaloneProxyExample {
     
    public static void main(String[] args) {
         
          MoxProxyService moxProxyClient = MoxProxyClient.builder()
                         .withBaseUrl("http://localhost:8081")                         
                         .withUser("change-user")
                         .withPassword("change-password").build();
         
     }   
}
```

# <a name="client-setup"></a>Http client setup example

In this example we will set up Selenium Webdriver (FirefoxDriver) to use MoxProxy. **Please be aware that other browsers setup may look different.**

Driver should be set up to accept **untrusted certificates** otherwise browser will block traffic through proxy. Generated mitm certificate may also be added to trusted authorities.

```java
public class FirefoxExample {
     
    public static void main(String[] args) {
         
        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("network.proxy.type", 1);
        profile.setPreference("network.proxy.http", "localhost");
        profile.setPreference("network.proxy.http_port", 89);
        profile.setPreference("network.proxy.ssl", "localhost");
        profile.setPreference("network.proxy.ssl_port", 89);
        profile.setPreference("network.proxy.socks", "localhost");
        profile.setPreference("network.proxy.socks_port", 89);
        profile.setAcceptUntrustedCertificates(true);
        profile.setAssumeUntrustedCertificateIssuer(false);
        FirefoxOptions options = new FirefoxOptions();
        options.setProfile(profile);        
        
        WebDriver driver = new FirefoxDriver(options);
        
        driver.get("https://en.wikipedia.org");
     }   
}
```

### <a name="session-matching"></a>Session matching strategy

Session id matching strategy has been implemented to identify http client instance (e.g. browser) traffic and rules (modifications). 
It is very useful when several http clients are using one proxy instance. It is recommended to always use session id matching strategy when using standalone proxy. 

MoxProxy identifies session by request header value. It means that http client (e.g. browser) should have MOXSESSIONID=UNIQUE_SESSIONID in any header value (e.g. MOXSESSIONID=3db357b0-b149-4569-be1e-1728e14f0cde).

It can be simply achieved in Selenium Webdriver by setting cookie. Generated session id will be used later to collect network traffic and creating rules for http client instance.

```java
public class FirefoxExample {
     
    public static void main(String[] args) {
         
        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("network.proxy.type", 1);
        profile.setPreference("network.proxy.http", "localhost");
        profile.setPreference("network.proxy.http_port", 89);
        profile.setPreference("network.proxy.ssl", "localhost");
        profile.setPreference("network.proxy.ssl_port", 89);
        profile.setPreference("network.proxy.socks", "localhost");
        profile.setPreference("network.proxy.socks_port", 89);
        profile.setAcceptUntrustedCertificates(true);
        profile.setAssumeUntrustedCertificateIssuer(false);
        FirefoxOptions options = new FirefoxOptions();
        options.setProfile(profile);        
        
        WebDriver driver = new FirefoxDriver(options);
        
        driver.get("https://en.wikipedia.org");
        
        String sessionId = UUID.randomUUID().toString();
        Cookie cookie = new Cookie("MOXSESSIONID", sessionId);
        driver.manage().addCookie(cookie);
     }   
}
```  

# <a name="traffic-recording"></a>Traffic recording

MoxProxy records everything that goes through it (or according to whitelist) so after http client setup every request and response will be stored separately in service storage.
By default MoxProxy is not recording traffic content (bodies) but it can be set up - see [local proxy](#local-proxy) and [standalone proxy](#standalone-proxy).

### <a name="get-traffic"></a>Retrieving recorded traffic

To retrieve recorded traffic use local proxy service or standalone proxy client (both are implementing the same interface).

```java
class ExampleTest {
    
    //...
    
    //local proxy
    @Test
    void whenLocalProxyActionsPerformed_thenTrafficRecorded() {
        driver.get(WIKI_URL);
        List<MoxProxyProcessedTrafficEntry> requestTraffic = proxy.getAllRequestTraffic();
        List<MoxProxyProcessedTrafficEntry> responseTraffic = proxy.getAllResponseTraffic();
        
        assertThat(requestTraffic).isNotEmpty();
        assertThat(responseTraffic).isNotEmpty();
    }
    
    //remote proxy
    @Test
    void whenRemoteProxyActionsPerformed_thenTrafficRecorded(){
        driver.get(WIKI_URL);
        List<MoxProxyProcessedTrafficEntry> requestTraffic = moxProxyClient.getAllRequestTraffic();
        List<MoxProxyProcessedTrafficEntry> responseTraffic = moxProxyClient.getAllResponseTraffic();
        
        assertThat(requestTraffic).isNotEmpty();
        assertThat(responseTraffic).isNotEmpty();
    }
}
```

When session id matching strategy is enabled (see [session matching strategy](#session-matching)) then recorded traffic can be retrieved for specific session id.

```java
class ExampleTest {
    
    //...
    
    //local proxy
    @Test
    void whenLocalProxyCollectTraffic_thenSessionTrafficCollected() {
        driver.get(WIKI_URL);
        
        List<MoxProxyProcessedTrafficEntry> requestTraffic = proxy.getSessionRequestTraffic(sessionId);
        List<MoxProxyProcessedTrafficEntry> responseTraffic = proxy.getSessionResponseTraffic(sessionId);

        assertThat(requestTraffic).isNotEmpty();
        assertThat(responseTraffic).isNotEmpty();        
    }
    
    //remote proxy
    @Test
    void whenRemoteProxyCollectTraffic_thenSessionTrafficCollected() {
        driver.get(WIKI_URL);
        
        List<MoxProxyProcessedTrafficEntry> requestTraffic = moxProxyClient.getSessionRequestTraffic(sessionId);
        List<MoxProxyProcessedTrafficEntry> responseTraffic = moxProxyClient.getSessionResponseTraffic(sessionId);

        assertThat(requestTraffic).isNotEmpty();
        assertThat(responseTraffic).isNotEmpty();        
    }
}
```

### <a name="clean-traffic"></a>Recorded traffic and rules cleanup

Recorded traffic and rules cleanup for all sessions.
```java
class ExampleTest {
    
    //...
    
    //local proxy
    @Test
    void whenLocalProxyCollectTraffic_thenSessionTrafficCollected() {        
        proxy.clearAllSessionEntries();

        assertThat(requestTraffic).isEmpty();
        assertThat(responseTraffic).isEmpty(); 
    }
    
    //remote proxy
    @Test
    void whenRemoteProxyCollectTraffic_thenSessionTrafficCollected() {        
        moxProxyClient.clearAllSessionEntries();
        
        assertThat(requestTraffic).isEmpty();
        assertThat(responseTraffic).isEmpty();           
    }
}
```
Recorded traffic and rules cleanup for specific session.
```java
class ExampleTest {
    
    //...
    
    //local proxy
    @Test
    void whenLocalProxyCollectTraffic_thenSessionTrafficCollected() {        
        //...
        
        proxy.clearSessionEntries(sessionId);

        assertThat(requestTraffic).isEmpty();
        assertThat(responseTraffic).isEmpty(); 
    }
    
    //remote proxy
    @Test
    void whenRemoteProxyCollectTraffic_thenSessionTrafficCollected() {        
        //...
        
        moxProxyClient.clearSessionEntries(sessionId);        
        
        assertThat(requestTraffic).isEmpty();
        assertThat(responseTraffic).isEmpty();           
    }
}
```

Standalone proxy also performs automated cleanups - see [standalone proxy](#standalone-proxy)

# <a name="traffic-modification"></a>Request/response modification

Traffic modification is possible through **MoxProxyRule** setup. 

Rules are matched against traffic by:
* method (GET,POST,PUT,DELETE)
* path regular expression

Builder provides setup for:
* **direction** (Request/Response) - required
* **action** to instruct proxy what it should do with the traffic item (RESPOND, MODIFY or DELETE) - required
    
    **Rule Actions are processed in order.**
     
     for direction = REQUEST:
    * RESPOND (instructing proxy to immediately respond to request)
    * MODIFY (header, body modification)
    * DELETE (removal of header or body)
    
    And for direction = RESPONSE:
    * MODIFY
    * DELETE 

    **RESPOND rule for direction = REQUEST is processed always in the first place.** It means that if we have two rules set up for the request (MODIFY and RESPOND) then RESPOND rule will be processed only!  
* **session id** to instruct proxy to identify http client session (session id matching strategy should be turned on on the proxy service)
* **Http rule definition** - required
    * method - required
    * status code
    * body 
    * delete body applicable to DELETE action
    * path pattern regular expression - required
    * headers 

Proxy service returns **Rule id** after proxy rule is applied. This id can be used later to instruct proxy to cancel the rule. 
Canceling rule removes it from proxy rules storage.

```java
class ExampleTest {
    
    //...
    
    void whenResponseModified_thenModificationApplied() {
        
        String body = "[\"proxy\",[\"Only MoxProxy!\"],[\"https://moxproxy.com\"]]";
        
                MoxProxyRule rule = MoxProxyRule.builder()
                        .withDirection(MoxProxyDirection.RESPONSE)
                        .withAction(MoxProxyAction.MODIFY)
                        .withSessionId(sessionId)
                        .withHttpRuleDefinition()
                            .withGetMethod()
                            .withStatusCode(200)
                            .withBody(body)
                            .withPathPattern("search=proxy")                            
                            .backToParent().build();
        
                String ruleId = proxy.createRule(rule);
                
                //...
                
                proxy.cancelRule(ruleId);
    }
}
```

### <a name="responding"></a>Responding

Respond action is applicable only to REQUEST direction. It instructs proxy to respond immediately with specifies status code, headers and content. It's kind of mock.

```java
class ExampleTest {
    
    //...
              
    @Test
    void whenErrorResponseRule_thenErrorReturned() {

        String body = "TEST_ERROR";

        MoxProxyRule rule = MoxProxyRule.builder()
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

        proxy.createRule(rule);

        driver.get(WIKI_URL);                

        assertThat(driver.getPageSource()).contains(body);
    }
    
}
```

### <a name="modifications"></a>Modifications

Modifications can be applied for both directions. For requests it is possible to modify headers and request body (if exists) and for responses headers, body and status code.

```java
class ExampleTest {
    
    //...
              
    @Test
    void whenRequestModified_thenModificationApplied() {

        String ipadAgent = "Mozilla/5.0 (iPad; CPU OS 5_1 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Version/5.1 Mobile/9B176 Safari/7534.48.3";
        String xpath = "//a[@href='/wiki/Special:MobileMenu']";

        MoxProxyRule rule = MoxProxyRule.builder()
                .withDirection(MoxProxyDirection.REQUEST)
                .withAction(MoxProxyAction.MODIFY)
                .withHttpRuleDefinition()
                .withGetMethod()
                .withPathPattern(WIKIPEDIA_ORG_PATTERN)
                .havingHeaders()
                .withHeader("User-Agent", ipadAgent).backToParent()
                .backToParent().build();

        proxy.createRule(rule);

        driver.get(WIKI_URL);        

        WebElement mobileMenu = driver.findElement(By.xpath(xpath));

        assertTrue(mobileMenu.isDisplayed());
    }
    
    @Test
    void whenResponseModified_thenModificationApplied() {

        String body = "[\"proxy\",[\"Only MoxProxy!\"],[\"https://moxproxy.com\"]]";

        MoxProxyRule rule = MoxProxyRule.builder()
                .withDirection(MoxProxyDirection.RESPONSE)
                .withAction(MoxProxyAction.MODIFY)
                .withHttpRuleDefinition()
                    .withGetMethod()
                    .withStatusCode(200)
                    .withBody(body)
                    .withPathPattern(SEARCH_PROXY)                    
                    .backToParent().build();

        proxy.createRule(rule);

        driver.get(WIKI_URL);        

        WebElement search = driver.findElement(BY_SEARCH);
        search.sendKeys(PROXY_TXT);        

        WebElement suggestions = driver.findElement(By.className("suggestions-result"));
        String text = suggestions.getText();

        assertEquals("Only MoxProxy!", text);
    }
}
```

### <a name="removal"></a>Fields removal

Fields removal applies for both directions to headers and body. To remove header simply specify header name with any value. To remove body use **withDeleteBody()** builder method.

```java
class ExampleTest {
    
    //...
                    
    @Test
    void whenBodyRemoved_thenNoResultsReturned() {

        MoxProxyRule rule = MoxProxyRule.builder()
                .withDirection(MoxProxyDirection.RESPONSE)
                .withAction(MoxProxyAction.DELETE)
                .withHttpRuleDefinition()
                .withGetMethod()
                .withStatusCode(200)
                .withDeleteBody()
                .havingHeaders()
                    .withHeader("content-type", "header-will-be-removed")                    
                    .backToParent()
                .withPathPattern(SEARCH_PROXY)
                .backToParent().build();

        proxy.createRule(rule);

        driver.get(WIKI_URL);        

        WebElement search = driver.findElement(BY_SEARCH);
        search.sendKeys(PROXY_TXT);        

        List<WebElement> suggestions = driver.findElements(By.className("suggestions-result"));

        assertEquals(0, suggestions.size());
    }
}
```

# <a name="examples"></a>More examples

Examples are part of this repository:
* [local proxy examples](https://github.com/lukasz-aw/moxproxy/tree/master/moxproxy.local.examples/src/test/java/testing/e2e)
* [web service examples](https://github.com/lukasz-aw/moxproxy/tree/master/moxproxy.web.service/src/test/java/testing)


