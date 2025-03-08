package database;

import java.util.Scanner;
import transaction.TransactionManager;
import user.Customer;

public class TransactionDAOTest {

    public static void main(String[] args) {
        // Simulate login using email or phone and password
        String emailOrPhone = "1234567890"; // Example input for email or phone
        String password = "s1234@gmail.com"; // Example password

        // Login the customer
        Customer customer = Customer.login(emailOrPhone, password);

        // Check if login was successful
        if (customer != null) {
            System.out.println("Login successful for customer: " + customer.getFullName());
            
            // Now that the customer is logged in, they can perform a transaction
            System.out.println("Select a transaction type:");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Transfer");
            
            // Assuming you want to allow the user to choose a transaction type:
            Scanner scanner = new Scanner(System.in);
            int transactionChoice = scanner.nextInt();
            
            TransactionManager transactionManager = new TransactionManager();

            switch (transactionChoice) {
                case 1:
                    // Perform deposit
                    transactionManager.deposit(customer.getBankAccounts());
                    break;
                case 2:
                    // Perform withdraw
                    transactionManager.withdraw(customer.getBankAccounts());
                    break;
                case 3:
                    // Perform transfer
                    transactionManager.transfer(customer.getBankAccounts());
                    break;
                default:
                    System.out.println("Invalid transaction type selected.");
                    break;
            }
        } else {
            System.out.println("Login failed. Please check your credentials.");
        }
    }
}
