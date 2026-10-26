package tn.pidev.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import tn.pidev.services.equipementService;

import java.sql.SQLException;

public class DeleteEquipement {
    @FXML
    private TextField refTextField;
    @FXML
    void deleteSalleAction(ActionEvent event) throws SQLException {
        int ref= Integer.parseInt(refTextField.getText());
        equipementService salleService = new equipementService();
        salleService.delete(ref);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("Salle delete avec succes");
        alert.showAndWait();
    }
}