package edu.uoc.trip.model.cells;

import edu.uoc.trip.model.utils.Coordinate;

/**
 * Interface that represents each Movable of the game.
 *
 * @author Antonio Fernández Salcedo
 * @version 1.0
 */
public interface Movable {
     void move(Coordinate destination);
}
