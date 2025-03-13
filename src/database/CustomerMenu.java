package database;

import java.util.Scanner;
import transaction.TransactionManager;
import user.Customer;

public class CustomerMenu {

    public static void main(String[] args) {
        CustomerMenu menu = new CustomerMenu();
        menu.customerMenu();

    }

    public void customerMenu() {
        System.out.println("Welcome to the Bank");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        System.out.print("Choose an option -> ");
        Scanner input = new Scanner(System.in);
        int choice = input.nextInt();
        switch (choice) {
            case 1:
                registerMenu();
                break;

            case 2:
                transactionMenu();
                break;

            case 3:
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice");
                break;
        }
    }

    public void createBankAccount() {
        Scanner input = new Scanner(System.in);
        Customer customer = new Customer();
        customer.createBankAccount();
    }

    public void registerMenu() {
        Scanner input = new Scanner(System.in);
        Customer customer = new Customer();
        customer.register();
    }

    public void transactionMenu() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter your email or phone number: ");
        String emailOrPhone = input.nextLine(); // Example input for email or phone
        System.out.println("Enter your password: ");
        String password = input.nextLine(); // Example password

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
            System.out.println("4. Create bank account");

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
                case 4:
                    // Create bank account
                    customer.createBankAccount();
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
