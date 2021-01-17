package util;

public class ConsoleUtil {

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";

    public static void printErrorMessage(String message) {
        System.out.println(ANSI_RED + message + ANSI_RESET);
    }

    public static void initShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> System.out.println("\nExiting application. Thank you for visiting.\n")));
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}
