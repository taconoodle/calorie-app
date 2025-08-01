import backend.testing.*;

public class Main {
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
}