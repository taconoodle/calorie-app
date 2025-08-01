package backend.testing;

import backend.database.DatabaseManager;
import backend.food.FoodManager;
import backend.food.Food;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;

/*
The FoodManagerTester class is a class storing test methods.
Each method is supposed to test a different aspect of the FoodManager class.
NOTE-TO-SELF: Will have to add more methods to test different cases of each FoodManager method:
ex. for the addFood method (adds a new Food in the database), cover both the exists and not-exists case
 */

public class FoodManagerTester {
    Connection conn;
    private final String DB_TEST_USERNAME = "postgres";

    public FoodManagerTester() throws Exception {
        this.conn = new DatabaseManager().establishConnection(DB_TEST_USERNAME).getConnection();
    }

    public static void main(String[] args) throws Exception {
        FoodManagerTester tester = new FoodManagerTester();
        tester.addFoodTest();
        tester.getFoodTest();
        tester.deleteFoodTest();
        tester.getFoodTest();

        tester.getFoodUnderCalsTest(true);
        tester.getFoodUnderCalsTest(false);

        tester.getFoodOverCalsTest(true);
        tester.getFoodOverCalsTest(false);
    }

    public void addFoodTest() throws Exception {
        System.out.println("\n---------- STARTING ADD FOOD TEST ----------\n" +
                             "--- ATTEMPTING TO INSERT FOOD WITH ID -1 ---\n");
        FoodManager manager = new FoodManager(this.conn);

        Food testFood = new Food(-1, "TestBrand", "TestModel", 100.1, 200.2, 300.3);
        if (manager.addFood(testFood)) {
            System.out.println("Successfully added food with ID: " + testFood.getFoodId() + " to database\n");
        }
    }

    public void getFoodTest() throws Exception {
        System.out.println("\n---------- STARTING GET FOOD TEST ----------\n" +
                             "---- ATTEMPTING TO FIND FOOD WITH ID -1 ----\n");
        FoodManager manager = new FoodManager(this.conn);
        //Attempt to pull from the database the food with ID -1 (test ID)
        Food newFood = manager.getFood(-1);

        if (newFood != null) {
            System.out.println("Food pulled from DB:\n" + newFood.toString() + "\n");
        }
    }

    public void deleteFoodTest() throws Exception {
        System.out.println("\n---------- STARTING DELETE FOOD TEST ----------\n" +
                             "----- ATTEMPTING TO DELETE FOOD WITH ID -1 ----\n");
        FoodManager manager = new FoodManager(this.conn);

        //Attempt to delete food with ID -1 (test ID) from database
        if (manager.deleteFood(-1)) {
            System.out.println("Successfully deleted food with ID -1 from database.\n");
        }
    }

    public void getFoodUnderCalsTest(boolean fail) throws Exception {
        FoodManager manager = new FoodManager(this.conn);
        double calorieLimit;

        Food testFood1 = new Food(-1, "TestBrand", "TestModel", 100.1, 200.2, 300.3);
        manager.addFood(testFood1);
        Food testFood2 = new Food(-2, "TestBrand", "TestModel", 100.1, 200.2, 300.3);
        manager.addFood(testFood2);

        if (fail) {
            System.out.println("\n---------- STARTING FIND FOOD UNDER GIVEN CALORIES TEST ----------\n" +
                                 "---------- ATTEMPTING TO FIND FOOD WITH CALORIES UNDER 0 ---------\n");

            calorieLimit = 0;
        }
        else {
            System.out.println("\n---------- STARTING FIND FOOD UNDER GIVEN CALORIES TEST ----------\n" +
                                 "--------- ATTEMPTING TO FIND FOOD WITH CALORIES UNDER 105 --------\n");

            calorieLimit = 105;
        }

        ArrayList<Food> result = manager.getFoodUnderCals(calorieLimit);

        //Delete the test foods from the database first
        manager.deleteFood(testFood1.getFoodId());
        manager.deleteFood(testFood2.getFoodId());

        if (result.isEmpty()) {
            System.out.println("No foods with calories under " + calorieLimit + " were found.\n");
        }
        else {
            //We have to print the foods that have less calories than given
            System.out.println("The foods with calories less than " + calorieLimit + " are:\n");
            while (!result.isEmpty()) {
                System.out.println(result.removeFirst().toString());
            }
            System.out.println();
        }
    }

    public void getFoodOverCalsTest(boolean fail) throws Exception {
        FoodManager manager = new FoodManager(this.conn);
        double calorieLimit;

        Food testFood1 = new Food(-1, "TestBrand", "TestModel", 100.1, 200.2, 300.3);
        manager.addFood(testFood1);
        Food testFood2 = new Food(-2, "TestBrand", "TestModel", 100.1, 200.2, 300.3);
        manager.addFood(testFood2);

        if (fail) {
            System.out.println("\n---------- STARTING FIND FOOD OVER GIVEN CALORIES TEST ----------\n" +
                                 "-------- ATTEMPTING TO FIND FOOD WITH CALORIES OVER 6000 --------\n");

            //6000 should be safe to test. Could always raise the number later.
            calorieLimit = 6000;
        }
        else {
            System.out.println("\n---------- STARTING FIND FOOD OVER GIVEN CALORIES TEST ----------\n" +
                                 "---------- ATTEMPTING TO FIND FOOD WITH CALORIES OVER 0 ---------\n");

            calorieLimit = 0;

        }

        ArrayList<Food> result = manager.getFoodOverCals(calorieLimit);

        //Delete the test foods from the database first
        manager.deleteFood(testFood1.getFoodId());
        manager.deleteFood(testFood2.getFoodId());

        if (result.isEmpty()) {
            System.out.println("No foods with calories over " + calorieLimit + " were found.\n");
        }
        else {
            //We have to print the foods that have less calories than given
            System.out.println("The foods with calories more than " + calorieLimit + " are:\n");
            while (!result.isEmpty()) {
                System.out.println(result.removeFirst().toString());
            }
            System.out.println();
        }
    }
}
