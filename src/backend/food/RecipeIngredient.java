package backend.food;

import static backend.food.RecipeManager.QUANTITY_BASE;

public class RecipeIngredient {
    private Food food;
    private double quantity;

    public RecipeIngredient(Food food, double quantity) {
        this.food = food;
        this.quantity = quantity;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getCalories() {
        return this.food.getCalories() * this.quantity / QUANTITY_BASE;
    }

    public double getProteins() {
        return this.food.getProteins() * this.quantity / QUANTITY_BASE;
    }

    public double getCarbs() {
        return this.food.getCarbs() * this.quantity / QUANTITY_BASE;
    }
}
