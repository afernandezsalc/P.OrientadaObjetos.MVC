package edu.uoc.trip.model.cells;

import edu.uoc.trip.model.levels.Direction;

import java.util.EnumSet;

/**
 * Enumerated class that represents each CellType of the game.
 *
 * @author Antonio Fernández Salcedo
 * @version 1.0
 */
public  enum  CellType {

    START('S', '^', "start.png", new boolean[]{true, false, false, false}) {
        public CellType next() {
            return null;
        }
    },

    FINISH('F', 'v', "finish.png", new boolean[]{false, false, true, false}) {
        public CellType next() {
            return null;
        }
    },

    MOUNTAINS('M', 'M', "mountains.png", new boolean[]{false, false, false, false}) {
        public CellType next() {
            return null;
        }
    },
    RIVER('~', '~', "river.png", new boolean[]{false, false, false, false}) {
        public CellType next() {
            return null;
        }
    },
    VERTICAL('V', '\u2551', "road_vertical.png", new boolean[]{true, false, true, false}) {
        public CellType next() {
            return HORIZONTAL;
        }
    },
    HORIZONTAL('H', '\u2550', "road_horizontal.png", new boolean[]{false, true, false, true}) {
        public CellType next() {
            return VERTICAL;
        }
    },
    BOTTOM_RIGHT('r', '\u2554', "road_bottom_right.png", new boolean[]{false, true, true, false}) {
        public CellType next() {
            return BOTTOM_LEFT;
        }
    },
    BOTTOM_LEFT('l', '\u2557', "road_bottom_left.png", new boolean[]{false, false, true, true}) {
        public CellType next() {
            return TOP_LEFT;
        }
    },
    TOP_RIGHT('R', '\u255A', "road_top_right.png", new boolean[]{true, true, false, false}) {
        public CellType next() {
            return BOTTOM_RIGHT;
        }
    },
    TOP_LEFT('L', '\u255D', "road_top_left.png", new boolean[]{true, false, false, true}) {
        public CellType next() {
            return TOP_RIGHT;
        }
    },
    FREE('·', '\u00b7', "free.png", new boolean[]{false, false, false, false}) {
        public CellType next() {
            return null;
        }
    },
    ROTATABLE_VERTICAL('G', '\u2503', "road_rotatable_vertical.png", new boolean[]{true, false, true, false}) {
        public CellType next() {
            return ROTATABLE_HORIZONTAL;
        }
    },
    ROTATABLE_HORIZONTAL('g', '\u2501', "road_rotatable_horizontal.png", new boolean[]{false, true, false, true}) {
        public CellType next() {
            return ROTATABLE_VERTICAL;
        }
    };

    private char fileSymbol;
    private char unicodeRepresentation;
    private String imageSrc;
    private boolean[] connections;

    CellType(char fileSymbol, char unicodeRepresentation, String imageSrc, boolean[] connections) {

        setFileSymbol(fileSymbol);
        setUnicodeRepresentation(unicodeRepresentation);
        setImageSrc(imageSrc);
        setConnections(connections);
    }

    public char getFileSymbol() {
        return fileSymbol;
    }

    private void setFileSymbol(char fileSymbol) {
        this.fileSymbol = fileSymbol;
    }

    public char getUnicodeRepresentation() {
        return unicodeRepresentation;
    }

    private void setUnicodeRepresentation(char unicodeRepresentation) {
        this.unicodeRepresentation = unicodeRepresentation;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    private void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    private void setConnections(boolean[] connections) {
        this.connections = connections;
    }

    public EnumSet<Direction> getAvailableConnections() {
            EnumSet<Direction> prueba = EnumSet.noneOf(Direction.class);

            if (  (this  ==  START ) && (connections[0])) {
                prueba.add(Direction.UP);
                return prueba;
            }

            if ( (this ==  FINISH ) && (connections[2])){
             prueba.add(Direction.DOWN);
                return prueba;
            }

            if  (this ==  MOUNTAINS ) {
                return prueba;
            }

            if  (this ==  RIVER ) {
                return prueba;
            }

            if (  (this  ==  VERTICAL ) && ( (connections[0]) ||(connections[2])) ) {
                prueba.add(Direction.UP);
                prueba.add(Direction.DOWN);
                return prueba;
            }

            if ( (this  ==  HORIZONTAL ) && ( (connections[1]) ||(connections[3])) )  {
                prueba.add(Direction.LEFT);
                prueba.add(Direction.RIGHT);
                return prueba;
            }

            if (  (this  ==  BOTTOM_LEFT ) && ( (connections[2]) ||(connections[3])) )  {
                prueba.add(Direction.LEFT);
                prueba.add(Direction.DOWN);
                return prueba;
            }

            if ( (this  ==  TOP_RIGHT ) && ( (connections[0]) ||(connections[1])) )  {
                prueba.add(Direction.UP);
                prueba.add(Direction.RIGHT);
                return prueba;
            }

            if (  (this  ==  TOP_LEFT ) && ( (connections[0]) ||(connections[3])) )  {
                prueba.add(Direction.UP);
                prueba.add(Direction.LEFT);
                return prueba;
            }

            if  (this ==  FREE ) {
                return prueba;
            }

            if (  (this  ==  ROTATABLE_VERTICAL ) && ( (connections[0]) ||(connections[3])) ) {
                prueba.add(Direction.UP);
                prueba.add(Direction.DOWN);
                return prueba;
            }

            if (  (this  ==  ROTATABLE_HORIZONTAL ) && ( (connections[1]) ||(connections[3])) ) {
                prueba.add(Direction.LEFT);
                prueba.add(Direction.RIGHT);
                return prueba;
            }
            return prueba;
        }

    public static CellType map2CellType(char fileSymbol){

        if (fileSymbol == 'S' ) {
            return CellType.START;
        }
        if (fileSymbol == 'F' ) {
            return CellType.FINISH;
        }
        if (fileSymbol == 'M' ) {
            return CellType.MOUNTAINS;
        }
        if (fileSymbol == '~' ) {
            return CellType.RIVER;
        }
        if (fileSymbol == 'V' ) {
            return CellType.VERTICAL;
        }
        if (fileSymbol == 'H' ) {
            return CellType.HORIZONTAL;
        }
        if (fileSymbol == 'r' ) {
            return CellType.BOTTOM_RIGHT;
        }
        if (fileSymbol == 'l' ) {
            return CellType.BOTTOM_LEFT;
        }
        if (fileSymbol == 'R' ) {
            return CellType.TOP_RIGHT;
        }
        if (fileSymbol == 'L' ) {
            return CellType.TOP_LEFT;
        }
        if (fileSymbol == '·' ) {
            return CellType.FREE;
        }
        if (fileSymbol == 'G' ) {
            return CellType.ROTATABLE_VERTICAL;
        }
        if (fileSymbol == 'g' ) {
            return CellType.ROTATABLE_HORIZONTAL;
        }
        return null;
    }

    //Método abstracto
    public abstract CellType next();

}
