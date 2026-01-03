package battleship.utils;

import battleship.game.Game;
import battleship.game.ShotResult;

public class ConsoleRender {
    public static void updateScreen(Game game, ShotResult enemyResponse) {
        BoardsRender.renderBoards(game);
        renderLastResult(enemyResponse);
    }

    private static void renderLastResult(ShotResult result) {
        System.out.println("Wynik Twojego poprzedniego strzału: "+result);
        System.out.println();
    }
}
