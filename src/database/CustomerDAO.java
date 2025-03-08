package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import user.Customer;

public class CustomerDAO {

    // Method to save a customer to the database
    public void saveCustomer(Customer customer) {
    String sql = "INSERT INTO customers (lastName, firstName, email, password, phoneNumber, birthDate, governmentId) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";        
            try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            // Set parameters for the PreparedStatement
            stmt.setString(1, customer.getLastName());
            stmt.setString(2, customer.getFirstName());
            stmt.setString(3, customer.getEmail());
            stmt.setString(4, customer.getPassword());
            stmt.setString(5, customer.getPhoneNumber());
            stmt.setDate(6, java.sql.Date.valueOf(customer.getBirthDate()));
            stmt.setString(7, customer.getMaskedGovernmentId());
          
            // Execute the insert statement
            int affectedRows = stmt.executeUpdate();

            // Get the generated customerId
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        // Set the auto-generated customerId
                        customer.setCustomerId(generatedKeys.getInt(1)); // Setting customerId after insert
                    } else {
                        throw new SQLException("Failed to retrieve customer ID.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update customer's password in the database
public void updatePasswordInDatabase(int customerID, String newPassword) {
    String query = "UPDATE customers SET password = ? WHERE customerID = ?";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(query)) {

        // Set the new password and user ID in the PreparedStatement
        ps.setString(1, newPassword);  // Setting the new password
        ps.setInt(2, customerID);  // Setting the user ID for identifying the customer

        // Execute the update query
        ps.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


    // Method to retrieve a customer by their ID
    public Customer getCustomerById(int customerID) {
        String query = "SELECT * FROM customers WHERE customerID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, customerID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                // Create and return the customer object using data from the result set
                Customer customer = new Customer(
                        rs.getString("lastName"),
                        rs.getString("firstName"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("password"), // This may not be ideal, consider creating a setter method
                        rs.getString("phoneNumber"),
                        rs.getDate("birthDate").toLocalDate(),
                        rs.getString("governmentId")
                );
                customer.setUserId(rs.getInt("customerID"));
                return customer;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method to retrieve all customers
    public static List<Customer> getAllCustomers() {
        List<Customer> customerList = new ArrayList<>();
        String query = "SELECT * FROM customers";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                // Create and populate the customer object
                Customer customer = new Customer(
                        rs.getString("lastName"),
                        rs.getString("firstName"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("password"), // This may not be ideal, consider creating a setter method
                        rs.getString("phoneNumber"),
                        rs.getDate("birthDate").toLocalDate(),
                        rs.getString("governmentId")
                );
                customer.setUserId(rs.getInt("customerID"));
                customerList.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerList;
    }

    // Method to update customer information
    public void updateCustomer(Customer customer) {
        String query = "UPDATE customers SET firstName = ?, lastName = ?, email = ?, phoneNumber = ?, birthDate = ?, governmentId = ? WHERE customerID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            // Set the customer data in the PreparedStatement
            ps.setString(1, customer.getFirstName());
            ps.setString(2, customer.getLastName());
            ps.setString(3, customer.getEmail());
            ps.setString(4, customer.getPhoneNumber());
            ps.setDate(5, Date.valueOf(customer.getBirthDate())); // Convert LocalDate to SQL Date
            ps.setString(6, customer.getMaskedGovernmentId());
            ps.setInt(7, customer.getUserId());
            // Execute the update query
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to delete a customer from the database
    public void deleteCustomer(int customerID) {
        String query = "DELETE FROM customers WHERE user_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            // Set the customer ID for deletion
            ps.setInt(1, customerID);

            // Execute the delete query
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Add this method to the CustomerDAO class in database/CustomerDAO.java

public Customer getCustomerByEmailOrPhone(String emailOrPhone) {
    String query = "SELECT * FROM customers WHERE email = ? OR phoneNumber = ?";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(query)) {
        
        ps.setString(1, emailOrPhone);
        ps.setString(2, emailOrPhone);
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
            // Create and return the customer object
            Customer customer = new Customer(
                rs.getString("lastName"),
                rs.getString("firstName"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getString("password"), // Using password for confirmPassword
                rs.getString("phoneNumber"),
                rs.getDate("birthDate").toLocalDate(),
                rs.getString("governmentId")
            );
            customer.setUserId(rs.getInt("customerID"));
            return customer;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}
}
