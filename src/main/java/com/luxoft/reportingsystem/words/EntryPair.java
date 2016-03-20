package com.luxoft.reportingsystem.words;

public class EntryPair implements Comparable<EntryPair> {

    private int amount;
    private String letter;

    public EntryPair(String letter, int amount) {
        this.setLetter(letter);
        this.setAmount(amount);
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public void incrementAmount() { amount++; }

    @Override
    public String toString() {
        return letter + ":" + amount;
    }

    @Override
    public int compareTo(EntryPair o) {
        return letter.compareTo(o.getLetter());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof EntryPair))
            return false;

        EntryPair entryPair = (EntryPair) o;

        if (amount != entryPair.amount)
            return false;
        return letter.equals(entryPair.letter);

    }

    @Override
    public int hashCode() {
        int result = amount;
        result = 31 * result + letter.hashCode();
        return result;
    }
}
