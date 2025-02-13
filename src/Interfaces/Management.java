package Interfaces;
public interface Management {
    //customer management
    void viewCustomerDetails();  // Both Admin & Staff
    void viewSpecificCustomerDetails(int ID);  // Both Admin & Staff
    void viewAllCustomers();  // Both Admin & Staff
    void updateCustomerAccount();  // Both Admin & Staff

    //bank account management
    void viewbankAccounts();  // Both Admin & Staff
    void viewSpecificbankAccount(int accountId);  // Both Admin & Staff
    void viewAllbankAccounts();  // Both Admin & Staff
    void freezeAccount(int accountId);  // Both Admin & Staff
    void unfreezeAccount(int accountId);

    // Loan handling
    boolean approveSmallLoan(int loanId);  // Admin & Staff
    boolean rejectLoan(int loanId);  // Admin & Staff
    void viewAllLoans();  // Both Admin & Staff
    void viewAllRequests();  // Both Admin & Staff
}



