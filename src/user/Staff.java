package user;

import Interfaces.Management;
import bankaccount.BankAccount;
import database.BankAccountDAO;
import database.CustomerDAO;
import database.TransactionDAO;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import transaction.Transaction;

public class Staff extends User implements Management {
    
    // Additional attributes for Staff
    protected int staffId;
    protected StaffRole role;

    public enum StaffRole {
        MANAGER,
        LOAN_OFFICER,
        CUSTOMER_SERVICE,
        TELLER
    }
    
    public Staff(String lastName, String firstName, String email, String password, String confirmPassword, 
    String phoneNumber, LocalDate birthDate, String governmentId, StaffRole role) {
        super(lastName, firstName, email, password, confirmPassword, phoneNumber, birthDate, governmentId);
        this.staffId = staffId;
        this.role = role;
    }
    public Staff(){
        
    }

    // Getter and Setter methods for new attributes
    public int getStaffId() {
        return staffId;
    }

    public String getPassword(){
        return password;
    }
    public String getPosition(){
        return role.toString();
    }
    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public String getGovernmentId(){return governmentId;}

    public StaffRole getRole() {
        return role;
    }

    public void setRole(StaffRole role) {
        this.role = role;
    }

    public String getFullName(){
        return this.getFirstName() + " " + this.getLastName();
    }

    public LocalDate getBirthDate(){return birthDate;}

    private boolean hasAccess(StaffRole role){
        return this.role == role;
    }

