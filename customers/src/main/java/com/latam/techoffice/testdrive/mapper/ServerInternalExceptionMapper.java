package com.latam.techoffice.testdrive.mapper;

import com.latam.techoffice.testdrive.error.ServerInternalException;
import java.util.logging.Logger;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Mauricio "Maltron" Leal <maltron@gmail.com> */
@Provider
public class ServerInternalExceptionMapper implements ExceptionMapper<ServerInternalException> {

    private static final Logger LOG = Logger.getLogger(ServerInternalExceptionMapper.class.getName());

    public ServerInternalExceptionMapper() {
    }

    @Override
    public Response toResponse(ServerInternalException ex) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
}
