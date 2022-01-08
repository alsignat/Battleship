package battleship;

import java.util.Scanner;

public class Game {

    private Player player1;
    private Player player2;
    private Player player;
    private boolean running = true;

    public Game() {
        this.player1 = new Player("Player 1");
        this.player2 = new Player("Player 2");
        player = player1;
    }

    public boolean isRunning() {
        return this.running;
    }

    public void changePlayer() {
        System.out.println("Press Enter and pass the move to another player");
        player = player == player1 ? player2 : player1;
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

    public Player getOtherPlayer() {
        return player == player1 ? player2 : player1;
    }

    public void play() {
        player.allocateShips();
        changePlayer();
        player.allocateShips();
        changePlayer();

        System.out.println("The game starts!");
        while (running) {
            player.hit(getOtherPlayer());
            running = player1.isAlive() && player2.isAlive();
            changePlayer();
        }
    }
}
