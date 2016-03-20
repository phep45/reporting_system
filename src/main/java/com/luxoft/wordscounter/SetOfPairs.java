package com.luxoft.wordscounter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class SetOfPairs implements Comparable<SetOfPairs> {
    private static final Logger log = LoggerFactory.getLogger(SetOfPairs.class);
    private Set<EntryPair> entryPairSet = new TreeSet<>();

    public void insert(String letter) {
        for (EntryPair oldPair : entryPairSet) {
            if (oldPair.getLetter().equals(letter)) {
                log.trace("Pair {} exists; incrementing amount", oldPair);
                oldPair.incrementAmount();
                return;
            }
        }
        log.trace("Creating new pair: {}:1", letter);
        entryPairSet.add(new EntryPair(letter, 1));
    }

    public Set<EntryPair> getEntryPairSet() {
        return entryPairSet;
    }

    @Override
    public int compareTo(SetOfPairs other) {
        if(this.entryPairSet.equals(other.getEntryPairSet()))
            return 0;
        if(entryPairSet.size() > other.getEntryPairSet().size())
            return 1;
        else if(entryPairSet.size() < other.getEntryPairSet().size())
            return -1;
        else {
            return compareSameSizeSets(other);
        }
    }

    private int compareSameSizeSets(SetOfPairs other) {
        List<EntryPair> thisSetAsList = new ArrayList<>(entryPairSet);
        List<EntryPair> otherSetAsList = new ArrayList<>(other.getEntryPairSet());

        Collections.sort(thisSetAsList);
        Collections.sort(otherSetAsList);

        for(int i = 0; i < thisSetAsList.size(); i++) {
            if(thisSetAsList.get(i).compareTo(otherSetAsList.get(i)) != 0)
                return thisSetAsList.get(i).compareTo(otherSetAsList.get(i));
        }
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        SetOfPairs that = (SetOfPairs) o;

        return entryPairSet != null ? entryPairSet.equals(that.entryPairSet) : that.entryPairSet == null;

    }

    @Override
    public int hashCode() {
        return entryPairSet != null ? entryPairSet.hashCode() : 0;
    }

    @Override
    public String toString() {
        return entryPairSet.toString();
    }
}
