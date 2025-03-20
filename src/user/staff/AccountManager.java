package user.staff;
import bankaccount.BankAccount;
import database.BankAccountDAO;
import database.TransactionDAO;
import java.util.List;
import java.util.Scanner;
import transaction.Transaction;

public class AccountManager {
    
    private Staff currentStaff;
    private BankAccountDAO bankAccountDAO;
    private TransactionDAO transactionDAO;
    
    public AccountManager(Staff staff) {
        this.currentStaff = staff;
        this.bankAccountDAO = new BankAccountDAO();
        this.transactionDAO = new TransactionDAO();
    }
    
    public void viewBankAccounts() {
        Scanner scanner = new Scanner(System.in);

        if (currentStaff.hasAccess(Staff.StaffRole.MANAGER) || currentStaff.hasAccess(Staff.StaffRole.TELLER)) {
            System.out.println("Would you like to: \n1. View all bank accounts \n2. Search for a bank account");
            System.out.println("Enter your choice (1 or 2): ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.println("All bank accounts:");
                viewAllBankAccounts();
            } else if (choice == 2) {
                System.out.println("Enter the bank account ID: ");
                int accountId = scanner.nextInt();
                scanner.nextLine();
                viewSpecificBankAccount(accountId);
            } else {
                System.out.println("Invalid choice. Please enter 1 or 2.");
            }
        } else {
            System.out.println("Access denied: Your role does not have permission to view bank accounts.");
        }
    }
    
    public void viewSpecificBankAccount(int accountId) {
        // Check if the user has the appropriate access
        if (currentStaff.hasAccess(Staff.StaffRole.MANAGER) || currentStaff.hasAccess(Staff.StaffRole.TELLER)) {
            // Fetch the bank account details from the database using a DAO method
            BankAccount bankAccount = bankAccountDAO.getBankAccountById(accountId);

            if (bankAccount == null) {
                System.out.println("Bank account with ID " + accountId + " not found.");
                return;
            }
            System.out.println(bankAccount.toString());
            
            // Optionally display other relevant bank account information here
        } else {
            // If the user does not have the required access, deny access
            System.out.println("Access denied: Your role does not have permission to view bank account details.");
        }
    }
    
    public void viewAllBankAccounts() {
        if (currentStaff.hasAccess(Staff.StaffRole.MANAGER) || currentStaff.hasAccess(Staff.StaffRole.TELLER)) {
            // Fetch and print all bank accounts' details from the BankAccountDAO
            System.out.println("All bank accounts:");

            // Retrieve all bank accounts from the database using BankAccountDAO
            List<BankAccount> bankAccounts = bankAccountDAO.getAllBankAccounts();

            if (bankAccounts.isEmpty()) {
                System.out.println("No bank accounts found.");
                return;
            }

            // Iterate through the list and display details for each bank account
            for (BankAccount bankAccount : bankAccounts) {
                System.out.println(bankAccount.toString()); // This will call the toString() method of BankAccount
            }

        } else {
            System.out.println("Access denied: Your role does not have permission to view bank accounts.");
        }
    }
    
    public void freezeAccount(int accountId) {
        if (currentStaff.hasAccess(Staff.StaffRole.MANAGER)){
            // Logic to freeze a bank account based on account ID
            boolean success = bankAccountDAO.freezeBankAccount(accountId);
            if (success) {
                System.out.println("Bank Account ID " + accountId + " has been frozen.");
            } else {
                System.out.println("Failed to freeze Bank Account ID " + accountId);
            }
        } else {
            System.out.println("Access denied: Your role does not have permission to freeze bank accounts.");
        }
    }
    
    public void unfreezeAccount(int accountId) {
        if (currentStaff.hasAccess(Staff.StaffRole.MANAGER)){
            // Logic to unfreeze a bank account based on account ID
            boolean success = bankAccountDAO.unfreezeBankAccount(accountId);
            if (success) {
                System.out.println("Bank Account ID " + accountId + " has been unfrozen.");
            } else {
                System.out.println("Failed to unfreeze Bank Account ID " + accountId);
            }
        } else {
            System.out.println("Access denied: Your role does not have permission to unfreeze bank accounts.");
        }
    }
    
    public void createBankAccount() {
        if (currentStaff.hasAccess(Staff.StaffRole.MANAGER) || currentStaff.hasAccess(Staff.StaffRole.CUSTOMER_SERVICE)) {
            // Implementation needed
            System.out.println("Bank account creation functionality not implemented yet.");
        } else {
            System.out.println("Access denied: Your role does not have permission to create bank account.");
        }
    }
    
    public void deleteBankAccount(int accountId) {
        if(currentStaff.hasAccess(Staff.StaffRole.MANAGER)){
            // Implementation needed
            System.out.println("Bank account deletion functionality not implemented yet.");
        }
        else{
            System.out.println("Access denied: You do not have permission to delete bank accounts.");
        }
    }
    
    public void viewAllTransactions(){
        if(currentStaff.hasAccess(Staff.StaffRole.MANAGER) || currentStaff.hasAccess(Staff.StaffRole.TELLER)){
            List<Transaction> transactions = transactionDAO.getAllTransactions();

            if (transactions.isEmpty()) {
                System.out.println("No transactions found.");
                return;
            }

            System.out.println("All transactions:");
            for (Transaction transaction : transactions) {
                System.out.println(transaction.toString());
            }
        }
        else{
            System.out.println("Access denied: Your role does not have permission to view transactions.");
        }
    }
    
