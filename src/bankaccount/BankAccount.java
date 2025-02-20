package bankaccount;

import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

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

    // Constructor with all fields
    public BankAccount(String accountName, String accountStatus) {
        this.accountNumber = generateAccountNumber();
        this.accountName = accountName;
        this.balance = 0.0;
        this.accountStatus = accountStatus;
        accounts.put(this.accountNumber, this);
    }
    public BankAccount(String accountName, String accountType, String accountStatus) {
        this.accountNumber = generateAccountNumber();
        this.accountName = accountName;
        this.balance = 0.0;
        this.accountType = accountType;
        this.accountStatus = accountStatus;
        accounts.put(this.accountNumber, this);
    }

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
        this.password = password;
    }

    protected void setPin(int pin) {
        this.pin = pin;
    }

    public void setBalance(double balance) {
        this.balance = balance;
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

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public static BankAccount getAccountByNumber(int accountNumber) {
        return accounts.get(accountNumber);
    }

    // Generate a unique account number
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

    // Check if an account number already exists
    private static boolean accountExists(int accountNumber) {
        return accounts.containsKey(accountNumber);
    }

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

    public void printBankDetails() {
        System.out.println("Account details: ");
        System.out.println(toString());
    }

    private void changePIN() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter old PIN: ");
        int oldPin = input.nextInt();
        if (oldPin == 123456) {
            System.out.println("Enter new PIN: ");
            int newPin = input.nextInt();
            setPin(newPin);
        }
        input.close();
    }
}
