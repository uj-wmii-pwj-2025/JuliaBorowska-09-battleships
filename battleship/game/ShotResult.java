package battleship.game;

import battleship.protocol.Command;

public enum ShotResult {
    PUDLO,
    TRAFIONY,
    TRAFIONY_ZATOPIONY,
    OSTATNI_ZATOPIONY;

    public static ShotResult mapCommand(Command command) {
        return switch (command) {
            case PUDLO -> ShotResult.PUDLO;
            case TRAFIONY -> ShotResult.TRAFIONY;
            case TRAFIONY_ZATOPIONY -> ShotResult.TRAFIONY_ZATOPIONY;
            case OSTATNI_ZATOPIONY -> ShotResult.OSTATNI_ZATOPIONY;
            default -> throw new IllegalStateException("Nieoczekiwana komenda: " + command);
        };
    }
}
