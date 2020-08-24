package de.thro.inf.vv.customerservice;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Timon Tonon, on 20.05.2020
 * This class is for the orders.
 * Every order has it's own OrderID a individual price which is in the variable amountOrder.
 * The variable createDate show the exact date, where the order is generate.
 */
public class Order {

    /**
     * Init variables.
     */
    private final String orderID;
    private float amountOrder;
    private LocalDateTime createDate;
    RandomOrder randomOrder = new RandomOrder();

    /**
     * Constructor.
     */
    public Order() {
        this.orderID = UUID.randomUUID().toString();
        this.createDate = getCreateDate();
        this.amountOrder = randomOrder.randomOrderGenerator();
    }

    /**
     * Getter.
     *
     * @return
     */
    public String getOrderID() {
        return orderID;
    }

    public float getAmount() {
        return amountOrder;
    }

    public LocalDateTime getCreateDate() {
        return LocalDateTime.now();
    }

    /**
     * ToString method to show the objects information.
     *
     * @return string of object values.
     */
    @Override
    public String toString() {
        return "Order{" +
                "ORDER ID = " + orderID +
                ", AMOUNT = " + amountOrder + " €/$" +
                ", DATE = " + createDate +
                '}';
    }
}
