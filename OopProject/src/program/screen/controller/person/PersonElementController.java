package program.screen.controller.person;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import program.entity.dynasty.Dynasty;
import program.entity.person.Person;
import program.entity.store.Store;
import program.screen.controller.base.ElementWithController;
import program.screen.controller.components.ContentController;
import program.screen.controller.dynasty.DynastyElementController;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class PersonElementController extends ElementWithController {

    @FXML
    private VBox mainContent;

    @FXML
    private VBox sideBar;

    private Button currentSideBarBtn;

    private final Person personData;

    public PersonElementController(Person personData) {
        this.personData = personData;
    }

    @FXML
    public void initialize() {
        initSideBar();
        viewInScreen(this.personData);
        Store.filteredPersons.addListener((ListChangeListener<Person>) c -> {
            initSideBar();
        });
    }

    public void initSideBar() {
        // clear old data
        sideBar.getChildren().clear();

        if(Store.filteredPersons.isEmpty()) {
            Label emptyLabel = new Label();
            emptyLabel.getStyleClass().add("empty-label");
            emptyLabel.setText("No result!");
            sideBar.getChildren().add(emptyLabel);
        }
        for (Person item: Store.filteredPersons) {
            Button sideBarBtn = new Button();
            sideBarBtn.setText("> " + item.getName());
            sideBarBtn.getStyleClass().add("side-bar-btn");
            if(Objects.equals(item.getId(), personData.getId())) {
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
    //hiện chi tiết từng person
    public void viewInScreen(Person currentPerson) {
        ImageView avatar = new ImageView();
        Image image = null;
        try {
            image = new Image(Objects.requireNonNull(getClass().getResource("/program/data/img/person/person"+ currentPerson.getId() +".png")).openStream());
        } catch (Exception e) {
            try {
                image = new Image(Objects.requireNonNull(getClass().getResource("/program/data/img/person/person/")).openStream());
            } catch (IOException ex) {
                image = null;
            }
        }
        avatar.setImage(image);
        avatar.setFitWidth(300);
        avatar.setFitHeight(450);

        Label personName = new Label(currentPerson.getName());
        personName.getStyleClass().add("title");
        personName.setPadding(new Insets(20, 0, 20, 0));
        personName.setWrapText(true);

        Label personGivenName = new Label("Tên gọi khác: " + currentPerson.getGivenName());
        personGivenName.getStyleClass().add("content");
        personGivenName.setPadding(new Insets(0, 0, 10, 0));
        personGivenName.setWrapText(true);

        Label personFather = new Label("Tên cha: " + currentPerson.getFather());
        personFather.getStyleClass().add("content");
        personFather.setPadding(new Insets(0, 0, 10, 0));
        personFather.setWrapText(true);

        Label personReign = new Label("Thời gian cai trị: " + currentPerson.getReign());
        personReign.getStyleClass().add("content");
        personReign.setPadding(new Insets(0, 0, 10, 0));
        personReign.setWrapText(true);

        Label personDateOfBirth = new Label("Sinh: " + currentPerson.getDateOfBirth());
        personDateOfBirth.getStyleClass().add("content");
        personDateOfBirth.setPadding(new Insets(0, 0, 10, 0));
        personDateOfBirth.setWrapText(true);

        Label personDateOfDeath = new Label("Mất: " + currentPerson.getDateOfDeath());
        personDateOfDeath.getStyleClass().add("content");
        personDateOfDeath.setPadding(new Insets(0, 0, 10, 0));
        personDateOfDeath.setWrapText(true);

        Text personDescription = new Text(currentPerson.getDescription());
        personDescription.getStyleClass().add("description");
        personDescription.setWrappingWidth(500);

        Label personDynasty = new Label();
        Dynasty dynasty = currentPerson.getDynasty();
        String text = "Triều Đại: ";
        if(dynasty != null) {
            text += currentPerson.getDynasty().getName();
            personDynasty.setOnMouseClicked(event -> {
                DynastyElementController dynastyDetailController = new DynastyElementController(dynasty);
                ContentController.switchToDetail(dynastyDetailController);
            });
        } else {
            text += "Không rõ";
        }

        personDynasty.setText(text);
        personDynasty.getStyleClass().add("dynasty");
        personDynasty.setPadding(new Insets(10, 0, 0, 0));
        personDynasty.setWrapText(true);

        mainContent.getChildren().clear();
        mainContent.getChildren().addAll(
                personName,
                personGivenName,
                personFather,
                personReign,
                personDateOfBirth,
                personDateOfDeath,
                avatar,
                personDescription,
                personDynasty
        );
    }
}
