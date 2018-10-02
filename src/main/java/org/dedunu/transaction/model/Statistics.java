package org.dedunu.transaction.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Statistics object was created to serve this as an object
 * from /statistics endpoint in TransactionController.
 */
public class Statistics {

    private double max;
    private double min;
    private double sum;
    private long count;

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }


    /**
     * This was annotated in order to recognized by Spring framework.
     * In case of zero count, average will be zero too.
     *
     * @return Average value of the last 60 seconds time period
     */
    @JsonProperty
    public double getAvg() {
        if (count != 0) {
            return sum / count;
        } else {
            return 0;
        }
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
