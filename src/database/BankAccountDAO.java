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
    public BankAccount getBankAccountById(int accountNumber) {
        String query = "SELECT * FROM bankaccounts WHERE account_number = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setInt(1, accountNumber);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToBankAccount(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching bank account by ID: " + accountNumber, e);
        }
        return null; // Return null if no account is found
    }

    // Retrieve all bank accounts
    public ArrayList<BankAccount> getAllBankAccounts() {
        ArrayList<BankAccount> accounts = new ArrayList<>();
        String query = "SELECT * FROM bankaccounts";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                accounts.add(mapResultSetToBankAccount(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching all bank accounts", e);
        }
        return accounts;
    }

    // Helper method to map ResultSet to BankAccount object
    private static BankAccount mapResultSetToBankAccount(ResultSet rs) throws SQLException {
        BankAccount account = new BankAccount(
            rs.getString("first_name"),
            rs.getString("last_name"),
            rs.getString("account_type"),
            rs.getString("status"),
            rs.getInt("pin")
        );
        account.setBalance(rs.getDouble("balance"));
        account.setCustomerId(rs.getInt("customerId"));
        account.setAccountNumber(rs.getInt("account_number"));
        return account;
    }

    public BankAccount getBankAccountByAccountNumber(int accountNumber) {
        String query = "SELECT * FROM bankaccounts WHERE account_number = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
    
            ps.setInt(1, accountNumber);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToBankAccount(rs); // Use the existing mapping method
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching bank account by account number: " + accountNumber, e);
        }
        return null; // Return null if no account is found
    }
    
    

    // Method to retrieve a BankAccount by customerId
    public BankAccount getBankAccountByCustomerId(int customerId) {
        String query = "SELECT * FROM bankaccounts WHERE customerId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
    
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                // Use the helper method to map the ResultSet to a BankAccount object
                return mapResultSetToBankAccount(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if no account is found
    }

    public BankAccount getBankAccountByEmailOrPhone(String emailOrPhone) {
        String query = "SELECT * FROM bankaccounts WHERE email = ? OR phone_number = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, emailOrPhone);
            ps.setString(2, emailOrPhone);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToBankAccount(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching bank account by email or phone: " + emailOrPhone, e);
        }
        return null; // Return null if no account is found
    }

    // Add this method to the BankAccountDAO class in database/BankAccountDAO.java

public ArrayList<BankAccount> getBankAccountsByCustomerId(int customerId) {
    ArrayList<BankAccount> accounts = new ArrayList<>();
    String query = "SELECT * FROM bankaccounts WHERE customerId = ?";
    
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(query)) {
        
        ps.setInt(1, customerId);
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
            BankAccount account = mapResultSetToBankAccount(rs);
            accounts.add(account);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw new RuntimeException("Error fetching bank accounts for customer: " + customerId, e);
    }
    
    return accounts;
}

public int getPinForAccount(int accountNumber) {
    String query = "SELECT pin FROM bankaccounts WHERE account_number = ?";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(query)) {

        ps.setInt(1, accountNumber);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("pin"); // Return the stored PIN
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw new RuntimeException("Error fetching PIN for account number: " + accountNumber, e);
    }
    return -1; // Return -1 if no account or PIN found
}
    
    

}