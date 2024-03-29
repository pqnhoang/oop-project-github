package program.screen.controller.event;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import program.entity.event.Event;
import program.entity.store.Store;
import program.screen.controller.components.ContentController;

import java.util.Objects;

public class EventTableController {
    @FXML
    private GridPane gridPane;
    @FXML
    private VBox paginationContainer;

    @FXML
    public void initialize() {
        viewScreenList();
        Store.filteredEvents.addListener((ListChangeListener<Event>) c -> {
            viewScreenList();
        });
    }

    // CREATE UI FROM DATA
    // hiện lên screen danh sách các event
    private void viewScreenList() {
        // clear old data
        paginationContainer.getChildren().clear();

        if(Store.filteredEvents.isEmpty()) {
            Label emptyLabel = new Label();
            emptyLabel.getStyleClass().add("text-title");
            emptyLabel.setText("No result!");
            gridPane.getChildren().clear();
            gridPane.getChildren().add(emptyLabel);
            paginationContainer.getChildren().add(gridPane);
        } else {
            //Create pagination
            Pagination pagination = new Pagination();
            pagination.setPageCount(Store.filteredEvents.size()/12 + 1);
            pagination.setCurrentPageIndex(0);
            pagination.setMaxPageIndicatorCount(5);

            pagination.setPageFactory((pageIndex) -> {
                gridPane.getChildren().clear();

                int gridCol = 0;
                int gridRow = 0;

                int startItemIndex = 12 * pagination.getCurrentPageIndex();
                int endItemIndex = startItemIndex + 12;
                if(endItemIndex > Store.filteredEvents.size()) endItemIndex = Store.filteredEvents.size();

                for (Event item: Store.filteredEvents.subList(startItemIndex, endItemIndex)){
                    VBox vBox = new VBox();
                    vBox.setMinWidth(200);

                    Label eventName = new Label(item.getName());
                    eventName.getStyleClass().add("text-title");
                    eventName.setCursor(Cursor.HAND);
                    eventName.setMaxWidth(200);
                    eventName.setWrapText(true);

                    ImageView eventImage = new ImageView();
                    Image image = null;
                    try {
                        image = new Image(Objects.requireNonNull(getClass().getResource("/program/data/img/event/event/" + item.getImgEvent())).openStream());
                    } catch (Exception e) {
                        image = null;
                    }
                    eventImage.setImage(image);
                    eventImage.setFitWidth(200);
                    eventImage.setFitHeight(250);

                    Text eventTime = new Text(item.getTime());
                    eventTime.setWrappingWidth(200);
                    eventTime.getStyleClass().add("text-description");

                    vBox.getChildren().addAll(eventImage, eventName,  eventTime);
                    vBox.setMaxWidth(200);

                    //constrait grid pane col and row index
                    GridPane.setColumnIndex(vBox, gridCol);
                    GridPane.setRowIndex(vBox, gridRow);

                    gridPane.getChildren().add(vBox);
                    gridCol++;
                    if (gridCol == 4){
                        gridCol = 0;
                        gridRow++;
                    }

                    // xu ly su kien click
                    eventName.setOnMouseClicked(e -> {
                        EventElementController eventDetailController = new EventElementController(item);
                        ContentController.switchToDetail(eventDetailController);
                    });
                }
                return new VBox(gridPane);
            });
            VBox paginationVBox = new VBox(pagination);
            paginationContainer.getChildren().add(paginationVBox);
        }
    }
}