    public void viewSpecificTransaction(String transactionId){
        if(currentStaff.hasAccess(Staff.StaffRole.MANAGER) || currentStaff.hasAccess(Staff.StaffRole.TELLER)){
            Transaction transaction = transactionDAO.getTransactionById(transactionId);
            if (transaction == null) {
                System.out.println("Transaction with ID " + transactionId + " not found.");
                return;
            }

            System.out.println(transaction.toString());
        }
        else{
            System.out.println("Access denied: Your role does not have permission to view transactions.");
        }
    }
    
    public void refundTransaction(String transactionId){
        if (currentStaff.hasAccess(Staff.StaffRole.MANAGER) || currentStaff.hasAccess(Staff.StaffRole.TELLER)) {
            // Retrieve the transaction
            Transaction transaction = transactionDAO.getTransactionById(transactionId);
            if (transaction == null) {
                System.out.println("Transaction with ID " + transactionId + " not found.");
                return;
            }
    
            // Check if a reverse transaction already exists
            if (transactionDAO.isRefunded(transactionId)) {
                System.out.println("Transaction has already been refunded.");
                return;
            }
            
            // Implementation for refund logic would go here
            System.out.println("Refund functionality not fully implemented yet.");
        } else {
            System.out.println("Access denied: You do not have permission to process refunds.");
        } 
    }
    
    public void depositMoney(int accountNumber, double amount){
        Scanner scanner = new Scanner(System.in);
        if (currentStaff.hasAccess(Staff.StaffRole.MANAGER) || currentStaff.hasAccess(Staff.StaffRole.TELLER)) {
            System.out.println("Enter the account number: ");
            int accountNumberInput = scanner.nextInt();
            scanner.nextLine();

            BankAccount bankAccount = bankAccountDAO.getBankAccountById(accountNumberInput);
            if (bankAccount == null) {
                System.out.println("Account with ID " + accountNumber + " not found.");
                return;
            }

            System.out.println("Enter the amount to deposit: ");
            double depositAmount = scanner.nextDouble();
            scanner.nextLine();

            if (depositAmount <= 0) {
                System.out.println("Invalid amount. Deposit amount must be greater than zero.");
                return;
            }

            double currentBalance = bankAccount.getBalance();
            double newBalance = currentBalance + depositAmount;

            bankAccount.setBalance(newBalance);
            
            Transaction depositTransaction = new Transaction(bankAccount, "DEPOSIT", depositAmount, "Completed");
            String transactionId = transactionDAO.generateTransactionID();
            depositTransaction.setTransactionID(transactionId);

            transactionDAO.updateAccountBalance(bankAccount, depositTransaction);
            transactionDAO.saveTransaction(depositTransaction);

            System.out.println("Deposit successful. New balance: " + newBalance);
        } else {
            System.out.println("Access denied: You do not have permission to deposit money.");
        }
    }
    
    public void transferMoney(String fromAccountNumber, String toAccountNumber, double amount){
        Scanner scanner = new Scanner(System.in);

        if (currentStaff.hasAccess(Staff.StaffRole.MANAGER) || currentStaff.hasAccess(Staff.StaffRole.TELLER)) {
            System.out.println("Enter the account number to transfer from: ");
            int fromAccountNumberInput = scanner.nextInt();
            scanner.nextLine();

            BankAccount fromBankAccount = bankAccountDAO.getBankAccountById(fromAccountNumberInput);
            if (fromBankAccount == null) {
                System.out.println("Account with ID " + fromAccountNumber + " not found.");
                return;
            }

            System.out.println("Enter the account number to transfer to: ");
            int toAccountNumberInput = scanner.nextInt();
            scanner.nextLine();

            BankAccount toBankAccount = bankAccountDAO.getBankAccountById(toAccountNumberInput);
            if (toBankAccount == null) {
                System.out.println("Account with ID " + toAccountNumber + " not found.");
                return;
            }

            System.out.println("Enter the amount to transfer: ");
            double transferAmount = scanner.nextDouble();
            scanner.nextLine();

            if (transferAmount <= 0) {
                System.out.println("Invalid amount. Transfer amount must be greater than zero.");
                return;
            }

            double fromAccountBalance = fromBankAccount.getBalance();
            if (fromAccountBalance < transferAmount) {
                System.out.println("Insufficient funds. Transfer amount exceeds account balance.");
                return;
            }

            double newFromAccountBalance = fromAccountBalance - transferAmount;
            fromBankAccount.setBalance(newFromAccountBalance);

            double toAccountBalance = toBankAccount.getBalance();
            double newToAccountBalance = toAccountBalance + transferAmount;
            toBankAccount.setBalance(newToAccountBalance);

            Transaction transferTransaction = new Transaction(fromBankAccount, "TRANSFER", transferAmount, "Completed");
            String transactionId = transactionDAO.generateTransactionID();
            transferTransaction.setTransactionID(transactionId);

            transactionDAO.updateAccountBalance(fromBankAccount, transferTransaction);
            transactionDAO.updateAccountBalance(toBankAccount, transferTransaction);
            transactionDAO.saveTransaction(transferTransaction);

            System.out.println("Transfer successful. New balance for account " + fromAccountNumber + ": " + newFromAccountBalance);
            System.out.println("New balance for account " + toAccountNumber + ": " + newToAccountBalance);
        } else {
            System.out.println("Access denied: You do not have permission to transfer money.");
        }
    }
}