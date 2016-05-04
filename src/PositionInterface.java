import structures.Pair;

/**
 * Created by wilhelm on 04/05/16.
 */
public interface PositionInterface {
    Boolean getPlayer();

    Boolean[][] getState();

    Integer getCells_left();

    boolean isFull();

    PositionInterface getResult(Position position, Pair<Integer, Integer> action);

    Boolean getCell(Integer column, Integer row);

    Integer getUtility();

    void computeUtility();
}
