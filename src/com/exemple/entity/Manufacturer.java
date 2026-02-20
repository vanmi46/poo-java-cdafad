package com.exemple.entity;

public class Manufacturer implements Entity{
    //Attributs
    private int id;
    private String name;

    //Constructeurs
    public Manufacturer() {}
    public Manufacturer(String name)
    {
        this.name=name;
    }
    public Manufacturer(int id, String name)
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
        return "Manufacturer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
