package battleship;

import java.util.Scanner;

enum Row {A, B, C, D, E, F, G, H, I, J}

enum Player {
    P1, P2;

    @Override
    public String toString() {
        return this == Player.P1 ? "Player 1" : "Player 2";
    }
}

public class Battleship {

    final Scanner sc = new Scanner(System.in);
    Field fieldsPlayer1;
    Field fieldsPlayer2;

    // constructor
    public Battleship(int size) {

        fieldsPlayer1 = new Field(size);
        fieldsPlayer2 = new Field(size);
    }

    public void initGame() {
        System.out.println("Player 1, place your ships on the game field");
        fieldsPlayer1.setInitialGameState();
        System.out.println("Press Enter and pass the move to another player");
        sc.nextLine();
        clearScreen();
        System.out.println("\f");
        System.out.println("Player 2, place your ships on the game field");
        fieldsPlayer2.setInitialGameState();
        System.out.println("Press Enter and pass the move to another player");
        sc.nextLine();
        clearScreen();
    }

    private void clearScreen() {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }

    public void performShooting(Player player, Field field) {
        System.out.printf("%s, it's your turn:\n", player);
        String coord = sc.nextLine();
        while (field.notValid(coord)) {
            coord = sc.nextLine();
        }
        field.shoot(coord);
    }

    public void display(Player player) {

        if (player == Player.P1) {
            fieldsPlayer2.displayFields(true);
            System.out.println("----------------------");
            fieldsPlayer1.displayFields(false);
        } else {
            fieldsPlayer1.displayFields(true);
            System.out.println("----------------------");
            fieldsPlayer2.displayFields(false);
        }
    }

    public void playGame() {
        System.out.println("The game starts!");
        Player currentPlayer = Player.P1;
        Field checkField = getOpponentField(currentPlayer);
        while (true) {
            display(currentPlayer);
            performShooting(currentPlayer, checkField);
            if (checkField.shipCount == 0) {
                break;
            }
            System.out.println("Press Enter and pass the move to another player");
            sc.nextLine();
            currentPlayer = togglePlayer(currentPlayer);
            checkField = getOpponentField(currentPlayer);
        }
    }

    private Field getOpponentField(Player currentPlayer) {
        return currentPlayer == Player.P1 ? fieldsPlayer2 : fieldsPlayer1;
    }

    private Player togglePlayer(Player currentPlayer) {
        return currentPlayer == Player.P1 ? Player.P2 : Player.P1;
    }
}
