package org.dedunu.transaction.manager;

import org.dedunu.transaction.model.Statistics;
import org.dedunu.transaction.model.Transaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TransactionManagerTest {

    @Test
    public void aFirstGetTransactionTest() {
        Statistics statistics = TransactionManager.INSTANCE.getStatistics();

        Assert.assertEquals(0, statistics.getCount());
        Assert.assertEquals(0, statistics.getMax(), 0.001);
        Assert.assertEquals(0, statistics.getMin(), 0.001);
        Assert.assertEquals(0, statistics.getAvg(), 0.001);
        Assert.assertEquals(0, statistics.getSum(), 0.001);
    }

    @Test
    public void bOlderTransactionTest() {
        Transaction transaction = new Transaction(1000, System.currentTimeMillis() - 61000);
        TransactionManager.INSTANCE.addTransaction(transaction);
        Statistics statistics = TransactionManager.INSTANCE.getStatistics();

        Assert.assertEquals(0, statistics.getCount());
        Assert.assertEquals(0, statistics.getMax(), 0.001);
        Assert.assertEquals(0, statistics.getMin(), 0.001);
        Assert.assertEquals(0, statistics.getAvg(), 0.001);
        Assert.assertEquals(0, statistics.getSum(), 0.001);
    }

    @Test
    public void cFutureTransactionTest() {
        Transaction transaction = new Transaction(1000, System.currentTimeMillis() + 1000);
        TransactionManager.INSTANCE.addTransaction(transaction);
        Statistics statistics = TransactionManager.INSTANCE.getStatistics();

        Assert.assertEquals(0, statistics.getCount());
        Assert.assertEquals(0, statistics.getMax(), 0.001);
        Assert.assertEquals(0, statistics.getMin(), 0.001);
        Assert.assertEquals(0, statistics.getAvg(), 0.001);
        Assert.assertEquals(0, statistics.getSum(), 0.001);
    }

    @Test
    public void validTransactionTest() {
        Transaction transaction = new Transaction(1000, System.currentTimeMillis());
        TransactionManager.INSTANCE.addTransaction(transaction);
        Statistics statistics = TransactionManager.INSTANCE.getStatistics();

        Assert.assertEquals(1, statistics.getCount());
        Assert.assertEquals(1000, statistics.getMax(), 0.001);
        Assert.assertEquals(1000, statistics.getMin(), 0.001);
        Assert.assertEquals(1000, statistics.getAvg(), 0.001);
        Assert.assertEquals(1000, statistics.getSum(), 0.001);
    }
}
