@echo off
javac --module-path "I/NEED/PATH/TO/JAVAFX/SDK/LIB" --add-modules javafx.controls,javafx.fxml -d out UserRegistration.java
java --module-path "DID/YOU/PUT/THAT/HERE/?" --add-modules javafx.controls,javafx.fxml -cp out UserRegistration
pause
