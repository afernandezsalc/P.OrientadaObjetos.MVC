package edu.uoc.trip.model.cells;

/**
 * Class that represents each RotatableCell of the game.
 *
 * @author Antonio Fernández Salcedo
 * @version 1.0
 */
public class RotatableCell extends Cell implements Rotatable{

    public RotatableCell (int row, int column, CellType type){
        super(row, column, type);
        setType(type);
    }
    public boolean isMovable(){
        return false;
    }

    public boolean isRotatable(){
        return true;
    }

    @Override
    public  void rotate() {
        setType(getType().next());
    }
}
