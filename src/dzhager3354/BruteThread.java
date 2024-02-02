package dzhager3354;

import dzhager3354.reactor.Init;
import dzhager3354.reactor.Reactor;

import java.util.Arrays;

public class BruteThread implements Runnable {
    private volatile char[] codes;
    private boolean isNewCode;
    private Controller controller;

    public BruteThread(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void run() {
        a:while (codes != null) {
            if (!isNewCode) continue;
            int rods = Init.getRodsCount(codes);
            if (rods < 15 || rods > 44 || controller.getMaxRods() > rods) {
                isNewCode = false;
                continue;
            }
            Reactor reactor = new Reactor();
            Init.init(reactor, codes);
            for (int i = 0; i < 10000; i++) {
                reactor.process();
                if (reactor.isExplode()) {
                    isNewCode = false;
                    continue a;
                }
            }
            controller.setMaxRods(rods, new String(codes));
            isNewCode = false;
        }
    }

    public void setCodes(char[] codes) {
        if (codes == null) {
            this.codes = null;
            return;
        }
        this.codes = Arrays.copyOf(codes, 54);
        isNewCode = true;
    }

    public boolean isNewCode() {
        return isNewCode;
    }
}
