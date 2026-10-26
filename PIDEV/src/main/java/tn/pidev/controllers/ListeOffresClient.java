package tn.pidev.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tn.pidev.entities.Categorie;
import tn.pidev.entities.Offre.Statut;
import tn.pidev.entities.Offre;
import tn.pidev.entities.SalleDeSport;
import tn.pidev.entities.Utilisateur;
import tn.pidev.services.OffreServices;
import javafx.event.ActionEvent;
import javafx.scene.layout.StackPane;
import javafx.geometry.Pos;
import tn.pidev.services.AbonnementServices;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import tn.pidev.entities.Abonnement;
import javafx.fxml.Initializable;
import java.util.ResourceBundle;
import java.net.URL;
import javafx.scene.control.ComboBox;
import java.util.List;
import java.util.stream.Collectors;

public class ListeOffresClient implements Initializable{
    @FXML
    private VBox offresContainer;

    private SalleDeSport salle;

    @FXML
    private Button btnVoirAbonnements;
    @FXML
    private ComboBox<Offre.Categorie> comboBoxCategorie;

    @FXML
    private ComboBox<Offre.Statut> comboBoxStatut;
    @FXML
    private HBox searchBarContainer;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Remplir les ComboBox avec les valeurs d'énumération
        comboBoxCategorie.getItems().setAll(Offre.Categorie.values());
        comboBoxCategorie.setPromptText("Choisir une catégorie");

