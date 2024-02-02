package dzhager3354.reactor.components;

import dzhager3354.reactor.Reactor;

public class HeatVent extends ItemReactorHeatStorage {
    private int reactorVent;
    private int selfVent;
    public HeatVent(double maxHeat, int selfVent, int reactorVent) {
        super(maxHeat);
        this.selfVent = selfVent;
        this.reactorVent = reactorVent;
    }

    @Override
    public void process(int x, int y, Reactor reactor, boolean heatRun) {
        if (heatRun) {
            if (reactorVent > 0) {
                double reactorDrain = reactor.getHeat();
                double rheat = reactorDrain;
                if (reactorDrain > reactorVent) {
                    reactorDrain = reactorVent;
                }
                rheat -= reactorDrain;
                reactorDrain = alterHeat(x, y, reactor, reactorDrain);
                if (alterHeat(x, y ,reactor, reactorDrain) > 0) return;
                reactor.setHeat(rheat);
            }
            alterHeat(x, y, reactor, -selfVent);
        }
    }
}
