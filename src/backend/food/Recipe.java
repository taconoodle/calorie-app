package backend.food;

import java.util.ArrayList;

import static backend.food.RecipeManager.QUANTITY_BASE;

/**
 * The Recipe class represents a record of a recipe.
 * A recipe is a kind of food that constists of other foods
 *
 * @author taconoodle
 */
public class Recipe extends Food {
    /**
     * Contains the ingredients of the recipe
     */
    private ArrayList<RecipeIngredient> ingredients;

    /**
     * Default constructor
     *
     * @param id          the recipe's ID
     * @param brand       the recipe's brand
     * @param description the recipe's description
     */
    public Recipe(int id, String brand, String description) {
        super(id, brand, description, 0, 0, 0);

        this.ingredients = new ArrayList<RecipeIngredient>();
    }

    /**
     * Gets the recipe's ingredients
     *
     * @return an ArrayList containing the recipe's ingredients
     */
    public ArrayList<RecipeIngredient> getIngredients() {
        return ingredients;
    }

    /**
     * Changes the list containing the recipe's ingredients
     *
     * @param ingredients the new list of the ingredients
     */
    public void setIngredients(ArrayList<RecipeIngredient> ingredients) {
        this.ingredients = ingredients;
    }

    /**
     * Adds an ingredient to the recipe and updates the recipe's nutritional info
     *
     * @param ingredient
     */
    public void addIngredient(RecipeIngredient ingredient) {
        this.ingredients.add(ingredient);

        this.setCalories(this.getCalories() + ingredient.getCalories());
        this.setProteins(this.getProteins() + ingredient.getProteins());
        this.setCarbs(this.getCarbs() + ingredient.getCarbs());
    }

    /**
     * Removes an ingredient from the recipe and updates the recipe's nutritional info
     *
     * @param ingredient
     */
    public void removeIngredient(RecipeIngredient ingredient) {
        ingredients.remove(ingredient);

        this.setCalories(this.getCalories() - ingredient.getCalories());
        this.setProteins(this.getProteins() - ingredient.getProteins());
        this.setCarbs(this.getCarbs() - ingredient.getCarbs());
    }

    /**
     * Creates a string containing the recipe's info
     *
     * @return the info string
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(super.toString() + "\nIngredients:");
        for (RecipeIngredient ingredient: ingredients) {
            result.append("\n\t").append(ingredient.toString());
        }
        return result.toString();
    }
}