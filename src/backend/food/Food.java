package backend.food;

public class Food {
    private int foodId;

    private String brand;
    private String brandModel;

    private double calories;
    private double proteins;
    private double carbs;

    public Food(int id, String brand, String brandModel, double calories, double proteins, double carbs) {
        this.brand = brand;
        this.brandModel = brandModel;

        this.foodId = id;

        this.calories = calories;
        this.proteins = proteins;
        this.carbs = carbs;
    }

    public int getFoodId() {
        return foodId;
    }

    public String getBrandModel() {
        return brandModel;
    }

    public void setBrandModel(String brandModel) {
        this.brandModel = brandModel;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getCalories() {
        return this.calories;
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

    @Override
    public String toString() {
        return String.format("%d | %s | %s | %.2f | %.2f | %.2f",
                this.getFoodId(),
                this.getBrand(),
                this.getBrandModel(),
                this.getCalories(),
                this.getProteins(),
                this.getCarbs());
    }
}
