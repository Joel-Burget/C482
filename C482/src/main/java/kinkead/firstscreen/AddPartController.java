package kinkead.firstscreen;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Border;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.io.Console;

public class AddPartController extends JFrame{
    public Text warningLabel;
    public Text machineIDText;
    private String name;
    private int inv;
    private double price;
    private int max;
    private int min;
    private int machineID;
    private String companyName;
    ToggleGroup partSource = new ToggleGroup();

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
    private RadioButton inHouse;
    @FXML
    public RadioButton outSourced;
    @FXML
    private javafx.scene.control.Button closeButton;
    @FXML
    private javafx.scene.control.Button saveButton;

    /**
     * #Initilize
     * creates toggle group for the radio buttons. Sets radio buttons to said group.
     */
    @FXML
    private void initialize(){
        ToggleGroup radioGroup = new ToggleGroup();
        inHouse.setToggleGroup(radioGroup);
        outSourced.setToggleGroup(radioGroup);
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
    public void saveButtonAction(javafx.event.ActionEvent actionEvent) {
        inHouse.setToggleGroup(partSource);
        inHouse.setSelected(true);
        outSourced.setToggleGroup(partSource);
        Stage stage = (Stage) closeButton.getScene().getWindow();

        if (inHouse.isSelected()) {
            try {
                System.out.println("Save button fired.");
                //getting new product from text fields
                if (nameField.getText() == null || nameField.getText() == "") {
                    System.out.println("No name");
                    warningLabel.setOpacity(1);
                    nameField.setStyle("-fx-text-box-border: red;");
                    throw new Exception("Invalid name");
                }
                name = nameField.getText();

                if (!invField.getText().matches("^[0-9]\\d*$")) {
                    warningLabel.setText("Please use a positive number.");
                    warningLabel.setOpacity(1);
                    invField.setStyle("-fx-text-box-border: red;");
                    throw new Exception("Invalid inventory");
                }
                inv = Integer.parseInt(invField.getText());

                if (!priceField.getText().matches("^(?:[1-9]\\d*|0)?(?:\\.\\d+)?$")) {
                    warningLabel.setText("Please use a positive number.");
                    warningLabel.setOpacity(1);
                    priceField.setStyle("-fx-text-box-border: red;");
                    throw new Exception("Invalid minimum");
                }
                price = Double.parseDouble(priceField.getText());

                if (!minField.getText().matches("^[0-9]\\d*$")) {
                    warningLabel.setText("Please use a positive number.");
                    warningLabel.setOpacity(1);
                    minField.setStyle("-fx-text-box-border: red;");
                    throw new Exception("Invalid minimum");
                }
                min = Integer.parseInt(minField.getText());

                if (!maxField.getText().matches("^[0-9]\\d*$")) {
                    warningLabel.setText("Please use a positive number.");
                    warningLabel.setOpacity(1);
                    maxField.setStyle("-fx-text-box-border: red;");
                    throw new Exception("Invalid maximum");
                }

                if (!maxField.getText().matches("^[0-9]\\d*$")) {
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
                    invField.setStyle("-fx-text-box-border: red;");
                    throw new Exception("Invalid inventory");
                }
                if(inv > max){
                    warningLabel.setText("Inventory cannot be more than maximum.");
                    warningLabel.setOpacity(1);
                    invField.setStyle("-fx-text-box-border: red;");
                    throw new Exception("Invalid inventory");
                }

                String outsourced = machineField.getText();
            //generating ID
                int partId = 0;
                for (int i = 0; i < Inventory.getAllParts().size() + 1; i++) {
                    partId = partId + 1;
                }

                Part add = new Outsourced(partId, name, price, inv, min, max, outsourced);
                Inventory.getAllParts().add(add);
                stage.close();
            } catch (Exception e) {
                System.out.println("Please complete all fields.");
            }
        }


        if (outSourced.isSelected()) {

            try {
                System.out.println("Save button fired.");
                //getting new product from text fields
                if (nameField.getText() == null || nameField.getText() == "") {
                    System.out.println("No name");
                    warningLabel.setOpacity(1);
                    nameField.setStyle("-fx-text-box-border: red;");
                    throw new Exception("Invalid name");
                }
                name = nameField.getText();
                if (!invField.getText().matches("^[0-9]\\d*$")) {
                    warningLabel.setText("Please use a positive number.");
                    warningLabel.setOpacity(1);
                    invField.setStyle("-fx-text-box-border: red;");
                    throw new Exception("Invalid inventory");
                }
                inv = Integer.parseInt(invField.getText());
                if (!priceField.getText().matches("^(?:[1-9]\\d*|0)?(?:\\.\\d+)?$")) {
                    warningLabel.setText("Please use a positive number.");
                    warningLabel.setOpacity(1);
                    priceField.setStyle("-fx-text-box-border: red;");
                    throw new Exception("Invalid minimum");
                }
                price = Double.parseDouble(priceField.getText());
                if (!minField.getText().matches("^[0-9]\\d*$")) {
                    warningLabel.setText("Please use a positive number.");
                    warningLabel.setOpacity(1);
                    minField.setStyle("-fx-text-box-border: red;");
                    throw new Exception("Invalid minimum");
                }
                min = Integer.parseInt(invField.getText());
                if (!maxField.getText().matches("^[0-9]\\d*$")) {
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
                    invField.setStyle("-fx-text-box-border: red;");
                    throw new Exception("Invalid inventory");
                }

                if(inv > max){
                    warningLabel.setText("Inventory cannot be more than maximum.");
                    warningLabel.setOpacity(1);
                    invField.setStyle("-fx-text-box-border: red;");
                    throw new Exception("Invalid inventory");
                }

                String Outscore = machineField.getText();

                //generating ID
                int partId = 0;
                for (int i = 0; i < Inventory.getAllParts().size() + 1; i++) {
                    partId = partId + 1;
                }
                Part add = new Outsourced(partId, name, price, inv, min, max, Outscore);
                Inventory.getAllParts().add(add);
                stage.close();
            } catch (Exception e) {
                System.out.println("Please complete all fields.");
            }
        }
    }
    /**
     * #CancelButton
     * Closes the window/scene
     */
    @FXML
    public void cancelButtonAction(javafx.event.ActionEvent actionEvent){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public EventHandler<javafx.event.ActionEvent> actionPerformed(javafx.event.ActionEvent saveButtonAction) {
        name = nameField.getText();
        return null;
    }
    /**
     * #RadioAction
     * Checks which radio button is pressed and changes the last field to match selected radio button
     */
    @FXML
    public void radioAction(javafx.event.ActionEvent actionEvent){
        if (inHouse.isSelected()){
            machineIDText.setText("Machine ID");
        }

        if (outSourced.isSelected()){
            machineIDText.setText("Company Name");
        }
    }

}
