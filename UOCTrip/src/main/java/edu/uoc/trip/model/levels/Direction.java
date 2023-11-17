package edu.uoc.trip.model.levels;

/**
 * Enumerated class that represents each Direction of the game.
 *
 * @author Antonio Fernández Salcedo
 * @version 1.0
 */
public enum Direction {
    UP(-1,0, 2),
    RIGHT(0, 1, 3),
    DOWN(1, 0,0),
    LEFT(0, -1,1);

    private final int dRow;
    private final int dColumn;
    private final int opposite;

     Direction(int dRow, int dColumn, int opposite) {

        this.dRow = dRow;
        this.dColumn = dColumn;
        this.opposite = opposite;
    }

    public static Direction getValueByIndex(int index) {
        if (index == 0) {
            return Direction.UP;
        }
        if (index == 1) {
            return Direction.RIGHT;
        }
        if (index == 2) {
            return Direction.DOWN;
        }
        return Direction.LEFT;
    }

    public int getDRow() {
        return dRow;
    }

    public int getDColumn() {
        return dColumn;
    }

    public Direction getOpposite() {
        if (opposite == 1) {
            return RIGHT;
        }
        if  (opposite == 2) {
            return DOWN;
        }
        if (opposite == 3) {
            return LEFT;
        } else
            return UP;
    }
}
