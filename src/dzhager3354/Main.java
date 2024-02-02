package dzhager3354;

import dzhager3354.reactor.Init;
import dzhager3354.reactor.Reactor;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<Controller> controllers = new ArrayList<>();
    public static void main(String[] args) {
        for (int i = 0; i < 12; i++) {
            Controller controller = new Controller(i, i != 11 ? 4 : 5);
            controllers.add(controller);
            new Thread(controller).start();
        }
        for (Controller controller : controllers)
            System.out.println(controller.currentSeed());
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String s = scanner.next();
            if (s.equals("info")) {
                for (Controller controller : controllers)
                    System.out.println(controller.currentSeed());
            }
        }
//        String temp = "00xex000000yxy000000xe00000000000000000000000000000000";
//        Reactor reactor = new Reactor();
//        Init.init(reactor, temp.toCharArray());
//        System.out.println(reactor);
//        for (int i = 0; i < 10000; i++) {
//            reactor.process();
//            if (reactor.isExplode()) {
//                System.out.println("RIP " + i);
//                break;
//            }
//        }
//
//        System.out.println();
//        System.out.println(reactor);
    }
}
