package bankapp;

import bankaccount.BankAccount;
import transaction.Transaction;

public class BankApp {
    public static void main(String[] args) {
        // Create a new bank account
        BankAccount account = new BankAccount(1234567890);
        Transaction accTransaction = new Transaction();
        accTransaction.operations();
        account.printBankDetails();

    }
}
