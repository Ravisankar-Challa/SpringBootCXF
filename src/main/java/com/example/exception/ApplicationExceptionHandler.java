package com.example.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.springframework.stereotype.Component;

import com.example.model.ErrorMessage;

@Provider
@Component
public class ApplicationExceptionHandler implements ExceptionMapper<ApplicationException>{

    @Override
    public Response toResponse(ApplicationException exception) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setErrorCode(exception.getErrorCode());
        errorMessage.setErrorMessage(exception.getErrorMessage());
        
        return Response.status(exception.getErrorCode())
                        .entity(errorMessage)
                        .type(MediaType.APPLICATION_JSON_TYPE)
                        .build();
    }
    
}
