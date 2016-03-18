package com.luxoft.reportingsystem.words;

import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class UniqueLettersTest {

    private UniqueLetters uniqueLetters;
    private List<Integer> outputForTest = Arrays.asList(3, 2, 2, 1, 1);

    @Test
    public void test() {
        uniqueLetters = new UniqueLetters(new File("src\\main\\resources\\words\\test.txt"));
        assertEquals(outputForTest, uniqueLetters.getAmounts());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowException() {
        uniqueLetters = new UniqueLetters(new File("asdfasfdssde"));
    }
}
