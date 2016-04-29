import javax.swing.JComponent;
import java.awt.Color;

/**
 * Created by Guillaume on 29/04/16.
 */
public class Line extends JComponent {
    final int x1;
    final int y1;
    final int x2;
    final int y2;
    final Color color;
    final int thickness;

    public Line(int x1, int y1, int x2, int y2, Color color, int thickness) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.color = color;
        this.thickness = thickness;
    }
}
