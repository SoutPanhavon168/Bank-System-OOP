package bankAccount;

public class SavingsAccount extends BankAccount {
    private double interestRate;

    // Constructor with account name and default interest rate
    public SavingsAccount(String accountName) {
        super(accountName, "Saving", "Active"); // Setting the accountType as "Saving"
        this.interestRate = 2.5; // Default interest rate for savings
    }

    // Constructor with custom interest rate
    public SavingsAccount(String accountName, double interestRate) {
        super(accountName, "Saving", "Active"); // Setting the accountType as "Saving"
        this.interestRate = interestRate;
    }

    // Method to apply interest on the current balance
    public void applyInterest() {
        setBalance(getBalance() + (getBalance() * (interestRate / 100))); // Apply interest to balance
    }

    // Getter for interest rate
    public double getInterestRate() {
        return interestRate;
    }

    // Setter for interest rate
    public void setInterestRate(double interestRate) {
        if (interestRate < 0) {
            throw new IllegalArgumentException("Interest rate cannot be negative.");
        }
        this.interestRate = interestRate;
    }

    @Override
    public String toString() {
        return super.toString() + "Interest Rate: " + interestRate + "%\n";
    }
}
