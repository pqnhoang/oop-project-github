package program.screen.controller.relic;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import program.entity.relic.Relic;
import program.entity.store.Store;
import program.screen.controller.components.ContentController;

import java.util.Objects;

public class RelicTableController {
    @FXML
    private GridPane gridPane;
    @FXML
    private VBox paginationContainer;

    @FXML
    public void initialize() {
        viewScreenList();
        Store.filteredRelics.addListener((ListChangeListener<Relic>) c -> {
            viewScreenList();
        });
    }
    //hiện lên danh sách các relics
    private void viewScreenList() {
        // clear old data
        paginationContainer.getChildren().clear();

        if(Store.filteredRelics.isEmpty()) {
            Label emptyLabel = new Label();
            emptyLabel.getStyleClass().add("text-title");
            emptyLabel.setText("No rusult!");
            gridPane.getChildren().clear();
            gridPane.getChildren().add(emptyLabel);
            paginationContainer.getChildren().add(gridPane);
        } else {
            //Create pagination
            Pagination pagination = new Pagination();
            pagination.setPageCount(Store.filteredRelics.size()/12 + 1);
            pagination.setCurrentPageIndex(0);
            pagination.setMaxPageIndicatorCount(5);

            pagination.setPageFactory((pageIndex) -> {
                gridPane.getChildren().clear();

                int gridCol = 0;
                int gridRow = 0;

                int startItemIndex = 12 * pagination.getCurrentPageIndex();
                int endItemIndex = startItemIndex + 12;
                if(endItemIndex > Store.filteredRelics.size()) endItemIndex = Store.filteredRelics.size();

                for (Relic item: Store.filteredRelics.subList(startItemIndex, endItemIndex)){
                    VBox vBox = new VBox();
                    vBox.setMinWidth(200);

                    Label relicTitle = new Label(item.getTitle());
                    relicTitle.getStyleClass().add("text-title");
                    relicTitle.setCursor(Cursor.HAND);
                    relicTitle.setMaxWidth(200);
                    relicTitle.setWrapText(true);

                    ImageView relicImage = new ImageView();
                    Image image = null;
                    try {
                        image = new Image(Objects.requireNonNull(getClass().getResource("/program/data/img/relic/relic/"+ item.getImgRelic())).openStream());
                    } catch (Exception e) {
                        image = null;
                    }
                    relicImage.setImage(image);
                    relicImage.setFitWidth(200);
                    relicImage.setFitHeight(250);

                    vBox.getChildren().addAll(relicImage, relicTitle);
                    vBox.setMaxWidth(200);

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
                    relicTitle.setOnMouseClicked(e -> {
                        RelicElementController relicDetailController = new RelicElementController(item);
                        ContentController.switchToDetail(relicDetailController);
                    });
                }
                return new VBox(gridPane);
            });
            VBox paginationVBox = new VBox(pagination);
            paginationContainer.getChildren().add(paginationVBox);
        }
        }
}
