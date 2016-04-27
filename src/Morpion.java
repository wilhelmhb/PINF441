import javax.swing.JPanel;

import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * class representing the whole game
 * @author Guillaume
 */
public class Morpion extends JPanel {
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /* definition of the dimensions of each cell */
    int cell_width;
    int cell_height;
    /* how many cells do we have ? */
    int nb_columns;
    int nb_rows;
    /* how many cell in a range do we have to possess in order to win ? */
    int aim;
    /* what colors will we use ? */
    Color unknown;
    Color checked;
    Color unchecked;
    /* values of the grid */
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
     * generates the graphic aspect of our Morpion
     */
    public void generateGraphics() {
        /* we use a grid layout */
        setLayout(new GridBagLayout());
        /* object to locate components */
        GridBagConstraints gbc = new GridBagConstraints();
        /* no cell is merged */
        gbc.gridheight = 1;
        gbc.gridwidth = 1;

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
        this.setPreferredSize(new Dimension(this.cell_height * this.nb_rows, this.cell_width * this.nb_columns));
        this.setMinimumSize(new Dimension(10, 10));
    }

    /**
     *   instanciate a Morpion given the size, aim and dimensions
     * @param height
     * @param width
     * @param aim : number of cells in a range to reach in order to win
     */
    public Morpion(int height, int width, int aim) {
        this(height, width, aim, 50, 50);
    }

    /**
     *   instanciate a Morpion given the size, aim and dimensions
     * @param height
     * @param width
     * @param aim : number of cells in a range to reach in order to win
     * @param cell_height
     * @param cell_width
     */
    public Morpion(int height, int width, int aim, int cell_height, int cell_width) {
        /* Instantiate the constants in the Morpion */
        this.cell_width = cell_width;
        this.cell_height = cell_height;
        this.nb_columns = width;
        this.nb_rows = height;
        this.unknown = Color.GRAY;
        this.checked = Color.BLACK;
        this.unchecked = Color.WHITE;
        this.a = new Boolean[nb_columns][nb_rows];
        this.c = new Cell[nb_columns][nb_rows];
        this.setSize(new Dimension(this.cell_height * this.nb_rows, this.cell_width * this.nb_columns));
        this.aim = aim;
        generateGraphics();

        LOGGER.info("Morpion instancié");
        //this.setPreferredSize(new Dimension(cell_width*(nb_columns + nb_clues_rows),cell_height*(nb_rows)));
    }

    /**
     * place a piece for player player on cell (column, row)
     * @param player : true for player one, false for player 2
     * @param column
     * @param row
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
     * verify that the cell (column, row) doesn't contain a piece yet, then place a piece on it
     * @param player
     * @param column
     * @param row
     * @return true if move is allowed
     */
    public boolean allowedMove(boolean player, int column, int row) {
        if(this.a[column][row] != null) {
            return false;
        }
        move(player, column, row);
        return true;
    }

    /**
     * choose randomly an empty cell in the column column
     * @param column
     * @return the index of an empty cell in column column
     */
    public int chooseAllowedRow(int column) {
        int row = (int) Math.floor(Math.random() * this.nb_rows);
        while(a[column][row] != null) {
            row = (int) Math.floor(Math.random() * this.nb_rows);
        }
        return row;
    }

    /**
     * test if the column column is full
     * @param column
     * @return true if not piece can be place on column column
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
     * choose randomly a column where you can put a piece somewhere
     * @return the index of a not full column
     */
    public int chooseNotFullColumn() {
        int column = (int) Math.floor(Math.random() * this.nb_columns);
        while(isColumnFull(column)) {
            column = (int) Math.floor(Math.random() * this.nb_columns);
        }
        return column;
    }

    /**
     * test if a piece can be added somewhere in the game
     * @return true if no piece can be placed any more
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
     * play once randomly for the player player
     * @param player
     * @return true if player has won thanks to this move
     */
    public boolean playOnce(boolean player) {
        int column = chooseNotFullColumn();
        int row = chooseAllowedRow(column);
        LOGGER.info("selected column : "+ column + " and selected row : "+ row);
        if(!allowedMove(player, column, row)) {
            LOGGER.severe("Tes algos sont pourraves, vieux !");
        }
        return isWon(player, column, row);
    }

    /**
     * test if player won the game thanks to a row
     * @param player
     * @param column
     * @param row
     * @return true if thanks to a move in cell (column, row), player won thanks to a row
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
     * test if player won the game thanks to a column
     * @param player
     * @param column
     * @param row
     * @return true if thanks to a move in cell (column, row), player won thanks to a column
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
     * test if player won the game thanks to the first bisector
     * @param player
     * @param column
     * @param row
     * @return true if thanks to a move in cell (column, row), player won thanks to the first bisector
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
     * test if player won the game thanks to a second bisector
     * @param player
     * @param column
     * @param row
     * @return true if thanks to a move in cell (column, row), player won thanks to the second bisector
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
     * test if player won
     * @param player
     * @param column
     * @param row
     * @return true if player won thanks to a piece in cell (column, row)
     */
    public boolean isWon(boolean player, int column, int row) {
        return isWonHorizontally(player, column, row)||isWonVertically(player, column, row)||isWonOnFirstDiag(player, column, row)||isWonOnSecondDiag(player, column, row);
    }

    /**
     * play randomly a game between two players
     */
    public void play() {
        boolean player = true;
        for (int k = 0; k < nb_columns * nb_rows; k++) {
            LOGGER.info(k + " coups déjà joués.");
            if (playOnce(player)) {
                LOGGER.warning("Player " + (player ? 1 : 2) + " won !");
                return;
            }
            player = !player;
        }
        LOGGER.warning("Match nul !");
    }

    /**
     * change level of messages displayed in the console
     * @param level
     */
    public void setLevelLogger(Level level) {
        LOGGER.setLevel(level);
    }
}
