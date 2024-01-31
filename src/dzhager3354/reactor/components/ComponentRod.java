package dzhager3354.reactor.components;

import dzhager3354.reactor.Reactor;

import java.util.ArrayList;
import java.util.List;

public abstract class ComponentRod extends Component{
    private int rods;
    public ComponentRod(int rods, double maxHeat) {
        super(maxHeat);
        this.rods = rods;
    }

    @Override
    public void process(int x, int y, Reactor reactor, boolean heatRun) {
        int basePulse = 1 + rods/2;
        for (int iter = 0; iter < rods; iter++) {
            int pulses = basePulse;
            if (!heatRun) {
                for (int i = 0; i < pulses; i++) {
                    acceptUraniumPulse(x, y, x, y, reactor, false);
                }
            } else {
                pulses += checkPulseable(reactor, x-1, y, x, y, false) +
                        checkPulseable(reactor, x+1, y, x, y, false) +
                        checkPulseable(reactor, x, y-1, x, y, false) +
                        checkPulseable(reactor, x, y+1, x, y, false);
                double heat = (pulses * pulses + pulses) * 2 + 4;

                List<Component> components = new ArrayList<>();
                checkHeatAcceptor(reactor, x-1, y, components);
                checkHeatAcceptor(reactor, x+1, y, components);
                checkHeatAcceptor(reactor, x, y-1, components);
                checkHeatAcceptor(reactor, x, y+1, components);

                while (components.size() > 0 && heat > 0) {
                    double dheat = heat / components.size();
                    heat -= dheat;
                    Component removeComponent = components.remove(0);
                    dheat = removeComponent.alterHeat(reactor, dheat);
                    heat += dheat;
                }
                if (heat > 0)
                    reactor.addHeat(heat);
            }
        }
        if (!heatRun && this.getHeat() >= this.getMaxHeat()-1)
            reactor.setSlot(x, y, null);
        else if (!heatRun)
            this.applyDamage(1);
    }

    @Override
    public abstract boolean acceptUraniumPulse(int x, int y, int pulseX, int pulseY, Reactor reactor, boolean heatRun);

    @Override
    public boolean canStoreHeat() {
        return false;
    }

    @Override
    public boolean isDamageable() {
        return true;
    }

    @Override
    public double alterHeat(int x, int y, Reactor reactor, double heat) {
        return heat;
    }

    @Override
    public double influenceExplosion(Reactor reactor) {
        return 2*rods;
    }

    private void checkHeatAcceptor(Reactor reactor, int x, int y, List<Component> components) {
        Component component = reactor.getComponent(x, y);
        if (component != null && component.canStoreHeat()) {
            components.add(component);
        }
    }

    private int checkPulseable(Reactor reactor, int x, int y, int otherX, int otherY, boolean heatRun) {
        Component component = reactor.getComponent(x, y);
        if (component != null && component.acceptUraniumPulse(x, y, otherX, otherY, reactor, heatRun)) {
            return 1;
        }
        return 0;
    }

    public int getRods() {
        return rods;
    }
}
