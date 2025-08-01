package backend.food;

import java.util.ArrayList;

public class Recipe extends Food {
    //List containing a dynamic amount of food
    private ArrayList<Food> ingredients;

    private float calories;
    private float proteins;
    private float carbs;

    public Recipe(int id, String brand, String brandModel, float calories, float proteins, float carbs) {
        super(id, brand, brandModel, calories, proteins, carbs);

        this.ingredients = new ArrayList<Food>();
    }

    public ArrayList<Food> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Food> ingredients) {
        this.ingredients = ingredients;
    }

    public void addIngredient(Food ingredient){
        this.ingredients.add(ingredient);

        this.calories += ingredient.getCalories();
        this.proteins += ingredient.getProteins();
        this.carbs += ingredient.getCarbs();
    }

    public void removeIngredient(Food ingredient) {
        ingredients.remove(ingredient);
    }
}