package de.thro.inf.vv.customerservice;
/**
 * @author Timon Tonon on 10.06.2020
 * SetUp class with Main.
 */

public class SetUp {
    private static RabbitMQSettings rabbit;

    /**
     * Class setUp with Main method to create instance of Customer class.
     *
     * @param args
     */
    public static void main(String[] args) {
      rabbit = new RabbitMQSettings(args);
    }

    }
