package battleship.utils;

public class Field {
    final int row, col;
    FieldStatus status;
    Ship ship;
    boolean shot = false;

    public Field (int row, int col) {
        this.row = row;
        this.col = col;
        this.status = FieldStatus.UNKNOWN;
        this.ship = null;
    }
    public Field (int row, int col, FieldStatus status) {
        this.row = row;
        this.col = col;
        this.status = status;
        this.ship = null;
    }

    public int getRow() {
        return row;
    }
    public int getCol() { return col; }
    public FieldStatus getStatus() { return status; }
    public Ship getShip () {
        if (status==FieldStatus.SHIP) return ship;
        return null;
    }

    public void setShot() { this.shot = true; }
    public void setStatus(FieldStatus status) { this.status = status; }
    public void setShip(Ship ship) {
        this.ship = ship;
        this.status = FieldStatus.SHIP;
    }

    @Override
    public  String toString() {
        return String.valueOf(status.getSymbol());
    }
}