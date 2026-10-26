package tn.pidev.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import tn.pidev.entities.Abonnement;
import tn.pidev.entities.Categorie;
import tn.pidev.entities.SalleDeSport;
import tn.pidev.entities.Utilisateur;
import tn.pidev.services.AbonnementServices;
import tn.pidev.services.EmailService;
import tn.pidev.services.StripePayment;
import java.io.File;
import java.sql.SQLException;
import java.util.List;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.control.ButtonType;
import javafx.scene.web.WebView;
public class VoirAbonnementClient {
        @FXML
        private VBox abonnementsContainer;
    @FXML
    private Button btnPayer;
    private Utilisateur utilisateurConnecte;
    private SalleDeSport salle;
    public void setSalle(SalleDeSport salle) {
        this.salle = salle;
    }

    private void recupererUtilisateurConnecte() {
        // Création d'un utilisateur statique (exemple)
         this.utilisateurConnecte=new Utilisateur(14677800,"Fekih", "Ahmed", 95003251, "ahmed.fekih@esprit.tn", "Homme", "ahmed123",Categorie.Client, "avtive");
  //     this.utilisateurConnecte= new Utilisateur(15644900,"Amir", "Raouine", 27003365, ""ahmed.fekih@esprit.tn"", "Homme", "amir12345", Categorie.Fondateur, "avtive");
    }
        public void afficherAbonnements(ActionEvent event) {
            try {

                recupererUtilisateurConnecte();
                AbonnementServices abonnementService = new AbonnementServices();
                List<Abonnement> abonnements = abonnementService.showAllAbonnement(salle, utilisateurConnecte);
                abonnementsContainer.getChildren().clear();
                boolean hasUnpaid = false;

                for (Abonnement abonnement : abonnements) {
                    HBox card = creerCarteAbonnement(abonnement);
                    abonnementsContainer.getChildren().add(card);
                    if ("non payé".equals(abonnement.getStatus())) {
                        hasUnpaid = true; // Il y a au moins un abonnement non payé
                    }

                }
                btnPayer.setVisible(hasUnpaid);
                btnPayer.setOnAction(e -> handlePayButton());


            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erreur lors de la récupération des abonnements.");
            }
        }

    private HBox creerCarteAbonnement(Abonnement abonnement) {
        HBox card = new HBox(10);
        card.setStyle("-fx-padding: 10px; -fx-border-color: #ccc; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-background-color: white;");

        VBox details = new VBox(5);
        Text nomAbonnement = new Text("Abonnement: " + abonnement.getNomAbonnement());
        Text nomOffre = new Text("Offre: " + abonnement.getNomOffre());
        Text nomSalle = new Text("Salle: " + abonnement.getNomSalle());
        Text dateDebut = new Text("Début: " + abonnement.getDateDebut());
        Text dateFin = new Text("Fin: " + abonnement.getDateFin());
        Text status = new Text("Statut: " + abonnement.getStatus());
        VBox buttonContainer = new VBox(5);
        Button modifierButton = new Button("Modifier");
        Button supprimerButton = new Button("Supprimer");

        // Ajouter les événements des boutons Modifier et Supprimer
        if ("non payé".equals(abonnement.getStatus())) {
            modifierButton.setOnAction(event -> modifierAbonnement(abonnement));
            supprimerButton.setOnAction(event -> supprimerAbonnement(abonnement));
        } else {
            // Désactiver les boutons si le statut n'est pas "non payé"
            modifierButton.setDisable(true);
            supprimerButton.setDisable(true);
        }
        modifierButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        supprimerButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        buttonContainer.getChildren().addAll(modifierButton, supprimerButton);

        details.getChildren().addAll(nomAbonnement, nomOffre, nomSalle, dateDebut, dateFin, status,buttonContainer);

        // Ajouter les images de l'abonnement
        VBox imageContainer = new VBox(5);
        String defaultImagePath = "C:\\Users\\VICTUS\\Downloads\\pfc gym.png";
        try {
            ImageView defaultImageView = new ImageView();
            Image defaultImage = new Image(new File(defaultImagePath).toURI().toString());
            defaultImageView.setImage(defaultImage);
            defaultImageView.setFitWidth(100);
            defaultImageView.setFitHeight(100);
            imageContainer.getChildren().add(defaultImageView);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("⚠ Impossible de charger l'image par défaut !");
        }



        HBox cardContainer = new HBox(15);  // Conteneur pour la carte
        cardContainer.getChildren().addAll( imageContainer,details);
        card.getChildren().add(cardContainer);

        return card;
    }
    @FXML
    public void modifierAbonnement(Abonnement abonnementSelectionne) {
        // Vérifier si l'abonnement sélectionné est null
        if (abonnementSelectionne == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucun abonnement sélectionné");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un abonnement à modifier.");
            alert.showAndWait();
            return;
        }

