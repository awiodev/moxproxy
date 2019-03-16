# Moxproxy

Moxproxy is java library written on the top of [LittleProxy](https://github.com/adamfisk/LittleProxy) library.
Main goal of Moxproxy library development is to support automated testing.

In current version it supports:
* http traffic recording
* request/response modification
    * headers
    * status codes
    * body
    
**Documentation is still work in progress.**

To use MoxProxy local service in your project, add **moxproxy.core** dependecy to your pom.xml.
Examples can be found in [mox.proxy.local.examples](https://github.com/lukasz-aw/moxproxy/tree/master/moxproxy.local.examples/src/test/java/testing/e2e) module

```xml
<dependency>
  <groupId>com.moxproxy</groupId>
  <artifactId>moxproxy.core</artifactId>
  <version>1.0.2</version>
</dependency>
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

