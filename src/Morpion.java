import javax.swing.JPanel;

import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Morpion extends JPanel {
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /*
     * definition of the dimensions of each cell
     */
    int cell_width;
    int cell_height;
    /*
     * how many cells do we have ?
     */
    int nb_columns;
    int nb_rows;
    /*
     * how many cell in a range do we have to possess in order to win ?
     */
    int aim;
    /* 
     * what colors will we use ?
     */
    Color unknown;
    Color checked;
    Color unchecked;
    /*
     * values of the grid
     */
    Boolean[][] a;//a[column][row]
    Cell[][] c;

    /**
     * (non-Javadoc)
     * @see javax.swing.JComponent#paintComponent(Graphics)
     */
    public void paintComponent(Graphics g){
        super.paintComponent(g);
    }

    /**
     * generates the graphic aspect of our picross
     */
    public void generateGraphics() {
        /* we use a grid layout */
        setLayout(new GridBagLayout());
        /* object to locate components */
        GridBagConstraints gbc = new GridBagConstraints();
        /* no cell is merged */
        gbc.gridheight = 1;
        gbc.gridwidth = 1;

        /* sets the inside of the picross */
        for(int k = 0 ; k < nb_columns ; k++){
            for(int i = 0 ; i < nb_rows ; i++){
                Cell c = new Cell(cell_width, cell_height, unknown, null);
                gbc.gridx = k;
                gbc.gridy = i;
                if(k == nb_columns-1){
                    gbc.gridwidth = GridBagConstraints.REMAINDER;
                }
                this.c[k][i] = c;
                a[k][i] = null;
                add(c,gbc);
            }
        }
        this.setPreferredSize(new Dimension(600, 600));
        this.setMinimumSize(new Dimension(600, 600));
    }

    /**
     * instanciate a Morpion given the size and aim
     * @param height
     * @param width
     * @param aim
     */
    public Morpion(int height, int width, int aim) {
        /* Instantiate the constants in the picross */
        this.cell_width = 50;
        this.cell_height = 50;
        this.nb_columns = width;
        this.nb_rows = height;
        this.unknown = Color.GRAY;
        this.checked = Color.BLACK;
        this.unchecked = Color.WHITE;
        this.a = new Boolean[nb_columns][nb_rows];
        this.c = new Cell[nb_columns][nb_rows];
        /* number of additional cells for the clues */
        this.setSize(new Dimension(600, 600));
        this.aim = aim;
        generateGraphics();

        LOGGER.info("Picross instancié");
        //this.setPreferredSize(new Dimension(cell_width*(nb_columns + nb_clues_rows),cell_height*(nb_rows)));
    }

    /**
     *
     */
    public void move(boolean player, int column, int row) {
        a[column][row] = player;
        if(player) {
            c[column][row].changeColor(this.checked);
        }
        else {
            c[column][row].changeColor(this.unchecked);
        }
    }

    /**
     *
     */
    public boolean allowedMove(boolean player, int column, int row) {
        if(this.a[column][row] != null) {
            return false;
        }
        move(player, column, row);
        return true;
    }

    /**
     *
     */
    public int chooseAuthorisedRow(int column) {
        int row = (int) Math.floor(Math.random() * this.nb_rows);
        while(a[column][row] != null) {
            row = (int) Math.floor(Math.random() * this.nb_rows);
        }
        return row;
    }

    /**
     *
     */
    public boolean isColumnFull(int column) {
        for(int k = 0; k < this.nb_rows ; k++) {
            if(this.a[column][k] == null) {
                LOGGER.info(k+ "");
                return false;
            }
        }
        return true;
    }

    /**
     *
     */
    public int chooseNotFullColumn() {
        int column = (int) Math.floor(Math.random() * this.nb_columns);
        while(isColumnFull(column)) {
            column = (int) Math.floor(Math.random() * this.nb_columns);
        }
        return column;
    }

    /**
     *
     */
    public boolean isMorpionFull() {
        for(int i = 0 ; i < this.nb_columns ; i++) {
            if(!isColumnFull(i)) {
                return false;
            }
        }
        return true;
    }

    /**
     *
     */
    public boolean playOnce(boolean player) {
        int column = chooseNotFullColumn();
        int row = chooseAuthorisedRow(column);
        LOGGER.info("selected column : "+ column + " and selected row : "+ row);
        if(!allowedMove(player, column, row)) {
            LOGGER.severe("Tes algos sont pourraves, vieux !");
        }
        return isWon(player, column, row);
    }

    /**
     *
     */
    public boolean isWonHorizontally(boolean player, int column, int row) {
        int count = 1;
        int column_left = column - 1;
        int column_right = column + 1;
        while((column_left >= 0)&&(a[column_left][row] != null)&&(a[column_left][row] == player)) {
            count++;
            column_left--;
        }
        while((column_right < nb_columns)&&(a[column_right][row] != null)&&(a[column_right][row] == player)) {
            count++;
            column_right++;
        }
        return count == this.aim;
    }

    /**
     *
     */
    public boolean isWonVertically(boolean player, int column, int row) {
        int count = 1;
        int row_up = row - 1;
        int row_down = row + 1;
        while((row_up >= 0)&&(a[column][row_up] != null)&&(a[column][row_up] == player)) {
            count++;
            row_up--;
        }
        while((row_down < nb_rows)&&(a[column][row_down] != null)&&(a[column][row_down] == player)) {
            count++;
            row_down++;
        }
        return count == this.aim;
    }

    /**
     *
     */
    public boolean isWonOnFirstDiag(boolean player, int column, int row) {
        int count = 1;
        int row_up = row - 1;
        int row_down = row + 1;
        int column_left = column - 1;
        int column_right = column + 1;
        while((row_up >=0)&&(column_right < nb_columns)&&(a[column_right][row_up] != null)&&(a[column_right][row_up] == player)) {
            count++;
            row_up--;
            column_right++;
        }
        while((row_down < nb_rows)&&(column_left >= 0)&&(a[column_left][row_down] != null)&&(a[column_left][row_down] == player)) {
            count++;
            row_down++;
            column_left--;
        }
        return count == this.aim;
    }

    /**
     *
     */
    public boolean isWonOnSecondDiag(boolean player, int column, int row) {
        int count = 1;
        int row_up = row - 1;
        int row_down = row + 1;
        int column_left = column - 1;
        int column_right = column + 1;
        while((row_down < nb_rows)&&(column_right < nb_columns)&&(a[column_right][row_down] != null)&&(a[column_right][row_down] == player)) {
            count++;
            row_down++;
            column_right++;
        }
        while((row_up >= 0)&&(column_left >= 0)&&(a[column_left][row_up] != null)&&(a[column_left][row_up] == player)) {
            count++;
            row_up--;
            column_left--;
        }
        return count == this.aim;
    }

    /**
     *
     */
    public boolean isWon(boolean player, int column, int row) {
        return isWonHorizontally(player, column, row)||isWonVertically(player, column, row)||isWonOnFirstDiag(player, column, row)||isWonOnSecondDiag(player, column, row);
    }

    /**
     *
     */
    public void play() {
        boolean player = true;
        for (int k = 0; k < nb_columns * nb_rows; k++) {
            LOGGER.info(k + " coups déjà joués.");
            if (playOnce(player)) {
                break;
            }
            player = !player;
        }
        LOGGER.warning("Player "+(player ? 1 : 2)+" won !");
    }

    public void setLevelLogger(Level level) {
        LOGGER.setLevel(level);
    }
}
