package com.luxoft.reportingsystem.lambdas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.UnaryOperator;

public class LambdasTest {

    static class RecursiveTest {

        UnaryOperator<Integer> fact = i -> i == 0 ? 1 : i * this.fact.apply(i - 1);

    }

    public static void main(String[] args) {

        List<Person> persons = new ArrayList<>();
        persons.add(new Person(0, "Jill"));
        persons.add(new Person(1, "Bill"));
        persons.add(new Person(2, "Will"));

        Goer goer = (a) -> {
            int x = 0;
            for (int i = 0; i < a; i++)
                x += i;
            return x;
        };

        System.out.println(goer.go(10));

        RecursiveTest recursiveTest = new LambdasTest.RecursiveTest();
        System.out.println(recursiveTest.fact.apply(5));

        Collections.sort(persons, (p1, p2) -> p1.getName().compareTo(p2.getName()));


        persons.forEach(Person::sayHello);
    }

}
