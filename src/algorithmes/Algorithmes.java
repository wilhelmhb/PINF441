package algorithmes;

public abstract class Algorithmes<State, Action, Player> {
	
	protected Game<State, Action, Player> game;
	
	public Algorithmes(Game<State, Action, Player> game){
		this.game = game;
	}

}
