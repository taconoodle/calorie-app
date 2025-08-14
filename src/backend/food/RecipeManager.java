package backend.food;

import java.sql.*;
import java.util.ArrayList;

import static backend.food.FoodManager.rowToFood;

/**
 * The RecipeManager class manages the Recipes both in the backend and in the database while acting as a bridge among them
 * @author taconoodle
 */
public class RecipeManager {

    /**
     * A database connection
     * The RecipeManager instance will this connection as its main database connection for its methods
     */
    Connection conn;

    /**
     * Used as the gram basis in the quantities of the ingredients
     * e.g. A recipe needs 120 grams of ingredient A.
     *     Ingredients' cals, prots and carbs are by default expressed for QUANTITY_BASE grams of the ingredient
     *     To find the calories the recipe gains from A, we have to multiply A's calories with the quantity and then divide it by the QUANTITY_BASE
     */
    protected static final double QUANTITY_BASE = 100;

    /**
     * Default constructor
     * @param conn the main database connection
     */
    public RecipeManager(Connection conn) {
        this.conn = conn;
    }

    /**
     * Converts a database row to a Recipe object
     * @param recipeResultSet a result set, the row of which contains the data needed
     * @param recipeIngredients an ArrayList containing the ingredients that will be moved to the Recipe
     * @return the created Recipe object
     * @throws SQLException if a database error is encountered
     */
    private Recipe rowToRecipe(ResultSet recipeResultSet, ArrayList<RecipeIngredient> recipeIngredients) throws SQLException {
        //Create a new recipe object with the data from the result set
        Recipe recipe = new Recipe (
                recipeResultSet.getInt("id"),
                recipeResultSet.getString("name"),
                recipeResultSet.getString("description")
        );

        //Move the ingredients inside the Recipe object
        while (!recipeIngredients.isEmpty()) {
            recipe.addIngredient(recipeIngredients.removeFirst());
        }
        return recipe;
    }

    /**
     * Queries the database for the requested recipe
     * @param recipeId the ID of the requested recipe
     * @return the requested Recipe object
     * @throws SQLException if a database error is encountered
     */
    public Recipe getRecipe(int recipeId) throws SQLException {
        PreparedStatement statement = null;
        try {
            //First we get the recipe's data from the Recipe table in the database
            String sql = "SELECT id, name, description FROM Recipe WHERE id = ?";
            statement = conn.prepareStatement(sql);
            statement.setInt(1, recipeId);

            ResultSet rs = statement.executeQuery();

            //We give the rowToRecipe function the recipe data we pulled and tell it to get the ingredients using the corresponding method
            if (rs.next()) {
                return rowToRecipe(rs, getIngredients(recipeId));
            }
            return null;
        }
        catch (SQLException e) {
            //Report the error and then throw it above
            System.err.println("SQL Database error: " + e.getMessage());
            throw e;
        }
        finally {
            //Close the statement to avoid leakages
            statement.close();
        }
    }

    /**
     * Insert a new recipe in the database
     * @param newRecipe the recipe to insert
     * @return true if successful
     * @throws SQLException if a database error is encountered
     */
    public boolean addRecipe(Recipe newRecipe) throws SQLException {
        PreparedStatement statement = null;
        try {
            //Add the data that belong to the Recipe table in the database
            String sql = "INSERT INTO Recipe (id, name, description) " +
                         "VALUES (?, ?, ?)";
            statement = conn.prepareStatement(sql);
            statement.setInt(1, newRecipe.getId());
            statement.setString(2, newRecipe.getBrand());
            statement.setString(3, newRecipe.getBrandModel());
            statement.executeUpdate();

            //Add the ingredients of the recipe in database
            ArrayList<RecipeIngredient> ingredients = newRecipe.getIngredients();

            while(!ingredients.isEmpty()) {
                RecipeIngredient ingredientToAdd = ingredients.removeFirst();
                addIngredient(
                        newRecipe.getId(),
                        ingredientToAdd.getFood().getId(),
                        ingredientToAdd.getQuantity()
                );
            }
            return true;
        }
        catch (SQLException e) {
            System.err.println("SQL Database error " + e.getMessage());
            throw e;
        }
        finally {
            //Close the statement to avoid leaks
            statement.close();
        }
    }

    /**
     * Deletes a recipe from the database
     * @param recipeId the ID of the recipe to delete
     * @return true if successful
     * @throws SQLException if a database error is encountered
     */
    public boolean deleteRecipe(int recipeId) throws SQLException {
        PreparedStatement statement = null;
        try {
            //Delete the recipe's entry from Recipes table (the database automatically deletes the ingredients)
            String sql = "DELETE FROM Recipe " +
                         "WHERE id = ?";

            statement = conn.prepareStatement(sql);
            statement.setInt(1, recipeId);
            statement.executeUpdate();

            return true;
        }
        catch (SQLException e) {
            System.err.println("SQL Database error: " + e.getMessage());
            throw e;
        }
        finally {
            //Close the statement to avoid leakages
            statement.close();
        }
    }

