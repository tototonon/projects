package de.thro.inf.vv.customerservice;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;
public class RabbitMQSettingsTest {
    private RabbitMQSettings rabbit;


    @Before
    public void setUp() throws Exception {
        rabbit = mock(RabbitMQSettings.class);

    }

    @Test
    public void createConnection() {
        Assert.assertNotNull(rabbit);
    }
    @Test
    public void TestConnection() {


    }
}