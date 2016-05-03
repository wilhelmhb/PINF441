import structures.Pair;

/**
 * Created by wilhelm on 03/05/16.
 */
public class Position {
    protected Boolean[][] state;

    protected Boolean player;

    protected Integer cells_left;

    public Boolean getPlayer() {
        return player;
    }

    public Boolean[][] getState() {
        return state;
    }

    public Position(Boolean[][] state, Boolean player) {
        this.state = state;
        this.player = player;
    }

    public Position(Boolean[][] state) {
        this.state = state;
        this.cells_left = 0;
        int i = 0;
        for(int k = 0; k < state.length ; k++) {
            for(int j = 0 ; j < state[0].length ; j++) {
                if(state[k][j] != null) {
                    if(state[k][j]) {
                        i++;
                    }
                    else {
                        i--;
                    }
                }
                else {
                    this.cells_left++;
                }
            }
        }
        this.player = (i == 0);
    }

    public Integer getCells_left() {
        return this.cells_left;
    }

    public boolean isFull(){
        return this.cells_left == 0;
    }

    public Position getResult(Position position, Pair<Integer, Integer> action) {
        Integer column = action.getFirst();
        Integer row = action.getSecond();
        Position p = new Position(this.state.clone(), !this.player);
        if(p.state[column][row] != null) {
            System.out.println("Problème, tu essayes de modifier une cellule déjà pleine !");
            return null;
        }
        p.state[column][row] = p.player;
        return p;
    }
}
