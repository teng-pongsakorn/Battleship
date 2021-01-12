package battleship;


public enum Ships {
    Aircraft_Carrier(5),
    Battleship(4),
    Submarine(3),
    Cruiser(3),
    Destroyer(2);

    final private int numShip;

    Ships(int numShip) {
        this.numShip = numShip;
    }

    public int getNumShip() {
        return this.numShip;
    }

    @Override
    public String toString() {
        String[] names = this.name().split("_");
        StringBuilder sb = new StringBuilder();
        for (String x: names) {
            sb.append(x + " ");
        }
        return sb.toString();
    }
}