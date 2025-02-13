package Interfaces;
public interface Management {
    void viewCustomerDetails();  // Both Admin & Staff
    void viewSpecificCustomerDetails(int ID);  // Both Admin & Staff
    void viewAllCustomers();  // Both Admin & Staff
    void updateCustomerAccount();  // Both Admin & Staff
    void viewAccount();  // Both Admin & Staff
    void viewAllAccounts();  // Both Admin & Staff
    void freezeAccount(int accountId);  // Both Admin & Staff
    void unfreezeAccount();

    // Loan handling
    boolean approveSmallLoan(int loanId);  // Admin & Staff
    boolean rejectLoan(int loanId);  // Admin & Staff
    void viewAllLoans();  // Both Admin & Staff
    void viewAllRequests();  // Both Admin & Staff
}



