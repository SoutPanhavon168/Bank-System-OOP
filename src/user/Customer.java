package user;

import bankaccount.BankAccount;
import database.CustomerDAO;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;
import transaction.TransactionManager;

public class Customer extends User {

    private int customerId;
    private BankAccount bankAccount;
    private ArrayList<BankAccount> bankAccounts;
    private int pin; // Store the PIN

    public Customer() {
        // Call the super constructor with default values for the User class
        super("", "", "", "", "", "", LocalDate.now(), "");
        this.bankAccounts = new ArrayList<>();
        this.pin = 1234; // Default PIN
        this.customerId = getUserId(); // Generate a unique user ID
    }



    public Customer(String lastName, String firstName, String email, String password, String confirmPassword,
            String phoneNumber, LocalDate birthDate, String governmentId) {
        super(lastName, firstName, email, password, confirmPassword, phoneNumber, birthDate, governmentId);
        this.bankAccounts = new ArrayList<>();
        this.pin = 1234; // Generate PIN when a new customer is created
        this.customerId = getUserId();
    }
    @Override
public String getFirstName() {
    return this.firstName; // Use this instead of super.getFirstName()
}

@Override
public String getLastName() {
    return this.lastName; // Use this instead of super.getLastName()
}

    

    private boolean isInputInvalid(String input){
        return input.matches(".*\\d.*");
    }

    // Method to register a new customer
    public void register() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Enter your last name: ");
            this.lastName = scanner.nextLine().trim();
            if (lastName.isEmpty()) {
                throw new CustomerException.EmptyFieldException("Last name");
            }
            if (isInputInvalid(lastName)){
                throw new CustomerException.InvalidInputException("Last name");
            }

            System.out.println("Enter your first name: ");
            this.firstName = scanner.nextLine().trim();
            if (firstName.isEmpty()) {
                throw new CustomerException.EmptyFieldException("First name");
            }
            
            if (isInputInvalid(firstName)){
                throw new CustomerException.InvalidInputException("First name");
            }
    
            System.out.println("Enter your email: ");
            String email = scanner.nextLine().trim();
            if (!isEmailValid(email)) {
                throw new CustomerException.InvalidEmailException();
            }
    
            System.out.println("Enter your password: ");
            String password = scanner.nextLine();
    
            System.out.println("Confirm your password: ");
            String confirmPassword = scanner.nextLine();
            if (!password.equals(confirmPassword)) {
                throw new CustomerException.PasswordMismatchException();
            }
    
            System.out.println("Enter your phone number: ");
            String phoneNumber = scanner.nextLine().trim();
            if (!isPhoneNumberValid(phoneNumber)) {
                throw new CustomerException.InvalidPhoneNumberException();
            }
    
            System.out.println("Enter your birth date (YYYY-MM-DD): ");
            LocalDate birthDate;
            try {
                birthDate = LocalDate.parse(scanner.nextLine().trim());
                if (LocalDate.now().minusYears(16).isBefore(birthDate)) {
                    throw new CustomerException.UnderageException();
                }
            } catch (DateTimeParseException e) {
                throw new CustomerException.InvalidBirthDateException();
            }
    
            System.out.println("Enter your government ID: ");
            String governmentId = scanner.nextLine().trim();
            if (governmentId.isEmpty()) {
                throw new CustomerException.EmptyFieldException("Government ID");
            }
    
            // Create new customer
            Customer customer = new Customer(lastName, firstName, email, password, confirmPassword, phoneNumber, birthDate, governmentId);
    
