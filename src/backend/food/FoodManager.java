package backend.food;

import java.sql.*;
import java.util.ArrayList;

/**
 * The RecipeManager class manages the Recipes both in the backend and in the database while acting as a bridge among them
 *
 * @author taconoodle
 */
public class FoodManager {
    /**
     * A database connection
     * The FoodManager instance will use this connection as its main database connection for its methods
     */
    private Connection conn;

    /**
     * Default constructor
     *
     * @param conn the main database connection
     */
    public FoodManager(Connection conn) {
        this.conn = conn;
    }

    /**
     * Converts a database row to a Food object
     *
     * @param extractionResultSet a result set, the row of which contains the data needed
     * @return the created Food object
     * @throws SQLException if a database error is encountered
     */
    public static Food rowToFood(ResultSet extractionResultSet) throws SQLException {
        return new Food(
                extractionResultSet.getInt("id"),
                extractionResultSet.getString("brand"),
                extractionResultSet.getString("description"),
                extractionResultSet.getDouble("calories"),
                extractionResultSet.getDouble("protein"),
                extractionResultSet.getDouble("carbs")
        );
    }

    /**
     * Insert a new food in the database
     *
     * @param newFood the food to insert
     * @return true if successful
     * @throws SQLException if a database error is encountered
     */
    public boolean addFood(Food newFood) throws SQLException {
        PreparedStatement statement = null;
        try {
            //SQL code that inserts the given Food object in the database
            String sql = "INSERT INTO Food (id, brand, description, calories, protein, carbs) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            statement = conn.prepareStatement(sql);
            statement.setInt(1, newFood.getId());
            statement.setString(2, newFood.getBrand());
            statement.setString(3, newFood.getDescription());
            statement.setDouble(4, newFood.getCalories());
            statement.setDouble(5, newFood.getProteins());
            statement.setDouble(6, newFood.getCarbs());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("SQL Database error: " + e.getMessage());
            return false;
        } finally {
            statement.close();
        }
    }

    /**
     * Queries the database for the requested food
     *
     * @param foodId the ID of the requested food
     * @return the requested food
     * @throws SQLException if a database error is encountered
     */
    public Food getFood(int foodId) throws SQLException {
        PreparedStatement statement = null;
        try {
            //SQL code that gets the food with the given ID from the database
            String sql = "SELECT * FROM Food f " +
                    "WHERE f.id = ?";
            statement = conn.prepareStatement(sql);
            statement.setInt(1, foodId);
            ResultSet rs = statement.executeQuery();

            //Extract the data from the database into a Food instance and return it
            if (rs.next()) {
                return rowToFood(rs);
            }
            return null;
        } catch (SQLException e) {
            System.err.println("SQL Database error: " + e.getMessage());
            return null;
        } finally {
            //CLOSE THE STATEMENT (avoid leakages)
            statement.close();
        }
    }

    /**
     * Deletes a food record from the database
     *
     * @param foodId the ID of the food to delete
     * @return true if successful
     * @throws SQLException if a database error is encountered
     */
    public boolean deleteFood(int foodId) throws SQLException {
        PreparedStatement statement = null;
        try {
            //SQL code that inserts the given Food object in the database
            String sql = "DELETE FROM Food " +
                    "WHERE id = ?";
            statement = conn.prepareStatement(sql);
            statement.setInt(1, foodId);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("SQL Database error: " + e.getMessage());
            return false;
        } finally {
            statement.close();
        }
    }

