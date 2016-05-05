import algorithmes.Game;
import structures.Pair;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import java.util.Scanner;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * class representing the whole game
 * @author Guillaume
 */
public class Tictactoe extends JPanel implements Game<PositionTicTacToe, Position, Pair<Integer, Integer>, Boolean> {
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /* definition of the dimensions of each cell */
    private Integer cell_width;
    private Integer cell_height;
    /* how many cells do we have ? */
    private Integer nb_columns;
    private Integer nb_rows;
    /* what colors will we use ? */
    private Color unknown;
    private Color checked;
    private Color unchecked;
    /* values of the grid */
    private PositionTicTacToe state;
    private Cell[][] c;

    public Tictactoe(Integer cell_height, Integer cell_width, Color color_first_player, Color color_second_player) {
        Scanner sc = new Scanner(System.in);
        this.nb_columns = sc.nextInt();
        this.nb_rows = sc.nextInt();
        this.aim = sc.nextInt();
        this.state = new Position();
        String s;
        for(int i = 0 ; i < nb_rows ; i++) {
            s = sc.nextLine();
            for(int j = 0 ; j < nb_columns ; i++) {
                switch(s.charAt(j)) {
                    case '0':
                        state.set(i, j, true);
                        break;
                    case '@':
                        state.set(i, j, false);
                        break;
                    default:
                }
            }
        }
        this.cell_height = cell_height;
        this.cell_width = cell_width;
        this.checked = color_first_player;
        this.unchecked = color_second_player;
        this.unknown = Color.GRAY;
    }

    /**
     * instantiate a Tictactoe given the size, aim, dimensions and colors for the players
     * @param height
     * @param width
     * @param aim : number of cells in a range to reach in order to win
     * @param cell_height
     * @param cell_width
     * @param color_first_player
     * @param color_second_player
     */
    public Tictactoe(Integer height, Integer width, Integer aim, Integer cell_height, Integer cell_width, Color color_first_player, Color color_second_player) {
        /* Instantiate the constants in the Tictactoe */
        this.cell_width = cell_width;
        this.cell_height = cell_height;
        this.nb_columns = width;
        this.nb_rows = height;
        this.unknown = Color.GRAY;
        this.checked = color_first_player;
        this.unchecked = color_second_player;
        this.state = new Position(new Boolean[nb_columns][nb_rows]);
        this.c = new Cell[nb_columns][nb_rows];
        this.setSize(new Dimension(this.cell_height * this.nb_rows, this.cell_width * this.nb_columns));
        this.aim = aim;
        generateGraphics();

        LOGGER.fine("Tictactoe instancié");
        //this.setPreferredSize(new Dimension(cell_width*(nb_columns + nb_clues_rows),cell_height*(nb_rows)));
    }

    /**
     * instantiate a Tictactoe given the size, aim and dimensions
     * @param height
     * @param width
     * @param aim
     * @param cell_width
     * @param cell_height
     */
    public Tictactoe(Integer height, Integer width, Integer aim, Integer cell_width, Integer cell_height) {
        this(height, width, aim, cell_width, cell_height, Color.WHITE, Color.BLACK);
    }

    /**
     * instantiate a Tictactoe given the size, aim and colors for the players
     * @param height
     * @param width
     * @param aim
     * @param color_first_player
     * @param color_second_player
     */
    public Tictactoe(Integer height, Integer width, Integer aim, Color color_first_player, Color color_second_player) {
        this(height, width, aim, 50, 50, color_first_player, color_second_player);
    }

    /**
     * instantiate a Tictactoe given the size and aim
     * @param height
     * @param width
     * @param aim : number of cells in a range to reach in order to win
     */
    public Tictactoe(Integer height, Integer width, Integer aim) {
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
     * generates the graphic aspect of our Tictactoe
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
                state.state[k][i] = null;
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
    public void move(boolean player, Integer column, Integer row) {
        this.state.state[column][row] = player;
        //state.move(column, row);
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
    public boolean allowedMove(Boolean player, Integer column, Integer row) {
        if(this.state.getState()[column][row] != null) {
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
    public Integer chooseAllowedRow(Integer column) {
        Integer row = (int) Math.floor(Math.random() * this.nb_rows);
        while(state.getState()[column][row] != null) {
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
            if(this.state.getState()[column][k] == null) {
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
    public boolean isTictactoeFull() {
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
    public boolean isWonHorizontally(boolean player, Integer column, Integer row) {
        Integer count = 1;
        Integer column_left = column - 1;
        Integer column_right = column + 1;
        Boolean[][] a = state.getState();
        while((column_left >= 0)&&(a[column_left][row] != null)&&(a[column_left][row] == player)) {
            count++;
            column_left--;
        }
        while((column_right < nb_columns)&&(a[column_right][row] != null)&&(a[column_right][row] == player)) {
            count++;
            column_right++;
        }
        if(count == this.aim) {
            LOGGER.info("won horizontally : search for crosses");
            LOGGER.info(column_left + " : " + column_right);
            for(Integer i = ++column_left ; i < column_right ; i++) {
                c[i][row].makeACross();
                LOGGER.info("made a cross on cell (" + i + "," + row + ")");
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
        Boolean[][] a = state.getState();
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
        Boolean[][] a = state.getState();
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
        Boolean[][] a = state.getState();
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
    public boolean isWon(boolean player, Integer column, Integer row) {
        return isWonHorizontally(player, column, row)||isWonVertically(player, column, row)||isWonOnFirstDiag(player, column, row)||isWonOnSecondDiag(player, column, row);
    }

    /**
     * play randomly a game between two players
     */
    public void play() {
        boolean player = state.getPlayer();
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
        return position.getResult(position, integerIntegerPair);
    }

    @Override
    public boolean isTerminal(Position position, Pair<Integer, Integer> integerIntegerPair) {
        if(position.isFull()) {
            LOGGER.info("La grille est pleine.");
            return true;
        }
        return isWon(!position.getPlayer(), integerIntegerPair.getFirst(), integerIntegerPair.getSecond());
    }

    @Override
    public boolean isTerminal(Position position) {
        for(int i = 0 ; i < position.getState().length ; i++) {
            for(int j = 0 ; j < position.getState()[0].length ; j++) {
                if(isWon(position.getCell(i,j), i, j)) {
                    return true;
                }
            }
        }
        return isTictactoeFull();
    }

    @Override
    public double getUtility(Position position, Boolean aBoolean) {
        return 0;
    }
}
