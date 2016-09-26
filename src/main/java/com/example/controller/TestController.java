package com.example.controller;

import java.util.StringJoiner;
import java.util.concurrent.CompletableFuture;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

import com.example.configuration.ApplicationConfiguration;
import com.example.filter.Authenticate;
import com.example.model.GetRequest;
import com.example.model.PostRequest;
import com.example.util.Constants;

@Path("/")
@Component
public class TestController {
    
    private ApplicationConfiguration config;
    
    public TestController(ApplicationConfiguration config) {
        this.config = config;
    }

    @GET
    @Path("hello")
    @Produces(MediaType.APPLICATION_JSON)
    public String sayHello() {
        return "{\"hai\":\"Hello World!!! " + config.getProperty1() +"\"}";
    }
    
    @GET
    @Path("get/validate/{name}")
    @Produces(MediaType.TEXT_PLAIN)
    public String validateGetRequest(@Valid @BeanParam GetRequest request) {
        return new StringJoiner(" ")
                .add(request.getVersion())
                .add(request.getName())
                .add(request.getQuestion()).toString();
    }
    
    @POST
    @Path("post/validate/{name}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public PostRequest validatePostRequest(
                        @PathParam("name")
                        @NotNull(message = "Name Missing")
                        @Pattern(regexp = "[a-zA-Z \']*", message = "Name format is incorrect")
                        final String name,
                        @Valid final PostRequest request) {
        return request;
    }
    
    @GET
    @Path("hello/async")
    @Authenticate
    @Produces(MediaType.TEXT_PLAIN)
    public void sayHello(@Suspended final AsyncResponse asyncResponse) {
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            asyncResponse.resume(sayHello());   
        });
    }
    
    @GET
    @Path("service")
    @Produces(Constants.SERVICE_V1)
    public String version1() {
        return "{\"version\":1}";
    }
    
    @GET
    @Path("service")
    @Produces(Constants.SERVICE_V2)
    public String version2() {
        return "{\"version\":2}";
    }
    
}
