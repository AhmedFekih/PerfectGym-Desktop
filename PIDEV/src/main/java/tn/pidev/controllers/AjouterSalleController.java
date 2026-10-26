package tn.pidev.controllers;

import javafx.scene.control.*;
import tn.pidev.entities.Utilisateur;
import tn.pidev.entities.Categorie;
import tn.pidev.entities.SalleDeSport;
import tn.pidev.services.salleDeSportService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public class AjouterSalleController {
    @FXML
    private TextField nomTextField;
    @FXML
    private TextField localisationTextField;
    @FXML
    private TextField statutTextField;

    @FXML
    private Label errorLabel;

    private String imagePath;
    // This will hold selected SalleDeSport ids
    private List<Utilisateur> users;


    @FXML
    void ajouteUserAction(ActionEvent event) throws SQLException {
        // Clear the error message
        errorLabel.setText("");

        // Get the text from the text fields
        String nom = nomTextField.getText();
        String localisation = localisationTextField.getText();
        String statut = statutTextField.getText();

        // Validate if any of the fields are empty
        if (nom.isEmpty() || localisation.isEmpty() || statut.isEmpty()) {
            // Display the error message
            errorLabel.setText("Veuillez remplir tous les champs obligatoires.");
            return; // Stop further execution
        }


        // Proceed with the insert operation if all fields are filled
        SalleDeSport salle = new SalleDeSport(nom, localisation, imagePath, statut);
        salleDeSportService salleService = new salleDeSportService();
        Utilisateur u1= new Utilisateur(15644900,"Amir", "Raouine", 27003365, "ahmed.fekih@esprit.tn", "Homme", "amir12345",Categorie.Fondateur, "avtive");


                salleService.insert1(salle,u1);

        // Show success alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("Salle de sport ajoutée avec succès");
        alert.showAndWait();

        // Optionally, you can clear the text fields after successful insertion

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
    public void handleUploadButton(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.jpeg"));

        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            imagePath = selectedFile.getAbsolutePath();
            System.out.println("Selected Image Path: " + imagePath);
        }
    }
}