        comboBoxStatut.getItems().setAll(Offre.Statut.values());
        comboBoxStatut.setPromptText("Choisir un statut");
        if (searchBarContainer != null) {
            searchBarContainer.setVisible(true);
        }
    }
    @FXML
    public void rechercherOffres() {
        Offre.Categorie categorieSelectionnee = comboBoxCategorie.getValue();
        Offre.Statut statutSelectionne = comboBoxStatut.getValue();

        try {
            OffreServices serviceOffre = new OffreServices();
            List<Offre> offres = serviceOffre.showAll(salle); // Récupérer toutes les offres

            List<Offre> offresFiltrees = offres.stream()
                    .filter(offre -> {
                        boolean filtreCategorie = (categorieSelectionnee == null || offre.getCategorie() == categorieSelectionnee);
                        boolean filtreStatut = (statutSelectionne == null || offre.getStatut() == statutSelectionne);
                        return filtreCategorie && filtreStatut;
                    })
                    .collect(Collectors.toList());

            afficherOffresFiltrees(offresFiltrees); // Afficher les offres filtrées
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la récupération des offres.");
        }
    }
    private void afficherOffresFiltrees(List<Offre> offresFiltrees) {
        offresContainer.getChildren().clear(); // Vider le conteneur actuel

        for (Offre offre : offresFiltrees) {
            HBox card = creerCarteOffre(offre); // Créer une carte pour chaque offre
            offresContainer.getChildren().add(card); // Ajouter la carte au conteneur
        }
    }
    @FXML
    public void reinitialiserRecherche() {
        comboBoxCategorie.setValue(null);
        comboBoxCategorie.setPromptText("Choisir une catégorie");
        comboBoxStatut.setValue(null);
        comboBoxStatut.setPromptText("Choisir un statut");
        afficherOffres(); // Recharger toutes les offres
    }
    public void setSalle(SalleDeSport s1) {
        this.salle = s1;
        afficherOffres();
    }
    private Utilisateur utilisateurConnecte;
    private void recupererUtilisateurConnecte() {
        // Création d'un utilisateur statique (exemple)
           this.utilisateurConnecte=new Utilisateur(14677800,"Fekih", "Ahmed", 95003251, "ahmed.fekih@esprit.tn", "Homme", "ahmed123",Categorie.Client, "active");
      //  this.utilisateurConnecte= new Utilisateur(15644900,"Amir", "Raouine", 27003365, "ahmed.fekih@esprit.tn", "Homme", "amir12345", Categorie.Fondateur, "active");
    }
    public void afficherOffres() {
        try {
            if (searchBarContainer != null) {
                searchBarContainer.setVisible(true);
            }
            recupererUtilisateurConnecte();
            OffreServices serviceOffre = new OffreServices();
            List<Offre> offres = serviceOffre.showAll(salle);
            offresContainer.getChildren().clear();
            if (utilisateurAAbonnement(salle)) {
                btnVoirAbonnements.setVisible(true);  // Afficher le bouton si l'utilisateur a des abonnements
            } else {
                btnVoirAbonnements.setVisible(false);  // Masquer le bouton si l'utilisateur n'a pas d'abonnement
            }
            for (Offre offre : offres) {
                HBox card = creerCarteOffre(offre);
                offresContainer.getChildren().add(card);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la récupération des offres.");
        }
    }
    private boolean utilisateurAAbonnement(SalleDeSport salle ) throws SQLException {
        recupererUtilisateurConnecte();
        // Appeler un service ou une méthode qui récupère les abonnements pour un utilisateur dans la salle
        AbonnementServices abonnementServices = new AbonnementServices();
        List<Abonnement> abonnements = abonnementServices.showAllAbonnement(salle,utilisateurConnecte);
        System.out.println("aaaaa"+abonnements);
        return !abonnements.isEmpty();  // Si la liste n'est pas vide, cela signifie qu'il y a des abonnements
    }
    private HBox creerCarteOffre(Offre offre) {
        HBox card = new HBox(10);
        card.setStyle("-fx-padding: 10px; -fx-border-color: #ccc; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-background-color: white;");

        // Créer un conteneur StackPane pour superposer l'image et les boutons
        StackPane imageContainer = new StackPane(); // Utilisation de StackPane
        imageContainer.setStyle("-fx-padding: 5px;");

        // Vérifier qu'il y a des images
        List<String> images = offre.getImages();
        if (images != null && !images.isEmpty()) {
            // Afficher seulement la première image
            ImageView imageView = new ImageView();
            try {
                Image image = new Image(new File(images.get(0)).toURI().toString());
                imageView.setImage(image);
                imageView.setFitWidth(100);
                imageView.setFitHeight(100);
                imageContainer.getChildren().add(imageView); // Ajouter la première image au StackPane
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Ajouter les boutons Précédent et Suivant pour naviguer entre les images
            int[] currentIndex = {0}; // Utiliser un tableau pour modifier l'index dans l'action du bouton
            if (images.size() > 1) {
                Button btnPrev = new Button("<");
                Button btnNext = new Button(">");
                /// ;-fx-text-fill:#4CAF50
                btnPrev.setStyle("-fx-background-color: #4CAF50 ;-fx-background-radius: 50%; -fx-min-width: 20px; -fx-min-height: 20px;");
                btnNext.setStyle("-fx-background-color: #4CAF50; -fx-background-radius: 50%; -fx-min-width: 20px; -fx-min-height: 20px;");

                // Lier l'action du bouton "Précédent"
                btnPrev.setOnAction(event -> {
                    if (currentIndex[0] > 0) {
                        currentIndex[0]--;
                        imageView.setImage(new Image(new File(images.get(currentIndex[0])).toURI().toString()));
                    }
                });

                // Lier l'action du bouton "Suivant"
                btnNext.setOnAction(event -> {
                    if (currentIndex[0] < images.size() - 1) {
                        currentIndex[0]++;
                        imageView.setImage(new Image(new File(images.get(currentIndex[0])).toURI().toString()));
                    }
                });

                // Placer les boutons sur l'image en utilisant le StackPane
                StackPane.setAlignment(btnPrev, Pos.CENTER_LEFT); // Positionner le bouton à gauche
                StackPane.setAlignment(btnNext, Pos.CENTER_RIGHT); // Positionner le bouton à droite

                // Ajouter les boutons au StackPane (au-dessus de l'image)
                imageContainer.getChildren().addAll(btnPrev, btnNext);
            }
        }
        VBox details = new VBox(5);
        Text nom = new Text("Nom: " + offre.getNomOffre());
        Text categorie = new Text("Categorie: " + offre.getCategorie());
        Text description = new Text("Description: " + offre.getDescription());
        Text prix = new Text("Prix: " + offre.getPrix() + " DT");
        Text duree = new Text("Durée: " + offre.getDuree() + " jours");
        Text statut = new Text("Statut: " + offre.getStatut());

        Button btnAbonner = new Button("S'abonner");

        btnAbonner.setOnAction(event -> {
            afficherFormulaireAbonnement(offre);
        });
        btnAbonner.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");

        if (offre.getStatut() == Offre.Statut.Inactive) {
            btnAbonner.setVisible(false);
        }

        details.getChildren().addAll(nom,categorie, description, prix, duree, statut, btnAbonner);
        card.getChildren().addAll(imageContainer, details);
        return card;
    }



    private void afficherFormulaireAbonnement(Offre offre) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterAbonnement.fxml"));
            Parent root = loader.load();
            AjouterAbonnement controller = loader.getController();
            controller.setOnAbonnementAjoute(this::afficherOffres);
            controller.setSelectedOffre(offre);

            Stage stage = new Stage();
            stage.setTitle("Abonnement à l'Offre");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void afficherMesAbonnements(ActionEvent event) {
        try {
            if (offresContainer != null) {
                offresContainer.getChildren().clear(); // Vider la liste avant d'afficher les abonnements

                Button btnRetour = new Button("Retour aux Offres");
                btnRetour.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-padding: 10;");
                btnRetour.setOnAction(e -> afficherOffres());

                offresContainer.getChildren().add(btnRetour); // Ajouter le bouton de retour en haut

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/VoirAbonnementClient.fxml"));
                Parent abonnementView = loader.load();
                btnVoirAbonnements.setVisible(false);
                searchBarContainer.setVisible(false);
                VoirAbonnementClient controller = loader.getController();
                controller.setSalle(salle);
                controller.afficherAbonnements(null);

                offresContainer.getChildren().add(abonnementView);
            } else {
                System.out.println("Erreur: offresContainer est null");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void retourSalle() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficheSalleClient.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Correction : récupérer la scène depuis un élément existant
            Stage stage = (Stage) offresContainer.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
