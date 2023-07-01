package app.screen.controller;

import app.screen.controller.components.Transition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;


public class CrawlController {
 
	@FXML
	private BorderPane crawlPane;

    @FXML
    private Button backClick;

    @FXML
    void handleBackClick(ActionEvent event) {
        Transition.startFadeTransition(crawlPane, "/app/screen/fxml/home.fxml");
      }

    @FXML
    void initialize() {

    }
}
