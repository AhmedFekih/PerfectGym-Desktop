package tn.pidev.services;

import tn.pidev.entities.SalleDeSport;
import tn.pidev.entities.Utilisateur;
import tn.pidev.utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class salleDeSportService implements CRUD<SalleDeSport,Utilisateur> {

    private Connection cnx = MyDatabase.getInstance().getCnx();
    private Statement st ;
    private PreparedStatement ps ;



    public  int insert1(SalleDeSport salle, Utilisateur u1) throws SQLException {

        // Ajout de la colonne `image` dans la requête SQL
        String req = "INSERT INTO `salledesport` (`nom`, `localisation`, `image`, `statut`, `idUser`) VALUES (?, ?, ?, ?, ?)";
        ps = cnx.prepareStatement(req);
        ps.setString(1, salle.getNom());
        ps.setString(2, salle.getLocalisation());
        ps.setString(3, salle.getImage());
        ps.setString(4, salle.getStatut());
        ps.setInt(5, u1.getCin());

        return ps.executeUpdate();
    }
    public List<Integer> getAlluserIds() throws SQLException {
        List<Integer> userIds = new ArrayList<>();
        String req = "SELECT cin FROM `utilisateur`";

        try (Statement st = cnx.createStatement(); ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                int idSalle = rs.getInt("cin");
                userIds.add(idSalle);
            }
        }

        return userIds;
    }

    @Override
    public void delete(int id) throws SQLException{
        String req = "DELETE FROM `salledesport` WHERE idSalle= ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
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

    @Override
    public List<SalleDeSport> afficher() {
        List<SalleDeSport> plans = new ArrayList<>();
        String req = "SELECT * FROM `salledesport`";
        try {
            Statement statement = cnx.createStatement();
            ResultSet rs = statement.executeQuery(req);
            while (rs.next()) {
                // Fetch id from the result set
                int id = rs.getInt("idSalle");
                String nomSalle = rs.getString("nom");
                String localisation = rs.getString("localisation");
                String image = rs.getString("image");
                String statut = rs.getString("statut");
                int idUser = rs.getInt("idUser");
                SalleDeSport plan = new SalleDeSport(nomSalle, localisation,image, statut);
                plan.setIdSalle(id);
                plans.add(plan);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return plans;
    }
    @Override
    public void update(SalleDeSport salle) throws SQLException{
        String req = "UPDATE `salledesport` SET `nom`=?,`localisation`=?,`statut`=? WHERE idSalle= ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, salle.getNom());
            ps.setString(2, salle.getLocalisation());
            ps.setString(3, salle.getStatut());
            ps.setInt(4, salle.getIdSalle());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
    @Override
    public List<SalleDeSport> afficher2() {
        List<SalleDeSport> salles = new ArrayList<>();
        String req = "SELECT * FROM `salledesport`";

        try {
            Statement statement = cnx.createStatement();
            ResultSet rs = statement.executeQuery(req);

            while (rs.next()) {
                // Fetch id from the result set
                int id = rs.getInt("idSalle");
                String nomSalle = rs.getString("nom");
                String localisation = rs.getString("localisation");
                String image = rs.getString("image");
                String statut = rs.getString("statut");
                int idUser = rs.getInt("idUser");

                SalleDeSport salle = new SalleDeSport(nomSalle, localisation, image, statut);
                salle.setIdSalle(id);
                salles.add(salle);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        // Sort the list alphabetically by name before returning it
        salles.sort((salle1, salle2) -> salle1.getNom().compareToIgnoreCase(salle2.getNom()));

        return salles;
    }

    public List<SalleDeSport> searchByMultipleLocations(String[] locations) throws SQLException {
        {
            List<SalleDeSport> result = new ArrayList<>();

            // Prepare the base query
            StringBuilder query = new StringBuilder("SELECT * FROM salledesport WHERE ");

            // Add conditions for each location
            for (int i = 0; i < locations.length; i++) {
                if (locations[i] != null && !locations[i].trim().isEmpty()) {  // Only add condition if location is not empty
                    query.append("localisation LIKE ?");
                    if (i < locations.length - 1) {
                        query.append(" OR ");  // Use OR between conditions
                    }
                }
            }

            // Create the prepared statement
            try (PreparedStatement pstmt = cnx.prepareStatement(query.toString())) {
                // Set the parameters for each location
                int paramIndex = 1;
                for (String location : locations) {
                    if (location != null && !location.trim().isEmpty()) {
                        pstmt.setString(paramIndex++, "%" + location.trim() + "%");  // Use "%" for partial matching
                    }
                }

                // Execute the query
                ResultSet rs = pstmt.executeQuery();

                // Process the result set
                while (rs.next()) {
                    int id = rs.getInt("idSalle");
                    String nomSalle = rs.getString("nom");
                    String localisation = rs.getString("localisation");
                    String image = rs.getString("image");
                    String statut = rs.getString("statut");

                    SalleDeSport salle = new SalleDeSport(nomSalle, localisation, image, statut);
                    salle.setIdSalle(id);
                    result.add(salle);
                }
            } catch (SQLException e) {
                System.out.println("Error during SQL query execution: " + e.getMessage());
            }

            return result;
        }

}}
