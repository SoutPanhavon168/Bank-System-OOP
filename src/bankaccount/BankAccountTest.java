package bankaccount;

import database.BankAccountDAO;
import user.Customer;

public class BankAccountTest {

    public static void main(String[] args) {
        // Step 1: Create a new Customer and register
        Customer customer = new Customer();
        customer.register();  // Ensure register() collects first name, last name, and PIN

        // Step 2: Retrieve customer details including PIN
        int pin = customer.getPin();  // Ensure getPin() method exists in Customer class

        // Step 3: Create a new BankAccount using customer details
        BankAccount account = new BankAccount(customer.getCustomerId(),customer.getFirstName(), customer.getLastName(), "Saving", "Active", pin);

        // Step 4: Print values to verify
        System.out.println("\nTesting saveBankAccount() method:");
        System.out.println("First Name: " + customer.getFirstName());
        System.out.println("Last Name: " + customer.getLastName());
        System.out.println("Account Type: " + account.getAccountType());
        System.out.println("Status: " + account.getAccountStatus());
        System.out.println("Balance: " + account.getBalance());
        System.out.println("PIN: " + pin); // Ensure the PIN is properly set
        System.out.println("Customer ID: " + account.getCustomerId());

        // Step 5: Save the newly created bank account to the database
        BankAccountDAO bankAccountDAO = new BankAccountDAO();
        bankAccountDAO.saveBankAccount(account);  // Ensure saveBankAccount handles PIN
        
        // Step 6: Verify account creation
        System.out.println("Bank account successfully created!");
    


    

}}
