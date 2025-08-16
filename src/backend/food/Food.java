package backend.food;

/**
 * The Food class represents a record of a kind of food
 * It contains some basic nutritional facts about the food
 *
 * @author taconoodle
 */
public class Food {
    /**
     * Unique number assigned to each food
     */
    private int id;

    /**
     * The name of the brand that produces the food
     */
    private String brand;

    /**
     * The description of the food
     * e.g. Lay's has both oregano (one food) and salt (other food)
     */
    private String description;

    /**
     * Amount of calories in food
     */
    private double calories;

    /**
     * Amount of proteins in food
     */
    private double proteins;

    /**
     * Amount of carbs in food
     */
    private double carbs;

    /**
     * Default constructor
     *
     * @param id         the food's id
     * @param brand      the food's brand
     * @param description the food's description
     * @param calories   the food's calories
     * @param proteins   the food's proteins
     * @param carbs      the food's carbs
     */
    public Food(int id, String brand, String description, double calories, double proteins, double carbs) {
        this.brand = brand;
        this.description = description;

        this.id = id;

        this.calories = calories;
        this.proteins = proteins;
        this.carbs = carbs;
    }

    /**
     * Gets the food's id
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the food's description
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Changes the food's description
     *
     * @param description the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the food's brand
     *
     * @return the brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Changes the food's brand
     *
     * @param brand the new brand
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * Gets the food's calories
     *
     * @return the amount of calories
     */
    public double getCalories() {
        return this.calories;
    }

    /**
     * Changes the food's calories
     *
     * @param calories the new amount of calories
     */
    public void setCalories(double calories) {
        this.calories = calories;
    }

    /**
     * Gets the food's proteins
     *
     * @return the amount of proteins
     */
    public double getProteins() {
        return proteins;
    }

    /**
     * Changes the food's amount of proteins
     *
     * @param proteins the new amount of proteins
     */
    public void setProteins(double proteins) {
        this.proteins = proteins;
    }

    /**
     * Gets the food's amount of carbs
     *
     * @return the amount of carbs
     */
    public double getCarbs() {
        return carbs;
    }

    /**
     * Changes the food's amount of carbs
     *
     * @param carbs the new amount of carbs
     */
    public void setCarbs(double carbs) {
        this.carbs = carbs;
    }

    /**
     * Creates a string containing the food's info
     *
     * @return the info string
     */
    @Override
    public String toString() {
        return String.format("%d | %s | %s | %.2f | %.2f | %.2f",
                this.getId(),
                this.getBrand(),
                this.getDescription(),
                this.getCalories(),
                this.getProteins(),
                this.getCarbs());
    }
}
