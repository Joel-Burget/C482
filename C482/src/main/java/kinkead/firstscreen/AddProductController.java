package kinkead.firstscreen;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.io.Console;
import java.net.URL;
import java.util.ResourceBundle;

public class AddProductController extends JFrame{
    public Text warningLabel;
    ObservableList<Part> tempList = FXCollections.observableArrayList();

    private String name;
    private int inv;
    private double price;
    private int max;
    private int min;
    private int machineID;



    @FXML
    public TableView<Part> partTableData;

    //creating part table
    @FXML
    public TableColumn<Part, Integer> partID;
    @FXML
    public TableColumn<Part, String> partName;
    @FXML
    public TableColumn<Part, Double> partInventoryLevel;
    @FXML
    public TableColumn<Part, Double> partPrice;

    //creating associated part table
    @FXML
    public TableView<Part> asscPartTableData;
    @FXML
    public TableColumn<Part, Integer> asscpartID;
    @FXML
    public TableColumn<Part, String> asscpartName;
    @FXML
    public TableColumn<Part, Double> asscpartInventoryLevel;
    @FXML
    public TableColumn<Part, Double> asscpartPrice;

    @FXML
    private TextField nameField;
    @FXML
    private TextField invField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField minField;
    @FXML
    private TextField maxField;
    @FXML
    private TextField machineField;

    @FXML
    public TextField partSearchBar;

    @FXML
    private javafx.scene.control.Button closeButton;
    @FXML
    private javafx.scene.control.Button saveButton;

    /**
     * #Initialize
     * Sets the part and associated part tables. Sets a label if no parts have been added to the associated part list.
     */
    public void initialize() {


        partID.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partInventoryLevel.setCellValueFactory(new PropertyValueFactory<Part, Double>("stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        partTableData.setItems(Inventory.getAllParts());

        asscpartID.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        asscpartName.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        asscpartInventoryLevel.setCellValueFactory(new PropertyValueFactory<Part, Double>("stock"));
        asscpartPrice.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        asscPartTableData.setItems(tempList);

        if(tempList.size() == 0){
            asscPartTableData.setPlaceholder(new Label("No parts have been added."));
        }
    }

    /**
     * #SaveButton
     * Checks all the fields for correct data entered and saves as new products and adds to the list of all products.
     *
     */

    public void saveButtonAction(javafx.event.ActionEvent actionEvent){

        Stage stage = (Stage) closeButton.getScene().getWindow();

        try{
            System.out.println("Save button fired.");
            //getting new product from text fields
            if(nameField.getText() == null || nameField.getText() == ""){
                System.out.println("No name");
                warningLabel.setOpacity(1);
                nameField.setStyle("-fx-text-box-border: red;");
                throw new Exception("Invalid name");
            }
            name = nameField.getText();
            if(!invField.getText().matches("^[0-9]\\d*$")){
                warningLabel.setText("Please use a positive number.");
                warningLabel.setOpacity(1);
                invField.setStyle("-fx-text-box-border: red;");
                throw new Exception("Invalid inventory");
            }
            inv = Integer.parseInt(invField.getText());
            if(!priceField.getText().matches("^(?:[1-9]\\d*|0)?(?:\\.\\d+)?$")) {
                warningLabel.setText("Please use a positive number.");
                warningLabel.setOpacity(1);
                priceField.setStyle("-fx-text-box-border: red;");
                throw new Exception("Invalid minimum");
            }
            price = Double.parseDouble(priceField.getText());
            if(!minField.getText().matches("^[0-9]\\d*$")) {
                warningLabel.setText("Please use a positive number.");
                warningLabel.setOpacity(1);
                minField.setStyle("-fx-text-box-border: red;");
                throw new Exception("Invalid minimum");
            }
            min = Integer.parseInt(minField.getText());
            if(!maxField.getText().matches("^[0-9]\\d*$")) {
                warningLabel.setText("Please use a positive number.");
                warningLabel.setOpacity(1);
                maxField.setStyle("-fx-text-box-border: red;");
                throw new Exception("Invalid maximum");
            }
            max = Integer.parseInt(maxField.getText());

            if(min > max){
                warningLabel.setText("Min cannot be more than max");
                warningLabel.setOpacity(1);
                minField.setStyle("-fx-text-box-border: red;");
                throw new Exception("Invalid minimum");
            }

            if(inv < min){
                warningLabel.setText("Inventory cannot be less than minimum.");
                warningLabel.setOpacity(1);
                minField.setStyle("-fx-text-box-border: red;");
                throw new Exception("Invalid inventory");
            }

            //generating ID
            int productId = 0;
            for(int i=0;i<Inventory.getAllProducts().size()+1;i++) {
                productId = productId +1;
            }

            //adding to product list and closing window
            Product add = new Product(productId, name, price, inv, min, max);

            if(tempList.size() > 0){
                for (Part part : tempList) {
                    add.addAssociatedPart(part);
                }
            }

            Inventory.getAllProducts().add(add);
            stage.close();
        }catch (Exception e){
            System.out.println("Please complete all fields.");
        }
    }
    /**
     * #CancelButton
     * Closes the window/scene
     *
     */
    @FXML
    public void cancelButtonAction(javafx.event.ActionEvent actionEvent){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
    /**
     * #AddPart
     * Adds selected part from the part table to the associated part list and adds to associated part table
     *
     */
    @FXML
    public void addPart(){
        int tempPart = partTableData.getSelectionModel().getSelectedIndex();
        tempList.add(Inventory.lookupPart(tempPart +1));
    }
    /**
     * #RemovePart
     * Removes selected part from both associated part list and associated parts table
     *
     */
    @FXML
    public void removePart(){
        tempList.remove(asscPartTableData.getSelectionModel().getSelectedIndex());
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
}
