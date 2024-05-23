package JavaFX;

import cyBooks.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

/**
 * This class represents a JavaFX application for user registration.
 * Users can input their name, surname, email, and date of birth, and submit the data to be stored in a database.
 */
public class UserRegistration extends Application {

    /**
     * Starts the JavaFX application by creating a window for user registration.
     *
     * @param primaryStage The primary stage for this application.
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("User Registration");

        GridPane grid = new GridPane();
        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();
        Label surnameLabel = new Label("Surname:");
        TextField surnameField = new TextField();
        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();
        Label dobLabel = new Label("Date of Birth:");
        DatePicker dobPicker = new DatePicker();

        // Add a label for displaying error messages
        Label errorMessage = new Label();
        errorMessage.setStyle("-fx-text-fill: red;");

        Button saveButton = new Button("Save");
        Button returnButton = new Button("Return");

        /**
         * Event handler for the save button. Validates input fields and attempts to save user data to the database.
         */
        saveButton.setOnAction(e -> {
            // Generate a unique ID for the user
            String id = UUID.randomUUID().toString();
            String name = nameField.getText();
            String surname = surnameField.getText();
            String email = emailField.getText();
            LocalDate dobLocalDate = dobPicker.getValue();
            Date dob = (dobLocalDate != null) ? Date.valueOf(dobLocalDate) : null;

            // Validate input fields
            if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || dob == null) {
                errorMessage.setText("All fields must be filled.");
                return;
            }

            // Create a new User object and attempt to save it to the database
            User user = new User(id, surname, name, dob, email, 0);
            try {
                InsertIntoDataBase db = new InsertIntoDataBase();
                db.addUser(user);
                errorMessage.setText("User registered successfully!");
            } catch (SQLException ex) {
                ex.printStackTrace();
                errorMessage.setText("Error registering user.");
            }
        });

        /**
         * Event handler for the return button. Navigates back to the user search screen.
         */
        returnButton.setOnAction(e -> new UserSearch().start(primaryStage));

        // Add components to the grid layout
        grid.add(nameLabel, 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(surnameLabel, 0, 1);
        grid.add(surnameField, 1, 1);
        grid.add(emailLabel, 0, 2);
        grid.add(emailField, 1, 2);
        grid.add(dobLabel, 0, 3);
        grid.add(dobPicker, 1, 3);
        grid.add(saveButton, 0, 4);
        grid.add(returnButton, 1, 4);
        grid.add(errorMessage, 0, 5, 2, 1);  // Span the error message across two columns

        // Set up the scene and show the stage
        Scene scene = new Scene(grid, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * The main method to launch the application.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
