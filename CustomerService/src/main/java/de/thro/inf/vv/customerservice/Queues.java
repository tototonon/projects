package de.thro.inf.vv.customerservice;
/**
 * @author Timon Tonon on 05.06.2020
 * Queue class.
 */
public class Queues {
    private static final String OPEN_ORDERS = "openOrders";
    private static final String APPROVED_ORDERS = "approvedOrders";
    private static final String NEEDS_APPROVAL = "needsApproval";
    private static final String DECLINED_ORDERS = "declinedOrders";
    private Queues() {
        throw new IllegalStateException("Utility class");
    }

    public static String getDeclinedOrders() {
        return DECLINED_ORDERS;
    }

    public static String getOpenOrders() {
        return OPEN_ORDERS;
    }

    public static String getApprovedOrders() { return APPROVED_ORDERS; }

    public static String getNeedsApproval() {
        return NEEDS_APPROVAL;
    }
}
