package de.thro.inf.vv.customerservice;

import java.util.Random;
/**
 * @author Timon Tonon on 20.05.2020
 * Generates random numbers, between 1 and 2000.
 */
public class RandomOrder {

    /**
     * Init the variable amountOrder.
     */
    private float amountOrder;


    /**
     * This method generates a random price for an order.
     *
     * @return amountOrder which contains the randomized price.
     */
    public float randomOrderGenerator() {
        Random random = new Random();

        int max = 2000;
        int min = 1;
        amountOrder += (random.nextFloat() * (max - min) + min);
        return this.amountOrder;
    }


}
