package com.example.configuration;

import javax.ws.rs.Produces;
import javax.ws.rs.ext.Provider;

import org.apache.johnzon.jaxrs.jsonb.jaxrs.JsonbJaxrsProvider;
import org.springframework.stereotype.Component;

import com.example.util.Constants;

@Provider
@Component
@Produces(value = {Constants.APPLICATION_ERROR_JSON})
public class CustomJaxrsProvider<T> extends JsonbJaxrsProvider<T> {

}
