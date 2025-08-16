package backend.testing.food;

import backend.database.DatabaseManager;
import backend.food.FoodManager;
import backend.food.Recipe;
import backend.food.RecipeIngredient;

import java.sql.Connection;
import java.sql.SQLException;

import static backend.database.DatabaseManager.DB_TEST_USERNAME;

public class RecipeTester {
    private Connection conn;

    public static void main(String[] args) throws Exception {
        RecipeTester tester = new RecipeTester();
        tester.testToString();
    }

    public RecipeTester() throws Exception {
        this.conn = new DatabaseManager().establishConnection(DB_TEST_USERNAME).getConnection();
    }

    private void testToString() throws SQLException {
        Recipe testObject = new Recipe(0, "peos", "vrasto");
        for (int i = 0; i < 5; i++) {
            RecipeIngredient ingredient = new RecipeIngredient (
                    new FoodManager(this.conn).getFood(0),
                    150
            );
            testObject.addIngredient(ingredient);
        }
        System.out.println(testObject.toString());
    }
}
