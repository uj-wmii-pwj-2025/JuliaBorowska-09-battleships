package battleship.utils;

import battleship.game.Game;
import battleship.game.GameStatus;

public class EndGameRender {
    public static void finalLog(Game game) {
        if (game.getGameStatus()==GameStatus.WIN) {
            showEnemyWater(game);
        }

        BoardsRender.renderBoards(game);
        System.out.print("WYNIK GRY: ");
        if (game.getGameStatus()==GameStatus.WIN) {
            System.out.println("wygrałeś - gratulacje!");
        }
        else {
            System.out.println("przegrałeś - powodzenia następnym razem!");
        }
    }

    private static void showEnemyWater(Game game) {
        Field[][] enemyFields = game.getEnemyBoard().getFields();
        int size = enemyFields.length;
        for (int row=0; row<size; row++) {
            for (int col=0; col<size; col++) {
                Field field = enemyFields[row][col];
                if (field.getStatus()==FieldStatus.UNKNOWN) {
                    field.setStatus(FieldStatus.WATER);
                }
            }
        }
    }
}
