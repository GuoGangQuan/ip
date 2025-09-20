package gloqi.ui;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Gloqi gloqi;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/userMe.png"));
    private Image gloqiImage = new Image(this.getClass().getResourceAsStream("/images/userGloqi.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Gloqi instance */
    public void setGloqi(Gloqi d) {
        this.gloqi = d;
        dialogContainer.getChildren().addAll(
                DialogBox.getGloqiDialog(gloqi.initialize(), gloqiImage),
                DialogBox.getGloqiDialog(gloqi.getGreeting(), gloqiImage)
        );
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Gloqi's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = gloqi.run(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getGloqiDialog(response, gloqiImage)
        );
        // Close on BYE
        if ("bye".equalsIgnoreCase(input.trim())) {
            closeWithDelay();
        }
        userInput.clear();


    }

    private void closeWithDelay() {
        PauseTransition delay = new PauseTransition(Duration.millis(500));
        delay.setOnFinished(e -> Platform.exit());
        delay.play();
    }

}
