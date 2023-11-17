package edu.uoc.trip.model.levels;

import edu.uoc.trip.model.cells.*;
import edu.uoc.trip.model.cells.CellType;
import edu.uoc.trip.model.cells.MovableCell;
import edu.uoc.trip.model.utils.Coordinate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Class that represents each level of the game.
 *
 * @author David García-Solórzano
 * @version 1.0
 */
public  class  Level {

    /**
     * Size of the board, i.e. size x size.
     */
    private int size;

    /**
     * Difficulty of the level
     */
    private LevelDifficulty difficulty;

    /**
     * Representation of the board.
     */
    private Cell[][] board;

    /**
     * Number of moves that the player has made so far.
     */
    private int numMoves = 0;

    /**
     * Minimum value that must be assigned to the attribute "size".
     */
    private static final int MINIMUM_BOARD_SIZE = 3;

    /**
     * Constructor
     *
     * @param fileName Name of the file that contains level's data.
     * @throws LevelException When there is any error while parsing the file.
     */
    public Level(String fileName) throws LevelException {
        parse(fileName);
        setNumMoves(0);
    }

    public int getNumMoves() {
        return numMoves;
    }

    private void setNumMoves(int numMoves) {
        this.numMoves = numMoves;
    }

    /**
     * Parses/Reads level's data from the given file.<br/>
     * It also checks which the board's requirements are met.
     *
     * @param fileName Name of the file that contains level's data.
     * @throws LevelException When there is any error while parsing the file
     *                        or some board's requirement is not satisfied.
     */
    private void parse(String fileName) throws LevelException {
        boolean isStarting = false;
        boolean isFinish = false;
        String line;

        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = Objects.requireNonNull(classLoader.getResourceAsStream(fileName));

        try (InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {

            line = getFirstNonEmptyLine(reader);

            if (line != null) {
                setSize(Integer.parseInt(line));
            }

            line = getFirstNonEmptyLine(reader);

            if (line != null) {
                setDifficulty(LevelDifficulty.valueOf(line));
            }

            board = new Cell[getSize()][getSize()];

            for (int row = 0; row < getSize(); row++) {
                char[] rowChar = Objects.requireNonNull(getFirstNonEmptyLine(reader)).toCharArray();
                for (int column = 0; column < getSize(); column++) {
                    board[row][column] = CellFactory.getCellInstance(row, column,
                            Objects.requireNonNull(CellType.map2CellType(rowChar[column])));
                }
            }

        } catch (IllegalArgumentException | IOException e) {
            throw new LevelException(LevelException.ERROR_PARSING_LEVEL_FILE);
        }

        //Check if there is one starting cell, one finish cell and, at least, any other type of cell.
        for (var j = 0; j < getSize(); j++) {

            if (getCell(new Coordinate(getSize() - 1, j)).getType() == CellType.START) {
                isStarting = true;
            }

            if (getCell(new Coordinate(0, j)).getType() == CellType.FINISH) {
                isFinish = true;
            }
        }

        //Checks if there are more than one starting cell
        if (Stream.of(board).flatMap(Arrays::stream).filter(x -> x.getType() == CellType.START).count() > 1) {
            throw new LevelException(LevelException.ERROR_PARSING_LEVEL_FILE);
        }

        //Checks if there are more than one finish cell
        if (Stream.of(board).flatMap(Arrays::stream).filter(x -> x.getType() == CellType.FINISH).count() > 1) {
            throw new LevelException(LevelException.ERROR_PARSING_LEVEL_FILE);
        }

        if (!isStarting) {
            throw new LevelException(LevelException.ERROR_NO_STARTING);
        }

        if (!isFinish) {
            throw new LevelException(LevelException.ERROR_NO_FINISH);
        }

        //Checks if there is one road (i.e. movable or rotatable cell) at least.
        if (Stream.of(board).flatMap(Arrays::stream).noneMatch(x -> x.isMovable() || x.isRotatable())) {
            throw new LevelException(LevelException.ERROR_NO_ROAD);
        }
    }

    public Cell getCell(Coordinate coord) throws LevelException {
        if (!validatePosition(coord)) {
            throw new LevelException(LevelException.ERROR_COORDINATE);
        } else {
            return board[coord.getRow()][coord.getColumn()];
        }
    }

    private void setCell(Coordinate coord, Cell cell) throws LevelException {

        if ((!validatePosition(coord)) || (cell == null)) {
            throw new LevelException(LevelException.ERROR_COORDINATE);
        } else {
            board[coord.getRow()][coord.getColumn()] = cell;
        }
    }

    public int getSize() {
        return size;
    }

    private void setSize(int size) throws LevelException {
        if (size < MINIMUM_BOARD_SIZE) {
            throw new LevelException(LevelException.ERROR_BOARD_SIZE);
        } else {
            this.size = size;
        }
    }

    public LevelDifficulty getDifficulty() {
        return difficulty;
    }

    private void setDifficulty(LevelDifficulty difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * This a helper method for {@link #parse(String fileName)} which returns
     * the first non-empty and non-comment line from the reader.
     *
     * @param br BufferedReader object to read from.
     * @return First line that is a parsable line, or {@code null} there are no lines to read.
     * @throws IOException if the reader fails to read a line.
     */
    private String getFirstNonEmptyLine(final BufferedReader br) throws IOException {
        do {

            String s = br.readLine();

            if (s == null) {
                return null;
            }
            if (s.isBlank() || s.startsWith("#")) {
                continue;
            }

            return s;
        } while (true);
    }

    private boolean validatePosition(Coordinate coord) {
        return ((0 <= coord.getRow()) && (coord.getRow() < size))
                && ((0 <= coord.getColumn()) && (coord.getColumn() < size));
    }

    public boolean isSolved() {
        //en columna quedará asignada la columna donde esta la pieza START
        int columna = 0;
        int fila = size-1;
        while ((board[fila][columna]).getType() != CellType.START) {
            System.out.print((board[fila][columna]).getType() + " " + columna + fila + " ");
            columna++;

        }

        int anteriorfila = fila;
        int anteriorcolun = columna;
        int actualco = columna;
        int n = 0;

        System.out.print(System.lineSeparator() + "star: " + (board[fila][columna]).getType() +
                " " + columna + fila + System.lineSeparator());

        //confirmamos que la casilla que esta por encima de START tiene entrada DOWNN, en caso contrario no tiene recorrido
        fila--;
        if (!(Objects.equals((board[fila][columna]).getType().getAvailableConnections(), EnumSet.of(Direction.DOWN, Direction.LEFT)))
                && !(Objects.equals((board[fila][columna]).getType().getAvailableConnections(),
                EnumSet.of(Direction.DOWN, Direction.RIGHT)))
                && !(Objects.equals((board[fila][columna]).getType().getAvailableConnections(),
                EnumSet.of(Direction.DOWN, Direction.UP)))) {
            System.out.print(System.lineSeparator() + "no valido: " + (board[fila][columna]).getType().getAvailableConnections() +
                    " " + columna + fila + EnumSet.of(Direction.DOWN, Direction.LEFT) + System.lineSeparator());
            return false;
        }

        System.out.print(System.lineSeparator() + "SI valido: " + (board[fila + 1][columna]).getType() +
                " " + (columna + 1) + fila + System.lineSeparator());

        System.out.print(System.lineSeparator() + "SI valido siguiente: " + (board[fila][columna]).getType() +
                " " + columna + fila + System.lineSeparator());

        while ( (((board[fila][columna]).getType() != CellType.FINISH))
                && ((fila>0) && (columna >0))
                && (n<5)
                &&((board[fila][columna]).getType() != CellType.FREE)
                &&((board[fila][columna]).getType() != CellType.MOUNTAINS)
                &&((board[fila][columna]).getType() != CellType.RIVER)
                &&  ((board[fila][columna]).getType() != CellType.START)){

            //caso right
            if ((Objects.equals((board[fila][columna]).getType().getAvailableConnections(), EnumSet.of(Direction.RIGHT, Direction.LEFT))) ||
                    (Objects.equals((board[fila][columna]).getType().getAvailableConnections(),
                            EnumSet.of(Direction.DOWN, Direction.RIGHT))) ||
                    (Objects.equals((board[fila][columna]).getType().getAvailableConnections(),
                            EnumSet.of(Direction.RIGHT, Direction.UP)))) {
                if  ( ((board[fila][anteriorcolun]).getType().getAvailableConnections()) != (EnumSet.of(Direction.RIGHT ))) {
                //if ((anteriorcolun != columna) ){
                    anteriorcolun++;
                    columna++;
                    actualco++;
                    System.out.print(System.lineSeparator() + "caso right " + (board[fila][columna]).getType() +
                            " " + columna + fila + System.lineSeparator());
                }
            }
            if ( ((board[fila][columna]).getType() == CellType.FINISH)) {
                return true;
            }

            //caso up
            if ((Objects.equals((board[fila][columna]).getType().getAvailableConnections(), EnumSet.of(Direction.UP, Direction.LEFT))) ||
                    (Objects.equals((board[fila][columna]).getType().getAvailableConnections(),
                            EnumSet.of(Direction.UP, Direction.RIGHT))) ||
                    (Objects.equals((board[fila][columna]).getType().getAvailableConnections(),
                            EnumSet.of(Direction.DOWN, Direction.UP)))) {
                if  ( ((board[anteriorfila][columna]).getType().getAvailableConnections()) != (EnumSet.of(Direction.UP ))) {
                //if (fila != anteriorfila) {
                    fila++;
                    anteriorfila++;
                    System.out.print(System.lineSeparator() + "caso up " + (board[fila][columna]).getType() +
                            " " + columna + fila + System.lineSeparator());
                }
            }
            if ( ((board[fila][columna]).getType() == CellType.FINISH)) {
                return true;
            }

            //caso down
            if ((Objects.equals((board[fila][columna]).getType().getAvailableConnections(), EnumSet.of(Direction.DOWN, Direction.LEFT))) ||
                    (Objects.equals((board[fila][columna]).getType().getAvailableConnections(),
                            EnumSet.of(Direction.DOWN, Direction.RIGHT))) ||
                    (Objects.equals((board[fila][columna]).getType().getAvailableConnections(),
                            EnumSet.of(Direction.DOWN, Direction.UP)))) {
                if  ( ((board[anteriorfila][columna]).getType().getAvailableConnections()) != (EnumSet.of(Direction.DOWN ))) {
                //if (fila != anteriorfila) {
                    fila--;
                    anteriorfila--;
                    System.out.print(System.lineSeparator() + "caso down " + (board[fila][columna]).getType() +
                            " " + columna + fila + System.lineSeparator());
                }
            }
            if ( ((board[fila][columna]).getType() == CellType.FINISH)) {
                return true;
            }

            //caso left
            if ((Objects.equals((board[fila][columna]).getType().getAvailableConnections(), EnumSet.of(Direction.DOWN, Direction.LEFT))) ||
                    (Objects.equals((board[fila][columna]).getType().getAvailableConnections(),
                            EnumSet.of(Direction.LEFT, Direction.RIGHT))) ||
                    (Objects.equals((board[fila][columna]).getType().getAvailableConnections(),
                            EnumSet.of(Direction.LEFT, Direction.UP)))) {
                if  ( ((board[fila][anteriorcolun]).getType().getAvailableConnections()) != (EnumSet.of(Direction.LEFT ))) {
               // if (anteriorcolun != columna) {
                    anteriorcolun--;
                    columna--;
                    actualco--;
                    System.out.print(System.lineSeparator() + "caso left " + (board[fila][columna]).getType() +
                            " " + columna + fila + System.lineSeparator());
                }
            }
            if ( ((board[fila][columna]).getType() == CellType.FINISH)) {
                return true;
            }


            System.out.print(System.lineSeparator() + "caso fallo " + (board[fila][columna]).getType() +
                    " " + columna + fila + System.lineSeparator());
            n++;
        }
        return true;
    }

    public void swapCells(Coordinate firstCoord, Coordinate secondCoord) throws LevelException {

        // si las piezas en la posición del board son móviles
        if (((board[firstCoord.getRow()][firstCoord.getColumn()]).isMovable()) &&
                ((board[secondCoord.getRow()][secondCoord.getColumn()]).isMovable())) {

            // Creo 2 objetos temporales para guardar el objeto first y secund del tablero.
            MovableCell a = (MovableCell) (board[firstCoord.getRow()][firstCoord.getColumn()]);
            MovableCell b = (MovableCell) (board[secondCoord.getRow()][secondCoord.getColumn()]);

            //intercambio de objetos
            a.move(secondCoord);
            b.move(firstCoord);

            setCell(firstCoord, b);
            setCell(secondCoord, a);

            numMoves++;

        } else {
            throw new LevelException(LevelException.ERROR_NO_MOVABLE_CELL);
        }
    }

    public void rotateCell(Coordinate coord) throws LevelException {

        // si la pieza en la posición del board es rotable
        if ((board[coord.getRow()][coord.getColumn()]).isRotatable()) {

            RotatableCell c = ( RotatableCell) (board[coord.getRow()][coord.getColumn()]);
            c.rotate();

            numMoves++;

        } else {
            throw new LevelException(LevelException.ERROR_NO_ROTATABLE_CELL);
        }
    }

    @Override
    public String toString() {

            // Recorrido dinámico del board

            String string = System.lineSeparator();
            int lado = getSize();
            //Primera línea de las columnas, desde 1 hasta size (en este caso 1234)
            for (int x = 0; x < lado; x++) {
                string = string + (x+1);
                //string += x+1;
            }
            string += System.lineSeparator();
            //Recorrido de filas. Cada vez que incremento 1 fila, aumenta el 97, en el caso de la fila b pasa a ser el 98
            for (int i = 0; i < lado; i++) {
                //códico Ascii del char 'a', es 97
                int prueba = (97 +i);
                char c = (char)prueba;
                string += c + "|";
                //Recorrido de columnas
                for (int j = 0; j < lado; j++) {
                    //string = string + board[i][j].getType().getUnicodeRepresentation();
                    string += board[i][j].getType().getUnicodeRepresentation();
                }
                string += System.lineSeparator();
            }
            return string;
        }
}
