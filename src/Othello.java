import structures.Pair;
import structures.Tuple;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * class representing the whole game
 * @author Guillaume
 */
public class Othello extends JPanel implements Game<PositionOthello, Position, Pair<Integer, Integer>, Boolean> {
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
    private PositionOthello state;
    private Cell[][] c;

    /* algorithms */
    private MiniMax<PositionOthello, Position, Pair<Integer, Integer>, Boolean> miniMax;
    private NegaMax<PositionOthello, Position, Pair<Integer, Integer>, Boolean> negaMax;
    private Alpha_Beta<PositionOthello, Position, Pair<Integer, Integer>, Boolean> alphaBeta;

    public Othello(Integer cell_height, Integer cell_width, Color color_first_player, Color color_second_player) {
        Scanner sc = new Scanner(System.in);
        this.nb_columns = sc.nextInt();
        this.nb_rows = sc.nextInt();
        this.state = new PositionOthello(nb_columns, nb_rows);
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
        this.miniMax = new MiniMax(this);
        this.negaMax = new NegaMax<>(this);
        this.alphaBeta = new Alpha_Beta<>(this);
    }

    /**
     * instantiate a Tictactoe given the size, dimensions and colors for the players
     * @param height
     * @param width
     * @param cell_height
     * @param cell_width
     * @param color_first_player
     * @param color_second_player
     */
    public Othello(Integer height, Integer width, Integer cell_height, Integer cell_width, Color color_first_player, Color color_second_player) {
        /* Instantiate the constants in the Tictactoe */
        this.cell_width = cell_width;
        this.cell_height = cell_height;
        this.nb_columns = width;
        this.nb_rows = height;
        this.unknown = Color.GRAY;
        this.checked = color_first_player;
        this.unchecked = color_second_player;
        this.state = new PositionOthello(new Boolean[nb_columns][nb_rows]);
        this.c = new Cell[nb_columns][nb_rows];
        this.setSize(new Dimension(this.cell_height * this.nb_rows, this.cell_width * this.nb_columns));
        generateGraphics();
        this.miniMax = new MiniMax(this);
        this.negaMax = new NegaMax<>(this);
        this.alphaBeta = new Alpha_Beta<>(this);

        LOGGER.fine("Tictactoe instancié");
        //this.setPreferredSize(new Dimension(cell_width*(nb_columns + nb_clues_rows),cell_height*(nb_rows)));
    }

    /**
     * instantiate a Tictactoe given the size and dimensions
     * @param height
     * @param width
     * @param cell_width
     * @param cell_height
     */
    public Othello(Integer height, Integer width, Integer cell_width, Integer cell_height) {
        this(height, width, cell_width, cell_height, Color.WHITE, Color.BLACK);
    }

    /**
     * instantiate a Tictactoe given the size
     * @param height
     * @param width
     */
    public Othello(Integer height, Integer width) {
        this(height, width, 50, 50);
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
        //System.out.println("move begin");
        this.state = (PositionOthello) this.getResult(state, new Tuple<>(column, row));
        for(int i = 0; i < state.getState().length;i++) {
            for(int j = 0; j < state.getState()[0].length ; j++) {
                if(state.getCell(i, j) != null) {
                    c[i][j].changeColor(state.getCell(i,j) ? checked : unchecked);
                }
            }
        }
        //System.out.println("move end");
    }

    /**
     * verify that the cell (column, row) doesn't contain a piece yet, then place a piece on it
     * @param player
     * @param column
     * @param row
     * @return true if move is allowed
     */
    public boolean allowedMove(Boolean player, Integer column, Integer row) {
        if(this.state.getCell(column, row) != null) {
            return false;
        }
        move(player, column, row);
        return true;
    }

