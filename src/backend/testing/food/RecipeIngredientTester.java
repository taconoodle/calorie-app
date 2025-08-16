package backend.testing.food;

import java.sql.*;

import backend.database.DatabaseManager;
import backend.food.FoodManager;
import backend.food.RecipeIngredient;

import static backend.database.DatabaseManager.DB_TEST_USERNAME;

public class RecipeIngredientTester {

    private Connection conn;

    public static void main(String[] args) throws Exception {
        RecipeIngredientTester tester = new RecipeIngredientTester();
        tester.testToString();
    }

    public RecipeIngredientTester() throws Exception {
        this.conn = new DatabaseManager().establishConnection(DB_TEST_USERNAME).getConnection();
    }

    private void testToString() throws SQLException {
        RecipeIngredient testObject = new RecipeIngredient(new FoodManager(this.conn).getFood(0), 150);
        System.out.println(testObject.toString());
    }
}
