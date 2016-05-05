import structures.Pair;
import structures.Tuple;

import java.util.List;

/**
 * Created by wilhelm on 03/05/16.
 */
public abstract class Position<Action> implements PositionInterface<Action> {
    protected Boolean[][] state;

    protected Boolean player;

    protected Integer cells_left;

    protected Integer utility;

    protected Integer aim;

    public Boolean getPlayer() {
        return player;
    }

    public Boolean[][] getState() {
        return state;
    }

    public Position(Boolean[][] state, Boolean player, Integer utility, Integer cells_left, Integer aim) {
        this.state = state;
        this.utility = utility;
        this.player = player;
        this.cells_left = cells_left;
        this.aim = aim;
    }

    public Position(Boolean[][] state, Integer aim) {
        this.state = state;
        Pair<Pair<Integer, Integer>, Integer> p = computeUtilityAndCellsLeftAndPlayer();
        this.player = (p.getSecond() == 0);
        this.aim = aim;
        Pair<Integer, Integer> p1 = p.getFirst();
        this.cells_left = p1.getSecond();
        this.utility = p1.getFirst();

    }

    public Pair<Pair<Integer,Integer>,Integer> computeUtilityAndCellsLeftAndPlayer() {
        Integer cells_left = 0;
        Integer utility = 0;
        int i = 0;
        for(int k = 0; k < state.length ; k++) {
            for(int j = 0 ; j < state[0].length ; j++) {
                if(getCell(k, j) != null) {
                    if(getCell(k, j)) {
                        i++;
                        if(isWon(true, k, j)) {
                            utility = 1;
                        }
                    }
                    else {
                        i--;
                        if(isWon(false, k, j)) {
                            utility = -1;
                        }
                    }
                }
                else {
                    cells_left++;
                }
            }
        }
        return new Tuple(new Tuple(utility, cells_left), i);
    }

    public Integer getCellsLeft() {
        return this.cells_left;
    }

    public boolean isFull(){
        return getCellsLeft() == 0;
    }

    public Integer getUtility() {
        return utility;
    }

    public boolean equals(Position e) {
        return (hashCode() == e.hashCode()) && (state == e.getState());
    }

    public boolean sameAs(Position e) {
        return (hashCode() == e.hashCode()) && (sameStateAs(e.getState()));
    }

    public boolean sameStateAs(Boolean[][] state) {
        int nb_columns = this.state.length;
        int nb_columns_s = state.length;
        int nb_rows = this.state[0].length;
        int nb_rows_s = state[0].length;
        if((nb_columns == nb_columns_s)&&(nb_rows == nb_rows_s)) {
            for(int i = 0 ; i < nb_columns ; i++) {
                for(int j = 0 ; j < nb_rows ; j++) {
                    if(this.state[i][j] != state[i][j]) {
                        return false;
                    }
                }
            }
            return true;
        }
        else {
            return false;
        }
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
        while((column_left >= 0)&&(getCell(column_left, row) != null)&&(getCell(column_left, row) == player)) {
            count++;
            column_left--;
        }
        while((column_right < state.length)&&(getCell(column_right, row) != null)&&(getCell(column_right, row) == player)) {
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
    public boolean isWonVertically(boolean player, Integer column, Integer row) {
        Integer count = 1;
        Integer row_up = row - 1;
        Integer row_down = row + 1;
        while((row_up >= 0)&&(getCell(column, row_up) != null)&&(getCell(column, row_up) == player)) {
            count++;
            row_up--;
        }
        while((row_down < state[0].length)&&(getCell(column, row_down) != null)&&(getCell(column, row_down) == player)) {
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
    public boolean isWonOnFirstDiag(boolean player, Integer column, Integer row) {
        Integer count = 1;
        Integer row_up = row - 1;
        Integer row_down = row + 1;
        Integer column_left = column - 1;
        Integer column_right = column + 1;
        while((row_up >=0)&&(column_right < state.length)&&(getCell(column_right, row_up) != null)&&(getCell(column_right, row_up) == player)) {
            count++;
            row_up--;
            column_right++;
        }
        while((row_down < state[0].length)&&(column_left >= 0)&&(getCell(column_left, row_down) != null)&&(getCell(column_left, row_down) == player)) {
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
    public boolean isWonOnSecondDiag(boolean player, Integer column, Integer row) {
        Integer count = 1;
        Integer row_up = row - 1;
        Integer row_down = row + 1;
        Integer column_left = column - 1;
        Integer column_right = column + 1;
        while((row_down < state[0].length)&&(column_right < state.length)&&(getCell(column_right, row_down) != null)&&(getCell(column_right, row_down) == player)) {
            count++;
            row_down++;
            column_right++;
        }
        while((row_up >= 0)&&(column_left >= 0)&&(getCell(column_left, row_up) != null)&&(getCell(column_left, row_up) == player)) {
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
    public boolean isWon(boolean player, Integer column, Integer row) {
        return isWonHorizontally(player, column, row)||isWonVertically(player, column, row)||isWonOnFirstDiag(player, column, row)||isWonOnSecondDiag(player, column, row);
    }

    public boolean isTerminal() {
        return isFull()||(utility!=0);
    }

    public Boolean getCell(Integer column, Integer row) {
        return state[column][row];
    }

    public boolean setCell(Integer column, Integer row, Boolean value) {
        if(this.getCell(column, row) != null) {
            return false;
        }
        else {
            state[column][row] = value;
            return true;
        }
    }

    abstract List<Action> getActions();

    public void print() {
        for(int i = 0 ; i < state[0].length ; i++) {
            for(int j = 0 ; j < state.length ; j++) {
                System.out.print((getCell(j,i) != null) ? (getCell(j,i) ? "0" : "@"): ".");
            }
            System.out.println();
        }
    }
}
