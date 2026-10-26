package tn.pidev.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;

public class MapController {

    @FXML
    private WebView mapWebView; // Reference to WebView in FXML
    @FXML
    private TextField placeTextField; // Reference to TextField in FXML

    @FXML
    public void initialize() {
        // Initialize map on startup (optional)
    }

    @FXML
    public void handleSearchButtonAction() {
        // Get the place name entered by the user
        String place = placeTextField.getText();

        if (!place.isEmpty()) {
            // Load map centered on the place entered by the user
            loadMapWithPlace(place);
        }
    }
    @FXML
    public void cancelAddPlanning(ActionEvent event) {
        try {
            // Load the AffichePlanning.fxml file (the previous page)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficheSalle.fxml"));
            AnchorPane affichePlanningPage = loader.load();

            // Create a new Scene with the loaded FXML
            Scene affichePlanningScene = new Scene(affichePlanningPage);

            // Get the current stage and set the new scene (going back to AffichePlanning)
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(affichePlanningScene);
            stage.show(); // Show the previous scene
        } catch (IOException e) {
            e.printStackTrace();
            // You can show an error dialog or log the error here
        }
    }

    private void loadMapWithPlace(String place) {
        // Create map HTML content with Leaflet (using your provided code)
        String mapHtmlContent = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Leaflet Map</title>\n" +
                "\n" +
                "    <!-- Leaflet CSS -->\n" +
                "    <link rel=\"stylesheet\" href=\"https://unpkg.com/leaflet@1.7.1/dist/leaflet.css\"/>\n" +
                "\n" +
                "    <!-- Leaflet JS -->\n" +
                "    <script src=\"https://unpkg.com/leaflet@1.7.1/dist/leaflet.js\"></script>\n" +
                "    <style>\n" +
                "        /* Set the size of the map container */\n" +
                "        #map { height: 100%; width: 100%; }\n" +
                "        html, body { height: 100%; margin: 0; }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<!-- Map container -->\n" +
                "<div id=\"map\"></div>\n" +
                "\n" +
                "<script>\n" +
                "    var map = L.map('map').setView([33.8869, 9.5375], 7); // Default view (Tunisia as fallback)\n" +
                "    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {\n" +
                "        attribution: '&copy; <a href=\"https://www.openstreetmap.org/copyright\">OpenStreetMap</a> contributors'\n" +
                "    }).addTo(map);\n" +
                "\n" +
                "    // Use Nominatim API for searching the place\n" +
                "    var searchQuery = '" + place + "';\n" +
                "    var geocoderUrl = 'https://nominatim.openstreetmap.org/search?q=' + encodeURIComponent(searchQuery) + '&format=json';\n" +
                "\n" +
                "    // Fetch location data using Nominatim API\n" +
                "    fetch(geocoderUrl)\n" +
                "        .then(response => response.json())\n" +
                "        .then(data => {\n" +
                "            if (data.length > 0) {\n" +
                "                var lat = data[0].lat;\n" +
                "                var lon = data[0].lon;\n" +
                "                map.setView([lat, lon], 13); // Zoom into the location\n" +
                "                var marker = L.marker([lat, lon]).addTo(map);\n" +
                "                marker.bindPopup('<b>' + data[0].display_name + '</b><br>' + lat + ', ' + lon).openPopup();\n" +
                "            } else {\n" +
                "                alert('Location not found');\n" +
                "            }\n" +
                "        })\n" +
                "        .catch(error => alert('Error: ' + error));\n" +
                "</script>\n" +
                "</body>\n" +
                "</html>\n";

        // Load the map HTML content into the WebView
        WebEngine webEngine = mapWebView.getEngine();
        webEngine.loadContent(mapHtmlContent);
    }
}
