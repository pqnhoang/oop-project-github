package program.screen.controller.dynasty;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import program.entity.dynasty.Dynasty;
import program.entity.store.Store;
import program.screen.controller.components.ContentController;

public class DynastyTableController {
    @FXML
    private GridPane gridPane;

    @FXML
    public void initialize() {
        initContent();
        Store.filteredDynasties.addListener((ListChangeListener<Dynasty>) c -> {
            initContent();
        });
    }

    private void initContent() {
        // clear old data
        gridPane.getChildren().clear();

        if(Store.filteredDynasties.isEmpty()) {
            Label emptyLabel = new Label();
            emptyLabel.getStyleClass().add("text-title");
            emptyLabel.setText("No result!");
            gridPane.getChildren().add(emptyLabel);
        } else {
            int gridCol = 0;
            int gridRow = 0;
            for (Dynasty item: Store.filteredDynasties){
                VBox vBox = new VBox();
                vBox.setMinWidth(200);

                Label dynastyName = new Label(item.getName());
                dynastyName.getStyleClass().add("text-title");
                dynastyName.setCursor(Cursor.HAND);

                vBox.getChildren().addAll(dynastyName);

                //constraint grid pane col and row index
                GridPane.setColumnIndex(vBox, gridCol);
                GridPane.setRowIndex(vBox, gridRow);

                gridPane.getChildren().add(vBox);
                gridCol++;
                if (gridCol == 4){
                    gridCol = 0;
                    gridRow++;
                }

                // xu ly su kien click
                dynastyName.setOnMouseClicked(e -> {
                    DynastyElementController dynastyDetailController = new DynastyElementController(item);
                    ContentController.switchToDetail(dynastyDetailController);
                });
            }
        }
    }
}
