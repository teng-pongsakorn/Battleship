package battleship;

import java.util.Scanner;

public class Field {

    final Scanner sc = new Scanner(System.in);
    Symbol[][] fields;
    int shipCount = 0;
    int shipTypeCount = 0;

    public Field(int size) {
        initEmptyFields(size);
    }

    public int getShipCount() {
        return this.shipCount;
    }

    public void shoot(String coord) {
        int[] xy = coordToIndex(coord);
        int rowIndex = xy[0];
        int colIndex = xy[1];
        if (fields[rowIndex][colIndex].isShip()) {
            Symbol sunkShip = fields[rowIndex][colIndex];
            fields[rowIndex][colIndex] = new Symbol(Symbol.HIT);
            shipCount--;
//            displayFields(true);
            if (countShipByType(sunkShip) == 0) {
                shipTypeCount--;
                if (shipCount == 0) {
                    System.out.println("You sank the last ship. You won. Congratulations!");
                } else {
                    System.out.println("You sank a ship! Specify a new target:");
                }
            } else {
                System.out.println("You hit a ship!");
            }
        } else if (fields[rowIndex][colIndex].isHit() || fields[rowIndex][colIndex].isMiss()) {
//            displayFields(true);
            System.out.println("Already selected!");
        } else {
            fields[rowIndex][colIndex] = new Symbol(Symbol.MISS);
//            displayFields(true);
            System.out.println("You missed.");
        }
    }

    private int countShipByType(Symbol sunkShip) {
        int count = 0;
        for (int i = 0; i < fields.length; i++) {
            for (int j = 0; j < fields.length; j++) {
                if (sunkShip.getShipType().equals(fields[i][j].getShipType())) {
                    count++;
                }
            }
        }
        return count;
    }

    private void initEmptyFields(int size) {
        fields = new Symbol[size][size];
        for (int i = 0; i < fields.length; i++) {
            for (int j = 0; j < fields.length; j++) {
                fields[i][j] = new Symbol(Symbol.FOG);
            }
        }
    }

    public void setInitialGameState() {
        displayFields(true);
        for (Ships ship: Ships.values()) {
            fillShips(ship);
            displayFields(false);
        }
//        System.out.println("The game starts!");
//        displayFields(true);
    }

    public void displayFields(boolean fog) {
        // print header
        System.out.print("\n ");
        for (int i = 0; i < fields.length; i++) {
            System.out.print(" " + (i + 1));
        }
        System.out.println();

        // print row with values
        for (int row = 0; row < fields.length; row++) {
            System.out.print(Row.values()[row]);
            for (int col = 0; col < fields.length; col++) {
                char x = (fields[row][col].isShip()) && fog ? Symbol.FOG : fields[row][col].getSymbolValue();
                System.out.print(" " + x);
            }
            System.out.println();
        }
        System.out.println();
    }


    private void fillShips(Ships ship) {
        // validate user input for the given ship type
        int numShip = ship.getNumShip();
        System.out.println("Enter the coordinates of the " + ship + "(" + numShip + " cells):");
        while (true) {
            String[] coord = sc.nextLine().split(" ");
            if (!hasValidOrientation(coord) || !hasValidNumber(coord, numShip) || !locateSafety(coord)) {
                continue;
            } else {
                createShips(ship, coord);
                break;
            }
        }
        shipTypeCount++;
    }

    private void createShips(Ships ship, String[] coord) {
        int[] indices = getIndexFromCoord(coord);
        int x0 = indices[0];
        int y0 = indices[1];
        int x1 = indices[2];
        int y1 = indices[3];

        if (y0 == y1) {
            for (int i = x0; i <= x1; i++) {
                fields[i][y0] = new Symbol(Symbol.SHIP, ship);
                shipCount++;
            }
        } else {
            for (int i = y0; i <= y1; i++) {
                fields[x0][i] = new Symbol(Symbol.SHIP, ship);
                shipCount++;
            }
        }
    }

