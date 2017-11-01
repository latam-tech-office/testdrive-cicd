package com.latam.techoffice.testdrive.mapper;

import com.latam.techoffice.testdrive.error.CreateException;
import java.util.logging.Logger;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Represents an existing duplication during the creation
 * 
 * @author Mauricio "Maltron" Leal <maltron at gmail dot com> */
@Provider
public class CreateExceptionMapper implements ExceptionMapper<CreateException> {

    private static final Logger LOG = Logger.getLogger(CreateExceptionMapper.class.getName());

    @Override
    public Response toResponse(CreateException exception) {
        return Response.status(Response.Status.CONFLICT).build();
    }
}
