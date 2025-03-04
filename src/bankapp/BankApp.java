package bankapp;

import java.time.LocalDate;
import java.util.Scanner;
import transaction.TransactionManager;
import user.Customers;
import database.BankAccountDAO;
import database.TransactionDAO;

public class BankApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Initialize the customer
        Customers customer = new Customers("Doe", "John", "john@example.com", "password123", "password123", "1234567890", LocalDate.of(1990, 1, 1), "ID123456");

        // Create an instance of BankAccountDAO and TransactionDAO for database operations
        BankAccountDAO bankAccountDAO = new BankAccountDAO();
        TransactionDAO transactionDAO = new TransactionDAO();
        
        // Save customer data to the database (Customer table should exist in DB)
        customer.saveCustomerToDatabase();

        // Create an instance of TransactionManager (only once, no need to recreate in each loop)
        TransactionManager transactionManager = new TransactionManager();

        while (true) {
            System.out.println("\n===== Customer Menu =====");
            System.out.println("1. View Account");
            System.out.println("2. Update Account");
            System.out.println("3. Create Bank Account");
            System.out.println("4. Deposit");
            System.out.println("5. Withdraw");
            System.out.println("6. Transfer");
            System.out.println("7. View Transaction History");
            System.out.println("8. View Specific Transaction");
            System.out.println("9. View Total Deposit Amount");
            System.out.println("10. View Total Withdrawal Amount");
            System.out.println("11. Sort Transactions by Date");
            System.out.println("12. Sort Transactions by Amount");
            System.out.println("13. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();  // Read choice
            scanner.nextLine(); // Consume the newline left by nextInt()

            switch (choice) {
                case 1:
                    customer.viewOwnAccount();
                    break;
                case 2:
                    customer.updateOwnAccount();
                    break;
                case 3:
                    customer.createBankAccount();
                    // Add created bank account to DB
                    bankAccountDAO.saveBankAccount(customer.getBankAccounts().get(customer.getBankAccounts().size() - 1));  // Save the last created bank account
                    break;
                case 4:
                    transactionManager.deposit(customer.getBankAccounts());  // Pass customer bank accounts
                    break;
                case 5:
                    transactionManager.withdraw(customer.getBankAccounts());  // Pass customer bank accounts
                    break;
                case 6:
                    transactionManager.transfer(customer.getBankAccounts());  // Pass customer bank accounts
                    break;
                case 7:
                    transactionManager.viewTransactionHistory();
                    break;
                case 8:
                    System.out.print("Enter Transaction ID to view: ");
                    String transactionID = scanner.nextLine();
                    transactionManager.viewSpecificTransaction(transactionID);
                    break;
                case 9:
                    double totalDeposit = transactionManager.getTotalDepositAmount();
                    System.out.println("Total Deposit Amount: $" + totalDeposit);
                    break;
                case 10:
                    double totalWithdrawal = transactionManager.getTotalWithdrawalAmount();
                    System.out.println("Total Withdrawal Amount: $" + totalWithdrawal);
                    break;
                case 11:
                    transactionManager.sortTransactionsByDate();
                    break;
                case 12:
                    transactionManager.sortTransactionsByAmount();
                    break;
                case 13:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 13.");
            }
        }
    }
}
