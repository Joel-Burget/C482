package kinkead.firstscreen;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * JavaDocs contained in project folder under JavaDocs folder
 *
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("mainForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 400);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        Part test = new InHouse(1, "2x4", 12.0, 12, 1, 10, 1);
        Part test2 = new Outsourced(2, "hex screws", 12.0, 12, 1, 10, "NoCo");
        Part test3 = new InHouse(3, "controller", 60.0, 12, 5, 20, 3);

        Inventory.getAllParts().add(test);
        Inventory.getAllParts().add(test2);
        Inventory.getAllParts().add(test3);

        Product wall = new Product(1, "wall", 100.00, 1, 1, 1);
        Product playstation = new Product(2, "Playstation", 100.00, 12, 1, 1);
        Product xbox = new Product(3, "Xbox", 250, 1, 1, 10);

        Inventory.addProduct(wall);
        Inventory.addProduct(playstation);
        Inventory.addProduct(xbox);

        wall.addAssociatedPart(test);
        playstation.addAssociatedPart(test2);
        xbox.addAssociatedPart(test3);

        System.out.println(xbox.getAllAssociatedParts());


        launch();
    }
}