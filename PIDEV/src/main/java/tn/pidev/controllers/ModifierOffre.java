package tn.pidev.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.pidev.entities.Offre;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import tn.pidev.services.OffreServices;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.scene.control.ListView;
import javafx.collections.FXCollections;

import java.io.File;
import java.util.List;

public class ModifierOffre {

    @FXML
    private TextField nomField;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField prixField;

    @FXML
    private TextField dureeField;

    @FXML
    private ComboBox<Offre.Statut> statusComboBox;
    @FXML
    private ComboBox<Offre.Categorie> CategorieComboBox;
    @FXML
    private ListView<String> imageListView;

    @FXML
    private Button choisirImageButton;

    @FXML
    private Button supprimerImageButton;

    private Offre offre;
    private OffreServices offreService;
    private ObservableList<Offre> offresList;  // Liste partagée des offres

    public ModifierOffre() {
        // Instancier OffreServices (vous pouvez aussi l'injecter si vous utilisez un framework de dépendances)
        offreService = new OffreServices();
    }

    // Cette méthode est appelée pour définir l'offre à modifier
    public void setOffre(Offre offre) {
        this.offre = offre;
        // Pré-remplir les champs avec les données de l'offre
        nomField.setText(offre.getNomOffre());
        descriptionField.setText(offre.getDescription());
        prixField.setText(String.valueOf(offre.getPrix()));
        dureeField.setText(String.valueOf(offre.getDuree()));
        statusComboBox.getItems().setAll(Offre.Statut.values()); // Remplir le ComboBox avec les valeurs de l'enum Statut
        statusComboBox.setValue(offre.getStatut());
        CategorieComboBox.getItems().setAll(Offre.Categorie.values()); // Remplir le ComboBox avec les valeurs de l'enum Statut
        CategorieComboBox.setValue(offre.getCategorie());
        // Afficher les images dans le ListView
        if (offre.getImages() != null) {
            imageListView.setItems(FXCollections.observableArrayList(offre.getImages()));
        }
    }

    // Méthode pour recevoir la liste des offres
    public void setOffresList(ObservableList<Offre> offresList) {
        this.offresList = offresList;
    }

    @FXML
    public void sauvegarderOffre() {
        // Vérification des champs vides
        if (nomField.getText().isEmpty() || descriptionField.getText().isEmpty() || dureeField.getText().isEmpty() ||
                prixField.getText().isEmpty() || statusComboBox.getValue() == null || CategorieComboBox.getValue() == null || imageListView.getItems().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Avertissement", "Tous les champs doivent être remplis !", null);
            return;
        }

        // Vérification du prix (nombre positif)
        double prixx;
        try {
            prixx = Double.parseDouble(prixField.getText());
            if (prixx < 0) {
                showAlert(Alert.AlertType.ERROR, "Erreur", null, "Le prix doit être un nombre positif !");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", null, "Le prix doit être un nombre valide !");
            return;
        }

        // Vérification de la durée (nombre positif)
        int durree;
        try {
            durree = Integer.parseInt(dureeField.getText());
            if (durree < 0) {
                showAlert(Alert.AlertType.ERROR, "Erreur", null, "La durée doit être un nombre positif !");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", null, "La durée doit être un nombre valide !");
            return;
        }

        try {
            // Mettre à jour l'offre avec les nouvelles valeurs des champs
            offre.setNomOffre(nomField.getText());
            offre.setDescription(descriptionField.getText());
            offre.setPrix(Double.parseDouble(prixField.getText()));
            offre.setDuree(Integer.parseInt(dureeField.getText()));
            offre.setStatut(statusComboBox.getValue());
            offre.setCategorie(CategorieComboBox.getValue());
            // Récupérer la liste des nouvelles images depuis le ListView
            List<String> newImages = imageListView.getItems();
            offre.setImages(newImages);

            // Appeler la méthode update avec l'offre et la liste des nouvelles images
            int rowsAffected = offreService.update(offre, newImages);

            if (rowsAffected > 0) {
                // Mettre à jour la liste des offres
                int index = offresList.indexOf(offre);
                if (index >= 0) {
                    offresList.set(index, offre);  // Remplacer l'offre modifiée dans la liste
                }

                // Fermer la fenêtre modale
                fermerModale();
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", null, "La mise à jour a échoué.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erreur lors de la mise à jour de l'offre");
        }
    }

    @FXML
    void choisirImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir des images");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        List<File> files = fileChooser.showOpenMultipleDialog(null); // Permet de choisir plusieurs fichiers
        if (files != null) {
            // Ajouter les chemins des fichiers sélectionnés dans le ListView
            for (File file : files) {
                imageListView.getItems().add(file.getAbsolutePath()); // Ajouter le chemin du fichier dans le ListView
            }
        }
    }

    @FXML
    void supprimerImage() {
        String selectedImage = imageListView.getSelectionModel().getSelectedItem();
        if (selectedImage != null) {
            imageListView.getItems().remove(selectedImage);  // Supprimer l'image sélectionnée
        } else {
            showAlert(Alert.AlertType.WARNING, "Avertissement", "Veuillez sélectionner une image à supprimer.", null);
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
    public void fermerModale() {
        // Fermer la fenêtre modale
        Stage stage = (Stage) nomField.getScene().getWindow();
        stage.close();
    }
}
