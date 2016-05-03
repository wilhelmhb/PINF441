import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Test;

import static org.junit.Assert.*;

public class MorpionTest {
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    @Test
	public void testMove() {
        Morpion p = new Morpion(3,3,3);
        p.move(true, 1, 1, 4);
        Boolean[][] a = new Boolean[3][3];
        a[1][1] = true;
        assertTrue(p.a[1][1]);
        assertEquals(new Long(2 * 81), p.state);
	}

    public static boolean equalArrays(Boolean[][] i, Boolean[][] j) {
        if(i.length != j.length) {
            return false;
        }
        else {
            boolean b = true;
            for(int k = 0; k<i.length; k++) {
                for(int l = 0; l < i[0].length ; l++) {
                    b &= i[k][l]==j[k][l];
                }
            }
            return b;
        }
    }

    @Test
    public void testMove2() {
        Morpion p = new Morpion(3,3,3);
        p.move(true, 1, 1, 4);
        Boolean[][] a = new Boolean[3][3];
        a[1][1] = true;
        assertTrue(equalArrays(p.a,a));
        assertEquals(new Long(2 * 81), p.state);
    }

    @Test
    public void testMove3() {
        Morpion p = new Morpion(3,3,3);
        p.move(false, 1, 1, 4);
        Boolean[][] a = new Boolean[3][3];
        a[1][1] = false;
        assertTrue(equalArrays(p.a,a));
        assertEquals(new Long(1 * 81), p.state);
    }

    @Test
    public void testMove4() {
        Morpion p = new Morpion(3,3,3);
        p.move(false, 0, 1, 3);
        Boolean[][] a = new Boolean[3][3];
        a[0][1] = false;
        assertTrue(equalArrays(p.a,a));
        assertEquals(new Long(1 * 27), p.state);
    }

    @Test
    public void testAllowedMove() {
        Morpion p = new Morpion(3,3,3);
        assertTrue("AllowedMove",p.allowedMove(true, 1, 1, 4));
        Boolean[][] a = new Boolean[3][3];
        a[1][1] = true;
        assertTrue("Result", equalArrays(p.a,a));
    }

    @Test
    public void testAllowedMove2() {
        Morpion p = new Morpion(3,3,3);
        assertTrue("AllowedMove2.1",p.allowedMove(true, 1, 1, 4));
        Boolean[][] a = new Boolean[3][3];
        a[1][1] = true;
        assertFalse("AllowedMove2.2",p.allowedMove(false, 1, 1, 4));
        assertTrue("Result2", equalArrays(p.a,a));
    }

    @Test
    public void testPlayOnce() {
        Morpion p = new Morpion(3,3,3);
        assertFalse("playOnce",p.playOnce(true));
        Boolean[][] a = new Boolean[3][3];
        assertFalse("Result2", equalArrays(p.a,a));
    }

    @Test
    public void testIsFullColumn() {
        Morpion p = new Morpion(3,3,3);
        p.a[0][0] = true;
        assertFalse("isFullColumn.1", p.isColumnFull(0));
        p.a[0][1] = false;
        p.a[0][2] = true;
        assertTrue("isFullColumn.2", p.isColumnFull(0));
    }

    @Test
    public void testIsFullColumn2() {
        Morpion p = new Morpion(3,3,3);
        p.move(true, 0, 0, 0);
        assertFalse("isFullColumn.1", p.isColumnFull(0));
        p.move(false, 0, 1, 3);
        p.move(true, 0, 2, 6);
        assertTrue("isFullColumn.2", p.isColumnFull(0));
    }

    @Test
    public void testChooseAuthorisedRow() {
        Morpion p = new Morpion(3,3,3);
        p.a[0][1] = p.a[0][2] = p.a[1][1] = true;
        p.a[2][1] = p.a[2][2] = p.a[1][0] = false;
        int c = p.chooseNotFullColumn();
        assertFalse("chooseAuthorisedRow.1", p.isColumnFull(c));
        int r = p.chooseAllowedRow(c);
        assertNull("chooseAuthorisedRow.2", p.a[c][r]);
    }

    @Test
    public void testChooseAuthorisedRow2() {
        Morpion p = new Morpion(3,3,3);
        p.a[0][1] = p.a[0][2] = p.a[1][1] = p.a[0][0] = true;
        p.a[2][1] = p.a[2][2] = p.a[1][0] = p.a[1][2] = false;
        int c = p.chooseNotFullColumn();
        LOGGER.info("Choosed column : "+ c);
        assertFalse("chooseAuthorisedRow2.1", p.isColumnFull(c));
        int r = p.chooseAllowedRow(c);
        LOGGER.info("Choosed cell : "+ c +","+r);
        assertNull("chooseAuthorisedRow2.2", p.a[c][r]);
    }

    @Test
    public void testMakeACross() throws InterruptedException{
        Morpion p = new Morpion(1,1,1);
        Window f = new Window(p, "Test", 50, 50);
        p.setLevelLogger(Level.FINE);
        p.play();
        //Thread.sleep(10000);
    }
}
