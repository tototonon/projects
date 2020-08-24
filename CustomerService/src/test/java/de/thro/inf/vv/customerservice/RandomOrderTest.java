package de.thro.inf.vv.customerservice;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RandomOrderTest {

    RandomOrder randomOrder;

    @Before
    public void setUp() throws Exception {
        randomOrder = new RandomOrder();

    }

    @Test
    public void randomOrderGenerator() {
        Assert.assertNotNull(randomOrder);
        /**
         * MAX VALUE.
         */
        float condition = 2000;
        /**
         * MIN VALUE.
         */
        float condition2 = 1;

        Assert.assertNotEquals(condition, randomOrder);
        Assert.assertNotEquals(condition2, randomOrder);
    }
}