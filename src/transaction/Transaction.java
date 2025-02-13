package transaction;

import java.time.LocalDateTime;
import java.util.Scanner;

public class Transaction {
    private String transactionID;
    private String transactionType;
    private double amount;
    private LocalDateTime dateTime;
    private String status;

    public Transaction(){
        
    }

    public Transaction(String transactionType, double amount, String status){
        this.transactionType = transactionType;
        this.amount = amount;
        this.status = status;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void operations(){
        Scanner input = new Scanner(System.in);
        int choice;
        System.out.println("1. Deposit");
        System.out.println("2. Withdraw");
        System.out.println("3. Transfer");
        System.out.println("4. Check Balance");
        System.out.println("5. Exit");
        System.out.print("Select an operation -> ");
        choice = input.nextInt();
        switch (choice){
            case 1:
                
                deposit();
                setTransactionType("Deposit");
                printTransactionDetails();
                break;
                case 2:
                withdraw();
                setTransactionType("Withdraw");
                printTransactionDetails();
                break;
                case 3:
                transfer();
                setTransactionType("Transfer");
                printTransactionDetails();
                break;
                case 4:
                checkBalance();
                setTransactionType("Check Balance");
                printTransactionDetails();
                break;
            case 5:
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice");
        }
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
    public double deposit(){
        Scanner input = new Scanner(System.in);
        System.out.println("Enter amount to deposit");
        amount = input.nextDouble();
        System.out.println("Amount deposited successfully");
        this.amount = amount;
        return this.amount;
    }

    public void withdraw(){
        Scanner input = new Scanner(System.in);
        System.out.print("Enter amount to withdraw: ");
        double withdrawAmount = input.nextDouble();
        if (withdrawAmount > amount){
            System.out.println("Insufficient balance");
        } else {
            amount -= withdrawAmount;
            System.out.println("Amount withdrawn successfully");
        }
    }

    public void transfer(){
        Scanner input = new Scanner(System.in);
        System.out.println("Enter accountID to transfer: ");
        String accountID = input.nextLine();
        System.out.print("Enter amount to transfer: ");
        double transferAmount = input.nextDouble();
        if (transferAmount > amount){
            System.out.println("Insufficient balance");
        } else {
            amount -= transferAmount;
            System.out.println("Amount transferred successfully");
        }
    }

    public void checkBalance(){
        System.out.println("Your balance is: " + amount);
    }


    public String generateTransactionID(){
        return transactionID = "T" + (int)(Math.random() * 1000);
    }

    @Override
    public String toString(){
        return "Transaction ID: " + generateTransactionID() + "\n" +
                "Transaction Type: " + transactionType + "\n" +
                "Amount: " + amount + "\n" +
                "Date and Time: " + getDateTime() + "\n" +
                "Status: " + status + "\n";
    }

    public void printTransactionDetails() {
        // Generate a transaction ID (if it's not set already)    
        // Create the Transaction object (you may need to store this or pass it around as needed)
        Transaction transaction = new Transaction(transactionType, amount, "Completed");
    
        // Print the transaction details
        System.out.println(transaction.toString());
    }
    
   
}


