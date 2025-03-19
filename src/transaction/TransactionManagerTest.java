package transaction;

import bankaccount.BankAccount;
import database.BankAccountDAO;
import java.util.List;

public class TransactionManagerTest {

    public static void main(String[] args) {
                // Create a TransactionManager instance
        TransactionManager transactionManager = new TransactionManager();
        
        // Test account number retrieval
        int testAccountNumber = 1764384239; // Replace with a valid account number from your database

        // Step 1: Verify if the account exists in the database
        BankAccountDAO bankAccountDAO = new BankAccountDAO();
        BankAccount testAccount = bankAccountDAO.getBankAccountById(testAccountNumber);

        if (testAccount != null) {
            System.out.println("Account retrieved successfully:");
            System.out.println("Account Number: " + testAccount.getAccountNumber());
            System.out.println("Account Holder: " + testAccount.getFirstName() + " " + testAccount.getLastName());
            System.out.println("Account Balance: " + testAccount.getBalance());
        } else {
            System.out.println("No account found with account number: " + testAccountNumber);
            return;
        }

        // Step 2: Fetch and display transaction history for the account
        List<Transaction> transactions = transactionManager.viewCurrentUserTransactionHistory(testAccountNumber);
        
if (transactions.isEmpty()) {
            System.out.println("No transactions found for account number: " + testAccountNumber);
        } else {
            System.out.println("Transaction history for account number: " + testAccountNumber);
            for (Transaction transaction : transactions) {
                System.out.println(transaction);
            }
        }        
    }
    }

