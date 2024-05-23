package JavaFX;

import cyBooks.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class BorrowDetails extends Application {

    private Borrows borrow;

    public BorrowDetails(Borrows borrow) {
        this.borrow = borrow;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Borrow Details");

        GridPane grid = new GridPane();
        Label idLabel = new Label("Borrow ID:");
        Label idValue = new Label(String.valueOf(borrow.getId()));
        Label userIdLabel = new Label("User ID:");
        Label userIdValue = new Label(borrow.getUser().getId());
        Label isbnLabel = new Label("Book ISBN:");
        Label isbnValue = new Label(borrow.getBook().getISBN());
        Label dateOfOperationLabel = new Label("Date of Operation:");
        Label dateOfOperationValue = new Label(borrow.getDateOfOperation().toString());
        Label dateOfReturnLabel = new Label("Date of Return:");
        Label dateOfReturnValue = new Label(borrow.getDateOfReturn().toString());
        Label statusLabel = new Label("Status:");
        Label statusValue = new Label(borrow.getStatus().toString());

        Button turnBackButton = new Button("Turn Back");
        Button returnButton = new Button("Return");

        turnBackButton.setOnAction(e -> {
            try {
                borrow.returnBook();
                System.out.println("Book returned successfully");
                new BorrowSearch().start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        returnButton.setOnAction(e -> new BorrowSearch().start(primaryStage));

        grid.add(idLabel, 0, 0);
        grid.add(idValue, 1, 0);
        grid.add(userIdLabel, 0, 1);
        grid.add(userIdValue, 1, 1);
        grid.add(isbnLabel, 0, 2);
        grid.add(isbnValue, 1, 2);
        grid.add(dateOfOperationLabel, 0, 3);
        grid.add(dateOfOperationValue, 1, 3);
        grid.add(dateOfReturnLabel, 0, 4);
        grid.add(dateOfReturnValue, 1, 4);
        grid.add(statusLabel, 0, 5);
        grid.add(statusValue, 1, 5);
        grid.add(turnBackButton, 0, 6);
        grid.add(returnButton, 1, 6);

        Scene scene = new Scene(grid, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

