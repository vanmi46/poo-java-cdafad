package com.exemple.entity;

import java.sql.Date;
import java.util.ArrayList;

public class Game implements Entity {
    //Attributs
    private int id;
    private String title;
    private String description;
    private Date publishAt;
    private Manufacturer manufacturer;
    private ArrayList<Category> categories = new ArrayList<>();

    //Constructeurs
    public Game(){}

    public Game(String title, String description, Date publishAt, Manufacturer manufacturer) {
        this.title = title;
        this.description = description;
        this.publishAt = publishAt;
        this.manufacturer = manufacturer;
    }

    public Game(int id, String title, String description, Date publishAt, Manufacturer manufacturer) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.publishAt = publishAt;
        this.manufacturer = manufacturer;
    }

    //Getters et Setters

    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPublishAt() {
        return this.publishAt;
    }

    public void setPublishAt(Date publishAt) {
        this.publishAt = publishAt;
    }

    public Manufacturer getManufacturer() {
        return this.manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public ArrayList<Category> getCategories() {
        return this.categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }

    public void addCategory(Category category) {
        this.categories.add(category);
    }

    public void removeCategory(Category category) {
        this.categories.remove(category);
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", publishAt=" + publishAt +
                ", manufacturer=" + manufacturer +
                ", categories=" + categories +
                '}';
    }
}
