package com.luxoft.wordscounter;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class UniqueLettersTest {

    private UniqueLetters uniqueLetters;
    private List<Integer> outputForTest = Arrays.asList(3, 2, 2, 1, 1);
    private List<Integer> outputForTest2 = Arrays.asList(12, 2, 2, 2, 2, 2, 1, 1, 1, 1);

   /* @Test
    public void testFileTest() {
        uniqueLetters = new UniqueLetters(new File("src\\main\\resources\\words\\test.txt"));
        assertEquals(outputForTest, uniqueLetters.getAmounts());
    }

    @Test
    public void testFileTest2() {
        uniqueLetters = new UniqueLetters(new File("src\\main\\resources\\words\\test2.txt"));
        assertEquals(outputForTest2, uniqueLetters.getAmounts());
    }*/

//    @Test(expected = IllegalArgumentException.class)
//    public void shouldThrowException() {
//        uniqueLetters = new UniqueLetters(new File("asdfasfdssde"));
//    }

//    OnPoint point = new OnPoint();
//
//    @Test
//    public void test() {
//        point.setWordsCollector(new WordsCollector() {
//            @Override
//            public List<String> collect() {
//                return Arrays.asList("asdf", "afds");
//            }
//        });

//    }


}
