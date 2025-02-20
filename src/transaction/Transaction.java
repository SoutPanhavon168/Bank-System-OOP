package transaction;

import bankaccount.BankAccount;
import java.time.LocalDateTime;
import java.util.ArrayList;

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

    public void deposit(double depositAmount) {
        this.transactionType = "Deposit";
        if (depositAmount > 0) {
            bankAccount.setBalance(bankAccount.getBalance() + depositAmount);
            this.amount = depositAmount;
            this.status = "Completed";
        } else {
            this.status = "Failed";
            System.out.println("Invalid deposit amount.");
        }
        printTransactionDetails();
    }

    public void withdraw(double withdrawAmount) {
        this.transactionType = "Withdraw";
        if (withdrawAmount > 0 && bankAccount.getBalance() >= withdrawAmount) {
            bankAccount.setBalance(bankAccount.getBalance() - withdrawAmount);
            this.amount = withdrawAmount;
            this.status = "Completed";
        } else {
            this.status = "Failed";
            System.out.println("Insufficient balance or invalid amount.");
        }
        printTransactionDetails();
    }

    public void transfer(BankAccount recipient, double transferAmount) {
        this.transactionType = "Transfer";
        if (transferAmount > 0 && bankAccount.getBalance() >= transferAmount) {
            bankAccount.setBalance(bankAccount.getBalance() - transferAmount);
            recipient.setBalance(recipient.getBalance() + transferAmount);
            this.amount = transferAmount;
            this.status = "Completed";
        } else {
            this.status = "Failed";
            System.out.println("Transfer failed. Insufficient balance or invalid amount.");
        }
        printTransactionDetails();
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
