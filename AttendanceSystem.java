import java.sql.*;
import java.util.Scanner;

public class AttendanceSystem {

    // DB Connection
    static final String URL = "jdbc:mysql://localhost:3306/attendance_db";
    static final String USER = "root";
    static final String PASS = "1234"; // change this

    public static Connection getConnection() throws Exception {
        // ⭐ FIX ADDED HERE
        Class.forName("com.mysql.cj.jdbc.Driver");

        return DriverManager.getConnection(URL, USER, PASS);
    }


    // student add krn lyi...
    public static void addStudent(Scanner sc) {
        try {
            System.out.print("Enter Name: ");
            String name = sc.next();

            System.out.print("Enter Course: ");
            String course = sc.next();

            Connection conn = getConnection();
            String query = "INSERT INTO students(name, course) VALUES (?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, name);
            ps.setString(2, course);
            ps.executeUpdate();

            System.out.println("Student Added");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Attandance mark krn lyii...
    public static void markAttendance(Scanner sc) {
        try {
            System.out.print("Enter Student ID: ");
            int id = sc.nextInt();

            System.out.print("Enter Date (YYYY-MM-DD): ");
            String date = sc.next();

            System.out.print("Status (Present/Absent): ");
            String status = sc.next();

            Connection conn = getConnection();
            String query = "INSERT INTO attendance(student_id, date, status) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setInt(1, id);
            ps.setString(2, date);
            ps.setString(3, status);
            ps.executeUpdate();

            System.out.println(" Attendance Marked");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // student vekhan layii....
    public static void viewStudents() {
        try {
            Connection conn = getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM students");

            System.out.println("\n--- Student List ---");
            while (rs.next()) {
                System.out.println(
                    rs.getInt("id") + " | " +
                    rs.getString("name") + " | " +
                    rs.getString("course")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // attandance vekhan layii....

    public static void viewAttendance() {
        try {
            Connection conn = getConnection();
            Statement st = conn.createStatement();

            String query = "SELECT s.name, a.date, a.status " +
                           "FROM attendance a JOIN students s " +
                           "ON a.student_id = s.id";

            ResultSet rs = st.executeQuery(query);

            System.out.println("\n--- Attendance Record ---");
            while (rs.next()) {
                System.out.println(
                    rs.getString("name") + " | " +
                    rs.getDate("date") + " | " +
                    rs.getString("status")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Main Menu
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== Attendance System =====");
            System.out.println("1. Add Student");
            System.out.println("2. Mark Attendance");
            System.out.println("3. View Students");
            System.out.println("4. View Attendance");
            System.out.println("5. Exit");

            System.out.print("Choose: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    addStudent(sc);
                    break;

                case 2:
                    markAttendance(sc);
                    break;

                case 3:
                    viewStudents();
                    break;

                case 4:
                    viewAttendance();
                    break;

                case 5:
                    System.out.println(" Exiting...");
                    System.exit(0);

                default:
                    System.out.println(" Invalid Choice");
            }
        }
    }
}


// cd "C:\Users\paras girdhar\Downloads\4th sem-project"
// javac -cp ".;lib\*" AttendanceSystem.java
// java -cp ".;lib\*" AttendanceSystem
