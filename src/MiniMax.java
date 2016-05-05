import structures.Pair;

import javax.swing.text.Position;
import java.util.List;

public class MiniMax<State, SuperState, Action, Player> extends Algorithmes<State, SuperState, Action, Player> implements AlgorithmesSearch<State, Action> {
	
	public MiniMax (Game<State, SuperState, Action, Player> game){
		super(game);
	}
	
	@Override
	public Action makeDecision(State state) {
		// TODO Auto-generated method stub
		System.out.println("MinMax : makeDecision");
		Action result = null;
		double resultValue = Double.NEGATIVE_INFINITY;
		Player player = game.getPlayer(state);
		System.out.println(player);
		List<Action> l = game.getActions(state);
		/*for(Action i : l) {
			System.out.println(((Pair) i).getFirst() + " , " + ((Pair) i).getSecond());
		}*/
		for (Action action : l) {
			System.out.println(((Pair) action).getFirst() + " , " + ((Pair) action).getSecond());
			double value = minValue((State) game.getResult((SuperState) state, action), player);
			System.out.println("value : " + value);
			if (value > resultValue) {
				System.out.println("changement de valeur");
				result = action;
				resultValue = value;
			}
		}
		return result;
	}
	
	public double minValue(State state, Player player){
		System.out.println("MiniMax : minValue : " + ((Position)state));
		if (game.isTerminal(state)) {
			System.out.println("Utility : " + game.getUtility(state, player));
			return game.getUtility(state, player);
		}
		System.out.println("State is not terminal");
		double value = Double.POSITIVE_INFINITY;
		for (Action action : game.getActions(state))
			value = Math.min(value,	maxValue((State) game.getResult((SuperState) state, action), player));
		return value;
	}

	public double minValue(State state, Player player, Action action){
		if (game.isTerminal(state, action))
			return game.getUtility(state, player);
		double value = Double.POSITIVE_INFINITY;
		for (Action curaction : game.getActions(state))
			value = Math.min(value,	maxValue((State) game.getResult((SuperState) state, curaction), player, curaction));
		return value;
	}

	public double maxValue(State state, Player player, Action action) {
		if (game.isTerminal(state, action))
			return game.getUtility(state, player);
		double value = Double.NEGATIVE_INFINITY;
		for (Action curaction : game.getActions(state))
			value = Math.max(value, minValue((State) game.getResult((SuperState) state, curaction), player, curaction));
		return value;
	}
	
	public double maxValue(State state, Player player) {
		System.out.println("MiniMax : maxValue");
		if (game.isTerminal(state)) {
			System.out.println("Utility : " + game.getUtility(state, player));
			return game.getUtility(state, player);
		}
		double value = Double.NEGATIVE_INFINITY;
		for (Action action : game.getActions(state))
			value = Math.max(value, minValue((State) game.getResult((SuperState) state, action), player));
		return value;
	}
}
