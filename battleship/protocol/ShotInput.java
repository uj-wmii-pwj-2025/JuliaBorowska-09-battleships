package battleship.protocol;

import java.util.Scanner;

public class ShotInput {
    private static final Scanner scanner = new Scanner(System.in);

    public static int[] askForTarget() {
        while (true) {
            System.out.println("Podaj cel strzału (np. A1): ");
            String input = scanner.nextLine().trim().toUpperCase();

            try {
                return CoordsParser.parse(input);
            } catch (Exception e) {
                System.out.println("Niepoprawny format celu. Spróbuj ponownie.");
            }
        }
    }
}
