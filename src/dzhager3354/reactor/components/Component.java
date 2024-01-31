package dzhager3354.reactor.components;

import dzhager3354.reactor.Reactor;

public abstract class Component implements Cloneable {
    private char code;
    private double maxHeat;
    private double heat;

    public Component(double maxHeat) {
        this.maxHeat = maxHeat;
    }

    public abstract void process(int x, int y, Reactor reactor, boolean heatRun);
    public abstract boolean acceptUraniumPulse(int x, int y, int pulseX, int pulseY, Reactor reactor, boolean heatRun);
    public abstract boolean canStoreHeat();
    public abstract boolean isDamageable();
    public abstract double alterHeat(int x, int y, Reactor reactor, double heat);
    public double alterHeat(Reactor reactor, double heat) {
        return alterHeat(reactor.getComponentOffset(this) % reactor.getLength(),
                reactor.getComponentOffset(this) / reactor.getLength(),
                reactor, heat);
    }
    public abstract double influenceExplosion(Reactor reactor);
    public boolean applyDamage(int damage) {
        heat += damage;
        return true;
    }

    public void setCode(char code) {
        this.code = code;
    }

    public char getCode() {
        return code;
    }

    public double getMaxHeat() {
        return maxHeat;
    }

    public double getHeat() {
        return heat;
    }

    public void setHeat(double heat) {
        this.heat = heat;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ":" + heat;
    }
}
