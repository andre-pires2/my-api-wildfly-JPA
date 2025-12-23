package com.andre.myapi.exception;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class InvalidPasswordExceptionMapper
        implements ExceptionMapper<InvalidPasswordException> {

    @Override
    public Response toResponse(InvalidPasswordException ex) {

        ApiError error = new ApiError(
                Response.Status.BAD_REQUEST.getStatusCode(),
                ex.getMessage()
        );

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(error)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
