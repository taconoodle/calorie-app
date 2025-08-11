package backend.food;

import java.util.ArrayList;

public class Recipe extends Food {
    //List containing a dynamic amount of food
    private ArrayList<RecipeIngredient> ingredients;

    private double calories;
    private double proteins;
    private double carbs;

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

    public void addIngredient(RecipeIngredient ingredient){
        this.ingredients.add(ingredient);

        this.calories += ingredient.getFood().getCalories();
        this.proteins += ingredient.getFood().getProteins();
        this.carbs += ingredient.getFood().getCarbs();
    }

    public void removeIngredient(RecipeIngredient ingredient) {
        ingredients.remove(ingredient);
    }
}