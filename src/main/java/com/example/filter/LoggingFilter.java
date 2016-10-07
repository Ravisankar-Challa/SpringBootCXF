package com.example.filter;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Component
@Provider
public class LoggingFilter implements ContainerRequestFilter, ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext reqContext) throws IOException {
        //System.out.println(reqContext.getHeaders()+reqContext.getUriInfo().getPath()+"************************************8");
        Optional<String> correlationId = Optional.ofNullable(reqContext.getHeaderString("X-Correlation-Id"));
        MDC.put("uuid", correlationId.isPresent() ? correlationId.get() : UUID.randomUUID().toString());
        
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
            throws IOException {
        //System.out.println(responseContext.getHeaders());
    }

}
