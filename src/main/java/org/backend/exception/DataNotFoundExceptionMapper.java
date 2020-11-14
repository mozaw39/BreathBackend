package org.backend.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class DataNotFoundExceptionMapper implements ExceptionMapper<Exceptions.DataNotFoundException> {
    @Override
    public Response toResponse(Exceptions.DataNotFoundException e) {
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
