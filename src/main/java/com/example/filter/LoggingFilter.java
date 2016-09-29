package com.example.filter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Component
@Provider
@Priority(1)
public class LoggingFilter implements ContainerRequestFilter, ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext reqContext) throws IOException {
        reqContext.setProperty("start", System.currentTimeMillis());
        Optional<String> correlationId = Optional.ofNullable(reqContext.getHeaderString("X-Correlation-Id"));
        MDC.put("uuid", correlationId.isPresent() ? correlationId.get() : UUID.randomUUID().toString());
        
    }

    @Override
    public void filter(ContainerRequestContext reqContext, ContainerResponseContext responseContext)
            throws IOException {
        System.out.println(" - "+Thread.currentThread().getName()+" ["+LocalDateTime.now()+"] \""+
            reqContext.getMethod()+" "+reqContext.getUriInfo().getAbsolutePath() +" - "+
                responseContext.getStatus()+" "+responseContext.getLength()+" "+
                (System.currentTimeMillis() - (Long)reqContext.getProperty("start"))+" "+reqContext.getHeaderString("User-Agent"));
    }

}
