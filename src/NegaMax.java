public class NegaMax<State, SuperState, Action, Player> extends Algorithmes<State, SuperState, Action, Player> implements AlgorithmesSearch<State, Action> {
	
	public NegaMax(Game<State, SuperState, Action, Player> game) {
		super(game);
	}

	@Override
	public Action makeDecision(State state) {
		// TODO Auto-generated method stub
		Action result = null;
		double resultValue = Double.NEGATIVE_INFINITY;
		Player player = game.getPlayer(state);
		for (Action action : game.getActions(state)) {
			double value = -maxValue((State) game.getResult((SuperState) state, action), player);
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
			value = Math.max(value, -maxValue((State) game.getResult((SuperState) state, action), player));
		return value;
	}

}
