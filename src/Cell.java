import javax.swing.BorderFactory;
import javax.swing.JPanel;

import java.awt.*;
import java.util.LinkedList;
import java.util.logging.Logger;

/**
 * cell of the Tictactoe
 * @author Guillaume
 */
public class Cell extends JPanel {
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    Color background;
    int width;
    int height;
    LinkedList<Line> diags;

    /**
     * creates a cell for the Tictactoe
     * @param w : int, width of the cell
     * @param h : int, height of the cell
     * @param c : Color, background color for the cell
     */
    public Cell(int w, int h, Color c) {
        this.background = c;
        this.width = w;
        this.height = h;
        this.diags = new LinkedList<>();
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
        Graphics2D g1 = (Graphics2D) g;
        this.setVisible(true);
        for (Line line : diags) {
            g1.setColor(line.color);
            g1.setStroke(new BasicStroke(line.thickness));
            g1.drawLine(line.x1, line.y1, line.x2, line.y2);
        }
    }

    /**
     * change the color of the cell
     * @param c : Color, the wanted color for the background of this cell
     */
    public void changeColor(Color c) {
        this.background = c;
        setBackground(c);
    }

    /**
     * draw a cross in the cell
     * @param c : color of the cross
     * @param thickness : thickness of the line
     */
    public void makeACross(Color c, int thickness) {
        LOGGER.fine("We are going to put a cross there.");
        diags.add(new Line(0, 0, height, width, c, thickness));
        diags.add(new Line(height, 0, 0, width, c, thickness));
        repaint();
    }

    /**
     * draw a cross of 5 pixels in the cell
     * @param c : color for the cross
     */
    public void makeACross(Color c) {
        makeACross(c, 5);
    }

    /**
     * draw a red cross of 5 pixels in the cell
     */
    public void makeACross() {
        LOGGER.fine("Make a red cross.");
        makeACross(Color.RED);
    }
}
