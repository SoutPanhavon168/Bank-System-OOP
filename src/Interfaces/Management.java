package Interfaces;
public interface Management {
    //customer management
    void viewCustomerDetails();
    void viewSpecificCustomerDetails(int ID);
    void viewAllCustomers();
    void updateCustomerAccount();

    //bank account management
    void createBankAccount();
    void deleteBankAccount(int accountId);
    void viewbankAccounts();
    void viewSpecificbankAccount(int accountId);
    void viewAllbankAccounts();
    void freezeAccount(int accountId);
    void unfreezeAccount(int accountId);

    //transaction
    void viewAllTransactions();
    void viewSpecificTransaction(String transactionId);
    void refundTransaction(String transactionId);
    void depositMoney(int accountNumber, double amount);
    void transferMoney(String fromAccountNumber, String toAccountNumber, double amount);

}

