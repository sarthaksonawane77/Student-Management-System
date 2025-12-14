import java.sql.*;

public class StudentManagementDemo {

    public static void main(String[] args) {
        // Database connection info
        String url = "jdbc:mysql://localhost:3306/studentdb"; // replace with your DB
        String username = "root";
        String password = "password"; // replace with your DB password

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Student ID: ");
        int studentId = sc.nextInt();
        sc.nextLine(); // consume newline
        System.out.print("Enter Student Name: ");
        String studentName = sc.nextLine();


        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Connected to database successfully.");

            // INSERT operation using PreparedStatement
            String insertSQL = "INSERT INTO students (id, name) VALUES (?, ?)";
            try (PreparedStatement ps = connection.prepareStatement(insertSQL)) {
                ps.setInt(1, studentId);
                ps.setString(2, studentName);
                int rowsInserted = ps.executeUpdate();
                System.out.println(rowsInserted + " row(s) inserted.");
            }

            // SELECT operation
            String selectSQL = "SELECT * FROM students";
            try (Statement stmt = connection.createStatement();       //this try block is "trywithresouce"
                 ResultSet rs = stmt.executeQuery(selectSQL)) {

                System.out.println("Student Records:");
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    System.out.println("ID: " + id + ", Name: " + name);
                }
            }

            // UPDATE operation
            String updateSQL = "UPDATE students SET name = ? WHERE id = ?";
            try (PreparedStatement ps = connection.prepareStatement(updateSQL)) {
                ps.setString(1, "Jane Doe");
                ps.setInt(2, studentId);
                int rowsUpdated = ps.executeUpdate();
                System.out.println(rowsUpdated + " row(s) updated.");
            }

            // DELETE operation
            String deleteSQL = "DELETE FROM students WHERE id = ?";
            try (PreparedStatement ps = connection.prepareStatement(deleteSQL)) {
                ps.setInt(1, studentId);
                int rowsDeleted = ps.executeUpdate();
                System.out.println(rowsDeleted + " row(s) deleted.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
