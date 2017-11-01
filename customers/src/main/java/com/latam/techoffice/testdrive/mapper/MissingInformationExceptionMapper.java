package com.latam.techoffice.testdrive.mapper;

import com.latam.techoffice.testdrive.error.MissingInformationException;
import java.util.logging.Logger;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * There is a missing information 
 * 
 * @author Mauricio "Maltron" Leal <maltron at gmail dot com> */
@Provider
public class MissingInformationExceptionMapper implements ExceptionMapper<MissingInformationException> {

    private static final Logger LOG = Logger.getLogger(MissingInformationExceptionMapper.class.getName());

    @Override
    public Response toResponse(MissingInformationException exception) {
        return Response.status(Response.Status.NOT_ACCEPTABLE).build();
    }
}
