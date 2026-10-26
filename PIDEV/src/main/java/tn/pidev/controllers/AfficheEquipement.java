package tn.pidev.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.util.Duration;
import tn.pidev.entities.Equipement;
import tn.pidev.services.equipementService;

import java.sql.SQLException;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;

public class AfficheEquipement {

    @FXML
    private HBox planningHBox;
    @FXML
    private TextField searchField;
    private int idSalle;
    private equipementService equipService = new equipementService(); // Initialize the service here
    @FXML
    private FlowPane planningFlowPane;

    public void setIdSalle(int idSalle) {
        this.idSalle = idSalle;
        loadEquipements(); // Load equipment as soon as the idSalle is set
    }

    public void loadEquipements() {
        // Fetch the equipment data from the database for the specific idSalle
        List<Equipement> equipementList = equipService.afficher(idSalle);

        // Clear previous content in planningHBox (if needed)
        planningHBox.getChildren().clear();

        // Loop through the data and add a new VBox for each equipment item
        for (Equipement equipement : equipementList) {
            VBox equipementBox = createEquipementBox(equipement);
            planningHBox.getChildren().add(equipementBox); // Add the VBox to the HBox
        }
    }

    private VBox createEquipementBox(Equipement equipement) {
        VBox equipementBox = new VBox(10);
        equipementBox.setAlignment(Pos.CENTER);
        equipementBox.setStyle("-fx-background-color: #333333; -fx-padding: 15px; -fx-border-color: #2ef01d; -fx-border-width: 2px; -fx-border-radius: 10px;");
        equipementBox.setPrefWidth(280);  // Slightly increased width for a more balanced look
        equipementBox.setMinWidth(250);
        // Add margin to separate boxes visually
        VBox.setMargin(equipementBox, new Insets(10, 10, 10, 10));

        // Create labels for equipment details
        Label statutLabel = new Label("Statut: " + equipement.getStatut());
        statutLabel.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");

        Label nameLabel = new Label("Nom: " + equipement.getNom());
        nameLabel.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");

        // Create the Update button
        Button updateButton = new Button("Update");
        updateButton.setStyle("-fx-background-color: transparent; -fx-border-color: #2ef01d; -fx-text-fill: white; -fx-border-radius: 5px; -fx-padding: 8px 15px;");
        updateButton.setOnAction(event -> UpdateDirection(equipement.getRef()));

        // Create an ImageView for displaying the image(s)
        ImageView imageView = new ImageView();
        imageView.setFitHeight(100); // Set the height of the image
        imageView.setFitWidth(100);  // Set the width of the image
        imageView.setPreserveRatio(true); // Preserve the aspect ratio of the image


        HBox buttonHBox = new HBox(10); // 10px spacing between buttons
        buttonHBox.setStyle("-fx-spacing: 10px;");
        buttonHBox.getChildren().addAll(updateButton);
        // Add the image view to an HBox for layout purposes
        HBox imageBox = new HBox();
        imageBox.setAlignment(Pos.CENTER);
        imageBox.getChildren().add(imageView);

        // Get the image path (this should be the path to the sprite sheet)
        String imagePath = equipement.getImage();

        // Call the method to load the sprite sheet animation
        loadSpriteSheet(imagePath, imageView); // Pass the ImageView to update with frames

        // Add the labels, image, and button to the VBox
        equipementBox.getChildren().addAll(imageBox, statutLabel, nameLabel, updateButton);
        planningFlowPane.getChildren().add(equipementBox);
        return equipementBox;
    }


