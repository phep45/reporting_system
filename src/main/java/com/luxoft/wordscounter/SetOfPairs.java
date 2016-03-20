package com.luxoft.wordscounter;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class SetOfPairs implements Comparable<SetOfPairs> {

    private Set<EntryPair> entryPairSet = new TreeSet<>();

    public void insert(String letter) {
        for (EntryPair oldPair : entryPairSet) {
            if (oldPair.getLetter().equals(letter)) {
                oldPair.incrementAmount();
                return;
            }
        }
        entryPairSet.add(new EntryPair(letter, 1));
    }

    public Set<EntryPair> getEntryPairSet() {
        return entryPairSet;
    }

    @Override
    public int compareTo(SetOfPairs other) {
        if(this.entryPairSet.equals(other.entryPairSet))
            return 0;
        if(entryPairSet.size() > other.entryPairSet.size())
            return 1;
        else if(entryPairSet.size() < other.entryPairSet.size())
            return -1;
        else {
            for(EntryPair pair : this.entryPairSet) {
                for(EntryPair otherPair : other.entryPairSet) {
                    if(pair.compareTo(otherPair) != 0)
                        return pair.compareTo(otherPair);
                }
            }
            return 0;
        }
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
