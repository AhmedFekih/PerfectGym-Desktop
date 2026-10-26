package tn.pidev.services;
import tn.pidev.entities.Utilisateur;
import tn.pidev.entities.Offre;
import tn.pidev.entities.Abonnement;
import tn.pidev.entities.SalleDeSport;
import tn.pidev.utils.MyDatabase;
import java.time.LocalDate;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AbonnementServices implements CRUDAbonnement<Abonnement,Utilisateur> {

    private Connection cnx = MyDatabase.getInstance().getCnx();
    private Statement st;
    private PreparedStatement ps;


    @Override
    public int insert(Abonnement abonnement) throws SQLException {

        String req = "INSERT INTO `abonnement`(`nomAbonnement`,`idClient`,`idOffre`, `dateDebut`, `dateFin`, `Status`)" +
                " VALUES (?, ?, ?, ?, ?, ?)";

        ps = cnx.prepareStatement(req);
        ps.setString(1, abonnement.getNomAbonnement());
        ps.setInt(2, abonnement.getUtilisateur().getCin());
        ps.setInt(3, abonnement.getOffre().getIdOffre());
        ps.setDate(4, Date.valueOf(abonnement.getDateDebut()));
        ps.setDate(5, Date.valueOf(abonnement.getDateFin()));
        ps.setString(6, abonnement.getStatus());

        return ps.executeUpdate();
    }

    @Override
    public int update(Abonnement abonnement) throws SQLException {
        String req = "UPDATE `abonnement` SET `nomAbonnement` = ?, `dateDebut` = ?, `dateFin` = ? WHERE `idAbonnement` = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1, abonnement.getNomAbonnement());
        ps.setDate(2, java.sql.Date.valueOf(abonnement.getDateDebut()));
        ps.setDate(3, java.sql.Date.valueOf(abonnement.getDateFin()));
        ps.setInt(4, abonnement.getIdAbonnement());
        return ps.executeUpdate();
    }

    public int getDureeOffre(int idOffre) throws SQLException {
        String req = "SELECT duree FROM offre WHERE idOffre = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, idOffre);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt("duree");
    }

    @Override
    public int delete(int idAbonnement) throws SQLException {
        String req = "DELETE FROM abonnement WHERE idAbonnement = " + idAbonnement;
        Statement st = cnx.createStatement();
        return st.executeUpdate(req);
    }


    @Override
    public List<Abonnement> showAll(Utilisateur u) throws SQLException {
        List<Abonnement> temp = new ArrayList<>();

        String req = "SELECT a.*, o.nomOffre,o.duree FROM abonnement a JOIN offre o ON a.idOffre = o.idOffre WHERE a.idClient = " + u.getCin();

        st = cnx.createStatement();

        ResultSet rs = st.executeQuery(req);

        while (rs.next()) {
            Abonnement a = new Abonnement();
            a.setNomAbonnement(rs.getString("nomAbonnement"));
            String nomOffre = rs.getString("nomOffre");
            a.setNomOffre(nomOffre);
            a.setDateDebut(rs.getDate("dateDebut").toLocalDate(), rs.getInt("duree"));
            a.setDateFin(rs.getDate("dateFin").toLocalDate());
            a.setStatus(rs.getString("Status"));

            temp.add(a);
        }

        return temp;
    }

    public List<Abonnement> showAllAbonnement(SalleDeSport s, Utilisateur u) throws SQLException {
        List<Abonnement> temp = new ArrayList<>();
        String req = "SELECT a.*,o.nomOffre,o.prix AS prixOffre,s.nom " +
                "FROM abonnement a " +
                "JOIN offre o ON a.idOffre = o.idOffre " +
                "JOIN salleDesport s ON o.idSalle = s.idSalle " +
                "WHERE a.idClient = ? AND s.idSalle = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, u.getCin());
        ps.setInt(2, s.getIdSalle());
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Abonnement a = new Abonnement();
            a.setIdAbonnement(rs.getInt("idAbonnement"));
            a.setNomAbonnement(rs.getString("nomAbonnement"));
            String nomOffre = rs.getString("nomOffre");
            a.setIdOffre(rs.getInt("idOffre"));
            a.setNomOffre(nomOffre);
            a.setNomSalle(rs.getString("nom"));
            a.settDateDebut(rs.getDate("dateDebut").toLocalDate());
            a.setDateFin(rs.getDate("dateFin").toLocalDate());
            a.setStatus(rs.getString("Status"));
            a.setPrixOffre(rs.getDouble("prixOffre"));

            temp.add(a);
        }
        return temp;
    }

    public List<Abonnement> showAllAbonnement1(SalleDeSport s) throws SQLException {
        List<Abonnement> temp = new ArrayList<>();
        String req = "SELECT a.*, o.nomOffre,o.prix AS prixOffre, u.nom AS nomClient " +
                "FROM abonnement a " +
                "JOIN offre o ON a.idOffre = o.idOffre " +
                "JOIN salleDesport s ON o.idSalle = s.idSalle " +
                "JOIN utilisateur u ON a.idClient = u.cin " +
                "WHERE a.status = 'payé' AND s.idSalle = ? AND u.categorie = 'Client'";

        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, s.getIdSalle());
        System.out.println(s.getIdSalle());

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Abonnement a = new Abonnement();
            a.setIdAbonnement(rs.getInt("idAbonnement"));
            a.setNomClient(rs.getString("nomClient"));
            a.setNomAbonnement(rs.getString("nomAbonnement"));
            a.setNomOffre(rs.getString("nomOffre"));
            a.setIdOffre(rs.getInt("idOffre"));
            a.settDateDebut(rs.getDate("dateDebut").toLocalDate());
            a.setDateFin(rs.getDate("dateFin").toLocalDate());
            a.setStatus(rs.getString("status"));
            a.setPrixOffre(rs.getDouble("prixOffre"));
            System.out.println(a);

            temp.add(a);
        }
        return temp;
    }
    public void updateStatus(int idAbonnement, String nouveauStatut) throws SQLException {
        String query = "UPDATE abonnement SET status = ? WHERE idAbonnement = ?";
        PreparedStatement ps = cnx.prepareStatement(query);
            ps.setString(1, nouveauStatut);
            ps.setInt(2, idAbonnement);
            ps.executeUpdate();
        }
    }

