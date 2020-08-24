package de.thro.inf.vv.customerservice;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

//TODO: THINK ABOUT TO USE TOPIC OR QUEUE// THINK ABOUT PRODUCER CONSUMER// LOGIC OF THIS CLASS WHICH CAN BE USEFUL
//TODO: FOR OTHER CLASSES WHICH NEED THIS TOO.

/**
 * This class creates the connection to RabbitMQ.
 * Send Data via Queue to the database of RabbitMQ.
 * Need this to communicate with all other canals.
 */

public class RabbitMQConnection {
    public static final String OPEN_ORDERS = "openOrders";
    private static final Logger logger = Logger.getLogger(RabbitMQConnection.class.getName());


    /**
     * RABBIT
     * MQ
     * CONNECTION
     */
    public void createConnection(String message) {

        ConnectionFactory factory = new ConnectionFactory();
        try {
            // Create the jsonFactory with an object mapper to serialize object to json
            // Create the jsonFactory with an object mapper to serialize object to json


            //TODO create Queue or topic for specific need
            factory.setUri("amqp://guest:guest@localhost");
            factory.setConnectionTimeout(30000);
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare(OPEN_ORDERS, true, false, false, null);
            channel.basicPublish("", OPEN_ORDERS, null, message.getBytes());

            Thread.sleep(4000);
        } catch (URISyntaxException e) {
            logger.log(Level.WARNING, "URISyntaxException", e);
        } catch (NoSuchAlgorithmException e) {
            logger.log(Level.WARNING, "NoSuchAlgorithmException", e);
        } catch (KeyManagementException e) {
            logger.log(Level.WARNING, "KeyManagementException", e);
        } catch (IOException e) {
            logger.log(Level.WARNING, "IOException", e);
        } catch (TimeoutException e) {
            logger.log(Level.WARNING, "TimeoutException", e);
        } catch (InterruptedException e) {
            logger.info("Thread interrupted");
        }
    }
}
