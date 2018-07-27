package org.dedunu.transaction.manager;

import org.dedunu.transaction.model.Statistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SlidingWindowDataStore {

    Logger logger = LoggerFactory.getLogger(SlidingWindowDataStore.class);

    // ReentrantReadWrite lock is using fair to treat transactions fairly.
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);

    private Lock readLock = readWriteLock.readLock();
    private Lock writeLock = readWriteLock.writeLock();

    private final int PERIOD = 60;

    private double[] sumWindow = new double[PERIOD];
    private double[] maxWindow = new double[PERIOD];
    private double[] minWindow = new double[PERIOD];

    private long[] countWindow = new long[PERIOD];

    private long pointer;

    private Cursor cursor = new Cursor(PERIOD);

    public SlidingWindowDataStore() {
        pointer = System.currentTimeMillis() / 1000;
        Arrays.fill(minWindow, -1);
    }

    public boolean updateStatistics(double amount, long timestamp) {
        writeLock.lock();

        if (pointer == 0) {
            pointer = timestamp;
        }

        if (timestamp - (System.currentTimeMillis() / 1000) > PERIOD ||
                timestamp > (System.currentTimeMillis() / 1000) ||
                pointer - timestamp > PERIOD) {
            /*
             *  Checking for multiple reasons:
             *      whether transaction is older than the period of time
             *      whether the transaction is from future
             *      whether the transaction is older than current timestamp (pointer)
             *
             *  If so release the lock and return an error
             */
            writeLock.unlock();
            return false;
        }

        long timestampDiff = timestamp - pointer;

        if (timestampDiff > PERIOD) {
            // If timeStampDiff is bigger than PERIOD, program will reinitialize variables
            reset();

            pointer = timestamp;
        } else if (timestampDiff < PERIOD && timestampDiff > 0) {
            // Allocating new memory locations while cleaning up old values.
            for (int i = 0; i < timestampDiff; i++) {
                moveCursor();
            }

            pointer = timestamp;
        }

        int position = cursor.getPosition((int) Math.abs(timestamp - pointer));

        if (position == -1) {
            // Invalid position release the write lock and return an error
            logger.error("cursor.getPosition returned -1");
            writeLock.unlock();
            return false;
        }

        sumWindow[position] = sumWindow[position] + amount;
        maxWindow[position] = Math.max(maxWindow[position], amount);

        if (minWindow[position] == -1) {
            minWindow[position] = amount;
        } else {
            minWindow[position] = Math.min(minWindow[position], amount);
        }

        countWindow[position] = countWindow[position] + 1;

        writeLock.unlock();

        return true;
    }

    public Statistics getStatistics() {
        readLock.lock();

        Statistics result = new Statistics();

        long currentSystemTimestamp = System.currentTimeMillis() / 1000;
        long timestampDiff = PERIOD - (currentSystemTimestamp - pointer);

        double sum = 0;
        double min = -1;
        double max = 0;
        long count = 0;

        if (timestampDiff > 0 && timestampDiff <= PERIOD) {
            for (int i = 0; i < timestampDiff; i++) {
                sum = sum + sumWindow[cursor.getPosition(i)];
                max = Math.max(max,maxWindow[cursor.getPosition(i)]);

                if (minWindow[cursor.getPosition(i)] != -1 && min > 0) {
                    min = Math.min(min, minWindow[cursor.getPosition(i)]);
                }
                if (minWindow[cursor.getPosition(i)] != -1 && min < 0) {
                    min = minWindow[cursor.getPosition(i)];
                }

                count = count + countWindow[cursor.getPosition(i)];
            }
        }

        readLock.unlock();

        if (min == -1) {
            min = 0;
        }

        result.setSum(sum);
        result.setMax(max);
        result.setMin(min);
        result.setCount(count);

        return result;
    }

    /**
     * Resets all the variables in the data store. This will be called when
     * cursor needs to be moved more than the period of times.
     *
     * ex:
     *  If data store didn't receive any data point for last 60 seconds. All the
     *  data in memory has to be wiped. This will start it do it.
     */
    public void reset() {
        sumWindow = new double[PERIOD];
        maxWindow = new double[PERIOD];
        minWindow = new double[PERIOD];

        countWindow = new long[PERIOD];

        pointer = System.currentTimeMillis() / 1000;

        cursor = new Cursor(PERIOD);

        Arrays.fill(minWindow, -1);
    }

    public void moveCursor() {
        cursor.move();
        sumWindow[cursor.getPosition(0)] = 0;
        maxWindow[cursor.getPosition(0)] = 0;
        minWindow[cursor.getPosition(0)] = -1;
        countWindow[cursor.getPosition(0)] = 0;
    }

}
