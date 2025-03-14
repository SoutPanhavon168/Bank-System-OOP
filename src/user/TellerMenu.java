package user;

import java.util.Scanner;

public class TellerMenu {
    
    private Staff staff;
    private Scanner scanner;
    
    public TellerMenu(Staff staff) {
        this.staff = staff;
        this.scanner = new Scanner(System.in);
    }
    
    public void displayMenu() {
        int choice;
        boolean exit = false;
        
        while (!exit) {
            System.out.println("\n===== TELLER MENU =====");
            System.out.println("1. View Specific Transaction");
            System.out.println("2. View All Transactions");
            System.out.println("3. Deposit Money");
            System.out.println("4. Withdraw Money");
            System.out.println("5. Transfer Money");
            System.out.println("6. View Staff Details");
            System.out.println("0. Logout");
            System.out.print("Enter your choice: ");
            
            choice = getValidIntInput();
            
            switch (choice) {
                case 1:
                    System.out.print("Enter transaction ID to view: ");
                    int transactionId = getValidIntInput();
                    staff.viewSpecificTransaction(transactionId);
                    break;
                case 2:
                    staff.viewAllTransactions();
                    break;
                case 3:
                    handleDeposit();
                    break;
                case 4:
                    handleWithdrawal();
                    break;
                case 5:
                    handleTransfer();
                    break;
                case 6:
                    staff.viewStaffDetails();
                    break;
                case 0:
                    exit = true;
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private void handleDeposit() {
        System.out.print("Enter account ID for deposit: ");
        int accountId = getValidIntInput();
        System.out.print("Enter amount to deposit: ");
        double amount = getValidDoubleInput();
        staff.depositMoney(accountId, amount);
    }
    
    private void handleWithdrawal() {
        System.out.print("Enter account ID for withdrawal: ");
        int accountId = getValidIntInput();
        System.out.print("Enter amount to withdraw: ");
        double amount = getValidDoubleInput();
        staff.withdrawMoney(accountId, amount);
    }
    
    private void handleTransfer() {
        System.out.print("Enter source account ID: ");
        int fromAccountId = getValidIntInput();
        System.out.print("Enter destination account ID: ");
        int toAccountId = getValidIntInput();
        System.out.print("Enter amount to transfer: ");
        double amount = getValidDoubleInput();
        staff.transferMoney(fromAccountId, toAccountId, amount);
    }
    
    private int getValidIntInput() {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next();
        }
        int input = scanner.nextInt();
        scanner.nextLine(); // Clear buffer
        return input;
    }
    
    private double getValidDoubleInput() {
        while (!scanner.hasNextDouble()) {
            System.out.println("Invalid input. Please enter a valid amount.");
            scanner.next();
        }
        double input = scanner.nextDouble();
        scanner.nextLine(); // Clear buffer
        return input;
    }
}