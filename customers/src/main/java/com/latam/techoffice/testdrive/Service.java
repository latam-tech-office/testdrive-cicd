package com.latam.techoffice.testdrive;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.NotFoundException;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Sorts;
import com.mongodb.MongoException;
import com.mongodb.MongoWriteConcernException;
import com.mongodb.MongoWriteException;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.bson.conversions.Bson;

import com.latam.techoffice.testdrive.error.NoContentException;
import com.latam.techoffice.testdrive.error.ServerInternalException;
import com.latam.techoffice.testdrive.model.Customer;
import com.latam.techoffice.testdrive.setup.MongoProvider;
import com.latam.techoffice.testdrive.error.CreateException;
import com.latam.techoffice.testdrive.error.InvalidException;
import static com.latam.techoffice.testdrive.model.converter.ConverterCustomer.*;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

@Stateless
public class Service {

    private static final Logger LOG = Logger.getLogger(Service.class.getName());
    
    public static final String JMS_FACTORY = "java:/AMQConnectionFactory";

    @EJB
    private MongoProvider provider;
    
    public Customer findByID(String ID) throws NotFoundException {
        Document found = findByCustomerID(ID);
        
        return fromDocument(found);
    }
    
    public Collection<Customer> fetchAll() throws NoContentException {
        Collection<Customer> result = new ArrayList<>();
        for(Document document: getCollection().find()
                .sort(Sorts.ascending(Customer.TAG_FIRST_NAME, Customer.TAG_LAST_NAME)))
            result.add(fromDocument(document));
        
        if(result.isEmpty()) throw new NoContentException("there is no customers");
        
        return result;
    }
    
    public String create(Customer customer) 
                                throws CreateException, ServerInternalException {
        Document document = toDocument(customer);
        try {
            getCollection().insertOne(document);
        } catch(MongoWriteConcernException | MongoWriteException e) {
            throw new CreateException("unable to create customer");
        } catch(MongoException ex) {
            throw new ServerInternalException(ex.getMessage());
        }
        
        return document.getObjectId(Customer.TAG_CUSTOMER_ID).toString();
    }

    public void update(Customer customer) 
            throws NotFoundException, InvalidException, ServerInternalException {
        findByCustomerID(customer);
        try {
            UpdateResult result = getCollection().updateOne(customerID(customer),
                    new Document("$set", customerHeadless(customer)));
            
        } catch(MongoWriteConcernException | MongoWriteException ex) {
            throw new InvalidException("unable to update customer");
        } catch(MongoException ex) {
            throw new ServerInternalException(ex.getMessage());
        }
    }
    
    public void delete(String customerID) 
                throws NotFoundException, InvalidException, ServerInternalException {
        Document found = findByCustomerID(customerID);
        try {
            DeleteResult result = getCollection().deleteOne(found);
        } catch(MongoWriteException | MongoWriteConcernException ex) {
            throw new InvalidException("unable to delete customer");
        } catch(MongoException ex) {
            throw new ServerInternalException(ex.getMessage());
        }
    }
    
    
    private Document findByCustomerID(Customer customer) throws NotFoundException, 
                                                                InvalidException {
        if(customer == null) 
            throw new InvalidException(String.format("customer is null"));
        
        if(customer.getCustomerID() == null)
            throw new InvalidException(String.format("customer ID is null"));
        
        return findByCustomerID(customer.getCustomerID());
    }
    
    
    private Document findByCustomerID(String customerID) throws NotFoundException {
        Bson filter = new Document().append(Customer.TAG_CUSTOMER_ID, 
                                                        new ObjectId(customerID));
        Document found = getCollection().find(filter).first();
        if(found == null) throw new NotFoundException(
                            String.format("customer %s not found", customerID));
        
        return found;
    }
    
    private MongoCollection<Document> getCollection() {
        return provider.getDatabase().getCollection(Customer.COLLECTION);
    }

    public void notify(String jndi, String message) {
        LOG.log(Level.INFO, ">>> Destination: {0}  Message: {1}",
                new Object[] {jndi, message});
        
        try(Connection connection = getConnection(); 
                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE)) {
            Context context = new InitialContext();
            Destination destination = (Destination)context.lookup(jndi);
            
            MessageProducer producer = session.createProducer(destination);
            TextMessage textMessage = session.createTextMessage(message);
            producer.send(textMessage);
            
        } catch(JMSException ex) {
            LOG.log(Level.SEVERE, "### JMS EXCEPTION:{0}", ex.getMessage());
        } catch(NamingException ex) {
            LOG.log(Level.SEVERE, "### NAMING EXCEPTION:{0}", ex.getMessage());
        } 
    }
    
    private Connection getConnection() throws JMSException {
        Connection connection = null;
        try {
            Context context = new InitialContext();
            ConnectionFactory factory = (ConnectionFactory)context.lookup(JMS_FACTORY);
            connection = factory.createConnection(); connection.start();
            
        } catch(NamingException ex) {
            LOG.log(Level.SEVERE, "### NAMING EXCEPTION:{0}", ex.getMessage());
        }
        
        return connection;
    }
}
