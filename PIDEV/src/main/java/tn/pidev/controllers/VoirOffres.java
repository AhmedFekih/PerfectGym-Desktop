package tn.pidev.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import tn.pidev.entities.Offre;
import tn.pidev.entities.SalleDeSport;
import tn.pidev.services.OffreServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Modality;
import java.io.File;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.sql.SQLException;
import java.io.IOException;
import java.util.List;

public class VoirOffres {

    @FXML
    private TableView<Offre> tableViewOffres;

    @FXML
    private TableColumn<Offre, String> colNom;

    @FXML
    private TableColumn<Offre, String> colDescription;


    @FXML
    private TableColumn<Offre, Integer> colDuree;

    @FXML
    private TableColumn<Offre, Double> colPrix;

    @FXML
    private TableColumn<Offre, String> colStatut;

    @FXML
    private TableColumn<Offre, Void> colAbonner;

    private SalleDeSport salle;

    private Stage currentStage;
    @FXML
    private ComboBox<String> comboBoxCritere;

    @FXML
    private TextField textFieldRecherche;
    public void setSalle(SalleDeSport s1) {
        this.salle = s1;
        afficherOffres();
    }
    @FXML
    private TableColumn<Offre, List<String>> colImage;


    @FXML
    public void initialize() {
        // Assurez-vous que la colonne Image attend une liste d'images
        colImage.setCellValueFactory(new PropertyValueFactory<>("images")); // 'images' est une ArrayList<String>

        colImage.setCellFactory(param -> new TableCell<Offre, List<String>>() {
            @Override
            protected void updateItem(List<String> images, boolean empty) {
                super.updateItem(images, empty);

                if (empty || images == null) {
                    setGraphic(null);
                } else {
                    HBox hbox = new HBox(10); // Crée un conteneur horizontal avec un espacement de 10 pixels
                    for (String imagePath : images) {
                        try {
                            ImageView imageView = new ImageView(imagePath); // Crée un ImageView pour chaque chemin d'image
                            imageView.setFitWidth(50); // Taille de l'image
                            imageView.setFitHeight(50);
                            hbox.getChildren().add(imageView); // Ajoute l'image à l'HBox
                        } catch (Exception e) {
                            e.printStackTrace(); // Gestion des erreurs si l'image ne peut pas être chargée
                        }
                    }
                    setGraphic(hbox); // Affiche l'HBox dans la cellule
                }
            }
        });
    }
    @FXML
    public void reinitialiserRecherche() {
        textFieldRecherche.clear();
        afficherOffres();
    }
    public void setStage(Stage stage) {
        this.currentStage = stage;
    }

    public void afficherOffres() {
        try {
            OffreServices serviceOffre = new OffreServices();
            List<Offre> offres = serviceOffre.showAll(salle); // Récupérer toutes les offres
            ObservableList<Offre> observableList = FXCollections.observableArrayList(offres);

            // Configuration des colonnes
            colNom.setCellValueFactory(new PropertyValueFactory<>("nomOffre"));
            colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
            colDuree.setCellValueFactory(new PropertyValueFactory<>("duree"));
            colPrix.setCellValueFactory(new PropertyValueFactory<>("prix"));
            colStatut.setCellValueFactory(new PropertyValueFactory<>("statut"));

            // Configuration de la colonne des images
            colImage.setCellValueFactory(new PropertyValueFactory<>("images"));

            colImage.setCellFactory(param -> new TableCell<Offre, List<String>>() {
                private int currentImageIndex = 0;

                @Override
                protected void updateItem(List<String> images, boolean empty) {
                    super.updateItem(images, empty);

                    if (empty || images == null || images.isEmpty()) {
                        setGraphic(null);
                    } else {
                        try {
                            StackPane stackPane = new StackPane();
                            stackPane.setPrefSize(10, 10);

                            ImageView imageView = new ImageView();
                            String imagePath = images.get(currentImageIndex);
                            Image image = new Image(new File(imagePath).toURI().toString());
                            imageView.setImage(image);
                            imageView.setFitWidth(150);
                            imageView.setFitHeight(150);

                            stackPane.getChildren().add(imageView);

                            if (images.size() > 1) {
                                Button btnPrev = new Button("<");
                                Button btnNext = new Button(">");

                                btnPrev.getStyleClass().add("small-button");
                                btnNext.getStyleClass().add("small-button");

                                StackPane.setAlignment(btnPrev, Pos.CENTER_LEFT);
                                StackPane.setAlignment(btnNext, Pos.CENTER_RIGHT);

                                btnPrev.setOnAction(event -> previousImage(images, imageView));
                                btnNext.setOnAction(event -> nextImage(images, imageView));

                                stackPane.getChildren().addAll(btnPrev, btnNext);
                            }

                            setGraphic(stackPane);
                        } catch (Exception e) {
                            e.printStackTrace();
                            setGraphic(null);
                        }
                    }
                }

                private void previousImage(List<String> images, ImageView imageView) {
                    if (currentImageIndex > 0) {
                        currentImageIndex--;
                        updateImage(images);
                    }
                }

                private void nextImage(List<String> images, ImageView imageView) {
                    if (currentImageIndex < images.size() - 1) {
                        currentImageIndex++;
                        updateImage(images);
                    }
                }

                private void updateImage(List<String> images) {
                    ImageView imageView = (ImageView) ((StackPane) getGraphic()).getChildren().get(0);
                    String imagePath = images.get(currentImageIndex);
                    imageView.setImage(new Image(new File(imagePath).toURI().toString()));
                }
            });

            // Appliquer la liste observable à la TableView
            tableViewOffres.setItems(observableList);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la récupération des offres.");
        }
    }
    @FXML
    public void rechercherOffres() {
        String critere = comboBoxCritere.getValue();
        String recherche = textFieldRecherche.getText().toLowerCase();

        ObservableList<Offre> offresFiltrees = FXCollections.observableArrayList();

        for (Offre offre : tableViewOffres.getItems()) {
            switch (critere) {
                case "Nom":
                    if (offre.getNomOffre().toLowerCase().contains(recherche)) {
                        offresFiltrees.add(offre);
                    }
                    break;
                case "Catégorie":
                    if (offre.getCategorie().toString().toLowerCase().contains(recherche)) {
                        offresFiltrees.add(offre);
                    }
                    break;
                default:
                    offresFiltrees.add(offre);
                    break;
            }
        }

        tableViewOffres.setItems(offresFiltrees);
    }
    @FXML
    public void redirigerAjouterOffre() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterOffre.fxml"));
            Parent root = loader.load();

