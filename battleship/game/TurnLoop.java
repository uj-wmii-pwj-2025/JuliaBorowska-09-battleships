package battleship.game;

import battleship.protocol.Command;
import battleship.protocol.Message;
import battleship.protocol.MessageIO;
import battleship.protocol.ShotInput;
import battleship.utils.ConsoleRender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class TurnLoop {
    private final Game game;
    private int targetRow;
    private int targetCol;

    public TurnLoop(Game game, int targetRow, int targetCol) {
        this.targetRow = targetRow;
        this.targetCol = targetCol;
        this.game = game;
    }

    public void run(BufferedReader in, PrintWriter out) throws IOException {
        while (game.getGameStatus()==GameStatus.IN_PROGRESS) {
            Message inMessage;
            inMessage = MessageIO.receive(in);

            ShotResult enemyResponse = ShotResult.mapCommand(inMessage.getCommand());
            game.applyEnemyResponse(targetRow, targetCol, enemyResponse);
            if (game.getGameStatus()!=GameStatus.IN_PROGRESS) return;

            ShotResult myResponse = game.receiveShot(inMessage.getRow(), inMessage.getCol());
            Command myResponseCommand = Command.mapShotResult(myResponse);

            ConsoleRender.updateScreen(game, enemyResponse);

            if (myResponse!=ShotResult.OSTATNI_ZATOPIONY) {
                int[] myTarget = ShotInput.askForTarget();
                targetRow = myTarget[0];
                targetCol = myTarget[1];
            }

            Message outMessage = new Message(myResponseCommand, targetRow, targetCol);
            MessageIO.send(out, outMessage);
        }
    }
}