package database;
import bankaccount.BankAccount;
import java.sql.*;
import java.util.ArrayList;
public class BankAccountDAO {
    // Save a new bank account to the database
    public void saveBankAccount(BankAccount account) {
        String query = "INSERT INTO bankaccounts (customerId, first_name, last_name, account_type, status, balance, pin) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setInt(1, account.getCustomerId()); // Insert customerId
            ps.setString(2, account.getFirstName()); // Insert first name
            ps.setString(3, account.getLastName()); // Insert last name
            ps.setString(4, account.getAccountType());
            ps.setString(5, account.getAccountStatus());
            ps.setDouble(6, account.getBalance());
            ps.setInt(7, account.getPin()); // Insert PIN
            
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
    public static BankAccount getBankAccountById(int accountNumber) {
        String query = "SELECT * FROM bankaccounts WHERE account_number = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, accountNumber);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                // Rebuild the BankAccount object from the result set
                BankAccount account = new BankAccount(
                    rs.getString("first_name"), // Retrieve first name
                    rs.getString("last_name"), // Retrieve last name
                    rs.getString("account_type"),
                    rs.getString("status"),
                    rs.getInt("pin") // Retrieve pin
                );
                account.setBalance(rs.getDouble("balance")); // Set the balance from DB
                account.setCustomerId(rs.getInt("customerId")); // Set the customer ID
                account.setAccountNumber(rs.getInt("account_number")); // Set the account number
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
                    rs.getString("first_name"), // Retrieve first name
                    rs.getString("last_name"), // Retrieve last name
                    rs.getString("account_type"),
                    rs.getString("status"),
                    rs.getInt("pin")
                );
                account.setBalance(rs.getDouble("balance"));
                account.setCustomerId(rs.getInt("customerId")); // Set the customer ID
                account.setAccountNumber(rs.getInt("account_number")); // Set the account number
                accounts.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }
}