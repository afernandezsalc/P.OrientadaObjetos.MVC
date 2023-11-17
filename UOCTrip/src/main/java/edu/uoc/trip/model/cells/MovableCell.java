package edu.uoc.trip.model.cells;

import edu.uoc.trip.model.utils.Coordinate;

/**
 * Class that represents each MovableCell of the game.
 *
 * @author Antonio Fernández Salcedo
 * @version 1.0
 */
public class MovableCell extends Cell implements Movable{

    public MovableCell (int row, int column, CellType type){
        super(row, column, type);

        setCoordinate(row, column);
    }

    public boolean isMovable(){
             return true;
}

    public boolean isRotatable(){
        return false;
    }

    @Override
    public void move(Coordinate destination) {
        setCoordinate(destination.getRow(), destination.getColumn());
    }
}
