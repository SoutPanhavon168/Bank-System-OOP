package user;
import Interfaces.Management;
import java.time.LocalDate;
import java.util.Scanner;

public class Staff extends User implements Management{
    public Staff(String lastName, String firstName, String email, String password, String confirmPassword, String phoneNumber, LocalDate birthDate, String governmentId){
        super(lastName, firstName, email, password, confirmPassword, phoneNumber, birthDate, governmentId);
    }

    @Override
    public void viewCustomerDetails(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Would you like to: \n1. View all customers \n2. Search for a customer");
        System.out.println("Enter your choice (1 or 2): ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if(choice == 1){
            System.out.println("All customers:");
            // Print all customers  details
            viewAllCustomers();
        }
        else if(choice == 2){
            System.out.println("Enter the customer's ID: ");
            int ID = scanner.nextInt();
            scanner.nextLine();
            viewSpecificCustomerDetails(ID);
            // Search for the customer with the email
            // Print the customer's details
        }
        else{
            System.out.println("Invalid choice. Please enter 1 or 2.");
        }        
        
    }

    @Override
    public void updateCustomerAccount(){

    }

    @Override
    public void viewSpecificCustomerDetails(int ID){
        System.out.println("Customer's" + ID + "details: ");
        //TODO: Print the customer's details from file
    }

    @Override
    public void viewAllCustomers(){
        System.out.println("All customers:");
        //TODO: Print all customers details from file
    }

    @Override 
    public void viewbankAccounts(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Would you like to: \n1. View all bank accounts \n2. Search for a bank account");
        System.out.println("Enter your choice (1 or 2): ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if(choice == 1){
            System.out.println("All bank accounts:");
            // Print all bank accounts details
            viewAllbankAccounts();
        }
        else if(choice == 2){
            System.out.println("Enter the bank account ID: ");
            int accountId = scanner.nextInt();
            scanner.nextLine();
            viewSpecificbankAccount(accountId);
            // Search for the bank account with the ID
            // Print the bank account's details
        }
        else{
            System.out.println("Invalid choice. Please enter 1 or 2.");
        }
    }

    @Override
    public void viewSpecificbankAccount(int accountId){

    }

    @Override
    public void viewAllbankAccounts() {
        
    }

    @Override
    public void freezeAccount(int accountId){

    }

    @Override 
    public void unfreezeAccount(int accountId){

    }

    @Override
    public boolean approveSmallLoan(int loanId){
        return true;
    }

    @Override
    public boolean rejectLoan(int loanId){
        return true;
    }

    @Override
    public void viewAllLoans(){

    }

    @Override
    public void viewAllRequests(){

    }


    

}
