package user;

import bankAccount.BankAccount;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import transaction.Transaction;

public class Customers extends User {

    private String fullName;
    private BankAccount bankAccount;
    private ArrayList<BankAccount> bankAccounts;

    public Customers() {
    }

    public Customers(String lastName, String firstName, String email, String password, String confirmPassword,
            String phoneNumber, LocalDate birthDate, String governmentId) {
        super(lastName, firstName, email, password, confirmPassword, phoneNumber, birthDate, governmentId);
        this.bankAccounts = new ArrayList<>();
    }

    public void updateOwnAccount() {
        Scanner scanner = new Scanner(System.in);

        int attempts = 3;
        boolean authentication = false;

        while (attempts > 0) {
            System.out.println("Enter your current password to contine: ");
            String currentPassword = scanner.nextLine();

            //check if the current entered password matches the one in the user file 
            // set authentication as true if it does 
            //else decrement attempts by 1 each times til 3 attempts run out then return; 
            System.out.println("\nUpdate Account Information: ");
            System.out.println("1. Update Email");
            System.out.println("2. Update Phone Number");
            System.out.println("3. Update Password");
            System.out.println("Please choose an option (1-3): ");

            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    System.out.println("Enter your email: ");
                    String newEmail = scanner.nextLine();
                    if (isEmailValid(newEmail)) {
                        setEmail(newEmail);
                    }
                case 2:
                    System.out.println("Enter your phone number: ");
                    String newPhoneNumber = scanner.nextLine();
                    if (isPhoneNumberValid(newPhoneNumber)) {
                        setPhoneNumber(newPhoneNumber);
                    }
                case 3:
                //update password
                default:
                    System.out.println("Invalid choice");
                    System.out.println("Please enter option (1-3).");
            }
        }
    }

    public String getFullName() {
        return this.fullName = getLastName() + " " + getFirstName();
    }

    public void viewOwnAccount() {
        System.out.println("===== Account Details =====");
        System.out.println("User ID: " + getUserId());
        System.out.println("Name: " + getFirstName() + " " + getLastName());
        System.out.println("Email: " + getEmail());
        System.out.println("Phone Number: " + getPhoneNumber());
        System.out.println("Birth Date: " + getBirthDate());
        System.out.println("Government ID: " + getMaskedGovernmentId());
        System.out.println("Number of Bank Accounts: " + bankAccounts.size());
        System.out.println("\n===== Bank Accounts =====");
        if (bankAccounts.isEmpty()) {
            System.out.println("No bank accounts found.");
        } else {
            for (BankAccount account : bankAccounts) {
                System.out.println(account.toString());
            }
        }
    }

    public void createBankAccount() {
        Scanner input = new Scanner(System.in);
        System.out.println("Select an account type");
        System.out.println("1. Saving");
        System.out.println("2. Current");
        System.out.println("3. Checking");
        System.out.println("Select an option-> ");
        int options = input.nextInt();
        switch (options) {
            case 1:
<<<<<<< HEAD
            bankAccount.setAccountType("Saving");
            break;
            case 2:
            bankAccount.setAccountType("Saving");
            break;
            case 3:
            bankAccount.setAccountType("Saving");
            break;

        }
        bankAccount = new BankAccount(getFullName(),"Active");
        
=======
                bankAccount = new BankAccount(getFullName(), "Saving", "Active");
                break;
            case 2:
                bankAccount = new BankAccount(getFullName(), "Current", "Active");
                break;
            case 3:
                bankAccount = new BankAccount(getFullName(), "Checking", "Active");
                break;

        }
        if (bankAccount != null) {
            bankAccounts.add(bankAccount); // Add the new bank account to the list
        }
>>>>>>> origin/main

    }

    public void deposit() {
        Transaction executeTransaction = new Transaction(bankAccount);
        executeTransaction.deposit(bankAccounts);

    }

    public void withdraw() {
        Transaction executeTransaction = new Transaction(bankAccount);
        executeTransaction.withdraw(bankAccounts);

    }

    public void transfer() {
        System.out.println("Enter bank account to transfer: ");
        System.out.println("Enter amount to deposit ($):");
        double amount = input.nextFloat();
        Transaction executeTransaction = new Transaction(bankAccount);
<<<<<<< HEAD
        executeTransaction.transfer(bankAccount, amount);
    }


    
    
}
=======
        executeTransaction.transfer(bankAccounts);
    }

}
>>>>>>> origin/main
