package algorithmes;

import java.util.*;
import structures.Pair;
import structures.Tuple;

public class Alpha_Beta_memoization<State, Action, Player> extends Algorithmes<State, Action, Player>
		implements AlgorithmesSearch<State, Action> {

	Map<State, Pair<Double, Double>> Transposition_Table = new HashMap<State, Pair<Double, Double>>();
	
	public Alpha_Beta_memoization(Game<State, Action, Player> game) {
		super(game);
	}

	@Override
	public Action makeDecision(State state) {
		// TODO Auto-generated method stub
		Action result = null;
		double resultValue = Double.NEGATIVE_INFINITY;
		Player player = game.getPlayer(state);
		for (Action action : game.getActions(state)) {
			double value = -maxValue(game.getResult(state, action), player, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
			if (value > resultValue) {
				result = action;
				resultValue = value;
			}
		}
		return result;
	}
	
	public double maxValue(State state, Player player, double alpha, double beta) {
		if (game.isTerminal(state)){
			double value = game.getUtility(state, player);
			//Transposition_Table.put(state, value);
			return value;
		}
		Pair<Double, Double> prob_value = Transposition_Table.get(state);
		
		/*if(prob_value != null){
			return prob_value;
		}*/
		
		double value = Double.NEGATIVE_INFINITY;
		for (Action action : game.getActions(state)) {
			value = Math.max(value, -maxValue(game.getResult(state, action), player, -beta, -alpha));
			alpha = Math.max(alpha, value);
			if (alpha >= beta)
				return value;
		}
		/*Transposition_Table.put(state, value);*/
		return value;
	}

}
