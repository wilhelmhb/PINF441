package algorithmes;

public abstract class Algorithmes<State, SuperState, Action, Player> {
	
	protected Game<State, SuperState, Action, Player> game;
	
	public Algorithmes(Game<State, SuperState, Action, Player> game){
		this.game = game;
	}

}
