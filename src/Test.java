import java.util.logging.Level;

/**
  class to launch the picross and the solver
  @author guillaume
 
 */
public class Test{
	public static void main(String[] args) {
		for(int l = 0; l < 10; l++) {
			testOnce(l, (int) Math.floor(3 + l * 1.5), (int) Math.floor(3 + l* 1.51), (int) Math.floor(3 + l* 1.4));
		}
	}
	
	/**
	  create a picross, solve it and display it
	  @author Guillaume
	 */
	public static void testOnce(int l, int i, int j, int k) {
		Morpion p = new Morpion(i, j, k);
		Window f = new Window(p, "Test" + l);
		p.setLevelLogger(Level.WARNING);
		p.play();
		try {Thread.sleep(1000);}catch(Exception e){}
		return;
	}
	
	/**
	  create a picrossColored, solve it and display it
	  @param file
	  @author Guillaume
	 */
	/*public static void testColored(String file) {
		MorpionColored p = new MorpionColored(new File(file).getAbsolutePath());
		Window f = new Window(p, file);
		p.solveOpt();
		//try {Thread.sleep(1000);}catch(Exception e){}
	}*/
}
