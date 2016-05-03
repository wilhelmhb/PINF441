import algorithmes.Game;
import structures.Pair;
import structures.Tuple;
import sun.awt.image.ImageWatched;

import javax.swing.JPanel;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * class representing the whole game
 * @author Guillaume
 */
public class Morpion extends JPanel implements Game<Position, Pair<Integer, Integer>, Boolean> {
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /* definition of the dimensions of each cell */
    Integer cell_width;
    Integer cell_height;
    /* how many cells do we have ? */
    Integer nb_columns;
    Integer nb_rows;
    /* how many cell in a range do we have to possess in order to win ? */
    Integer aim;
    /* what colors will we use ? */
    Color unknown;
    Color checked;
    Color unchecked;
    /* values of the grid */
    Position state;
    Cell[][] c;

    /**
     * instantiate a Morpion given the size, aim, dimensions and colors for the players
     * @param height
     * @param width
     * @param aim : number of cells in a range to reach in order to win
     * @param cell_height
     * @param cell_width
     * @param color_first_player
     * @param color_second_player
     */
    public Morpion(Integer height, Integer width, Integer aim, Integer cell_height, Integer cell_width, Color color_first_player, Color color_second_player) {
        /* Instantiate the constants in the Morpion */
        this.cell_width = cell_width;
        this.cell_height = cell_height;
        this.nb_columns = width;
        this.nb_rows = height;
        this.unknown = Color.GRAY;
        this.checked = color_first_player;
        this.unchecked = color_second_player;
        this.state = new Boolean[nb_columns][nb_rows];
        this.c = new Cell[nb_columns][nb_rows];
        this.setSize(new Dimension(this.cell_height * this.nb_rows, this.cell_width * this.nb_columns));
        this.aim = aim;
        generateGraphics();

        LOGGER.fine("Morpion instancié");
        //this.setPreferredSize(new Dimension(cell_width*(nb_columns + nb_clues_rows),cell_height*(nb_rows)));
    }

    /**
     * instantiate a Morpion given the size, aim and dimensions
     * @param height
     * @param width
     * @param aim
     * @param cell_width
     * @param cell_height
     */
    public Morpion(Integer height, Integer width, Integer aim, Integer cell_width, Integer cell_height) {
        this(height, width, aim, cell_width, cell_height, Color.WHITE, Color.BLACK);
    }

    /**
     * instantiate a Morpion given the size, aim and colors for the players
     * @param height
     * @param width
     * @param aim
     * @param color_first_player
     * @param color_second_player
     */
    public Morpion(Integer height, Integer width, Integer aim, Color color_first_player, Color color_second_player) {
        this(height, width, aim, 50, 50, color_first_player, color_second_player);
    }

    /**
     * instantiate a Morpion given the size and aim
     * @param height
     * @param width
     * @param aim : number of cells in a range to reach in order to win
     */
    public Morpion(Integer height, Integer width, Integer aim) {
        this(height, width, aim, 50, 50);
    }

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

