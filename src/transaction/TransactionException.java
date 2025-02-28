package transaction;

import bankAccount.BankAccount;

public class TransactionException extends Exception {
    public TransactionException(String message) {
        super(message);
    }

    // Validate if the amount is greater than zero, otherwise throw exception
    public static void validateAmount(double amount) throws TransactionException {
        if (amount <= 0) {
            throw new TransactionException("Invalid amount: $" + amount + ". Amount must be greater than zero.");
        }
    }

    // Validate if sufficient funds are available, otherwise throw exception
    public static void validateSufficientFunds(BankAccount account, double amount) throws TransactionException {
        if (account.getBalance() < amount) {
            throw new TransactionException("Insufficient funds: Available balance is $" + account.getBalance() + ", but attempted transaction is $" + amount + ".");
        }
    }

    // Validate if the account index is within range
    public static void validateAccountSelection(int index, int size) throws TransactionException {
        if (index < 0 || index >= size) {
            throw new TransactionException("Invalid selection. Please choose a valid account.");
        }
    }

    // Validate if the recipient account exists
    public static void validateRecipientAccount(BankAccount recipient) throws TransactionException {
        if (recipient == null) {
            throw new TransactionException("Recipient account not found.");
        }
    }
}
