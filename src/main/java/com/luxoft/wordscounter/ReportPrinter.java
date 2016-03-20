package com.luxoft.wordscounter;

import com.google.common.base.Preconditions;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ReportPrinter {
    public void printReport(Map map) {
        Preconditions.checkNotNull(map, "No data to print");
        for (Object s : map.keySet())
            System.out.println(map.get(s) + " => " + s);
    }

}
