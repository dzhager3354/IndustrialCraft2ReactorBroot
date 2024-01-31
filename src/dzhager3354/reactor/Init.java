package dzhager3354.reactor;

import dzhager3354.reactor.components.*;

public class Init {
    public static void init(Reactor reactor, char[] ch) {
        Component component;
        for (int i = 0; i < 54; i++) {
            switch (ch[i]) {
                case 'x':
                    component = new HeatVent(2000, 20, 0);
                    component.setCode('x');
                    break;
                case 'i':
                    component = new HeatVent(1000, 12, 0);
                    break;
                case 'y':
                    component = new ComponentHeatVent(0, 8);
                    component.setCode('y');
                    break;
                case 'd':
                    component = new ComponentRodThorium(1, 10000);
                    component.setCode('d');
                    break;
                case 'e':
                    component = new ComponentRodThorium(2, 10000);
                    component.setCode('e');
                    break;
                case 'f':
                    component = new ComponentHeatVent(4, 10000);
                    component.setCode('f');
                    break;
                case 'm':
                    component = new HeatExchanger(10000, 24, 8);
                    component.setCode('m');
                    break;
                case 'o':
                    component = new HeatExchanger(5000, 36, 0);
                    component.setCode('o');
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + ch[i]);
            }
            reactor.addSlot(component);
        }
    }

    public static String getHash(Reactor reactor) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < 54; i++) {
            res.append(reactor.getComponent(i % 9, i / 9).getCode());
        }
        return res.toString();
    }
    public static int getRodsCount(Reactor reactor) {
        int sum = 0;
        for (int i = 0; i < 54; i++) {
            Component component = reactor.getComponent(i % 9, i / 9);
            if (component instanceof ComponentRod) {
                sum += ((ComponentRod) component).getRods();
            }
        }
        return sum;
    }
}