    /**
     * Calculates the calories of a recipe
     * Internally, gets all of its ingredients to calculate the total calories
     * @param recipeId the ID of the recipe of which the calories we want
     * @return the amount of calories
     * @throws SQLException if a database error is encountered
     */
    public double getRecipeCals(int recipeId) throws SQLException {
        ArrayList<RecipeIngredient> ingredients = getIngredients(recipeId);
        double calories = 0;

        while (!ingredients.isEmpty()) {
            calories += ingredients.removeFirst().getCalories();
        }
        return calories;
    }

    /**
     * Calculates the proteins of a recipe
     * Internally, gets all of its ingredients to calculate the total proteins
     * @param recipeId the ID of the recipe of which the proteins we want
     * @return the amount of proteins
     * @throws SQLException if a database error is encountered
     */
    public double getRecipeProteins(int recipeId) throws SQLException {
        ArrayList<RecipeIngredient> ingredients = getIngredients(recipeId);
        double proteins = 0;

        while (!ingredients.isEmpty()) {
            proteins += ingredients.removeFirst().getProteins();
        }
        return proteins;
    }

    /**
     * Calculates the carbs of a recipe
     * Internally, gets all of its ingredients to calculate the total carbs
     * @param recipeId the ID of the recipe of which the carbs we want
     * @return the amount of proteins
     * @throws SQLException if a database error is encountered
     */
    public double getRecipeCarbs(int recipeId) throws SQLException {
        ArrayList<RecipeIngredient> ingredients = getIngredients(recipeId);
        double carbs = 0;

        while (!ingredients.isEmpty()) {
            carbs += ingredients.removeFirst().getCarbs();
        }
        return carbs;
    }

    /**
     * Queries the database for the ingredients of a recipe
     * @param recipeId the ID of the recipe, of which its ingredients we need
     * @return an ArrayList containing the ingredients
     * @throws SQLException if a database error is encountered
     */
    public ArrayList<RecipeIngredient> getIngredients(int recipeId) throws SQLException {
        PreparedStatement statement = null;
        try {
            //Pull the ingredients from the database
            String sql = "SELECT f.id, f.brand, f.brand_model, f.calories, f.protein, f.carbs, ing.quantity " +
                         "FROM Ingredients ing " +
                         "JOIN Food f ON f.id = ing.food_id " +
                         "WHERE ing.recipe_id = ?";

            statement = conn.prepareStatement(sql);
            statement.setInt(1, recipeId);
            ResultSet rs = statement.executeQuery();

            //Add the ingredients one-by-one in the Recipe object
            ArrayList<RecipeIngredient> ingredients = new ArrayList<>();
            while (rs.next()) {
                ingredients.add(new RecipeIngredient(rowToFood(rs), rs.getDouble(7)));
            }
            return ingredients;

        }
        catch (SQLException e) {
            System.err.println("SQL Database error: " + e.getMessage());
            throw e;
        }
        finally {
            //Close the statement to avoid leakages
            statement.close();
        }
    }

    /**
     * Insert a new ingredient in a recipe in the database
     * This creates a new entry in the Ingredients table
     * @param recipeId the ID of the recipe that gets the ingredient
     * @param foodId the ID of the ingredient
     * @param quantity the amount of grams of the ingredient the recipe needs
     * @return true if successful
     * @throws SQLException if a database error is encountered
     */
    public boolean addIngredient(int recipeId, int foodId, double quantity) throws SQLException {
        PreparedStatement statement = null;
        try {
            //Add the ingredient in the database
            String sql = "INSERT INTO Ingredients (recipe_id, food_id, quantity) " +
                         "VALUES (?, ?, ?)";
            statement = conn.prepareStatement(sql);
            statement.setInt(1, recipeId);
            statement.setInt(2, foodId);
            statement.setDouble(3, quantity);

            statement.executeUpdate();
            return true;
        }
        catch (SQLException e) {
            System.err.println("SQL Database error: " + e.getMessage());
            throw e;
        }
        finally {
            //Close the statement to avoid leaks
            statement.close();
        }
    }

    /**
     * Removes an ingredient from a recipe
     * @param recipeId the ID of the recipe the ingredient belongs to
     * @param foodId the ID of the ingredient
     * @return true if successful
     * @throws SQLException if a database error is encountered
     */
    public boolean removeIngredient(int recipeId, int foodId) throws SQLException {
        PreparedStatement statement = null;
        try {
            //Delete ingredient from database
            String sql = "DELETE FROM Ingredients WHERE recipe_id = ? AND food_id = ?";
            statement = conn.prepareStatement(sql);
            statement.setInt(1, recipeId);
            statement.setInt(2, foodId);

            statement.executeUpdate();
            return true;
        }
        catch (SQLException e) {
            System.err.println("SQL Database error: " + e.getMessage());
            throw e;
        }
        finally {
            //Close the statement to avoid leaks
            statement.close();
        }
    }

}
