import java.awt.*;
import java.util.logging.Level;

/**
  class to launch the Tictactoe
  @author Guillaume
 
 */
public class Test{
	public static void main(String[] args) {
		/*for(int l = 0; l < 10; l++) {
			testOnceRandomly(l, (int) Math.floor(3 + l * 1.5), (int) Math.floor(3 + l * 2), (int) Math.floor(3));
			testOnceMiniMax(l, (int) Math.floor(3 + l * 1.5), (int) Math.floor(3 + l * 2), (int) Math.floor(3));
			testOnceNegaMax(l, (int) Math.floor(3 + l * 1.5), (int) Math.floor(3 + l * 2), (int) Math.floor(3));
			testOnceAlphaBeta(l, (int) Math.floor(3 + l * 1.5), (int) Math.floor(3 + l * 2), (int) Math.floor(3));
		}*/
		/*testOnceRandomly(1, 3, 3, 3);
		testOnceMiniMax(1, 3, 3, 3);
		testOnceNegaMax(1, 3, 3, 3);
		testOnceAlphaBeta(1, 3, 3, 3);*/
		testOnceRandomly(1, 4, 3, 3);
		testOnceMiniMax(1, 4, 3, 3);
		testOnceNegaMax(1, 4, 3, 3);
		testOnceAlphaBeta(1, 4, 3, 3);
		return;
	}
	
	/**
	 * create a Tictactoe, launch the game, and display it
	 * @param l : index of the Tictactoe
	 * @param i : number of columns
	 * @param j : number of rows
     * @param k : aim of the morpion
     */
	public static void testOnceRandomly(int l, int i, int j, int k) {
		Tictactoe p = new Tictactoe(i, j, k, 50, 50, Color.BLUE, Color.GREEN);
		Window f = new Window(p, "Test" + l + ", dimensions : (" + i + "x" + j + "), but : " + k, j * (50 + 10), i * (50 + 10));
		p.setLevelLogger(Level.FINE);
		p.playRandomly();
		try {Thread.sleep(1000);}catch(Exception e){}
		return;
	}

	public static void testOnceMiniMax(int l, int i, int j, int k) {
		Tictactoe p = new Tictactoe(i, j, k, 50, 50, Color.BLUE, Color.GREEN);
		Window f = new Window(p, "Test" + l + ", dimensions : (" + i + "x" + j + "), but : " + k, j * (50 + 10), i * (50 + 10));
		p.setLevelLogger(Level.FINE);
		p.playMiniMax();
		try {Thread.sleep(1000);}catch(Exception e){}
		return;
	}

	public static void testOnceNegaMax(int l, int i, int j, int k) {
		Tictactoe p = new Tictactoe(i, j, k, 50, 50, Color.BLUE, Color.GREEN);
		Window f = new Window(p, "Test" + l + ", dimensions : (" + i + "x" + j + "), but : " + k, j * (50 + 10), i * (50 + 10));
		p.setLevelLogger(Level.FINE);
		p.playNegaMax();
		try {Thread.sleep(1000);}catch(Exception e){}
		return;
	}

	public static void testOnceAlphaBeta(int l, int i, int j, int k) {
		Tictactoe p = new Tictactoe(i, j, k, 50, 50, Color.BLUE, Color.GREEN);
		Window f = new Window(p, "Test" + l + ", dimensions : (" + i + "x" + j + "), but : " + k, j * (50 + 10), i * (50 + 10));
		p.setLevelLogger(Level.FINE);
		p.playAlphaBeta();
		try {Thread.sleep(1000);}catch(Exception e){}
		return;
	}
}
