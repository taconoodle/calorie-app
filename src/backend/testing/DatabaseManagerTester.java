package backend.testing;

import backend.database.DatabaseManager;

import java.sql.SQLException;

public class DatabaseManagerTester {
    public static void main(String[] args) throws Exception {
        DatabaseManager dbManager = new DatabaseManager();
//        dbManager.establishConnection("postgres", dbManager.getPassword("postgres"));
        System.out.println(dbManager.getPassword("postgres"));
    }
}
