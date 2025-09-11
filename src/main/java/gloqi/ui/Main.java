package gloqi.ui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Duke using FXML.
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) {
        try {
            Gloqi gloqi = new Gloqi();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            // inject the Duke instance
            fxmlLoader.<MainWindow>getController().setGloqi(gloqi);
            stage.show();
        } catch (IOException | GloqiException e) {
            e.printStackTrace();
        }
    }
}
