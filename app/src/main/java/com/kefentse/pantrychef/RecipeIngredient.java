package com.kefentse.pantrychef;

public class RecipeIngredient implements java.io.Serializable {
    private String name;
    private double quantity;

    public RecipeIngredient(String name, double quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() { return name; }
    public double getQuantity() { return quantity; }
}