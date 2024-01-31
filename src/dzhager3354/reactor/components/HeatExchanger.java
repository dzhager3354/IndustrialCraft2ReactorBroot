package dzhager3354.reactor.components;

import dzhager3354.reactor.Reactor;

import java.util.ArrayList;
import java.util.List;

public class HeatExchanger extends ItemReactorHeatStorage {
    private int switchSide;
    private int switchReactor;

    public HeatExchanger(double maxHeat, int switchSide, int switchReactor) {
        super(maxHeat);
        this.switchSide = switchSide;
        this.switchReactor = switchReactor;
    }

    @Override
    public void process(int x, int y, Reactor reactor, boolean heatRun) {
        if (!heatRun) return;

        double myHeat = 0;
        List<Component> components = new ArrayList<>();
        if (switchSide > 0) {
            checkHeatAcceptor(reactor, x-1, y, components);
            checkHeatAcceptor(reactor, x+1, y, components);
            checkHeatAcceptor(reactor, x, y-1, components);
            checkHeatAcceptor(reactor, x, y+1, components);
            for (Component component : components) {
                double mymed = getHeat() * 100 / getMaxHeat();
                double heatableMed = component.getHeat() * 100 / component.getMaxHeat();
                double add = (component.getMaxHeat() / 100 * (heatableMed + mymed/2.0));
                if (add > switchSide) {
                    add = switchSide;
                }
                if (heatableMed + mymed / 2 < 1) {
                    add = switchSide / 2.0;
                }
                if (heatableMed + mymed / 2 < 0.75) {
                    add = switchSide / 4.0;
                }
                if (heatableMed + mymed / 2 < 0.5) {
                    add = switchSide / 8.0;
                }
                if (heatableMed + mymed / 2 < 0.25) {
                    add = 1;
                }
                if (Math.round(heatableMed * 10) / 10.0 > Math.round(mymed * 10) / 10.0) {
                    add -= 2*add;
                } else if (Math.round(heatableMed * 10.0) / 10.0 == Math.round(mymed * 10.0) / 10.0) {
                    add = 0;
                }
                myHeat -= add;
                myHeat += component.alterHeat(reactor, add);
            }
        }
        if (switchReactor > 0) {
            double mymed2 = this.getHeat() * 100 / this.getMaxHeat();
            double reactorMed = reactor.getHeat() * 100.0 / reactor.getMaxHeat();
            double add2 = Math.floor(Math.round(reactor.getMaxHeat() / 100.0 * (reactorMed + mymed2 / 2.0)));
            if (add2 > this.switchReactor) {
                add2 = this.switchReactor;
            }

            if (reactorMed + mymed2 / 2.0 < 1.0) {
                add2 = this.switchSide / 2.0;
            }
            if (reactorMed + mymed2 / 2.0 < 0.75) {
                add2 = this.switchSide / 4.0;
            }
            if (reactorMed + mymed2 / 2.0 < 0.5) {
                add2 = this.switchSide / 8.0;
            }
            if (reactorMed + mymed2 / 2.0 < 0.25) {
                add2 = 1;
            }
            if (Math.round(reactorMed * 10.0) / 10.0 > Math.round(mymed2 * 10.0) / 10.0) {
                add2 -= 2 * add2;
            }
            else if (Math.round(reactorMed * 10.0) / 10.0 == Math.round(mymed2 * 10.0) / 10.0) {
                add2 = 0;
            }
            myHeat -= add2;
        }
        alterHeat(x, y, reactor, myHeat);
    }

    private void checkHeatAcceptor(Reactor reactor, int x, int y, List<Component> list) {
        Component component = reactor.getComponent(x, y);
        if (component != null && component.canStoreHeat()) {
            list.add(component);
        }
    }
}
