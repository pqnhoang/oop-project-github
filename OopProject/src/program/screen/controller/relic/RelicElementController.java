package program.screen.controller.relic;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import program.entity.relic.Relic;
import program.entity.store.Store;
import program.screen.controller.base.ElementWithController;
import program.screen.controller.components.Components;

import java.util.Objects;

public class RelicElementController extends ElementWithController {
    @FXML
    private VBox mainContent;
    @FXML
    private VBox sideBar;

    private Button currentSideBarBtn;

    private final Relic relicData;

    public RelicElementController(Relic relicData) {
        this.relicData = relicData;
    }

    @FXML
    public void initialize() {
        initSideBar();
        viewInScreen(this.relicData);
        Store.filteredRelics.addListener((ListChangeListener<Relic>) c -> {
            initSideBar();
        });
    }

    public void initSideBar() {
        // clear old data
        sideBar.getChildren().clear();

        if(Store.filteredRelics.isEmpty()) {
            Label emptyLabel = new Label();
            emptyLabel.getStyleClass().add("empty-label");
            emptyLabel.setText("No result!");
            sideBar.getChildren().add(emptyLabel);
        }
        for (Relic item: Store.filteredRelics) {
            Button sideBarBtn = new Button();
            sideBarBtn.setText("> " + item.getTitle());
            sideBarBtn.getStyleClass().add("side-bar-btn");
            if(Objects.equals(item.getId(), relicData.getId())) {
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
    //hiện chi tiết từng relic
    public void viewInScreen(Relic relicData) {
        //CREATE UI FROM DATA
        ImageView relicImage = new ImageView();
        Image image = null;
        try {
            image = new Image(Objects.requireNonNull(getClass().getResource("/program/data/img/relic/relic/"+ relicData.getImgRelic())).openStream());
        } catch (Exception e) {
            image = null;
        }
        relicImage.setImage(image);
        relicImage.setFitWidth(400);
        relicImage.setFitHeight(550);

        Label relicTitle = new Label(relicData.getTitle());
        relicTitle.getStyleClass().add("title");
        relicTitle.setPadding(new Insets(20, 0, 20, 0));
        relicTitle.setWrapText(true);

        Label relicContent = new Label(""+relicData.getContent());
        relicContent.getStyleClass().add("content");
        relicContent.setPadding(new Insets(0, 0, 10, 0));
        relicContent.setWrapText(true);

        Label relicDestination = new Label("Địa điểm: " + relicData.getAddress());
        relicDestination.getStyleClass().add("content");
        relicDestination.setPadding(new Insets(0, 0, 10, 0));
        relicDestination.setWrapText(true);

        GridPane personList = Components.returnPersonList(relicData.getRelatedHistoricalPerson());

        mainContent.getChildren().clear();
        mainContent.getChildren().addAll(
                relicTitle,
                relicDestination,
                relicImage,
                relicContent,
                personList
        );
    }
}