package battleship;

import java.util.ArrayList;
import java.util.Scanner;

public class Player {

    private final Field field;
    private String name;

    public void showField() {
        field.showField();
    }

    public void showFieldFogged() {
        field.showFieldFogged();
    }

    public Player(String name) {
        this.field = new Field();
        this.field.showField();
        this.name = name;
    }

    public boolean isAlive() {
        return this.field.isAlive();
    }

    public void allocateShips() {
        System.out.println(this.name + ", place your ships on the game field");
        field.newAicraftCarrier();
        field.newBattleship();
        field.newSubmarine();
        field.newCruiser();
        field.newDestroyer();
    }

    public void hit(Player otherPlayer) {
        otherPlayer.showFieldFogged();
        System.out.println("---------------------");
        this.showField();
        System.out.println(this.name + ", it's your turn:");
        Scanner scanner = new Scanner(System.in);
        String cell = scanner.nextLine();
        int row = cell.charAt(0) - 65;
        int col = Integer.parseInt(cell.substring(1)) - 1;
        while (row >= field.getFieldLength() || col >= field.getFieldLength()) {
            System.out.println("Error! You entered the wrong coordinates! Try again:");
            cell = scanner.nextLine();
            row = cell.charAt(0) - 65;
            col = Integer.parseInt(cell.substring(1)) - 1;
        }

        boolean isSunk = false;
        outer:
        for (Ship s : otherPlayer.field.ships) {
            for (int[] cells : s.cells) {
                if (cells[0] == row && cells[1] == col) {
                    s.cells.remove(cells);
                    if (s.cells.isEmpty()) {
                        isSunk = true;
                        otherPlayer.field.ships.remove(s);
                        break outer;
                    }
                    break outer;
                }
            }
        }

        if (otherPlayer.field.getCell(row, col) != Field.occupied && otherPlayer.field.getCell(row, col) != Field.hit) {
            otherPlayer.field.setMissed(row, col);
            otherPlayer.showFieldFogged();
            System.out.println("You missed!");
        }  else if (otherPlayer.field.ships.size() == 0) {
            otherPlayer.field.setHit(row, col);
            otherPlayer.showFieldFogged();
            System.out.println("You sank the last ship. You won. Congratulations!");
        } else if (isSunk) {
                otherPlayer.field.setHit(row, col);
                otherPlayer.showFieldFogged();
                System.out.println("You sank a ship!");
        } else if (otherPlayer.field.getCell(row, col) == Field.hit) {
            otherPlayer.showFieldFogged();
            System.out.println("You hit a ship!");
        } else {
            otherPlayer.field.setHit(row, col);
            otherPlayer.showFieldFogged();
            System.out.println("You hit a ship!");
        }
    }

}
