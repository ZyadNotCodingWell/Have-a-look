package cyBooks;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.Date; 
import java.time.LocalDate; 

/**
 * This class represents a JavaFX application for modifying user information.
 */
public class UserModification extends Application {

    /**
     * Starts the JavaFX application by creating a window for modifying user information.
     *
     * @param primaryStage The primary stage for this application.
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Modification des informations");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(10);

        Label idLabel = new Label("Id: ");
        TextField idField = new TextField();
        Label nameLabel = new Label("Name: ");
        TextField nameField = new TextField();
        Label surnameLabel = new Label("Surname: ");
        TextField surnameField = new TextField();
        Label dobLabel = new Label("Date of Birth: ");
        DatePicker dobPicker = new DatePicker();
        Label emailLabel = new Label("Email: ");
        TextField emailField = new TextField();

        Button submitButton = new Button("Modifier");

        /**
         * Event handler for the submit button. Updates user information if all fields are filled.
         */
        submitButton.setOnAction(event -> {
            String id = idField.getText();
            String newName = nameField.getText();
            String newSurname = surnameField.getText();;
            LocalDate newDobLocalDate = dobPicker.getValue(); 
            String newEmail = emailField.getText();

            if (id.isEmpty() || newName.isEmpty() || newSurname.isEmpty() || newDobLocalDate == null || newEmail.isEmpty()) {
                System.out.println("Tous les champs doivent être remplis.");
            } else {
                User user = UserDatabase.getUserById(id);
                if (user != null) {
                    user.setName(newName);
                    user.setSurname(newSurname);
                    Date newDob = Date.valueOf(newDobLocalDate); 
                    user.setDateOfBirth(newDob); 
                    user.setEmail(newEmail);
                    System.out.println("Informations mises à jour pour l'utilisateur : " + user);
                } else {
                    System.out.println("Aucun utilisateur trouvé avec l'ID : " + id);
                }

                idField.clear();
                nameField.clear();
                surnameField.clear();
                dobPicker.setValue(null);
                emailField.clear();
            }
        });

        grid.add(idLabel, 0, 0);
        grid.add(idField, 1, 0);
        grid.add(nameLabel, 0, 1);
        grid.add(nameField, 1, 1);
        grid.add(surnameLabel, 0, 2);
        grid.add(surnameField, 1, 2);
        grid.add(dobLabel, 0, 4);
        grid.add(dobPicker, 1, 4);
        grid.add(emailLabel, 0, 5);
        grid.add(emailField, 1, 5);
        grid.add(submitButton, 0, 6, 2, 1);

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
