package database;

import bankaccount.BankAccount;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import transaction.Transaction;

public class TransactionDAO {

    // Method to save a transaction to the database with PIN verification
    public boolean saveTransaction(Transaction transaction) {
        // First verify PIN before proceeding with transaction
        

        String query = "INSERT INTO transactions (transaction_id, account_number, type, amount, status, transaction_date) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            // Set the transaction ID (generate a new one for each transaction)
            String transactionID = generateTransactionID();
            transaction.setTransactionID(transactionID);
            ps.setString(1, transactionID);

            // Get account number from the bank account linked to the transaction
            BankAccount account = transaction.getBankAccount();
            if (account != null) {
                ps.setInt(2, account.getAccountNumber());
            } else {
                ps.setInt(2, 0); // Handle case where account might be null
            }

            // Set the transaction type
            ps.setString(3, transaction.getType().name());

            // Set the transaction amount
            ps.setDouble(4, transaction.getAmount());

            // Set the transaction status (String)
            ps.setString(5, transaction.getStatus());

            // Set the transaction date (Now includes current date and time)
            LocalDateTime transactionDateTime = LocalDateTime.now();  // Capture current date and time
            transaction.setTransactionDate(transactionDateTime);
            ps.setTimestamp(6, Timestamp.valueOf(transactionDateTime));  // Convert to SQL Timestamp

            // Execute the insert query
            int result = ps.executeUpdate();
            if (result > 0) {
                System.out.println("Transaction saved successfully!");

                // After saving the transaction, update the account balance
                updateAccountBalance(account, transaction);

                return true;
            } else {
                System.out.println("Failed to save transaction to database.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("SQL Error when saving transaction: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private void updateAccountBalance(BankAccount account, Transaction transaction) {
        String updateQuery = "UPDATE bankaccounts SET balance = ? WHERE account_number = ?";

        // Calculate the new balance based on the transaction type
        double newBalance = 0.0;
        if (transaction.getType().name().equals("DEPOSIT")) {
            newBalance = account.getBalance() + transaction.getAmount();
        } else if (transaction.getType().name().equals("WITHDRAWAL")) {
            newBalance = account.getBalance() - transaction.getAmount();
        }
        // Add any other transaction types you need to handle

        // Update the balance in the database
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(updateQuery)) {

            ps.setDouble(1, newBalance);
            ps.setInt(2, account.getAccountNumber());
            int result = ps.executeUpdate();

            if (result > 0) {
                System.out.println("Account balance updated successfully.");
            } else {
                System.out.println("Failed to update account balance.");
            }
        } catch (SQLException e) {
            System.out.println("Error when updating account balance: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Method to generate a unique transaction ID (for each new transaction)
    private String generateTransactionID() {
        return "TXN-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss")) + "-" + System.currentTimeMillis();
    }

    // Method to retrieve a transaction by its ID
    public Transaction getTransactionById(String transactionID) {
        String query = "SELECT * FROM transactions WHERE transaction_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, transactionID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                // Fetch the bank account details from the database
                BankAccountDAO bankAccountDAO = new BankAccountDAO();
                int accountNumber = rs.getInt("account_number");
                BankAccount account = bankAccountDAO.getBankAccountById(accountNumber);

                // Create the transaction object with all the data from the database
                Transaction transaction = new Transaction(
                        account,
                        rs.getString("type"),
                        rs.getDouble("amount"),
                        rs.getString("status")
                );
                transaction.setTransactionID(rs.getString("transaction_id"));
                transaction.setTransactionDate(rs.getTimestamp("transaction_date").toLocalDateTime());

                return transaction;
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving transaction: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    // Method to retrieve all transactions for a specific account (with PIN verification)
    public List<Transaction> getTransactionsByAccount(int accountNumber) {
        // Get the account first to verify PIN
        BankAccountDAO bankAccountDAO = new BankAccountDAO();
        BankAccount account = bankAccountDAO.getBankAccountById(accountNumber);

        if (account == null) {
            System.out.println("Account not found.");
            return new ArrayList<>();
        }

        // Verify PIN before allowing access to transaction history
        Transaction transaction = new Transaction(account, "", 0.0, ""); // Dummy transaction object
        

        List<Transaction> transactionList = new ArrayList<>();
        String query = "SELECT * FROM transactions WHERE account_number = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, accountNumber);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Transaction dbTransaction = new Transaction(
                        account,
                        rs.getString("type"),
                        rs.getDouble("amount"),
                        rs.getString("status")
                );
                dbTransaction.setTransactionID(rs.getString("transaction_id"));
                dbTransaction.setTransactionDate(rs.getTimestamp("transaction_date").toLocalDateTime());

                transactionList.add(dbTransaction);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving transactions: " + e.getMessage());
            e.printStackTrace();
        }
        return transactionList;
    }

    // Method to retrieve all transactions (admin function, no PIN required)
    public List<Transaction> getAllTransactions() {
        List<Transaction> transactionList = new ArrayList<>();
        String query = "SELECT * FROM transactions";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                BankAccountDAO bankAccountDAO = new BankAccountDAO();
                int accountNumber = rs.getInt("account_number");
                BankAccount account = bankAccountDAO.getBankAccountById(accountNumber);

                Transaction transaction = new Transaction(
                        account,
                        rs.getString("type"),
                        rs.getDouble("amount"),
                        rs.getString("status")
                );
                transaction.setTransactionID(rs.getString("transaction_id"));
                transaction.setTransactionDate(rs.getTimestamp("transaction_date").toLocalDateTime());

                transactionList.add(transaction);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving all transactions: " + e.getMessage());
            e.printStackTrace();
        }
        return transactionList;
    }

    // Method to update the transaction status in the database (with PIN verification)
    public boolean updateTransactionStatus(String transactionID, String newStatus) {
        // First get the transaction to find the associated account
        Transaction transaction = getTransactionById(transactionID);
        if (transaction == null) {
            System.out.println("Transaction not found.");
            return false;
        }

        // Verify PIN before allowing update
        

        String query = "UPDATE transactions SET status = ? WHERE transaction_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, newStatus);
            ps.setString(2, transactionID);
            int result = ps.executeUpdate();

            if (result > 0) {
                System.out.println("Transaction status updated successfully!");
                return true;
            } else {
                System.out.println("Failed to update transaction status.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("SQL Error when updating transaction: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
