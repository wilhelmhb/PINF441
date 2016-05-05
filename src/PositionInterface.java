import structures.Pair;

/**
 * Created by wilhelm on 04/05/16.
 */
interface PositionInterface<Action> {
    Boolean getPlayer();

    Boolean[][] getState();

    Integer getCellsLeft();

    boolean isFull();

    Boolean getCell(Integer column, Integer row);

    boolean setCell(Integer column, Integer row, Boolean value);

    Integer getUtility();

    Pair<Pair<Integer,Integer>,Integer> computeUtilityAndCellsLeftAndPlayer();

    Position getResult(Position position, Action action);

    boolean equals(Position e);

    boolean sameAs(Position e);

    boolean sameStateAs(Boolean[][] state);

    /**
     * test if player won the game thanks to a row
     * @param player
     * @param column
     * @param row
     * @return true if thanks to a move in cell (column, row), player won thanks to a row
     */
    boolean isWonHorizontally(boolean player, Integer column, Integer row);

    /**
     * test if player won the game thanks to a column
     * @param player
     * @param column
     * @param row
     * @return true if thanks to a move in cell (column, row), player won thanks to a column
     */
    boolean isWonVertically(boolean player, Integer column, Integer row);

    /**
     * test if player won the game thanks to the first bisector
     * @param player
     * @param column
     * @param row
     * @return true if thanks to a move in cell (column, row), player won thanks to the first bisector
     */
    boolean isWonOnFirstDiag(boolean player, Integer column, Integer row);

    /**
     * test if player won the game thanks to a second bisector
     * @param player
     * @param column
     * @param row
     * @return true if thanks to a move in cell (column, row), player won thanks to the second bisector
     */
    boolean isWonOnSecondDiag(boolean player, Integer column, Integer row);

    /**
     * test if player won
     * @param player
     * @param column
     * @param row
     * @return true if player won thanks to a piece in cell (column, row)
     */
    boolean isWon(boolean player, Integer column, Integer row);

    boolean isTerminal();
}
