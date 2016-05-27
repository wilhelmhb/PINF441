public class Alpha_Beta<State, SuperState, Action, Player> extends Algorithmes<State, SuperState, Action, Player> implements AlgorithmesSearch<State, Action> {

	public Alpha_Beta(Game<State, SuperState, Action, Player> game) {
		super(game);
	}

	@Override
	public Action makeDecision(State state) {
		// TODO Auto-generated method stub
		Action result = null;
		double resultValue = Double.NEGATIVE_INFINITY;
		Player player = game.getPlayer(state);
		for (Action action : game.getActions(state)) {
			double value = minValue((State) game.getResult((SuperState) state, action), player, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
			if (value > resultValue) {
				result = action;
				resultValue = value;
			}
		}
		System.out.println("Action : " + result);
		System.out.println("Utilité : " + resultValue);
		return result;
	}

	public Boolean decide(State state) {
		// TODO Auto-generated method stub
		Action result = null;
		double resultValue = Double.NEGATIVE_INFINITY;
		Player player = game.getPlayer(state);
		for (Action action : game.getActions(state)) {
			double value = minValue((State) game.getResult((SuperState) state, action), player, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
			if (value > resultValue) {
				result = action;
				resultValue = value;
			}
		}
		if(resultValue == 1.0) {
			return true;
		}
		if(resultValue == -1.0) {
			return null;
		}
		return false;
	}

	public double maxValue(State state, Player player, double alpha, double beta) {
		if (game.isTerminal(state))
			return game.getUtility(state, player);
		double value = Double.NEGATIVE_INFINITY;
		for (Action action : game.getActions(state)) {
			value = Math.max(value, minValue((State) game.getResult((SuperState) state, action), player, alpha, beta));
			if (value >= beta)
				return value;
			alpha = Math.max(alpha, value);
		}
		return value;
	}
	public double minValue(State state, Player player, double alpha, double beta) {
		if (game.isTerminal(state))
			return game.getUtility(state, player);
		double value = Double.POSITIVE_INFINITY;
		for (Action action : game.getActions(state)) {
			value = Math.min(value, maxValue((State) game.getResult((SuperState) state, action), player, alpha, beta));
			if (value <= alpha)
				return value;
			beta = Math.min(beta, value);
		}
		return value;
	}

}
