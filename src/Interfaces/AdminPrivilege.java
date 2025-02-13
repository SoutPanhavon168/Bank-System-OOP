package Interfaces;

public interface AdminPrivilege extends Management {
    void addAccount();  // Only Admin
    void removeAccount();  // Only Admin
    boolean approveLargeLoan(int loanId);  // Only Admin
    void viewAllTransactions();  // Only Admin
    void viewAllPayments();  // Only Admin
}
