package database;
import bankaccount.BankAccount;
import java.sql.*;
import java.util.ArrayList;
import user.Customer;

public class BankAccountDAO {

    // Save a new bank account to the database
    public void saveBankAccount(BankAccount account) {
        String query = "INSERT INTO bankaccounts (first_name, last_name, account_type, status, balance) VALUES (?, ?, ?, ?, ?)";
        Customer customer = new Customer();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
    
            ps.setString(1, customer.getFirstName());  // Insert first name
            ps.setString(2, customer.getLastName());   // Insert last name
            ps.setString(3, account.getAccountType());
            ps.setString(4, account.getAccountStatus());
            ps.setDouble(5, account.getBalance());
    
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Account saved successfully!");
            } else {
                System.out.println("Failed to save account.");
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    

    // Retrieve a bank account by account number
    public BankAccount getBankAccountById(int accountNumber) {
        String query = "SELECT * FROM bankaccounts WHERE account_number = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, accountNumber);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                // Rebuild the BankAccount object from the result set
                BankAccount account = new BankAccount(
                        rs.getString("first_name"),  // Retrieve first name
                        rs.getString("last_name"),   // Retrieve last name
                        rs.getString("account_type"),
                        rs.getString("status")
                );
                account.setBalance(rs.getDouble("balance")); // Set the balance from DB
                return account;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Retrieve all bank accounts
    public ArrayList<BankAccount> getAllBankAccounts() {
        ArrayList<BankAccount> accounts = new ArrayList<>();
        String query = "SELECT * FROM bankaccounts";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                // Rebuild the BankAccount object from the result set
                BankAccount account = new BankAccount(
                        rs.getString("first_name"),  // Retrieve first name
                        rs.getString("last_name"),   // Retrieve last name
                        rs.getString("account_type"),
                        rs.getString("status")
                );
                account.setBalance(rs.getDouble("balance"));
                accounts.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }
}
