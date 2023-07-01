package app.screen.controller;

import java.io.IOException;

import app.history.storage.Storage;
import app.screen.controller.components.Transition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.Node;
public class HomeController {
	

    @FXML
    private BorderPane homePane;
    private Stage stage;

    @FXML
    void handleStartCrawl(ActionEvent event) {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Storage.crawl();
                    Storage.init();
                    Transition.startFadeTransition(homePane, "/app/screen/fxml/layout.fxml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread1.start();
        Transition.startFadeTransition(homePane, "/app/screen/fxml/crawler.fxml");
    }

    @FXML
    void handleStartView(ActionEvent event) throws IOException {
        Storage.init();
        Transition.startFadeTransition(homePane, "/app/screen/fxml/layout.fxml");
    }
    
    @FXML
    void handleExit(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    
}
