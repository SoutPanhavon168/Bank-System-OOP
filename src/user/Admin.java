package user;

import java.util.ArrayList;
import java.time.LocalDate;
import java.util.Scanner;
//admin needs to login first before doing any action
public class Admin extends User {
    private String admin_username;
    private String admin_password; 


    static ArrayList<User> users = new ArrayList<User>();
    public Admin(String admin_password,String admin_username,String lastName, String firstName, String email, String password, String confirmPassword,
            String phoneNumber, LocalDate birthDate, String governmentId) {
        super(lastName, firstName, email, password, confirmPassword, phoneNumber, birthDate, governmentId);
        this.admin_username = admin_username;
        this.admin_password = admin_password; 
    }

    public void admin_login(){
        Scanner sc = new Scanner(System.in);
        try  {
            System.out.println("Enter the admin username: ");
            String username = sc.nextLine();
            System.out.println("Enter the admin password: ");
            String password = sc.nextLine();
            if(username.equals(this.admin_username) && password.equals(this.admin_password)){
                while (isAdmin) {
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
    public void addAccount() {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("Enter the first name: ");
            String firstName = sc.nextLine();
            this.firstName = firstName;
            System.out.println("Enter the last name: ");
            String lastName = sc.nextLine();
            this.lastName = lastName;
            System.out.println("Enter the email: ");
            String email = sc.nextLine();
            while(!isEmailValid(email)){
                System.out.println("Invalid email. Please enter a valid email: ");
                email = sc.nextLine();
            }
            System.out.println("Enter the phone number: ");
            String phoneNumber = sc.nextLine();
            if(!isPhoneNumberValid(phoneNumber)){
                System.out.println("Invalid phone number. Please enter a valid phone number: ");
                phoneNumber = sc.nextLine();
            }
            System.out.println("Enter the password: ");
            String password = sc.nextLine();
            do {
                System.out.print("Confirm your password: ");
                confirmPassword = sc.nextLine();
                if (!confirmPassword.equals(password)) {
                    System.out.println("Passwords do not match. Try again.");
                }
            }while (!confirmPassword.equals(password));
            System.out.println("Enter the birth date: ");
            String birthDate = sc.nextLine();
            this.birthDate = LocalDate.parse(birthDate);
            do {
                System.out.println("Enter the government ID: ");
                String governmentId = sc.nextLine();
                if(!isGovernmentIdValid(governmentId)){
                    System.out.println("Invalid government ID. Must be at least 6 characters long.");
                    governmentId = sc.nextLine();
                }
            } while (!isGovernmentIdValid(governmentId));
            System.out.println("Enter the user type: ");
            String userType = sc.nextLine();
            if (isAdmin) {
                if(userType.equals("admin")){
                    isAdmin = true;
                }
                else if(userType.equals("staff")){
                    isStaff = true;
                }
                else{
                    isAdmin = false;
                    isStaff = false;
                }
                
            }
        }

        //wait todo until we have the database
        // Add account to database
    }
    public void removeAccount() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the account number(UserID) to remove: ");
        int accountNumber = sc.nextInt();

        //wait todo until we have the database
        if(accountNumber == userId) {
            System.out.println("Entet the password to confirm: ");
            String password = sc.nextLine();
            if(password.equals(this.password)) {
                System.out.println("Account removed successfully");
            }
            else {
                System.out.println("Incorrect password");
            }
        }
        else {
            System.out.println("Account not found");
        }
        sc.close();
        // Remove account from database
    }
    public void updateAccount(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the account number(UserID) to update: ");
        int accountNumber = sc.nextInt();

        //wait todo until we have the database

        if(accountNumber == userId) {
            System.out.println("Enter the password to confirm: ");
            String password = sc.nextLine();
            if(password.equals(this.password)) {
                System.out.println("Enter the new first name: ");
                String firstName = sc.nextLine();
                this.firstName = firstName;
                System.out.println("Enter the new last name: ");
                String lastName = sc.nextLine();
                this.lastName = lastName;
                System.out.println("Enter the new phone number: ");
                String phoneNumber = sc.nextLine();
                if(!isPhoneNumberValid(phoneNumber)){
                    System.out.println("Invalid phone number. Please enter a valid phone number: ");
                    phoneNumber = sc.nextLine();
                }
                System.out.println("Enter the new password: ");
                String newPassword = sc.nextLine();
                do {
                    System.out.print("Confirm your new password: ");
                    String newConfirmPassword = sc.nextLine();
                    if (!newConfirmPassword.equals(newPassword)) {
                        System.out.println("Passwords do not match. Try again.");
                    }
                System.out.println("Enter the new email: ");
                String email = sc.nextLine();
                while(!isEmailValid(email)){
                    System.out.println("Invalid email. Please enter a valid email: ");
                    email = sc.nextLine();
                }
                System.out.println("Enter the new birth date: ");
                String birthDate = sc.nextLine();
                
                System.out.println("Enter the new government ID: ");
                String governmentId = sc.nextLine();
                this.governmentId = governmentId;
                System.out.println("Enter the new user type: ");
                String userType = sc.nextLine();
                if(userType.equals("admin")){
                    isAdmin = true;
                }
                else if(userType.equals("staff")){
                    isStaff = true;
                }
                else{
                    isAdmin = false;
                    isStaff = false;
                }
                //wait todo until we have the database
                // Update account in database
            }
        }
            else {
                System.out.println("Incorrect password");
            }
        }
        else {
            System.out.println("Account not found");
        }
    }
    boolean approveLargeLoan(int loanId){
        return true;
    };  // Only Admin
    void viewAllTransactions(){
        //fetch all transactions from the database
    };  // Only Admin
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
