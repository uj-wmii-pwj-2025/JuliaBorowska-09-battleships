package battleship.network;

import battleship.game.Game;
import battleship.game.ShotResult;
import battleship.game.TurnLoop;
import battleship.protocol.Command;
import battleship.protocol.Message;
import battleship.protocol.MessageIO;
import battleship.protocol.ShotInput;
import battleship.utils.BoardsRender;
import battleship.utils.EndGameRender;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Server {
    private final int port;
    private final Game game;

    public Server(int port, Game game) {
        this.port = port;
        this.game = game;
    }

    public void start(int timeout) {
        System.out.println("Server: nasłuchuje na porcie "+port);

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            Socket clientSocket = serverSocket.accept();
            clientSocket.setSoTimeout(timeout);
            System.out.println("Server: połączono z klientem");

            InputStream inStream = clientSocket.getInputStream();
            InputStreamReader inReader = new InputStreamReader(inStream, StandardCharsets.UTF_8);
            BufferedReader in = new BufferedReader(inReader);

            OutputStream outStream = clientSocket.getOutputStream();
            OutputStreamWriter outWriter = new OutputStreamWriter(outStream, StandardCharsets.UTF_8);
            PrintWriter out = new PrintWriter(outWriter, true);

            int[] firstTarget = firstTurn(in, out);
            new TurnLoop(game, firstTarget[0], firstTarget[1]).run(in, out);
            EndGameRender.finalLog(game);

            in.close();
            out.close();
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private int[] firstTurn(BufferedReader in, PrintWriter out) throws IOException {
        Message startMessage = MessageIO.receive(in);
        ShotResult myResponse = game.receiveShot(startMessage.getRow(), startMessage.getCol());
        Command myResponseCommand = Command.mapShotResult(myResponse);

        BoardsRender.renderBoards(game);

        int[] myTarget = ShotInput.askForTarget();
        Message outMessage = new Message(myResponseCommand, myTarget[0], myTarget[1]);
        MessageIO.send(out, outMessage);
        return myTarget;
    }
}
