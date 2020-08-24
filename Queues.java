package de.thro.inf.vv.core;

public class Queues {
    private static final String APPROVED_CUSTOMERS = "approvedCustomers";
    private static final String DECLINED_CUSTOMERS = "declinedCustomers";
    private static final String EXCHANGE_NAME = "logs";

    public Queues() {
        throw new IllegalStateException("Utility class");
    }

    public static String getDeclinedCustomers() {
        return DECLINED_CUSTOMERS;
    }


    public static String getApprovedCustomers() { return APPROVED_CUSTOMERS; }

    public static String getExchangeName() {
        return EXCHANGE_NAME;
    }
}
