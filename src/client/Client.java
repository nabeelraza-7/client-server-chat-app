package client;

import java.io.File;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Client extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = new FXMLLoader(new File("src/client/ChatUI.fxml").toURI().toURL()).load();
        Scene scene = new Scene(root);
        stage.setTitle("Chat App");
        stage.setScene(scene);
        stage.show();

    }

}
