import java.awt.*;
import java.util.logging.Level;

/**
 * Created by wilhelm on 04/05/16.
 */
public class Main {
    public static void main(String[] args) {
        switch (args[0]) {
            case "tictactoe":
                launchTictactoe();
                break;
            case "connect4":
                launchConnect4();
                break;
            default:
                launchOther();
        }
    }

    private static void launchTictactoe() {
        /*Tictactoe p = new Tictactoe(50, 50, Color.BLUE, Color.GREEN);
        //Window f = new Window(p, "Test" + l + ", dimensions : (" + i + "x" + j + "), but : " + k, j * (50 + 10), i * (50 + 10));
        p.setLevelLogger(Level.FINE);
        p.play();*/
    }

    private static void launchConnect4() {
    }

    private static void launchOther() {
    }
}
