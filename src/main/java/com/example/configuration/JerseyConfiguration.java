package com.example.configuration;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import com.example.controller.TestController;
import com.example.exception.ConstraintVoilationExceptionHandler;
import com.example.filter.LoggingFilter;

@Component
@ApplicationPath("/api")
public class JerseyConfiguration extends ResourceConfig {
    
    public JerseyConfiguration() {
        //register(TestController.class);
        //register(ConstraintVoilationExceptionHandler.class);
        //register(LoggingFilter.class);
        packages(true, "com.example");
    }
}