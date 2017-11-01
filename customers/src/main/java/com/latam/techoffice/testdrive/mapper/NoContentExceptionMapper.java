package com.latam.techoffice.testdrive.mapper;

import com.latam.techoffice.testdrive.error.NoContentException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NoContentExceptionMapper implements ExceptionMapper<NoContentException> {

    @Override
    public Response toResponse(NoContentException ex) {
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
