package program.screen.controller.event;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import program.entity.event.Event;
import program.entity.store.Store;
import program.screen.controller.base.ElementWithController;
import program.screen.controller.components.Components;

import java.util.Objects;

public class EventElementController extends ElementWithController {
    @FXML
    private VBox mainContent;
    @FXML
    private VBox sideBar;

    private Button currentSideBarBtn;

    private final Event eventData;

    public EventElementController(Event eventData) {
        this.eventData = eventData;
    }

    @FXML
    public void initialize() {
        initSideBar();
        viewInScreen(this.eventData);
        Store.filteredEvents.addListener((ListChangeListener<Event>) c -> {
            initSideBar();
        });
    }

    public void initSideBar() {
        // clear old data
        sideBar.getChildren().clear();

        if(Store.filteredEvents.isEmpty()) {
            Label emptyLabel = new Label();
            emptyLabel.getStyleClass().add("empty-label");
            emptyLabel.setText("No result!");
            sideBar.getChildren().add(emptyLabel);
        }
        for (Event item: Store.filteredEvents) {
            Button sideBarBtn = new Button();
            sideBarBtn.setText("> " + item.getName());
            sideBarBtn.getStyleClass().add("side-bar-btn");
            if(Objects.equals(item.getId(), eventData.getId())) {
                currentSideBarBtn = sideBarBtn;
                sideBarBtn.getStyleClass().add("current-content-btn");
            }

            sideBarBtn.setOnAction(event -> {
                currentSideBarBtn.getStyleClass().remove("current-content-btn");
                sideBarBtn.getStyleClass().add("current-content-btn");
                currentSideBarBtn = sideBarBtn;
                viewInScreen(item);
            });

            sideBar.getChildren().add(sideBarBtn);
        }
    }

    //CREATE UI FROM DATA
    //hiện chi tiết từng event
    public void viewInScreen(Event eventData) {
        ImageView eventImage = new ImageView();
        Image image = null;
        try {
            image = new Image(Objects.requireNonNull(getClass().getResource("/program/data/img/event/event/" + eventData.getImgEvent())).openStream());
        } catch (Exception e) {
            image = null;
        }
        eventImage.setImage(image);
        eventImage.setFitWidth(400);
        eventImage.setFitHeight(550);

        Label eventName = new Label(eventData.getName());
        eventName.getStyleClass().add("title");
        eventName.setPadding(new Insets(20, 0, 20, 0));
        eventName.setWrapText(true);

        Label eventTime = new Label("Thời gian: " + eventData.getTime());
        eventTime.getStyleClass().add("content");
        eventTime.setPadding(new Insets(0, 0, 10, 0));
        eventTime.setWrapText(true);

        Label eventDestination = new Label("Địa điểm: " + eventData.getDestination());
        eventDestination.getStyleClass().add("content");
        eventDestination.setPadding(new Insets(0, 0, 10, 0));
        eventDestination.setWrapText(true);

        Label eventDescription = new Label("Thông tin chi tiết: " + eventData.getDescription());
        eventDescription.getStyleClass().add("content");
        eventDescription.setPadding(new Insets(0, 0, 10, 0));
        eventDescription.setWrapText(true);

        GridPane personList = Components.returnPersonList(eventData.getRelativeEventPersons());

        mainContent.getChildren().clear();
        mainContent.getChildren().addAll(
                eventName,
                eventTime,
                eventDestination,
                eventImage,
                eventDescription,
                personList
        );
    }
}