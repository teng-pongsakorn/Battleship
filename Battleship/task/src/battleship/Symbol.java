package battleship;

class Symbol {

    final static char FOG = '~';
    final static char SHIP = 'O';
    final static char HIT = 'X';
    final static char MISS = 'M';

    private final char symbolValue;
    private final Ships shipType;

    public Symbol(char value) {
        this.symbolValue = value;
        this.shipType = null;
    }

    public Symbol(char value, Ships shipType) {
        this.symbolValue = value;
        this.shipType = shipType;
    }

    public char getSymbolValue() {
        return this.symbolValue;
    }

    public Ships getShipType() {
        return this.shipType;
    }

    @Override
    public String toString() {
        return "" + this.getSymbolValue();
    }

    public boolean isShip() {
        return this.symbolValue == SHIP;
    }

    public boolean isMiss() {
        return this.symbolValue == MISS;
    }

    public boolean isHit() {
        return this.symbolValue == HIT;
    }
}