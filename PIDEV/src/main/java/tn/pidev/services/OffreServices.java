package tn.pidev.services;

import tn.pidev.entities.SalleDeSport;
import tn.pidev.entities.Offre;
import tn.pidev.utils.MyDatabase;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OffreServices implements CRUDOffre<Offre,SalleDeSport,List<String>> {

    private Connection cnx = MyDatabase.getInstance().getCnx();
    private Statement st ;
    private PreparedStatement ps ;

    @Override
    public int insert(Offre offre, SalleDeSport salle) throws SQLException {
        String req = "INSERT INTO `offre`(`idSalle`,`nomOffre`,`statut`,`description`,`prix`, `duree`, `categorie`) VALUES (?, ?, ?, ?, ?, ?,?)";
        ps = cnx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, salle.getIdSalle());
        ps.setString(2, offre.getNomOffre());
        ps.setString(3, offre.getStatut().name());
        ps.setString(4, offre.getDescription());
        ps.setDouble(5, offre.getPrix());
        ps.setInt(6, offre.getDuree());
        ps.setString(7, offre.getCategorie().name());

        int rowsInserted = ps.executeUpdate();

        // Récupérer l'ID généré de l'offre
        ResultSet rs = ps.getGeneratedKeys();
        int idOffre = -1;
        if (rs.next()) {
            idOffre = rs.getInt(1);
        }

        // Ajouter les images dans la table offre_images
        if (idOffre != -1 && offre.getImages() != null) {
            String imgReq = "INSERT INTO `offre_images`(`idOffre`, `imageUrl`) VALUES (?, ?)";
            PreparedStatement imgPs = cnx.prepareStatement(imgReq);
            for (String image : offre.getImages()) {
                imgPs.setInt(1, idOffre);
                imgPs.setString(2, image);
                imgPs.executeUpdate();
            }
        }

        return rowsInserted;
    }


    @Override
    public int update(Offre offre, List<String> newImages) throws SQLException {
        System.out.println("Update Offre " + offre);

        // Étape 1: Mise à jour des informations de l'offre (sans toucher aux images)
        String req = "UPDATE offre SET nomOffre = ?, statut = ?, description = ?, prix = ?, duree = ? , categorie = ? WHERE idOffre = ?";
        PreparedStatement pst = cnx.prepareStatement(req);
        pst.setString(1, offre.getNomOffre());
        pst.setString(2, offre.getStatut().name());
        pst.setString(3, offre.getDescription());
        pst.setDouble(4, offre.getPrix());
        pst.setInt(5, offre.getDuree());
        pst.setString(6, offre.getCategorie().name());
        pst.setInt(7, offre.getIdOffre());
        int rowsAffected = pst.executeUpdate();

        // Étape 2: Récupérer les anciennes images
        List<String> oldImages = new ArrayList<>();
        String selectImagesReq = "SELECT imageUrl FROM offre_images WHERE idOffre = ?";
        PreparedStatement selectImagesPst = cnx.prepareStatement(selectImagesReq);
        selectImagesPst.setInt(1, offre.getIdOffre());
        ResultSet rs = selectImagesPst.executeQuery();
        while (rs.next()) {
            oldImages.add(rs.getString("imageUrl"));
        }

        // Étape 3: Déterminer les images à supprimer et à ajouter
        List<String> imagesToDelete = new ArrayList<>(oldImages);
        imagesToDelete.removeAll(newImages); // Images qui existaient avant mais ne sont plus présentes
        List<String> imagesToAdd = new ArrayList<>(newImages);
        imagesToAdd.removeAll(oldImages); // Nouvelles images à ajouter

        // Étape 4: Supprimer les images obsolètes
        if (!imagesToDelete.isEmpty()) {
            String deleteImagesReq = "DELETE FROM offre_images WHERE idOffre = ? AND imageUrl = ?";
            PreparedStatement deleteImagePst = cnx.prepareStatement(deleteImagesReq);
            for (String imageUrl : imagesToDelete) {
                deleteImagePst.setInt(1, offre.getIdOffre());
                deleteImagePst.setString(2, imageUrl);
                deleteImagePst.executeUpdate();
            }
        }

        // Étape 5: Ajouter les nouvelles images
        if (!imagesToAdd.isEmpty()) {
            String insertImageReq = "INSERT INTO offre_images (idOffre, imageUrl) VALUES (?, ?)";
            PreparedStatement insertImagePst = cnx.prepareStatement(insertImageReq);
            for (String imageUrl : imagesToAdd) {
                insertImagePst.setInt(1, offre.getIdOffre());
                insertImagePst.setString(2, imageUrl);
                insertImagePst.executeUpdate();
            }
        }

        return rowsAffected;
    }



    @Override
    public List<Offre> showAll(SalleDeSport salle) throws SQLException {
        List<Offre> temp = new ArrayList<>();
        String req = "SELECT * FROM `offre` WHERE idSalle = ?";

        PreparedStatement pst = cnx.prepareStatement(req);
        pst.setInt(1, salle.getIdSalle());
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("idOffre");
            String nom = rs.getString("nomOffre");
            double prix = rs.getDouble("prix");
            String description = rs.getString("description");
            int duree = rs.getInt("duree");
            Offre.Statut statut = Offre.Statut.valueOf(rs.getString("statut"));

            // Récupérer les images associées
            String imgReq = "SELECT imageUrl FROM offre_images WHERE idOffre = ?";
            PreparedStatement imgPs = cnx.prepareStatement(imgReq);
            imgPs.setInt(1, id);
            ResultSet imgRs = imgPs.executeQuery();
            List<String> images = new ArrayList<>();
            while (imgRs.next()) {
                images.add(imgRs.getString("imageUrl"));
            }
            Offre.Categorie categorie = Offre.Categorie.valueOf(rs.getString("categorie"));  // Récupérer la catégorie
            Offre o = new Offre(id, nom, statut, description, prix, duree, images,categorie);
            temp.add(o);
        }

        return temp;
    }


    @Override
    public int delete(int id) throws SQLException {
        // Étape 1: Supprimer les images associées à l'offre
        String deleteImagesReq = "DELETE FROM offre_images WHERE idOffre = ?";
        PreparedStatement deleteImagesPst = cnx.prepareStatement(deleteImagesReq);
        deleteImagesPst.setInt(1, id);
        deleteImagesPst.executeUpdate();

        // Étape 2: Supprimer l'offre elle-même
        String deleteOfferReq = "DELETE FROM offre WHERE idOffre = ?";
        PreparedStatement deleteOfferPst = cnx.prepareStatement(deleteOfferReq);
        deleteOfferPst.setInt(1, id);

        return deleteOfferPst.executeUpdate(); // Retourne le nombre de lignes affectées
    }

    public int getDureeOffre(int idOffre) throws SQLException {
        String req = "SELECT duree FROM offre WHERE idOffre = ?";
        PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, idOffre);
            ResultSet rs =pst.executeQuery();
            rs.next();
            return rs.getInt("duree");
    }


}