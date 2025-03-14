package database;

import java.util.Scanner;
import transaction.TransactionManager;
import user.Customer;

public class CustomerMenu {

    public static void main(String[] args) {
        CustomerMenu menu = new CustomerMenu();
        menu.customerMenu();
    }

    // Main menu for the customer
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
                Menu();
                break;

            case 3:
                System.exit(0);
                break;

            default:
                System.out.println("Invalid choice");
                break;
        }
    }

    // Menu for updating account info
    public void updateAccount(Customer currentCustomer) {
        if (currentCustomer != null) {
            // Call the updateOwnAccount method on the current user
            currentCustomer.updateOwnAccount();
        } else {
            System.out.println("No customer found to update.");
        }
    }

    // Creating a bank account for the customer
    public void createBankAccount() {
        Scanner input = new Scanner(System.in);
        Customer customer = new Customer();
        customer.createBankAccount();
    }

    // Registration menu for the customer
    public void registerMenu() {
        Scanner input = new Scanner(System.in);
        Customer customer = new Customer();
        customer.register();
    }

    // Login process for the customer
    public void Menu() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter your email or phone number: ");
        String emailOrPhone = input.nextLine(); // User enters email or phone number
        System.out.println("Enter your password: ");
        String password = input.nextLine(); // User enters password

        // Attempt to log in the customer
        Customer customer = Customer.login(emailOrPhone, password);
        if (customer != null) {
            System.out.println("Logged in customer ID: " + customer.getCustomerId());
            System.out.println("Login successful for customer: " + customer.getFullName());

            // Display menu for logged-in customer
            System.out.println("1. Update Account Information");
            System.out.println("2. Transactions");
            System.out.println("3. Exit");
            System.out.print("Choose an option -> ");
            int choice2 = input.nextInt();

            switch (choice2) {
                case 1:
                    // Call updateAccount with the logged-in customer
                    updateAccount(customer);
                    break;

                case 2:
                    // Handle transactions (deposit, withdraw, transfer, etc.)
                    handleTransactions(customer);
                    break;

                case 3:
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid option.");
                    break;
            }
        } else {
            System.out.println("Login failed. Please check your credentials.");
        }
    }

    // Handle different transactions
    public void handleTransactions(Customer customer) {
        Scanner input = new Scanner(System.in);
        System.out.println("Select a transaction type:");
        System.out.println("1. Deposit");
        System.out.println("2. Withdraw");
        System.out.println("3. Transfer");
        System.out.println("4. Create Bank Account");

        int transactionChoice = input.nextInt();

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
    }
}
