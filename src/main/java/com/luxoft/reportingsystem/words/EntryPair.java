package com.luxoft.reportingsystem.words;

public class EntryPair {

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
}
