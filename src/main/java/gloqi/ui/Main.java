package gloqi.ui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Gloqi using FXML.
 */
public class Main extends Application {

    private final Gloqi gloqi = new Gloqi();
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setTitle("Gloqi");
            stage.setScene(scene);
            // inject the Gloqi instance
            fxmlLoader.<MainWindow>getController().setGloqi(gloqi);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
