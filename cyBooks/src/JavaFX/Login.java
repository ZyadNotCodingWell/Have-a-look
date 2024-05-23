package com.example.demo2;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import cyBooks.Librarian; 

public class Login extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Login");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(10);

        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();

        Button loginButton = new Button("Login");

        // Setup the login button using UIHelper
        UIHelper.setupButton(loginButton, "Login", () -> {
            if (authenticate(usernameField.getText(), passwordField.getText())) {
                openRegistrationPage();
            } else {
                System.out.println("Incorrect credentials");
            }
        });

        grid.add(usernameLabel, 0, 0);
        grid.add(usernameField, 1, 0);
        grid.add(passwordLabel, 0, 1);
        grid.add(passwordField, 1, 1);
        grid.add(loginButton, 0, 2, 2, 1);

        Scene scene = new Scene(grid, 300, 150);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Method to authenticate the librarian
    private boolean authenticate(String username, String password) {
        // A modifier pour utiliser la classe Librarian du backend
        return username.equals("librarian") && password.equals("1234");
    }

    // Method to open registration or modification page once logged in
    private void openRegistrationPage() {
        // Modify as needed to open the appropriate page
        UserRegistration registrationPage = new UserRegistration();
        registrationPage.start(new Stage());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