    /**
     * Queries the database for the foods containing less than a number of calories
     *
     * @param calorieLimit the number of calories
     * @return an ArrayList of the matching foods
     * @throws SQLException if a database error is encountered
     */
    public ArrayList<Food> getFoodUnderCals(double calorieLimit) throws SQLException {
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
        } catch (SQLException e) {
            System.err.println("SQL Database error: " + e.getMessage());
            return null;
        } finally {
            statement.close();
        }
    }

    /**
     * Queries the database for the foods containing over a number of calories
     *
     * @param calorieLimit the number of calories
     * @return an ArrayList of the matching foods
     * @throws SQLException if a database error is encountered
     */
    public ArrayList<Food> getFoodOverCals(double calorieLimit) throws SQLException {
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
        } catch (SQLException e) {
            System.err.println("SQL Database error: " + e.getMessage());
            return null;
        } finally {
            statement.close();
        }
    }

    /**
     * Queries the database for the foods containing less than a number of proteins
     *
     * @param proteinLimit the number of proteins
     * @return an ArrayList of the matching foods
     * @throws SQLException if a database error is encountered
     */
    public ArrayList<Food> getFoodUnderProteins(double proteinLimit) throws SQLException {
        PreparedStatement statement = null;
        try {
            String sql = "SELECT * FROM Food WHERE protein <= ?";
            statement = conn.prepareStatement(sql);
            statement.setDouble(1, proteinLimit);
            ResultSet rs = statement.executeQuery();

            ArrayList<Food> foods = new ArrayList<>();
            while (rs.next()) {
                foods.add(rowToFood(rs));
            }
            return foods;
        } catch (SQLException e) {
            System.err.println("SQL Database error: " + e.getMessage());
            return null;
        } finally {
            statement.close();
        }
    }

    /**
     * Queries the database for the foods containing over a number of proteins
     *
     * @param proteinLimit the number of proteins
     * @return an ArrayList of the matching foods
     * @throws SQLException if a database error is encountered
     */
    public ArrayList<Food> getFoodOverProteins(double proteinLimit) throws SQLException {
        PreparedStatement statement = null;
        try {
            String sql = "SELECT * FROM Food WHERE protein > ?";
            statement = conn.prepareStatement(sql);
            statement.setDouble(1, proteinLimit);
            ResultSet rs = statement.executeQuery();

            ArrayList<Food> foods = new ArrayList<>();
            while (rs.next()) {
                foods.add(rowToFood(rs));
            }
            return foods;
        } catch (SQLException e) {
            System.err.println("SQL Database error: " + e.getMessage());
            return null;
        } finally {
            statement.close();
        }
    }

    /**
     * Queries the database for the foods containing less than a number of carbs
     *
     * @param carbLimit the number of carbs
     * @return an ArrayList of the matching foods
     * @throws SQLException if a database error is encountered
     */
    public ArrayList<Food> getFoodUnderCarbs(double carbLimit) throws SQLException {
        PreparedStatement statement = null;
        try {
            String sql = "SELECT * FROM Food WHERE carbs <= ?";
            statement = conn.prepareStatement(sql);
            statement.setDouble(1, carbLimit);
            ResultSet rs = statement.executeQuery();

            ArrayList<Food> foods = new ArrayList<>();
            while (rs.next()) {
                foods.add(rowToFood(rs));
            }
            return foods;
        } catch (SQLException e) {
            System.err.println("SQL Database error: " + e.getMessage());
            return null;
        } finally {
            statement.close();
        }
    }

    /**
     * Queries the database for the foods containing over a number of carbs
     *
     * @param carbLimit the number of carbs
     * @return an ArrayList of the matching foods
     * @throws SQLException if a database error is encountered
     */
    public ArrayList<Food> getFoodOverCarbs(double carbLimit) throws SQLException {
        PreparedStatement statement = null;
        try {
            String sql = "SELECT * FROM Food WHERE carbs > ?";
            statement = conn.prepareStatement(sql);
            statement.setDouble(1, carbLimit);
            ResultSet rs = statement.executeQuery();

            ArrayList<Food> foods = new ArrayList<>();
            while (rs.next()) {
                foods.add(rowToFood(rs));
            }
            return foods;
        } catch (SQLException e) {
            System.err.println("SQL Database error: " + e.getMessage());
            return null;
        } finally {
            statement.close();
        }
    }
}

