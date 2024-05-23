// UIHelper.java
package com.example.demo2;

import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Utility class to help with setting up user interface components in JavaFX applications.
 * This class provides methods to configure buttons for both keyboard and mouse interaction,
 * enhancing accessibility and usability.
 */

public class UIHelper {

    public static void setupButton(Button button, String mnemonic, Runnable action) {
         /**
          * Configures a Button to respond to both mouse clicks and keyboard actions.
          * This method sets up a mnemonic for the button and assigns an action to be performed
          * when the button is either clicked with the mouse or triggered with the Enter key on the keyboard.
          * 
          * @param button The Button to be configured.
          * @param mnemonic The text for the Button, with an underscore character (_) indicating the mnemonic.
          *                 The mnemonic is a keyboard shortcut which activates the button when combined with the Alt key.
          * @param action The action to be executed when the Button is activated.
          */
         
      
        button.setMnemonicParsing(true); // Enables parsing of the mnemonic
        button.setText("_" + mnemonic);  // Set mnemonic (underscore indicates the mnemonic key)
        button.setOnAction(e -> action.run()); // Sets the action to be performed when the button is clicked
        button.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {  // Sets the action to be performed when Enter is pressed while the button is focused
                action.run();
            }
        });
    }
}
