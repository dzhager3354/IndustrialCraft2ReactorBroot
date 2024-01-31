package dzhager3354.reactor.components;

import dzhager3354.reactor.Reactor;

public class ComponentRodUranium extends ComponentRod {
    public static Component createOneRod(){
        return new ComponentRodUranium(1, 20000);
    }

    public static Component createTwoRod() {
        return new ComponentRodUranium(2, 20000);
    }

    public static Component createFourRod() {
        return new ComponentRodUranium(4, 20000);
    }

    private ComponentRodUranium(int rods, double maxHeat) {
        super(rods, maxHeat);
    }

    @Override
    public boolean acceptUraniumPulse(int x, int y, int pulseX, int pulseY, Reactor reactor, boolean heatRun) {
        return true;
    }
}
