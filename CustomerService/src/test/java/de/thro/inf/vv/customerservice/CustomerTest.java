package de.thro.inf.vv.customerservice;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CustomerTest {

    Customer customer;
    @Before
    public void setUp() throws Exception {
        customer = new Customer();
    }

    @Test
    public void randomNameGenerator() {
        Assert.assertNotNull(customer.getName());
        Assert.assertEquals(customer.randomNameGenerator(),customer.getName());
    }

    @Test
    public void randomEmailGenerator() {
        Assert.assertNotNull(customer.getEmail());
        Assert.assertEquals(customer.randomEmailGenerator(),customer.getEmail());
    }

    @Test
    public void incomingOrder() {
        Order order = new Order();
        Assert.assertNotNull(order);
    }
}