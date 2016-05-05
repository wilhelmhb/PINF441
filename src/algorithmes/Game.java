package algorithmes;

import java.util.List;

public interface Game<State, SuperState, Action, Player> {

	State getInitialState();

	Player getPlayer(State state);

	List<Action> getActions(State state);

	SuperState getResult(SuperState state, Action action);

	boolean isTerminal(State state);

	boolean isTerminal(State state, Action action);

	double getUtility(State state, Player player);
}