    @Override
    public void viewCustomerDetails() {
        Scanner scanner = new Scanner(System.in);

        if ((!hasAccess(StaffRole.MANAGER)) && !hasAccess(StaffRole.CUSTOMER_SERVICE)){
            System.out.println("You do not have access to view customer details.");
            scanner.close();
            return;
        }
        int choice;
        do {
            System.out.println("Would you like to: \n1. View all customers \n2. Search for a specific customer");
            System.out.print("Enter your choice (1 or 2): ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number (1 or 2).");
                scanner.next();
            }
            choice = scanner.nextInt();
            scanner.nextLine();
            
            if (choice == 1) {
                viewAllCustomers();
            } else if (choice == 2) {
                System.out.print("Enter customer ID: ");
                while (!scanner.hasNextInt()) {
                    System.out.println("Invalid input. Please enter a valid customer ID.");
                    scanner.next();
                }
                int customerId = scanner.nextInt();
                scanner.nextLine();
                viewSpecificCustomerDetails(customerId);
            } else {
                System.out.println("Invalid choice. Please enter 1 or 2.");
            }
        } while (choice != 1 && choice != 2);
    }


    @Override
    public void updateCustomerAccount() {
        if(hasAccess(StaffRole.MANAGER) && hasAccess(StaffRole.CUSTOMER_SERVICE)){
            System.out.println("You do not have access to update customer accounts.");
            return;
        }
        CustomerDAO customerDAO = new CustomerDAO();

        // Logic for updating customer account
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the customer's ID: ");
        int customerId = scanner.nextInt();
        scanner.nextLine();

        Customer customer = customerDAO.getCustomerById(customerId);

        if (customer == null){
            System.out.println("Customer not found");
            scanner.close();
            return;
        }

        System.out.println("What would you like to update?");
        System.out.println("1. Email");
        System.out.println("2. Phone Number");
        System.out.println("3. Password");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch(choice){
            case 1:
            System.out.println("Enter the new email: ");
            String newEmail = scanner.nextLine();
            customer.setEmail(newEmail);
            break;
            case 2:
            System.out.println("Enter the new phone number: ");
            String newPhoneNumber = scanner.nextLine();
            customer.setPhoneNumber(newPhoneNumber);
            break;
            case 3:
            System.out.println("Enter the new password: ");
            String newPassword = scanner.nextLine();
            customer.setPassword(newPassword);
            customerDAO.updatePasswordInDatabase(customerId,newPassword);
            if(newPassword.equals(customer.getPassword())){
                System.out.println("Invalid password. Please enter a different password.");
                newPassword = scanner.nextLine();
            }else{
                System.out.println("Password updated successfully.");
            }
            break;
            default:
            System.out.println("Invalid choice. Please enter 1, 2, or 3.");
        }

        customerDAO.updateCustomer(customer);

        System.out.println("Customer details updated successfully.");

        scanner.close();
    }

    @Override
    public void viewSpecificCustomerDetails(int customerId) {
        // Logic to view a specific customer's details based on their ID
        CustomerDAO customerDAO = new CustomerDAO();
        Customer customer = customerDAO.getCustomerById(userId);

        if(customer == null){
            System.out.println("Customer with ID: " + userId + "not found.");
            return;
        }

        System.out.println("Customer's " + userId + " details: ");
        System.out.println("Full Name: " + customer.getFullName());
        System.out.println("Email: " + customer.getEmail());
        System.out.println("Phone Number: " + customer.getPhoneNumber());
        System.out.println("Password: " + customer.getPassword());
        System.out.println("Birth Date: " + customer.getBirthDate());
        System.out.println("Government ID: " + customer.getMaskedGovernmentId());
    }

    @Override
    public void viewAllCustomers() {
        // Logic to view all customers' details
        List<Customer> customers = CustomerDAO.getAllCustomers();

        if(customers.isEmpty()){
            System.out.println("No customers found.");
            return;
        }

        System.out.println("All customers: ");
        for(Customer customer : customers){
            System.out.println("Full name: " + customer.getFullName());
            System.out.println("Email: " + customer.getEmail());
            System.out.println("Phone Number: " + customer.getPhoneNumber());
            System.out.println("Birth Date: " + customer.getBirthDate());
            System.out.println("Government ID: " + customer.getMaskedGovernmentId());
            System.out.println("-------------------------------");
        }

    }

   @Override
    public void viewbankAccounts() {
        Scanner scanner = new Scanner(System.in);

        if (hasAccess(StaffRole.MANAGER) || hasAccess(StaffRole.LOAN_OFFICER)) {
            System.out.println("Would you like to: \n1. View all bank accounts \n2. Search for a bank account");
            System.out.println("Enter your choice (1 or 2): ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.println("All bank accounts:");
                viewAllbankAccounts();
            } else if (choice == 2) {
                System.out.println("Enter the bank account ID: ");
                int accountId = scanner.nextInt();
                scanner.nextLine();
                viewSpecificbankAccount(accountId);
            } else {
                System.out.println("Invalid choice. Please enter 1 or 2.");
            }
        } else {
            System.out.println("Access denied: Your role does not have permission to view bank accounts.");
        }

        scanner.close();
    }

    @Override
    public void viewSpecificbankAccount(int accountId) {
    // Check if the user has the appropriate access
    if (hasAccess(StaffRole.MANAGER) || hasAccess(StaffRole.LOAN_OFFICER)) {
        // Fetch the bank account details from the database using a DAO method
        BankAccountDAO bankAccountDAO = new BankAccountDAO();
        BankAccount bankAccount = bankAccountDAO.getBankAccountById(accountId);

        if (bankAccount == null) {
            System.out.println("Bank account with ID " + accountId + " not found.");
            return;
        }
        System.out.println(bankAccount.toString());
        
        
        //System.out.println("Creation Date: " + bankAccount.getCreationDate());
        
        // Optionally display other relevant bank account information here
    } else {
        // If the user does not have the required access, deny access
        System.out.println("Access denied: Your role does not have permission to view bank account details.");
    }
}


    @Override
    public void viewAllbankAccounts() {
        BankAccountDAO bankAccountDAO = new BankAccountDAO();
        if (hasAccess(StaffRole.MANAGER) || hasAccess(StaffRole.LOAN_OFFICER)) {
            // Fetch and print all bank accounts' details from the BankAccountDAO
            System.out.println("All bank accounts:");

            // Retrieve all bank accounts from the database using BankAccountDAO
            List <BankAccount> bankAccounts = bankAccountDAO.getAllBankAccounts();

            if (bankAccounts.isEmpty()) {
                System.out.println("No bank accounts found.");
                return;
            }

            // Iterate through the list and display details for each bank account
            for (BankAccount bankAccount : bankAccounts) {
                System.out.println(bankAccount.toString()); // This will call the toString() method of BankAccount
            }

        } else {
            System.out.println("Access denied: Your role does not have permission to view bank accounts.");
        }
    }

    @Override
    public void freezeAccount(int accountId) {
        if (hasAccess(StaffRole.MANAGER)){
            // Logic to freeze a bank account based on account ID
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the account ID you want to freeze: ");
            int bankAccountId = scanner.nextInt();
            scanner.nextLine();

            BankAccountDAO bankAccountDAO = new BankAccountDAO();
            boolean success = bankAccountDAO.freezeBankAccount(bankAccountId);
            if (success) {
                System.out.println("Bank Account ID " + accountId + " has been frozen.");
            } else {
                System.out.println("Failed to freeze Bank Account ID " + bankAccountId);
            }
        } else {
            System.out.println("Access denied: Your role does not have permission to freeze bank accounts.");
        }
    }

    @Override
    public void unfreezeAccount(int accountId) {
        if (hasAccess(StaffRole.MANAGER)){
            // Logic to unfreeze a bank account based on account ID
            System.out.println("Enter the account ID you want to unfreeze: ");
            Scanner scanner = new Scanner(System.in);
            int bankAccountId = scanner.nextInt();
            scanner.nextLine();

            BankAccountDAO bankAccountDAO = new BankAccountDAO();
            boolean success = bankAccountDAO.unfreezeBankAccount(bankAccountId);
            if (success) {
                System.out.println("Bank Account ID " + bankAccountId + " has been unfrozen.");
            } else {
                System.out.println("Failed to unfreeze Bank Account ID " + bankAccountId);
            }
        } else {
            System.out.println("Access denied: Your role does not have permission to unfreeze bank accounts.");
        }
    }

   
    // Additional Staff-specific methods
    public void viewStaffDetails() {
        // Display staff details (ID, role)
        System.out.println("Staff ID: " + staffId);
        System.out.println("Role: " + role);
        System.out.println("Full Name: " + getFullName());
        System.out.println("Email: " + getEmail());
    }

    @Override
    public void createBankAccount() {
        if (hasAccess(StaffRole.MANAGER) || hasAccess(StaffRole.CUSTOMER_SERVICE)) {
            
        } else {
            System.out.println("Access denied: Your role does not have permission to create bank account.");
        }
        throw new UnsupportedOperationException("Unimplemented method 'createBankAccount'");
    }

    @Override
    public void deleteBankAccount(int accountId) {
        if(hasAccess(StaffRole.MANAGER)){
            // Logic to delete bank account
        }
        else{
            System.out.println("Access denied: You do not have permission to delete bank accounts.");
        }
        throw new UnsupportedOperationException("Unimplemented method 'deleteBankAccount'");
    }

     //transaction
     @Override
    public void viewAllTransactions(){
        TransactionDAO transactionDAO = new TransactionDAO();

        if(hasAccess(StaffRole.MANAGER) || hasAccess(StaffRole.TELLER)){

            List<Transaction> transactions = transactionDAO.getAllTransactions();

            if (transactions.isEmpty()) {
                System.out.println("No transactions found.");
                return;
            }

            System.out.println("All transactions:");
            for (Transaction transaction : transactions) {
                System.out.println(transaction.toString());
            }

        }
        else{
            System.out.println("Access denied: Your role does not have permission to view transactions.");
        }
    }

    @Override
    public void viewSpecificTransaction(String transactionId){
        // TODO Auto-generated method stub
        Scanner scanner = new Scanner(System.in);
        TransactionDAO transactionDAO = new TransactionDAO();
        if(hasAccess(StaffRole.MANAGER) || hasAccess(StaffRole.TELLER)){

            System.out.println("Enter the transaction ID: ");
            String transactionID = scanner.nextLine();

            Transaction transaction = transactionDAO.getTransactionById(transactionID);
            if (transaction == null) {
                System.out.println("Transaction with ID " + transactionID + " not found.");
                return;
            }

            System.out.println(transaction.toString());
        }
        else{
            System.out.println("Access denied: Your role does not have permission to view transactions.");
        }
       
    }

    @Override
    public void refundTransaction(String transactionId){
        Scanner scanner = new Scanner(System.in);
        TransactionDAO transactionDAO = new TransactionDAO();
    
        if (hasAccess(StaffRole.MANAGER) || hasAccess(StaffRole.TELLER)) {
            // Retrieve the transaction
            Transaction transaction = transactionDAO.getTransactionById(transactionId);
            if (transaction == null) {
                System.out.println("Transaction with ID " + transactionId + " not found.");
                return;
            }
    
            // Check if a reverse transaction already exists
            if (transactionDAO.isRefunded(transactionId)) {
                System.out.println("Transaction has already been refunded.");
                return;
            }
    


        } else {
            System.out.println("Access denied: You do not have permission to process refunds.");
        } 
    }  

    @Override
    public void depositMoney(int accountNumber, double amount){

        TransactionDAO transactionDAO = new TransactionDAO();
        BankAccountDAO bankAccountDAO = new BankAccountDAO();
        Scanner scanner = new Scanner(System.in);
        if (hasAccess(StaffRole.MANAGER) || hasAccess(StaffRole.TELLER)) {

            System.out.println("Enter the account number: ");
            int accountNumberInput = scanner.nextInt();
            scanner.nextLine();

            BankAccount bankAccount = bankAccountDAO.getBankAccountById(accountNumberInput);
            if (bankAccount == null) {
                System.out.println("Account with ID " + accountNumber + " not found.");
                return;
            }

            System.out.println("Enter the amount to deposit: ");
            double depositAmount = scanner.nextDouble();
            scanner.nextLine();

            if (depositAmount <= 0) {
                System.out.println("Invalid amount. Deposit amount must be greater than zero.");
                return;
            }

            double currentBalance = bankAccount.getBalance();
            double newBalance = currentBalance + depositAmount;

            bankAccount.setBalance(newBalance);
            
            Transaction depositTransaction = new Transaction(bankAccount, "DEPOSIT", depositAmount, "Completed");
            String transactionId = transactionDAO.generateTransactionID();
            depositTransaction.setTransactionID(transactionId);

            transactionDAO.updateAccountBalance(bankAccount, depositTransaction);

            transactionDAO.saveTransaction(depositTransaction);

            System.out.println("Deposit successful. New balance: " + newBalance);
        }  

        else {
            System.out.println("Access denied: You do not have permission to deposit money.");
        }
        
    } 

    @Override 
    public void transferMoney(String fromAccountNumber, String toAccountNumber, double amount){
        TransactionDAO transactionDAO = new TransactionDAO();
        BankAccountDAO bankAccountDAO = new BankAccountDAO();
        Scanner scanner = new Scanner(System.in);

        if (hasAccess(StaffRole.MANAGER) || hasAccess(StaffRole.TELLER)) {
            System.out.println("Enter the account number to transfer from: ");
            int fromAccountNumberInput = scanner.nextInt();
            scanner.nextLine();

            BankAccount fromBankAccount = bankAccountDAO.getBankAccountById(fromAccountNumberInput);
            if (fromBankAccount == null) {
                System.out.println("Account with ID " + fromAccountNumber + " not found.");
                return;
            }

            System.out.println("Enter the account number to transfer to: ");
            int toAccountNumberInput = scanner.nextInt();
            scanner.nextLine();

            BankAccount toBankAccount = bankAccountDAO.getBankAccountById(toAccountNumberInput);
            if (toBankAccount == null) {
                System.out.println("Account with ID " + toAccountNumber + " not found.");
                return;
            }

            System.out.println("Enter the amount to transfer: ");
            double transferAmount = scanner.nextDouble();
            scanner.nextLine();

            if (transferAmount <= 0) {
                System.out.println("Invalid amount. Transfer amount must be greater than zero.");
                return;
            }

            double fromAccountBalance = fromBankAccount.getBalance();
            if (fromAccountBalance < transferAmount) {
                System.out.println("Insufficient funds. Transfer amount exceeds account balance.");
                return;
            }

            double newFromAccountBalance = fromAccountBalance - transferAmount;
            fromBankAccount.setBalance(newFromAccountBalance);

            double toAccountBalance = toBankAccount.getBalance();
            double newToAccountBalance = toAccountBalance + transferAmount;
            toBankAccount.setBalance(newToAccountBalance);

            Transaction transferTransaction = new Transaction(fromBankAccount, "TRANSFER", transferAmount, "Completed");
            String transactionId = transactionDAO.generateTransactionID();
            transferTransaction.setTransactionID(transactionId);

            transactionDAO.updateAccountBalance(fromBankAccount, transferTransaction);
            transactionDAO.updateAccountBalance(toBankAccount, transferTransaction);

            transactionDAO.saveTransaction(transferTransaction);

            System.out.println("Transfer successful. New balance for account " + fromAccountNumber + ": " + newFromAccountBalance);
            System.out.println("New balance for account " + toAccountNumber + ": " + newToAccountBalance);
    }
    else {
        System.out.println("Access denied: You do not have permission to transfer money.");
    }
    }
}