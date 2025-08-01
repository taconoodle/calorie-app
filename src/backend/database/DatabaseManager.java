package backend.database;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class DatabaseManager {
    private Connection conn;

    public DatabaseManager() throws Exception {
        LoadDriver();
    }

    public DatabaseManager establishConnection(String username, String password) throws SQLException {
        try {
            this.conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/calories-app", username, password);
            System.out.println("Connection to database established successfully.");
            return this;
        }
        catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            throw e;
        }
    }

    public DatabaseManager establishConnection(String username) throws SQLException {
        try {
            String password = getPassword(username);
            this.conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/calories-app", username, password);
            System.out.println("Connection to database established successfully.");
            return this;
        }
        catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            throw e;
        }
    }

    private void LoadDriver() throws Exception {
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("PostgreSQL database driver loaded successfully!");
        }
        catch(ClassNotFoundException e) {
            System.err.println("PostgreSQL driver not found!");
            throw new Exception("No JDBC driver was found.");
        }
    }

    public Connection getConnection() {
        return this.conn;
    }

    public String getPassword(String username) {
        String filePath = "./config/auth_file.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while((line = reader.readLine()) != null){
                String[] lineData = line.split(":");
                if (lineData[0].trim().equals(username)) {
                    return lineData[1].trim();
                }
            }
        }
        catch(FileNotFoundException e) {
            System.out.println("Password file does not exist.");
        }
        catch(IOException e) {
            System.out.println("Something went wrong while fetching the password from " + filePath);
        }
        return null;

    }
}
