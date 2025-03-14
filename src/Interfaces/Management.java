package Interfaces;
public interface Management {
    //customer management
    void viewCustomerDetails();  // Both Admin & Staff
    void viewSpecificCustomerDetails(int ID);  // Both Admin & Staff
    void viewAllCustomers();  // Both Admin & Staff
    void updateCustomerAccount();  // Both Admin & Staff

    //bank account management
    void createBankAccount();  // Both Admin & Staff
    void deleteBankAccount(int accountId);  // Both Admin & Staff
    void viewbankAccounts();  // Both Admin & Staff
    void viewSpecificbankAccount(int accountId);  // Both Admin & Staff
    void viewAllbankAccounts();  // Both Admin & Staff
    void freezeAccount(int accountId);  // Both Admin & Staff
    void unfreezeAccount(int accountId);

    //transaction
    void viewAllTransactions();
    void viewSpecificTransaction(int transactionId);
    void refundTransaction(int transactionId);  // Admin & Staff
    void depositMoney(int accountId, double amount);  // Admin & Staff
    void withdrawMoney(int accountId, double amount);  // Admin & Staff
    void transferMoney(int fromAccountId, int toAccountId, double amount);  // Admin & Staff

    
}