    /**
     * test if a piece can be added somewhere in the game
     * @return true if no piece can be placed any more
     */
    public boolean isOthelloFull() {
        return state.isFull();
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
    public PositionOthello getInitialState() {
        return this.state;
    }

    @Override
    public Boolean getPlayer(PositionOthello position) {
        return position.getPlayer();
    }

    @Override
    public List<Pair<Integer, Integer>> getActions(PositionOthello position) {
        return position.getActions();
    }

    @Override
    public Position getResult(Position state, Pair<Integer, Integer> action) {
        //System.out.println("getResultOthello begin");
        return this.state.getResult(state, action);
    }

    @Override
    public boolean isTerminal(PositionOthello position) {
        return position.isTerminal();
    }

    @Override
    public boolean isTerminal(PositionOthello PositionOthello, Pair<Integer, Integer> integerIntegerPair) {
        PositionOthello p = (PositionOthello) getResult(PositionOthello, integerIntegerPair);
        return p.isTerminal();
    }

    @Override
    public double getUtility(PositionOthello position, Boolean aBoolean) {
        return aBoolean ? position.getUtility() : -position.getUtility();
    }

    public Boolean getCell(Integer column, Integer row) {
        return state.getCell(column, row);
    }

    public Boolean[][] getState() {
        return this.state.getState();
    }

    public void setCell(Integer column, Integer row, Boolean value) {
        state.setCell(column, row, value);
    }

    public Pair<Integer, Integer> chooseCellRandomly() {
        List l = getActions(this.state);
        int i = l.size();
        return (Pair<Integer, Integer>) l.get((int) Math.floor(Math.random() * i));
    }

    public boolean playOnceRandomly(boolean player) {
        Pair<Integer, Integer> cell = chooseCellRandomly();
        Integer column = cell.getFirst();
        Integer row = cell.getSecond();
        LOGGER.finest("selected column : "+ column + " and selected row : "+ row);
        if(!allowedMove(player, column, row)) {
            LOGGER.severe("L'algo est pourrave, vieux !");
        }
        return state.isTerminal();
    }

    public void playRandomly() {
        boolean player = state.getPlayer();
        if(!isTerminal(state)) {
            while (!playOnceRandomly(player)) {
                player = !player;
                try {
                    Thread.sleep(2000);
                }
                catch (Exception e){
                }
            }
            System.out.println(state.isWon() == null ? "Match nul !" : (state.isWon() ? "Player 1 win" : "Player 2 win"));
        }
        System.out.println(state.isWon() == null ? "Match nul !" : (state.isWon() ? "Player 1 win" : "Player 2 win"));
    }

    public Pair<Integer, Integer> chooseCellMiniMax() {
        return miniMax.makeDecision(state);
    }

    public boolean playOnceMiniMax(boolean player) {
        Pair<Integer, Integer> cell = chooseCellMiniMax();
        Integer column = cell.getFirst();
        Integer row = cell.getSecond();
        LOGGER.finest("selected column : "+ column + " and selected row : "+ row);
        if(!allowedMove(player, column, row)) {
            LOGGER.severe("MiniMax est pourrave, vieux !");
        }
        return state.isWon(player, column, row);
    }

    public void playMiniMax() {
        boolean player = state.getPlayer();
        if(!isTerminal(state)) {
            while (!playOnceMiniMax(player)) {
                player = !player;
            }
            System.out.println(state.isWon() == null ? "Match nul !" : (state.isWon() ? "Player 1 win" : "Player 2 win"));
        }
        System.out.println(state.isWon() == null ? "Match nul !" : (state.isWon() ? "Player 1 win" : "Player 2 win"));
    }

    public Pair<Integer, Integer> chooseCellNegaMax() {
        return (Tuple) negaMax.makeDecision(state);
    }

    public boolean playOnceNegaMax(boolean player) {
        Tuple<Integer, Integer> cell = (Tuple) chooseCellNegaMax();
        Integer column = cell.getFirst();
        Integer row = cell.getSecond();
        LOGGER.finest("selected column : "+ column + " and selected row : "+ row);
        if(!allowedMove(player, column, row)) {
            LOGGER.severe("MiniMax est pourrave, vieux !");
        }
        return state.isWon(player, column, row);
    }

    public void playNegaMax() {
        boolean player = state.getPlayer();
        if(!isTerminal(state)) {
            while (!playOnceNegaMax(player)) {
                player = !player;
            }
            System.out.println(state.isWon() == null ? "Match nul !" : (state.isWon() ? "Player 1 win" : "Player 2 win"));
        }
        System.out.println(state.isWon() == null ? "Match nul !" : (state.isWon() ? "Player 1 win" : "Player 2 win"));
    }

    public Pair<Integer, Integer> chooseCellAlphaBeta() {
        return (Tuple) alphaBeta.makeDecision(state);
    }

    public boolean playOnceAlphaBeta(boolean player) {
        Tuple<Integer, Integer> cell = (Tuple) chooseCellAlphaBeta();
        Integer column = cell.getFirst();
        Integer row = cell.getSecond();
        LOGGER.finest("selected column : "+ column + " and selected row : "+ row);
        if(!allowedMove(player, column, row)) {
            LOGGER.severe("MiniMax est pourrave, vieux !");
        }
        return state.isWon(player, column, row);
    }

    public void playAlphaBeta() {
        boolean player = state.getPlayer();
        if(!isTerminal(state)) {
            while (!playOnceAlphaBeta(player)) {
                player = !player;
            }
            System.out.println(state.isWon() == null ? "Match nul !" : (state.isWon() ? "Player 1 win" : "Player 2 win"));
        }
        System.out.println(state.isWon() == null ? "Match nul !" : (state.isWon() ? "Player 1 win" : "Player 2 win"));
    }

    public void setPlayer(boolean player) {
        state.setPlayer(player);
    }

}
