package algorithmes;

public class Alpha_Beta<State, Action, Player> extends Algorithmes<State, Action, Player> implements AlgorithmesSearch<State, Action> {

	public Alpha_Beta(Game<State, Action, Player> game) {
		super(game);
	}

	@Override
	public Action makeDecision(State state) {
		// TODO Auto-generated method stub
		Action result = null;
		double resultValue = Double.NEGATIVE_INFINITY;
		Player player = game.getPlayer(state);
		for (Action action : game.getActions(state)) {
			double value = minValue(game.getResult(state, action), player, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
			if (value > resultValue) {
				result = action;
				resultValue = value;
			}
		}
		return result;
	}
	public double maxValue(State state, Player player, double alpha, double beta) {
		if (game.isTerminal(state))
			return game.getUtility(state, player);
		double value = Double.NEGATIVE_INFINITY;
		for (Action action : game.getActions(state)) {
			value = Math.max(value, minValue(game.getResult(state, action), player, alpha, beta));
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
			value = Math.min(value, maxValue(game.getResult(state, action), player, alpha, beta));
			if (value <= alpha)
				return value;
			beta = Math.min(beta, value);
		}
		return value;
	}

}
