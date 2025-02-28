package bankAccount;

import java.util.HashMap;
import java.util.NoSuchElementException;
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

    // Constructor with account name and status
    public BankAccount(String accountName, String accountStatus) {
        if (accountName == null || accountName.trim().isEmpty()) {
            throw new IllegalArgumentException("Account name cannot be empty.");
        }
        this.accountNumber = generateAccountNumber();
        this.accountName = accountName;
        this.balance = 0.0;
        this.accountStatus = accountStatus;
        accounts.put(this.accountNumber, this);
    }

    // Constructor with account name, type, and status
    public BankAccount(String accountName, String accountType, String accountStatus) {
        if (accountName == null || accountName.trim().isEmpty()) {
            throw new IllegalArgumentException("Account name cannot be empty.");
        }
        if (accountType == null || accountType.trim().isEmpty()) {
            throw new IllegalArgumentException("Account type cannot be empty.");
        }
        this.accountNumber = generateAccountNumber();
        this.accountName = accountName;
        this.balance = 0.0;
        this.accountType = accountType;
        this.accountStatus = accountStatus;
        accounts.put(this.accountNumber, this);
    }

    // Default constructor
    public BankAccount() {
        this.accountNumber = generateAccountNumber();
        this.accountName = "Default Name";
        this.balance = 0.0;
        this.accountType = "Saving";
        this.accountStatus = "Active";
        accounts.put(this.accountNumber, this);
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    private void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    protected void setPassword(String password) {
        if (password == null || password.length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters long.");
        }
        this.password = password;
    }

    protected void setPin(int pin) {
        if (pin < 1000 || pin > 9999) {
            throw new IllegalArgumentException("PIN must be a 4-digit number.");
        }
        this.pin = pin;
    }

    public void setBalance(double balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative.");
        }
        this.balance = balance;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        if (accountName == null || accountName.trim().isEmpty()) {
            throw new IllegalArgumentException("Account name cannot be empty.");
        }
        this.accountName = accountName;
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        if (accountType == null || accountType.trim().isEmpty()) {
            throw new IllegalArgumentException("Account type cannot be empty.");
        }
        this.accountType = accountType;
    }

    public static BankAccount getAccountByNumber(int accountNumber) {
        BankAccount account = accounts.get(accountNumber);
        if (account == null) {
            throw new NoSuchElementException("Account with number " + accountNumber + " does not exist.");
        }
        return account;
    }

    private static int generateAccountNumber() {
        int accountNumber;
        do {
            accountNumber = 1000000000 + random.nextInt(900000);
        } while (accountExists(accountNumber));
        return accountNumber;
    }

    public static HashMap<Integer, BankAccount> getAccountsList() {
        return accounts;
    }

    private static boolean accountExists(int accountNumber) {
        return accounts.containsKey(accountNumber);
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
