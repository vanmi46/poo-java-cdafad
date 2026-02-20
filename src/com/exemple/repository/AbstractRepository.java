package com.exemple.repository;

import com.exemple.database.Mysql;
import com.exemple.entity.Category;
import com.exemple.entity.Entity;

import java.sql.Connection;
import java.util.ArrayList;

public abstract class AbstractRepository<T extends Entity> {

    protected Connection connection;

    public AbstractRepository() {
        this.connection = Mysql.getConnexion();
    }

    public abstract T save(T entity);
    public abstract T find(int id);
    public abstract ArrayList<T> findAll();
}
