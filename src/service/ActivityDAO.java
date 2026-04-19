package service;
import model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.sql.*;
import model.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Handles database operations (CRUD) using JDBC SQLite.
 */
public class ActivityDAO {

    /**
     * Inserts new activity into database.
     *
     * @param a activity object
     */
    public void add(Activity a) {

        String sql = "INSERT INTO activities(id, type, date, duration, calories, value) VALUES(?,?,?,?,?,?)";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, a.getId());
            ps.setString(2, (a instanceof RunningActivity) ? "RUNNING" : "CYCLING");
            ps.setString(3, a.getDate().toString());
            ps.setInt(4, a.getDuration());
            ps.setDouble(5, a.getCalories());

            if (a instanceof RunningActivity r) {
                ps.setDouble(6, r.getDistance());
            } else if (a instanceof CyclingActivity c) {
                ps.setDouble(6, c.getSpeed());
            }

            ps.executeUpdate();
            System.out.println("Saved to DB!");

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * Retrieves all activities from database.
     *
     * @return list of activities
     */
    public List<Activity> getAll() {

        List<Activity> list = new ArrayList<>();

        String sql = "SELECT * FROM activities";

        try (Connection conn = DatabaseConnection.connect();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {

                int id = rs.getInt("id");
                String type = rs.getString("type");
                LocalDate date = LocalDate.parse(rs.getString("date"));
                int duration = rs.getInt("duration");
                double calories = rs.getDouble("calories");
                double value = rs.getDouble("value");

                if (type.equals("RUNNING")) {
                    list.add(new RunningActivity(id, date, duration, calories, value));
                } else {
                    list.add(new CyclingActivity(id, date, duration, calories, value));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


    /**
     * Deletes activity by ID.
     *
     * @param id activity ID
     */
    public void delete(int id) {

        String sql = "DELETE FROM activities WHERE id=?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Updates activity data.
     *
     * @param id activity ID
     * @param duration new duration
     * @param calories new calories
     */
    public void update(int id, int duration, double calories) {

        String sql = "UPDATE activities SET duration=?, calories=? WHERE id=?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, duration);
            ps.setDouble(2, calories);
            ps.setInt(3, id);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Checks if activity exists in database.
     *
     * @param id activity ID
     * @return true if exists
     */
    public boolean exists(int id) {

        String sql = "SELECT id FROM activities WHERE id=?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Exports data to CSV file.
     *
     * @param fileName file name
     */
    public void exportToCSV(String fileName) {

        String sql = "SELECT * FROM activities";

        try (Connection conn = DatabaseConnection.connect();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql);
             BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {


            bw.write("id,type,date,duration,calories,value");
            bw.newLine();

            while (rs.next()) {

                String line =
                        rs.getInt("id") + "," +
                                rs.getString("type") + "," +
                                rs.getString("date") + "," +
                                rs.getInt("duration") + "," +
                                rs.getDouble("calories") + "," +
                                rs.getDouble("value");

                bw.write(line);
                bw.newLine();
            }

            System.out.println("Export completed!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Imports data from CSV file.
     *
     * @param fileName file name
     */
    public void importFromCSV(String fileName) {

        String sql = "INSERT INTO activities(id,type,date,duration,calories,value) VALUES(?,?,?,?,?,?)";

        try (Connection conn = DatabaseConnection.connect();
             BufferedReader br = new BufferedReader(new FileReader(fileName));
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String line = br.readLine();

            while ((line = br.readLine()) != null) {

                String[] p = line.split(",");

                int id = Integer.parseInt(p[0]);

                if (exists(id)) {
                    System.out.println("Skipping ID (already exists): " + id);
                    continue;
                }

                ps.setInt(1, Integer.parseInt(p[0]));
                ps.setString(2, p[1]);
                ps.setString(3, p[2]);
                ps.setInt(4, Integer.parseInt(p[3]));
                ps.setDouble(5, Double.parseDouble(p[4]));
                ps.setDouble(6, Double.parseDouble(p[5]));

                ps.executeUpdate();
            }

            System.out.println("Import completed!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}