    private void loadSpriteSheet(String spriteSheetPath, ImageView imageView) {
        // Load the sprite sheet image (ensure the path is correct)
        Image spriteSheet = new Image("file:" + spriteSheetPath);

        // Check if the sprite sheet loaded successfully
        if (spriteSheet.isError()) {
            System.out.println("Error loading sprite sheet: " + spriteSheetPath);
            return;
        }

        // Define the frame dimensions (modify these based on your sprite sheet)
        int frameWidth = 500;  // Width of each frame in the sprite sheet (adjust accordingly)
        int frameHeight = 340; // Height of each frame in the sprite sheet

        // Set the ImageView size to fit the display (e.g., 100x100)
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        imageView.setPreserveRatio(true); // Ensure aspect ratio is preserved

        // Create a Timeline to animate through the frames
        Timeline timeline = new Timeline();
        int numberOfRows = 6;  // Example: 2 rows of frames in the sprite sheet
        int numberOfColumns = 6;  // Example: 4 columns of frames in the sprite sheet

        // Loop through each frame and add a KeyFrame to the Timeline
        for (int row = 0; row < numberOfRows; row++) {
            for (int col = 0; col < numberOfColumns; col++) {
                // Calculate the x and y positions to crop the sprite sheet (frame positions)
                int x = col * frameWidth;  // X position for cropping
                int y = row * frameHeight; // Y position for cropping

                // Create a KeyFrame for each frame
                int frameIndex = row * numberOfColumns + col;

                // Increase the duration to make it slower and smoother (e.g., 0.2 seconds per frame)
                KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.1 * frameIndex), event -> {
                    // Crop the sprite sheet to show the current frame
                    Image frameImage = new WritableImage(spriteSheet.getPixelReader(), x, y, frameWidth, frameHeight);

                    // Update the ImageView to display the current frame
                    imageView.setImage(frameImage);
                });

                // Add the KeyFrame to the Timeline
                timeline.getKeyFrames().add(keyFrame);
            }
        }

        // Set the Timeline to loop indefinitely (to keep animating the sprite sheet)
        timeline.setCycleCount(Timeline.INDEFINITE);

        // Start the animation
        timeline.play();
    }


    @FXML
    public void openAddPlanningPage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Ajouterequipement.fxml"));
            AnchorPane addPlanningPage = loader.load();

            // Get the controller of the new scene (AjoutEquipementController)
            AjoutEquipementController ajoutEquipementController = loader.getController();

            // Set the idSalle in the AjoutEquipementController
            ajoutEquipementController.setIdSalle(idSalle);

            Scene addPlanningScene = new Scene(addPlanningPage);

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(addPlanningScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void UpdateDirection(int ref) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateEquipement.fxml"));
            AnchorPane updatePlanningPage = loader.load();

            // Get the controller for the update page
            UpdateEquipement updateController = loader.getController();
            updateController.setIdPlanning(ref);

            Scene updatePlanningScene = new Scene(updatePlanningPage);
            Stage stage = (Stage) planningHBox.getScene().getWindow();  // Use planningHBox to get the current stage
            stage.setScene(updatePlanningScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void trierEquipementParNom() {
        try {
            List<Equipement> sortedEquipes = equipService.trierParNomEquipement(idSalle);

            // Clear the current content in HBox before adding sorted items
            planningHBox.getChildren().clear();

            // Populate the sorted equipment into the UI
            for (Equipement equipe : sortedEquipes) {
                // Use the same createEquipementBox method to ensure they are displayed in boxes like normal items
                VBox equipementBox = createEquipementBox(equipe);
                planningHBox.getChildren().add(equipementBox); // Add the VBox to the HBox for sorted items
            }
        } catch (SQLException e) {
            System.out.println("Error while sorting Equipements: " + e.getMessage());
        }
    }


    // Create HBox for equipment UI (for sorted list)
    private HBox createEquipementUI(Equipement equipe) {
        HBox box = new HBox();
        box.getChildren().add(new Label(equipe.getNom()));
        return box;
    }

    @FXML
    public void chercherParNomEquipement() {
        String searchQuery = searchField.getText().trim(); // Get the text from the search field

        if (searchQuery.isEmpty()) {
            // If search field is empty, load all equipment
            loadEquipements();
            return;
        }

        try {
            // Call the service to search equipment by name
            List<Equipement> searchResults = equipService.rechercherParNom(searchQuery, idSalle);

            // Clear previous content in planningHBox
            planningHBox.getChildren().clear();

            // Populate the search results into the UI
            for (Equipement equipement : searchResults) {
                VBox equipementBox = createEquipementBox(equipement); // Create the same styled box for each result
                planningHBox.getChildren().add(equipementBox);
            }
        } catch (SQLException e) {
            System.out.println("Error during equipment search: " + e.getMessage());
        }
    }
}
