import structures.Pair;

/**
 * Created by wilhelm on 05/05/16.
 */
public class PositionTicTacToe extends Position<Pair<Integer, Integer>> {


    public PositionTicTacToe(Boolean[][] state, Integer aim) {
        super(state, aim);
    }

    public PositionTicTacToe(Boolean[][] state, Boolean player, Integer utility, Integer cells_left, Integer aim) {
        super(state, player, utility, cells_left, aim);
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
            System.out.println("Problème, tu essayes de modifier une cellule déjà pleine !");
            return null;
        }
        //make a move
        Boolean[][] state = position.state.clone();
        state[column][row] = position.player;
        Position p = new PositionTicTacToe(state, !position.player, position.utility, position.cells_left - 1, position.aim);
        if(p.isWon(position.player, column, row)) {
            p.utility = position.player ? 1 : -1;
        }
        return p;
    }
}
