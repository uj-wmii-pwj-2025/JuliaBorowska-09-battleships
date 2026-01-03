package battleship.protocol;

public class CoordsParser {
    static int[] parse(String coordsStr) {
        if (coordsStr.length()<2 || coordsStr.length()>3) throw new IllegalArgumentException("ERROR: Nieprawidłowe współrzędne.");

        char colChar = coordsStr.charAt(0);
        if (colChar<'A' || colChar>'J') throw new IllegalArgumentException("ERROR: Nieprawidłowa kolumna.");
        int col = colChar-'A';

        int row;
        try {
            row = Integer.parseInt(coordsStr.substring(1))-1;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("ERROR: Nieprawidłowy numer wiersza.");
        }
        if (row<0 || row>9) {
            throw new IllegalArgumentException("ERROR: Numer wiersza poza zakresem.");
        }

        return new int[] {row, col};
    }
}
