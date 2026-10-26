package tn.pidev.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {
//    @FXML
//    public void openAddPlanningPage(ActionEvent event) {
//        try {
//            // Load the AjoutePlanning.fxml file
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AffichePlanning.fxml"));
//            AnchorPane addPlanningPage = loader.load();
//
//            // Create a new Scene with the loaded FXML
//            Scene addPlanningScene = new Scene(addPlanningPage);
//
//            // Get the current stage and set the new scene
//            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
//            stage.setScene(addPlanningScene);
//            stage.show(); // Show the new scene
//        } catch (IOException e) {
//            e.printStackTrace();
//            // You can show an error dialog or log the error here
//        }
//    }

    @FXML
    public void openSallePage(ActionEvent event) {
        try {
            // Load the AjoutePlanning.fxml file
        //   FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficheSalle.fxml"));
           FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficheSalleClient.fxml"));
            AnchorPane addPlanningPage = loader.load();

            // Create a new Scene with the loaded FXML
            Scene addPlanningScene = new Scene(addPlanningPage);

            // Get the current stage and set the new scene
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(addPlanningScene);
            stage.show(); // Show the new scene
        } catch (IOException e) {
            e.printStackTrace();
            // You can show an error dialog or log the error here
        }
    }
//    @FXML
//    public void openEventPage(ActionEvent event) {
//        try {
//            // Load the AjoutePlanning.fxml file
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficheEvenement.fxml"));
//            AnchorPane addPlanningPage = loader.load();
//
//            // Create a new Scene with the loaded FXML
//            Scene addPlanningScene = new Scene(addPlanningPage);
//
//            // Get the current stage and set the new scene
//            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
//            stage.setScene(addPlanningScene);
//            stage.show(); // Show the new scene
//        } catch (IOException e) {
//            e.printStackTrace();
//            // You can show an error dialog or log the error here
//        }
//    }
//
//    @FXML
//    public void openOffrePage(ActionEvent event) {
//        try {
//            // Load the AjoutePlanning.fxml file
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherOffre.fxml"));
//            AnchorPane addPlanningPage = loader.load();
//
//            // Create a new Scene with the loaded FXML
//            Scene addPlanningScene = new Scene(addPlanningPage);
//
//            // Get the current stage and set the new scene
//            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
//            stage.setScene(addPlanningScene);
//            stage.show(); // Show the new scene
//        } catch (IOException e) {
//            e.printStackTrace();
//            // You can show an error dialog or log the error here
//        }
//    }
//
//    @FXML
//    public void openPlanAli(ActionEvent event) {
//        try {
//            // Load the AjoutePlanning.fxml file
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AffichePlanAli.fxml"));
//            AnchorPane addPlanningPage = loader.load();
//
//            // Create a new Scene with the loaded FXML
//            Scene addPlanningScene = new Scene(addPlanningPage);
//
//            // Get the current stage and set the new scene
//            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
//            stage.setScene(addPlanningScene);
//            stage.show(); // Show the new scene
//        } catch (IOException e) {
//            e.printStackTrace();
//            // You can show an error dialog or log the error here
//        }
//    }
}
