package battleship.game;

import battleship.utils.*;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Board {
    private final Field[][] fields;
    private final List<Ship> ships;

    public Board(Field[][] fields, List<Ship> ships) {
        this.fields = fields;
        this.ships = ships;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int size = fields.length;

        sb.append("   ");
        for (int col=0; col<size; col++) {
            sb.append((char) ('A' + col)).append(" ");
        }
        sb.append("\n");

        for (int row=0; row<size; row++) {
            if (row+1<size) sb.append(" ");
            sb.append(row+1).append(" ");

            for (int col=0; col<size; col++) {
                sb.append(fields[row][col]).append(" ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    public static Board generateRandomBoard() {
        Path randomMapPath = Path.of("maps/random.txt");
        MapGenerator generator = new MapGenerator();
        try {
            generator.generate(randomMapPath);
        } catch (Exception e) {
            System.out.println("ERROR: Wystąpił błąd podczas generowania mapy.");
        }

        Board randomBoard = null;
        try {
            randomBoard = MapLoader.load(randomMapPath);
        } catch (Exception e) {
            System.out.println("ERROR: Wystąpił błąd podczas wczytywania mapy.");
        }

        return randomBoard;
    }

    public static Board createEnemyBoard() {
        Field[][] fields = new Field[MapLoader.SIZE][MapLoader.SIZE];
        for (int row=0; row<MapLoader.SIZE; row++) {
            for (int col=0; col<MapLoader.SIZE; col++) {
                fields[row][col] = new Field(row, col);
            }
        }
        return new Board(fields, new ArrayList<>());
    }

    public Field getField(int row, int col) { return fields[row][col]; }
    public Field[][] getFields() {
        return fields;
    }
    public List<Ship> getShips() {
        return ships;
    }

    public ShotResult shoot(int row, int col) {
        Field field = fields[row][col];
        field.setShot();

        if (field.getStatus()==FieldStatus.WATER) {
            return ShotResult.PUDLO;
        }

        Ship ship = field.getShip();
        if (!ship.isSunk()) return ShotResult.TRAFIONY;
        if (allShipsSunk()) return ShotResult.OSTATNI_ZATOPIONY;
        return ShotResult.TRAFIONY_ZATOPIONY;
    }

    public boolean allShipsSunk() {
        for (Ship ship : ships) {
            if (!ship.isSunk()) return false;
        }
        return true;
    }
}