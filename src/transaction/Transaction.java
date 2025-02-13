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
    
    public Transaction(BankAccount bankAccount, String transactionType, double amount, String status) {
        this(transactionType, amount, status);
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

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void deposit(double depositAmount) {
        if (depositAmount > 0) {
            bankAccount.setBalance(bankAccount.getBalance() + depositAmount);
            this.transactionType = "Deposit";
            this.amount = depositAmount;
            this.status = "Completed";
            printTransactionDetails();
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public void withdraw(double withdrawAmount) {
        if (withdrawAmount > 0 && bankAccount.getBalance() >= withdrawAmount) {
            bankAccount.setBalance(bankAccount.getBalance() - withdrawAmount);
            this.transactionType = "Withdraw";
            this.amount = withdrawAmount;
            this.status = "Completed";
            printTransactionDetails();
        } else {
            System.out.println("Insufficient balance or invalid amount.");
        }
    }

    public void transfer(BankAccount recipient, double transferAmount) {
        if (transferAmount > 0 && bankAccount.getBalance() >= transferAmount) {
            bankAccount.setBalance(bankAccount.getBalance() - transferAmount);
            recipient.setBalance(recipient.getBalance() + transferAmount);
            this.transactionType = "Transfer";
            this.amount = transferAmount;
            this.status = "Completed";
            printTransactionDetails();
        } else {
            System.out.println("Transfer failed. Insufficient balance or invalid amount.");
        }
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
        Transaction transaction = new Transaction(transactionType, amount, "Completed");

        // Print the transaction details
        System.out.println(transaction.toString());
    }

}
