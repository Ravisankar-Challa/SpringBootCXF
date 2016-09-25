package com.example.controller;

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
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

import com.example.configuration.ApplicationConfiguration;
import com.example.model.GetRequest;
import com.example.model.PostRequest;

@Path("/")
@Component
public class TestController {
    
    private ApplicationConfiguration config;
    
    public TestController(ApplicationConfiguration config) {
        this.config = config;
    }

    @GET
    @Path("hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String sayHello() {
        return "Hello World!!! " + config.getProperty1();
    }
    
    @GET
    @Path("get/validate/{name}")
    @Produces(MediaType.TEXT_PLAIN)
    public String validateGetRequest(@Valid @BeanParam GetRequest request) {
        return "Success " + request.getVersion() + request.getName() + request.getQuestion();
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
    
}
