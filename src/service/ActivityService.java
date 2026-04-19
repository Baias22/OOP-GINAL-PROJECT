package service;

import model.Activity;
import java.util.List;

public class ActivityService {

    private ActivityDAO dao = new ActivityDAO();

    public void addActivity(Activity activity) {
        dao.add(activity);
    }

    public void viewActivities() {
        List<Activity> list = dao.getAll();

        if (list.isEmpty()) {
            System.out.println("No activities found.");
            return;
        }

        for (Activity a : list) {
            System.out.println(a.displayInfo());
        }
    }

    public void deleteActivity(int id) {
        dao.delete(id);
    }

    public void updateActivity(int id, int duration, double calories) {
        dao.update(id, duration, calories);
    }

    public boolean exists(int id) {
        return dao.exists(id);
    }
}
