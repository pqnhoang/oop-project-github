package program.screen.controller.components;

import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import program.entity.person.Person;
import program.screen.controller.person.PersonElementController;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class Components {   
   // gridPane chứa danh sách nhân vật gồm 1 ảnh kèm tên bên dưới
    @SuppressWarnings("exports")
	public static GridPane returnPersonList(List<Person> personObservableList) {
        GridPane gridPane = new GridPane();
        gridPane.setVgap(20);
        int row = 0;
        int column = 0;
        for(Person item: personObservableList) {
            VBox vBox = new VBox();
            vBox.setMinWidth(200);
            GridPane.setRowIndex(vBox, row);
            GridPane.setColumnIndex(vBox, column);

            Label kingName = new Label(item.getName());
            kingName.getStyleClass().add("king-name");
            kingName.setCursor(Cursor.HAND);

            kingName.setOnMouseClicked(e -> {
                PersonElementController personDetailController = new PersonElementController(item);
                ContentController.switchToDetail(personDetailController);
            });

            ImageView avatar = new ImageView();
            Image image = null;
            try {
                // Construct the image path based on the person's ID
                String imagePath = "/program/data/img/person/person/" + item.getId() + ".png";
                
                // Attempt to load the specific person image
                image = new Image(Objects.requireNonNull(Components.class.getResource(imagePath)).openStream());
                
                // If the image loading fails, or the image is not found, it will throw an exception
                // In that case, we will set the image to null and proceed to the catch block
            } catch (Exception e) {
                // Loading the specific person image failed or the image doesn't exist
                
                try {
                    // Load the default image ("/program/data/img/person/person/0.png")
                    image = new Image(Objects.requireNonNull(Components.class.getResource("/program/data/img/person/person/0.png")).openStream());
                } catch (IOException ex) {
                    // If loading the default image also fails, set the image to null
                    image = null;
                }
            }

            avatar.setImage(image);

            avatar.setFitWidth(150);
            avatar.setFitHeight(200);

            vBox.getChildren().addAll(avatar, kingName);
            gridPane.getChildren().add(vBox);

            column++;
            if (column == 3) {
                column = 0;
                row++;
            }
        }
        return gridPane;
    }
}
