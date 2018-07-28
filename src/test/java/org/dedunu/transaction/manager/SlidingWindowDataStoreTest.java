package org.dedunu.transaction.manager;

import org.dedunu.transaction.model.Statistics;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class SlidingWindowDataStoreTest {

    private SlidingWindowDataStore slidingWindowDataStore;

    private Statistics statistics;

    @Test
    public void firstDataPointTest() {
        slidingWindowDataStore = new SlidingWindowDataStore();
        slidingWindowDataStore.updateStatistics(1000, (System.currentTimeMillis()/1000));

        statistics = slidingWindowDataStore.getStatistics();

        Assert.assertEquals(1000, statistics.getSum(), 0.001);
        Assert.assertEquals(1000, statistics.getMax(), 0.001);
        Assert.assertEquals(1000, statistics.getMin(), 0.001);
        Assert.assertEquals(1000, statistics.getAvg(), 0.001);
        Assert.assertEquals(1, statistics.getCount());
    }

    @Test
    public void multipleDataPointsTest() {
        slidingWindowDataStore = new SlidingWindowDataStore();
        slidingWindowDataStore.updateStatistics(1000, (System.currentTimeMillis()/1000));
        slidingWindowDataStore.updateStatistics(1000, (System.currentTimeMillis()/1000)-1);
        slidingWindowDataStore.updateStatistics(1000, (System.currentTimeMillis()/1000)-2);
        slidingWindowDataStore.updateStatistics(1000, (System.currentTimeMillis()/1000)-3);
        slidingWindowDataStore.updateStatistics(1000, (System.currentTimeMillis()/1000)-4);

        statistics = slidingWindowDataStore.getStatistics();

        Assert.assertEquals(5000, statistics.getSum(), 0.001);
        Assert.assertEquals(1000, statistics.getMax(), 0.001);
        Assert.assertEquals(1000, statistics.getMin(), 0.001);
        Assert.assertEquals(1000, statistics.getAvg(), 0.001);
        Assert.assertEquals(5, statistics.getCount());
    }


    @Test
    public void minDataPointsTest() {
        slidingWindowDataStore = new SlidingWindowDataStore();

        slidingWindowDataStore.updateStatistics(1000, (System.currentTimeMillis()/1000));
        slidingWindowDataStore.updateStatistics(10, (System.currentTimeMillis()/1000)-1);
        slidingWindowDataStore.updateStatistics(1, (System.currentTimeMillis()/1000)-2);
        slidingWindowDataStore.updateStatistics(1000, (System.currentTimeMillis()/1000)-3);
        slidingWindowDataStore.updateStatistics(1000, (System.currentTimeMillis()/1000)-4);

        statistics = slidingWindowDataStore.getStatistics();

        Assert.assertEquals(1, statistics.getMin(), 0.001);

        slidingWindowDataStore.updateStatistics(0.5, (System.currentTimeMillis()/1000)-3);

        statistics = slidingWindowDataStore.getStatistics();

        Assert.assertEquals(0.5, statistics.getMin(), 0.001);
    }


    @Ignore
    public void timeWindowTestUniformTest() throws InterruptedException {
        slidingWindowDataStore = new SlidingWindowDataStore();

        for (int i = 0; i < 70; i++) {
            slidingWindowDataStore.updateStatistics(1000, (System.currentTimeMillis() / 1000));

            if (i > 60) {
                statistics = slidingWindowDataStore.getStatistics();

                Assert.assertEquals(60000, statistics.getSum(), 0.001);
                Assert.assertEquals(1000, statistics.getMax(), 0.001);
                Assert.assertEquals(1000, statistics.getMin(), 0.001);
                Assert.assertEquals(1000, statistics.getAvg(), 0.001);
                Assert.assertEquals(60, statistics.getCount());
            }

            Thread.sleep(1000);
        }

    }

    @Ignore
    public void timeWindowExpiryTest() throws InterruptedException {
        slidingWindowDataStore = new SlidingWindowDataStore();

        for (int i = 0; i < 63; i++) {
            Thread.sleep(1000);
            slidingWindowDataStore.updateStatistics(1000, (System.currentTimeMillis() / 1000));
            slidingWindowDataStore.updateStatistics(1000, (System.currentTimeMillis() / 1000) - 60);
        }

        Thread.sleep(500);

        statistics = slidingWindowDataStore.getStatistics();

        Assert.assertEquals(59000, statistics.getSum(), 0.001);
        Assert.assertEquals(1000, statistics.getMax(), 0.001);
        Assert.assertEquals(1000, statistics.getMin(), 0.001);
        Assert.assertEquals(1000, statistics.getAvg(), 0.001);
        Assert.assertEquals(59, statistics.getCount());
    }

    @Test
    public void doubleOverflowTest() {
        slidingWindowDataStore = new SlidingWindowDataStore();

        slidingWindowDataStore.updateStatistics(Double.MAX_VALUE, (System.currentTimeMillis() / 1000));
        slidingWindowDataStore.updateStatistics(Double.MAX_VALUE, (System.currentTimeMillis() / 1000));
        slidingWindowDataStore.updateStatistics(Double.MAX_VALUE, (System.currentTimeMillis() / 1000));
        slidingWindowDataStore.updateStatistics(Double.MAX_VALUE, (System.currentTimeMillis() / 1000));
        slidingWindowDataStore.updateStatistics(Double.MAX_VALUE, (System.currentTimeMillis() / 1000));


        statistics = slidingWindowDataStore.getStatistics();

        Assert.assertEquals(Double.POSITIVE_INFINITY, statistics.getSum(), 0.001);
    }


    @Ignore
    public void memoryRestTest() throws InterruptedException {
        slidingWindowDataStore = new SlidingWindowDataStore();

        slidingWindowDataStore.updateStatistics(1000, (System.currentTimeMillis() / 1000));

        Thread.sleep(61000);

        slidingWindowDataStore.updateStatistics(2000, (System.currentTimeMillis() / 1000));

        statistics = slidingWindowDataStore.getStatistics();

        Assert.assertEquals(2000, statistics.getSum(), 0.001);
    }
}
