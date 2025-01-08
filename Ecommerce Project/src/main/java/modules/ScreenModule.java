package modules;

import java.io.IOException;
import java.util.Scanner;

public class ScreenModule {
    public static void clearScreen(){
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (IOException | InterruptedException ex) {}
    }
    public static final Scanner scanner = new Scanner(System.in);
}
