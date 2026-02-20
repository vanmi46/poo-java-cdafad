package com.exemple.repository;

import com.exemple.database.Mysql;
import com.exemple.entity.Category;
import com.exemple.entity.Game;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class GameRepository {
    //Attribut
    private Connection connection;

    //Constructeur
    public GameRepository() {
        this.connection = Mysql.getConnexion();
    }

    //Méthodes

    public Game save(Game game) {
        try {
            //1 écrire la requête SQL
            String sql = "INSERT INTO game(title, `description`,publish_at, manufacturer_id ) VALUE(?,?,?,?)";
            //2 Préparer la requête
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //3 Assigner les paramètres
            ps.setString(1, game.getTitle());
            ps.setString(2, game.getDescription());
            ps.setDate(3, new java.sql.Date(game.getPublishAt().getTime()));
            ps.setInt(4, game.getManufacturer().getId());
            //4 Exécuter la requête
            ps.executeUpdate();
            //5 Récupérer l'ID du jeu
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                game = new Game(
                        rs.getInt("id"),
                        game.getTitle(),
                        game.getDescription(),
                        game.getPublishAt(),
                        game.getManufacturer()
                );
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return game;
    }

    public Game saveCategoriesToGame(Game game)
    {
        try {
            for(Category category : game.getCategories()) {
                //1 Ecrire la requête SQL
                String sql = "INSERT INTO game_category (game_id, category_id) VALUE (?,?)";
                //2 Préparer la requête
                PreparedStatement ps = connection.prepareStatement(sql);
                //3 Assigner les paramètres
                ps.setInt(1, game.getId());
                ps.setInt(2, category.getId());
                //4 Exécuter la requête
                ps.executeUpdate();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return game;
    }

    public Game saveGameWithCategory(Game game) {
        Game newgame = null;
        try {
            //Ajout du game (table game)
            newgame = this.save(game);
            //Ajout des categories (table game_category)
            this.saveCategoriesToGame(newgame);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return newgame;
    }
}
