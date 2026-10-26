package tn.pidev.controllers;

import javafx.scene.control.Alert;
import tn.pidev.entities.Abonnement;
import tn.pidev.entities.Categorie;
import tn.pidev.entities.Offre;
import tn.pidev.entities.Utilisateur;
import tn.pidev.services.AbonnementServices;
import javafx.stage.Stage;
import javafx.scene.control.DateCell;
import javafx.util.Callback;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.event.ActionEvent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.time.LocalDate;
import java.sql.SQLException;

public class AjouterAbonnement {

    @FXML
    private TextField nomAbonnementField;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private Button confirmerAbonnementButton;

    private Utilisateur u1;
    private Offre selectedOffre;

    private AbonnementServices abonnementService;
    private Runnable onAbonnementAjoute;  // Callback pour notifier le contrôleur principal

    // Méthode pour définir le callback
    public void setOnAbonnementAjoute(Runnable onAbonnementAjoute) {
        this.onAbonnementAjoute = onAbonnementAjoute;
    }
    public void initialize() {

        u1=  new Utilisateur(14677800,"Fekih", "Ahmed", 95003251, "ahmed.fekih@esprit.tn", "Homme", "ahmed123",Categorie.Client, "active");
      //     u1= new Utilisateur(15644900,"Amir", "Raouine", 27003365, "ahmed.fekih@esprit.tn", "Homme", "amir12345",Categorie.Fondateur, "active");

        // Bloquer la saisie manuelle pour la date de fin
        endDatePicker.setDisable(true);
        startDatePicker.setDayCellFactory(new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isBefore(LocalDate.now())) {
                            setDisable(true);
                            setStyle("-fx-background-color: #d3d3d3;"); // Optionnel : changer la couleur des dates désactivées
                        }
                    }
                };
            }
        });
        // Mettre à jour la date de fin automatiquement lorsqu'on choisit une date de début
        startDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && selectedOffre != null) {
                calculateEndDate(newValue);
            }
        });
    }

    public void setSelectedOffre(Offre offre) {
        this.selectedOffre = offre;

        // Si une date de début est déjà choisie, on met à jour la date de fin
        if (startDatePicker.getValue() != null) {
            calculateEndDate(startDatePicker.getValue());
        }
    }

    private void calculateEndDate(LocalDate startDate) {
        try {
            int duree = selectedOffre.getDuree();
            LocalDate endDate = startDate.plusDays(duree);
            endDatePicker.setValue(endDate);
        } catch (Exception e) {
            endDatePicker.setValue(null);
        }
    }

    @FXML
    public void confirmerAbonnement() {
        if (nomAbonnementField.getText().isEmpty() || startDatePicker.getValue() == null ) {

            showAlert(Alert.AlertType.ERROR, "Avertissement", "Tous les champs doivent être remplis !", null);
            return;
        }
        try {
            String nomAbonnement = nomAbonnementField.getText();
            LocalDate dateDebut = startDatePicker.getValue();
            LocalDate dateFin = endDatePicker.getValue();


            Abonnement abonnement = new Abonnement();
            abonnement.setNomAbonnement(nomAbonnement);
            abonnement.setUtilisateur(u1);
            abonnement.setOffre(selectedOffre);
            abonnement.settDateDebut(dateDebut);
            abonnement.setDateFin(dateFin);
            abonnement.setStatus("non payé");

            AbonnementServices abonnementService = new AbonnementServices();
            abonnementService.insert(abonnement);
            fermerFenetre();
            if (onAbonnementAjoute != null) {
                onAbonnementAjoute.run();
            }
            System.out.println("Abonnement ajouté avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de l'abonnement : " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Données invalides : " + e.getMessage());
        }
    }
    private void showAlert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    @FXML
    public void annuler() {
        fermerFenetre();
    }

    private void fermerFenetre() {
        Stage stage = (Stage) nomAbonnementField.getScene().getWindow();
        stage.close();
    }
}
