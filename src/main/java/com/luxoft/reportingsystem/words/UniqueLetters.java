package com.luxoft.reportingsystem.words;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class UniqueLetters {

    private File file;
    private Map<Set<String>, Integer> map;

    public UniqueLetters(File file) {
        this.file = file;
    }

    public Map<Set<String>, Integer> countUniques() {

        map = new HashMap<>();

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            Scanner scan = new Scanner(fileInputStream);
            while(scan.hasNext()) {
                Set<String> set = new TreeSet<>(Arrays.asList(scan.next().split("")));
                if(!map.containsKey(set))
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
        List<Integer> listOfAmounts = map.values().stream().collect(Collectors.toList());
        Collections.sort(listOfAmounts, Integer::compareTo);
        return listOfAmounts;
    }

    public void printUniques() {
        for(Set s : map.keySet())
            System.out.println(map.get(s) + " => " + s);
    }


    public static void main(String[] args) {
        UniqueLetters uniqueLetters = new UniqueLetters(new File("src\\main\\resources\\words\\INPUT.txt"));
        uniqueLetters.countUniques();
        System.out.println(uniqueLetters.getAmounts());
        uniqueLetters.printUniques();
    }

}
