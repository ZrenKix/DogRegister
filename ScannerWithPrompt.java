/*
 *  @author William Jakobsson, wija4116
 */

import java.util.ArrayList;
import java.util.Scanner;
import java.io.InputStream;

public class ScannerWithPrompt {
    private static final String END_OF_TEXT = "?> ";

    private static ArrayList<InputStream> usedInputStreams = new ArrayList<>();
    private Scanner scanner;

    public ScannerWithPrompt() {
        this(System.in);
    }

    public ScannerWithPrompt(InputStream inputStream) {
        if (usedInputStreams.contains(inputStream))
            throw new IllegalStateException("Error: InputStream already in use");
        usedInputStreams.add(inputStream);
        this.scanner = new Scanner(inputStream);
    }

    public String readText(String prompt) {
        System.out.print(prompt + END_OF_TEXT);
        String input = formatString(scanner.nextLine().trim());
        return input;
    }

    public int readInteger(String prompt) {
        System.out.print(prompt + END_OF_TEXT);
        int input = scanner.nextInt();
        scanner.nextLine();
        return input;
    }

    public double readDouble(String prompt) {
        System.out.print(prompt + END_OF_TEXT);
        double input = scanner.nextDouble();
        scanner.nextLine();
        return input;
    }

    private String formatString(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
    }
}