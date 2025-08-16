package backend.database;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

/**
 * The DatabaseManager class manages a connection with the database and acts as a middle-man between the backend and the database
 *
 * @author taconoodle
 */
public class DatabaseManager {
    /**
     * The main connection of the instance
     */
    private Connection conn;

    /**
     * The username used to test the database
     */
    public static final String DB_TEST_USERNAME = "postgres";

    /**
     * Default constructor
     *
     */
    public DatabaseManager() {
        LoadDriver();
    }

    /**
     * Attempt to connect to database
     *
     * @param username the username used to connect
     * @param password the password used to connect
     * @return self
     * @throws SQLException if a problem occurs while connecting (usually wrong credentials)
     */
    public DatabaseManager establishConnection(String username, String password) throws SQLException {
        try {
            this.conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/calories-app", username, password);
            System.out.println("Connection to database established successfully.");
            return this;
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            return null;
        }
    }

    /**
     * Attempt to connect to database
     *
     * @param username the username used to connect
     * @return self
     * @throws SQLException if a problem occurs while connecting (usually wrong credentials)
     */
    public DatabaseManager establishConnection(String username) throws SQLException {
        try {
            //The password is not provided in this method, get it from file
            String password = getPassword(username);
            this.conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/calories-app", username, password);
            System.out.println("Connection to database established successfully.");
            return this;
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Load the JDBC driver
     */
    private void LoadDriver() {
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("PostgreSQL database driver loaded successfully!");
        } catch (ClassNotFoundException e) {
            System.err.println("PostgreSQL driver not found!");
        }
    }

    /**
     * Gets the main connection
     *
     * @return the main database connection
     */
    public Connection getConnection() {
        return this.conn;
    }

    /**
     * Gets the requested password from config file
     * <p>
     * The credentials are stored in a "auth_file.txt" file inside the config folder.
     * Each credential should have the format "username:password"
     *
     * @param username the username of which the password will be fetched
     * @return the requested password
     */
    public String getPassword(String username) {
        String filePath = "./config/auth_file.txt";
        //read the lines one-by-one until you find the username
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] lineData = line.split(":");
                if (lineData[0].trim().equals(username)) {
                    return lineData[1].trim();
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Password file does not exist.");
        } catch (IOException e) {
            System.out.println("Something went wrong while fetching the password from " + filePath);
        }
        return null;

    }
}
