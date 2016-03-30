package com.luxoft.wordscounter;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.tuple.MutablePair;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.TreeSet;

@Component
public class ReportPrinter {

    private static final String SEPARATOR = " => ";

    public void printReport(  Map<TreeSet<MutablePair<String, Integer>>, Integer> map) {
        Preconditions.checkNotNull(map, "No data to print");
        for (Map.Entry s : map.entrySet()) {
            System.out.println(s.getValue() + SEPARATOR + s.getKey());
        }
    }

}