        for(Integer k = 0 ; k < nb_columns ; k++){
            for(Integer i = 0 ; i < nb_rows ; i++){
                Cell c = new Cell(cell_width, cell_height, unknown);
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
     * place a piece for player player on cell (column, row)
     * @param player : true for player one, false for player 2
     * @param column
     * @param row
     */
    public void move(boolean player, Integer column, Integer row, Integer index) {
        state += player ? 2 * this.powers[index] : 1 * this.powers[index];
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
    public boolean allowedMove(Boolean player, Integer column, Integer row, Integer index) {
        if(this.a[column][row] != null) {
            return false;
        }
        move(player, column, row, index);
        return true;
    }

    /**
     * choose randomly an empty cell in the column column
     * @param column
     * @return the index of an empty cell in column column
     */
    public Integer chooseAllowedRow(Integer column) {
        Integer row = (int) Math.floor(Math.random() * this.nb_rows);
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
    public boolean isColumnFull(Integer column) {
        for(Integer k = 0; k < this.nb_rows ; k++) {
            if(this.a[column][k] == null) {
                LOGGER.finer(k+ "");
                return false;
            }
        }
        return true;
    }

    /**
     * choose randomly a column where you can put a piece somewhere
     * @return the index of a not full column
     */
    public Integer chooseNotFullColumn() {
        Integer column = (int) Math.floor(Math.random() * this.nb_columns);
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
        for(Integer i = 0 ; i < this.nb_columns ; i++) {
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
        Integer column = chooseNotFullColumn();
        Integer row = chooseAllowedRow(column);
        LOGGER.finest("selected column : "+ column + " and selected row : "+ row);
        if(!allowedMove(player, column, row, column + row * nb_columns)) {
            LOGGER.severe("Tes algos sont pourraves, vieux !");
        }
        return isWon(player, column, row, column +row * nb_columns);
    }

    /**
     * test if player won the game thanks to a row
     * @param player
     * @param column
     * @param row
     * @return true if thanks to a move in cell (column, row), player won thanks to a row
     */
    public boolean isWonHorizontally(boolean player, Integer column, Integer row, Integer index) {
        Integer count = 1;
        Integer column_left = column - 1;
        Integer column_right = column + 1;
        while((column_left >= 0)&&(a[column_left][row] != null)&&(a[column_left][row] == player)) {
            count++;
            column_left--;
        }
        while((column_right < nb_columns)&&(a[column_right][row] != null)&&(a[column_right][row] == player)) {
            count++;
            column_right++;
        }
        Integer count2 = 1;
        Integer cell_left = index - 1;
        Integer cell_right = index + 1;
        Boolean b = getCell(index);
        while((cell_left % nb_columns >= 0)&&(b != null)&&(b == player)) {
            count2++;
            cell_left--;
            b = getCell(cell_left);
        }
        b = getCell(index);
        while((cell_left % nb_columns >= 0)&&(b != null)&&(b == player)) {
            count2++;
            cell_right++;
            b = getCell(cell_left);
        }
        if(count2 == this.aim) {
            LOGGER.fine("won horizontally : search for crosses");
            for(Integer i = ++column_left ; i < column_right ; i++) {
                c[i][row].makeACross();
                LOGGER.fine("made a cross on cell (" + i + "," + row + ")");
            }
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
    public boolean isWonVertically(boolean player, Integer column, Integer row) {
        Integer count = 1;
        Integer row_up = row - 1;
        Integer row_down = row + 1;
        while((row_up >= 0)&&(a[column][row_up] != null)&&(a[column][row_up] == player)) {
            count++;
            row_up--;
        }
        while((row_down < nb_rows)&&(a[column][row_down] != null)&&(a[column][row_down] == player)) {
            count++;
            row_down++;
        }
        if(count == this.aim) {
            LOGGER.fine("won vertically : search for crosses");
            for(Integer i = ++row_up ; i < row_down ; i++) {
                c[column][i].makeACross();
                LOGGER.fine("made a cross on cell (" + column + "," + i + ")");
            }
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
    public boolean isWonOnFirstDiag(boolean player, Integer column, Integer row) {
        Integer count = 1;
        Integer row_up = row - 1;
        Integer row_down = row + 1;
        Integer column_left = column - 1;
        Integer column_right = column + 1;
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
        if(count == this.aim) {
            LOGGER.fine("won on first bisector : search for crosses");
            column_right--;
            for(Integer i = ++row_up ; i < row_down ; i++) {
                c[column_right][i].makeACross();
                LOGGER.fine("made a cross on cell (" + column_right + "," + i + ")");
                column_right--;
            }
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
    public boolean isWonOnSecondDiag(boolean player, Integer column, Integer row) {
        Integer count = 1;
        Integer row_up = row - 1;
        Integer row_down = row + 1;
        Integer column_left = column - 1;
        Integer column_right = column + 1;
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
        if(count == this.aim) {
            LOGGER.fine("won on second bisector : search for crosses");
            column_left++;
            for(Integer i = ++row_up ; i < row_down ; i++) {
                c[column_left][i].makeACross();
                LOGGER.fine("made a cross on cell (" + column_left + "," + i + ")");
                column_left++;
            }
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
    public boolean isWon(boolean player, Integer column, Integer row, Integer index) {
        return isWonHorizontally(player, column, row, index)||isWonVertically(player, column, row)||isWonOnFirstDiag(player, column, row)||isWonOnSecondDiag(player, column, row);
    }

    /**
     * play randomly a game between two players
     */
    public void play() {
        boolean player = true;
        for (Integer k = 0; k < nb_columns * nb_rows; k++) {
            LOGGER.finer(k + " coups déjà joués.");
            if (playOnce(player)) {
                LOGGER.info("Player " + (player ? 1 : 2) + " won !");
                return;
            }
            player = !player;
        }
        LOGGER.info("Match nul !");
    }

    /**
     * change level of messages displayed in the console
     * @param level
     */
    public void setLevelLogger(Level level) {
        for(Handler h : LOGGER.getHandlers()) {
            h.setLevel(level);
        }
    }

    public Tuple<List<Integer>, List<Integer>> getFullCells() {
        LinkedList l1 = new LinkedList();
        LinkedList l2 = new LinkedList();
        Long aLong = this.state;
        Integer k = 0;
        while (aLong != 0) {
            if(aLong % 3 != 0) {
                l1.add(k);
                l2.add(aLong % 3);
            }
            k++;
            aLong /= 3;
        }
        return new Tuple(l1, l2);
    }

    public Boolean getCell(Integer index) {
        LOGGER.info("index : " + index);
        int i = (int) ((long) ((this.state / powers[index]) % 3));
        switch (i) {
            case 0:
                return null;
            case 1:
                return false;
            case 2:
                return true;
            default:
                LOGGER.severe("Value of cell unknown");
                return null;
        }
    }


    @Override
    public Position getInitialState() {
        return this.state;
    }

    @Override
    public Boolean getPlayer(Position position) {
        return position.getPlayer();
    }

    @Override
    public List<Pair<Integer, Integer>> getActions(Position position) {
        return null;
    }

    @Override
    public Position getResult(Position position, Pair<Integer, Integer> integerIntegerPair) {
        return position.getResult();
    }

    @Override
    public boolean isTerminal(Position position, Pair<Integer, Integer> integerIntegerPair) {
        if(position.isFull()) {
            LOGGER.info("La grille est pleine.");
            return true;
        }
        return isWon(!position.getPlayer(), integerIntegerPair.getFirst(), integerIntegerPair.getSecond(), 0);
    }

    @Override
    public boolean isTerminal(Position position) {
        return false;
    }

    @Override
    public double getUtility(Position position, Boolean aBoolean) {
        return 0;
    }
}
