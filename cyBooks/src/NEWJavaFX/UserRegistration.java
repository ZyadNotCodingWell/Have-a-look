package JavaFX;

import cyBooks.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.UUID;

public class UserRegistration extends Application {

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

        Button saveButton = new Button("Save");
        Button returnButton = new Button("Return");

        saveButton.setOnAction(e -> {
            // Save user to database
            String id = UUID.randomUUID().toString();
            String name = nameField.getText();
            String surname = surnameField.getText();
            String email = emailField.getText();
            User user = new User(id, surname, name, null, email, 0);
            try {
                InsertIntoDataBase db = new InsertIntoDataBase();
                db.addUser(user);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        returnButton.setOnAction(e -> new UserSearch().start(primaryStage));

        grid.add(nameLabel, 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(surnameLabel, 0, 1);
        grid.add(surnameField, 1, 1);
        grid.add(emailLabel, 0, 2);
        grid.add(emailField, 1, 2);
        grid.add(saveButton, 0, 3);
        grid.add(returnButton, 1, 3);

        Scene scene = new Scene(grid, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
