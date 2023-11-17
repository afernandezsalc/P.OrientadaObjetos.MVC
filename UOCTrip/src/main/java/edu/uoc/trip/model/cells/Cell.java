package edu.uoc.trip.model.cells;

import edu.uoc.trip.model.utils.Coordinate;

/**
 * Class that represents each Cell of the game.
 *
 * @author Antonio Fernández Salcedo
 * @version 1.0
 */
public class Cell {
    private CellType type;
    private Coordinate coordinate;

    public Cell(int row, int column, CellType type) {

        setType(type);
        setCoordinate(row, column);
    }

    public CellType getType() {
        return type;
    }

    protected void setType(CellType type) {
        this.type = type;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    protected void setCoordinate(int row, int column) {
        this.coordinate = new Coordinate(row, column);
    }

    public boolean isMovable() {
        return false;
    }

    public boolean isRotatable(){
        return false;
    }

    @Override
    public String toString() {
        //String string = (String.valueOf('\u00b7'));
        String string = String.valueOf(CellType.FREE.getUnicodeRepresentation());
        return string;
    }

}
