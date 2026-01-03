package battleship.utils;

public enum FieldStatus {
    WATER('.'),
    SHIP('#'),
    UNKNOWN('?');

    private final char symbol;
    FieldStatus (char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol () { return this.symbol; }

    public static FieldStatus fromChar (char c) {
        for (FieldStatus status : values()) {
            if (status.symbol==c) return status;
        }
        throw new IllegalArgumentException("Nieznany znak mapy: " + c);
    }
}
