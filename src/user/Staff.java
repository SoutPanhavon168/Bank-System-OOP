package user;

import Interfaces.Management;
import bankaccount.BankAccount;
import database.BankAccountDAO;
import database.CustomerDAO;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Staff extends User implements Management {
    
    // Additional attributes for Staff
    protected int staffId;
    protected String role;
    
    public Staff(String lastName, String firstName, String email, String password, String confirmPassword, 
                 String phoneNumber, LocalDate birthDate, String governmentId, int staffId, String role) {
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

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFullName(){
        return this.getFirstName() + " " + this.getLastName();
    }

    private boolean hasAccess(String requiredRole){
        return this.role.equals(requiredRole);
    }

    @Override
    public void viewCustomerDetails() {
        Scanner scanner = new Scanner(System.in);

        if (!hasAccess("Manager") && !hasAccess("Customer Service")) {
            System.out.println("You do not have access to view customer details.");
            scanner.close();
            return;
        }

        System.out.println("Would you like to: \n1. View all customers \n2. Search for a customer");
        System.out.println("Enter your choice (1 or 2): ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if(choice == 1){
            System.out.println("All customers:");
            viewAllCustomers();
        }
        else if(choice == 2){
            System.out.println("Enter the customer's ID: ");
            int ID = scanner.nextInt();
            scanner.nextLine();
            viewSpecificCustomerDetails(ID);
        }
        else{
            System.out.println("Invalid choice. Please enter 1 or 2.");
        } 
        
        scanner.close();
    }

    @Override
    public void updateCustomerAccount() {
        if(hasAccess("Manager") && hasAccess("Customer Service")){
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

        if (hasAccess("Manager") || hasAccess("Loan Officer")) {
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
    if (hasAccess("Manager") || hasAccess("Loan Officer")) {
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
        if (hasAccess("Manager") || hasAccess("Loan Officer")) {
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
        if (hasAccess("Manager")) {
            // Logic to freeze a bank account based on account ID
            System.out.println("Freezing Bank Account ID " + accountId);
            // Call the relevant logic to freeze the account
        } else {
            System.out.println("Access denied: Your role does not have permission to freeze bank accounts.");
        }
    }

    @Override
    public void unfreezeAccount(int accountId) {
        if (hasAccess("Manager")) {
            // Logic to unfreeze a bank account based on account ID
            System.out.println("Unfreezing Bank Account ID " + accountId);
            // Call the relevant logic to unfreeze the account
        } else {
            System.out.println("Access denied: Your role does not have permission to unfreeze bank accounts.");
        }
    }

    @Override
    public boolean approveSmallLoan(int loanId) {
        if (hasAccess("Manager") || hasAccess("Loan Officer")) {
            // Logic to approve small loans
            System.out.println("Approving small loan with Loan ID " + loanId);
            // Approve loan logic here
            return true;
        } else {
            System.out.println("Access denied: Your role does not have permission to approve small loans.");
            return false;
        }
    }

    @Override
    public boolean rejectLoan(int loanId) {
        if (hasAccess("Manager") || hasAccess("Loan Officer")) {
            // Logic to reject loans
            System.out.println("Rejecting loan with Loan ID " + loanId);
            // Reject loan logic here
            return true;
        } else {
            System.out.println("Access denied: Your role does not have permission to reject loans.");
            return false;
        }
    }

    @Override
    public void viewAllLoans() {
        if (hasAccess("Manager") || hasAccess("Loan Officer")) {
            // Logic to view all loans
            System.out.println("Viewing all loans:");
            // Fetch and display all loans from the database
        } else {
            System.out.println("Access denied: Your role does not have permission to view loans.");
        }
    }

    @Override
    public void viewAllRequests() {
        if (hasAccess("Manager")) {
            // Logic to view all requests (e.g., loan applications, account requests)
            System.out.println("Viewing all requests:");
            // Fetch and display all requests from the database
        } else {
            System.out.println("Access denied: Your role does not have permission to view requests.");
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createBankAccount'");
    }

    @Override
    public void deleteBankAccount(int accountId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteBankAccount'");
    }


}