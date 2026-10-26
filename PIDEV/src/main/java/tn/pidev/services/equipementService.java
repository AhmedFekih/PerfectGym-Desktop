package tn.pidev.services;
import tn.pidev.entities.Equipement;
import tn.pidev.entities.SalleDeSport;
import tn.pidev.utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class equipementService implements CRUD2<Equipement> {
    private Connection cnx = MyDatabase.getInstance().getCnx();
    private Statement st ;
    private PreparedStatement ps ;

    @Override
    public int insert(Equipement equipe) throws SQLException {

        String req = "INSERT INTO `equipement`(`\ref`, `nom`, `quantite`, `statut`,`idSalle `,`image `) " +
                "VALUES ('" + equipe.getNom() + "', '" + equipe.getNom() + "', '" + equipe.getQuantite()+ "', '" + equipe.getStatut() +"', '" + equipe.getImage() + "')";

        st = cnx.createStatement();

        return st.executeUpdate(req);
    }


    public SalleDeSport getSalleById(int idSalle) throws SQLException {
        String query = "SELECT * FROM `salledesport` WHERE idSalle = ?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setInt(1, idSalle);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // Mapping the result set to a Planning object
                SalleDeSport planning = new SalleDeSport();
                planning.setIdSalle(resultSet.getInt("idSalle"));
                planning.setNom(resultSet.getString("nom"));
                planning.setLocalisation(resultSet.getString("localisation"));
                planning.setStatut(resultSet.getString("statut"));
                return planning;  // Return the populated Planning object
            }
        }
        return null;  // Return null if no planning is found with the given ID
    }
    public  int insert1(Equipement equipe) throws SQLException {

        // Ajout de la colonne `image` dans la requête SQL
        String req = "INSERT INTO `equipement` (`ref`, `nom`, `quantite`, `statut`,`idSalle`,`image`) VALUES (?, ?, ?, ?,?,?)";

        ps = cnx.prepareStatement(req);

        ps.setInt(1, equipe.getRef());
        ps.setString(2, equipe.getNom());
        ps.setInt(3, equipe.getQuantite());  // On s'assure que l'image est correctement utilisée
        ps.setString(4, equipe.getStatut());
        ps.setInt(5, equipe.getIdSalle());
        ps.setString(6, equipe.getImage());
        return ps.executeUpdate();
    }
    public List<Integer> getAllSalleDeSportIds() throws SQLException {
        List<Integer> salleIds = new ArrayList<>();
        String req = "SELECT idSalle FROM `salledesport`"; // Only fetch the idSalle
        try (Statement st = cnx.createStatement(); ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                int idSalle = rs.getInt("idSalle");
                salleIds.add(idSalle);
            }
        }

        return salleIds;
    }

    @Override
    public void update(Equipement equipe) throws SQLException {
        // Corrected SQL query: remove the extra `,` before `WHERE`
        String req = "UPDATE `equipement` SET `nom`=?, `quantite`=?, `statut`=? WHERE `ref`=?";

        try {
            // Prepare the statement with the updated query
            PreparedStatement ps = cnx.prepareStatement(req);

            // Set the parameters for the query
            ps.setString(1, equipe.getNom());  // Set the name
            ps.setInt(2, equipe.getQuantite());  // Set the quantity
            ps.setString(3, equipe.getStatut());  // Set the status
            ps.setInt(4, equipe.getRef());  // Use `ref` to identify the row to update

            // Execute the update
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error during update: " + e.getMessage());
            throw e;  // Re-throw the exception for proper handling
        }
    }

    public Equipement geEquipementByRef(int ref) throws SQLException {
        String query = "SELECT * FROM `equipement` WHERE ref = ?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setInt(1, ref);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // Mapping the result set to a Planning object
                Equipement planning = new Equipement();
                planning.setRef(resultSet.getInt("ref"));
                planning.setNom(resultSet.getString("nom"));
                planning.setQuantite(Integer.parseInt(resultSet.getString("quantite")));
                planning.setStatut(resultSet.getString("statut"));

                return planning;  // Return the populated Planning object
            }
        }
        return null;  // Return null if no planning is found with the given ID
    }

    @Override
    public void delete(int id) throws SQLException{
        String req = "DELETE FROM `equipement` WHERE ref= ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Equipement> afficher(int idSalle) {
        List<Equipement> equipes = new ArrayList<>();
        String req = "SELECT * FROM equipement WHERE idSalle = ?";

        try {
            // Use PreparedStatement instead of Statement
            PreparedStatement ps = cnx.prepareStatement(req);
            // Set the idSalle parameter before executing the query
            ps.setInt(1, idSalle);
            ResultSet rs = ps.executeQuery();

            // Iterate over the result set
            while (rs.next()) {
                int ref = rs.getInt("ref");
                String nomEquipemenet = rs.getString("nom");
                int quantite = rs.getInt("quantite");
                String statut = rs.getString("statut");
                String image = rs.getString("image");

                // Create the Equipement object and set its values
                Equipement equipe = new Equipement(nomEquipemenet, quantite, statut, idSalle,image);
                equipe.setRef(ref); // Set the ref

                // Add the Equipement object to the list
                equipes.add(equipe);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return equipes;
    }
    public List<Equipement> trierParNomEquipement(int idSalle) throws SQLException {
        List<Equipement> equipes = new ArrayList<>();
        String req = "SELECT * FROM equipement WHERE idSalle = ? ORDER BY nom ASC"; // Alphabetical order

        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, idSalle);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int ref = rs.getInt("ref");
                String nomEquipement = rs.getString("nom");
                int quantite = rs.getInt("quantite");
                String statut = rs.getString("statut");
                String image = rs.getString("image");

                Equipement equipe = new Equipement(nomEquipement, quantite, statut, idSalle,image);
                equipe.setRef(ref); // Set the ref

                equipes.add(equipe); // Add to list
            }
        }
        return equipes;
    }
    public List<Equipement> rechercherParNom(String nom, int idSalle) throws SQLException {
        List<Equipement> equipements = new ArrayList<>();

        String query = "SELECT * FROM equipement WHERE nom LIKE ? AND idSalle = ?";
        PreparedStatement stmt = cnx.prepareStatement(query);
        stmt.setString(1, "%" + nom + "%");
        stmt.setInt(2, idSalle);

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Equipement equipement = new Equipement();
            equipement.setRef(rs.getInt("ref"));
            equipement.setNom(rs.getString("nom"));
            equipement.setStatut(rs.getString("statut"));
            equipements.add(equipement);
        }

        return equipements;
    }

}
