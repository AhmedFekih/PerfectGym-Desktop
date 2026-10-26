package tn.pidev.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tn.pidev.entities.SalleDeSport;
import tn.pidev.services.salleDeSportService;
import java.io.IOException;
import java.sql.SQLException;


public class UpdateSalle{


    @FXML
    private TextField inputId;

    @FXML
    private TextField inputnom;

    @FXML
    private TextField inputlocalisation;
    @FXML
    private TextField inputstatut;

    private salleDeSportService salleservice;
    private int idSalle;

    public void setIdSalle(int idSalle) {
        this.idSalle = idSalle;

        // Fetch the full planning object using the idPlanning from the database
        try {
            salleservice = new salleDeSportService();
            SalleDeSport planning = salleservice.getSalleById(idSalle);
            inputId.setText(String.valueOf(idSalle));
            inputnom.setText(planning.getNom());
            inputlocalisation.setText(planning.getLocalisation());
            inputstatut.setText(planning.getStatut());
        } catch (SQLException e) {
            showErrorAlert("Database Error", "Failed to retrieve planning data.");
        }
    }


    @FXML
    void submit(ActionEvent event) {
        // Validate input fields
        if (isInputValid()) {
            int currentPlanId = Integer.parseInt(inputId.getText());
            // Create a SalleDeSport object with the updated values
            SalleDeSport updatedSalle = new SalleDeSport();
            updatedSalle.setIdSalle(currentPlanId);
            updatedSalle.setNom(inputnom.getText());
            updatedSalle.setLocalisation(inputlocalisation.getText());
            updatedSalle.setStatut(inputstatut.getText());
            try {
                // Call the update method in SalleDeSportService to update the database
                salleservice.update(updatedSalle);

                // Show success message
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setHeaderText(null);
                successAlert.setContentText("SalleDeSport updated successfully.");
                successAlert.showAndWait();

            } catch (SQLException e) {
                showErrorAlert("Database Error", "Failed to update salle de sport data.");
            }
        }
    }


    private void showErrorAlert(String databaseError, String s) {
    }

    // Method to check if the input fields are valid (i.e., not empty)
    private boolean isInputValid() {
        String errorMessage = "";

        if (inputId.getText() == null || inputId.getText().isEmpty()) {
            errorMessage += "ID field is empty!\n";
        }
        if (inputnom.getText() == null || inputnom.getText().isEmpty()) {
            errorMessage += "Nom field is empty!\n";
        }
        if (inputlocalisation.getText() == null || inputlocalisation.getText().isEmpty()) {
            errorMessage += "Localisation field is empty!\n";
        }

        if (inputstatut.getText() == null || inputstatut.getText().isEmpty()) {
            errorMessage += "Statut field is empty!\n";
        }

        if (errorMessage.isEmpty()) {
            return true;
        } else {
            // Show error alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }
    @FXML
    public void cancelAddPlanning(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficheSalle.fxml"));
            AnchorPane affichePlanningPage = loader.load();
            Scene affichePlanningScene = new Scene(affichePlanningPage);

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(affichePlanningScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



