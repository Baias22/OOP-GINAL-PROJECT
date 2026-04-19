package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
public class DatabaseConnection {


    /**
     * Manages SQLite database connection and table creation.
     */
    public static Connection connect() {
        try {
            return DriverManager.getConnection("jdbc:sqlite:C:/Users/user/IdeaProjects/OOP_PROGECT/fitness.db");
        } catch (Exception e) {
            System.out.println("Connection error: " + e.getMessage());
            return null;
        }
    }

    public static void createTable() {

        String sql = """
        CREATE TABLE IF NOT EXISTS activities (
            id INTEGER PRIMARY KEY,
            type TEXT,
            date TEXT,
            duration INTEGER,
            calories REAL,
            value REAL
        );
    """;

        try (Connection conn = connect();
             Statement st = conn.createStatement()) {

            st.execute(sql);
            System.out.println("Table created or already exists!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}