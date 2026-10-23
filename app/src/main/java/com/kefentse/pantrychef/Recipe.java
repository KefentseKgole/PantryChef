package com.kefentse.pantrychef;

import java.util.List;

public class Recipe implements java.io.Serializable {
    private long id;
    private String name;
    private String instructions;
    private List<RecipeIngredient> ingredients;

    public Recipe(long id, String name, String instructions) {
        this.id = id;
        this.name = name;
        this.instructions = instructions;
    }

    public long getId() { return id; }
    public String getName() { return name; }
    public String getInstructions() { return instructions; }

    public List<RecipeIngredient> getIngredients() { return ingredients; }
    public void setIngredients(List<RecipeIngredient> ingredients) { this.ingredients = ingredients; }
}