import structures.Pair;
import structures.Tuple;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by wilhelm on 05/05/16.
 */
public class PositionOthello extends Position<Pair<Integer, Integer>> {

    public PositionOthello(Boolean[][] state) {
        super(state, null);
    }

    public PositionOthello(Integer nb_columns, Integer nb_rows) {
        super(new Boolean[nb_columns][nb_rows], null);
    }

    public PositionOthello(Boolean[][] state, Boolean player, Integer utility, Integer cells_left) {
        super(state, player, utility, cells_left, null);
    }

    /**
     * test if player won the game thanks to a row
     * @param player
     * @param column
     * @param row
     * @return true if thanks to a move in cell (column, row), player won thanks to a row
     */
    public boolean changeLeft(boolean player, Integer column, Integer row) {
        //System.out.println("changeleft begin");
        Integer column_left = column - 1;
        while((column_left >= 0)&&(getCell(column_left, row) != null)&&(getCell(column_left, row) != player)) {
            column_left--;
        }
        /*System.out.println("Player : " + player);
        System.out.println("Left cell : " + column_left);
        System.out.println("Left cell is in grid : " + (column_left >= 0));
        /*System.out.println("Left cell is not null : " + (getCell(column_left, row) != null));
        System.out.println("Reached an other player's cell : " + (getCell(column_left, row) == player));*/
        if((column_left >= 0)&&(getCell(column_left, row) != null)&&(getCell(column_left, row) == player)) {
            for(int i = column_left ; i < column; i++) {
                setCell(i, row, player);
            }
            /*System.out.println("Column : " + column);
            System.out.println("Left column : " + column_left);*/
            return (column != (column_left + 1));
        }
        return false;
    }

    @Override
    public boolean setCell(Integer column, Integer row, Boolean player) {
        state[column][row] = player;
        return true;
    }

    public boolean changeRight(boolean player, Integer column, Integer row) {
        Integer column_right = column + 1;
        while((column_right < state.length)&&(getCell(column_right, row) != null)&&(getCell(column_right, row) != player)) {
            column_right++;
        }
        if((column_right < state.length)&&(getCell(column_right, row) != null)&&(getCell(column_right, row) == player)) {
            for(int i = column_right ; i > column; i--) {
                setCell(i, row, player);
            }
            return (column != (column_right - 1));
        }
        return false;
    }

    public boolean changeHorizontally(boolean player, Integer column, Integer row) {
        return changeLeft(player, column, row) || changeRight(player, column, row);
    }

    public boolean changeUp(boolean player, Integer column, Integer row) {
        Integer row_up = row - 1;
        while((row_up >= 0)&&(getCell(column, row_up) != null)&&(getCell(column, row_up) != player)) {
            row_up--;
        }
        if((row_up >= 0)&&(getCell(column, row_up) != null)&&(getCell(column, row_up) == player)) {
            for(int i = row_up ; i < row; i++) {
                setCell(column, i, player);
            }
            return (row != (row_up + 1));
        }
        return false;
    }

    public boolean changeDown(boolean player, Integer column, Integer row) {
        Integer row_down = row + 1;
        while((row_down < state[0].length)&&(getCell(column, row_down) != null)&&(getCell(column, row_down) != player)) {
            row_down++;
        }
        if((row_down < state[0].length)&&(getCell(column, row_down) != null)&&(getCell(column, row_down) == player)) {
            for(int i = row_down ; i > row; i--) {
                setCell(column, i, player);
            }
            return (row != (row_down + 1));
        }
        return false;
    }

    public boolean changeVertically(boolean player, Integer column, Integer row) {
        return changeUp(player, column, row) || changeDown(player, column, row);
    }

    /**
     * test if player won the game thanks to the first bisector
     * @param player
     * @param column
     * @param row
     * @return true if thanks to a move in cell (column, row), player won thanks to the first bisector
     */
    public boolean changeFirstDiagUp(boolean player, Integer column, Integer row) {
        Integer row_up = row - 1;
        Integer column_right = column + 1;
        while((row_up >=0)&&(column_right < state.length)&&(getCell(column_right, row_up) != null)&&(getCell(column_right, row_up) != player)) {
            row_up--;
            column_right++;
        }
        if((row_up >=0)&&(column_right < state.length)&&(getCell(column_right, row_up) != null)&&(getCell(column_right, row_up) == player)) {
            int j = column_right;
            for(int i = row_up ; i < row; i++) {
                setCell(j, i, player);
                j--;
            }
            return (column != (column_right - 1));
        }
        return false;
    }

    public boolean changeFirstDiagDown(boolean player, Integer column, Integer row) {
        Integer row_down = row + 1;
        Integer column_left = column - 1;
        while((row_down < state[0].length)&&(column_left >= 0)&&(getCell(column_left, row_down) != null)&&(getCell(column_left, row_down) != player)) {
            row_down++;
            column_left--;
        }
        if((row_down < state[0].length)&&(column_left >= 0)&&(getCell(column_left, row_down) != null)&&(getCell(column_left, row_down) == player)) {
            int j = column_left;
            for(int i = row_down ; i > row; i--) {
                setCell(j, i, player);
                j++;
            }
            return (column != (column_left + 1));
        }
        return false;
    }

    public boolean changeFirstDiag(boolean player, Integer column, Integer row) {
        return changeFirstDiagUp(player, column, row) || changeFirstDiagDown(player, column, row);
    }

