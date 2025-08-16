package backend.food;

import static backend.food.RecipeManager.QUANTITY_BASE;

/**
 * The RecipeIngredient represents a record of an ingredient that belongs to a recipe.
 * It contains the Food that's actually the ingredient
 *
 * @author taconoodle
 */
public class RecipeIngredient {
    /**
     * The actual ingredient
     */
    private Food food;
    /**
     * The amount of grams of food the recipe contains
     */
    private double quantity;

    /**
     * Default constructor
     *
     * @param food     the food acting as the ingredient
     * @param quantity the amount of grams
     */
    public RecipeIngredient(Food food, double quantity) {
        this.food = food;
        this.quantity = quantity;
    }

    /**
     * Gets the ingredient's food
     *
     * @return the food
     */
    public Food getFood() {
        return food;
    }

    /**
     * Changes which food the ingredient uses
     *
     * @param food the new food
     */
    public void setFood(Food food) {
        this.food = food;
    }

    /**
     * Gets the quantity of the food in the ingredient
     *
     * @return the quantity
     */
    public double getQuantity() {
        return quantity;
    }

    /**
     * Changes the amount of grams of food
     *
     * @param quantity the new amount of grams
     */
    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    /**
     * Calculates the amount of calories the ingredient contains
     *
     * @return the calorie amount
     */
    public double getCalories() {
        return this.food.getCalories() * this.quantity / QUANTITY_BASE;
    }

    /**
     * Calculates the amount of protein the ingredient contains
     *
     * @return the protein amount
     */
    public double getProteins() {
        return this.food.getProteins() * this.quantity / QUANTITY_BASE;
    }

    /**
     * Calculates the amount of carbs the ingredient contains
     *
     * @return the carb amount
     */
    public double getCarbs() {
        return this.food.getCarbs() * this.quantity / QUANTITY_BASE;
    }

    /**
     * Creates a string containing the ingredient's info
     *
     * @return the info string
     */
    @Override
    public String toString() {
        return String.format(food.toString() + "\t\t%.2f grams", quantity);
    }
}
