package moxproxy.webservice;

import io.netty.handler.codec.http.HttpHeaders;
import moxproxy.builders.MoxProxyRuleBuilder;
import moxproxy.configuration.ServiceBeanConfiguration;
import moxproxy.dto.MoxProxyRule;
import moxproxy.enums.MoxProxyAction;
import moxproxy.enums.MoxProxyDirection;
import moxproxy.interfaces.IMoxProxyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;

@Component
@Import(ServiceBeanConfiguration.class)
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    IMoxProxyServer proxyServer;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        proxyServer.startServer();
/*        String body = "<!DOCTYPE HTML \"-//IETF//DTD HTML 2.0//EN\">\n"
                + "<html><head>\n"
                + "<title>"+"Bad Gateway"+"</title>\n"
                + "</head><body>\n"
                + "DUPA"
                + "</body></html>\n";

        byte[] len = body.getBytes(Charset.forName("UTF-8"));

        MoxProxyRuleBuilder rulebuilder = new MoxProxyRuleBuilder();
        MoxProxyRule rule = rulebuilder.withDirection(MoxProxyDirection.REQUEST)
                .withAction(MoxProxyAction.RESPOND)
                .withHttpObjectDefinition()
                .withMethod("GET")
                .withPathPattern("page\\.art")
                .withStatusCode(500)
                .withBody(body)
                .havingHeaders()
                    .addItem().withHeader("Content-Type", "text/html; charset=UTF-8").backToParent()
                    .addItem().withHeader(HttpHeaders.Names.CONTENT_LENGTH, len.length).backToParent()
                    //.addItem().withHeader("Cache-Control", "no-cache").backToParent()
                    .backToParent()
                .backToParent().build();

        proxyServer.createRule(rule);*/
    }
}
