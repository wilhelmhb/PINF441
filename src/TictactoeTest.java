import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Test;

import static org.junit.Assert.*;

public class TictactoeTest {
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    @Test
	public void testMove() {
        Tictactoe p = new Tictactoe(3,3,3);
        p.move(true, 1, 1);
        Boolean[][] a = new Boolean[3][3];
        a[1][1] = true;
        assertTrue(p.getCell(1, 1));
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
        Tictactoe p = new Tictactoe(3,3,3);
        p.move(true, 1, 1);
        Boolean[][] a = new Boolean[3][3];
        a[1][1] = true;
        assertTrue(equalArrays(p.getState(),a));
    }

    @Test
    public void testMove3() {
        Tictactoe p = new Tictactoe(3,3,3);
        p.move(false, 1, 1);
        Boolean[][] a = new Boolean[3][3];
        a[1][1] = false;
        assertTrue(equalArrays(p.getState(),a));
    }

    @Test
    public void testMove4() {
        Tictactoe p = new Tictactoe(3,3,3);
        p.move(false, 0, 1);
        Boolean[][] a = new Boolean[3][3];
        a[0][1] = false;
        assertTrue(equalArrays(p.getState(),a));
    }

    @Test
    public void testAllowedMove() {
        Tictactoe p = new Tictactoe(3,3,3);
        assertTrue("AllowedMove",p.allowedMove(true, 1, 1));
        Boolean[][] a = new Boolean[3][3];
        a[1][1] = true;
        assertTrue("Result", equalArrays(p.getState(),a));
    }

    @Test
    public void testAllowedMove2() {
        Tictactoe p = new Tictactoe(3,3,3);
        assertTrue("AllowedMove2.1",p.allowedMove(true, 1, 1));
        Boolean[][] a = new Boolean[3][3];
        a[1][1] = true;
        assertFalse("AllowedMove2.2",p.allowedMove(false, 1, 1));
        assertTrue("Result2", equalArrays(p.getState(),a));
    }

    @Test
    public void testPlayOnce() {
        Tictactoe p = new Tictactoe(3,3,3);
        assertFalse("playOnce",p.playOnceRandomly(true));
        Boolean[][] a = new Boolean[3][3];
        assertFalse("Result2", equalArrays(p.getState(), a));
    }

    @Test
    public void testIsFullColumn() {
        Tictactoe p = new Tictactoe(3,3,3);
        p.setCell(0, 0, true);
        assertFalse("isFullColumn.1", p.isColumnFull(0));
        p.setCell(0, 1, false);
        p.setCell(0, 2, true);
        assertTrue("isFullColumn.2", p.isColumnFull(0));
    }

    @Test
    public void testIsFullColumn2() {
        Tictactoe p = new Tictactoe(3,3,3);
        p.move(true, 0, 0);
        assertFalse("isFullColumn.1", p.isColumnFull(0));
        p.move(false, 0, 1);
        p.move(true, 0, 2);
        assertTrue("isFullColumn.2", p.isColumnFull(0));
    }

    @Test
    public void testChooseAuthorisedRow() {
        Tictactoe p = new Tictactoe(3,3,3);
        p.setCell(0, 1, true);
        p.setCell(0, 2, true);
        p.setCell(2, 1, false);
        p.setCell(1, 1, true);
        p.setCell(1, 0, false);
        p.setCell(2, 2, false);
        int c = p.chooseNotFullColumn();
        assertFalse("chooseAuthorisedRow.1", p.isColumnFull(c));
        int r = p.chooseAllowedRowRandomly(c);
        assertNull("chooseAuthorisedRow.2", p.getCell(c, r));
    }

    @Test
    public void testChooseAuthorisedRow2() {
        Tictactoe p = new Tictactoe(3,3,3);
        p.setCell(0, 1, true);
        p.setCell(0, 2, true);
        p.setCell(2, 1, false);
        p.setCell(1, 1, true);
        p.setCell(1, 0, false);
        p.setCell(2, 2, false);
        p.setCell(0, 0, true);
        p.setCell(1, 2, false);
        int c = p.chooseNotFullColumn();
        LOGGER.info("Choosed column : "+ c);
        assertFalse("chooseAuthorisedRow2.1", p.isColumnFull(c));
        int r = p.chooseAllowedRowRandomly(c);
        LOGGER.info("Choosed cell : "+ c +","+r);
        assertNull("chooseAuthorisedRow2.2", p.getCell(c, r));
    }

    @Test
    public void testMakeACross() throws InterruptedException{
        Tictactoe p = new Tictactoe(1,1,1);
        Window f = new Window(p, "Test", 50, 50);
        p.setLevelLogger(Level.FINE);
        p.playRandomly();
        //Thread.sleep(10000);
    }
}
