package tn.pidev.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.pidev.entities.Abonnement;
import tn.pidev.services.OffreServices;
import tn.pidev.services.AbonnementServices;
import javafx.scene.control.DateCell;
import javafx.util.Callback;


import java.sql.SQLException;
import java.time.LocalDate;

public class ModifierAbonnement {

    @FXML
    private TextField txtNomAbonnement;

    @FXML
    private DatePicker datePickerDebut;

    @FXML
    private DatePicker datePickerFin;

    private Abonnement abonnement;
    private Stage stage;
    private VoirAbonnementClient voirAbonnementController;

    public void setVoirAbonnementController(VoirAbonnementClient controller) {
        this.voirAbonnementController = controller;
    }

    public void setAbonnement(Abonnement abonnement) {
        this.abonnement = abonnement;
        remplirChamps();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void remplirChamps() {
        txtNomAbonnement.setText(abonnement.getNomAbonnement());
        datePickerDebut.setValue(abonnement.getDateDebut());
        datePickerFin.setDisable(true);
        datePickerDebut.setDayCellFactory(picker -> new javafx.scene.control.DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (item.isBefore(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #d3d3d3;"); // Facultatif, pour une indication visuelle
                }
            }
        });
        calculerDateFin(abonnement.getDateDebut());

        datePickerDebut.valueProperty().addListener((observable, oldValue, newValue) -> calculerDateFin(newValue));
    }


    private void calculerDateFin(LocalDate dateDebut) {
        try {
            if (dateDebut != null) {
                int idOffre = abonnement.getIdOffre();
                OffreServices offreservice = new OffreServices();
                int dureeOffre = offreservice.getDureeOffre(idOffre);
                LocalDate dateFin = dateDebut.plusDays(dureeOffre);
                datePickerFin.setValue(dateFin);
            } else {
                datePickerFin.setValue(null);

            }
        }
     catch (SQLException e) {
        e.printStackTrace();
    }
    }

    @FXML
    private void enregistrerModifications() {
        String nouveauNom = txtNomAbonnement.getText();
        LocalDate nouvelleDateDebut = datePickerDebut.getValue();



        if (nouveauNom.isEmpty() || nouvelleDateDebut == null) {
            afficherAlerte("Champs invalides", "Veuillez remplir tous les champs.");
            return;
        }
        try {
            abonnement.setNomAbonnement(nouveauNom);
            abonnement.settDateDebut(nouvelleDateDebut);
            int idOffre = abonnement.getIdOffre();
            OffreServices offreservice = new OffreServices();
            int dureeOffre = offreservice.getDureeOffre(idOffre);
            abonnement.setDateFin(nouvelleDateDebut.plusDays(dureeOffre));
            AbonnementServices service = new AbonnementServices();
            System.out.println(abonnement);
            service.update(abonnement);
            afficherAlerte("Succès", "Abonnement mis à jour avec succès.");
            System.out.println(voirAbonnementController);
            if (voirAbonnementController != null) {
                voirAbonnementController.rafraichirAbonnements();
            }
            fermerModale();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void fermerModale() {
        // Fermer la fenêtre modale
        Stage stage = (Stage) txtNomAbonnement.getScene().getWindow();
        stage.close();
    }
    @FXML
    private void annuler() {
        fermerModale();
    }


    private void afficherAlerte(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
