package com.latam.techoffice.testdrive;

import com.latam.techoffice.testdrive.error.CreateException;
import com.latam.techoffice.testdrive.error.InvalidException;
import java.util.Collection;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.latam.techoffice.testdrive.error.NoContentException;
import com.latam.techoffice.testdrive.error.ServerInternalException;
import com.latam.techoffice.testdrive.model.Customer;
import java.util.logging.Level;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.core.GenericEntity;

@Path("/v1/customer")
public class Resource {

    private static final Logger LOG = Logger.getLogger(Resource.class.getName());
    
    @Context
    private UriInfo info;
    
    @EJB
    private Service service;
    
    
    @GET @Path("/{customerID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByCustomerID(@PathParam("customerID")String customerID)
            throws NotFoundException, InvalidException, ServerInternalException {
        Customer found = service.findByID(customerID);
        
        return Response.ok(found, MediaType.APPLICATION_JSON).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response fetchAll() throws NoContentException {
        Collection<Customer> customers = service.fetchAll();
        GenericEntity<Collection<Customer>> result = 
                new GenericEntity<Collection<Customer>>(customers){};
        
        return Response.ok(result, MediaType.APPLICATION_JSON).build();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(Customer customer) 
                                throws CreateException, ServerInternalException {
        LOG.log(Level.INFO, ">>> (Resource) Customer: %s", customer.toString());
        String newCustomerID = service.create(customer);
        
        return Response.status(Response.Status.CREATED)
                .entity(newCustomerID).type(MediaType.APPLICATION_JSON).build();
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(Customer customer) 
            throws NotFoundException, InvalidException, ServerInternalException {
        service.update(customer);
        
        return Response.status(Response.Status.OK).build();
    }
    
    @DELETE @Path("/{customerID}")
    public Response delete(@PathParam("customerID")String customerID) 
            throws NotFoundException, InvalidException, ServerInternalException {
        service.delete(customerID);
        
        return Response.status(Response.Status.OK).build();
    }
    
//    @GET @Path("/async/{jndi}/{message}")
//    public Response sendAsync(@PathParam("jndi") String jndi, @PathParam("message") String message) {
//        service.sendAsync(jndi, message);
//        
//        return Response.ok().build();
//    }
}
