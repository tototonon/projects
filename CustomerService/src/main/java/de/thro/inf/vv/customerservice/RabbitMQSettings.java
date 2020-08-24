package de.thro.inf.vv.customerservice;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import de.thro.inf.vv.core.JSONMessage;
import org.apache.commons.cli.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class creates the connection to RabbitMQ.
 * To connect it need the path to JSon Config file, where the login data is.
 * Set the interval in which orders are generate.
 * Send Data via Queue to the database of RabbitMQ.
 */
public class RabbitMQSettings {
    /**
     * Init variables.
     */
    private static int interval;
    private static String[] args;
    private static final Gson gson = new Gson();
    private static CommandLine cmd;
    private static Path pathtoJson;
    private String message;
    private static Connection connection;
    private static Channel channel;
    public static final String OPEN_ORDERS = "openOrders";
    private static final Logger logger = Logger.getLogger(RabbitMQSettings.class.getName());


    public final Runnable randomOrder = () -> {
        try {


            Customer customer = new Customer();
            message = customer.toString();
            RabbitMQSettings.channel.basicPublish("", RabbitMQSettings.OPEN_ORDERS, null, message.getBytes("UTF-8"));
        } catch (IOException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
        }
    };

    /**
     * CommandLine arguments.
     */

    public static void commandLine() {
        Options options = new Options();
        options.addOption(new Option("p", "path", true, "Path to Json config"));
        options.addOption(new Option("i", "interval", true, "Interval of the orders"));
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("ant", options);
        CommandLineParser parser = new DefaultParser();
        try {
            cmd = parser.parse(options, args);

        } catch (ParseException e) {
            logger.log(Level.WARNING, e.getMessage());

        }
    }

    /**
     * Get the Path to JSonConfig File.
     *
     * @param cmd
     */

    public static void getPath(CommandLine cmd) {
        RabbitMQSettings.cmd = cmd;
        try {
            pathtoJson = Paths.get(cmd.getOptionValue("p"));

            if (pathtoJson.toFile().exists()) {
                logger.log(Level.INFO,"Path found is :" + pathtoJson);
            }
            if (!pathtoJson.toFile().exists()) {
                logger.info("Path to Json file not found !");
                throw new NullPointerException();
            }

        } catch (NullPointerException e) {
            logger.log(Level.INFO,"Use user.dir");
            File file = new File(System.getProperty("user.dir"));
            File[] path = file.listFiles();
            try {
                assert path != null;
            } catch (ArrayIndexOutOfBoundsException ex) {
                logger.log(Level.WARNING, "No Json Config found");
                System.exit(1);
            }

        }
    }

    /**
     * RABBIT
     * MQ
     * CONNECTION
     */

    public static void createConnection() {
        ConnectionFactory factory = new ConnectionFactory();


        try {
            Customer customer = new Customer();
            byte[] body = customer.toString().getBytes("UTF-8");
            JSONMessage msgconnection = gson.fromJson(new FileReader(pathtoJson.toString()), JSONMessage.class);
            factory.setUri(msgconnection.getProtocol() + "://" + msgconnection.getUsername() + ":" + msgconnection.getPassword() + "@" + msgconnection.getIp());
            factory.setConnectionTimeout(30000);
            connection = factory.newConnection();
            channel = connection.createChannel();
            channel.queueDeclare(Queues.getOpenOrders(), true, false, false, null);
            channel.basicPublish("", Queues.getOpenOrders(), null, body);

            Thread.sleep(1000);

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
            Thread.currentThread().interrupt();
            logger.info("Thread interrupted");
        }

    }

    /**
     * Set Interval for the orders.
     *
     * @param cmd
     */

    public static void getInterval(CommandLine cmd) {
        RabbitMQSettings.cmd = cmd;
        try {
            interval = Integer.parseInt(cmd.getOptionValue("i"));
        } catch (NumberFormatException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
            logger.info("Usage -i for interval");
            throw new NumberFormatException();
        }
    }


    public static Channel getChannel() {
        return channel;
    }

    /**
     * Main.
     *
     * @param args
     */
    public RabbitMQSettings(String[] args) {

        this.args = args;
        commandLine();
        getInterval(cmd);
        getPath(cmd);
        Customer customer = new Customer();

        System.out.println(customer.toString());

        createConnection();

        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(randomOrder, interval, interval, TimeUnit.SECONDS);
    }


}






