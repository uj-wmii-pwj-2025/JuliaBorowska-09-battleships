package battleship.utils;

import battleship.game.Game;

public class BoardsRender {
    public static void renderBoards(Game game) {
        clearScreen();
        Field[][] my = game.getMyBoard().getFields();
        Field[][] enemy = game.getEnemyBoard().getFields();
        int size = my.length;

        printHeader();
        for (int row=0; row<size; row++) {
            printRow(row, my, enemy);
        }
        System.out.println();
    }

    private static void printHeader() {
        System.out.println("     TWOJA PLANSZA           PLANSZA PRZECIWNIKA");
        System.out.print("   ");
        printColumns();
        System.out.print("      ");
        printColumns();
        System.out.println();
    }
    private static void printColumns() {
        for (char c = 'A'; c <= 'J'; c++) {
            System.out.print(c + " ");
        }
    }
    private static void printRow(int row, Field[][] my, Field[][] enemy) {
        printMyRow(row, my);
        System.out.print("   ");
        printEnemyRow(row, enemy);
        System.out.println();
    }
    private static void printMyRow(int row, Field[][] fields) {
        int size = fields.length;
        System.out.printf("%2d ", row+1);
        for (int col=0; col<size; col++) {
            System.out.print(printMyField(fields[row][col])+" ");
        }
    }
    private static char printMyField(Field f) {
        if (f.shot) {
            if (f.getStatus() == FieldStatus.SHIP) return '@';
            return '~';
        }
        if (f.getStatus() == FieldStatus.SHIP) return '#';
        return '.';
    }
    private static void printEnemyRow(int row, Field[][] fields) {
        int size = fields.length;
        System.out.printf("%2d ", row+1);
        for (int col=0; col<size; col++) {
            System.out.print(fields[row][col]+" ");
        }
    }
    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
