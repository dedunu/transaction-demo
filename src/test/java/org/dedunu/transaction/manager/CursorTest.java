package org.dedunu.transaction.manager;

import org.junit.Assert;
import org.junit.Test;

public class CursorTest {

    private Cursor cursor;

    @Test
    public void moveAhead() {
        cursor = new Cursor(60);
        cursor.move();
        Assert.assertEquals(1, cursor.getPosition(0));
    }

    @Test
    public void moveAheadTwice() {
        cursor = new Cursor(60);
        cursor.move();
        cursor.move();
        Assert.assertEquals(2, cursor.getPosition(0));
    }

    @Test
    public void moveAheadEdgeRotation() {
        cursor = new Cursor(5);
        for (int i = 0; i < 5; i++) {
            cursor.move();
        }
        Assert.assertEquals(0, cursor.getPosition(0));
    }

    @Test
    public void moveAheadEdge() {
        cursor = new Cursor(5);
        for (int i = 0; i < 4; i++) {
            cursor.move();
        }
        Assert.assertEquals(4, cursor.getPosition(0));
    }

    @Test
    public void moveAheadMultipleEdgeRotation() {
        cursor = new Cursor(10);
        for (int i = 0; i < 100; i++) {
            cursor.move();
        }
        Assert.assertEquals(0, cursor.getPosition(0));
    }

    @Test
    public void invalidGetPosition() {
        cursor = new Cursor(10);
        Assert.assertEquals(-1, cursor.getPosition(10));
    }

    @Test
    public void initialGetPositionValidation() {
        cursor = new Cursor(10);
        Assert.assertEquals(0, cursor.getPosition(0));
        Assert.assertEquals(9, cursor.getPosition(1));
        Assert.assertEquals(8, cursor.getPosition(2));
        Assert.assertEquals(7, cursor.getPosition(3));
        Assert.assertEquals(6, cursor.getPosition(4));
        Assert.assertEquals(5, cursor.getPosition(5));
        Assert.assertEquals(4, cursor.getPosition(6));
        Assert.assertEquals(3, cursor.getPosition(7));
        Assert.assertEquals(2, cursor.getPosition(8));
        Assert.assertEquals(1, cursor.getPosition(9));
        Assert.assertEquals(-1, cursor.getPosition(10));
    }

    @Test
    public void moveAheadGetPositionValidation() {
        cursor = new Cursor(10);
        for (int i = 0; i < 9; i++) {
            cursor.move();
        }
        Assert.assertEquals(9, cursor.getPosition(0));
        Assert.assertEquals(8, cursor.getPosition(1));
        Assert.assertEquals(7, cursor.getPosition(2));
        Assert.assertEquals(6, cursor.getPosition(3));
        Assert.assertEquals(5, cursor.getPosition(4));
        Assert.assertEquals(4, cursor.getPosition(5));
        Assert.assertEquals(3, cursor.getPosition(6));
        Assert.assertEquals(2, cursor.getPosition(7));
        Assert.assertEquals(1, cursor.getPosition(8));
        Assert.assertEquals(0, cursor.getPosition(9));
        Assert.assertEquals(-1, cursor.getPosition(10));
    }

    @Test
    public void moveAheadRotationGetPositionValidation() {
        cursor = new Cursor(10);
        for (int i = 0; i < 10; i++) {
            cursor.move();
        }
        Assert.assertEquals(0, cursor.getPosition(0));
        Assert.assertEquals(9, cursor.getPosition(1));
        Assert.assertEquals(8, cursor.getPosition(2));
        Assert.assertEquals(7, cursor.getPosition(3));
        Assert.assertEquals(6, cursor.getPosition(4));
        Assert.assertEquals(5, cursor.getPosition(5));
        Assert.assertEquals(4, cursor.getPosition(6));
        Assert.assertEquals(3, cursor.getPosition(7));
        Assert.assertEquals(2, cursor.getPosition(8));
        Assert.assertEquals(1, cursor.getPosition(9));
        Assert.assertEquals(-1, cursor.getPosition(10));
    }

    @Test
    public void moveAheadEdgeGetPositionValidation() {
        cursor = new Cursor(10);
        cursor.move();
        Assert.assertEquals(1, cursor.getPosition(0));
        Assert.assertEquals(0, cursor.getPosition(1));
        Assert.assertEquals(9, cursor.getPosition(2));
        Assert.assertEquals(8, cursor.getPosition(3));
        Assert.assertEquals(7, cursor.getPosition(4));
        Assert.assertEquals(6, cursor.getPosition(5));
        Assert.assertEquals(5, cursor.getPosition(6));
        Assert.assertEquals(4, cursor.getPosition(7));
        Assert.assertEquals(3, cursor.getPosition(8));
        Assert.assertEquals(2, cursor.getPosition(9));
        Assert.assertEquals(-1, cursor.getPosition(10));
    }
}
