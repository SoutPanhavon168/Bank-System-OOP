package transaction;
import bankaccount.BankAccount;
import database.BankAccountDAO;
import database.TransactionDAO;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class TransactionManager {

    private ArrayList<Transaction> transactions = new ArrayList<>();
    private TransactionDAO transactionDAO = new TransactionDAO();  // Add a reference to the TransactionDAO

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public boolean verifyPin(BankAccount account) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter PIN for account " + account.getAccountNumber() + ": ");
        String enteredPin = input.nextLine();
        
        // Fetch the PIN from the database
        BankAccountDAO bankAccountDAO = new BankAccountDAO();
        int storedPin = bankAccountDAO.getPinForAccount(account.getAccountNumber());  // Retrieves the PIN as an int
        
        // Convert the stored PIN (int) to a String for comparison
        String storedPinString = Integer.toString(storedPin);
    
        // Compare the entered PIN with the stored PIN
        if (enteredPin.equals(storedPinString)) {
            return true;
        } else {
            System.out.println("Incorrect PIN. Transaction cancelled.");
            return false;
        }
    }

    public void deposit(ArrayList<BankAccount> bankAccounts) {
        Scanner input = new Scanner(System.in);
    
        if (bankAccounts.isEmpty()) {
            System.out.println("No bank accounts available.");
            return;
        }
    
        try {
            listBankAccounts(bankAccounts);
            System.out.print("Enter the number of the account: ");
            int index = input.nextInt() - 1;
    
            TransactionException.validateAccountSelection(index, bankAccounts.size()); // Validate selection
    
            BankAccount selectedAccount = bankAccounts.get(index);
    
            // Verify PIN before proceeding
            if (!verifyPin(selectedAccount)) {
                return;
            }
    
            System.out.print("Enter amount to deposit ($): ");
            double depositAmount = input.nextDouble();
    
            TransactionException.validateAmount(depositAmount); // Validate deposit amount
    
            // Create a transaction object
            Transaction transaction = new Transaction(selectedAccount, "Deposit", depositAmount, "Completed");
            addTransaction(transaction);
    
            // Save the transaction to the database, which also updates the balance
            boolean success = transactionDAO.saveTransaction(transaction);
    
            if (success) {
                // After saving the transaction, fetch the updated account with the latest balance
                BankAccountDAO bankAccountDAO = new BankAccountDAO();
                BankAccount updatedAccount = bankAccountDAO.getBankAccountById(selectedAccount.getAccountNumber());
    
                // Print the updated balance from the database
                System.out.println("Deposit successful. New balance: $" + updatedAccount.getBalance());
    
                // Print the transaction details
                System.out.println(transaction.toString());
            } else {
                System.out.println("Transaction failed. Please try again.");
            }
    
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter numbers only.");
        } catch (TransactionException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }
    

    public void withdraw(ArrayList<BankAccount> bankAccounts) {
        Scanner input = new Scanner(System.in);

        if (bankAccounts.isEmpty()) {
            System.out.println("No bank accounts available.");
            return;
        }

        try {
            listBankAccounts(bankAccounts);
            System.out.print("Enter the number of the account: ");
            int index = input.nextInt() - 1;

            TransactionException.validateAccountSelection(index, bankAccounts.size()); // Validate selection

            BankAccount selectedAccount = bankAccounts.get(index);

            // Verify PIN before proceeding
            if (!verifyPin(selectedAccount)) {
                return;
            }

            System.out.print("Enter amount to withdraw ($): ");
            double withdrawAmount = input.nextDouble();

            TransactionException.validateAmount(withdrawAmount); // Validate withdraw amount
            TransactionException.validateSufficientFunds(selectedAccount, withdrawAmount); // Validate sufficient funds

            

            Transaction transaction = new Transaction(selectedAccount, "Withdraw", withdrawAmount, "Completed");
            addTransaction(transaction);
            transactionDAO.saveTransaction(transaction);  // Save the transaction to the database
            System.out.println(transaction.toString());

        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter numbers only.");
        } catch (TransactionException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    public void transfer(ArrayList<BankAccount> bankAccounts) {
        Scanner input = new Scanner(System.in);
        BankAccountDAO bankAccountDAO = new BankAccountDAO();
        
        try {
            System.out.println("Choose transfer type:");
            System.out.println("1. Transfer between my own accounts");
            System.out.println("2. Transfer to another person's account");
            System.out.print("Enter your choice: ");
            int transferType = input.nextInt();
        
            BankAccount sender, recipient = null;
        
            System.out.println("Select your account to transfer from:");
            listBankAccounts(bankAccounts);
            System.out.print("Enter the number of your account: ");
            int senderIndex = input.nextInt() - 1;
        
            TransactionException.validateAccountSelection(senderIndex, bankAccounts.size()); // Validate selection
            sender = bankAccounts.get(senderIndex);
        
            // Verify PIN before proceeding
            if (!verifyPin(sender)) {
                return;
            }
        
            if (transferType == 1) {
                System.out.println("Select the account to transfer to:");
                listBankAccounts(bankAccounts);
                System.out.print("Enter the number of the recipient's account: ");
                int recipientIndex = input.nextInt() - 1;
        
                TransactionException.validateAccountSelection(recipientIndex, bankAccounts.size()); // Validate recipient selection
                if (recipientIndex == senderIndex) {
                    throw new TransactionException("Cannot transfer to the same account.");
                }
        
                recipient = bankAccounts.get(recipientIndex);
        
            } else if (transferType == 2) {
                System.out.print("Enter the recipient's account number: ");
                int recipientAccountNumber = input.nextInt();
        
                recipient = bankAccountDAO.getBankAccountById(recipientAccountNumber); // Fetch recipient from DB
        
                TransactionException.validateRecipientAccount(recipient); // Validate recipient existence
            } else {
                throw new TransactionException("Invalid transfer type.");
            }
        
            System.out.print("Enter amount to transfer ($): ");
            double transferAmount = input.nextDouble();
        
            TransactionException.validateAmount(transferAmount); // Validate transfer amount
            TransactionException.validateSufficientFunds(sender, transferAmount); // Validate sufficient funds
        
            // Perform fund transfer using TransactionDAO
            boolean transferSuccess = transactionDAO.transferFunds(sender.getAccountNumber(), recipient.getAccountNumber(), transferAmount);
        
            if (transferSuccess) {     
                // Fetch updated balances from DB
                BankAccount updatedSender = bankAccountDAO.getBankAccountById(sender.getAccountNumber());
                System.out.println("New balance for sender: $" + updatedSender.getBalance());
            } else {
                System.out.println("Transaction failed. Please try again.");
            }
        
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter numbers only.");
        } catch (TransactionException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }
    
    public void viewSpecificTransaction(String transactionID) {
        Transaction transaction = transactionDAO.getTransactionById(transactionID);
        if (transaction != null) {
            System.out.println(transaction);
        } else {
            System.out.println("Transaction not found.");
        }
    }
    
    public void viewTransactionHistory() {
        List<Transaction> transactions = transactionDAO.getAllTransactions();
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }
    
        System.out.println("===== Transaction History =====");
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }
    


public void sortTransactionsByAmount() {
    List<Transaction> transactions = transactionDAO.getAllTransactions();
    if (transactions.isEmpty()) {
        System.out.println("No transactions found to sort.");
        return;
    }

    // Sort transactions by amount
    transactions.sort(Comparator.comparingDouble(Transaction::getAmount));

    System.out.println("Transactions sorted by amount.");
    for (Transaction transaction : transactions) {
        System.out.println(transaction);
    }
}


public double getTotalDepositAmount() {
    List<Transaction> transactions = transactionDAO.getAllTransactions();
    return transactions.stream()
            .filter(transaction -> transaction.getType() == Transaction.TransactionType.DEPOSIT)
            .mapToDouble(Transaction::getAmount)
            .sum();
}


public double getTotalWithdrawalAmount() {
    List<Transaction> transactions = transactionDAO.getAllTransactions();
    return transactions.stream()
            .filter(transaction -> transaction.getType() == Transaction.TransactionType.WITHDRAW)
            .mapToDouble(Transaction::getAmount)
            .sum();
}


public void sortTransactionsByDate() {
    List<Transaction> transactions = transactionDAO.getAllTransactions();
    if (transactions.isEmpty()) {
        System.out.println("No transactions found to sort.");
        return;
    }

    transactions.sort(Comparator.comparing(Transaction::getTransactionDate));

    System.out.println("Transactions sorted by date.");
    for (Transaction transaction : transactions) {
        System.out.println(transaction);
    }
}


private void listBankAccounts(ArrayList<BankAccount> bankAccounts) {
    if (bankAccounts.isEmpty()) {
        System.out.println("No bank accounts available.");
        return;
    }
    for (int i = 0; i < bankAccounts.size(); i++) {
        System.out.println((i + 1) + ". " + bankAccounts.get(i).getAccountNumber());
    }
    
}

}
