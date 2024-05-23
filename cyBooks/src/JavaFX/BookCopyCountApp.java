package com.example.demo2;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class BookCopyCountApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Set the title of the primary stage
        primaryStage.setTitle("Book Search");

        // Create a grid pane and set its properties for alignment and padding
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(10);

        // Create label and text field for entering the book title
        Label titleLabel = new Label("Book Title: ");
        TextField titleField = new TextField();
        Button searchButton = new Button("Search");
        Label resultLabel = new Label();

        // Setting up button event handling using a lambda expression
        searchButton.setOnAction(event -> performSearch(titleField.getText(), resultLabel));

        // Handle key press event for the text field to allow searching by pressing the Enter key
        titleField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                performSearch(titleField.getText(), resultLabel);
            }
        });

        // Add UI components to the grid
        grid.add(titleLabel, 0, 0);
        grid.add(titleField, 1, 0);
        grid.add(searchButton, 0, 1, 2, 1);
        grid.add(resultLabel, 0, 2, 2, 1);

        // Create a scene with the specified size and add it to the primary stage
        Scene scene = new Scene(grid, 400, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Method to handle search operation and update UI based on the response
    private void performSearch(String title, Label resultLabel) {
        try {
            String response = sendBookCopyCountRequest(title);
            resultLabel.setText("Number of Copies: " + response);
        } catch (Exception e) {
            e.printStackTrace();
            resultLabel.setText("Error during search.");
        }
    }

    // Method to send a HTTP POST request to get the number of book copies available
    private String sendBookCopyCountRequest(String title) throws Exception {
        URI uri = new URI("http://localhost:4567/book/copyCount");
        URL url = uri.toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; utf-8");
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoOutput(true);

        String jsonInputString = String.format("{\"title\": \"%s\"}", title);

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        try (Scanner scanner = new Scanner(connection.getInputStream(), StandardCharsets.UTF_8.name())) {
            return scanner.useDelimiter("\\A").next();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}


