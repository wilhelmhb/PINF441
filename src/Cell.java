import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.LayoutManager;
import java.util.logging.Logger;

/**
 * cell of the Morpion
 * @author Guillaume
 *
 */
public class Cell extends JPanel {
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    Color background;
    int width;
    int height;

    /**
     * creates a cell for the picross
     * @param w : int, width of the cell
     * @param h : int, height of the cell
     * @param c : Color, background color for the cell
     */
    public Cell(int w, int h, Color c, Integer value) {
        this.background = c;
        this.width = w;
        this.height = h;
        this.setPreferredSize(new Dimension(w,h));//we want the cell to have the announced size
        this.setMinimumSize(new Dimension(10,10));//the cell has to be at least 10*10
        this.setBackground(this.background);//color the cell
        this.setBorder(BorderFactory.createLineBorder(Color.black));//add a border to the cell
    }

    /**
     * (non-Javadoc)
     * @see javax.swing.JComponent#paintComponent(Graphics)
     */
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        this.setVisible(true);
    }

    /**
     * change the color of the cell
     * @param c : Color, the wanted color for the background of this cell
     */
    public void changeColor(Color c) {
        this.background = c;
        setBackground(c);
    }
}
