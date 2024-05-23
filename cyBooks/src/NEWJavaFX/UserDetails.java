package JavaFX;

import cyBooks.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.SQLException;

public class UserDetails extends Application {

    private User user;

    public UserDetails(User user) {
        this.user = user;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("User Details");

        GridPane grid = new GridPane();
        Label idLabel = new Label("ID:");
        Label idValue = new Label(user.getId());
        Label nameLabel = new Label("Name:");
        Label nameValue = new Label(user.getName());
        Label surnameLabel = new Label("Surname:");
        Label surnameValue = new Label(user.getSurname());
        Label emailLabel = new Label("Email:");
        Label emailValue = new Label(user.getEmail());

        Button modifyButton = new Button("Modify");
        Button deleteButton = new Button("Delete");
        Button returnButton = new Button("Return");

        modifyButton.setOnAction(e -> new UserRegistration().start(primaryStage)); // You should create a UserModification class for modifying the user

        deleteButton.setOnAction(e -> {
            try {
                // Delete user from database
                String userId = user.getId();
                // Assuming you have a method to delete user by ID
                UserDatabase.deleteUser(userId); // Implement this method in UserDatabase
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            new UserSearch().start(primaryStage);
        });

        returnButton.setOnAction(e -> new UserSearch().start(primaryStage));

        grid.add(idLabel, 0, 0);
        grid.add(idValue, 1, 0);
        grid.add(nameLabel, 0, 1);
        grid.add(nameValue, 1, 1);
        grid.add(surnameLabel, 0, 2);
        grid.add(surnameValue, 1, 2);
        grid.add(emailLabel, 0, 3);
        grid.add(emailValue, 1, 3);
        grid.add(modifyButton, 0, 4);
        grid.add(deleteButton, 1, 4);
        grid.add(returnButton, 2, 4);

        Scene scene = new Scene(grid, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
