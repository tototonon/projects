package de.thro.inf.vv.accountingservice1;


import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Timon Tonon on 14.06.2020.
 * SetUp class with main.
 */
public class SetUp {
    private static Subscriber subscriber;

    /**
     * Main.
     *
     * @param args = arguments passed as Program Arguments.
     * @throws IOException
     * @throws TimeoutException
     */
    public static void main(String[] args) throws IOException {
        subscriber = new Subscriber(args);
        subscriber.consumeMessage();
    }
}
