# Moxproxy

Moxproxy is java library written on the top of [LittleProxy](https://github.com/adamfisk/LittleProxy) library to support automated testing by controlling network traffic between http client and server or application components.
 

It supports:
* http traffic recording
* modifications of requests and responses
    * headers
    * status codes
    * contents
    
#Lets start!

* [Local proxy](#local-proxy)
* [Standalone proxy](#standalone-proxy)
* [Http client setup](#client-setup)
    * [Session matching strategy](#session-matching)
* [Traffic recording](#traffic-recording)
    * [Retrieving recorded requests](#get-requests)
    * [Retrieving recorded responses](#get-responses)
* [Request/Response modification](#traffic-modification)
    * [Responding](#responding)
    * [Request header modification](#request-header-mod)
    * [Request header removal](#request-header-rem)
    * [Request body modification](#request-body-mod)


# <a name="local-proxy"></a>Local proxy

To start local MoxProxy service, add **moxproxy.core** dependecy to your pom.xml.
```xml
<dependency>
  <groupId>com.moxproxy</groupId>
  <artifactId>moxproxy.core</artifactId>
  <version>1.0.2</version>
</dependency>
```
Setup proxy using LocalMoxProxy builder.

Builder provides setup for:
* **proxy port**
* **recorder whitelist** to record traffic only for specific domain (e.g. wikipedia.org)
* **content recording** to record traffic bodies (content recording is disabled by default)
* **session matching strategy** to enable http clients session identification (useful for concurrent tests execution)
* **authority** to specify custom **man in the middle** authority (if not specified then default certificate and keystore will be generated on first proxy startup) 

```
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
                    .withCertOrganizationalUnitName("MoxProxy-mitm, test automation purpose").backToParent()
                .build();

proxy.startServer();

...

proxy.stopServer();
```







Standalone MoxProxy webservice binary can be downloaded from [here](https://github.com/lukasz-aw/moxproxy/releases/download/moxproxy-1.0.2/moxproxy.web.service-1.0.2.zip) and started with following command:
```sh
$ java -jar moxproxy.web.service-1.0.2.jar
```
Application can be configured with **application.yml** file distributed with binary

To communicate with webservice add **moxproxy.client** dependency to your pom.xml.
Examples can be found in [moxproxy.web.service](https://github.com/lukasz-aw/moxproxy/blob/master/moxproxy.web.service/src/test/java/testing/WebServiceE2ETest.java) end to end test.

```xml
<dependency>
  <groupId>com.moxproxy</groupId>
  <artifactId>moxproxy.client</artifactId>
  <version>1.0.2</version>
</dependency>
```

**Documentation will be updated as soon as possible...**

