package edu.uoc.trip.model.utils;

import java.util.Objects;

/**
 * Class that represents each Coordinate of the game.
 *
 * @author Antonio Fernández Salcedo
 * @version 1.0
 */
public class Coordinate {
    private int row;
    private int column;

    public Coordinate(int row, int column){
        setColumn(column);
        setRow(row);
    }
    public int getRow(){
        return row;
    }
    private void setRow(int row){
       // Coordinate.this.row = row;
        this.row = row;
    }
    public int getColumn(){
        return column;
    }
    private void setColumn(int column){
       // Coordinate.this.column = column;
        this.column = column;
    }

    @Override
    public boolean equals(Object  obj){
        //Una coordenada es null
        if ( obj == null){
            return false;
        }
        // Se pasa como argumento un objeto que no sea del tipo Coordinate
        if ( getClass() != obj.getClass()){
            return false;
        }
        // Coincidencia de las filas y columnas
        if ( this.getRow() == ((Coordinate) obj).getRow()
                && this.getColumn() == ((Coordinate) obj).getColumn() ) {
            return true;
        }
        //No coincidencia de las filas y/o columnas
        return false;
    }

    //La implementación del método hashCode se debe realizar:
    // 1- Almacenar un valor contante distinto de = en una variable int.
    // 2- Por cada campo usado en el método equals se debe obtener un hash code(int)
    // 3- Combinar los hash code obtenidos.
    // Implementar este método en cada clase de una aplicación es repetitivo, laborioso
    // y propenso a errores, para hacer más sencilla la implementación existe el método Cbjects.hash
    // Cada vez que se invoca en el mismo objeto más de una vez durante una ejecución de una aplicación
    // Java, el método hashCode debe devolver el mismo entero, siempre que no se modifique la información
    // utilizada en comparaciones iguales en el objeto.
    @Override
    public int hashCode(){
        return Objects.hash(row, column);
    }

    @Override
    public String toString() {
        String string =   "(" + getRow() + "," +  getColumn() + ")" ;
        return string;
    }


}
