package com.example.configuration;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import ch.qos.logback.access.tomcat.LogbackValve;

@Configuration
public class AccessLogConfiguration {
    
    @Profile({"local-sandbox"})
    @Bean
    public EmbeddedServletContainerCustomizer servletContainerLocal() {
        return new EmbeddedServletContainerCustomizer() {
            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {
                LogbackValve logbackValve = new LogbackValve();
                logbackValve.setAsyncSupported(true);
                logbackValve.setFilename("logback-access-console.xml");
                ((TomcatEmbeddedServletContainerFactory) container).addContextValves(logbackValve);
            }
        };
    }
    
    @Profile({"dev", "staging", "production"})
    @Bean
    public EmbeddedServletContainerCustomizer servletContainer() {
        return new EmbeddedServletContainerCustomizer() {
            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {
                LogbackValve logbackValve = new LogbackValve();
                logbackValve.setAsyncSupported(true);
                logbackValve.setFilename("logback-access-file.xml");
                ((TomcatEmbeddedServletContainerFactory) container).addContextValves(logbackValve);
            }
        };
    }
}
