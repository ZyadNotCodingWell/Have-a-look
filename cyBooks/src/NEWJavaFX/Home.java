package JavaFX;

import cyBooks.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Home extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Library Management System");

        Button userButton = new Button("User");
        userButton.setOnAction(e -> new UserSearch().start(primaryStage));

        Button bookButton = new Button("Book");
        bookButton.setOnAction(e -> new BookSearch().start(primaryStage));

        Button borrowButton = new Button("Borrow");
        borrowButton.setOnAction(e -> new BorrowSearch().start(primaryStage));

        Button logoutButton = new Button("Log out");
        logoutButton.setOnAction(e -> new Login().start(primaryStage));

        VBox vbox = new VBox(userButton, bookButton, borrowButton, logoutButton);
        Scene scene = new Scene(vbox, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
