package bankaccount;

import transaction.Transaction;

public class BankAccount {
    private int accountNumber;
    private String accountName;
    private double balance;
    private String accountType;
    private String accountStatus;
    private String password; // Added for authentication

    // Constructor to initialize account with a specific number
    public BankAccount(int accountNumber) {
        this.accountNumber = accountNumber;
        this.accountName = "Sereyreaksa";
        this.balance = 0.0; // Initialize balance to 0
        this.accountType = "Saving";
        this.accountStatus = "Active";
    }

    // Constructor with all fields
    public BankAccount(int accountNumber, String accountName, double balance, String accountType, String accountStatus) {
        this.accountNumber = accountNumber;
        this.accountName = accountName;
        this.balance = balance;
        this.accountType = accountType;
        this.accountStatus = accountStatus;
    }

    // Getters and Setters
    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }
    
    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public double getBalance() {
        return balance;
    }

    // Deposit method that updates the balance
    public void deposit(double amount) {
        Transaction transaction = new Transaction("Deposit", amount, "Completed");
        this.balance += transaction.deposit();
        
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Override toString for better readability
    @Override
    public String toString() {
        return "BankAccount{" +
                "accountNumber=" + accountNumber +
                ", accountName='" + accountName + '\'' +
                ", balance=" + balance +
                ", accountType='" + accountType + '\'' +
                ", accountStatus='" + accountStatus + '\'' +
                '}';
    }

    // Print bank details
    public void printBankDetails() {
        System.out.println(toString());
    }
}
