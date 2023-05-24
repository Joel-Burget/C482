package kinkead.firstscreen;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ModifyPartController extends JFrame{
    String name;
    public Text warningLabel;

    private int inv;
    private double price;
    private int max;
    private int min;
    private int machineID;
    ToggleGroup partSource = new ToggleGroup();
    public Text machineIDText;

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
     * #Init
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
        //Set Radio buttons
        initializeRadioButtons();

        int id = MainController.getSelectedPart();

        //Setting temp part to contain data for text fields
        Part temp = Inventory.lookupPart(id);
        String tempID = Integer.toString(id);
        if(temp.getClass() == Outsourced.class){
            outSourced.setSelected(true);
            //setting text fields
            idField.setText(tempID);
            nameField.setText(temp.getName());
            invField.setText(Integer.toString(temp.getStock()));
            priceField.setText(Double.toString(temp.getPrice()));
            minField.setText(Integer.toString(temp.getMin()));
            maxField.setText(Integer.toString(temp.getMax()));
            machineField.setText(((Outsourced) temp).getCompanyName());
        }

        if(temp.getClass() == InHouse.class){
            inHouse.setSelected(true);
            //setting text fields
            idField.setText(tempID);
            nameField.setText(temp.getName());
            invField.setText(Integer.toString(temp.getStock()));
            priceField.setText(Double.toString(temp.getPrice()));
            minField.setText(Integer.toString(temp.getMin()));
            maxField.setText(Integer.toString(temp.getMax()));
            machineField.setText(Integer.toString(((InHouse) temp).getMachineId()));
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
    public void saveButtonAction(javafx.event.ActionEvent actionEvent) {
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
                int machineID = Integer.parseInt(machineField.getText());


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

                int partId = MainController.getSelectedPart();
                Part add = new InHouse(partId, name, price, inv, min, max, machineID);
                Inventory.updatePart(MainController.getSelectedPart()-1, add);
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

                int partId = MainController.getSelectedPart();

                Part add = new Outsourced(partId, name, price, inv, min, max, Outscore);
                Inventory.updatePart(MainController.getSelectedPart()-1, add);
                stage.close();
            } catch (Exception e) {
                System.out.println("Please complete all fields.");
            }
        }
    }
    /**
     *  Cancel button, closes stage without saving
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

    @FXML
    private void initializeRadioButtons(){
        ToggleGroup radioGroup = new ToggleGroup();
        inHouse.setToggleGroup(radioGroup);
        outSourced.setToggleGroup(radioGroup);
    }

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
