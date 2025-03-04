package bankaccount;

public class CheckingAccount extends BankAccount {
    private int checkLimit;
    private double transactionFee;

    // Constructor with default check limit and transaction fee
    public CheckingAccount(String accountName) {
        super(accountName, "Checking", "Active"); // Account type is "Checking"
        this.checkLimit = 50; // Default limit of checks that can be written per month
        this.transactionFee = 1.0; // Default transaction fee per withdrawal or transfer
    }

    // Constructor with custom check limit and transaction fee
    public CheckingAccount(String accountName, int checkLimit, double transactionFee) {
        super(accountName, "Checking", "Active"); // Account type is "Checking"
        this.checkLimit = checkLimit;
        this.transactionFee = transactionFee;
    }

    // Method to deduct transaction fee for each withdrawal/transfer
    public void applyTransactionFee() {
        setBalance(getBalance() - transactionFee); // Deduct the transaction fee from the balance
        if (getBalance() < 0) {
            throw new IllegalStateException("Cannot apply fee: Insufficient funds.");
        }
    }

    // Getter for check limit
    public int getCheckLimit() {
        return checkLimit;
    }

    // Setter for check limit
    public void setCheckLimit(int checkLimit) {
        if (checkLimit < 0) {
            throw new IllegalArgumentException("Check limit cannot be negative.");
        }
        this.checkLimit = checkLimit;
    }

    // Getter for transaction fee
    public double getTransactionFee() {
        return transactionFee;
    }

    // Setter for transaction fee
    public void setTransactionFee(double transactionFee) {
        if (transactionFee < 0) {
            throw new IllegalArgumentException("Transaction fee cannot be negative.");
        }
        this.transactionFee = transactionFee;
    }

    @Override
    public String toString() {
        return super.toString() + "Check Limit: " + checkLimit + "\n" +
               "Transaction Fee: " + transactionFee + "\n";
    }
}
