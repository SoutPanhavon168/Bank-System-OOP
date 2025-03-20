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

    public void updateAccountBalance(BankAccount account, Transaction transaction) {
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
    public String generateTransactionID() {
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
public ArrayList<BankAccount> getAllBankAccounts() {
    ArrayList<BankAccount> accounts = new ArrayList<>();
    String query = "SELECT * FROM bankaccounts"; // Example query
    try (Connection conn = DatabaseConnection.getConnection();
         Statement statement = conn.createStatement();
         ResultSet rs = statement.executeQuery(query)) {
        while (rs.next()) {
            int accountNumber = rs.getInt("account_number");
            double balance = rs.getDouble("balance");
            accounts.add(new BankAccount(accountNumber, balance));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return accounts;
}
    public boolean transferFunds(int fromAccountNumber, int toAccountNumber, double amount) {
        String debitQuery = "UPDATE bankaccounts SET balance = balance - ? WHERE account_number = ? AND balance >= ?";
        String creditQuery = "UPDATE bankaccounts SET balance = balance + ? WHERE account_number = ?";
        String insertTransactionQuery = "INSERT INTO transactions (transaction_id, account_number, type, amount, status, transaction_date) VALUES (?, ?, ?, ?, ?, ?)";
    
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false); // Start transaction
    
            String senderTxnID = generateTransactionID();
            String receiverTxnID = generateTransactionID();
    
            try (PreparedStatement debitStmt = conn.prepareStatement(debitQuery);
                 PreparedStatement creditStmt = conn.prepareStatement(creditQuery);
                 PreparedStatement transactionStmt = conn.prepareStatement(insertTransactionQuery)) {
    
                // Deduct funds from sender
                debitStmt.setDouble(1, amount);
                debitStmt.setInt(2, fromAccountNumber);
                debitStmt.setDouble(3, amount);
                int debitResult = debitStmt.executeUpdate();
    
                if (debitResult == 0) {
                    System.out.println("Insufficient funds for transfer.");
                    conn.rollback();
                    return false;
                }
    
                // Add funds to recipient
                creditStmt.setDouble(1, amount);
                creditStmt.setInt(2, toAccountNumber);
                int creditResult = creditStmt.executeUpdate();
    
                if (creditResult == 0) {
                    System.out.println("Recipient account not found.");
                    conn.rollback();
                    return false;
                }
    
                LocalDateTime now = LocalDateTime.now();
                Timestamp timestamp = Timestamp.valueOf(now);
    
                // Record transaction for sender
                transactionStmt.setString(1, senderTxnID);
                transactionStmt.setInt(2, fromAccountNumber);
                transactionStmt.setString(3, "TRANSFER");
                transactionStmt.setDouble(4, amount);
                transactionStmt.setString(5, "COMPLETED");
                transactionStmt.setTimestamp(6, timestamp);
                transactionStmt.executeUpdate();
    
                // Record transaction for recipient
                transactionStmt.setString(1, receiverTxnID);
                transactionStmt.setInt(2, toAccountNumber);
                transactionStmt.setString(3, "TRANSFER");
                transactionStmt.setDouble(4, amount);
                transactionStmt.setString(5, "COMPLETED");
                transactionStmt.setTimestamp(6, timestamp);
                transactionStmt.executeUpdate();
    
                conn.commit(); // Commit transaction
                System.out.println("Transfer successful!");
                return true;
            } catch (SQLException e) {
                conn.rollback(); // Rollback transaction if something goes wrong
                System.out.println("Error during transfer: " + e.getMessage());
                e.printStackTrace();
                return false;
            } finally {
                conn.setAutoCommit(true); // Reset auto-commit mode
            }
        } catch (SQLException e) {
            System.out.println("Database error during transfer: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    

    public boolean isRefunded(String transactionId) {
        String query = "SELECT COUNT(*) FROM transactions WHERE sender_account_id = " +
                       "(SELECT recipient_account_id FROM transactions WHERE transaction_id = ?) " +
                       "AND recipient_account_id = (SELECT sender_account_id FROM transactions WHERE transaction_id = ?) " +
                       "AND amount = (SELECT amount FROM transactions WHERE transaction_id = ?)";
    
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, transactionId);
            stmt.setString(2, transactionId);
            stmt.setString(3, transactionId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next() && rs.getInt(1) > 0) {
                return true; // Refund transaction exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    
    
    
}
