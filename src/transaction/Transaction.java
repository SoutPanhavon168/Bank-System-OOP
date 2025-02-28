package transaction;

import bankAccount.BankAccount;
import java.time.LocalDate;

public class Transaction {
    private String transactionID;
    private BankAccount bankAccount;
    private double amount;
    private LocalDate transactionDate;
    private TransactionType type;
    private String status;

    public enum TransactionType {
        DEPOSIT,
        WITHDRAWAL,
        TRANSFER
    }
    

    // Constructor
    public Transaction(BankAccount bankAccount, String type, double amount, String status) {
        this.transactionID = generateTransactionID();  // You can generate an ID or pass it as a parameter
        this.bankAccount = bankAccount;
        this.amount = amount;
        this.transactionDate = LocalDate.now();
        this.type = TransactionType.valueOf(type.toUpperCase()); // Convert string to enum
        this.status = status;
    }

    private String generateTransactionID() {
        // You can implement logic for generating a unique transaction ID, for now just return a placeholder
        return "txn" + System.currentTimeMillis();
    }

    // Getters and other methods
    public String getTransactionID() {
        return transactionID;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public TransactionType getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionID='" + transactionID + '\'' +
                ", amount=" + amount +
                ", transactionDate=" + transactionDate +
                ", type=" + type +
                ", status='" + status + '\'' +
                '}';
    }
}
