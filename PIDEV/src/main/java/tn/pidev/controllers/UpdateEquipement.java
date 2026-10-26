package tn.pidev.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tn.pidev.entities.Equipement;
import tn.pidev.services.equipementService;

import java.io.IOException;
import java.sql.SQLException;

public class UpdateEquipement {


    @FXML
    private TextField inputidsalle;
    @FXML
    private TextField inputNom;
    @FXML
    private TextField inputimage;
    @FXML
    private TextField inputstatut;

    private equipementService salleservice;
    private int ref;



    public void setIdPlanning(int ref) {
        this.ref = ref;

        // Fetch the full planning object using the idPlanning from the database
        try {
            salleservice = new equipementService();
            Equipement planning = salleservice.geEquipementByRef(ref);

            inputidsalle.setText(String.valueOf(ref));
            inputNom.setText(planning.getNom());
            inputimage.setText(String.valueOf(planning.getQuantite()));
            inputstatut.setText(planning.getStatut());
        } catch (SQLException e) {
            showErrorAlert("Database Error", "Failed to retrieve planning data.");
        }
    }

    @FXML
    void submit(ActionEvent event) {
        // Validate inputs by trimming spaces and checking if they are empty
        if (inputNom.getText().trim().isEmpty() || inputimage.getText().trim().isEmpty() || inputstatut.getText().trim().isEmpty()) {
            showErrorAlert("Input Error", "Please fill in all fields.");
            return;
        }

        try {
            int currentRef = Integer.parseInt(inputidsalle.getText().trim());  // Assuming inputidsalle holds the ref ID

            // Create an updated Equipement object with the new values
            Equipement updatedEquipement = new Equipement();
            updatedEquipement.setRef(currentRef);
            updatedEquipement.setNom(inputNom.getText().trim());
            updatedEquipement.setQuantite(Integer.parseInt(inputimage.getText().trim()));  // Parse quantity to integer
            updatedEquipement.setStatut(inputstatut.getText().trim());

            // Call the update method in equipementService to update the database
            salleservice.update(updatedEquipement);

            // Show success message
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Success");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Equipement updated successfully.");
            successAlert.showAndWait();

        } catch (SQLException e) {
            showErrorAlert("Database Error", "Failed to update equipment data.");
        } catch (NumberFormatException e) {
            showErrorAlert("Input Error", "Please ensure all fields have valid data.");
        }
    }

    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
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
        @FXML
        void deleteSalleAction(ActionEvent event) throws SQLException {
            equipementService salleService = new equipementService();
            salleService.delete(ref);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("Salle delete avec succes");
            alert.showAndWait();
        }

}



