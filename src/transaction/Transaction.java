package transaction;

import bankAccount.BankAccount;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Transaction {

    private String transactionID;
    private String transactionType;
    private double amount;
    private LocalDateTime dateTime;
    private String status;
    private BankAccount bankAccount;
    ArrayList<Transaction> transactions = new ArrayList<>();

    // to be implemented:
    // public void viewTransactionHistory();
    // public void viewSpecificTransaction(); (By Date)
    // public void viewAllTransactions();
    // public void viewTransactionByType(String transactionType);
    // public void sortTransactionsByDate();
    // public void sortTransactionsByAmount();


    

    public Transaction(BankAccount bankAccount, String transactionType, double amount, String status) {
        this(transactionType, amount, status);
        this.bankAccount = bankAccount;
    }
    public Transaction(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public Transaction(String transactionType, double amount, String status) {
        this.transactionType = transactionType;
        this.amount = amount;
        this.status = status;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    //for viewing history
    public String getTransactionType() {
        return transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getDateTime() {
        if (dateTime == null) {
            dateTime = LocalDateTime.now().withNano(0);
        }
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void deposit(ArrayList<BankAccount> bankAccounts) {
        Scanner input = new Scanner(System.in);
        
        System.out.println("Enter the account number you want to deposit into: ");
        int accountNumber = input.nextInt();
    
        // Find the bank account
        BankAccount selectedAccount = null;
        for (BankAccount account : bankAccounts) {
            if (account.getAccountNumber() == accountNumber) {
                selectedAccount = account;
                break;
            }
        }
    
        if (selectedAccount == null) {
            System.out.println("Account not found.");
            return;
        }
    
        System.out.println("Enter amount to deposit ($): ");
        double depositAmount = input.nextDouble();
    
        if (depositAmount > 0) {
            selectedAccount.setBalance(selectedAccount.getBalance() + depositAmount);
            System.out.println("Deposit successful. New balance: $" + selectedAccount.getBalance());
    
            // Save transaction record
            Transaction transaction = new Transaction(selectedAccount, "Deposit", depositAmount, "Completed");
            System.out.println(transaction.toString());
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }
    

    public void withdraw(ArrayList<BankAccount> bankAccounts) {
        Scanner input = new Scanner(System.in);
    
        System.out.println("Enter your account number to withdraw from: ");
        int accountNumber = input.nextInt();
    
        // Find the account
        BankAccount selectedAccount = null;
        for (BankAccount account : bankAccounts) {
            if (account.getAccountNumber() == accountNumber) {
                selectedAccount = account;
                break;
            }
        }
    
        if (selectedAccount == null) {
            System.out.println("Account not found.");
            return;
        }
    
        System.out.println("Enter amount to withdraw ($): ");
        double withdrawAmount = input.nextDouble();
    
        if (withdrawAmount > 0 && selectedAccount.getBalance() >= withdrawAmount) {
            selectedAccount.setBalance(selectedAccount.getBalance() - withdrawAmount);
            System.out.println("Withdrawal successful. New balance: $" + selectedAccount.getBalance());
    
            // Save transaction record
            Transaction transaction = new Transaction(selectedAccount, "Withdraw", withdrawAmount, "Completed");
            System.out.println(transaction.toString());
        } else {
            System.out.println("Withdrawal failed. Insufficient balance or invalid amount.");
        }
    }
    

    public void transfer(ArrayList<BankAccount> bankAccounts) {
        Scanner input = new Scanner(System.in);
    
        // Select sender account
        System.out.println("Enter your account number to transfer from: ");
        int senderAccountNumber = input.nextInt();
    
        BankAccount sender = null;
        for (BankAccount account : bankAccounts) {
            if (account.getAccountNumber() == senderAccountNumber) {
                sender = account;
                break;
            }
        }
        
        if (sender == null) {
            System.out.println("Sender account not found.");
            return;
        }
    
        // Select receiver account
        System.out.println("Enter recipient's account number: ");
        int recipientAccountNumber = input.nextInt();
    
        BankAccount recipient = null;
        for (BankAccount account : bankAccounts) {
            if (account.getAccountNumber() == recipientAccountNumber) {
                recipient = account;
                break;
            }
        }
    
        if (recipient == null) {
            System.out.println("Recipient account not found.");
            return;
        }
    
        System.out.println("Enter amount to transfer ($): ");
        double transferAmount = input.nextDouble();
    
        if (transferAmount > 0 && sender.getBalance() >= transferAmount) {
            sender.setBalance(sender.getBalance() - transferAmount);
            recipient.setBalance(recipient.getBalance() + transferAmount);
            
            System.out.println("Transfer successful!");
            System.out.println("New balance for sender: $" + sender.getBalance());
    
            // Save transaction record
            Transaction transaction = new Transaction(sender, "Transfer", transferAmount, "Completed");
            System.out.println(transaction.toString());
        } else {
            System.out.println("Transfer failed. Insufficient balance or invalid amount.");
        }
    }
    

    public void viewTransactionHistory(){
        // view all transaction history
    }
    public void viewSpecificTransaction(String transactionID){
        // view specific transaction by id
        // if id == id, print transaction details
        // else print "Transaction not found"
    } 
    public void viewTransactionByType(String transactionType){
        // view transaction by type
        // if type == type, print transaction details
        // else print "Transaction not found"
    }
    public void sortTransactionsByDate(){
        // sort transactions by date
        // print sorted transactions
        // if transactions.isEmpty(), print "No transactions found"
        // else print sorted transactions
    }
    public void sortTransactionsByAmount(){
        // sort transactions by amount
        // print sorted transactions
        // if transactions.isEmpty(), print "No transactions found"
        // else print sorted transactions
    }

    public void checkBalance() {
        System.out.println("Your balance is: " + bankAccount.getBalance());
    }


    public String generateTransactionID() {
        return transactionID = "T" + (int) (Math.random() * 1000);
    }

    @Override
    public String toString() {
        return "Transaction ID: " + generateTransactionID() + "\n"
                + "Transaction Type: " + transactionType + "\n"
                + "Amount: " + amount + "\n"
                + "Date and Time: " + getDateTime() + "\n"
                + "Status: " + status + "\n";
    }

    public void printTransactionDetails() {
        // Generate a transaction ID (if it's not set already)    
        // Create the Transaction object (you may need to store this or pass it around as needed)
        Transaction transaction = new Transaction(transactionType, amount, status);

        // Print the transaction details
        System.out.println(transaction.toString());
    }
    }
