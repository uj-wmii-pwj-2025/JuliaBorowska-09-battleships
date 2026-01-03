package battleship.protocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class MessageIO {
    private static final int MAX_RETRIES = 3;

    public static Message receive(BufferedReader in) throws IOException {
        String line;
        try {
            line = in.readLine();
        } catch (IOException e) {
            throw new IOException("ERROR: Błąd komunikacji");
        }
        if (line==null) throw new IOException("ERROR: Połączenie zamknięte.");
        return Message.parse(line);
    }

    public static void send(PrintWriter out, Message message) {
        out.print(message.serialize());
        out.flush();
    }
}
