package de.thro.inf.vv.customerservice;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OrderTest {

    Order order;
    Customer customer;

    @Before
    public void setUp() throws Exception {
        order = new Order();

    }
    @Test
    public void testIDNotNull() {
        Assert.assertNotNull(order.getOrderID());
    }
}