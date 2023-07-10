package program.screen.controller.components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import program.entity.store.Store;
import program.screen.controller.base.ElementWithController;
import program.screen.controller.dynasty.DynastyElementController;
import program.screen.controller.dynasty.DynastyTableController;
import program.screen.controller.event.EventElementController;
import program.screen.controller.event.EventTableController;
import program.screen.controller.person.PersonElementController;
import program.screen.controller.person.PersonTableController;
import program.screen.controller.relic.RelicElementController;
import program.screen.controller.relic.RelicTableController;

import java.io.IOException;

public class ContentController {

    @FXML
    private static StackPane contentArea;

    @FXML
    private static Button dynastyBtn;

    @FXML
    private static Button eventBtn;

    @FXML
    private static Button festivalBtn;

    @FXML
    private static Button personBtn;

    @FXML
    private static Button relicBtn;

    @FXML
    private static TextField tfSearch;

    private static String currentPane = "unknown";

    public static void setContentArea(@SuppressWarnings("exports") StackPane contentArea) { ContentController.contentArea = contentArea; }
    public static void setDynastyBtn(@SuppressWarnings("exports") Button dynastyBtn) { ContentController.dynastyBtn = dynastyBtn; }
    public static void setEventBtn(@SuppressWarnings("exports") Button eventBtn) { ContentController.eventBtn = eventBtn; }
    public static void setFestivalBtn(@SuppressWarnings("exports") Button festivalBtn) { ContentController.festivalBtn = festivalBtn; }
    public static void setPersonBtn(@SuppressWarnings("exports") Button personBtn) { ContentController.personBtn = personBtn; }
    public static void setRelicBtn(@SuppressWarnings("exports") Button relicBtn) { ContentController.relicBtn = relicBtn; }
    public static void setTfSearch(@SuppressWarnings("exports") TextField tfSearch) { ContentController.tfSearch = tfSearch; }

    public static void switchToDetail(ElementWithController controller) {
        // xóa từ khóa đang tìm kiếm trong text field search
        tfSearch.setText("");

        if(controller instanceof DynastyElementController) {
            selectMenu(dynastyBtn);
            currentPane = "dynasty";
        }
        if(controller instanceof EventElementController) {
            selectMenu(eventBtn);
            currentPane = "event";
        }
        if(controller instanceof PersonElementController) {
            selectMenu(personBtn);
            currentPane = "person";
        }
        if(controller instanceof RelicElementController) {
            selectMenu(relicBtn);
            currentPane = "relic";
        }

        FXMLLoader loader = new FXMLLoader(ContentController.class.getResource("/program/screen/fxml/detail.fxml"));
        // Set controller cho detail page
        loader.setController(controller);
        Parent root = null;
        try {
            root = loader.load();
            contentArea.getChildren().clear();
            contentArea.getChildren().setAll(root);
        } catch (IOException ex) {
        }
    }

    public static void switchToDynasty() {
        // reset search input
        tfSearch.setText("");

        DynastyTableController dynastyController = new DynastyTableController();
        selectMenu(dynastyBtn);
        loadPane(dynastyController);
        currentPane = "dynasty";
    }

    public static void switchToEvent() {
        // reset search input
        tfSearch.setText("");

        EventTableController eventController = new EventTableController();
        selectMenu(eventBtn);
        loadPane(eventController);
        currentPane = "event";
    }

    public static void switchToFestival() {
        // xóa từ khóa đang tìm kiếm trong text field search
        tfSearch.setText("");
        selectMenu(festivalBtn);

        // xóa dữ liệu đang hiển thị trong content area
        contentArea.getChildren().clear();

        // trích xuất dữ liệu mới vào content area
        FXMLLoader loader = new FXMLLoader(ContentController.class.getResource("/program/screen/fxml/festival.fxml"));
        Parent root = null;
        try {
            root = loader.load();
            contentArea.getChildren().setAll(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        currentPane = "festival";
    }

    public static void switchToPerson() {
        // xóa từ khóa đang tìm kiếm trong text field search
        tfSearch.setText("");

        PersonTableController personController = new PersonTableController();
        selectMenu(personBtn);
        loadPane(personController);
        currentPane = "person";
    }

    public static void switchToRelic() {
        // xóa từ khóa đang tìm kiếm trong text field search
        tfSearch.setText("");

        RelicTableController relicController = new RelicTableController();
        selectMenu(relicBtn);
        loadPane(relicController);
        currentPane = "relic";
    }

    public static void handleSearch(String searchString) {
        switch (currentPane) {
            case "dynasty" -> {
                Store.searchDynasty(searchString);
            }
            case "event" -> {
                Store.searchEvent(searchString);
            }
            case "festival" -> {
                Store.searchFestival(searchString);
            }
            case "person" -> {
                Store.searchPerson(searchString);
            }
            case "relic" -> {
                Store.searchRelic(searchString);
            }
            default -> {}
        }
    }

    
     // method reset trạng thái css của menu đang được chọn trong head bar và đặt trạng thái mới cho menu được chọn
    private static void selectMenu(Button menuBtn) {
        // clear current navBar selected menu css
        switch (currentPane) {
            case "dynasty" -> {
                dynastyBtn.getStyleClass().add("menu-bar");
                dynastyBtn.getStyleClass().remove("selected-menu");
            }
            case "event" -> {
                eventBtn.getStyleClass().add("menu-bar");
                eventBtn.getStyleClass().remove("selected-menu");
            }
            case "festival" -> {
                festivalBtn.getStyleClass().add("menu-bar");
                festivalBtn.getStyleClass().remove("selected-menu");
            }
            case "person" -> {
                personBtn.getStyleClass().add("menu-bar");
                personBtn.getStyleClass().remove("selected-menu");
            }
            case "relic" -> {
                relicBtn.getStyleClass().add("menu-bar");
                relicBtn.getStyleClass().remove("selected-menu");
            }
            default -> {}
        }

        // set selected navBar selected menu
        menuBtn.getStyleClass().remove("menu-bar");
        menuBtn.getStyleClass().add("selected-menu");
    }
    
    // method trích xuất dữ liệu mới vào trong content area
    private static void loadPane(Object controller) {
        // xóa dữ liệu đang hiển thị trong content area
        contentArea.getChildren().clear();

        // trích xuất dữ liệu mới vào content area
        FXMLLoader loader = new FXMLLoader(ContentController.class.getResource("/program/screen/fxml/list.fxml"));
        loader.setController(controller);
        Parent root = null;
        try {
            root = loader.load();
            contentArea.getChildren().setAll(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
