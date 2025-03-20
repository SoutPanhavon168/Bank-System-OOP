package database;
import bankaccount.BankAccount;
import java.sql.*;
import java.util.ArrayList;

public class BankAccountDAO {

    // Save a new bank account to the database
    public void saveBankAccount(BankAccount account) {
        String query = "INSERT INTO bankaccounts (account_number, customerId, first_name, last_name, account_type, status, balance, pin) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
    
            // Set parameters for the prepared statement
            ps.setInt(1, account.getAccountNumber()); // Insert account_number (manually generated)
            ps.setInt(2, account.getCustomerId()); // Insert customerId
            ps.setString(3, account.getFirstName()); // Insert first name
            ps.setString(4, account.getLastName()); // Insert last name
            ps.setString(5, account.getAccountType()); // Insert account type
            ps.setString(6, account.getAccountStatus()); // Insert account status
            ps.setDouble(7, account.getBalance()); // Insert balance
            ps.setInt(8, account.getPin()); // Insert PIN
            
            // Execute the update and check if any rows were affected
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
        Date createdDate = rs.getDate("created_at");  // Make sure the column name matches in the DB
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
        account.setCreatedDate(createdDate);
        return account;
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

    // Method to retrieve a BankAccount by email or phone
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

    // Method to retrieve bank accounts by customerId
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

    // Method to retrieve the PIN for a bank account
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

    // Method to update the PIN for a bank account
    public void updatePinForAccount(int accountNumber, int newPin) {
        String query = "UPDATE bankaccounts SET pin = ? WHERE account_number = ?";
    
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
        
            ps.setInt(1, newPin);  // Set the new PIN
            ps.setInt(2, accountNumber);  // Set the account number to update the correct account
        
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("PIN updated successfully.");
            } else {
                System.out.println("Failed to update PIN.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean freezeBankAccount(int accountNumber) {
        System.out.println("Fetching account details before freezing...");
        BankAccount account = getBankAccountById(accountNumber);
    
        if (account == null) {
            System.out.println("Account ID " + accountNumber + " does not exist.");
            return false;
        }
    
        System.out.println("Attempting to freeze account ID: " + accountNumber);
        String query = "UPDATE bankaccounts SET status = 'Inactive' WHERE account_number = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setInt(1, accountNumber);
            int rowsAffected = ps.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Account " + accountNumber + " frozen successfully.");
                return true;
            } else {
                System.out.println("Failed to freeze account.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    public boolean unfreezeBankAccount(int accountNumber) {
        System.out.println("Fetching account details before unfreezing...");
        BankAccount account = getBankAccountById(accountNumber);
    
        if (account == null) {
            System.out.println("Account ID " + accountNumber + " does not exist.");
            return false;
        }
    
        System.out.println("Attempting to unfreeze account ID: " + accountNumber);
        String query = "UPDATE bankaccounts SET status = 'Active' WHERE account_number = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setInt(1, accountNumber);
            int rowsAffected = ps.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Account " + accountNumber + " unfrozen successfully.");
                return true;
            } else {
                System.out.println("Failed to unfreeze account.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
}
