package battleship.protocol;

import battleship.game.ShotResult;

public enum Command {
    START("start"),
    PUDLO("pudło"),
    TRAFIONY("trafiony"),
    TRAFIONY_ZATOPIONY("trafiony zatopiony"),
    OSTATNI_ZATOPIONY("ostatni zatopiony");

    private final String text;

    Command(String text) {
        this.text = text;
    }
    public String getText() {
        return text;
    }
    public static Command fromText(String text) {
        for (Command c : values()) {
            if (c.text.equals(text)) return c;
        }
        throw new IllegalArgumentException("ERROR: nieznana komenda.");
    }
    public static Command mapShotResult(ShotResult result) {
        return switch (result) {
            case PUDLO -> Command.PUDLO;
            case TRAFIONY -> Command.TRAFIONY;
            case TRAFIONY_ZATOPIONY -> Command.TRAFIONY_ZATOPIONY;
            case OSTATNI_ZATOPIONY -> Command.OSTATNI_ZATOPIONY;
        };
    }
}
