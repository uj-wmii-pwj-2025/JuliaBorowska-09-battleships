package battleship.protocol;

public class Message {
    private final Command command;
    private final int row;
    private final int col;

    public Message(Command command, int row, int col) {
        this.command = command;
        this.row = row;
        this.col = col;
    }

    public Command getCommand() {
        return command;
    }
    public int getRow() {
        return row;
    }
    public int getCol() {
        return col;
    }

    public static Message parse(String line) throws IllegalArgumentException{
        if (line==null) throw new IllegalArgumentException("ERROR: Pusta wiadomość.");
        String[] parts = line.split(";");
        if (parts.length!=2) throw new IllegalArgumentException("ERROR: Niepoprawny format wiadomości.");

        Command command = Command.fromText(parts[0]);
        int[] coords = CoordsParser.parse(parts[1]);
        int row = coords[0];
        int col = coords[1];

        return new Message(command, row, col);
    }

    public String serialize() {
        char colChar = (char) ('A'+col);
        int rowNr = row+1;
        return command.getText()+";"+colChar+rowNr+"\n";
    }
}
