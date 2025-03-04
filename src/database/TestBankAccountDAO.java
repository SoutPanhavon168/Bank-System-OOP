package database;
import bankaccount.BankAccount;
import java.util.ArrayList;

public class TestBankAccountDAO {

    public static void main(String[] args) {
        // Create an instance of BankAccountDAO
        BankAccountDAO bankAccountDAO = new BankAccountDAO();

        // Create a new BankAccount object
        BankAccount account = new BankAccount("John Doe", "Saving", "Active");

        // Save the bank account to the database
        bankAccountDAO.saveBankAccount(account);

        // Retrieve the account by its account number
        BankAccount retrievedAccount = bankAccountDAO.getBankAccountById(account.getAccountNumber());
        if (retrievedAccount != null) {
            System.out.println("Account retrieved successfully:");
            System.out.println(retrievedAccount.toString());
        } else {
            System.out.println("Account not found.");
        }

        // Retrieve all accounts
        ArrayList<BankAccount> allAccounts = bankAccountDAO.getAllBankAccounts();
        System.out.println("\nAll Accounts:");
        for (BankAccount bankAccount : allAccounts) {
            System.out.println(bankAccount.toString());
        }
    }
}
