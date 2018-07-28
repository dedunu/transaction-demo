package org.dedunu.transaction.manager;

import org.dedunu.transaction.model.Statistics;
import org.dedunu.transaction.model.Transaction;

public enum TransactionManager {

    // TransactionManger is an enum to achieve singleton pattern
    INSTANCE;

    private final SlidingWindowDataStore dataStore = new SlidingWindowDataStore();

    public boolean addTransaction(Transaction transaction) {
        if ((transaction.getTimestamp() - System.currentTimeMillis()) / 1000 <= 60) {
            return dataStore.updateStatistics(transaction.getAmount(), transaction.getTimestamp() / 1000);
        } else {
            return false;
        }
    }

    public Statistics getStatistics() {
        return dataStore.getStatistics();
    }
}
