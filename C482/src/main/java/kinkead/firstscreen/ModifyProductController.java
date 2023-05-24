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
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyProductController extends JFrame{
    String name;
    public ObservableList<Part> tempList = FXCollections.observableArrayList();
    public Text warningLabel;

    private int inv;
    private double price;
    private int max;
    private int min;


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
    public TextField partSearchBar;

    @FXML
    private TextField idField;
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
    private javafx.scene.control.Button closeButton;
    @FXML
    private javafx.scene.control.Button saveButton;

    /**
     * #SaveButton
     * Checks which radio button is checked and adds parts based on which is selected, inHouse or outSourced. Also checks
     * data for corrected entry.
     *
     * RUNTIME
     * Several issues with checking for numbers that are positive non-zero. Solved by using regex.
     *
     * FUTURE ENHANCEMENTS
     * Could move error checking to its own method to clean up code.
     */

    public void initialize(){
        int id = MainController.getSelectedPart();

        //Setting temp product to contain data for text fields
        Product temp = Inventory.lookupProduct(id);
        String tempID = Integer.toString(id);

        System.out.println(temp);

        //setting text fields
        idField.setText(tempID);
        nameField.setText(temp.getName());
        invField.setText(Integer.toString(temp.getStock()));
        priceField.setText(Double.toString(temp.getPrice()));
        minField.setText(Integer.toString(temp.getMin()));
        maxField.setText(Integer.toString(temp.getMax()));

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

        if(temp.getAllAssociatedParts().size() == 0){
            asscPartTableData.setPlaceholder(new Label("No parts have been added."));
        }

        for (Part part : temp.getAllAssociatedParts()) {
            tempList.add(part);
        }
    }

    /**
     * #SaveButton
     * Checks which radio button is checked and adds parts based on which is selected, inHouse or outSourced. Also checks
     * data for corrected entry.
     *
     * RUNTIME
     * Several issues with checking for numbers that are positive non-zero. Solved by using regex.
     *
     * FUTURE ENHANCEMENTS
     * Could move error checking to its own method to clean up code.
     */

    public void saveButtonAction(javafx.event.ActionEvent actionEvent){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        int selectedPart = MainController.getSelectedPart() -1;

        //getting new part data from text fields
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


            //adding to product list and closing window
            Product add = new Product(MainController.getSelectedPart(), name, price, inv, min, max);

            if(tempList.size() > 0){
                for (Part part : tempList) {
                    add.addAssociatedPart(part);
                }
            }

            Product tempProduct= new Product(selectedPart +1, name, price, inv, min, max);
            for (Part part: tempList) {
                tempProduct.addAssociatedPart(part);
            }

            Inventory.updateProduct(selectedPart, tempProduct);


            stage.close();
        }catch (Exception e){
            System.out.println("Please complete all fields.");
        }

    }

    @FXML
    public void cancelButtonAction(javafx.event.ActionEvent actionEvent){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

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

    @FXML
    public void addPart(){
        int tempPart = partTableData.getSelectionModel().getSelectedIndex();
        tempList.add(Inventory.lookupPart(tempPart +1));
        System.out.println(Inventory.lookupPart(tempPart +1).getName());
    }

    @FXML
    public void removePart(){
        tempList.remove(asscPartTableData.getSelectionModel().getSelectedIndex());
    }


}
