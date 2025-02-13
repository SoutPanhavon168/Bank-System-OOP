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
    public BankAccount(String accountName, double balance, String accountType, String accountStatus) {
        this.accountName = accountName;
        this.balance = 0.0;
        this.accountType = accountType;
        this.accountStatus = accountStatus;

        accounts.add(this);
    }
    // // to be used later
    // public void createSubAccount(String accountName, String accountType, String accountStatus) {
    //     this.accountName = accountName;
    //     this.balance = 1.0; // default
    //     this.accountType = accountType;
    //     this.accountStatus = "Active";
    // }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
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

    public void setBalance(double balance){
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


