package dzhager3354;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        new Thread(Controller.controller).start();
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String s = scanner.next();
            if (s.equals("info")) {
                System.out.println(Controller.controller.currentSeed());
            }
        }
    }
}
