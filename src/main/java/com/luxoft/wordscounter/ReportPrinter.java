package com.luxoft.wordscounter;

import com.google.common.base.Preconditions;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ReportPrinter {
    public void printReport(Map<SetOfPairs, Integer> map) {
        Preconditions.checkNotNull(map, "No data to print");
        for (Map.Entry<SetOfPairs, Integer> s : map.entrySet()) {
            System.out.println(s.getValue() + " => " + s.getKey());
        }
    }

}
