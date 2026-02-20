package com.exemple.repository;

import com.exemple.database.Mysql;
import com.exemple.entity.Category;
import com.exemple.entity.Game;
import com.exemple.entity.Manufacturer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class GameRepository {
    //Attribut
    private Connection connection;

    //Constructeur
    public GameRepository() {
        this.connection = Mysql.getConnexion();
    }

    //Méthodes
    //Ajout un jeu sans les categories
    private Game saveGame(Game game)
    {
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

    //ajout des categories à un Jeu
    private Game saveCategoriesToGame(Game game)
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

    //Ajouter un jeu avec les categories
    public Game save(Game game) {
        Game newgame = null;
        try {
            //Ajout du game (table game)
            newgame = this.saveGame(game);
            //Ajout des categories (table game_category)
            this.saveCategoriesToGame(newgame);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return newgame;
    }

    //récupérer le jeu sans categories
    private Game findGameById(int id) {
        Game game = null;
        try {
            //1 Ecrire la requête
            String sql = "SELECT g.id, g.title, g.description, d.publish_at, g.manufacturer_id, m.name FROM game" +
                    "INNER JOIN manufacturer AS m ON g.manufacturer_id = m.id WHERE g.id = ?";
            //2 Préparer la requête
            PreparedStatement ps = connection.prepareStatement(sql);
            //3 Assigner les paramètres
            ps.setInt(1, id);
            //4 Récupérer le resultat
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Manufacturer manufacturer = new Manufacturer(rs.getInt("manufacturer_id"), rs.getString("name"));
                game = new Game(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getDate("publish_at"),
                        manufacturer
                );
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return game;
    }

    //récupérer les categories d'un jeu
    private ArrayList<Category> findAllCategoriesWithinGame(Game game) {
        ArrayList<Category> categories = new ArrayList<>();
        try {
            //1 Ecrire la requête
            String sql = "SELECT c.id, c.name FROM category AS c\n" +
                    "INNER JOIN game_category AS gc ON c.id = gc.category_id\n" +
                    "WHERE gc.game_id = ?";
            //2 Préparer la requête
            PreparedStatement ps = connection.prepareStatement(sql);
            //3 Assigner les paramètres
            ps.setInt(1, game.getId());
            //4 Récupérer le resultat
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Category category = new Category(rs.getInt("id"), rs.getString("name"));
                categories.add(category);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return categories;
    }

    // récupérer le jeu avec ces composantes par son ID (game + + manufacturer + categories)
    public Game find(int id)
    {
        Game game = null;
        try {
            //récupérer le Jeu
            game = this.findGameById(id);
            //récupérer la liste des categories
            ArrayList<Category> categories = this.findAllCategoriesWithinGame(game);
            //assignation des categories
            for(Category category : categories) {
                game.addCategory(category);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return game;
    }

    //Méthode pour récupérer une ArraList de Game sans les categories
    private ArrayList<Game> findAllGamesWithoutCategories()
    {
        ArrayList<Game> games = new ArrayList<>();
        try {
            //1 Ecrire la requête
            String sql = "SELECT g.id, g.title, g.description, g.publish_at, g.manufacturer_id, m.name FROM game AS g" +
                    " INNER JOIN manufacturer AS m ON g.manufacturer_id = m.id";
            //2 Préparation de la requête
            PreparedStatement ps = connection.prepareStatement(sql,  Statement.RETURN_GENERATED_KEYS);
            //3 Récupération du resultat
            ResultSet rs = ps.executeQuery();
            //4 Construction de l'ArrayList<Game>
            while(rs.next()) {
                //Créer un objet Jeu
                Game game = new Game(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getDate("publish_at"),
                        new Manufacturer(rs.getInt("manufacturer_id"), rs.getString("name"))
                );
                //Ajout à l'ArrayList
                games.add(game);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return  games;
    }

    //Méthode pour retourner la liste de tous les jeux (avec le manufacturer + les categories)
    public ArrayList<Game> findAll()
    {
        ArrayList<Game> games = new ArrayList<>();
        try {
            //récupération des jeux
            games = this.findAllGamesWithoutCategories();
            //Parcours des jeux
            for(Game game : games) {
                //parcours des categories
                for(Category category : this.findAllCategoriesWithinGame(game) ) {
                    //assignation des categories
                    game.addCategory(category);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return games;
    }
}
