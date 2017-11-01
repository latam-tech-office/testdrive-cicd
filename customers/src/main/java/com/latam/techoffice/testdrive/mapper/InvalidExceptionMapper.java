package com.latam.techoffice.testdrive.mapper;

import com.latam.techoffice.testdrive.error.InvalidException;
import java.util.logging.Logger;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * 409 Conflict
 * @author Mauricio "Maltron" Leal <maltron at gmail dot com>
 */
@Provider
public class InvalidExceptionMapper implements ExceptionMapper<InvalidException> {

    private static final Logger LOG = Logger.getLogger(InvalidExceptionMapper.class.getName());

    @Override
    public Response toResponse(InvalidException ex) {
        return Response.status(Response.Status.CONFLICT).build();
    }
    
    

}
