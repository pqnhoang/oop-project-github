package program.screen.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import program.screen.controller.components.Transition;


public class CrawlController {
 
	@FXML
	private BorderPane crawlPane;

    @FXML
    private Button backClick;

    @FXML
    void handleBackClick(ActionEvent event) {
        Transition.beginFadeTransition(crawlPane, "/program/screen/fxml/home.fxml");
      }

    @FXML
    void initialize() {

    }
}
