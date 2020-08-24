package de.thro.inf.vv.accountingservice1;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import de.thro.inf.vv.customerservice.Customer;
import de.thro.inf.vv.customerservice.Queues;
import de.thro.inf.vv.customerservice.RabbitMQSettings;
import org.apache.commons.cli.*;

import java.io.Closeable;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Timon Tonon on 14.06.2020.
 * This class consumes the messages of the OPEN_ORDERS Queue and writes the orders in corresponding Queues.
 */

public class Subscriber implements Closeable {
    /**
     * Init variables.
     */
    private static CommandLine cmd;
    private static String[] args;
    private static Connection connection;
    private static Channel channel;

    private final static Logger logger = Logger.getLogger(Subscriber.class.getName());

    /**
     * CommandLine method for the arguments passed to this class.
     * Path: the path to Json Config.
     * Interval: the interval in which, random orders will be generate.
     */
    public static void commandLineArgs() {
        Options options = new Options();
        options.addOption(new Option("p", "path", true, "Path to Json File"));
        options.addOption(new Option("i", "interval", true, "Interval"));
        CommandLineParser parser = new DefaultParser();
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            logger.log(Level.WARNING, "Parsing failed");
            System.exit(1);
        }
    }

    /**
     * Method consumeMessage, creates connection with OPEN_ORDERS queue.
     * If the price of the order is below 500 it writes the order to the APPROVED_ORDERS queue.
     * If the price is above 500, it writes the order to the NEEDS_APPROVAL queue.
     */
    public void consumeMessage() throws IOException {
        ConnectionFactory factory = new ConnectionFactory();
        try {
            connection = factory.newConnection();
            channel = connection.createChannel();
            channel.basicConsume(Queues.getOpenOrders(), (consumerTag, message) -> {
                String m = new String(message.getBody(), StandardCharsets.UTF_8);
                Customer customer = new Customer();
                String customerMsg = customer.toString();
                if (customer.getAmountOrder() < 500) {
                    customer.setApprovedBy(new String[]{"Buchhaltung"});
                    logger.info("Approval need!!! The price is :" + customer.getAmountOrder());

                    channel.queueDeclare(Queues.getApprovedOrders(), true, false, false, null);
                    channel.basicPublish("", Queues.getApprovedOrders(), null, customerMsg.getBytes("UTF-8"));

                    logger.log(Level.INFO, () -> "Written in Queue " + Queues.getApprovedOrders() + ":" + customerMsg);
                } else {
                    logger.info("NeedsApproval !!! The price is " + customer.getAmountOrder());

                    channel.queueDeclare(Queues.getNeedsApproval(), true, false, false, null);
                    channel.basicPublish("", Queues.getNeedsApproval(), null, m.getBytes());
                    logger.log(Level.INFO, () -> "Written in Queue " + Queues.getNeedsApproval() + ":" + m);
                }
            }, consumerTag -> {
            });


        } catch (IOException e) {
            Thread.currentThread().interrupt();
            close();
            logger.info("Thread interrupted");
            System.exit(1);
        } catch (TimeoutException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);

        }
    }


    /**
     * Constructor.
     *
     * @param args = arguments passed by Main method in SetUp.
     */
    public Subscriber(String[] args) {

        this.args = args;
        commandLineArgs();
        RabbitMQSettings.getInterval(cmd);
        RabbitMQSettings.getPath(cmd);

    }

    @Override
    public void close() throws IOException {
        connection.close();
        try {
            channel.close();
        } catch (TimeoutException e) {
            logger.log(Level.SEVERE, "Can not close channel", e);
        }
    }
}





