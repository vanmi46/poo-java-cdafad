package com.exemple.entity;

public class Category implements Entity {

    //Attributs
    private int id;
    private String name;

    //Constructeurs
    public Category() {}
    public Category(String name)
    {
        this.name=name;
    }
    public Category(int id, String name)
    {
        this.id=id;
        this.name=name;
    }

    //Getters et Setters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //méthodes
    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