    private boolean locateSafety(String[] coord) {
        // check if not too close to other ships
        // check all 4 sides

        int[] indices = getIndexFromCoord(coord);
        int x0 = indices[0];
        int y0 = indices[1];
        int x1 = indices[2];
        int y1 = indices[3];


        if (x0 == x1) {
            // horizontal
            int northIndex = x0 > 0 ? x0 - 1 : -1;
            int southIndex = x0 < Row.values().length - 1 ? x0 + 1 : -1;
            int leftIndex = y0 > 0 ? y0 - 1 : -1;
            int rightIndex = y1 < fields.length - 1 ? y1 + 1 : -1;
            // check north area
            if (northIndex != -1) {
                for (int i = y0; i <= y1; i++) {
                    if (fields[northIndex][i].isShip()) {
                        System.out.println("Error! You placed it too close to another one. Try again:");
                        return false;
                    }
                }
            }
            // check south area
            if (southIndex != -1) {
                for (int i = y0; i <= y1; i++) {
                    if (fields[southIndex][i].isShip()) {
                        System.out.println("Error! You placed it too close to another one. Try again:");
                        return false;
                    }
                }
            }
            // check left
            if (leftIndex != -1 && fields[x0][leftIndex].isShip()) {
                System.out.println("Error! You placed it too close to another one. Try again:");
                return false;
            }
            // check right
            if (rightIndex != -1 && fields[x0][rightIndex].isShip()) {
                System.out.println("Error! You placed it too close to another one. Try again:");
                return false;
            }

        } else {
            // vertical
            int leftCol = y0 > 0 ? y0 - 1 : -1;
            int rightCol = y0 < fields.length - 1 ? y0 + 1 : -1;
            int northIndex = x0 > 0 ? x0 - 1 : -1;
            int southIndex = x1 < Row.values().length -1 ? x1 + 1 : -1;

            // check north
            if (northIndex != -1 && fields[northIndex][y0].isShip()) {
                System.out.println("Error! You placed it too close to another one. Try again:");
                return false;
            }
            // check south
            if (southIndex != -1 && fields[southIndex][y0].isShip()) {
                System.out.println("Error! You placed it too close to another one. Try again:");
                return false;
            }
            // check left
            if (leftCol != -1) {
                for (int i = x0; i <= x1; i++) {
                    if (fields[i][leftCol].isShip()) {
                        System.out.println("Error! You placed it too close to another one. Try again:");
                        return false;
                    }
                }
            }
            // check right
            if (rightCol != -1) {
                for (int i = x0; i <= x1; i++) {
                    if (fields[i][rightCol].isShip()) {
                        System.out.println("Error! You placed it too close to another one. Try again:");
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean hasValidNumber(String[] coord, int numShip) {
        // check if number of ships is valid - match the ship type
        int[] indices = getIndexFromCoord(coord);
        int x0 = indices[0];
        int y0 = indices[1];
        int x1 = indices[2];
        int y1 = indices[3];

        if (x0 == x1) {
            // horizontal ships
            if (Math.abs(y0 - y1) + 1 != numShip) {
                System.out.println("Error! Wrong length of the Submarine! Try again:");
                return false;
            }
            return true;
        } else {
            // vertical ships
            if (Math.abs(x0 - x1) + 1 != numShip) {
                System.out.println("Error! Wrong length of the Submarine! Try again:");
                return false;
            }
            return true;
        }
    }

    private boolean hasValidOrientation(String[] coord) {
        // check if location of ships is valid - only vertical or horizontal orientation
        int[] indices = getIndexFromCoord(coord);
        int x0 = indices[0];
        int y0 = indices[1];
        int x1 = indices[2];
        int y1 = indices[3];

        boolean result = x0 == x1 || y0 == y1;
        if (!result) {
            System.out.println("Error! Wrong ship location! Try again:");
        }
        return result;
    }

    private int[] getIndexFromCoord(String[] coord) {
        // result int[] {rowA, colA, rowB, colB} ~ (x0, y0, x1, y1)
        // where x0 <= x1 and y0 <= y1
        String rowA = coord[0].substring(0, 1);
        String rowB = coord[1].substring(0, 1);
        int colA = Integer.parseInt(coord[0].substring(1, coord[0].length())) - 1;
        int colB = Integer.parseInt(coord[1].substring(1, coord[1].length())) - 1;
        int indexRowA = Row.valueOf(rowA).ordinal();
        int indexRowB = Row.valueOf(rowB).ordinal();
        int x0 = Math.min(indexRowA, indexRowB);
        int x1 = Math.max(indexRowA, indexRowB);
        int y0 = Math.min(colA, colB);
        int y1 = Math.max(colA, colB);
        return new int[] {x0, y0, x1, y1};
    }

    public boolean isValidCoord(String coord) {
        try {
            int[] xy = coordToIndex(coord);
            if (xy[0] < 0 || xy[0] >= fields.length || xy[1] < 0 || xy[1] >= fields.length) {
                throw new RuntimeException();
            }
            return false;
        } catch (Exception e) {
            System.out.println("\nError! You entered the wrong coordinates! Try again:\n");
            return true;
        }
    }

    private int[] coordToIndex(String coord) {
        // return int[] {rowIndex, colIndex} from the given coordinate string
        int[] result = new int[2];
        String rowChar = coord.substring(0, 1);
        String colChar = coord.substring(1, coord.length());
        result[0] = Row.valueOf(rowChar).ordinal();
        result[1] = Integer.parseInt(colChar) - 1;
        return result;
    }

    public boolean notValid(String coord) {
        try {
            int[] xy = coordToIndex(coord);
            if (xy[0] < 0 || xy[0] >= fields.length || xy[1] < 0 || xy[1] >= fields.length) {
                throw new RuntimeException();
            }
            return false;
        } catch (Exception e) {
            System.out.println("\nError! You entered the wrong coordinates! Try again:\n");
            return true;
        }
    }
}
