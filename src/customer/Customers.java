package customer;

import user.User;
import bankaccount.BankAccount;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Customers extends User {
    private ArrayList<BankAccount> bankAccounts;

   public Customers(String lastName, String firstName, String email, String password, String confirmPassword,
                     String phoneNumber, LocalDate birthDate, String governmentId) {
        super(lastName, firstName, email, password, confirmPassword, phoneNumber, birthDate, governmentId);
        this.bankAccounts = new ArrayList<>();
    }

    public void updateOwnAccount(){
        Scanner scanner = new Scanner (System.in);
        
        int attempts = 3;
        boolean authentication = false;

        while (attempts > 0){
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

        switch(choice){
            case 1: 
            System.out.println("Enter your email: ");
            String newEmail = scanner.nextLine();
            if(isEmailValid(newEmail)){
                setEmail(newEmail);
            }
            case 2:
            System.out.println("Enter your phone number: ");
            String newPhoneNumber = scanner.nextLine();
            if(isPhoneNumberValid(newPhoneNumber)){
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
                System.out.println("Account Number: " + account.getAccountNumber());
                System.out.println("Account Type: " + account.getAccountType());
                System.out.println("Balance: $" + account.getBalance());
                System.out.println("-----------------------");
            }
        }
    }

    public void createbankAccount(){

    }

    public void deposit(){

    }

    public void withdraw(){

    }
    
    public void requestLoan(){

    }
    
    
}