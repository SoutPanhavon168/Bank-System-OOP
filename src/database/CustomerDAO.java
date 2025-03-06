package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import user.Customer;

public class CustomerDAO {

    // Method to save a customer to the database
    public void saveCustomer(Customer customer) {
        String query = "INSERT INTO customers (userId, firstName, lastName, email, password, phoneNumber, birthDate, governmentId) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            // Set the customer data in the PreparedStatement
            ps.setInt(1, customer.getUserId());
            ps.setString(2, customer.getFirstName());
            ps.setString(3, customer.getLastName());
            ps.setString(4, customer.getEmail());
            ps.setString(5, customer.getPassword());
            ps.setString(6, customer.getPhoneNumber());
            ps.setDate(7, Date.valueOf(customer.getBirthDate())); // Convert LocalDate to SQL Date
            ps.setString(8, customer.getMaskedGovernmentId());

            // Execute the insert query
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update customer's password in the database
public void updatePasswordInDatabase(int userId, String newPassword) {
    String query = "UPDATE customers SET password = ? WHERE user_id = ?";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(query)) {

        // Set the new password and user ID in the PreparedStatement
        ps.setString(1, newPassword);  // Setting the new password
        ps.setInt(2, userId);  // Setting the user ID for identifying the customer

        // Execute the update query
        ps.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


    // Method to retrieve a customer by their ID
    public static Customer getCustomerById(int userId) {
        String query = "SELECT * FROM customers WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                // Create and return the customer object using data from the result set
                Customer customer = new Customer(
                        rs.getString("last_name"),
                        rs.getString("first_name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("password"), // This may not be ideal, consider creating a setter method
                        rs.getString("phone_number"),
                        rs.getDate("birth_date").toLocalDate(),
                        rs.getString("government_id")
                );
                customer.setUserId(rs.getInt("user_id"));
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
                        rs.getString("last_name"),
                        rs.getString("first_name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("password"), // This may not be ideal, consider creating a setter method
                        rs.getString("phone_number"),
                        rs.getDate("birth_date").toLocalDate(),
                        rs.getString("government_id")
                );
                customer.setUserId(rs.getInt("user_id"));
                customerList.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerList;
    }

    // Method to update customer information
    public static void updateCustomer(Customer customer) {
        String query = "UPDATE customers SET firstName = ?, lastName = ?, email = ?, phoneNumber = ?, birthDate = ?, governmentId = ? WHERE userId = ?";

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
    public void deleteCustomer(int userId) {
        String query = "DELETE FROM customers WHERE user_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            // Set the customer ID for deletion
            ps.setInt(1, userId);

            // Execute the delete query
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
