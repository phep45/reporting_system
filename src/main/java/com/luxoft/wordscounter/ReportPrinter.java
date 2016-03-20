package com.luxoft.wordscounter;

import com.google.common.base.Preconditions;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Component
public class ReportPrinter {
    public void printReport(Map<Set<EntryPair>, Integer> map) {
        Preconditions.checkNotNull(map, "No data to print");
        for (Set s : map.keySet())
            System.out.println(map.get(s) + " => " + s);
    }
}
