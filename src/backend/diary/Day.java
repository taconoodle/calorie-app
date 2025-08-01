package backend.diary;

import backend.food.Food;

import java.time.LocalDate;

public class Day {
    private LocalDate date;

    private Meal breakfast;
    private Meal lunch;
    private Meal dinner;
    private Meal snacks;

    public Day(LocalDate date, Meal breakfast, Meal lunch, Meal dinner, Meal snacks) {
        this.date = date;

        this.breakfast = new Meal(Meal.Type.BREAKFAST);
        this.lunch = new Meal(Meal.Type.LUNCH);
        this.dinner = new Meal(Meal.Type.DINNER);
        this.snacks = new Meal(Meal.Type.SNACKS);
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Meal getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(Meal breakfast) {
        this.breakfast = breakfast;
    }

    public Meal getLunch() {
        return lunch;
    }

    public void setLunch(Meal lunch) {
        this.lunch = lunch;
    }

    public Meal getSnacks() {
        return snacks;
    }

    public void setSnacks(Meal snacks) {
        this.snacks = snacks;
    }

    public Meal getDinner() {
        return dinner;
    }

    public void setDinner(Meal dinner) {
        this.dinner = dinner;
    }

    public void addFoodInBreakfast(Food food) {
        this.breakfast.addFood(food);
    }

    public void addFoodInLunch(Food food) {
        this.lunch.addFood(food);
    }

    public void addFoodInDinner(Food food) {
        this.dinner.addFood(food);
    }

    public void addFoodInSnacks(Food food) {
        this.snacks.addFood(food);
    }

    public void removeFoodFromBreakfast(Food food) {
        this.breakfast.removeFood(food);
    }

    public void removeFoodFromLunch(Food food) {
        this.lunch.removeFood(food);
    }

    public void removeFoodFromDinner(Food food) {
        this.dinner.removeFood(food);
    }

    public void removeFoodFromSnacks(Food food) {
        this.snacks.removeFood(food);
    }
}
