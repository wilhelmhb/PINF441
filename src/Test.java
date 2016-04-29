import java.awt.*;
import java.util.logging.Level;

/**
  class to launch the Morpion
  @author Guillaume
 
 */
public class Test{
	public static void main(String[] args) {
		for(int l = 0; l < 10; l++) {
			testOnce(l, (int) Math.floor(3 + l * 1.5), (int) Math.floor(3 + l * 2), (int) Math.floor(3));
		}
		return;
	}
	
	/**
	 * create a Morpion, launch the game, and display it
	 * @param l : index of the Morpion
	 * @param i : number of columns
	 * @param j : number of rows
     * @param k : aim of the morpion
     */
	public static void testOnce(int l, int i, int j, int k) {
		Morpion p = new Morpion(i, j, k, 50, 50, Color.BLUE, Color.GREEN);
		Window f = new Window(p, "Test" + l + ", dimensions : (" + i + "x" + j + "), but : " + k, j * (50 + 10), i * (50 + 10));
		p.setLevelLogger(Level.FINE);
		p.play();
		try {Thread.sleep(1000);}catch(Exception e){}
		return;
	}
}
