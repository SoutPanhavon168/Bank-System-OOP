package user;

import Interfaces.Management;
import java.time.LocalDate;
import java.util.Scanner;

public class Staff extends User implements Management {

    // Additional attributes for Staff
    private int staffId;
    private String department;
    private String role;
    
    public Staff(String lastName, String firstName, String email, String password, String confirmPassword, 
                 String phoneNumber, LocalDate birthDate, String governmentId, int staffId, String department, String role) {
        super(lastName, firstName, email, password, confirmPassword, phoneNumber, birthDate, governmentId);
        this.staffId = staffId;
        this.department = department;
        this.role = role;
    }

    // Getter and Setter methods for new attributes
    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    private boolean hasAccess(String requiredDepartment, String requiredRole){
        return this.department.equals(requiredDepartment) && this.role.equals(requiredRole);
    }

    @Override
    public void viewCustomerDetails() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Would you like to: \n1. View all customers \n2. Search for a customer");
        System.out.println("Enter your choice (1 or 2): ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if(choice == 1){
            System.out.println("All customers:");
            // Print all customers' details
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
        // Logic for updating customer account
        System.out.println("Updating customer account...");
    }

    @Override
    public void viewSpecificCustomerDetails(int customerId) {
        // Logic to view a specific customer's details based on their ID
        System.out.println("Customer's " + customerId + " details: ");
        // Fetch customer details from database and print them
    }

    @Override
    public void viewAllCustomers() {
        // Logic to view all customers' details
        System.out.println("All customers:");
        // Fetch and print all customer details from database
    }

    @Override
    public void viewbankAccounts() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Would you like to: \n1. View all bank accounts \n2. Search for a bank account");
        System.out.println("Enter your choice (1 or 2): ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if(choice == 1){
            System.out.println("All bank accounts:");
            // Print all bank accounts' details
            viewAllbankAccounts();
        }
        else if(choice == 2){
            System.out.println("Enter the bank account ID: ");
            int accountId = scanner.nextInt();
            scanner.nextLine();
            viewSpecificbankAccount(accountId);
        }
        else{
            System.out.println("Invalid choice. Please enter 1 or 2.");
        }

        scanner.close();
    }

    @Override
    public void viewSpecificbankAccount(int accountId) {
        // Logic to view a specific bank account's details based on account ID
        System.out.println("Bank Account ID " + accountId + " details: ");
        // Fetch bank account details and print them
    }

    @Override
    public void viewAllbankAccounts() {
        // Logic to view all bank accounts' details
        System.out.println("All bank accounts:");
        // Fetch and print all bank accounts' details from database
    }

    @Override
    public void freezeAccount(int accountId) {
        // Logic to freeze a bank account based on account ID
        System.out.println("Freezing Bank Account ID " + accountId);
        // Call the relevant logic to freeze the account
    }

    @Override
    public void unfreezeAccount(int accountId) {
        // Logic to unfreeze a bank account based on account ID
        System.out.println("Unfreezing Bank Account ID " + accountId);
        // Call the relevant logic to unfreeze the account
    }

    @Override
    public boolean approveSmallLoan(int loanId) {
        // Logic to approve small loans
        System.out.println("Approving small loan with Loan ID " + loanId);
        // Approve loan logic here
        return true;
    }

    @Override
    public boolean rejectLoan(int loanId) {
        // Logic to reject loans
        System.out.println("Rejecting loan with Loan ID " + loanId);
        // Reject loan logic here
        return true;
    }

    @Override
    public void viewAllLoans() {
        // Logic to view all loans
        System.out.println("Viewing all loans:");
        // Fetch and display all loans from the database
    }

    @Override
    public void viewAllRequests() {
        // Logic to view all requests (e.g., loan applications, account requests)
        System.out.println("Viewing all requests:");
        // Fetch and display all requests from the database
    }

    // Additional Staff-specific methods
    public void viewStaffDetails() {
        // Display staff details (ID, department, role)
        System.out.println("Staff ID: " + staffId);
        System.out.println("Department: " + department);
        System.out.println("Role: " + role);
        System.out.println("Full Name: " + getFullName());
        System.out.println("Email: " + getEmail());
    }
}
