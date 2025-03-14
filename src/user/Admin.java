package user;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import database.CustomerDAO;
import database.StaffDAO;
import bankaccount.BankAccount;
import database.TransactionDAO;
import transaction.Transaction;
//admin needs to login first before doing any action
public class Admin extends Staff {
    private String admin_username;
    private String admin_password;
    static ArrayList<BankAccount> users = new ArrayList<>();
    private int pin;

    public Admin(String admin_password,String admin_username,String lastName, String firstName, String email, String password, String confirmPassword,
            String phoneNumber, LocalDate birthDate, String governmentId, int staffId, String role ) {
        super(lastName, firstName, email, password, confirmPassword, phoneNumber, birthDate, governmentId, staffId, role);
        this.admin_username = admin_username;
        this.admin_password = admin_password; 
    }
    public Admin() {
        this.admin_username = admin_username;
        this.admin_password = admin_password
    }

    public void admin_login(){
        Scanner sc = new Scanner(System.in);
        admin_username = "admin";
        admin_password = "admin123";
        try  {
            System.out.println("Enter the admin username: ");
            String username = sc.nextLine();
            System.out.println("Enter the admin password: ");
            String password = sc.nextLine();
            if(username.equals(this.admin_username) && password.equals(this.admin_password)){
                while (true) {
                System.out.println("========Admin Menu=======");
                System.out.println("1. Add Account");
                System.out.println("2. Remove Account");
                System.out.println("3. Update Account");
                System.out.println("4. Approve Large Loan");
                System.out.println("5. View All Transactions");
                System.out.println("6. View All Payments");
                System.out.println("7. Exit");
                System.out.println("Please choose an option (1-7): ");
                int choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1:
                        addAccount();
                        break;
                    case 2:
                        removeAccount();
                        break;
                    case 3:
                        updateAccount();
                        break;
                    case 4:
                        approveLargeLoan(1);
                        break;
                    case 5:
                        viewAllTransactions();
                        break;
                    case 6:
                        viewAllPayments();
                        break;
                    case 7:
                        System.out.println("Exiting Admin Menu");
                        return;
                    default:
                        System.out.println("Invalid option");
                        break;
                }
            }
        }else{
            System.out.println("Invalid username or password");
        }
    } catch (Exception e) {
        System.out.println("Error!!!");
    } finally {
        sc.close();
    }
}
    
    public void addStaffAccount(){
            Scanner scanner = new Scanner(System.in);
            Customer staff = new Customer();
            StaffDAO  staffDAO = new StaffDAO();
            try {
                System.out.println("Enter your last name: ");
                this.lastName = scanner.nextLine().trim();
                if (lastName.isEmpty()) {
                    throw new CustomerException.EmptyFieldException("Last name");
                }
                if (staff.isInputInvalid(lastName)){
                    throw new CustomerException.InvalidInputException("Last name");
                }
        
                System.out.println("Enter your first name: ");
                this.firstName = scanner.nextLine().trim();
                if (firstName.isEmpty()) {
                    throw new CustomerException.EmptyFieldException("First name");
                }
                
                if (staff.isInputInvalid(firstName)){
                    throw new CustomerException.InvalidInputException("First name");
                }
        
                System.out.println("Enter your email: ");
                String email = scanner.nextLine().trim();
                if (!isEmailValid(email)) {
                    throw new CustomerException.InvalidEmailException();
                }
        
                System.out.println("Enter your password: ");
                String password = scanner.nextLine();
        
                System.out.println("Confirm your password: ");
                String confirmPassword = scanner.nextLine();
                if (!password.equals(confirmPassword)) {
                    throw new CustomerException.PasswordMismatchException();
                }
        
                System.out.println("Enter your phone number: ");
                String phoneNumber = scanner.nextLine().trim();
                if (!isPhoneNumberValid(phoneNumber)) {
                    throw new CustomerException.InvalidPhoneNumberException();
                }
        
                System.out.println("Enter your birth date (YYYY-MM-DD): ");
                LocalDate birthDate;
                try {
                    birthDate = LocalDate.parse(scanner.nextLine().trim());
                    if (birthDate.isAfter(LocalDate.now().minusYears(16))) {
                        throw new CustomerException.UnderageException();
                    }
                } catch (DateTimeParseException e) {
                    throw new CustomerException.InvalidBirthDateException();
                }
        
                System.out.println("Enter your government ID: ");
                String governmentId = scanner.nextLine().trim();
                if (governmentId.isEmpty()) {
                    throw new CustomerException.EmptyFieldException("Government ID");
                }
    
                if (!isGovernmentIdValid(governmentId)){
                    throw new CustomerException.InvalidGovernmentIdException();
                }
            } catch (CustomerException e) {
                System.out.println("Registration failed: " + e.getMessage());
    }
        
    Staff staff1 = new Staff(lastName, firstName, email, password, confirmPassword, phoneNumber, birthDate, governmentId, role);
    staffDAO.saveStaff(staff1);
    System.out.println("Staff account created successfully");
}
    public void addAccount() {
        Scanner scanner = new Scanner(System.in);
        CustomerDAO customerDAO = new CustomerDAO();
        Customer customers = new Customer();
        try {
            System.out.println("Enter your last name: ");
            this.lastName = scanner.nextLine().trim();
            if (lastName.isEmpty()) {
                throw new CustomerException.EmptyFieldException("Last name");
            }
            if (customers.isInputInvalid(lastName)){
                throw new CustomerException.InvalidInputException("Last name");
            }
    
            System.out.println("Enter your first name: ");
            this.firstName = scanner.nextLine().trim();
            if (firstName.isEmpty()) {
                throw new CustomerException.EmptyFieldException("First name");
            }
            
            if (customers.isInputInvalid(firstName)){
                throw new CustomerException.InvalidInputException("First name");
            }
    
            System.out.println("Enter your email: ");
            String email = scanner.nextLine().trim();
            if (!isEmailValid(email)) {
                throw new CustomerException.InvalidEmailException();
            }
    
            System.out.println("Enter your password: ");
            String password = scanner.nextLine();
    
            System.out.println("Confirm your password: ");
            String confirmPassword = scanner.nextLine();
            if (!password.equals(confirmPassword)) {
                throw new CustomerException.PasswordMismatchException();
            }
    
            System.out.println("Enter your phone number: ");
            String phoneNumber = scanner.nextLine().trim();
            if (!isPhoneNumberValid(phoneNumber)) {
                throw new CustomerException.InvalidPhoneNumberException();
            }
    
            System.out.println("Enter your birth date (YYYY-MM-DD): ");
            LocalDate birthDate;
            try {
                birthDate = LocalDate.parse(scanner.nextLine().trim());
                if (birthDate.isAfter(LocalDate.now().minusYears(16))) {
                    throw new CustomerException.UnderageException();
                }
            } catch (DateTimeParseException e) {
                throw new CustomerException.InvalidBirthDateException();
            }
    
            System.out.println("Enter your government ID: ");
            String governmentId = scanner.nextLine().trim();
            if (governmentId.isEmpty()) {
                throw new CustomerException.EmptyFieldException("Government ID");
            }

            if (!isGovernmentIdValid(governmentId)){
                throw new CustomerException.InvalidGovernmentIdException();
            }
    
            // Ask for PIN
            System.out.println("Enter your PIN (4 digits): ");
            this.pin = scanner.nextInt();
            String pinStr = String.valueOf(pin);
            if (pinStr.length() != 4 || !pinStr.matches("\\d+")) {
                throw new CustomerException.InvalidPinException();
            }
    
            // Confirm PIN
            System.out.println("Confirm your PIN: ");
            int confirmPin = scanner.nextInt();
            if (pin != confirmPin) {
                throw new CustomerException.PinMismatchException();
            }

            List <Customer> customers1 = CustomerDAO.getAllCustomers();

            for (Customer customer : customers1) {
                if (customer.getGovernmentId().equals(governmentId)) {
                    throw new CustomerException.CustomerAlreadyExistsException();
                    }
                if (customer.getEmail().equals(email)) {
                    throw new CustomerException.CustomerAlreadyExistsException();
                }
                if (customer.getPhoneNumber().equals(phoneNumber)) {
                    throw new CustomerException.CustomerAlreadyExistsException();
                }
            }
    
            // Create new customer with PIN
            Customer customer = new Customer(lastName, firstName, email, password, confirmPassword, phoneNumber, birthDate, governmentId);
    
            // Save customer to the database using CustomerDAO
            customerDAO.saveCustomer(customer); // Store customer in DB
    
        } catch (CustomerException e) {
            System.out.println("Registration failed: " + e.getMessage());
    }
        System.out.println("Registration successfully!!");
    }
    public void removeAccount() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the account number(UserID) to remove: ");
        int accountNumber = sc.nextInt();
        //wait todo until we have the database
        CustomerDAO customerDAO = new CustomerDAO();
        customerDAO.deleteCustomer(accountNumber);
        // Remove account from database
        System.out.println("Account removed successfully");
    }
    public void updateAccount(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the account number(UserID) to update: ");
        String InputuserId = sc.nextLine();
        if(userId == Integer.parseInt(InputuserId)){
            System.out.println("Enter the new password: ");
            String newPassword = sc.nextLine();
            CustomerDAO passcuCustomer = new CustomerDAO();
            passcuCustomer.updatePasswordInDatabase(userId,newPassword);
            } else {
                System.out.println("Account not found");
            }
        }

    public boolean approveLargeLoan(int loanId) {
        // Add logic to approve large loan
        boolean loanApproved = true; // Placeholder for actual logic
        if (loanApproved) {
            System.out.println("Loan approved successfully");
        } else {
            System.out.println("Loan not found");
        }
        return loanApproved;
    } 
    public void viewAllTransactions() {
        TransactionDAO transactionDAO = new TransactionDAO();
        List<Transaction> transactions = transactionDAO.getAllTransactions();

        for (Transaction transaction : transactions) {
            System.out.println("Transaction ID: " + transaction.getTransactionID());
            System.out.println("Account Number: " + transaction.getBankAccount().getAccountNumber());
            System.out.println("Type: " + transaction.getType());
            System.out.println("Amount: " + transaction.getAmount());
            System.out.println("Status: " + transaction.getStatus());
            System.out.println("Date: " + transaction.getTransactionDate());
            System.out.println("----------------------------------");
        }
    }
      // Only Admin
    void viewAllPayments(){
        //fetch all payments from the database
    }; 
    @Override
    public String toString() {
        return "User ID: " + userId +
               " | Name: " + firstName + " " + lastName +
               " | Email: " + email +
               " | Phone: " + phoneNumber +
               " | Birth Date: " + birthDate +
               " | Role: Admin" +
               " | Government ID: " + governmentId;
    }
}

