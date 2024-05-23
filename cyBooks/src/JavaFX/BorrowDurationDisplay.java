package com.example.demo2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import cyBooks.BadStockArgumentException;
import cyBooks.Borrows;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class BorrowDurationDisplay extends Application {

    @Override
    public void start(Stage primaryStage) throws BadStockArgumentException {
        primaryStage.setTitle("Borrow Duration Display");

        // Creating a Borrows instance for demonstration
        Borrows borrows = new Borrows(null, null);
        borrows.setDateOfReturn(new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(14))); // Borrowed 14 days ago for demonstration
        borrows.setDateOfReturn(new Date(System.currentTimeMillis()));

        // Getting the borrow duration
        String borrowDuration = borrows.getBorrowDuration();

        // Creating a label to display the borrow duration
        Label borrowDurationLabel = new Label("Borrow Duration: " + borrowDuration);

        // Creating a VBox layout to organize the UI elements
        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(borrowDurationLabel);
        vbox.setPrefSize(300, 200);

        // Creating the scene and setting it on the stage
        Scene scene = new Scene(vbox);
        primaryStage.setScene(scene);

        // Displaying the stage
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
