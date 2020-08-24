package de.thro.inf.vv.customerservice;

import com.github.javafaker.Faker;

import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.UUID;


public class Customer {

    /**
     * Init local variables.
     */
    private final String customerID;
    private String email;
    private String name;
    private float amountOrder;


    /**
     * Init random variable of class Random, need this for randomize.
     */
    private static Random random = new Random();
    //private static final Logger logger = Logger.getLogger(Customer.class.getName());

    /**
     * Init object variables.
     */
    Faker faker;
    RabbitMQConnection createConnection = new RabbitMQConnection();
    Order order;

    /**
     * Enum for the salutation.
     */
    enum salutation {
        Company, Mr, Mrs, Div;
    }

    /**
     * Constructor.
     */

    public Customer()  {
        incomingOrder();
        this.customerID = UUID.randomUUID().toString();
        this.amountOrder = order.getAmount();
        randomNameGenerator();
        randomEmailGenerator();
        createConnection.createConnection(toString());
    }

    /**
     * Method to generate randomized first and lastname.
     *
     * @return the full name.
     */
    public String randomNameGenerator() {
        faker = new Faker();
        name = faker.name().fullName();
        return name;
    }

    /**
     * Method to generate randomized EmailAccounts.
     *
     * @return email address.
     */
    public String randomEmailGenerator() {
        faker = new Faker();
        email = faker.internet().emailAddress();
        return email;
    }

    /**
     * This method simulates an incoming order.
     */
    public void incomingOrder() {
        order = new Order();
    }

    /**
     * Getter.
     *
     * @return
     */
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    /**
     * ToString method to show the objects information.
     *
     * @return string of object values.
     */
    @Override
    public String toString() {
        return "Kunde{" +
                "customerID = '" + customerID + '\'' +
                ", salutation = '" + salutation.Company + "/" + salutation.Mr + "/" + salutation.Mrs + "/" + salutation.Div + '\'' +
                ", name = '" + name + '\'' +
                ", emailAdress = '" + email + '\'' +
                ", amountOrder = '" + amountOrder + "€/$" + '\'' +
                ", With " + order +
                '}';
    }
}
