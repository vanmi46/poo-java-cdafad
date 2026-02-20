package com.exemple.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.exemple.database.Env.*;

public class Mysql {

    //Connexion à la BDD
    private final static Connection connexion;
    static {
        try {
            connexion = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static Connection getConnexion()
    {
        return connexion;
    }
}
