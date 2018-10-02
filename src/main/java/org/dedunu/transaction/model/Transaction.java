package org.dedunu.transaction.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Transaction object was created to use as an object
 * in /transaction endpoint in TransactionController.
 */
public class Transaction implements Serializable {
    private long timestamp;
    private double amount;

    public Transaction(@JsonProperty("amount") double amount, @JsonProperty("timestamp") long timestamp) {
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
        return "Transaction[ Amount: " + amount + ", Timestamp: " + timestamp + " ]";
    }
}
