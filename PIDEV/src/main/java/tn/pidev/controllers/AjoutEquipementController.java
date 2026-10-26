package tn.pidev.controllers;

import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import tn.pidev.entities.Equipement;
import tn.pidev.entities.SalleDeSport;
import tn.pidev.services.equipementService;

import java.io.File;
import java.sql.SQLException;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;


public class AjoutEquipementController {

    @FXML
    private TextField nomTextField;
    @FXML
    private TextField quantiteTextField;
    @FXML
    private TextField statutTextField;
    @FXML
    private TextField idTextField; // VBox to hold checkboxes dynamically
    @FXML
    private Label errorLabel;
    private String imagePath;
    private int idSalle;

    // This will hold selected SalleDeSport ids
    private List<SalleDeSport> sallesDeSport;

    public void setIdSalle(int idSalle) {
        this.idSalle = idSalle;

        // Automatically set the idSalle in the text field when it's set
        idTextField.setText(String.valueOf(idSalle));
    }


    @FXML
    void ajouteUserAction(ActionEvent event) throws SQLException {
        // Clear previous error messages
        errorLabel.setText("");
        // Get values from text fields
        String nom = nomTextField.getText();
        String quantiteText = quantiteTextField.getText();
        String statut = statutTextField.getText();

        // Validate if any of the fields are empty
        if (nom.isEmpty() || quantiteText.isEmpty() || statut.isEmpty()) {
            errorLabel.setText("Veuillez remplir tous les champs.");
            return;
        }
        // Get selected SalleDeSport id from the ComboBox
        Integer selectedSalleId = Integer.valueOf(idTextField.getText()); // This returns the selected idSalle
        if (selectedSalleId == null) {
            errorLabel.setText("Veuillez sélectionner une salle.");
            return;
        }

        try {
            // Parse the quantity (must be an integer)
            int quantite = Integer.parseInt(quantiteText);

            // Create the Equipement object
            Equipement equipe = new Equipement(nom, quantite, statut, selectedSalleId,imagePath);

            // Insert into the database
            equipementService equipementService = new equipementService();
            equipementService.insert1(equipe);

            // Show success alert
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("Equipement ajouté avec succès");
            alert.showAndWait();

            // Clear fields after successful addition
            clearFields();

        } catch (NumberFormatException e) {
            errorLabel.setText("Quantité doit être un nombre.");
        }
    }

    // Method to clear input fields after successful insertion
    private void clearFields() {
        nomTextField.clear();
        quantiteTextField.clear();
        statutTextField.clear();
        errorLabel.setText(""); // Clear any previous error messages
    }
    @FXML
    public void cancelAddPlanning(ActionEvent event) {
        try {
            // Create a new FXMLLoader to load the AfficheEquipement page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficheEquipement.fxml"));

            // Load the AfficheEquipement page
            AnchorPane afficheEquipementPage = loader.load();

            // Get the controller of the AfficheEquipement page
            AfficheEquipement afficheEquipementController = loader.getController();

            // Set the idSalle in the AfficheEquipementController to ensure the correct salle is displayed
            afficheEquipementController.setIdSalle(idSalle);

            // Create the scene for the AfficheEquipement page
            Scene afficheEquipementScene = new Scene(afficheEquipementPage);

            // Get the current stage (window)
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            // Set the new scene and show the stage
            stage.setScene(afficheEquipementScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    public void handleUploadButton(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();

        // Add the extension filters
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.jpeg") // For image files
        );
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Blender Files", "*.blend") // For Blender files
        );
        // Add more file types as needed
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("All Files", "*.*") // For all file types
        );

        // Show the file chooser dialog and handle the selected file
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            imagePath = selectedFile.getAbsolutePath();
            System.out.println("Selected File Path: " + imagePath);
        }
    }


}

