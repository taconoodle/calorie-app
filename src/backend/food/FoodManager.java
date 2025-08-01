package backend.food;

import java.sql.*;
import java.util.ArrayList;

/*
    NOTE-TO-SELF: IF I DO NOT CREATE A RecipeManager CLASS, I HAVE TO MAKE THIS ONE SUPPORT RECIPES
    NOTE-TO-SELF: CHECK BEST LIST TYPE FOR FOODS IN findFoodUnderCals and findFoodOverCals
 */
public class FoodManager {
    private Connection conn;

    public FoodManager(Connection conn) {
        this.conn = conn;
    }

    private Food rowToFood(ResultSet extractionResultSet) throws SQLException {
        return new Food(
                extractionResultSet.getInt("id"),
                extractionResultSet.getString("brand"),
                extractionResultSet.getString("brand_model"),
                extractionResultSet.getDouble("calories"),
                extractionResultSet.getDouble("protein"),
                extractionResultSet.getDouble("carbs")
        );
    }

    public boolean addFood(Food newFood) throws Exception {
        PreparedStatement statement = null;
        try {
            //SQL code that inserts the given Food object in the database
            String sql = "INSERT INTO Food (id, brand, brand_model, calories, protein, carbs) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            statement = conn.prepareStatement(sql);
            statement.setInt(1, newFood.getFoodId());
            statement.setString(2, newFood.getBrand());
            statement.setString(3, newFood.getBrandModel());
            statement.setDouble(4, newFood.getCalories());
            statement.setDouble(5, newFood.getProteins());
            statement.setDouble(6, newFood.getCarbs());
            statement.executeUpdate();
            return true;
        }
        catch(SQLException e) {
            System.err.println("SQL Database error: " + e.getMessage());
            throw e;
        }
        finally {
            statement.close();
        }
    }

    public Food getFood(int id) throws Exception {
        PreparedStatement statement = null;
        try {
            //SQL code that gets the food with the given ID from the database
            String sql = "SELECT * FROM Food f " +
                    "WHERE f.id = ?";
            statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            //Extract the data from the database into a Food instance and return it
            if (rs.next()) {
                return rowToFood(rs);
            }
            //There might not exist a food with the given ID
            System.out.println("Food with ID: " + id + " was not found.\n");
            return null;
        }
        catch(SQLException e) {
            System.err.println("SQL Database error: " + e.getMessage());
            throw e;
        }
        finally {
            //CLOSE THE STATEMENT (avoid leakages)
            statement.close();
        }
    }

    public boolean deleteFood(int id) throws Exception {
        PreparedStatement statement = null;
        try {
            //SQL code that inserts the given Food object in the database
            String sql = "DELETE FROM Food " +
                    "WHERE id = ?";
            statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
            return true;
        }
        catch(SQLException e) {
            System.err.println("SQL Database error: " + e.getMessage());
            throw e;
        }
        finally {
            statement.close();
        }
    }

    //This method searches the database and gets all the foods with calories less or equal to the given number
    public ArrayList<Food> getFoodUnderCals(double calorieLimit) throws Exception {
        PreparedStatement statement = null;
        try {
            String sql = "SELECT * FROM Food WHERE calories <= ?";
            statement = conn.prepareStatement(sql);
            statement.setDouble(1, calorieLimit);
            ResultSet rs = statement.executeQuery();

            ArrayList<Food> foods = new ArrayList<>();
            while (rs.next()) {
                foods.add(rowToFood(rs));
            }
            return foods;
        }
        catch (SQLException e) {
            System.err.println("SQL Database error: " + e.getMessage());
            throw e;
        }
        finally {
            statement.close();
        }
    }

    //This method searches the database and gets all the foods with calories more than the given number
    public ArrayList<Food> getFoodOverCals(double calorieLimit) throws Exception {
        PreparedStatement statement = null;
        try {
            String sql = "SELECT * FROM Food WHERE calories > ?";
            statement = conn.prepareStatement(sql);
            statement.setDouble(1, calorieLimit);
            ResultSet rs = statement.executeQuery();

            ArrayList<Food> foods = new ArrayList<>();
            while (rs.next()) {
                foods.add(rowToFood(rs));
            }
            return foods;
        }
        catch (SQLException e) {
            System.err.println("SQL Database error: " + e.getMessage());
            throw e;
        }
        finally {
            statement.close();
        }
    }
}

