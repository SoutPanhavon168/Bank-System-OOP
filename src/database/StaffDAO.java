package database;
import java.sql.*;
import user.Staff;
public class StaffDAO {
    // Method to save a staff member to the database
    public void saveStaff(Staff staff) {
        String sql = "INSERT INTO Staff (lastName, firstName, email, password, phoneNumber, birthDate, governmentId, role) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
    
            // Set parameters for the PreparedStatement
            stmt.setString(1, staff.getLastName());
            stmt.setString(2, staff.getFirstName());
            stmt.setString(3, staff.getEmail());
            stmt.setString(4, staff.getPassword());
            stmt.setString(5, staff.getPhoneNumber());
    
            // Handle null birthDate
            if (staff.getBirthDate() != null) {
                stmt.setDate(6, java.sql.Date.valueOf(staff.getBirthDate()));
            } else {
                stmt.setNull(6, java.sql.Types.DATE); // If birthDate is null, set it as null in the database
            }
    
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
    public void deleteStaff(int staffId) {
        String sql = "DELETE FROM Staff WHERE staffId = ?";
    
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, staffId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateStaffPassword(int staffId, String newPassword) {
        String query = "UPDATE Staff SET password = ? WHERE staffId = ?";
    
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, newPassword);
            ps.setInt(2, staffId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public int getStaffIdBystaffId(int staffId) {
        String query = "SELECT staffId FROM Staff WHERE staffId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) { 
            ps.setInt(1, staffId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("StaffId"); // Return the found customerId
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log the exception (consider using a logger in real applications)
        }
        return -1; // Return -1 if customerId is not found
    }
}
