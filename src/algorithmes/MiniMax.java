package algorithmes;

public class MiniMax<State, Action, Player> extends Algorithmes<State, Action, Player> implements AlgorithmesSearch<State, Action> {
	
	public MiniMax (Game<State, Action, Player> game){
		super(game);
	}
	
	@Override
	public Action makeDecision(State state) {
		// TODO Auto-generated method stub
		Action result = null;
		double resultValue = Double.NEGATIVE_INFINITY;
		Player player = game.getPlayer(state);
		for (Action action : game.getActions(state)) {
			double value = minValue(game.getResult(state, action), player);
			if (value > resultValue) {
				result = action;
				resultValue = value;
			}
		}
		return result;
	}
	
	public double minValue(State state, Player player){
		if (game.isTerminal(state))
			return game.getUtility(state, player);
		double value = Double.POSITIVE_INFINITY;
		for (Action action : game.getActions(state))
			value = Math.min(value,	maxValue(game.getResult(state, action), player));
		return value;
	}

	public double minValue(State state, Player player, Action action){
		if (game.isTerminal(state, action))
			return game.getUtility(state, player);
		double value = Double.POSITIVE_INFINITY;
		for (Action curaction : game.getActions(state))
			value = Math.min(value,	maxValue(game.getResult(state, curaction), player, curaction));
		return value;
	}

	public double maxValue(State state, Player player, Action action) {
		if (game.isTerminal(state, action))
			return game.getUtility(state, player);
		double value = Double.NEGATIVE_INFINITY;
		for (Action curaction : game.getActions(state))
			value = Math.max(value, minValue(game.getResult(state, curaction), player, curaction));
		return value;
	}
	
	public double maxValue(State state, Player player) { 
		if (game.isTerminal(state))
			return game.getUtility(state, player);
		double value = Double.NEGATIVE_INFINITY;
		for (Action action : game.getActions(state))
			value = Math.max(value, minValue(game.getResult(state, action), player));
		return value;
	}
	

}
