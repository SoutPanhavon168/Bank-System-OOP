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
    void viewSpecificTransaction(String transactionId);
    void refundTransaction(String transactionId);  // Admin & Staff
    void depositMoney(int accountNumber, double amount);  // Admin & Staff
    void transferMoney(String fromAccountNumber, String toAccountNumber, double amount);  // Admin & Staff

}