    public boolean changeSecondDiagUp(boolean player, Integer column, Integer row) {
        Integer row_up = row - 1;
        Integer column_left = column - 1;
        while((row_up >=0)&&(column_left >= 0)&&(getCell(column_left, row_up) != null)&&(getCell(column_left, row_up) != player)) {
            row_up--;
            column_left--;
        }
        if((row_up >=0)&&(column_left >= 0)&&(getCell(column_left, row_up) != null)&&(getCell(column_left, row_up) == player)) {
            int j = column_left;
            for(int i = row_up ; i < row; i++) {
                setCell(j, i, player);
                j++;
            }
            return (column != (column_left + 1));
        }
        return false;
    }

    public boolean changeSecondDiagDown(boolean player, Integer column, Integer row) {
        Integer row_down = row + 1;
        Integer column_right = column + 1;
        while((row_down < state[0].length)&&(column_right < state.length)&&(getCell(column_right, row_down) != null)&&(getCell(column_right, row_down) != player)) {
            row_down++;
            column_right++;
        }
        if((row_down < state[0].length)&&(column_right < state.length)&&(getCell(column_right, row_down) != null)&&(getCell(column_right, row_down) == player)) {
            int j = column_right;
            for(int i = row_down ; i > row; i--) {
                setCell(j, i, player);
                j--;
            }
            return (column != (column_right - 1));
        }
        return false;
    }

    public boolean changeSecondDiag(boolean player, Integer column, Integer row) {
        return changeSecondDiagUp(player, column, row) || changeSecondDiagDown(player, column, row);
    }

    /**
     * test if player won
     * @param player
     * @param column
     * @param row
     * @return true if player won thanks to a piece in cell (column, row)
     */
    public boolean change(boolean player, Integer column, Integer row) {
        return changeHorizontally(player, column, row) || changeVertically(player, column, row) || changeFirstDiag(player, column, row) || changeSecondDiag(player, column, row);
    }

    @Override
    public Position getResult(Position position, Pair<Integer, Integer> action) {
        if((position.isTerminal())) {
            System.out.println("Problème, tu essayes de modifier une position terminale !");
            return null;
        }
        Integer column = action.getFirst();
        Integer row = action.getSecond();
        if(position.state[column][row] != null) {
            System.out.println("Problème, tu essayes de modifier une cellule déjà pleine : "+ column + "," + row);
            return null;
        }
        //make a move
        Boolean[][] state = position.cloneState();
        state[column][row] = position.player;
        change(player, column, row);
        Position p = new PositionOthello(state, !position.player, position.utility, position.cells_left - 1);
        if(p.isWon(position.player, column, row)) {
            p.utility = position.player ? 1 : -1;
        }
        return p;
    }

    @Override
    List<Pair<Integer, Integer>> getActions() {
        LinkedList l = new LinkedList();
        for(int column = 0 ; column < state.length ; column++) {
            for(int row = 0 ; row < state[0].length ; row++) {
                if(this.getCell(column, row) == null) {
                    //System.out.println("Considered cell : (" + column + "," + row + "), because " + getCell(column, row));
                    int column_left = column - 1;
                    int column_right = column + 1;
                    int row_up = row - 1;
                    int row_down = row + 1;
                    //System.out.println(row + ";" + column + ";" + row_up + ";" + row_down + ";" + column_left + ";" + column_right);
                    /*System.out.println("Player : " + player);
                    System.out.println("Left cell still in grid : " + (column_left >= 0));
                    System.out.println("Left cell not empty: " + (getCell(column_left, row) != null));
                    System.out.println("May change left cell : " + (getCell(column_left, row) != player));
                    System.out.println("Not isolated : " + ((column_left >= 0) && (getCell(column_left, row) != null) && (getCell(column_left, row) != player)));
                    */if (
                        (
                            (row_up >= 0) &&
                            (getCell(column, row_up) != null) &&
                            (getCell(column, row_up) != player)
                        ) ||
                        (
                            (row_down < state[0].length) &&
                            (getCell(column, row_down) != null) &&
                            (getCell(column, row_down) != player)
                        ) || //vertically
                        (
                            (column_left >= 0) &&
                            (getCell(column_left, row) != null) &&
                            (getCell(column_left, row) != player)
                        ) ||
                        (
                            (column_right < state.length) &&
                            (getCell(column_right, row) != null) &&
                            (getCell(column_right, row) != player)
                        ) ||//horizontally
                        (
                            (column_right < state.length) &&
                            (row_down < state[0].length) &&
                            (getCell(column_right, row_down) != null) &&
                            (getCell(column_right, row_down) != player)
                        ) ||
                        (
                            (column_left >= 0) &&
                            (row_up >= 0) &&
                            (getCell(column_left, row_up) != null) &&
                            (getCell(column_left, row_up) != player)
                        ) || //second_diag
                        (
                            (column_left >= 0) &&
                            (row_down < state[0].length) &&
                            (getCell(column_left, row_down) != null) &&
                            (getCell(column_left, row_down) != player)
                        ) ||
                        (
                            (row_up >= 0) &&
                            (column_right < state.length) &&
                            (getCell(column_right, row_up) != null) &&
                            (getCell(column_right, row_up) != player))//first_diag
                        ) {
                        PositionOthello po = new PositionOthello(cloneState());
                        if (po.change(player, column, row)) {
                            l.add(new Tuple<Integer, Integer>(column, row));
                        }
                    }
                }
            }
        }
        return l;
    }

    @Override
    public boolean isTerminal() {
        /*System.out.println("isFull : " + isFull());
        System.out.println("Actions : " + getActions());*/
        return isFull() || (getActions().size() == 0);
    }
}