        // Vérifier si l'abonnement a le statut "non payé"
        if (!"non payé".equals(abonnementSelectionne.getStatus())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Modification impossible");
            alert.setHeaderText(null);
            alert.setContentText("Vous ne pouvez modifier que les abonnements avec le statut 'Non payé'.");
            alert.showAndWait();
            return;
        }

        try {
            // Chargement de la fenêtre de modification (FXML)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierAbonnement.fxml"));
            Parent root = loader.load();

            // Récupérer le contrôleur de la fenêtre de modification et passer l'abonnement sélectionné
            ModifierAbonnement controller = loader.getController();
            controller.setAbonnement(abonnementSelectionne);
            controller.setVoirAbonnementController(this);

            // Créer et afficher la nouvelle fenêtre
            Stage stage = new Stage();
            stage.setTitle("Modifier Abonnement");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            // Gestion d'erreur en cas de problème avec le chargement du fichier FXML
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Une erreur est survenue lors du chargement de la page de modification.");
            alert.showAndWait();
        }
    }
    @FXML
    public void supprimerAbonnement(Abonnement abonnementSelectionne) {
        // Vérifier si l'abonnement sélectionné est null
        if (abonnementSelectionne == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucun abonnement sélectionné");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un abonnement à supprimer.");
            alert.showAndWait();
            return;
        }

        // Vérifier que l'abonnement a le statut "non payé"
        if (!"non payé".equals(abonnementSelectionne.getStatus())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Suppression impossible");
            alert.setHeaderText(null);
            alert.setContentText("Vous ne pouvez supprimer que les abonnements avec le statut 'Non payé'.");
            alert.showAndWait();
            return;
        }

        // Demander confirmation avant de supprimer
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirmation de suppression");
        confirmation.setHeaderText("Êtes-vous sûr de vouloir supprimer cet abonnement ?");
        confirmation.setContentText("Cette action est irréversible.");

        if (confirmation.showAndWait().get() == ButtonType.OK) {
            try {
                AbonnementServices abonnementServices = new AbonnementServices();
                int i = abonnementServices.delete(abonnementSelectionne.getIdAbonnement());

                // Rafraîchir la liste des abonnements après la suppression
                afficherAbonnements(null);

                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Suppression réussie");
                successAlert.setHeaderText(null);
                successAlert.setContentText("L'abonnement a été supprimé avec succès.");
                successAlert.showAndWait();

            } catch (SQLException e) {
                e.printStackTrace();
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Erreur");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Une erreur est survenue lors de la suppression de l'abonnement.");
                errorAlert.showAndWait();
            }
        }
    }

    public void rafraichirAbonnements() {
        try {
            // Récupérer l'utilisateur connecté et la salle associée
            recupererUtilisateurConnecte();
            boolean hasUnpaid = false;
            // Appel à la méthode pour récupérer les abonnements actuels
            AbonnementServices abonnementServices = new AbonnementServices();
            List<Abonnement> abonnements = abonnementServices.showAllAbonnement(salle, utilisateurConnecte);

            // Vider le conteneur pour éviter les doublons
            abonnementsContainer.getChildren().clear();

            // Ajouter à nouveau tous les abonnements dans le conteneur
            for (Abonnement abonnement : abonnements) {
                HBox card = creerCarteAbonnement(abonnement);  // Créer la carte pour chaque abonnement
                abonnementsContainer.getChildren().add(card);
                if ("non payé".equals(abonnement.getStatus())) {
                    hasUnpaid = true; // Il y a au moins un abonnement non payé
                }
            }

            // Afficher le bouton "Payer" uniquement s'il y a des abonnements non payés
            btnPayer.setVisible(hasUnpaid);// Ajouter la carte dans le VBox

        } catch (SQLException e) {
            e.printStackTrace();
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Erreur");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Une erreur est survenue lors du rafraîchissement des abonnements.");
            errorAlert.showAndWait();
        }
    }
    @FXML
    private void handlePayButton() {
        try {
            recupererUtilisateurConnecte();

            // Service pour gérer les abonnements
            AbonnementServices abonnementService = new AbonnementServices();
            List<Abonnement> abonnements = abonnementService.showAllAbonnement(salle, utilisateurConnecte);

            // Calculer le total à payer uniquement pour les abonnements non payés
            double totalAmount = 0;
            for (Abonnement abonnement : abonnements) {
                if ("non payé".equals(abonnement.getStatus())) {
                    totalAmount += abonnement.getPrixOffre();
                }
            }

            if (totalAmount <= 0) {
                showError("Erreur de montant", "Aucun abonnement non payé trouvé.", "Veuillez vérifier les abonnements.");
                return;
            }

            // Créer une session de paiement Stripe
            StripePayment StripePaymentService = new StripePayment();
            String paymentUrl = StripePaymentService.createCheckoutSession(totalAmount);

            // Ouvrir l'URL de paiement dans un WebView
            WebView webView = new WebView();
            webView.getEngine().load(paymentUrl);

            // Afficher le WebView dans une nouvelle fenêtre
            Stage stage = new Stage();
            stage.setScene(new Scene(webView, 800, 600));
            stage.setTitle("Paiement Stripe");

            webView.getEngine().locationProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue.contains("success")) {
                    stage.close(); // Fermer la fenêtre WebView
                    // Récupérer l'email du client à partir de la session Stripe
                    String emailUtilisateur =this.utilisateurConnecte.getAdresse();
                    double totalAmountt = 0;
                    for (Abonnement abonnement : abonnements) {
                        if ("non payé".equals(abonnement.getStatus())) {
                            totalAmountt += abonnement.getPrixOffre();
                        }
                    }
                    // Envoyer l'email de confirmation
                    EmailService emailService = new EmailService();
                    String subject = "Confirmation de votre paiement";
                    String messageText = "Bonjour " + this.utilisateurConnecte.getNom() + ",\n\n" +
                            "Nous vous confirmons que votre paiement de "+totalAmountt+"DT pour les abonnements a été effectué avec succès.\n\n" +
                            "Merci de votre confiance.\n\n" +
                            "Cordialement,\n L'équipe de PFC Gym";
                    mettreAJourStatutAbonnements();
                    emailService.sendConfirmationEmail(emailUtilisateur, subject, messageText);
                } else if (newValue.contains("cancel")) {
                    stage.close(); // Fermer la fenêtre WebView
                    showError("Annulation", "Le paiement a été annulé.", "Veuillez réessayer.");
                }
            });

            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            showError("Erreur de paiement", "Une erreur s'est produite lors du paiement.", e.getMessage());
        }
    }
    private void showError(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR); // Utilisez AlertType.ERROR
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private void mettreAJourStatutAbonnements() {
        try {
            recupererUtilisateurConnecte();
            AbonnementServices abonnementService = new AbonnementServices();
            List<Abonnement> abonnements = abonnementService.showAllAbonnement(salle, utilisateurConnecte);

            for (Abonnement abonnement : abonnements) {
                if ("non payé".equals(abonnement.getStatus())) {
                    abonnementService.updateStatus(abonnement.getIdAbonnement(), "payé");
                }
            }

            // Rafraîchir la liste des abonnements affichés
            rafraichirAbonnements();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Paiement Réussi");
            alert.setHeaderText(null);
            alert.setContentText("Le paiement a été effectué avec succès ! Tous vos abonnements ont été mis à jour.");
            alert.showAndWait();
        } catch (SQLException e) {
            e.printStackTrace();
            showError("Erreur", "Mise à jour impossible", "Une erreur s'est produite lors de la mise à jour des abonnements.");
        }
    }

}


