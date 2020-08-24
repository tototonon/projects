package de.thro.inf.vv.accountingservice1;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import de.thro.inf.vv.customerservice.Customer;
import de.thro.inf.vv.customerservice.Queues;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SubscriberTest {
    private Subscriber subscriber;
    Connection connection;
    Channel channel;
    ConnectionFactory factory;

    @Before
    public void setUp() throws Exception {
        subscriber = mock(Subscriber.class);
        connection = mock(Connection.class);
        channel = mock(Channel.class);
        factory = mock(ConnectionFactory.class);

    }

    @Test
    public void commandLineArgs() {
    }

    @Test
    public void testSomething() throws IOException, TimeoutException {

        connection = factory.newConnection();
        Customer customer = new Customer();
        String customerMsg = customer.toString();
        channel.queueDeclare(Queues.getApprovedOrders(), true, false, false, null);
        channel.basicPublish("", Queues.getApprovedOrders(), null, customerMsg.getBytes("UTF-8"));
        Assert.assertEquals(customerMsg, customer.toString());
        Assert.assertNotNull(Queues.getApprovedOrders());
    }



    @Test
    public void close() {

        }
    }