            // Save customer to the database using CustomerDAO
            CustomerDAO customerDAO = new CustomerDAO();
            customerDAO.saveCustomer(customer); // Store customer in DB
    
        } catch (CustomerException e) {
            System.out.println("Registration failed: " + e.getMessage());
        }
    }
    

    public String getFullName(){
        return getFirstName() + " " + getLastName();
    }
    

    // Method to authenticate PIN
    private boolean authenticatePin(int enteredPin) throws Exception {
        if (enteredPin == this.pin) {
            return true;
        } else {
            throw new Exception("Incorrect PIN.");
        }
    }

    public void viewOwnAccount() {
        // Display the personal information of the customer
        System.out.println("===== Account Details =====");
        System.out.println("User ID: " + getUserId());
        System.out.println("Name: " + getFirstName() + " " + getLastName());
        System.out.println("Email: " + getEmail());
        System.out.println("Phone Number: " + getPhoneNumber());
        System.out.println("Birth Date: " + getBirthDate());
        System.out.println("Government ID: " + getMaskedGovernmentId());
    
        // Display the bank account details
        System.out.println("\n===== Bank Accounts =====");
        if (bankAccounts.isEmpty()) {
            System.out.println("No bank accounts found.");
        } else {
            for (BankAccount account : bankAccounts) {
                System.out.println(account.toString()); // Assuming `BankAccount` class has a `toString()` method
            }
        }
    }

    public void updateOwnAccount() {
        Scanner scanner = new Scanner(System.in);
    
        int attempts = 3;
        boolean authenticated = false;
    
        // Authenticate using PIN before allowing any updates
        while (attempts > 0) {
            System.out.println("Enter your PIN to proceed: ");
            String enteredPin = scanner.nextLine();
    
            try {
                if (authenticatePin(Integer.parseInt(enteredPin))) {
                    authenticated = true;
                    break; // Exit the loop if the PIN is correct
                }
            } catch (Exception e) {
                attempts--;
                System.out.println(e.getMessage());
                if (attempts > 0) {
                    System.out.println("You have " + attempts + " attempts remaining.");
                } else {
                    System.out.println("Too many failed attempts. Access denied.");
                    return; // Exit if too many failed attempts
                }
            }
        }
    
        if (authenticated) {
            // Prompt the user for which information they want to update
            System.out.println("\nUpdate Account Information: ");
            System.out.println("1. Update Email");
            System.out.println("2. Update Phone Number");
            System.out.println("3. Update Password");
            System.out.println("Please choose an option (1-3): ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline
    
            switch (choice) {
                case 1:
                    System.out.println("Enter your new email: ");
                    String newEmail = scanner.nextLine();
                    if (isEmailValid(newEmail)) {
                        setEmail(newEmail); // Update email in the Customer object
                        System.out.println("Email updated successfully!");
                    } else {
                        System.out.println("Invalid email format.");
                    }
                    break;
                case 2:
                    System.out.println("Enter your new phone number: ");
                    String newPhoneNumber = scanner.nextLine();
                    if (isPhoneNumberValid(newPhoneNumber)) {
                        setPhoneNumber(newPhoneNumber); // Update phone number in the Customer object
                        System.out.println("Phone number updated successfully!");
                    } else {
                        System.out.println("Invalid phone number format.");
                    }
                    break;
                case 3:
                    System.out.println("Enter your new password: ");
                    String newPassword = scanner.nextLine();
                    setPassword(newPassword); // Update password in the Customer object
                    System.out.println("Password updated successfully!");
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        }
    
        scanner.close();
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
        scanner.close();
    }

    // Update Account Information (instead of forgotPassword)
    public void updateAccountInformation() {
        Scanner scanner = new Scanner(System.in);

        int attempts = 3;
        boolean authentication = false;

        // Authenticate PIN first
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
            scanner.nextLine(); // Consume the newline
            switch (choice) {
                case 1:
                    System.out.println("Enter your new email: ");
                    String newEmail = scanner.nextLine();
                    if (isEmailValid(newEmail)) {
                        setEmail(newEmail);
                    }
                    break;
                case 2:
                    System.out.println("Enter your new phone number: ");
                    String newPhoneNumber = scanner.nextLine();
                    if (isPhoneNumberValid(newPhoneNumber)) {
                        setPhoneNumber(newPhoneNumber);
                    }
                    break;
                case 3:
                    // Update password logic here
                    System.out.println("Enter your new password: ");
                    String newPassword = scanner.nextLine();
                    setPassword(newPassword);
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }

        scanner.close();
    }

    // Method to view account details and associated bank accounts
    public void viewBankAccountDetails() {
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

    public boolean login() {
        // Implement login logic
        return true;
    }

    public void setPassword(String password) {
        // You can add password validation here (e.g., minimum length, special characters, etc.)
        this.password = password;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public void updatePassword() {
        Scanner scanner = new Scanner(System.in);
    
        System.out.println("Enter your current password: ");
        String currentPassword = scanner.nextLine();
    
        // Validate the current password
        if (currentPassword.equals(getPassword())) {
            System.out.println("Enter your new password: ");
            String newPassword = scanner.nextLine();
            System.out.println("Confirm your new password: ");
            String confirmPassword = scanner.nextLine();
    
            if (newPassword.equals(confirmPassword)) {
                // Update the password in the Customer object
                setPassword(newPassword);
    
                // Assuming CustomerDAO has a method to update the password in the database
                CustomerDAO customerDAO = new CustomerDAO();
                customerDAO.updatePasswordInDatabase(getUserId(), newPassword);
    
                System.out.println("Password updated successfully!");
            } else {
                System.out.println("Passwords do not match. Please try again.");
            }
        } else {
            System.out.println("Incorrect current password.");
        }
    
        scanner.close();
    }
    

    // Create a new bank account
    public void createBankAccount() {
        Scanner input = new Scanner(System.in); // DO NOT CLOSE THIS SCANNER
        System.out.println("Select an account type");
        System.out.println("1. Saving");
        System.out.println("2. Current");
        System.out.println("3. Checking");
        System.out.print("Select an option-> ");
        int options = input.nextInt();
        input.nextLine(); // Consume the newline
    
        switch (options) {
            case 1:
                bankAccount = new BankAccount(getFirstName(), getLastName(), "Saving", "Active");
                break;
            case 2:
                bankAccount = new BankAccount(getFirstName(), getLastName(), "Current", "Active");
                break;
            case 3:
                bankAccount = new BankAccount(getFirstName(), getLastName(), "Checking", "Active");
                break;
            default:
                System.out.println("Invalid option. Try again.");
                return; // Exit the method without adding an account
        }
    
        if (bankAccount != null) {
            bankAccounts.add(bankAccount); // Add the new bank account to the list
            System.out.println("Bank account created successfully!");
        }
    }

    // Methods to handle deposits, withdrawals, and transfers
    public void deposit() {
        // Use the TransactionManager to handle deposit logic
        TransactionManager transactionManager = new TransactionManager();
        transactionManager.deposit(bankAccounts);
    }

    public void withdraw() {
        // Use the TransactionManager to handle withdraw logic
        TransactionManager transactionManager = new TransactionManager();
        transactionManager.withdraw(bankAccounts);
    }

    public void transfer() {
        // Use the TransactionManager to handle transfer logic
        TransactionManager transactionManager = new TransactionManager();
        transactionManager.transfer(bankAccounts);
    }

    public ArrayList<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

    public void setUserId(int int1) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setUserId'");
    }
}
