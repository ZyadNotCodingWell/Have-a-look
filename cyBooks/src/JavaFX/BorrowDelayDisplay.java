package com.example.demo2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import cyBooks.BadStockArgumentException;
import cyBooks.Borrows;
import cyBooks.OpStatus;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class BorrowDelayDisplay extends Application {
	 @Override
	    public void start(Stage primaryStage) throws BadStockArgumentException {
	        primaryStage.setTitle("Late Return Display");
	  
	        // Creating a Borrows instance for demonstration
	        Borrows borrows = new Borrows(null, null);
	        borrows.setStatus(OpStatus.Late);
	        borrows.setDateOfReturn(new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(7))); // Seven days late for demonstration
	      
	        // Getting the late return time difference
	        String lateDuration = borrows.DeadlineTimeDiff();
	        
	        // Creating a label to display the late return duration
	        Label lateDurationLabel = new Label("Late Return Duration: " + lateDuration);
	       
	        // Creating a VBox layout to organize the UI elements
	        VBox vbox = new VBox(10);
	        vbox.getChildren().addAll(lateDurationLabel);
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
