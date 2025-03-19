package user.staff;

import database.CustomerDAO;
import database.StaffDAO;
import java.util.List;
import java.util.Scanner;
import user.Customer;
import user.StaffException;

public class UserManager {
    
    private Staff currentStaff;
    private CustomerDAO customerDAO;
    private StaffDAO staffDAO;
    
    public UserManager(Staff staff) {
        this.currentStaff = staff;
        this.customerDAO = new CustomerDAO();
        this.staffDAO = new StaffDAO();
    }
    
    public Staff loginStaff(String email, String password) {
        try {
            // Verify credentials using StaffDAO
            Staff staff = staffDAO.verifyCredentials(email, password);

            if (staff != null) {
                System.out.println("Login successful. Welcome, " + staff.getFirstName() + "!");
                return staff; // Return the authenticated Staff object
            } else {
                System.out.println("Invalid email or password.");
                return null;
            }
        } catch (StaffException.DatabaseAccessException e) {
            System.out.println("Login failed: " + e.getMessage());
            return null;
        }
    }
    
    public void viewCustomerDetails() {
        Scanner scanner = new Scanner(System.in);

        if ((!currentStaff.hasAccess(Staff.StaffRole.MANAGER)) && !currentStaff.hasAccess(Staff.StaffRole.CUSTOMER_SERVICE)){
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
    
    public void updateCustomerAccount() {
        if((!currentStaff.hasAccess(Staff.StaffRole.MANAGER)) && (!currentStaff.hasAccess(Staff.StaffRole.CUSTOMER_SERVICE))){
            System.out.println("You do not have access to update customer accounts.");
            return;
        }
    
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
    
                // Check if the new password is the same as the current password
                if(newPassword.equals(customer.getPassword())){
                    System.out.println("The new password cannot be the same as the old password. Please enter a different password.");
                } else {
                    customer.setPassword(newPassword);
                    customerDAO.updatePasswordInDatabase(customerId, newPassword);
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
    
    public void viewSpecificCustomerDetails(int customerId) {
        // Logic to view a specific customer's details based on their ID
        Customer customer = customerDAO.getCustomerById(customerId);

        if(customer == null){
            System.out.println("Customer with ID: " + customerId + " not found.");
            return;
        }

        System.out.println("Customer's " + customerId + " details: ");
        System.out.println("Full Name: " + customer.getFullName());
        System.out.println("Email: " + customer.getEmail());
        System.out.println("Phone Number: " + customer.getPhoneNumber());
        System.out.println("Password: " + customer.getPassword());
        System.out.println("Birth Date: " + customer.getBirthDate());
        System.out.println("Government ID: " + customer.getMaskedGovernmentId());
    }

    public void viewAllCustomers() {
        // Logic to view all customers' details
        List<Customer> customers = customerDAO.getAllCustomers();

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
    
    public void viewStaffDetails() {
        // Display staff details (ID, role)
        System.out.println("Staff ID: " + currentStaff.getStaffId());
        System.out.println("Role: " + currentStaff.getRole());
        System.out.println("Full Name: " + currentStaff.getFullName());
        System.out.println("Email: " + currentStaff.getEmail());
    }
}