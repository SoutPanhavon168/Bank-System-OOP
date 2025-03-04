package database;

import transaction.Transaction;
import bankaccount.BankAccount;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    // Method to save a transaction to the database
    public void saveTransaction(Transaction transaction) {
        String query = "INSERT INTO transactions (transaction_id, account_number, type, amount, status, transaction_date) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, transaction.getTransactionID()); // Set the transaction ID from the Transaction object

            // Get account number from the bank account linked to the transaction
            BankAccount account = transaction.getBankAccount();
            if (account != null) {
                ps.setInt(2, account.getAccountNumber()); // Set the account number
            } else {
                ps.setInt(2, 0);  // Or handle null account differently
            }

            // Set the transaction type
            ps.setString(3, transaction.getType());

            // Set the transaction amount
            ps.setDouble(4, transaction.getAmount());

            // Set the transaction status
            ps.setString(5, transaction.getStatus());

            // Set the transaction date as a Timestamp
            LocalDateTime transactionDate = transaction.getTransactionDate(); // Assuming LocalDateTime
            if (transactionDate != null) {
                ps.setTimestamp(6, Timestamp.valueOf(transactionDate)); // Convert LocalDateTime to Timestamp
            } else {
                ps.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now())); // Use current time if null
            }

            // Execute the insert query
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to retrieve a transaction by its ID
    public Transaction getTransactionById(String transactionID) {
        String query = "SELECT * FROM transactions WHERE transaction_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, transactionID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                BankAccountDAO bankAccountDAO = new BankAccountDAO();
                int accountNumber = rs.getInt("account_number");
                BankAccount account = bankAccountDAO.getBankAccountById(accountNumber);

                // Create the transaction object
                Transaction transaction = new Transaction(account, 
                        rs.getString("type"), 
                        rs.getDouble("amount"), 
                        rs.getString("status"));
                transaction.setTransactionID(transactionID);  // Set the ID after retrieval from DB
                transaction.setTransactionDate(rs.getTimestamp("transaction_date").toLocalDateTime());  // Set date from DB

                return transaction;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method to retrieve all transactions for a specific account
    public List<Transaction> getTransactionsByAccount(int accountNumber) {
        List<Transaction> transactionList = new ArrayList<>();
        String query = "SELECT * FROM transactions WHERE account_number = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, accountNumber);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                BankAccountDAO bankAccountDAO = new BankAccountDAO();
                BankAccount account = bankAccountDAO.getBankAccountById(accountNumber);

                // Create and populate the transaction object
                Transaction transaction = new Transaction(account,
                        rs.getString("type"),
                        rs.getDouble("amount"),
                        rs.getString("status"));
                transaction.setTransactionID(rs.getString("transaction_id"));
                transaction.setTransactionDate(rs.getTimestamp("transaction_date").toLocalDateTime());

                transactionList.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactionList;
    }

    // Method to retrieve all transactions
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

                // Create and populate the transaction object
                Transaction transaction = new Transaction(account,
                        rs.getString("type"),
                        rs.getDouble("amount"),
                        rs.getString("status"));
                transaction.setTransactionID(rs.getString("transaction_id"));
                transaction.setTransactionDate(rs.getTimestamp("transaction_date").toLocalDateTime());

                transactionList.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactionList;
    }
}
