package database;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import user.Staff;
public class StaffDAO {
    // Method to save a staff member to the database
    public void saveStaff(Staff staff) {
        String sql = "INSERT INTO staff (staffId,lastName, firstName, email, password, phoneNumber, birthDate, governmentId, role) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            // Set parameters for the PreparedStatement
            stmt.setString(1, staff.getLastName());
            stmt.setString(2, staff.getFirstName());
            stmt.setString(3, staff.getEmail());
            stmt.setString(4, staff.getPassword());
            stmt.setString(5, staff.getPhoneNumber());
            stmt.setDate(6, java.sql.Date.valueOf(staff.getBirthDate()));
            stmt.setString(7, staff.getMaskedGovernmentId());
            stmt.setString(8, staff.getPosition());

            // Execute the insert statement
            int affectedRows = stmt.executeUpdate();

            // Get the generated staffId
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        // Set the auto-generated staffId
                        staff.setStaffId(generatedKeys.getInt(1)); // Setting staffId after insert
                    } else {
                        throw new SQLException("Failed to retrieve staff ID.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }
}
