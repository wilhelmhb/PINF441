import algorithmes.Game;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * class representing the whole game
 * @author Guillaume
 */
public class Connect4 extends JPanel implements Game<PositionConnect4, Position, Integer, Boolean> {
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
    private PositionConnect4 state;
    private Cell[][] c;

    public Connect4(Integer cell_height, Integer cell_width, Color color_first_player, Color color_second_player) {
        Scanner sc = new Scanner(System.in);
        this.nb_columns = sc.nextInt();
        this.nb_rows = sc.nextInt();
        Integer aim = sc.nextInt();
        this.state = new PositionConnect4(nb_columns, nb_rows, aim);
        String s;
        for(int i = 0 ; i < nb_rows ; i++) {
            s = sc.nextLine();
            for(int j = 0 ; j < nb_columns ; i++) {
                switch(s.charAt(j)) {
                    case '0':
                        state.setCell(i, j, true);
                        break;
                    case '@':
                        state.setCell(i, j, false);
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
    public Connect4(Integer height, Integer width, Integer aim, Integer cell_height, Integer cell_width, Color color_first_player, Color color_second_player) {
        /* Instantiate the constants in the Tictactoe */
        this.cell_width = cell_width;
        this.cell_height = cell_height;
        this.nb_columns = width;
        this.nb_rows = height;
        this.unknown = Color.GRAY;
        this.checked = color_first_player;
        this.unchecked = color_second_player;
        this.state = new PositionConnect4(new Boolean[nb_columns][nb_rows], aim);
        this.c = new Cell[nb_columns][nb_rows];
        this.setSize(new Dimension(this.cell_height * this.nb_rows, this.cell_width * this.nb_columns));
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
    public Connect4(Integer height, Integer width, Integer aim, Integer cell_width, Integer cell_height) {
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
    public Connect4(Integer height, Integer width, Integer aim, Color color_first_player, Color color_second_player) {
        this(height, width, aim, 50, 50, color_first_player, color_second_player);
    }

    /**
     * instantiate a Tictactoe given the size and aim
     * @param height
     * @param width
     * @param aim : number of cells in a range to reach in order to win
     */
    public Connect4(Integer height, Integer width, Integer aim) {
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
     */
    public Integer move(boolean player, Integer column) {
        int i = 0;
        while(state.getCell(column, i) == null) {
            i++;
        }
        i--;
        if(i < 0) {
            System.out.println("Problème, tu essayes de d'ajouter un pion dans une colonne pleine !");
            return null;
        }
        state.setCell(column, i, player);
        return i;
    }

    /**
     * verify that the cell (column, row) doesn't contain a piece yet, then place a piece on it
     * @param player
     * @param column
     * @return true if move is allowed
     */
    public Integer allowedMove(Boolean player, Integer column) {
        if(isColumnFull(column)) {
            System.out.println("Algo de choix moisi");
            return null;
        }
        else {
            return move(player, column);
        }
    }

    /**
     * test if the column column is full
     * @param column
     * @return true if not piece can be place on column column
     */
    public boolean isColumnFull(Integer column) {
        return state.isColumnFull(column);
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
    public boolean isConnect4Full() {
        return state.isFull();
    }

    /**
     * play once randomly for the player player
     * @param player
     * @return true if player has won thanks to this move
     */
    public boolean playOnce(boolean player) {
        Integer column = chooseNotFullColumn();
        Integer row;
        if((row = allowedMove(player, column)) == null) {
            LOGGER.severe("Tes algos sont pourraves, vieux !");
        }
        return state.isWon(player, column, row);
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
    public PositionConnect4 getInitialState() {
        return this.state;
    }

    @Override
    public Boolean getPlayer(PositionConnect4 position) {
        return position.getPlayer();
    }

    @Override
    public List<Integer> getActions(PositionConnect4 position) {
        return (position.getActions());
    }

    @Override
    public Position getResult(Position state, Integer action) {
        return this.state.getResult(state, action);
    }

    @Override
    public boolean isTerminal(PositionConnect4 position) {
        return position.isTerminal();
    }

    @Override
    public boolean isTerminal(PositionConnect4 positionConnect4, Integer action) {
        PositionConnect4 p = (PositionConnect4) getResult(positionConnect4, action);
        return p.isTerminal();
    }

    @Override
    public double getUtility(PositionConnect4 position, Boolean aBoolean) {
        return position.getUtility();
    }
}
