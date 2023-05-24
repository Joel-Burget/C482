package kinkead.firstscreen;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;

import javafx.stage.Stage;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController extends JFrame implements Initializable {
    public static int selectedPart;

    //creating part table
    @FXML
    public TableView<Part> partTableData;
    @FXML
    public TableColumn<Part, Integer> partID;
    @FXML
    public TableColumn<Part, String> partName;
    @FXML
    public TableColumn<Part, Double> partInventoryLevel;
    @FXML
    public TableColumn<Part, Double> partPrice;

    //creating the product table
    @FXML
    private TableView<Product> productTableData;
    @FXML
    public TableColumn<Part, Integer> productID;
    @FXML
    public TableColumn<Part, String> productName;
    @FXML
    public TableColumn<Part, Double> productInventory;
    @FXML
    public TableColumn<Part, Double> productPrice;


    public JLabel searchFail = new JLabel("No results found.");

    @FXML
    private Label welcomeText;

    @FXML
    public RadioButton inHouseRadio;
    @FXML
    public RadioButton outsourcedRadio;

    @FXML
    public TextField partSearchBar;

    @FXML
    public TextField productSearchBar;

    /**
     * #Initialize
     * Sets up both tables for parts and products.
     *
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("I am initialized");

        partID.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partInventoryLevel.setCellValueFactory(new PropertyValueFactory<Part, Double>("stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        partTableData.setItems(Inventory.getAllParts());

        productID.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        productName.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        productInventory.setCellValueFactory(new PropertyValueFactory<Part, Double>("stock"));
        productPrice.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        productTableData.setItems(Inventory.getAllProducts());

}

    public void OnButtonClicked(ActionEvent actionEvent) {
        System.out.println("I am clicked");
    }

    public void OnExitClicked(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void addButton(ActionEvent actionEvent) throws IOException {
        Stage addPartForm = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("addPartForm.fxml"));
        addPartForm.setTitle("Add a Part");
        addPartForm.setScene(new Scene(root, 450, 450));
        addPartForm.show();
    }

    public void deleteButtonAction(javafx.event.ActionEvent actionEvent) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this item?");
              alert.showAndWait().ifPresent(response -> {
                if(response == ButtonType.OK){
                   Inventory.getAllParts().remove(selectedPart);
                 }
              });
    }


    public void modifyButtonAction(ActionEvent actionEvent) throws IOException {
        selectedPart = partTableData.getSelectionModel().getSelectedIndex() +1;
        Stage modifyPartForm = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("modifyPartForm.fxml"));
        modifyPartForm.setTitle("Modify a Part");
        modifyPartForm.setScene(new Scene(root, 450, 450));
        modifyPartForm.show();
    }

    public void addProductButton(ActionEvent actionEvent) throws IOException {
        Stage addPartForm = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("addProductForm.fxml"));
        addPartForm.setTitle("Add a Product");
        addPartForm.setScene(new Scene(root, 1000, 600));
        addPartForm.show();
    }

    public void deleteProductButtonAction(javafx.event.ActionEvent actionEvent) {
        selectedPart = productTableData.getSelectionModel().getSelectedIndex();
        if(Inventory.lookupProduct(selectedPart+1).getAllAssociatedParts().isEmpty() == false){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Cannot delete a product with associated parts.");
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this item?");
            alert.showAndWait().ifPresent(response -> {
                if(response == ButtonType.OK){
                    Inventory.getAllProducts().remove(selectedPart);
                }
            });
        }

    }

    public void modifyProductButtonAction(ActionEvent actionEvent) throws IOException {
        selectedPart = productTableData.getSelectionModel().getSelectedIndex() +1;
        Stage addPartForm = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("modifyProductForm.fxml"));
        addPartForm.setTitle("Modify a Product");
        addPartForm.setScene(new Scene(root, 1000, 600));
        addPartForm.show();
    }

    public static int getSelectedPart() {
        return selectedPart;
    }

    /**
     * #Search
     * Logic for the part search fields. Searches by part name or part ID number.
     *
     * RUNTIME ERROR with search if/else, search would find ID number but not name. Reworked into try/catch to successfully search
     * either term.
     *
     * FUTURE ENHANCEMENT add sorting to search to ensure parts are in the same order as the parts list. Could also re-work logic to
     * not rely on try/catch error catching to search either ID or name.
     */
    public void partSearch(ActionEvent actionEvent){

        ObservableList<Part> searchItems = FXCollections.observableArrayList();
        try{
            String search = partSearchBar.getText();

            for (Part part : Inventory.getAllParts()) {
                int searchInt = Integer.parseInt(search);
                if(part.getId() == searchInt){
                    searchItems.add(part);
                }
            }
            partTableData.setItems(searchItems);
        }
        catch(NumberFormatException e){
            String search = partSearchBar.getText();
            //Use Inventory lookup
            for (Part part : Inventory.getAllParts()) {
                if(part.getName().contains(search)){
                    searchItems.add(part);
                }
            }
            partTableData.setItems(searchItems);
        }
        if(searchItems.size() == 0){
            partTableData.setPlaceholder(new Label("No results found."));
        }
    }

    public void productSearch(ActionEvent actionEvent){
        ObservableList<Product> searchItems = FXCollections.observableArrayList();
        try{
            String search = productSearchBar.getText();

            for (Product product : Inventory.getAllProducts()) {
                int searchInt = Integer.parseInt(search);
                if(product.getId() == searchInt){
                    searchItems.add(product);
                }
            }
            productTableData.setItems(searchItems);
        }
        catch(NumberFormatException e){
            String search = productSearchBar.getText();
            //Use Inventory lookup
            for (Product product : Inventory.getAllProducts()) {
                if(product.getName().contains(search)){
                    searchItems.add(product);
                }
            }
            productTableData.setItems(searchItems);
        }
        if(searchItems.size() == 0){
            productTableData.setPlaceholder(new Label("No results found."));
        }
    }
}



