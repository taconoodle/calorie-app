package backend.diary;

import java.util.ArrayList;

import backend.food.*;

public class Meal {
    private ArrayList<Food> foods;

    private double calories;
    private double proteins;
    private double carbs;

    public enum Type {
        BREAKFAST,
        LUNCH,
        DINNER,
        SNACKS
    }

    private Type mealType;

    public Meal(Type type) {
        this.mealType = type;

        this.calories = 0;
        this.proteins = 0;
        this.carbs = 0;
    }

    public ArrayList<Food> getFoods() {
        return foods;
    }

    public void setFoods(ArrayList<Food> foods) {
        this.foods = foods;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(float calories) {
        this.calories = calories;
    }

    public double getProteins() {
        return proteins;
    }

    public void setProteins(float proteins) {
        this.proteins = proteins;
    }

    public double getCarbs() {
        return carbs;
    }

    public void setCarbs(float carbs) {
        this.carbs = carbs;
    }

    public Type getMealType() {
        return mealType;
    }

    public void setMealType(Type mealType) {
        this.mealType = mealType;
    }

    public void addFood(Food food) {
        foods.add(food);

        this.calories += food.getCalories();
        this.proteins += food.getProteins();
        this.carbs += food.getCarbs();
    }

    public void removeFood(Food food) {
        foods.remove(food);
    }
}
