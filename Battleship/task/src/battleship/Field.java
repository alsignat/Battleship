package battleship;

import java.util.ArrayList;
import java.util.Scanner;

public class Field {

    private char[][] field;
    private float shipCells = 0.0f;
    private Scanner scanner = new Scanner(System.in);
    public static final char occupied = 'O';
    public static final char hit = 'X';
    public static final char miss = 'M';
    public ArrayList<Ship> ships = new ArrayList<>(5);

    public boolean isAlive() {
        return !ships.isEmpty();
    }

    public int getShipCells() {
        return (int) shipCells;
    }

    public int getFieldLength() {
        return field.length;
    }

    public int getCell(int row, int col) {
        return field[row][col];
    }

    public void setHit(int row, int col) {
        field[row][col] = hit;
        shipCells--;
    }

    public void setMissed(int row, int col) {
        field[row][col] = miss;
    }

    public Field(int fieldSize) {
        this.field = new char[fieldSize][fieldSize];
        for (int row = 0; row < fieldSize; row++) {
            for (int col = 0; col < fieldSize; col++) {
                field[row][col] = '~';
            }
        }

    }

    public Field() {
        this(10);
    }

    public void showField() {
        System.out.print(System.lineSeparator());
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        for (int row = 0; row < this.field.length; row++) {
            System.out.print((char) ('A' + row));
            System.out.print(" ");
            for (int cell = 0; cell < this.field.length; cell++) {
                System.out.print(this.field[row][cell]);
                System.out.print(cell == this.field.length - 1 ? System.lineSeparator() : " ");
            }
        }
        System.out.print(System.lineSeparator());
    }

    public void showFieldFogged() {
        System.out.print(System.lineSeparator());
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        for (int row = 0; row < this.field.length; row++) {
            System.out.print((char) ('A' + row));
            System.out.print(" ");
            for (int cell = 0; cell < this.field.length; cell++) {
                if (this.field[row][cell] == hit || this.field[row][cell] == miss) {
                    System.out.print(this.field[row][cell]);
                } else {
                    System.out.print('~');
                }
                System.out.print(cell == this.field.length - 1 ? System.lineSeparator() : " ");
            }
        }
        System.out.print(System.lineSeparator());
    }

    public void newAicraftCarrier() {
        newShip(5, "Aircraft Carrier");
    }

    public void newBattleship() {
        newShip(4, "Battleship");
    }

    public void newSubmarine() {
        newShip(3, "Submarine");
    }

    public void newCruiser() {
        newShip(3, "Cruiser");
    }

    public void newDestroyer() {
        newShip(2, "Destroyer");
    }

    public void newShip(int length, String name) {
        System.out.printf("Enter the coordinates of the %s (%d cells):", name, length);
        System.out.print(System.lineSeparator());
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                fill(length, name);
                shipCells += length;
                showField();
                break;
            } catch (WrongLocationException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void fill(int length, String name) throws WrongLocationException {
        String[] input = scanner.nextLine().split("\\s+");
        int rowStart = Math.min(input[0].charAt(0) - 65, input[1].charAt(0) - 65);
        int rowEnd = Math.max(input[0].charAt(0) - 65, input[1].charAt(0) - 65);
        int colStart = Math.min(Integer.parseInt(input[0].substring(1)) - 1, Integer.parseInt(input[1].substring(1)) - 1);
        int colEnd = Math.max(Integer.parseInt(input[0].substring(1)) - 1, Integer.parseInt(input[1].substring(1)) - 1);

        if (rowEnd != rowStart && colEnd != colStart
                || rowEnd >= field.length || rowStart >= field.length
                || colEnd >= field.length || colStart >= field.length) {
            throw new WrongLocationException("Error! Wrong ship location! Try again:");
        }

        if (rowEnd - rowStart != length - 1  && colEnd - colStart != length - 1) {
            throw new WrongLocationException(String.format("Error! Wrong length of the %s! Try again:", name));
        }

        for (int row = Math.max(0, rowStart - 1); row <= Math.min(field.length - 1, rowEnd + 1); row++) {
            for (int col = Math.max(0, colStart - 1); col <= Math.min(field.length - 1, colEnd + 1); col++) {
                if (field[row][col] == occupied) {
                    throw new WrongLocationException("Error! You placed it too close to another one. Try again:");
                }
            }
        }

        Ship ship = new Ship();
        if (rowEnd == rowStart) {
            for (int col = colStart; col <= colEnd; col++) {
                field[rowStart][col] = occupied;
                ship.cells.add(new int[]{rowStart, col});
            }
        }

        if (colEnd == colStart) {
            for (int row = rowStart; row <= rowEnd; row++) {
                field[row][colStart] = occupied;
                ship.cells.add(new int[]{row, colStart});
            }
        }
        ships.add(ship);

    }
}
