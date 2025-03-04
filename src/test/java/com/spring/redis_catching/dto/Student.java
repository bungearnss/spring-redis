package com.spring.redis_catching.dto;

import java.util.List;

public class Student {

    private String name;
    private int age;
    private String city;
    private List<Integer> marks;

    public Student setName(String name) {
        this.name = name;
        return this;
    }

    public Student setAge(int age) {
        this.age = age;
        return this;
    }

    public Student setCity(String city) {
        this.city = city;
        return this;
    }

    public Student setMarks(List<Integer> marks) {
        this.marks = marks;
        return this;
    }
}
