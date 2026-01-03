package battleship.game;

import battleship.utils.Field;
import battleship.utils.FieldStatus;
import battleship.utils.Ship;
import java.util.*;

public class Game {
    private final Board myBoard;
    private final Board enemyBoard;
    private TurnStatus turn;
    private GameStatus gameStatus;

    public Game(Board myBoard, TurnStatus startingTurn) {
        this.myBoard = myBoard;
        this.enemyBoard = Board.createEnemyBoard();
        this.turn = startingTurn;
        this.gameStatus = GameStatus.IN_PROGRESS;
    }

    public ShotResult receiveShot(int row, int col) {
        ShotResult result = myBoard.shoot(row, col);
        if (result==ShotResult.OSTATNI_ZATOPIONY) {
            gameStatus = GameStatus.LOSS;
        } else {
            turn = TurnStatus.ME;
        }
        return result;
    }

    public void applyEnemyResponse(int row, int col, ShotResult result) {
        Field field = enemyBoard.getField(row, col);
        field.setShot();

        switch (result) {
            case PUDLO -> field.setStatus(FieldStatus.WATER);
            case TRAFIONY, TRAFIONY_ZATOPIONY, OSTATNI_ZATOPIONY -> updateEnemyShips(row, col);
        }

        if (result==ShotResult.TRAFIONY_ZATOPIONY || result==ShotResult.OSTATNI_ZATOPIONY) {
            Ship ship = field.getShip();
            markWaterAroundShip(ship);
        }

        if (result==ShotResult.OSTATNI_ZATOPIONY) {
            gameStatus = GameStatus.WIN;
        } else {
            turn = TurnStatus.ENEMY;
        }
    }

    private void updateEnemyShips(int row, int col) {
        Field[][] fields = enemyBoard.getFields();
        List<Ship> ships = enemyBoard.getShips();
        int size = fields.length;

        Field field = enemyBoard.getField(row, col);
        field.setStatus(FieldStatus.SHIP);

        Set<Ship> neighbourShips = new HashSet<>();
        if (row>0) {
            Ship upShip = fields[row-1][col].getShip();
            if (upShip!=null) neighbourShips.add(upShip);
        }
        if (row+1<size) {
            Ship downShip = fields[row+1][col].getShip();
            if (downShip!=null) neighbourShips.add(downShip);
        }
        if (col>0) {
            Ship leftShip = fields[row][col-1].getShip();
            if (leftShip!=null) neighbourShips.add(leftShip);
        }
        if (col+1<size) {
            Ship rightShip = fields[row][col+1].getShip();
            if (rightShip!=null) neighbourShips.add(rightShip);
        }

        if (neighbourShips.isEmpty()) {
            Ship ship = new Ship(field);
            field.setShip(ship);
            ships.add(ship);
        }
        else if (neighbourShips.size()==1) {
            Ship ship = neighbourShips.iterator().next();
            ship.addField(field);
            field.setShip(ship);
        }
        else {
            Iterator<Ship> it = neighbourShips.iterator();
            Ship mainShip = it.next();
            mainShip.addField(field);
            field.setShip(mainShip);

            while (it.hasNext()) {
                Ship other = it.next();
                for (Field f : other.getFields()) {
                    mainShip.addField(f);
                    f.setShip(mainShip);
                }
                ships.remove(other);
            }
        }
    }

    private void markWaterAroundShip(Ship ship) {
        for (Field field : ship.getFields()) {
            List<Field> neighbours = getNeighbours(field.getRow(), field.getCol());
            for (Field neighbour : neighbours) {
                if (neighbour.getStatus()==FieldStatus.UNKNOWN) {
                    neighbour.setStatus(FieldStatus.WATER);
                }
            }
        }
    }

    private List<Field> getNeighbours(int row, int col) {
        Field[][] fields = enemyBoard.getFields();
        int size = fields.length;

        List<Field> neighbours = new ArrayList<>();
        for (int i=-1; i<=1; i++) {
            for (int j=-1; j<=1; j++) {
                if (i==0 && j==0) continue;
                if (row+i<0 || col+j<0) continue;
                if (row+i>=size || col+j>=size) continue;
                neighbours.add(fields[row+i][col+j]);
            }
        }
        return neighbours;
    }
    public GameStatus getGameStatus() { return gameStatus; }
    public Board getMyBoard() { return myBoard; }
    public Board getEnemyBoard() { return enemyBoard; }
// opcjonalne funkcje:
//    public ShotResult shootAtEnemy(Coordinates c);
//    public boolean isMyTurn() { return turn == Turn.ME; }
}