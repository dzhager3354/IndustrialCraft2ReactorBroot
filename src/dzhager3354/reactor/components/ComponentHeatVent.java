package dzhager3354.reactor.components;

import dzhager3354.reactor.Reactor;

public class ComponentHeatVent extends Component {
    private double sideVent;

    public ComponentHeatVent(double maxHeat, double sideVent) {
        super(maxHeat);
        this.sideVent = sideVent;
    }

    @Override
    public void process(int x, int y, Reactor reactor, boolean heatRun) {
        if (heatRun) {
            cold(x-1, y, reactor);
            cold(x+1, y, reactor);
            cold(x, y-1, reactor);
            cold(x, y+1, reactor);
        }
    }

    @Override
    public boolean acceptUraniumPulse(int x, int y, int pulseX, int pulseY, Reactor reactor, boolean heatRun) {
        return false;
    }

    @Override
    public boolean canStoreHeat() {
        return false;
    }

    @Override
    public boolean isDamageable() {
        return false;
    }

    @Override
    public double alterHeat(int x, int y, Reactor reactor, double heat) {
        return heat;
    }

    @Override
    public double influenceExplosion(Reactor reactor) {
        return 0;
    }

    private void cold(int x, int y, Reactor reactor) {
        Component component = reactor.getComponent(x, y);
        if (component != null && component.canStoreHeat()) {
            component.alterHeat(x, y, reactor, -sideVent);
        }
    }
}
