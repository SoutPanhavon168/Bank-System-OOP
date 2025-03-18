package transaction;

import bankaccount.BankAccount;
import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Transaction {
    public enum TransactionType {
        DEPOSIT, WITHDRAW, TRANSFER;
    }
    
    private String transactionID;
    private BankAccount bankAccount;
    private TransactionType type;
    private double amount;
    private String status;
    private LocalDateTime transactionDate;
    
    // Constructor with PIN verification
    public Transaction(BankAccount bankAccount, String type, double amount, String status) {
        this.bankAccount = bankAccount;
        this.type = TransactionType.valueOf(type.toUpperCase());
        this.amount = amount;
        this.status = "Pending"; // Initially set as pending until PIN is verified
        this.transactionDate = LocalDateTime.now();
        
        // PIN verification happens at creation time
    }
    
    public boolean verifyPin() {
      Scanner scanner = new Scanner(System.in);
      System.out.print("Please enter your 4-digit PIN to authorize this transaction: ");
      
      int attempts = 0;
      final int MAX_ATTEMPTS = 3;
  
      while (attempts < MAX_ATTEMPTS) {
          try {
              int enteredPin = scanner.nextInt();
              scanner.nextLine(); // Clear the buffer (move to the next line)
              
              // Verify the entered PIN against the account's PIN
              if (enteredPin == bankAccount.getPin()) {
                  return true; // PIN is correct, exit immediately
              } else {
                  attempts++;
                  if (attempts < MAX_ATTEMPTS) {
                      System.out.println("Incorrect PIN. You have " + (MAX_ATTEMPTS - attempts) + 
                                         " attempts remaining. Please try again: ");
                  }
              }
          } catch (InputMismatchException e) {
              scanner.nextLine(); // Clear the invalid input in the buffer
              System.out.println("Invalid input. Please enter a 4-digit PIN: ");
          }
      }
  
      System.out.println("Maximum PIN attempts exceeded. Transaction cancelled for security.");
      return false; // PIN verification failed after max attempts
  }
  

    // Getters and setters
    public String getTransactionID() {
        return transactionID;
    }
    
    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }
    
    public BankAccount getBankAccount() {
        return bankAccount;
    }
    
    public TransactionType getType() {
        return type;
    }
    
    public double getAmount() {
        return amount;
    }

    public String getTransactionId() {
        // Get transaction details from the database
        return transactionID;

    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }
    
    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }
    
    
}
