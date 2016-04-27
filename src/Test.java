import java.util.logging.Level;

/**
  class to launch the picross and the solver
  @author guillaume
 
 */
public class Test{
	public static void main(String[] args) {
		test();
	}
	
	/**
	  create a picross, solve it and display it
	  @author Guillaume
	 */
	public static void test() {
		Morpion p = new Morpion(3,3,3);
		Window f = new Window(p, "Test");
		p.play();
		//try {Thread.sleep(1000);}catch(Exception e){}
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
