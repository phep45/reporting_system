package com.luxoft.reportingsystem.words;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

public class UniqueLettersTest {

    private UniqueLetters uniqueLetters;

    @Test
    public void test() {
        uniqueLetters = new UniqueLetters(new File("src\\main\\resources\\words\\test2.txt"));
        uniqueLetters.countUniques();

    }

}
