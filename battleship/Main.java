package battleship;

import battleship.game.Board;
import battleship.game.Game;
import battleship.game.TurnStatus;
import battleship.network.Client;
import battleship.network.Server;
import battleship.utils.Arguments;
import battleship.utils.GameMode;
import battleship.utils.MapLoader;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        Arguments config;
        try {
            config = Arguments.parse(args);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        logConfig(config);

        Board board;
        try {
            board = loadBoard(config.mapPath);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.println("     TWOJA PLANSZA");
        System.out.println(board);

        Game game;
        if (config.mode== GameMode.SERVER) {
            game = new Game(board, TurnStatus.ENEMY);
            new Server(config.port, game).start(config.timeoutMillis);
        }
        else {
            game = new Game(board, TurnStatus.ME);
            new Client(config.port, config.host, game).start(config.timeoutMillis);
        }
    }

    private static void logConfig(Arguments config) {
        System.out.println("\nTryb: " + config.mode);
        System.out.println("Port: " + config.port);
        System.out.println("Host: " + config.host);
        System.out.println("Map path: " + config.mapPath);
        System.out.println("Timeout: " + config.timeoutMillis + " ms\n");
    }
    private static Board loadBoard(Path mapPath) {
        Board board;
        if (mapPath!=null) {
            try {
                board = MapLoader.load(mapPath);
            } catch (Exception e) {
                System.out.println("ERROR: Nie udało się wczytać podanej mapy.");
                System.out.println(e.getMessage());
                System.out.println("Generuję losową mapę.\n");
                board = Board.generateRandomBoard();
            }
        } else {
            board = Board.generateRandomBoard();
        }
        if (board==null) {
            throw new RuntimeException("ERROR: Wczytywanie mapy się nie powiodło.");
        }
        return board;
    }
}
