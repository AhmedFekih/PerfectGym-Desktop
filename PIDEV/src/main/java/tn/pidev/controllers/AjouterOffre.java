package tn.pidev.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import tn.pidev.entities.Offre;
import tn.pidev.entities.SalleDeSport;
import tn.pidev.services.OffreServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import java.io.IOException;
import javafx.stage.FileChooser;
import java.util.List;
import java.util.ArrayList;
import javafx.scene.control.ListView;

import java.io.File;

public class AjouterOffre {
    private List<String> images = new ArrayList<>();

    @FXML
    private TextField DescriptionId;

    @FXML
    private TextField DureeId;

    @FXML
    private TextField NomOffreId;

    @FXML
    private TextField PrixId;

    @FXML
    private ComboBox<Offre.Statut> StatusComboBox;
    @FXML
    private ComboBox<Offre.Categorie> CategorieComboBox;
    @FXML
    private ListView<String> imageListView;

    @FXML
    private Button choisirImageButton;

    private SalleDeSport salle;

    @FXML
    public void initialize() {
        StatusComboBox.getItems().setAll(Offre.Statut.values());
        CategorieComboBox.getItems().setAll(Offre.Categorie.values()); // Populate ComboBox with Category enum values

    }
    public void setSalle(SalleDeSport salle) {
        this.salle = salle;
    }

    @FXML
    void choisirImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir des images");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All Files", "*.*"));

        // Permettre de sélectionner plusieurs fichiers
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(null);

        if (selectedFiles != null) {
            // Ajouter chaque chemin d'image à la ListView
            for (File file : selectedFiles) {
                imageListView.getItems().add(file.getAbsolutePath());
            }
        }
    }
    @FXML
    void AjouterOffre(ActionEvent event) {
        // Vérification des champs vides
        if (NomOffreId.getText().isEmpty() || DescriptionId.getText().isEmpty()  || DureeId.getText().isEmpty() ||
                PrixId.getText().isEmpty() || StatusComboBox.getValue() == null || CategorieComboBox.getValue() == null || imageListView.getItems().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Avertissement", "Tous les champs doivent être remplis !", null);
            return;
        }

        // Vérification du prix (nombre positif)
        double prixx;
        try {
            prixx = Double.parseDouble(PrixId.getText());
            if (prixx < 0) {
                showAlert(Alert.AlertType.ERROR, "Erreur", null, "Le prix doit être un nombre positif !");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", null, "Le prix doit être un nombre valide !");
            return;
        }

        // Vérification de la durée
        int durree;
        try {
            durree = Integer.parseInt(DureeId.getText());
            if (durree < 0) {
                showAlert(Alert.AlertType.ERROR, "Erreur", null, "La durée doit être un nombre positif !");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", null, "La durée doit être un nombre valide !");
            return;
        }

        try {
            String nomOffre = NomOffreId.getText();
            String description = DescriptionId.getText();
            int duree = Integer.parseInt(DureeId.getText());
            double prix = Double.parseDouble(PrixId.getText());
            Offre.Statut statut = StatusComboBox.getValue();
            Offre.Categorie categorie = CategorieComboBox.getValue();

            // Récupérer les chemins des images depuis la ListView
            List<String> images = imageListView.getItems();

            Offre offre = new Offre(nomOffre, statut, description, prix, duree, images,categorie);
            OffreServices serviceOffre = new OffreServices();
            serviceOffre.insert(offre, salle);

            showAlert(Alert.AlertType.INFORMATION, "Succès", null, "Offre ajoutée avec succès !");
            retourVersListeDesOffres(event);

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur est survenue", "Impossible d'ajouter l'offre. Veuillez réessayer.");
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
    void Annuler(ActionEvent event) {
        retourVersListeDesOffres(event);
    }

    private void retourVersListeDesOffres(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherOffre.fxml"));
            Parent root = loader.load();

            VoirOffres voirOffresController = loader.getController();
            voirOffresController.setSalle(salle);
            voirOffresController.afficherOffres(); // Actualiser la liste des offres

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
