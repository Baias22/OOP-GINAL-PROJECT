package util;

import java.util.Scanner;


/**
 * Utility class for input validation.
 * Prevents invalid user input.
 */
public class InputValidator {

    /**
     * Reads a positive integer from user input.
     *
     * @param sc scanner for input
     * @param msg message shown to user
     * @return validated positive integer (> 0)
     */
    public static int readPositiveInt(Scanner sc, String msg) {
        while (true) {
            System.out.print(msg);

            if (!sc.hasNextInt()) {
                System.out.println("Invalid input. Enter a number.");
                sc.next();
                continue;
            }

            int value = sc.nextInt();

            if (value <= 0) {
                System.out.println("Value must be greater than 0.");
                continue;
            }

            return value;
        }
    }
    /**
     * Reads a positive decimal number from user input.
     *
     * @param sc scanner for input
     * @param msg message shown to user
     * @return validated positive double (> 0)
     */
    public static double readPositiveDouble(Scanner sc, String msg) {
        while (true) {
            System.out.print(msg);

            if (!sc.hasNextDouble()) {
                System.out.println("Invalid input. Enter a number.");
                sc.next();
                continue;
            }

            double value = sc.nextDouble();

            if (value <= 0) {
                System.out.println("Value must be greater than 0.");
                continue;
            }

            return value;
        }
    }

    /**
     * Reads user choice within a given range.
     *
     * @param sc scanner for input
     * @param msg message shown to user
     * @param min minimum allowed value
     * @param max maximum allowed value
     * @return validated choice within range [min, max]
     */
    public static int readChoice(Scanner sc, String msg, int min, int max) {
        while (true) {
            System.out.print(msg);


            if (!sc.hasNextInt()) {
                System.out.println("Invalid input. Enter a number.");
                sc.next();
                continue;
            }

            int choice = sc.nextInt();


            if (choice < min || choice > max) {
                System.out.println("Choose between " + min + " and " + max + ".");
                continue;
            }

            return choice;
        }
    }
}