package com.spring.microservice.student.model;

import javax.persistence.*;

@Entity
public class Dormitory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    public Dormitory() {
    }

    public Dormitory(String name) {
        this.name = name;
    }

    public long getId() {
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
        return "Dormitory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
