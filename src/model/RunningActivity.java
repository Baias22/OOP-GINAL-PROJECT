package model;
import java.time.LocalDate;
/**
 * Represents running activity with distance.
 */
public class RunningActivity extends Activity {
    private double distance;

    public RunningActivity(int id, LocalDate date, int duration, double calories, double distance) {
        super(id, date, duration, calories);
        this.distance = distance;
    }

    public double getDistance() { return distance; }

    @Override
    public String toFileString() {
        return super.toFileString() + "," + distance;
    }

    @Override
    public String displayInfo() {
        return super.displayInfo() + ", Distance: " + distance + " km";
    }
}