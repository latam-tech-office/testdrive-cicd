package com.latam.techoffice.testdrive.model.converter;

import com.latam.techoffice.testdrive.model.Customer;
import java.util.logging.Logger;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 * Convert all the contents into a Mongo's Document specific format 
 * 
 * @author Mauricio "Maltron" Leal <maltron at gmail dot com>
 */
public class ConverterCustomer {

    private static final Logger LOG = Logger.getLogger(ConverterCustomer.class.getName());
    
    public static Document toDocument(Customer customer) {
        return new Document()
                .append(Customer.TAG_CUSTOMER_ID, new ObjectId(customer.getCustomerID()))
                .append(Customer.TAG_FIRST_NAME, customer.getFirstName())
                .append(Customer.TAG_LAST_NAME, customer.getLastName());
    }
    
    public static Customer fromDocument(Document document) {
        return new Customer(document.getObjectId(Customer.TAG_CUSTOMER_ID).toString(),
                document.getString(Customer.TAG_FIRST_NAME),
                document.getString(Customer.TAG_LAST_NAME));
    }
    
    public static Document customerID(Customer customer) {
        return customerID(customer.getCustomerID());
    }
    
    public static Document customerID(String customerID) {
        return new Document().append(Customer.TAG_CUSTOMER_ID, new ObjectId(customerID));
    }
    
    public static Document customerHeadless(Customer customer) {
        return new Document().append(Customer.TAG_FIRST_NAME, customer.getFirstName())
                .append(customer.TAG_LAST_NAME, customer.getLastName());
    }
    
}
