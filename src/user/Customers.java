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
    private int pin; // Store the PIN

    public Customers() {
    }

    public Customers(String lastName, String firstName, String email, String password, String confirmPassword,
            String phoneNumber, LocalDate birthDate, String governmentId) {
        super(lastName, firstName, email, password, confirmPassword, phoneNumber, birthDate, governmentId);
        this.bankAccounts = new ArrayList<>();
        this.pin = 1234; // Generate PIN when a new customer is created
    }

    // Method to generate a random PIN
    

    // Method to authenticate PIN
    private boolean authenticatePin(int enteredPin) throws Exception {
        if (enteredPin == 1234) {
            return true;
        } else {
            throw new Exception("Incorrect PIN.");
        }
    }

    // Method to allow customers to change their PIN
    public void changePin() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your current PIN to change it: ");
        String currentPin = scanner.nextLine();

        try {
            if (authenticatePin(Integer.parseInt(currentPin))) {
                System.out.println("Enter your new PIN: ");
                int newPin = scanner.nextInt();
                this.pin = newPin; // Update PIN
                System.out.println("PIN changed successfully!");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Update account method with PIN authentication
    public void updateOwnAccount() {
        Scanner scanner = new Scanner(System.in);

        int attempts = 3;
        
        boolean authentication = false;

        while (attempts > 0) {
            System.out.println("Enter your PIN to continue: ");
            String enteredPin = scanner.nextLine();

            try {
                if (authenticatePin(Integer.parseInt(enteredPin))) {
                    authentication = true;
                    break; // Exit the loop if PIN is correct
                }
            } catch (Exception e) {
                attempts--;
                System.out.println(e.getMessage());
                if (attempts > 0) {
                    System.out.println("You have " + attempts + " attempts remaining.");
                } else {
                    System.out.println("Too many failed attempts. Access denied.");
                    return; // Exit the method after too many failed attempts
                }
            }
        }

        if (authentication) {
            // Proceed with updating account information if authentication is successful
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
                    break;
                case 2:
                    System.out.println("Enter your phone number: ");
                    String newPhoneNumber = scanner.nextLine();
                    if (isPhoneNumberValid(newPhoneNumber)) {
                        setPhoneNumber(newPhoneNumber);
                    }
                    break;
                case 3:
                    //update
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
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
        Transaction executeTransaction = new Transaction(bankAccount);
        executeTransaction.transfer(bankAccounts);
    }
}
