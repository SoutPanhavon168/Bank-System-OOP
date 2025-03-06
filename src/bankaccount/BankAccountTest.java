package bankaccount;

import database.BankAccountDAO;
import user.Customer;

public class BankAccountTest {
    public static void main(String[] args) {
        // Step 1: Create a new Customer (You can use the register() method to simulate customer registration)
        Customer customer = new Customer();
        customer.register();  // Make sure the register method works properly before calling save

        // Step 2: Create a new BankAccount and set values
        BankAccount account = new BankAccount(customer.getFirstName(), customer.getLastName(), "Saving", "Active");

        // Step 3: Print values to check if they are correct
        System.out.println("Testing saveBankAccount() method:");
        System.out.println("First Name: " + customer.getFirstName());
        System.out.println("Last Name: " + customer.getLastName());
        System.out.println("Account Type: " + account.getAccountType());
        System.out.println("Status: " + account.getAccountStatus());
        System.out.println("Balance: " + account.getBalance());

        // Step 4: Create BankAccountDAO and call the saveBankAccount method
        BankAccountDAO bankAccountDAO = new BankAccountDAO();
        bankAccountDAO.saveBankAccount(account);  // Save the newly created bank account

        // Step 5: Check if the account was inserted into the database
        System.out.println("Last created bank account: " + account.getAccountType());
    }
}
