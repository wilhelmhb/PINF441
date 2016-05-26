import java.awt.Dimension;
import java.util.logging.Logger;

import javax.swing.JFrame;

/**
 * the window containing the picross
 * @author wilhelm
 */
public class Window extends JFrame {
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /**
     * display a Tictactoe in a window
     * @param m : Tictactoe instance to display in the window
     * @param name : name of the window
     * @param height
     * @param width
     */
    public Window(Tictactoe m, String name, int height, int width){
        m.setMinimumSize(new Dimension(600,600));
        this.setTitle(name);
        this.setSize(height, width);//Set the window to height and width
        this.setLocation(10,10);
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//allows basic window operations
        this.setResizable(true);//forbid us to resize the window
        this.setContentPane(m);//insert our Picross in the window
        m.revalidate();
        m.repaint();
        this.setVisible(true);//allow us to see the window
    }

    /**
     * display a Tictactoe in a window
     * @param m : Tictactoe instance to display in the window
     * @param name : name of the window
     * @param height
     * @param width
     */
    public Window(Connect4 m, String name, int height, int width){
        m.setMinimumSize(new Dimension(600,600));
        this.setTitle(name);
        this.setSize(height, width);//Set the window to height and width
        this.setLocation(10,10);
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//allows basic window operations
        this.setResizable(true);//forbid us to resize the window
        this.setContentPane(m);//insert our Picross in the window
        m.revalidate();
        m.repaint();
        this.setVisible(true);//allow us to see the window
    }
}

