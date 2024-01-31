package dzhager3354.reactor.components;

import dzhager3354.reactor.Reactor;

public class ItemReactorHeatStorage extends Component {
    public ItemReactorHeatStorage(double maxHeat) {
        super(maxHeat);
    }

    @Override
    public void process(int x, int y, Reactor reactor, boolean heatRun) {

    }

    @Override
    public boolean acceptUraniumPulse(int x, int y, int pulseX, int pulseY, Reactor reactor, boolean heatRun) {
        return false;
    }

    @Override
    public boolean canStoreHeat() {
        return true;
    }

    @Override
    public boolean isDamageable() {
        return true;
    }

    @Override
    public double alterHeat(int x, int y, Reactor reactor, double heat) {
        double myHeat = getHeat() + heat;
        double maxHeat = getMaxHeat();
        if (myHeat > maxHeat) {
            reactor.setSlot(x, y, null);
            heat = maxHeat - myHeat + 1;
        } else {
            if (myHeat < 0) {
                heat = myHeat;
                myHeat = 0;
            } else {
                heat = 0;
            }
            setHeat(myHeat);
        }
        return heat;
    }

    @Override
    public double influenceExplosion(Reactor reactor) {
        return 0;
    }
}
