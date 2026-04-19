package model;
import java.time.LocalDate;

/**
 * Represents cycling activity with speed.
 */

public class CyclingActivity extends Activity {
    private double speed;

    public CyclingActivity(int id, LocalDate date, int duration, double calories, double speed) {
        super(id, date, duration, calories);
        this.speed = speed;
    }

    public double getSpeed() {
        return speed;
    }

    @Override
    public String toFileString() {
        return getId() + ",CYCLING," + getDate() + "," +
                getDuration() + "," + getCalories() + "," + speed;
    }

    @Override
    public String displayInfo() {
        return super.displayInfo() + ", Speed: " + speed + " km/h";
    }
}