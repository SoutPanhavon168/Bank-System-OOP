package bankapp;

import java.util.Scanner;

import bankaccount.BankAccount;
import transaction.*;

public class bankapp {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        // Create two bank accounts
        BankAccount account1 = new BankAccount(12345, "John Doe", 1000.0, "Saving", "Active");
        BankAccount account2 = new BankAccount(67890, "Jane Smith", 500.0, "Saving", "Active");
        System.out.println("Enter transaction type: ");
        String transactionType = input.nextLine();
        Transaction transaction = new Transaction(account1, transactionType, 500.0, "Success");

        // Add accounts to the list (to simulate saving them in a bank)
        //BankAccount.getAccountsList().add(account1);
        //BankAccount.getAccountsList().add(account2);

        // Print initial details
        System.out.println("Initial Account Details:");
        System.out.println(account1);
        //System.out.println(account2);

        // Perform operations
        System.out.println("Enter deposit amount: ");
        double depositAmount = input.nextDouble();
        System.out.println("Enter withdraw amount: ");
        double withdrawAmount = input.nextDouble();
        transaction.deposit(depositAmount);  // Deposit 200 into account1
        transaction.withdraw(withdrawAmount);  // Withdraw 50 from account1
        //transaction.transfer(account2, 100);  // Transfer 100 from account1 to account2

        System.out.println("\nUpdated Account Details:");
        System.out.println(account1);  // Print updated account1
        //System.out.println(account2);  // Print updated account2
    }
}
