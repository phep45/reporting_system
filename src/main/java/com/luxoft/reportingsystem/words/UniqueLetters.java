package com.luxoft.reportingsystem.words;

import java.io.File;
import java.util.*;

public class UniqueLetters {

    public static final String EMPTY_STR = "";
    private List<String> wordsList;

    public UniqueLetters(List<String> wordsList) {
        this.wordsList = wordsList;
    }

    public Map<Set<EntryPair>, Integer> countUniques() {
        Map<Set<EntryPair>, Integer> map = new HashMap<>();
        System.out.println(wordsList);
        wordsList.forEach(s -> {
            List<String> letters = Arrays.asList(s.split(EMPTY_STR));
            Set<EntryPair> set = new TreeSet<>();
            letters.forEach(letter -> {
                for (EntryPair oldPair : set) {
                    if (oldPair.getLetter().equals(letter)) {
                        oldPair.incrementAmount();
                        return;
                    }
                }
                set.add(new EntryPair(letter,1));
            });
            if(!map.containsKey(set))
                map.put(set, 1);
            else {
                Integer val = map.get(set);
                val++;
                map.put(set, val);
            }
        });
        return map;
    }

  /*  public Map<Set<String>, Integer> countUniques(File file) {
        Map<Set<String>, Integer> map = new HashMap<>();

        try (Scanner scan = new Scanner(new FileInputStream(file))) {
            while (scan.hasNext()) {
                Set<String> set = new TreeSet<>(Arrays.asList(scan.next().split("")));
                if (!map.containsKey(set))
                    map.put(set, 1);
                else {
                    Integer val = map.get(set);
                    val++;
                    map.put(set, val);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return map;
    }*/

   /* public List<Integer> getAmounts() {
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
    }*/


    public static void main(String[] args) {
        WordsCollector wordsCollector = new WordsCollector(new File("src\\main\\resources\\words\\test2.txt"));
        UniqueLetters uniqueLetters = new UniqueLetters(wordsCollector.collect());
        Map<Set<EntryPair>, Integer> map = uniqueLetters.countUniques();
        for (Set s : map.keySet())
            System.out.println(map.get(s) + " => " + s);
        System.out.println(map.keySet().size());
    }

}
