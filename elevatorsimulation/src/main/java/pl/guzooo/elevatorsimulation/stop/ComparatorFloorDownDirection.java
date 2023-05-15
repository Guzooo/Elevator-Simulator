package pl.guzooo.elevatorsimulation.stop;

import java.util.Comparator;

public class ComparatorFloorDownDirection implements Comparator<Integer> {

    @Override
    public int compare(Integer i1, Integer i2) {
        return Integer.compare(i2, i1);
    }
}