            AjouterOffre controller = loader.getController();
           // SalleDeSport s1 = new SalleDeSport(1,"Salle1", "Tunis", "Active");
            controller.setSalle(salle);

            Stage stage = (Stage) tableViewOffres.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void modifierOffre() {
        // Récupère l'offre sélectionnée dans la TableView
        Offre offreSelectionnee = tableViewOffres.getSelectionModel().getSelectedItem();

        if (offreSelectionnee != null) {
            if (offreSelectionnee.getStatut() == Offre.Statut.Inactive) {
                try {
                    // Charge la fenêtre modale pour modifier l'offre
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierOffre.fxml"));
                    Parent root = loader.load();

                    // Récupère le contrôleur de la fenêtre modale
                    ModifierOffre controller = loader.getController();

                    // Passe l'offre sélectionnée au contrôleur de la fenêtre modale
                    controller.setOffre(offreSelectionnee);
                    controller.setOffresList(tableViewOffres.getItems());  // Passer l'ObservableList

                    // Crée une nouvelle scène et une nouvelle fenêtre pour la modale
                    Stage stage = new Stage();
                    stage.setTitle("Modifier l'Offre");
                    stage.initModality(Modality.APPLICATION_MODAL); // Rendre la fenêtre modale
                    stage.setScene(new Scene(root));
                    stage.showAndWait(); // Attend que la fenêtre modale soit fermée

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Modification Impossible");
                alert.setHeaderText(null);
                alert.setContentText("Vous ne pouvez modifier que les offres avec le statut 'inactive'.");
                alert.showAndWait(); // Affiche un message d'erreur si le statut n'est pas "inactive"
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune Offre Sélectionnée");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une offre dans la liste avant de modifier.");
            alert.showAndWait();  // Afficher l'alerte et attendre la réponse de l'utilisateur
        }
    }

    public void mettreAJourOffres() {
        try {
            OffreServices serviceOffre = new OffreServices();
            List<Offre> offres = serviceOffre.showAll(salle); // Recharger les offres
            ObservableList<Offre> observableList = FXCollections.observableArrayList(offres);

            tableViewOffres.setItems(observableList);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la récupération des offres.");
        }
    }
    @FXML
    public void supprimerOffre() {
        Offre offreSelectionnee = tableViewOffres.getSelectionModel().getSelectedItem();

        if (offreSelectionnee != null) {
            if (offreSelectionnee.getStatut() == Offre.Statut.Inactive) {
                try {
                    Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmation.setTitle("Confirmation de suppression");
                    confirmation.setHeaderText("Êtes-vous sûr de vouloir supprimer cette offre ?");
                    confirmation.setContentText("Cette action est irréversible.");

                    if (confirmation.showAndWait().get() == ButtonType.OK) {
                        OffreServices serviceOffre = new OffreServices();
                        serviceOffre.delete(offreSelectionnee.getIdOffre());
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Suppression réussie");
                        successAlert.setHeaderText(null);
                        successAlert.setContentText("L'offre a été supprimée avec succès.");
                        successAlert.showAndWait();
                        mettreAJourOffres(); // Mettre à jour la liste des offres après la suppression
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText("Erreur lors de la suppression");
                    alert.setContentText("Impossible de supprimer l'offre. Veuillez réessayer.");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Suppression Impossible");
                alert.setHeaderText(null);
                alert.setContentText("Vous ne pouvez supprimer que les offres avec le statut 'inactive'.");
                alert.showAndWait(); // Affiche un message d'erreur si le statut n'est pas "inactive"
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune Offre Sélectionnée");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une offre à supprimer.");
            alert.showAndWait();
        }
    }


    @FXML
    public void VoirAbonnement() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/VoirAbonnement.fxml"));
            Parent root = loader.load();

            VoirAbonnement controller = loader.getController();
            controller.setSalle(salle);
            controller.afficherAbonnements(null);
            Stage stage = (Stage) tableViewOffres.getScene().getWindow();
            stage.setTitle("Mes Abonnements");
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void retourSalle() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficheSalle.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Récupérer la fenêtre actuelle et y afficher la nouvelle scène
            Stage stage = (Stage) tableViewOffres.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}