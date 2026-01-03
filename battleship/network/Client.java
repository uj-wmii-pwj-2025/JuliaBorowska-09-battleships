package battleship.network;

import battleship.game.Game;
import battleship.game.TurnLoop;
import battleship.protocol.Command;
import battleship.protocol.Message;
import battleship.protocol.MessageIO;
import battleship.protocol.ShotInput;
import battleship.utils.EndGameRender;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Client {
    private final int port;
    private final String host;
    private final Game game;

    public Client(int port, String host, Game game) {
        this.port = port;
        this.host = host;
        this.game = game;
    }

    public void start(int timeout) {
        System.out.println("Client: łączę się z "+host+" przez port "+port);

        try {
            Socket socket = new Socket(host, port);
            socket.setSoTimeout(timeout);

            InputStream inStream = socket.getInputStream();
            InputStreamReader inReader = new InputStreamReader(inStream, StandardCharsets.UTF_8);
            BufferedReader in = new BufferedReader(inReader);

            OutputStream outStream = socket.getOutputStream();
            OutputStreamWriter outWriter = new OutputStreamWriter(outStream, StandardCharsets.UTF_8);
            PrintWriter out = new PrintWriter(outWriter, true);

            int[] firstTarget = firstTurn(out);
            new TurnLoop(game, firstTarget[0], firstTarget[1]).run(in, out);
            EndGameRender.finalLog(game);

            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private int[] firstTurn(PrintWriter out) throws IOException {
        int[] myTarget = ShotInput.askForTarget();
        Message startMessage = new Message(Command.START, myTarget[0], myTarget[1]);
        MessageIO.send(out, startMessage);
        return myTarget;
    }
}
