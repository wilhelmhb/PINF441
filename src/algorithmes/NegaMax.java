package algorithmes;

public class NegaMax<State, Action, Player> extends Algorithmes<State, Action, Player> implements AlgorithmesSearch<State, Action> {
	
	public NegaMax(Game<State, Action, Player> game) {
		super(game);
	}

	@Override
	public Action makeDecision(State state) {
		// TODO Auto-generated method stub
		Action result = null;
		double resultValue = Double.NEGATIVE_INFINITY;
		Player player = game.getPlayer(state);
		for (Action action : game.getActions(state)) {
			double value = -maxValue(game.getResult(state, action), player);
			if (value > resultValue) {
				result = action;
				resultValue = value;
			}
		}
		return result;
	}
	
	public double maxValue(State state, Player player) { 
		if (game.isTerminal(state))
			return game.getUtility(state, player);
		double value = Double.NEGATIVE_INFINITY;
		for (Action action : game.getActions(state))
			value = Math.max(value, -maxValue(game.getResult(state, action), player));
		return value;
	}

}
