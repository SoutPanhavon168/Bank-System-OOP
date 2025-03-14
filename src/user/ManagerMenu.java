package user;

import java.util.Scanner;

public class ManagerMenu {
    
    private Staff staff;
    private Scanner scanner;
    
    public ManagerMenu(Staff staff) {
        this.staff = staff;
        this.scanner = new Scanner(System.in);
    }
    
    public void displayMenu() {
        int choice;
        boolean exit = false;
        
        while (!exit) {
            System.out.println("\n===== MANAGER MENU =====");
            System.out.println("1. View Customer Details");
            System.out.println("2. Update Customer Account");
            System.out.println("3. View Bank Accounts");
            System.out.println("4. Freeze Account");
            System.out.println("5. Unfreeze Account");
            System.out.println("6. Create Bank Account");
            System.out.println("7. Delete Bank Account");
            System.out.println("8. View All Transactions");
            System.out.println("9. View Specific Transaction");
            System.out.println("10. Refund Transaction");
            System.out.println("11. Deposit Money");
            System.out.println("12. Withdraw Money");
            System.out.println("13. Transfer Money");
            System.out.println("14. View Staff Details");
            System.out.println("0. Logout");
            System.out.print("Enter your choice: ");
            
            choice = getValidIntInput();
            
            switch (choice) {
                case 1:
                    staff.viewCustomerDetails();
                    break;
                case 2:
                    staff.updateCustomerAccount();
                    break;
                case 3:
                    staff.viewbankAccounts();
                    break;
                case 4:
                    System.out.print("Enter account ID to freeze: ");
                    int freezeId = getValidIntInput();
                    staff.freezeAccount(freezeId);
                    break;
                case 5:
                    System.out.print("Enter account ID to unfreeze: ");
                    int unfreezeId = getValidIntInput();
                    staff.unfreezeAccount(unfreezeId);
                    break;
                case 6:
                    staff.createBankAccount();
                    break;
                case 7:
                    System.out.print("Enter account ID to delete: ");
                    int deleteId = getValidIntInput();
                    staff.deleteBankAccount(deleteId);
                    break;
                case 8:
                    staff.viewAllTransactions();
                    break;
                case 9:
                    System.out.print("Enter transaction ID to view: ");
                    int transactionId = getValidIntInput();
                    staff.viewSpecificTransaction(transactionId);
                    break;
                case 10:
                    System.out.print("Enter transaction ID to refund: ");
                    int refundId = getValidIntInput();
                    staff.refundTransaction(refundId);
                    break;
                case 11:
                    handleDeposit();
                    break;
                case 12:
                    handleWithdrawal();
                    break;
                case 13:
                    handleTransfer();
                    break;
                case 14:
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