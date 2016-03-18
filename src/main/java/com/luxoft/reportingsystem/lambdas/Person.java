package com.luxoft.reportingsystem.lambdas;

public class Person {

    private int id;
    private String name;

    public Person() {
        this(0,null);
    }

    public Person(int id, String name) {
        this.setId(id);
        this.setName(name);
    }

    public void sayHello() {
        System.out.println("Hi, my name is " + name);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return id + ": " + name;
    }
}
