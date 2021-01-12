package battleship;


public class Main {

    final static int size = 10;

    public static void main(String[] args) {
        Battleship battleship = new Battleship(size);
        battleship.initGame();
        battleship.playGame();
    }
}
