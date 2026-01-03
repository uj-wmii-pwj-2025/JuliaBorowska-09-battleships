package battleship.utils;

import battleship.game.Board;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class MapLoader {
    public static final int SIZE = 10;

    public static Board load(Path path) throws IOException {
        Field[][] fields = loadFields(path);
        List<Ship> ships = parseShips(fields);
        return new Board(fields, ships);
    }

    static Field[][] loadFields(Path path) throws IOException {
        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        if (lines.size()!=SIZE) throw new IllegalArgumentException("ERROR: Mapa musi mieć "+SIZE+" wierszy.");

        Field[][] map = new Field[SIZE][SIZE];
        int row = 0;
        for (String line : lines) {
            if (line.length()!=SIZE) throw new IllegalArgumentException("ERROR: Nieprawidłowa długość wiersza: "+row);
            for (int col=0; col<SIZE; col++) {
                char c = line.charAt(col);
                FieldStatus status = FieldStatus.fromChar(c);
                map[row][col] = new Field(row, col, status);
            }
            row++;
        }
        return map;
    }

    static List<Ship> parseShips(Field[][] fields) {
        List<Ship> ships = new ArrayList<>();
        int size = fields.length;

        for (int row=0; row<size; row++) {
            for (int col=0; col<size; col++) {
                Field field = fields[row][col];
                if (field.status!=FieldStatus.SHIP) continue;

                Set<Ship> neighbourShips = new HashSet<>();
                if (row>0) {
                    Ship upShip = fields[row-1][col].getShip();
                    if (upShip!=null) neighbourShips.add(upShip);
                }
                if (col>0) {
                    Ship leftShip = fields[row][col-1].getShip();
                    if (leftShip!=null) neighbourShips.add(leftShip);
                }

                if (neighbourShips.isEmpty()) {
                    Ship ship = new Ship(field);
                    field.ship = ship;
                    ships.add(ship);
                }
                else if (neighbourShips.size()==1) {
                    Ship ship = neighbourShips.iterator().next();
                    ship.addField(field);
                    field.ship = ship;
                }
                else {
                    Iterator<Ship> it = neighbourShips.iterator();
                    Ship mainShip = it.next();
                    mainShip.addField(field);
                    field.ship = mainShip;

                    Ship other = it.next();
                    for (Field f : other.fields) {
                        mainShip.addField(f);
                        f.ship = mainShip;
                    }
                    ships.remove(other);
                }
            }
        }
        return ships;
    }
}
