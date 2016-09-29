package com.example.filter;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.springframework.stereotype.Component;

import com.example.exception.ApplicationException;
import com.example.model.ErrorMessage;

@Provider
@Component
@Priority(2)
@Authenticate
public class AuthenticationFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String userName = requestContext.getHeaderString("user");
        if(!"RAVI".equals(userName)) {
            throw new ApplicationException(401, "Not authorized");
            /*throw new NotAuthorizedException("Bearer");
            ErrorMessage errorMessage = new ErrorMessage();
            errorMessage.setErrorCode(401);
            errorMessage.setErrorMessage("Not authenticated");
            requestContext.abortWith(
                    Response.status(401)
                            .entity(errorMessage)
                            .type(MediaType.APPLICATION_JSON_TYPE)
                            .build());*/
        }
        
    }

}
