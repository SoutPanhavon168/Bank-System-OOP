package bankaccount;

public class CurrentAccount extends BankAccount {
    private double overdraftLimit;
    private double monthlyFee;

    // Constructor with default overdraft limit and monthly fee
    public CurrentAccount(String accountName) {
        super(accountName, "Current", "Active"); // Account type is "Current"
        this.overdraftLimit = 1000.0; // Default overdraft limit
        this.monthlyFee = 15.0; // Example monthly fee
    }
    
    // Constructor with custom overdraft limit and monthly fee
    public CurrentAccount(String accountName, double overdraftLimit, double monthlyFee) {
        super(accountName, "Current", "Active");
        this.overdraftLimit = overdraftLimit;
        this.monthlyFee = monthlyFee;
    }

    // Method to apply the monthly fee
    public void applyMonthlyFee() {
        setBalance(getBalance() - monthlyFee); // Deduct monthly fee from the balance
        if (getBalance() < -overdraftLimit) {
            throw new IllegalStateException("Cannot apply fee: Overdraft limit exceeded.");
        }
    }

    // Getter for overdraft limit
    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    // Setter for overdraft limit
    public void setOverdraftLimit(double overdraftLimit) {
        if (overdraftLimit < 0) {
            throw new IllegalArgumentException("Overdraft limit cannot be negative.");
        }
        this.overdraftLimit = overdraftLimit;
    }

    // Getter for monthly fee
    public double getMonthlyFee() {
        return monthlyFee;
    }

    // Setter for monthly fee
    public void setMonthlyFee(double monthlyFee) {
        if (monthlyFee < 0) {
            throw new IllegalArgumentException("Monthly fee cannot be negative.");
        }
        this.monthlyFee = monthlyFee;
    }

    @Override
    public String toString() {
        return super.toString() + "Overdraft Limit: " + overdraftLimit + "\n" +
               "Monthly Fee: " + monthlyFee + "\n";
    }
}
