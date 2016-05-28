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

    @Test
    public void testChangeLeft() {
        Boolean[][] p = new Boolean[3][1];
        p[1][0] = false;
        p[0][0] = true;
        p[2][0] = true;
        PositionOthello po = new PositionOthello(p);
        assertTrue(po.changeLeft(true, 2, 0));
        Boolean[][] test = new Boolean[3][1];
        test[0][0] = true;
        test[1][0] = true;
        test[2][0] = true;
        assertArrayEquals(po.getState(), test);
    }

    @Test
    public void testChangeLeft2() {
        Boolean[][] p = new Boolean[3][1];
        p[1][0] = true;
        p[0][0] = false;
        p[2][0] = false;
        PositionOthello po = new PositionOthello(p);
        assertTrue(po.changeLeft(false, 2, 0));
        Boolean[][] test = new Boolean[3][1];
        test[0][0] = false;
        test[1][0] = false;
        test[2][0] = false;
        assertArrayEquals(po.getState(), test);
    }

    @Test
    public void testChangeLeft3() {
        Boolean[][] p = new Boolean[3][1];
        p[1][0] = false;
        p[0][0] = true;
        p[2][0] = false;
        PositionOthello po = new PositionOthello(p);
        assertFalse(po.changeLeft(false, 2, 0));
        Boolean[][] test = new Boolean[3][1];
        test[0][0] = true;
        test[1][0] = false;
        test[2][0] = false;
        assertArrayEquals(po.getState(), test);
    }

    @Test
    public void testChangeLeft4() {
        Boolean[][] p = new Boolean[3][1];
        p[1][0] = true;
        p[0][0] = false;
        p[2][0] = true;
        PositionOthello po = new PositionOthello(p);
        assertFalse(po.changeLeft(true, 2, 0));
        Boolean[][] test = new Boolean[3][1];
        test[0][0] = false;
        test[1][0] = true;
        test[2][0] = true;
        assertArrayEquals(po.getState(), test);
    }

    @Test
    public void testOthelloMove() {
        Othello o = new Othello(1, 3);
        o.setCell(0,0,true);
        o.setCell(1,0,false);
        assertTrue(o.getCell(0, 0));
        assertFalse(o.getCell(1,0));
        o.move(true, 2, 0);
        Boolean[][] test = new Boolean[3][1];
        test[0][0] = true;
        test[1][0] = true;
        test[2][0] = true;
        //assertTrue(o.getState()[1][0]);
        assertArrayEquals(o.getState(), test);
    }

    @Test
    public void testChangeRight() {
        Boolean[][] p = new Boolean[3][1];
        p[1][0] = false;
        p[0][0] = true;
        p[2][0] = true;
        PositionOthello po = new PositionOthello(p);
        assertTrue(po.changeRight(true, 0, 0));
        Boolean[][] test = new Boolean[3][1];
        test[0][0] = true;
        test[1][0] = true;
        test[2][0] = true;
        assertArrayEquals(po.getState(), test);
    }

    @Test
    public void testChangeRight2() {
        Boolean[][] p = new Boolean[3][1];
        p[1][0] = true;
        p[0][0] = false;
        p[2][0] = false;
        PositionOthello po = new PositionOthello(p);
        assertTrue(po.changeRight(false, 0, 0));
        Boolean[][] test = new Boolean[3][1];
        test[0][0] = false;
        test[1][0] = false;
        test[2][0] = false;
        assertArrayEquals(po.getState(), test);
    }

    @Test
    public void testChangeUp() {
        Boolean[][] p = new Boolean[1][3];
        p[0][0] = true;
        p[0][1] = false;
        p[0][2] = true;
        PositionOthello po = new PositionOthello(p);
        assertTrue(po.changeUp(true, 0, 2));
        Boolean[][] test = new Boolean[1][3];
        test[0][0] = true;
        test[0][1] = true;
        test[0][2] = true;
        assertArrayEquals(po.getState(), test);
    }

    @Test
    public void testChangeUp2() {
        Boolean[][] p = new Boolean[1][3];
        p[0][0] = false;
        p[0][1] = true;
        p[0][2] = false;
        PositionOthello po = new PositionOthello(p);
        assertTrue(po.changeUp(false, 0, 2));
        Boolean[][] test = new Boolean[1][3];
        test[0][0] = false;
        test[0][1] = false;
        test[0][2] = false;
        assertArrayEquals(po.getState(), test);
    }

    @Test
    public void testChangeDown() {
        Boolean[][] p = new Boolean[1][3];
        p[0][0] = true;
        p[0][1] = false;
        p[0][2] = true;
        PositionOthello po = new PositionOthello(p);
        assertTrue(po.changeDown(true, 0, 0));
        Boolean[][] test = new Boolean[1][3];
        test[0][0] = true;
        test[0][1] = true;
        test[0][2] = true;
        assertArrayEquals(po.getState(), test);
    }

    @Test
    public void testChangeDown2() {
        Boolean[][] p = new Boolean[1][3];
        p[0][0] = false;
        p[0][1] = true;
        p[0][2] = false;
        PositionOthello po = new PositionOthello(p);
        assertTrue(po.changeDown(false, 0, 0));
        Boolean[][] test = new Boolean[1][3];
        test[0][0] = false;
        test[0][1] = false;
        test[0][2] = false;
        assertArrayEquals(po.getState(), test);
    }

    @Test
    public void testChange() {
        Boolean[][] p = new Boolean[1][3];
        p[0][0] = false;
        p[0][1] = false;
        p[0][2] = true;
        PositionOthello po = new PositionOthello(p);
        assertFalse(po.change(false, 0, 0));
        Boolean[][] test = new Boolean[1][3];
        test[0][0] = false;
        test[0][1] = false;
        test[0][2] = true;
        assertArrayEquals(po.getState(), test);
    }

    @Test
    public void testChange2() {
        Boolean[][] p = new Boolean[1][3];
        p[0][0] = false;
        p[0][1] = true;
        p[0][2] = true;
        PositionOthello po = new PositionOthello(p);
        assertFalse(po.change(true, 0, 2));
        Boolean[][] test = new Boolean[1][3];
        test[0][0] = false;
        test[0][1] = true;
        test[0][2] = true;
        assertArrayEquals(po.getState(), test);
    }


}
