package com.hlj.test.entity;

public class Person {
    private Integer id;
    private String personName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public Person(String personName) {
        this.personName=personName;
    }
}
