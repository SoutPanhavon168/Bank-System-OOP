package bankaccount;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class BankAccount {

    private static HashMap<Integer, BankAccount> accounts = new HashMap<>();
    private static Random random = new Random();
    private int accountNumber;
    private String accountName;
    private double balance;
    private String accountType;
    private String accountStatus;
    private String password;
    private int pin;
    private String firstName;
    private String lastName;
    private int customerId;

    // Primary Constructor (with PIN)
    public BankAccount(String accountName, String accountType, String accountStatus, int pin) {
        validateName(accountName);
        validateType(accountType);
        this.accountNumber = generateAccountNumber();
        this.accountName = accountName;
        this.accountType = accountType;
        this.accountStatus = accountStatus;
        this.balance = 0.0;
        setPin(pin);
        accounts.put(this.accountNumber, this);
    }

    public BankAccount(int customerId, String firstName, String lastName, String accountType, String accountStatus, int pin) {
        this.firstName = firstName;
        this.customerId = customerId;
        this.lastName = lastName;
        this.accountType = accountType;
        this.accountStatus = accountStatus;
        this.balance = 0.0;
        setPin(pin);
    }
// Constructor with first and last name (with PIN)
    public BankAccount(String firstName, String lastName, String accountType, String accountStatus, int pin) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.accountType = accountType;
        this.accountStatus = accountStatus;
        this.balance = 0.0;
        setPin(pin);
    }

// Chained Constructor (Defaults Account Type, with PIN)
    public BankAccount(String accountName, String accountStatus, int pin) {
        this(accountName, "Saving", accountStatus, pin);
    }

// Default Constructor (Defaults Everything, with PIN)
    public BankAccount() {
        this("Default Name", "Saving", "Active", 1234); // Default values including a default PIN
    }

    protected void setPassword(String password) {
        if (password == null || password.length() < 6) {
            throw new BankAccountException("Password must be at least 6 characters long.");
        }
        this.password = password;
    }

    protected void setPin(int pin) {
        // Check if the PIN is negative
        if (pin < 0) {
            throw new BankAccountException("PIN cannot be negative.");
        }
    
        // Check if the PIN is exactly a 4-digit number
        if (String.valueOf(pin).length() != 4) {
            throw new BankAccountException("PIN must be a 4-digit number.");
        }
    
        this.pin = pin;  // Set the PIN if valid
    }
    

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setBalance(double balance) {
        if (balance < 0) {
            throw new BankAccountException("Balance cannot be negative.");
        }
        this.balance = balance;
    }

    public String getAccountName() {
        return lastName + " " + firstName;
    }

    public void setAccountName(String accountName) {
        validateName(accountName);
        this.accountName = accountName;
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountType() {
        return accountType;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public int getPin() {
        return pin;
    }

    public void setAccountType(String accountType) {
        validateType(accountType);
        this.accountType = accountType;
    }

    public static BankAccount getAccountByNumber(int accountNumber) {
        BankAccount account = accounts.get(accountNumber);
        if (account == null) {
            throw new BankAccountException("Account with number " + accountNumber + " does not exist.");
        }
        return account;
    }

    public static int generateAccountNumber() {
    return Math.abs(UUID.randomUUID().hashCode()); // Generates a large unique number
}


    public static HashMap<Integer, BankAccount> getAccountsList() {
        return accounts;
    }

    private static void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new BankAccountException("Account name cannot be empty.");
        }
    }

    private static void validateType(String type) {
        if (type == null || type.trim().isEmpty()) {
            throw new BankAccountException("Account type cannot be empty.");
        }
    }


    @Override
    public String toString() {
        return '\n' + "Account Number: " + accountNumber + '\n'
                + "First name: " + firstName + '\n' 
                + "Last name: " + lastName + '\n' 
                + "Balance: " + balance + '\n'
                + "Account Type: " + accountType + '\n'
                + "Account Status: " + accountStatus + '\n'
                + "========================================" + '\n';
    }
}
