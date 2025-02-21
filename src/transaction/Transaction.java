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
    
        if (bankAccounts.isEmpty()) {
            System.out.println("No bank accounts available.");
            return;
        }
    
        // Display available accounts
        System.out.println("Select an account to deposit into:");
        for (int i = 0; i < bankAccounts.size(); i++) {
            System.out.println((i + 1) + ". " + bankAccounts.get(i).getAccountNumber());
        }
    
        // Get user selection
        System.out.print("Enter the number of the account: ");
        int index = input.nextInt() - 1; // Adjust for 0-based indexing
    
        if (index < 0 || index >= bankAccounts.size()) {
            System.out.println("Invalid selection.");
            return;
        }
    
        BankAccount selectedAccount = bankAccounts.get(index);
    
        // Get deposit amount
        System.out.print("Enter amount to deposit ($): ");
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
    
        if (bankAccounts.isEmpty()) {
            System.out.println("No bank accounts available.");
            return;
        }
    
        // Display available accounts
        System.out.println("Select an account to withdraw from:");
        for (int i = 0; i < bankAccounts.size(); i++) {
            System.out.println((i + 1) + ". " + bankAccounts.get(i).getAccountNumber());
        }
    
        // Get user selection
        System.out.print("Enter the number of the account: ");
        int index = input.nextInt() - 1; // Adjust for 0-based indexing
    
        if (index < 0 || index >= bankAccounts.size()) {
            System.out.println("Invalid selection.");
            return;
        }
    
        BankAccount selectedAccount = bankAccounts.get(index);
    
        // Get withdrawal amount
        System.out.print("Enter amount to withdraw ($): ");
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
    
        if (bankAccounts.size() < 2) {
            System.out.println("At least two accounts are required for a transfer.");
            return;
        }
    
        // Ask transfer type
        System.out.println("Choose transfer type:");
        System.out.println("1. Transfer between my own accounts");
        System.out.println("2. Transfer to another person's account");
        System.out.print("Enter your choice: ");
        int transferType = input.nextInt();
    
        BankAccount sender = null;
        BankAccount recipient = null;
    
        // Select sender account
        System.out.println("Select your account to transfer from:");
        for (int i = 0; i < bankAccounts.size(); i++) {
            System.out.println((i + 1) + ". " + bankAccounts.get(i).getAccountNumber());
        }
        
        System.out.print("Enter the number of your account: ");
        int senderIndex = input.nextInt() - 1;
    
        if (senderIndex < 0 || senderIndex >= bankAccounts.size()) {
            System.out.println("Invalid selection.");
            return;
        }
    
        sender = bankAccounts.get(senderIndex);
    
        // If transferring between own accounts
        if (transferType == 1) {
            System.out.println("Select the account to transfer to:");
            for (int i = 0; i < bankAccounts.size(); i++) {
                if (i != senderIndex) { // Exclude sender's account
                    System.out.println((i + 1) + ". " + bankAccounts.get(i).getAccountNumber());
                }
            }
    
            System.out.print("Enter the number of the recipient's account: ");
            int recipientIndex = input.nextInt() - 1;
    
            if (recipientIndex < 0 || recipientIndex >= bankAccounts.size() || recipientIndex == senderIndex) {
                System.out.println("Invalid selection.");
                return;
            }
    
            recipient = bankAccounts.get(recipientIndex);
    
        } else if (transferType == 2) {
            // Transfer to another person's account (manual entry)
            System.out.print("Enter the recipient's account number: ");
            int recipientAccountNumber = input.nextInt();
    
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
        } else {
            System.out.println("Invalid choice.");
            return;
        }
    
        // Get transfer amount
        System.out.print("Enter amount to transfer ($): ");
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
