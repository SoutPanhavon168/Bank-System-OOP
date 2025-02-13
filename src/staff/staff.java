package staff;
import user.User;
import Interfaces.Management;
import java.time.LocalDate;
import java.util.Scanner;

public class staff extends User implements Management{
    public staff(String lastName, String firstName, String email, String password, String confirmPassword, String phoneNumber, LocalDate birthDate, String governmentId){
        super(lastName, firstName, email, password, confirmPassword, phoneNumber, birthDate, governmentId);
    }

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
    public 

    

}
