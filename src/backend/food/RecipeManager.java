package backend.food;

import java.sql.*;
import java.util.ArrayList;

import static backend.food.FoodManager.rowToFood;

public class RecipeManager {

    Connection conn;

    public RecipeManager(Connection conn) {
        this.conn = conn;
    }

    /*
    The rowToRecipe method converts a row pulled from the database to a Recipe object

    RETURNS: The created Recipe object

    ARGS: recipeResultSet -> A result set the row of which contains the data needed
          ingredients -> An ArrayList containing the ingredients that will be moved to the Recipe
     */
    private Recipe rowToRecipe(ResultSet recipeResultSet, ArrayList<RecipeIngredient> ingredients) throws SQLException {
        //Create a new recipe object with the data from the result set
        Recipe recipe = new Recipe (
                recipeResultSet.getInt("id"),
                recipeResultSet.getString("name"),
                recipeResultSet.getString("description")
        );

        //Move the ingredients inside the Recipe object
        while (!ingredients.isEmpty()) {
            recipe.addIngredient(ingredients.removeFirst());
        }
        return recipe;
    }

    public Recipe getRecipe(int recipeId) throws SQLException{
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

    public boolean addRecipe(Recipe newRecipe) throws SQLException {
        PreparedStatement statement = null;
        try {
            String sql = "INSERT INTO Recipe (id, name, description) " +
                    "VALUES (?, ?, ?)";
            statement = conn.prepareStatement(sql);
            statement.setInt(1, newRecipe.getId());
            statement.setString(2, newRecipe.getBrand());
            statement.setString(3, newRecipe.getBrandModel());
            statement.executeUpdate();

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

    public boolean deleteRecipe(int id) throws SQLException {
        PreparedStatement statement = null;
        try {
            String sql = "DELETE FROM Recipe " +
                         "WHERE id = ?";

            statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
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

    public double getRecipeCals(int recipeId) throws SQLException {
        ArrayList<RecipeIngredient> ingredients = getIngredients(recipeId);
        double calories = 0;

        while (!ingredients.isEmpty()) {
            calories += ingredients.removeFirst().getFood().getCalories();
        }
        return calories;
    }

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

    /*
    The addIngredient method adds a food in the database as an ingredient of a Recipe

    RETURNS: True if the operation was successful

    ARGS: recipeId -> The ID of the recipe the ingredients belongs to
          foodId -> The ID of the ingredient to add in the database
          quantity -> The amount of the ingredient the recipe contains, in grams
     */
    public boolean addIngredient(int recipeId, int foodId, double quantity) throws SQLException {
        PreparedStatement statement = null;
        try {
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

    /*
    The removeIngredient method deletes an ingredient from the database

    RETURNS: True if the operation was successful

    ARGS: recipeId -> The recipe that contains the ingredient
          foodId -> The ID of the ingredient to delete
     */
    public boolean removeIngredient(int recipeId, int foodId) throws SQLException {
        PreparedStatement statement = null;
        try {
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
