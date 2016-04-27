import java.awt.Dimension;
import java.util.logging.Logger;

import javax.swing.JFrame;

/**
 * the window containing the picross
 * @author wilhelm
 */
public class Window extends JFrame {
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    public Window(Morpion m, String name){
        m.setMinimumSize(new Dimension(600,600));
        this.setTitle(name);
        this.setSize(750, 750);//Set the window to height and width
        this.setLocation(10,10);
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//allows basic window operations
        this.setResizable(true);//forbid us to resize the window
        this.setContentPane(m);//insert our Picross in the window
        m.revalidate();
        m.repaint();
        this.setVisible(true);//allow us to see the window
    }


    /*public Window(PicrossColored picross, String name){
        picross.setMinimumSize(new Dimension(600,600));
        this.setTitle(name);//Define the title of the window
        this.setSize(750, 750);//Set the window to height and width
        this.setLocation(10,10);
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//allows basic window operations
        this.setResizable(true);//forbid us to resize the window
        this.setContentPane(picross);//insert our Picross in the window
        picross.revalidate();
        picross.repaint();
        this.setVisible(true);//allow us to see the window
    }*/
}

