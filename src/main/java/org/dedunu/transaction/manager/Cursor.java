package org.dedunu.transaction.manager;

public class Cursor {

    private int cursorSize;
    private int cursor = 0;

    /**
     * This returns a Cursor object. Cursor will rotate after
     * the give cursorSize.
     *
     * @param cursorSize Cursor rotation threshold
     */
    public Cursor(int cursorSize) {
        this.cursorSize = cursorSize;
    }

    /**
     * Cursor will be moved on step further. If it has reached
     * cursorSize, it will start again from zero.
     */
    public void move() {
        if (cursor + 1 < cursorSize) {
            cursor++;
        } else {
            cursor = 0;
        }
    }

    /**
     * This returns array location for a given number of seconds ago.
     *
     * @param numberOfSeconds number of Seconds client wants to access
     * @return the array location numberOfSeconds ago
     */
    public int getPosition(int numberOfSeconds) {
        if (numberOfSeconds >= cursorSize) {
            return -1;
        } else if (cursor - numberOfSeconds >= 0) {
            return cursor - numberOfSeconds;
        } else {
            return cursor + cursorSize - numberOfSeconds;
        }
    }
}
