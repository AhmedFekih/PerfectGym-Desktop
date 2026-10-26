package tn.pidev.controllers;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tn.pidev.entities.SalleDeSport;
import tn.pidev.services.salleDeSportService;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.sql.SQLException;
import java.util.List;

public class AfficheSalle {

    @FXML
    private HBox planningHBox;
    @FXML
    private TextField searchTextField;

    @FXML
    private TextField searchTextField1;
    @FXML
    private TextField searchTextField2;
    private List<String> salles;
    @FXML
    private FlowPane planningFlowPane;

    @FXML
    private Button searchButton;// This will hold the planning boxes horizontally

    public void initialize() {
        // Get planning data from the database
        salleDeSportService planningService = new salleDeSportService();
        List<SalleDeSport> planningList = planningService.afficher();

        // Loop through the data and add a new VBox for each planning
        for (SalleDeSport planning : planningList) {
            VBox planBox = new VBox(20); // Reduced spacing slightly to make it more compact
            planBox.setStyle("-fx-background-color: #333333; -fx-padding: 15px; -fx-border-color: #2ef01d; -fx-border-width: 2px; -fx-border-radius: 10px;");
            planBox.setPrefWidth(280);  // Slightly increased width for a more balanced look
            planBox.setMinWidth(250);

            // Create a label for the plan name
            Label planningLabel = new Label(planning.getNom());
            planningLabel.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");
            Label Loc = new Label(planning.getLocalisation());
            Loc.setStyle("-fx-text-fill: #bdbdbd; -fx-font-size: 14px;"); // Make sure Loc is visible and slightly lighter for contrast


            // Create an ImageView for the image
            String imagePath = planning.getImage();  // Assuming the Planning model has a getImagePath() method to get the image path
            Image planImage = new Image("file:" + imagePath); // Load the image from the file system (or database if stored there)
            ImageView imageView = new ImageView(planImage);
            imageView.setFitHeight(100); // Set the height of the image
            imageView.setFitWidth(80);  // Set the width of the image
            imageView.setPreserveRatio(true); // Preserve the aspect ratio of the image


            // Create a button for update
            Button updateButton = new Button("Update");
            updateButton.setStyle("-fx-background-color: transparent; -fx-border-color: #2ef01d; -fx-text-fill: white; -fx-border-radius: 5px; -fx-padding: 8px 15px;");
            Button updateButtonoffre = new Button("Update offre");
            updateButtonoffre.setStyle("-fx-background-color: transparent; -fx-border-color: #2ef01d; -fx-text-fill: white; -fx-border-radius: 5px; -fx-padding: 8px 15px;");
            // Create a button for equipment
            Button equipementButton = new Button("Equipement");
            equipementButton.setStyle("-fx-background-color: transparent; -fx-border-color: #2ef01d; -fx-text-fill: white; -fx-border-radius: 5px; -fx-padding: 8px 15px;");

            // Add an event handler to the update button to call UpdateDirection method
            updateButton.setOnAction(event -> UpdateDirection(planning.getIdSalle()));
            updateButtonoffre.setOnAction(event -> AfficherOffres(planning));  // Pass only the idSalle to UpdateDirection method
            equipementButton.setOnAction(event -> EquipDirection(planning.getIdSalle()));

            // Create a new HBox for both buttons
            HBox buttonHBox = new HBox(10); // 10px spacing between buttons
            buttonHBox.setStyle("-fx-spacing: 10px;"); // Optional: Adjust spacing between buttons

            // Add the buttons to the buttonHBox
            buttonHBox.getChildren().addAll( updateButton,updateButtonoffre, equipementButton);

            // Add the image, label, and buttons to the VBox
            HBox labelAndImage = new HBox(12); // HBox for label and image, with 10px spacing
            labelAndImage.getChildren().addAll(imageView, planningLabel, Loc);

            // Add the labelAndImage and buttonHBox to the VBox
            planBox.getChildren().addAll(labelAndImage, buttonHBox);

            // Add the planBox to the HBox for horizontal display
            planningFlowPane.getChildren().add(planBox);
        }
    }


