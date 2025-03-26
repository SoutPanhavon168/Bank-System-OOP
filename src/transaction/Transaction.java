package transaction;

import bankaccount.BankAccount;
import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Transaction {
    public enum TransactionType {
        DEPOSIT, WITHDRAW, TRANSFER, UNKNOWN;
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
        this.status = "Completed";//ly set as pending until PIN is verified
        this.transactionDate = LocalDateTime.now();
        
        // PIN verification happens at creation time
    }
    
    public Transaction(BankAccount account, TransactionType type2, double double1, String string) {
        //TODO Auto-generated constructor stub
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
    
    public String getTypes() {
        return this.type.toString(); // Converts enum to a string
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

    public String getDate() {
        return transactionDate.toString();
    }

    public void setType(TransactionType transactionType) {
        
    }
    
 

    @Override
public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Transaction ID: ").append(transactionID != null ? transactionID : "N/A").append("\n");
    
    // Handle null bankAccount
    if (bankAccount != null) {
        sb.append("Account: ").append(bankAccount.getAccountNumber()).append("\n");
    } else {
        sb.append("Account: N/A\n");
    }
    
    // Handle null type
    if (type != null) {
        sb.append("Type: ").append(type.toString()).append("\n");
    } else {
        sb.append("Type: N/A\n");
    }
    
    sb.append("Amount: $").append(String.format("%.2f", amount)).append("\n");
    sb.append("Status: ").append(status != null ? status : "N/A").append("\n");
    sb.append("Date: ").append(transactionDate != null ? transactionDate : "N/A").append("\n");
    sb.append("----------------------------------------\n");
    return sb.toString();
}
    
}
