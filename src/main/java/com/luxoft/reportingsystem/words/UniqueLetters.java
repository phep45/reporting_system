package com.luxoft.reportingsystem.words;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class UniqueLetters {

    private Map<Set<String>, Integer> map;

    public UniqueLetters(File file) {
        if (!file.getName().endsWith(".txt"))
            throw new IllegalArgumentException();
        map = countUniques(file);
    }

    private Map<Set<String>, Integer> countUniques(File file) {
        map = new HashMap<>();

        try (Scanner scan = new Scanner(new FileInputStream(file))) {
            while (scan.hasNext()) {
                Set<String> set = new TreeSet<>(Arrays.asList(scan.next().split("")));
                if (!map.containsKey(set))
                    map.put(set, 1);
                else {
                    Integer val = map.get(set);

                    val = val + 1;
                    map.put(set, val);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return map;
    }

    public List<Integer> getAmounts() {
        if (map == null)
            return null;
        List<Integer> listOfAmounts = map.values().stream().collect(Collectors.toList());
        Collections.sort(listOfAmounts, (val1, val2) -> val2.compareTo(val1));
        return listOfAmounts;
    }

    public void printUniques() {
        if (map != null)
            for (Set s : map.keySet())
                System.out.println(map.get(s) + " => " + s);
    }


    public static void main(String[] args) {
        UniqueLetters uniqueLetters = new UniqueLetters(new File("src\\main\\resources\\words\\INPUT.txt"));
        System.out.println(uniqueLetters.getAmounts());
        uniqueLetters.printUniques();
    }

}
