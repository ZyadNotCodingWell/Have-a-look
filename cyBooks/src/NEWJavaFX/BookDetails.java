package JavaFX;

import cyBooks.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class BookDetails extends Application {

    private Book book;

    public BookDetails(Book book) {
        this.book = book;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Book Details");

        GridPane grid = new GridPane();
        Label isbnLabel = new Label("ISBN:");
        Label isbnValue = new Label(book.getISBN());
        Label titleLabel = new Label("Title:");
        Label titleValue = new Label(book.getTitle());
        Label authorLabel = new Label("Author:");
        Label authorValue = new Label(book.getAuthor());
        Label themeLabel = new Label("Theme:");
        Label themeValue = new Label(book.getTheme());
        Label copiesLabel = new Label("Copies:");
        Label copiesValue = new Label(String.valueOf(book.getNbOfCopies()));
        Label availableCopiesLabel = new Label("Available Copies:");
        Label availableCopiesValue = new Label(String.valueOf(book.getNbOfAvailableCopies()));

        Button returnButton = new Button("Return");

        returnButton.setOnAction(e -> new BookSearch().start(primaryStage));

        grid.add(isbnLabel, 0, 0);
        grid.add(isbnValue, 1, 0);
        grid.add(titleLabel, 0, 1);
        grid.add(titleValue, 1, 1);
        grid.add(authorLabel, 0, 2);
        grid.add(authorValue, 1, 2);
        grid.add(themeLabel, 0, 3);
        grid.add(themeValue, 1, 3);
        grid.add(copiesLabel, 0, 4);
        grid.add(copiesValue, 1, 4);
        grid.add(availableCopiesLabel, 0, 5);
        grid.add(availableCopiesValue, 1, 5);
        grid.add(returnButton, 1, 6);

        Scene scene = new Scene(grid, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
