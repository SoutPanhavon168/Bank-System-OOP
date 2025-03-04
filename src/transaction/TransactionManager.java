package transaction;
import transaction.Transaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Scanner;

import bankaccount.BankAccount;

public class TransactionManager {

    private ArrayList<Transaction> transactions = new ArrayList<>();

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
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

            System.out.print("Enter amount to deposit ($): ");
            double depositAmount = input.nextDouble();

            TransactionException.validateAmount(depositAmount); // Validate deposit amount

            selectedAccount.setBalance(selectedAccount.getBalance() + depositAmount);
            System.out.println("Deposit successful. New balance: $" + selectedAccount.getBalance());

            Transaction transaction = new Transaction(selectedAccount, "Deposit", depositAmount, "Completed");
            addTransaction(transaction);
            System.out.println(transaction.toString());

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

            System.out.print("Enter amount to withdraw ($): ");
            double withdrawAmount = input.nextDouble();

            TransactionException.validateAmount(withdrawAmount); // Validate withdraw amount
            TransactionException.validateSufficientFunds(selectedAccount, withdrawAmount); // Validate sufficient funds

            selectedAccount.setBalance(selectedAccount.getBalance() - withdrawAmount);
            System.out.println("Withdrawal successful. New balance: $" + selectedAccount.getBalance());

            Transaction transaction = new Transaction(selectedAccount, "Withdraw", withdrawAmount, "Completed");
            addTransaction(transaction);
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

                for (BankAccount account : bankAccounts) {
                    if (account.getAccountNumber() == recipientAccountNumber) {
                        recipient = account;
                        break;
                    }
                }

                TransactionException.validateRecipientAccount(recipient); // Validate recipient existence
            } else {
                throw new TransactionException("Invalid transfer type.");
            }

            System.out.print("Enter amount to transfer ($): ");
            double transferAmount = input.nextDouble();

            TransactionException.validateAmount(transferAmount); // Validate transfer amount
            TransactionException.validateSufficientFunds(sender, transferAmount); // Validate sufficient funds

            sender.setBalance(sender.getBalance() - transferAmount);
            recipient.setBalance(recipient.getBalance() + transferAmount);

            System.out.println("Transfer successful!");
            System.out.println("New balance for sender: $" + sender.getBalance());

            Transaction transaction = new Transaction(sender, "Transfer", transferAmount, "Completed");
            addTransaction(transaction);
            System.out.println(transaction.toString());

        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter numbers only.");
        } catch (TransactionException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    public void viewSpecificTransaction(String transactionID) {
        for (Transaction transaction : transactions) {
            if (transaction.getTransactionID().equals(transactionID)) {
                System.out.println(transaction);
                return;
            }
        }
        System.out.println("Transaction not found.");
    }

    public void viewTransactionHistory() {
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }
        
        System.out.println("===== Transaction History =====");
        for (Transaction transaction : transactions) {
            System.out.println(transaction.toString());  // Assumes you have a proper toString method in Transaction
        }
    }
    
    public void sortTransactionsByAmount() {
        if (transactions.isEmpty()) {
            System.out.println("No transactions found to sort.");
            return;
        }

        // Sort by amount
        Collections.sort(transactions, new Comparator<Transaction>() {
            @Override
            public int compare(Transaction t1, Transaction t2) {
                return Double.compare(t1.getAmount(), t2.getAmount());
            }
        });

        System.out.println("Transactions sorted by amount.");
        viewTransactionHistory();  // Optionally view the transactions after sorting
    }

    

    public double getTotalDepositAmount() {
    double total = 0;
    for (Transaction transaction : transactions) {
        if (transaction.getType() == Transaction.TransactionType.DEPOSIT) {
            total += transaction.getAmount();
        }
    }
    return total;
    }

    public double getTotalWithdrawalAmount() {
    double total = 0;
    for (Transaction transaction : transactions) {
        if (transaction.getType() == Transaction.TransactionType.WITHDRAWAL) {
            total += transaction.getAmount();
        }
    }
    return total;
    }


    public void sortTransactionsByDate() {
    transactions.sort(Comparator.comparing(Transaction::getTransactionDate));
    System.out.println("Transactions sorted by date.");
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
