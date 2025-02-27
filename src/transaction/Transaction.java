package transaction;

import bankAccount.BankAccount;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Transaction {

    private String transactionID;
    private String transactionType;
    private double amount;
    private LocalDateTime dateTime;
    private String status;
    private BankAccount bankAccount;
    ArrayList<Transaction> transactions = new ArrayList<>();

    // to be implemented:
    // public void viewTransactionHistory();
    // public void viewSpecificTransaction(); (By Date)
    // public void viewAllTransactions();
    // public void viewTransactionByType(String transactionType);
    // public void sortTransactionsByDate();
    // public void sortTransactionsByAmount();

    public Transaction(BankAccount bankAccount, String transactionType, double amount, String status) {
        this(transactionType, amount, status);
        this.bankAccount = bankAccount;
    }
    public Transaction(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public Transaction(String transactionType, double amount, String status) {
        this.transactionType = transactionType;
        this.amount = amount;
        this.status = status;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    //for viewing history
    public String getTransactionType() {
        return transactionType;
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

    public void setStatus(String status) {
        this.status = status;
    }

    public void deposit(ArrayList<BankAccount> bankAccounts) {
        Scanner input = new Scanner(System.in);
    
        if (bankAccounts.isEmpty()) {
            System.out.println("No bank accounts available.");
            return;
        }
    
        try {
            System.out.println("Select an account to deposit into:");
            for (int i = 0; i < bankAccounts.size(); i++) {
                System.out.println((i + 1) + ". " + bankAccounts.get(i).getAccountNumber());
            }
    
            System.out.print("Enter the number of the account: ");
            int index = input.nextInt() - 1;
    
            if (index < 0 || index >= bankAccounts.size()) {
                throw new IndexOutOfBoundsException("Invalid selection.");
            }
    
            BankAccount selectedAccount = bankAccounts.get(index);
    
            System.out.print("Enter amount to deposit ($): ");
            double depositAmount = input.nextDouble();
    
            if (depositAmount <= 0) {
                throw new IllegalArgumentException("Invalid deposit amount.");
            }
    
            selectedAccount.setBalance(selectedAccount.getBalance() + depositAmount);
            System.out.println("Deposit successful. New balance: $" + selectedAccount.getBalance());
    
            Transaction transaction = new Transaction(selectedAccount, "Deposit", depositAmount, "Completed");
            System.out.println(transaction.toString());
        
        } catch (InputMismatchException e) /* <- this catches the try block (if it throws and exception) */ {
            System.out.println("Invalid input. Please enter numbers only.");
            // IndexOutOfBoundsException == access an array that is out of bound (accessing an out of bound index)
            // IllegalArgumentException == if u put number 0 or negative
        } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) { // <- catches any exception
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }
    
    public class InsufficientFundsException extends Exception {
        public InsufficientFundsException(String message) {
            super(message);
        }
    }
    
    
    public void withdraw(ArrayList<BankAccount> bankAccounts) {
        Scanner input = new Scanner(System.in);
    
        if (bankAccounts.isEmpty()) {
            System.out.println("No bank accounts available.");
            return;
        }
    
        try {
            System.out.println("Select an account to withdraw from:");
            for (int i = 0; i < bankAccounts.size(); i++) {
                System.out.println((i + 1) + ". " + bankAccounts.get(i).getAccountNumber());
            }
    
            System.out.print("Enter the number of the account: ");
            int index = input.nextInt() - 1;
    
            if (index < 0 || index >= bankAccounts.size()) {
                throw new IndexOutOfBoundsException("Invalid selection.");
            }
    
            BankAccount selectedAccount = bankAccounts.get(index);
    
            System.out.print("Enter amount to withdraw ($): ");
            double withdrawAmount = input.nextDouble();
    
            if (withdrawAmount <= 0) {
                throw new IllegalArgumentException("Withdrawal amount must be greater than zero.");
            }
    
            if (selectedAccount.getBalance() < withdrawAmount) {
                throw new InsufficientFundsException("Insufficient balance for withdrawal.");
            }
    
            selectedAccount.setBalance(selectedAccount.getBalance() - withdrawAmount);
            System.out.println("Withdrawal successful. New balance: $" + selectedAccount.getBalance());
    
            Transaction transaction = new Transaction(selectedAccount, "Withdraw", withdrawAmount, "Completed");
            System.out.println(transaction.toString());
    
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter numbers only.");
        } catch (IndexOutOfBoundsException | IllegalArgumentException | InsufficientFundsException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }
    
    
    

    public void transfer(ArrayList<BankAccount> bankAccounts) {
        Scanner input = new Scanner(System.in);
        
        try {
            System.out.println("Choose transfer type:");
            System.out.println("1. Transfer between my own accounts");
            System.out.println("2. Transfer to another person's account");
            System.out.print("Enter your choice: ");
            int transferType = input.nextInt();
    
            BankAccount sender = null;
            BankAccount recipient = null;
    
            System.out.println("Select your account to transfer from:");
            for (int i = 0; i < bankAccounts.size(); i++) {
                System.out.println((i + 1) + ". " + bankAccounts.get(i).getAccountNumber());
            }
    
            System.out.print("Enter the number of your account: ");
            int senderIndex = input.nextInt() - 1;
    
            if (senderIndex < 0 || senderIndex >= bankAccounts.size()) {
                throw new IndexOutOfBoundsException("Invalid selection.");
            }
    
            sender = bankAccounts.get(senderIndex);
    
            if (transferType == 1) {
                System.out.println("Select the account to transfer to:");
                for (int i = 0; i < bankAccounts.size(); i++) {
                    if (i != senderIndex) {
                        System.out.println((i + 1) + ". " + bankAccounts.get(i).getAccountNumber());
                    }
                }
    
                System.out.print("Enter the number of the recipient's account: ");
                int recipientIndex = input.nextInt() - 1;
    
                if (recipientIndex < 0 || recipientIndex >= bankAccounts.size() || recipientIndex == senderIndex) {
                    throw new IndexOutOfBoundsException("Invalid selection.");
                }
    
                recipient = bankAccounts.get(recipientIndex);
    
            } else if (transferType == 2) {
                System.out.print("Enter the recipient's account number: ");
                int recipientAccountNumber = input.nextInt();
    
                for (BankAccount account : bankAccounts) {
                    if (account.getAccountNumber() == recipientAccountNumber) {
                        recipient = account;
                        break;
                    }
                }
    
                if (recipient == null) {
                    throw new IllegalArgumentException("Recipient account not found.");
                }
            } else {
                throw new IllegalArgumentException("Invalid transfer type.");
            }
    
            System.out.print("Enter amount to transfer ($): ");
            double transferAmount = input.nextDouble();
    
            if (transferAmount <= 0) {
                throw new IllegalArgumentException("Transfer amount must be greater than zero.");
            }
    
            if (sender.getBalance() < transferAmount) {
                throw new InsufficientFundsException("Insufficient balance for transfer.");
            }
    
            sender.setBalance(sender.getBalance() - transferAmount);
            recipient.setBalance(recipient.getBalance() + transferAmount);
    
            System.out.println("Transfer successful!");
            System.out.println("New balance for sender: $" + sender.getBalance());
    
            Transaction transaction = new Transaction(sender, "Transfer", transferAmount, "Completed");
            System.out.println(transaction.toString());
    
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter numbers only.");
        } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (InsufficientFundsException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }
    
    
    

    public void viewTransactionHistory(){
        // view all transaction history
    }
    public void viewSpecificTransaction(String transactionID){
        // view specific transaction by id
        // if id == id, print transaction details
        // else print "Transaction not found"
    } 
    public void viewTransactionByType(String transactionType){
        // view transaction by type
        // if type == type, print transaction details
        // else print "Transaction not found"
    }
    public void sortTransactionsByDate(){
        // sort transactions by date
        // print sorted transactions
        // if transactions.isEmpty(), print "No transactions found"
        // else print sorted transactions
    }
    public void sortTransactionsByAmount(){
        // sort transactions by amount
        // print sorted transactions
        // if transactions.isEmpty(), print "No transactions found"
        // else print sorted transactions
    }

    public void checkBalance() {
        System.out.println("Your balance is: " + bankAccount.getBalance());
    }


    public String generateTransactionID() {
        return transactionID = "T" + (int) (Math.random() * 1000);
    }

    @Override
    public String toString() {
        return "Transaction ID: " + generateTransactionID() + "\n"
                + "Transaction Type: " + transactionType + "\n"
                + "Amount: " + amount + "\n"
                + "Date and Time: " + getDateTime() + "\n"
                + "Status: " + status + "\n";
    }

    public void printTransactionDetails() {
        // Generate a transaction ID (if it's not set already)    
        // Create the Transaction object (you may need to store this or pass it around as needed)
        Transaction transaction = new Transaction(transactionType, amount, status);

        // Print the transaction details
        System.out.println(transaction.toString());
    }
    }
