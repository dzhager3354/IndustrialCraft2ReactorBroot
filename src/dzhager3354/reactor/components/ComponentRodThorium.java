package dzhager3354.reactor.components;

import dzhager3354.reactor.Reactor;

public class ComponentRodThorium extends ComponentRod {
    public ComponentRodThorium(int rods, double maxHeat) {
        super(rods, maxHeat);
    }

    @Override
    public boolean acceptUraniumPulse(int x, int y, int pulseX, int pulseY, Reactor reactor, boolean heatRun) {
        return true;
    }

    @Override
    public double getFinalHeat(double heat, int cells) {
        return heat + 4*cells;
    }
}
