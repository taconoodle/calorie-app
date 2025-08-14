package backend.food;

import java.util.ArrayList;

import static backend.food.RecipeManager.QUANTITY_BASE;

public class Recipe extends Food {
    //List containing a dynamic amount of food
    private ArrayList<RecipeIngredient> ingredients;

    public Recipe(int id, String brand, String description) {
        super(id, brand, description, 0, 0, 0);

        this.ingredients = new ArrayList<RecipeIngredient>();
    }

    public ArrayList<RecipeIngredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<RecipeIngredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void addIngredient(RecipeIngredient ingredient) {
        this.ingredients.add(ingredient);

        this.setCalories(this.getCalories() + ingredient.getCalories());
        this.setProteins(this.getProteins() + ingredient.getProteins());
        this.setCarbs(this.getCarbs() + ingredient.getCarbs());
    }

    public void removeIngredient(RecipeIngredient ingredient) {
        ingredients.remove(ingredient);

        this.setCalories(this.getCalories() - ingredient.getCalories());
        this.setProteins(this.getProteins() - ingredient.getProteins());
        this.setCarbs(this.getCarbs() - ingredient.getCarbs());
    }
}