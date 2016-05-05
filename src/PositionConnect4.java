import java.util.LinkedList;
import java.util.List;

/**
 * Created by wilhelm on 05/05/16.
 */
public class PositionConnect4 extends Position<Integer> {


    public PositionConnect4(Boolean[][] state, Integer aim) {
        super(state, aim);
    }

    public PositionConnect4(Boolean[][] state, Boolean player, Integer utility, Integer cells_left, Integer aim) {
        super(state, player, utility, cells_left, aim);
    }

    public PositionConnect4(Integer nb_columns, Integer nb_rows, Integer aim) {
        super(new Boolean[nb_columns][nb_rows], aim);
    }

    public boolean isColumnFull(Integer column) {
        return state[column][0] != null;
    }

    @Override
    public Position getResult(Position position, Integer action) {
        if((position.isTerminal())) {
            System.out.println("Problème, tu essayes de modifier une position terminale !");
            return null;
        }
        Integer column = action;
        int i = 0;
        while(position.state[column][i] == null) {
            i++;
        }
        i--;
        if(i < 0) {
            System.out.println("Problème, tu essayes de d'ajouter un pion dans une colonne pleine !");
            return null;
        }
        //make a move
        Boolean[][] state = position.state.clone();
        state[column][i] = position.player;
        Position p = new PositionConnect4(state, !position.player, position.utility, position.cells_left - 1, position.aim);
        if(p.isWon(position.player, column, i)) {
            p.utility = position.player ? 1 : -1;
        }
        return p;
    }

    @Override
    public boolean isFull() {
        for(int i = 0 ; i < state.length ; i++) {
            if(state[i][0] == null) {
                return true;
            }
        }
        return false;
    }

    @Override
    List<Integer> getActions() {
        LinkedList l = new LinkedList();
        for(int i = 0 ; i < state.length ; i++) {
            if(isColumnFull(i)) {
                    l.add(i);
            }
        }
        return l;
    }
}
