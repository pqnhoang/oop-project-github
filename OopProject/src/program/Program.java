package program;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;   
import javafx.scene.Scene;
import javafx.stage.Stage;
import program.screen.controller.components.Transition;

import java.util.Objects;

public class Program extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/program/screen/fxml/home.fxml")));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Vietnam History");
        stage.setResizable(false);
        stage.setScene(scene);
        Transition.setRootStage(stage);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
