package bankaccount;

import java.util.HashMap;
import java.util.Random;

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

    // Primary Constructor
    public BankAccount(String accountName, String accountType, String accountStatus) {
        validateName(accountName);
        validateType(accountType);
        this.accountNumber = generateAccountNumber();
        this.accountName = accountName;
        this.accountType = accountType;
        this.accountStatus = accountStatus;
        this.balance = 0.0;
        accounts.put(this.accountNumber, this);
    }

    public BankAccount(String firstName, String lastName, String accountType, String accountStatus) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.accountType = accountType;
        this.accountStatus = accountStatus;
        this.balance = 0.0; // Default balance
    }

    // Chained Constructor (Defaults Account Type)
    public BankAccount(String accountName, String accountStatus) {
        this(accountName, "Saving", accountStatus);
    }  
   


    // Default Constructor (Defaults Everything)
    public BankAccount() {
        this("","", "Saving", "Active");
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    protected void setPassword(String password) {
        if (password == null || password.length() < 6) {
            throw new BankAccountException("Password must be at least 6 characters long.");
        }
        this.password = password;
    }

    protected void setPin(int pin) {
        if (pin < 1000 || pin > 9999) {
            throw new BankAccountException("PIN must be a 4-digit number.");
        }
        this.pin = pin;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAccountStatus(){
        return accountStatus;
    }

    public void setBalance(double balance) {
        if (balance < 0) {
            throw new BankAccountException("Balance cannot be negative.");
        }
        this.balance = balance;
    }

    public String getAccountName() {
        return accountName;
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

    private static int generateAccountNumber() {
        int accountNumber;
        do {
            accountNumber = 1000000000 + random.nextInt(900000);
        } while (accounts.containsKey(accountNumber));
        return accountNumber;
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
        return  "Account Number: " + accountNumber + '\n' +
                "Account Name: " + accountName + '\n' +
                "Balance: " + balance + '\n' +
                "Account Type: " + accountType + '\n' +
                "Account Status: " + accountStatus + '\n' +
                "========================================" + '\n';
    }
}
