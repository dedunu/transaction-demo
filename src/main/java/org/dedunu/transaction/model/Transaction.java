package org.dedunu.transaction.model;

/**
 * Transaction object was created to use as an object
 * in /transaction endpoint in TransactionController.
 */
public class Transaction {
    private long timestamp;
    private double amount;

    public Transaction(double amount, long timestamp) {
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String toString() {
        return "Transaction[ Amount: " + amount + ", Timestamp: " + timestamp +" ]";
    }
}
