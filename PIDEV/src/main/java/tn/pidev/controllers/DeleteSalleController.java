package tn.pidev.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import tn.pidev.services.salleDeSportService;

import java.sql.SQLException;

public class DeleteSalleController {
    @FXML
    private TextField idTextField;
    @FXML
    void deleteSalleAction(ActionEvent event) throws SQLException {
        int id = Integer.parseInt(idTextField.getText());
        salleDeSportService salleService = new salleDeSportService();
        salleService.delete(id);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("Salle delete avec succes");
        alert.showAndWait();
    }
}
