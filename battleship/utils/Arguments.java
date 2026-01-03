package battleship.utils;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class Arguments {
    public GameMode mode;
    public int port;
    public String host;
    public Path mapPath;
    public int timeoutMillis = 1_000;

    public static Arguments parse(String[] args) {
        Map<String, String> argValues = mapArgs(args);
        Arguments result = new Arguments();

        result.parseMode(argValues);
        result.parsePort(argValues);
        result.parseHost(argValues);
        result.parseMap(argValues);
        result.parseTimeout(argValues);

        return result;
    }

    private static Map<String, String> mapArgs(String[] args) {
        Map<String, String> argValues = new HashMap<>();
        for (int i=0; i<args.length; i++) {
            String arg = args[i];
            if (!arg.startsWith("-")) throw new IllegalArgumentException("ERROR: Niepoprawna nazwa argumentu.");
            if (i+1>= args.length) throw new IllegalArgumentException("ERROR: Brak wartości dla argumentu.");
            if (argValues.containsKey(arg)) throw new IllegalArgumentException("ERROR: Duplikat argumentu.");
            argValues.put(arg, args[i+1]);
            i++;
        }
        return argValues;
    }

    private void parseMode(Map<String, String> argValues) {
        String modeStr = argValues.get("-mode");
        if (modeStr==null) throw new IllegalArgumentException("ERROR: Nie podano -mode.");

        this.mode = switch (modeStr) {
            case "server" -> GameMode.SERVER;
            case "client" -> GameMode.CLIENT;
            default -> throw new IllegalArgumentException("ERROR: Podano niepoprawne -mode.");
        };
    }
    private void parsePort(Map<String, String> argValues) {
        String portStr = argValues.get("-port");
        if (portStr==null) throw new IllegalArgumentException("ERROR: Nie podano -port.");

        try {
            this.port = Integer.parseInt(portStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("ERROR: Podano niepoprawną wartość -port.");
        }

        if (this.port<=0 || this.port>65_535) throw new IllegalArgumentException("ERROR: Podano wartość -port spoza dopuszczalnego zakresu.");
    }
    private void parseHost(Map<String, String> argValues) {
        this.host = argValues.get("-host");
        if (this.mode==GameMode.CLIENT && this.host==null) {
            throw new IllegalArgumentException("ERROR: Nie podano -host w trybie klienta.");
        }
    }
    private void parseMap(Map<String, String> argValues) {
        String mapStr = argValues.get("-map");
        if (mapStr==null || mapStr.equals("random")) {
            this.mapPath = null;
        } else {
            this.mapPath = Path.of(mapStr);
        }
    }
    private void parseTimeout(Map<String, String> argValues) {
        String timeoutStr = argValues.get("-timeout");
        if (timeoutStr!=null) {
            try {
                int seconds = Integer.parseInt(timeoutStr);
                if (seconds<=0) throw new IllegalArgumentException("ERROR: Podano niepoprawną wartość -timeout.");
                this.timeoutMillis = seconds*1_000;
            } catch (Exception e) {
                throw new IllegalArgumentException("ERROR: Podano niepoprawną wartość -timeout (błąd parsowania).");
            }
        }
    }
}
