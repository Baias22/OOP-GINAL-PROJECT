package model;
import java.time.LocalDate;

/**
 * Represents a physical activity (base class).
 * Stores common fields for all activities.
 *
 * @author Baias Joldoshbekov
 */

public class Activity {
    private int id;
    private LocalDate date;
    private int duration;
    private double calories;


    public Activity(int id, LocalDate date, int duration, double calories) {
        this.id = id;
        this.date = date;
        this.duration = duration;
        this.calories = calories;
    }

    public int getId() { return id; }
    public LocalDate getDate() { return date; }
    public int getDuration() { return duration; }
    public double getCalories() { return calories; }

    public void setDuration(int duration) { this.duration = duration; }
    public void setCalories(double calories) { this.calories = calories; }

    public String toFileString() {
        return id + ",RUNNING," + date + "," + duration + "," + calories;
    }

    public String displayInfo() {
        return "ID: " + id +
                ", Date: " + date +
                ", Duration: " + duration +
                ", Calories: " + calories;
    }
}