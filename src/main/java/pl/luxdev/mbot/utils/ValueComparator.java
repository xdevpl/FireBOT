package pl.luxdev.mbot.utils;

import java.util.*;

/**
 * Created by xd3v on 04.05.2017.
 * All rights reserved.
 */

public class ValueComparator implements Comparator {
    Map map;
    public ValueComparator(Map map) {
        this.map = map;
    }
    @Override
    public int compare(Object keyA, Object keyB) {
        Comparable valueA = (Comparable) map.get(keyA);
        Comparable valueB = (Comparable) map.get(keyB);
        return valueB.compareTo(valueA);
    }
}




