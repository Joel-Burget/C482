package kinkead.firstscreen;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product extends Part{
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Product(int id, String name, double price, int stock, int min, int max) {
        super(id, name, price, stock, min, max);
        setName(name);
        setId(id);
        setPrice(price);
        setStock(stock);
        setMin(min);
        setMax(max);
    }

    public void setId(int id){
        this.id =id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStock(int stock){
        this.stock = stock;
    }

    public void setMin(int min){
        this.min = min;
    }

    public void setMax(int max){
        this.max = max;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public double getPrice(){
        return price;
    }

    public int getStock(){
        return stock;
    }

    public int getMin(){
        return min;
    }

    public int getMax(){
        return max;
    }

    public void addAssociatedPart(Part newPart){
        associatedParts.add(newPart);
    }

    public boolean deleteAssociatedPart(Part selectedAssociatePart){
        for (Part part : Inventory.getAllParts()) {
            if (selectedAssociatePart.getId() == part.getId()) {
                associatedParts.remove(part);
                return true;
            }
        }
        return false;
    }

    public void updateProduct(int index, Part selectedPart){
        if(associatedParts.contains(associatedParts.get(index))){
            associatedParts.set(index, selectedPart);
        }
    }

    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }
}
