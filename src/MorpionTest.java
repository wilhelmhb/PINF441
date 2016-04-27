import junit.framework.TestCase;

import java.util.logging.Logger;

public class MorpionTest extends TestCase {
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	public void testMove() {
        Morpion p = new Morpion(3,3,3);
        p.move(true, 1, 1);
        Boolean[][] a = new Boolean[3][3];
        a[1][1] = true;
        assertTrue(p.a[1][1]);
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

    public void testMove2() {
        Morpion p = new Morpion(3,3,3);
        p.move(true, 1, 1);
        Boolean[][] a = new Boolean[3][3];
        a[1][1] = true;
        assertTrue(equalArrays(p.a,a));
    }

    public void testMove3() {
        Morpion p = new Morpion(3,3,3);
        p.move(false, 1, 1);
        Boolean[][] a = new Boolean[3][3];
        a[1][1] = false;
        assertTrue(equalArrays(p.a,a));
    }

    public void testAllowedMove() {
        Morpion p = new Morpion(3,3,3);
        assertTrue("AllowedMove",p.allowedMove(true, 1, 1));
        Boolean[][] a = new Boolean[3][3];
        a[1][1] = true;
        assertTrue("Result", equalArrays(p.a,a));
    }

    public void testAllowedMove2() {
        Morpion p = new Morpion(3,3,3);
        assertTrue("AllowedMove2.1",p.allowedMove(true, 1, 1));
        Boolean[][] a = new Boolean[3][3];
        a[1][1] = true;
        assertFalse("AllowedMove2.2",p.allowedMove(false, 1, 1));
        assertTrue("Result2", equalArrays(p.a,a));
    }

    public void testPlayOnce() {
        Morpion p = new Morpion(3,3,3);
        assertFalse("playOnce",p.playOnce(true));
        Boolean[][] a = new Boolean[3][3];
        assertFalse("Result2", equalArrays(p.a,a));
    }

    public void testIsFullColumn() {
        Morpion p = new Morpion(3,3,3);
        p.a[0][0] = true;
        assertFalse("isFullColumn.1", p.isColumnFull(0));
        p.a[0][1] = false;
        p.a[0][2] = true;
        assertTrue("isFullColumn.2", p.isColumnFull(0));
    }

    public void testIsFullColumn2() {
        Morpion p = new Morpion(3,3,3);
        p.move(true, 0, 0);
        assertFalse("isFullColumn.1", p.isColumnFull(0));
        p.move(false, 1, 0);
        p.move(true, 2, 0);
        assertTrue("isFullColumn.2", p.isColumnFull(0));
    }

    public void testChooseAuthorisedRow() {
        Morpion p = new Morpion(3,3,3);
        p.a[0][1] = p.a[0][2] = p.a[1][1] = true;
        p.a[2][1] = p.a[2][2] = p.a[1][0] = false;
        int c = p.chooseNotFullColumn();
        assertFalse("chooseAuthorisedRow.1", p.isColumnFull(c));
        int r = p.chooseAuthorisedRow(c);
        assertNull("chooseAuthorisedRow.2", p.a[c][r]);
    }

}
