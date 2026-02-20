package com.exemple.repository;

import com.exemple.entity.Category;
import com.exemple.entity.Manufacturer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ManufacturerRepository extends AbstractRepository<Manufacturer> {

    //Constructeur
    public ManufacturerRepository() {
        super();
    }

    //Méthodes
    //Méthode pour ajouter une categorie
    public Manufacturer save(Manufacturer manufacturer)
    {
        try {
            //1 Ecrire la requête
            String sql = "INSERT INTO manufacturer (`name`) VALUE (?)";
            //2 préparer la requête
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //3 Assigner le paramètre
            stmt.setString(1, manufacturer.getName());
            //4 Exécuter la requête
            stmt.executeUpdate();
            //récupération de l'id (Resultset)
            ResultSet rs = stmt.getGeneratedKeys();
            //récupération de l'ID
            if (rs.next()) {
                manufacturer = new Manufacturer(
                    rs.getInt("id"),
                    manufacturer.getName()
                );
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return manufacturer;
    }

    //méthode pour récuperer un categorie par son id
    public Manufacturer find(int id)
    {
        Manufacturer manufacturer = null;
        try {
            //1 Ecrire la requête
            String sql = "SELECT id, `name` FROM manufacturer WHERE id = ?";
            //2 préparer la requête
            PreparedStatement stmt = connection.prepareStatement(sql);
            //3 Assigner le paramètre
            stmt.setInt(1, id);
            //4 Exécuter la requête
            ResultSet rs = stmt.executeQuery();
            //récupérer le resultat de la requête
            if (rs.next()) {
                manufacturer = new Manufacturer(rs.getInt("id"), rs.getString("name"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return manufacturer;
    }

    //Méthode pour retourner la liste de toutes les categories (ArrayList)
    public ArrayList<Manufacturer> findAll()
    {
        ArrayList<Manufacturer> manufacturers = new ArrayList<>();
        try {
            //1 Ecrire la requête
            String sql = "SELECT id, `name` FROM category";
            //2 préparer la requête
            PreparedStatement stmt = connection.prepareStatement(sql);
            //4 Exécuter la requête
            ResultSet rs = stmt.executeQuery();
            //récupérer le resultat de la requête
            while (rs.next()) {
                Manufacturer manufacturer = new Manufacturer(rs.getInt("id"), rs.getString("name"));
                manufacturers.add(manufacturer);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return manufacturers;
    }
}
