package app;
import util.InputValidator;
import model.RunningActivity;
import model.CyclingActivity;
import service.ActivityDAO;
import service.DatabaseConnection;
import java.time.LocalDate;
import java.util.Scanner;
import java.sql.Connection;

public class Main {

    public static int safeId(Scanner sc, String msg) {
        while (true) {
            System.out.print(msg);

            if (sc.hasNextInt()) {
                int id = sc.nextInt();

                if (id <= 0) {
                    System.out.println("ID must be positive!");
                    continue;
                }

                return id;
            } else {
                System.out.println("Invalid ID!");
                sc.next();
            }
        }
    }

    public static int safeInt(Scanner sc, String msg) {
        while (true) {
            System.out.print(msg);

            if (sc.hasNextInt()) {
                int value = sc.nextInt();

                if (value <= 0) {
                    System.out.println("Value must be positive!");
                    continue;
                }

                return value;
            } else {
                System.out.println("Invalid input! Enter a number.");
                sc.next();
            }
        }
    }

    public static double safeDouble(Scanner sc, String msg) {
        while (true) {
            System.out.print(msg);

            if (sc.hasNextDouble()) {
                double value = sc.nextDouble();

                if (value <= 0) {
                    System.out.println("Value must be positive!");
                    continue;
                }

                return value;
            } else {
                System.out.println("Invalid input! Enter a number.");
                sc.next();
            }
        }
    }


    private static void addActivity(Scanner sc, ActivityDAO dao) {


        int type = InputValidator.readChoice(sc,
                "Enter activity type (1 - Running, 2 - Cycling): ", 1, 2);

        int id;

        while (true) {
            id = InputValidator.readPositiveInt(sc, "Enter activity ID: ");

            if (dao.exists(id)) {
                System.out.println(" Activity ID already exists. Try again.");
            } else {
                break;
            }
        }

        int duration = InputValidator.readPositiveInt(sc,  "Enter duration (minutes): ");
        double calories = InputValidator.readPositiveDouble(sc, "Enter calories: ");

        if (type == 1) {
            double distance = InputValidator.readPositiveDouble(sc, "Enter distance (km): ");
            dao.add(new RunningActivity(id, LocalDate.now(), duration, calories, distance));
        } else {
            double speed = InputValidator.readPositiveDouble(sc, "Enter speed: ");
            dao.add(new CyclingActivity(id, LocalDate.now(), duration, calories, speed));
        }

        System.out.println("Added successfully!");
    }


    public static void main(String[] args) {

        System.out.println(new java.io.File("fitness.db").getAbsolutePath());

        Connection conn = DatabaseConnection.connect();
        DatabaseConnection.createTable();


        ActivityDAO dao = new ActivityDAO();

        conn = DatabaseConnection.connect();

        if (conn != null) {
            System.out.println("Connected to SQLite!");
        } else {
            System.out.println("Connection failed!");
        }


        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("========================================");
            System.out.println("        WELCOME TO FITNESS TRACKER      ");
            System.out.println("========================================");
            System.out.println("   Track your running and cycling data  ");
            System.out.println("       Manage your workouts easily      ");
            System.out.println("========================================\n");
            System.out.println("Enter a number or command to start:");

            System.out.println("1 / add     - Add activity");
            System.out.println("2 / view    - View all activities");
            System.out.println("3 / delete  - Delete activity");
            System.out.println("4 / update  - Update activity");
            System.out.println("5 / export  - Export data to CSV");
            System.out.println("6 / import  - Import data from CSV");
            System.out.println("0 / exit    - Exit");
            System.out.print("Your choice: ");

            String input = sc.next().trim().toLowerCase();

            try {
                switch (input) {

                    case "1":
                    case "add":

                        addActivity(sc, dao);
                        break;

                    case "2":
                    case "view":
                        dao.getAll().forEach(a -> System.out.println(a.displayInfo()));
                        break;


                    case "3":
                    case "delete":
                        int delId = safeId(sc, "Enter activity ID to delete: ");
                        dao.delete(delId);
                        System.out.println("Deleted!");
                        break;


                    case "4":
                    case "update":

                        int upId = safeId(sc, "Enter  activity ID to update: ");
                        int newDuration = safeInt(sc, "New duration: ");
                        double newCalories = safeDouble(sc, "New calories: ");

                        dao.update(upId, newDuration, newCalories);
                        System.out.println("Updated!");
                        break;

                    case "5":
                    case "export":
                        dao.exportToCSV("export.csv");
                        break;

                    case "6":
                    case "import":
                        dao.importFromCSV("export.csv");
                        break;


                    case "0":
                    case "exit":
                        System.out.println(" Thank you for using Fitness Tracker! Bye!");
                        System.exit(0);
                        break;

                    default:
                        System.out.println("Invalid option!");
                }

            }
            catch(Exception e){
                    System.out.println("Unexpected error occurred. Please try again.");

                }

        }
    }
}