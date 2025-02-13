package bankaccount;

import Interfaces.Authentication;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class BankAccount implements Authentication {

    private static ArrayList<BankAccount> accounts = new ArrayList<>();
    private static Random random = new Random();
    private int accountNumber;
    private String accountName;
    private double balance;
    private String accountType;
    private String accountStatus;
    private String password; // Added for authentication
    private int pin;

    // Constructor with all fields
    public BankAccount(int accountNumber, String accountName, double balance, String accountType, String accountStatus) {
        this.accountNumber = accountNumber;
        this.accountName = accountName;
        this.balance = balance;
        this.accountType = accountType;
        this.accountStatus = accountStatus;
    }
    // to be used later
    public void createAccount(int accountNumber, String accountName, double balance, String accountType, String accountStatus) {
        this.accountNumber = accountNumber;
        this.accountName = accountName;
        this.balance = balance;
        this.accountType = accountType;
        this.accountStatus = accountStatus;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    protected void setPassword(String password) {
        this.password = password;
    }

    protected void setPin(int pin) {
        this.pin = pin;
    }

    protected void setBalance(double balance){
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

    public static void createAccount(String accountName, double balance, String accountType, String accountStatus) {
        int newAccountNumber = generateAccountNumber();
        BankAccount newAccount = new BankAccount(newAccountNumber, accountName, balance, accountType, accountStatus);
        accounts.add(newAccount);
    }

    public BankAccount getAccountByNumber(int accountNumber){
        for (BankAccount account : accounts) {
            if (account.getAccountNumber() == accountNumber) {
                return account;
            }
        }
        return null; // Return null if account is not found
    }

    // Generate a unique account number
    private static int generateAccountNumber() {
        int accountNumber;
        do {
            accountNumber = 1000000000 + random.nextInt(900000); // Generate 9-digit number
        } while (accountExists(accountNumber));
        return accountNumber;
    }
    
    public static ArrayList<BankAccount> getAccountsList() {
        return accounts;
    }
    
    // Check if an account number already exists
    private static boolean accountExists(int accountNumber) {
        for (BankAccount account : accounts) {
            if (account.getAccountNumber() == accountNumber) {
                return true;
            }
        }
        return false;
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
    public static void printBankDetails(int accountNumber) {
        for (BankAccount account : accounts) {
            if (account.getAccountNumber() == accountNumber) {
                System.out.println(account);
                return;
            }
        }
        System.out.println("Account not found.");
    }

    @Override
    public boolean login() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter account number");
        int accountNumber = input.nextInt();
        System.out.println("Enter password: ");
        String password = input.nextLine();
    
        // for testing purposes
        if(password.equals("12345") && accountNumber == 123456789){
            input.close();
            return true;
        }
        input.close();
        return false;
    }

    @Override
    public void register() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter account name: ");
        String accountName = input.nextLine();
        System.out.println("Enter initial balance: ");
        double balance = input.nextDouble();
        System.out.println("Enter account type: ");
        String accountType = input.next();
        createAccount(accountName, balance, accountType, "Active");
        input.close();
    }
    
    @Override
    public void forgotPassword() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter account number: ");
        int accountNumber = input.nextInt();
        BankAccount account = getAccountByNumber(accountNumber);
        if (account != null) {
            System.out.println("Account found. Enter new password: ");
            String newPassword = input.nextLine();
            account.setPassword(newPassword);
        } else {
            System.out.println("Account not found.");
        }

        input.close();
    }

    private void changePIN() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter old PIN: ");
        int oldPin = input.nextInt();
        //for testing purpose
        if (oldPin == 123456) {
            System.out.println("Enter new PIN: ");
            int newPin = input.nextInt();
            setPin(newPin);
        }
        // setPassword(newPassword);
        input.close();
    }
}


