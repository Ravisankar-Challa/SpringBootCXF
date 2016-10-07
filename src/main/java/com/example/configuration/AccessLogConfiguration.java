package com.example.configuration;

import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import ch.qos.logback.access.tomcat.LogbackValve;

@Configuration
public class AccessLogConfiguration {
    
    @Profile({"local-sandbox"})
    @Bean
    public EmbeddedServletContainerFactory servletContainerLocal() {
        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
        LogbackValve logbackValve = new LogbackValve();
        logbackValve.setFilename("logback-access-console.xml");
        tomcat.addContextValves(logbackValve);
        return tomcat;
    }
    
    @Profile({"dev", "staging", "production"})
    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
        LogbackValve logbackValve = new LogbackValve();
        logbackValve.setFilename("logback-access-file.xml");
        tomcat.addContextValves(logbackValve);
        return tomcat;
    }
}