    @FXML
    public void openAddPlanningPage(ActionEvent event) {
        try {
            // Load the AjoutePlanning.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouteSalle.fxml"));
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
    @FXML
    public void openmaps(ActionEvent event) {
        try {
            // Load the AjoutePlanning.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/search.fxml"));
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
        }}

    @FXML
    public void UpdateDirection(int idPlanning) {
        try {
            // Pass the idPlanning to the UpdatePlan.fxml page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateSalle.fxml"));
            AnchorPane updatePlanningPage = loader.load();

            // Get the controller for the UpdatePlan.fxml page
            UpdateSalle updatePlanController = loader.getController();

            // Pass the idPlanning to the controller
            updatePlanController.setIdSalle(idPlanning);

            // Create a new Scene with the loaded FXML
            Scene updatePlanningScene = new Scene(updatePlanningPage);

            // Get the current stage and set the new scene
            Stage stage = (Stage) planningHBox.getScene().getWindow();  // Fixed here: Use planningHBox as the reference
            stage.setScene(updatePlanningScene);
            stage.show(); // Show the new scene
        } catch (IOException e) {
            e.printStackTrace();
            // You can show an error dialog or log the error here
        }
    }
    @FXML
    public void AfficherOffres(SalleDeSport salle) {
        try {
            // Pass the idPlanning to the UpdatePlan.fxml page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherOffre.fxml"));
            Parent root = loader.load();

            // Get the controller for the UpdatePlan.fxml page
            VoirOffres AfficherOffre = loader.getController();
            // Pass the idPlanning to the controller
            AfficherOffre.setSalle(salle);
            // Create a new Scene with the loaded FXML
            Stage stage = (Stage) planningHBox.getScene().getWindow();
            stage.setTitle("les Offres");
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
            // You can show an error dialog or log the error here
        }
    }
    @FXML
    public void EquipDirection(int idPlanning) {
        System.out.println("ID Passed: " + idPlanning);  // Add this line for debugging
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficheEquipement.fxml"));
            AnchorPane updatePlanningPage = loader.load();

            AfficheEquipement updatePlanController = loader.getController();

            updatePlanController.setIdSalle(idPlanning);  // Pass the idSalle here

            Scene updatePlanningScene = new Scene(updatePlanningPage);
            Stage stage = (Stage) planningHBox.getScene().getWindow();
            stage.setScene(updatePlanningScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void sortSallesByName(ActionEvent event) {
        salleDeSportService planningService = new salleDeSportService();
        List<SalleDeSport> sortedList = planningService.afficher2();  // Already sorted alphabetically in afficher()

        // Clear current items in the HBox
        planningFlowPane.getChildren().clear();

        // Re-populate the HBox with sorted items
        for (SalleDeSport salle : sortedList) {
            // Use the same logic as in the initialize() method to create and add VBox
            VBox planBox = new VBox(20); // Reduced spacing slightly to make it more compact
            planBox.setStyle("-fx-background-color: #333333; -fx-padding: 15px; -fx-border-color: #2ef01d; -fx-border-width: 2px; -fx-border-radius: 10px;");
            planBox.setPrefWidth(280);  // Slightly increased width for a more balanced look
            planBox.setMinWidth(250);

            Label salleLabel = new Label(salle.getNom());
            salleLabel.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");

            String imagePath = salle.getImage();
            Image salleImage = new Image("file:" + imagePath);
            ImageView imageView = new ImageView(salleImage);
            imageView.setFitHeight(100);
            imageView.setFitWidth(80);
            imageView.setPreserveRatio(true);



            Button updateButton = new Button("Update");
            updateButton.setStyle("-fx-background-color: transparent; -fx-border-color: #2ef01d; -fx-text-fill: white; -fx-border-radius: 5px; -fx-padding: 8px 15px;");
            updateButton.setOnAction(e -> UpdateDirection(salle.getIdSalle()));

            Button updateButtonoffre = new Button("Update offre");
            updateButtonoffre.setStyle("-fx-background-color: transparent; -fx-border-color: #2ef01d; -fx-text-fill: white; -fx-border-radius: 5px; -fx-padding: 8px 15px;");
            updateButtonoffre.setOnAction(e -> AfficherOffres(salle));  // Pass only the idSalle to UpdateDirection method

            Button equipementButton = new Button("Equipement");
            equipementButton.setStyle("-fx-background-color: transparent; -fx-border-color: #2ef01d; -fx-text-fill: white; -fx-border-radius: 5px; -fx-padding: 8px 15px;");
            equipementButton.setOnAction(e -> EquipDirection(salle.getIdSalle()));

            HBox buttonHBox = new HBox(12);
            buttonHBox.getChildren().addAll(updateButton,updateButtonoffre, equipementButton);

            HBox labelAndImage = new HBox(12);
            labelAndImage.getChildren().addAll(imageView, salleLabel);

            planBox.getChildren().addAll(labelAndImage, buttonHBox);
            planningFlowPane.getChildren().add(planBox);
        }
    }

    @FXML
    public void handleSearch() {
        // Get the input from the TextField and split it by comma or space
        String input = searchTextField.getText();

        // Split by comma or space to handle multiple locations
        String[] locations = input.split("[,\\s]+"); // This regex splits by both commas and spaces

        try {
            salleDeSportService service = new salleDeSportService();
            // Call the search service to search by multiple locations
            List<SalleDeSport> result = service.searchByMultipleLocations(locations);
            // Do something with the result (e.g., update UI with the list of gyms)
            updateResults(result);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void updateResults(List<SalleDeSport> results) {
        // Clear current results
        planningFlowPane.getChildren().clear();

        // Add each result to the UI (you can use the same logic from the `initialize()` method)
        for (SalleDeSport salle : results) {
            VBox planBox = new VBox(30);
            planBox.setStyle("-fx-background-color: #333333; -fx-padding: 20px; -fx-border-color: #2ef01d; -fx-border-width: 2px;");
            planBox.setPrefWidth(250);
            planBox.setMinWidth(250);

            Label salleLabel = new Label(salle.getNom());
            salleLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");

            String imagePath = salle.getImage();
            Image salleImage = new Image("file:" + imagePath);
            ImageView imageView = new ImageView(salleImage);
            imageView.setFitHeight(100);
            imageView.setFitWidth(80);
            imageView.setPreserveRatio(true);


            Button updateButton = new Button("Update");
            updateButton.setStyle("-fx-background-color: transparent; -fx-border-color: #2ef01d; -fx-text-fill: white;");
            updateButton.setOnAction(e -> UpdateDirection(salle.getIdSalle()));

            Button equipementButton = new Button("Equipement");
            equipementButton.setStyle("-fx-background-color: transparent; -fx-border-color: #2ef01d; -fx-text-fill: white;");
            equipementButton.setOnAction(e -> EquipDirection(salle.getIdSalle()));

            HBox buttonHBox = new HBox(10);
            buttonHBox.getChildren().addAll(updateButton, equipementButton);

            HBox labelAndImage = new HBox(10);
            labelAndImage.getChildren().addAll(imageView, salleLabel);

            planBox.getChildren().addAll(labelAndImage, buttonHBox);
            planningHBox.getChildren().add(planBox);
        }
    }



    private void searchByTwoLocations(String loc1, String loc2) {
        // Logique pour filtrer et afficher les salles correspondant aux deux localisations
        System.out.println("Recherche des salles par deux localisations: " + loc1 + " et " + loc2);
        // Vous devrez implémenter cette méthode pour filtrer vos données en fonction de loc1 et loc2
    }

    private void searchByOneLocation(String loc) {
        // Logique pour filtrer et afficher les salles correspondant à une localisation
        System.out.println("Recherche des salles par une localisation: " + loc);
        // Vous devrez implémenter cette méthode pour filtrer vos données en fonction de loc
    }

    private void searchAll() {
        // Logique pour afficher toutes les salles si aucune localisation n'est spécifiée
        System.out.println("Recherche générale de toutes les salles.");
    }

}





