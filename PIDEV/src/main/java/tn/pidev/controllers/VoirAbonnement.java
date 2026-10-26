package tn.pidev.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.pidev.entities.Abonnement;
import tn.pidev.entities.Categorie;
import tn.pidev.entities.SalleDeSport;
import tn.pidev.entities.Utilisateur;
import tn.pidev.services.AbonnementServices;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.stream.Collectors;

import java.sql.SQLException;
import java.util.List;

public class VoirAbonnement {

    @FXML
    private TableView<Abonnement> tableViewAbonnements;
    @FXML
    private TableColumn<Abonnement, String> colNomClient;
    @FXML
    private TableColumn<Abonnement, String> colNomAbonnement;

    @FXML
    private TableColumn<Abonnement, String> colNomOffre;



    @FXML
    private TableColumn<Abonnement, String> colDateDebut;

    @FXML
    private TableColumn<Abonnement, String> colDateFin;

    @FXML
    private TableColumn<Abonnement, Double> colPrix;


    private SalleDeSport salle;
    private Utilisateur utilisateurConnecte;

    @FXML
    private TextField textFieldRecherche;

    @FXML
    private ComboBox<String> comboBoxCritere;

    private ObservableList<Abonnement> abonnementsList = FXCollections.observableArrayList();

    public void setSalle(SalleDeSport salle) {
        this.salle = salle;
    }

@FXML
public void rechercherAbonnements() {
    String critere = comboBoxCritere.getValue();
    String recherche = textFieldRecherche.getText().toLowerCase();

    ObservableList<Abonnement> abonnementsFiltres = FXCollections.observableArrayList();

    for (Abonnement abonnement : tableViewAbonnements.getItems()) {
        switch (critere) {
            case "Nom Offre":
                if (abonnement.getNomOffre().toLowerCase().contains(recherche)) {
                    abonnementsFiltres.add(abonnement);
                }
                break;
            case "Nom Client":
                if (abonnement.getNomClient().toLowerCase().contains(recherche)) {
                    abonnementsFiltres.add(abonnement);
                }
                break;
            default:
                abonnementsFiltres.add(abonnement);
                break;
        }
    }

    tableViewAbonnements.setItems(abonnementsFiltres);
}

    @FXML
    public void reinitialiserRecherche() {
        textFieldRecherche.clear();
        afficherAbonnements(null); // Rechargez tous les abonnements
    }
    public void afficherAbonnements(ActionEvent event) {
        try {
            // Récupérer l'utilisateur statique
//                recupererUtilisateurConnecte();
                AbonnementServices abonnementServices = new AbonnementServices();
                // Obtenez les abonnements de l'utilisateur statique pour la salle donnée
                List<Abonnement> abonnements = abonnementServices.showAllAbonnement1(salle);
            ObservableList<Abonnement> observableList = FXCollections.observableArrayList(abonnements);

                // Remplir les colonnes
            colNomClient.setCellValueFactory(new PropertyValueFactory<>("nomClient"));
            colNomAbonnement.setCellValueFactory(new PropertyValueFactory<>("nomAbonnement"));
                colNomOffre.setCellValueFactory(new PropertyValueFactory<>("nomOffre"));
                colDateDebut.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
                colDateFin.setCellValueFactory(new PropertyValueFactory<>("dateFin"));
                colPrix.setCellValueFactory(new PropertyValueFactory<>("prixOffre"));

                tableViewAbonnements.setItems(observableList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void retournerAOffres() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherOffre.fxml"));
            Parent root = loader.load();

            VoirOffres controller = loader.getController();
            controller.setSalle(salle);  // Assurez-vous que la salle est transmise

            Stage stage = (Stage) tableViewAbonnements.getScene().getWindow();
            stage.setTitle("Voir les Offres");
            stage.setScene(new Scene(root));

        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Une erreur est survenue lors du retour à la page des offres.");
            alert.showAndWait();
        }
    }


}
