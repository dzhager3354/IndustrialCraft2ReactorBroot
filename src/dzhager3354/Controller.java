package dzhager3354;

import dzhager3354.reactor.Init;

public class Controller implements Runnable {
    private volatile int maxRods;
    private char[] chars = new char[54];
    private char[] language;
    private int id;
    private int count;
    private int[] rodsArr = new int[3];

    public Controller(int id, int count){
        this.id = id;
        this.count = count;
    }

    public void setMaxRods(int rods, String str) {
        if (maxRods <= rods) {
            maxRods = rods;
            System.out.println(id + ": new max rod: " + maxRods + " " + str);
        }
    }

    @Override
    public void run() {
        language = new char[]{'f','e','d','m','o','x','y'};
        for (int i = 0; i < 53; i++) {
            chars[i] = 'f';
        }
        chars[52] = language[id*4 % language.length];
        chars[53] = language[id*4 / language.length];
        rodsArr[0] = Init.getRodsCount(chars);
        br:while (true) {
            ticks();
            Thread thread = new Thread(new BruteThread(this));
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (isEnd()) {
                System.out.println("End work thread" + id);
                break br;
            }
        }
    }

    private void ticks() {
        do {
            tick(0, 0);
        } while (rodsArr[0] < 15 || rodsArr[0] > 45 || rodsArr[0] < maxRods);
    }

    private void tick(int i, int thread) {
        if (i == chars.length) return;
        if (getPositionChar(chars[i]) == language.length - 1) {
            chars[i] = language[0];
            rodsArr[thread] = rodsArr[thread] + 4;
            tick(i+1, thread);
        } else {
            if (chars[i] == 'f') {
                rodsArr[thread] -= 2;
            }
            else if (chars[i] == 'e' || chars[i] == 'd') {
                rodsArr[thread] -= 1;
            }
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
        int res = id * 4 + count;
        return chars[52] == language[res % language.length] && chars[53] == language[res / language.length];
    }

    public int getMaxRods() {
        return maxRods;
    }

    public String currentSeed() {
        return id + " : " + new String(chars);
    }
}
