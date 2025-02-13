package bankapp;

import bankaccount.*;
import bankaccount.BankAccount;
public class bankapp {
    public static void main(String[] args) {
        // Details for the new account
        String accountName = "John Doe";
        double balance = 1000.0;  // Initial balance
        String accountType = "Checking";
        String accountStatus = "Active";

        // Create the account
        BankAccount.createAccount(accountName, balance, accountType, accountStatus);

        // BankAccount.printBankDetails()

        // Print out the list of accounts to confirm
         for (BankAccount account : BankAccount.getAccountsList()) {
             System.out.println(account.getAccountNumber());
             BankAccount.printBankDetails(account.getAccountNumber());
         }

        // Deposit some money
        

    }
}
