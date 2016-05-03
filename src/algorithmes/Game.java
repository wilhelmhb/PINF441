package algorithmes;

import java.util.List;

public interface Game<State, Action, Player> {

	State getinitialState();

	Player getPlayer(State state);

	List<Action> getActions(State state);

	State getResult(State state, Action action);

	boolean isTerminal(State state);

	double getUtility(State state, Player player);
}
