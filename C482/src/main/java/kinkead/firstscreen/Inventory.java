package kinkead.firstscreen;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.security.auth.callback.Callback;
import java.util.Map;

import java.util.HashMap;

public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public static ObservableList<Product> getAllProducts(){return allProducts;}

    public static void addPart(Part newPart){
        allParts.add(newPart);
    }

    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }

    public static Part lookupPart(int partID){
        for (var part: allParts) {
            if(part.getId() == partID){
                return part;
            }
        }
        return null;
    }

    public static Part lookupPart(String name){
        for (var part: allParts) {
            if(part.getName() == name){
                return part;
            }
        }
        return null;
    }

    public static Product lookupProduct(int productID){
        for (var product: allProducts) {
            if(product.getId() == productID){
                return product;
            }
        }
        return null;
    }

    public static Product lookupProduct(String name){
        for (var product: allProducts) {
            if(product.getName() == name){
                return product;
            }
        }
        return null;
    }

    public static void updatePart(int index, Part selectedPart){
        if(allParts.contains(allParts.get(index))){
            allParts.set(index, selectedPart);
        }
    }

    public static void updateProduct(int index, Product selectedProduct){
        if(allProducts.contains(allProducts.get(index))){
            allProducts.set(index, selectedProduct);
        }
    }

    public static void deletePart(int index){
        if(allParts.contains(index)){
            allParts.remove(index);
        }
    }

    public static void deleteProduct(int index){
        if(allProducts.contains(index)){
            allProducts.remove(index);
        }
    }
}