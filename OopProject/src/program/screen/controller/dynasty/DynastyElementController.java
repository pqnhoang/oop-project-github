package program.screen.controller.dynasty;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import program.entity.dynasty.Dynasty;
import program.entity.store.Store;
import program.screen.controller.base.ElementWithController;
import program.screen.controller.components.Components;

import java.util.Objects;

public class DynastyElementController extends ElementWithController {

    @FXML
    private VBox mainContent;

    @FXML
    private VBox sideBar;

    private Button currentSideBarBtn;

    private final Dynasty dynastyData;

    public DynastyElementController(Dynasty dynastyData) {
        this.dynastyData = dynastyData;
    }

    @FXML
    public void initialize() {
        initSideBar();
        initMainContent(this.dynastyData);
        Store.filteredDynasties.addListener((ListChangeListener<Dynasty>) c -> {
            initSideBar();
        });
    }

    public void initSideBar() {
        // clear old data
        sideBar.getChildren().clear();

        if(Store.filteredDynasties.isEmpty()) {
            Label emptyLabel = new Label();
            emptyLabel.getStyleClass().add("empty-label");
            emptyLabel.setText("No result!");
            sideBar.getChildren().add(emptyLabel);
        }
        for (Dynasty item: Store.filteredDynasties) {
            Button sideBarBtn = new Button();
            sideBarBtn.setText("> " + item.getName());
            sideBarBtn.getStyleClass().add("side-bar-btn");
            if(Objects.equals(item.getId(), dynastyData.getId())) {
                currentSideBarBtn = sideBarBtn;
                sideBarBtn.getStyleClass().add("current-content-btn");
            }

            sideBarBtn.setOnAction(event -> {
                currentSideBarBtn.getStyleClass().remove("current-content-btn");
                sideBarBtn.getStyleClass().add("current-content-btn");
                currentSideBarBtn = sideBarBtn;
                initMainContent(item);
            });

            sideBar.getChildren().add(sideBarBtn);
        }
    }

    public void initMainContent(Dynasty dynastyData) {
        Label dynastyName = new Label(dynastyData.getName());
        dynastyName.getStyleClass().add("title");
        dynastyName.setPadding(new Insets(10, 0, 10, 0));
        dynastyName.setWrapText(true);

        Label exitedTime = new Label("Thời gian tồn tại: " + dynastyData.getTimeExist());
        exitedTime.getStyleClass().add("sub-title");
        exitedTime.setPadding(new Insets(0, 0, 10, 0));
        exitedTime.setWrapText(true);

        Label capital = new Label("Kinh đô: " + dynastyData.getCapital());
        capital.getStyleClass().add("description");
        capital.setPadding(new Insets(0, 0, 10, 0));
        capital.setWrapText(true);

        GridPane personList = Components.returnPersonList(dynastyData.getKing());

        mainContent.getChildren().clear();
        mainContent.getChildren().addAll(
                dynastyName,
                exitedTime,
                capital,
                personList
        );
    }
}
