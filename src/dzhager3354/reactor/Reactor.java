package dzhager3354.reactor;

import dzhager3354.reactor.components.Component;

import java.util.ArrayList;
import java.util.List;

public class Reactor {
    private final int length = 9;
    private double output;
    private double heat;
    private final double maxHeat = 10000;
    private int emitHeatBuffer;
    private int emitHeat;
    private List<Component> reactorSlots = new ArrayList<>();
    private boolean isExplode;

    public void setSlot(int x, int y, Component component) {
        if (reactorSlots.size() < x + length*y) {
            throw new IllegalArgumentException();
        }
        reactorSlots.set(x+length*y, component);
    }

    public void addSlot(Component component) {
        reactorSlots.add(component);
    }

    public Component getComponent(int x, int y) {
        if (x < 0 || x >= length || y < 0 || y >= 6) return null;
        return reactorSlots.get(x+length*y);
    }

    public int getComponentOffset(Component component) {
        return reactorSlots.indexOf(component);
    }

    public void explode() {
        isExplode = true;
    }

    public void process() {
        if (isExplode) return;
        for (int pass = 0; pass < 2; pass++) {
            for (int y = 0; y < 6; y++) {
                for (int x = 0; x < length; x++) {
                    Component component = getComponent(x, y);
                    if (component != null) {
                        component.process(x, y, this, pass == 0);
                    }
                }
            }
        }

        if (this.heat > this.maxHeat) {
            this.explode();
        };
    }

    public void addEmitHeat(int heat) {
        emitHeatBuffer += heat;
    }

    public void addHeat(double heat) {
        this.heat += heat;
    }

    public double getOutput() {
        return output;
    }

    public double getHeat() {
        return heat;
    }

    public double getMaxHeat() {
        return maxHeat;
    }

    public int getEmitHeat() {
        return emitHeat;
    }

    public boolean isExplode() {
        return isExplode;
    }

    public void setHeat(double heat) {
        this.heat = heat;
    }

    public int getLength() {
        return length;
    }

    @Override
    public String toString() {
        String res = "";
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < length; j++) {
                res += reactorSlots.get(j + i*length) + " ";
            }
            res += "\n";
        }
        return res;
    }
}
