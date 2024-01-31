package dzhager3354;

import dzhager3354.reactor.Init;
import dzhager3354.reactor.Reactor;

import java.util.ArrayList;
import java.util.List;

public class Controller implements Runnable {
    public static final Controller controller = new Controller();
    private volatile int maxRods;
    private char[] chars = new char[54];
    private char[] language;
    private List<BruteThread> threads = new ArrayList<>();

    private Controller(){}

    public void setMaxRods(int rods, String str) {
        if (maxRods <= rods) {
            maxRods = rods;
            System.out.println("new max rod: " + maxRods + " " + str);
        }
    }

    @Override
    public void run() {
        language = new char[]{'x', 'y', 'd', 'e', 'f', 'm', 'o'};
        for (int i = 0; i < 54; i++) {
            chars[i] = language[0];
        }
        for (int i = 0; i < 12; i++) {
            BruteThread thread = new BruteThread();
            threads.add(thread);
            thread.setCodes(chars);
            tick(0);
            new Thread(thread).start();
        }
        br:while (true) {
            for (BruteThread thread : threads) {
                if (!thread.isNewCode()) {
                    thread.setCodes(chars);
                    tick(0);
                    if (isEnd()){
                        for (BruteThread thread1 : threads) {
                            thread1.setCodes(null);
                        }
                        break br;
                    }
                }
            }
        }
    }

    private void tick(int i) {
        if (getPositionChar(chars[i]) == language.length - 1) {
            chars[i] = language[0];
            tick(i+1);
        } else {
            chars[i] = language[getPositionChar(chars[i])+1];
        }
    }

    private int getPositionChar(char ch) {
        for (int i = 0; i < language.length; i++) {
            if (language[i] == ch) return i;
        }
        throw new IllegalArgumentException();
    }

    private boolean isEnd() {
        for (int i = 0; i < 54; i++) {
            if (chars[i] != language[0]) return false;
        }
        return true;
    }

    public int getMaxRods() {
        return maxRods;
    }
}
