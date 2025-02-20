package Admin;
import java.util.Scanner;
import Interfaces.Management;

//admin needs to login first before doing any action
public class Admin extends User  {
    private String admin_username;
    private String admin_password; 


    public Admin(String admin_password, String admin_username, int userId, String firstName, String lastName, String phoneNumber, String password, String confirmPassword, String email, String birthDate, String governmentId, String userType) {
        super(userId, firstName, lastName, phoneNumber, password, confirmPassword, email, birthDate, governmentId, userType);
        this.admin_username = admin_username;
        this.admin_password = admin_password;
    }
    public void addAccount() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the first name: ");
        String firstName = sc.nextLine();
        System.out.println("Enter the last name: ");
        String lastName = sc.nextLine();
        System.out.println("Enter the phone number: ");
        String phoneNumber = sc.nextLine();
        System.out.println("Enter the password: ");
        String password = sc.nextLine();
        System.out.println("Enter the confirm password: ");
        String confirmPassword = sc.nextLine();
        System.out.println("Enter the email: ");
        String email = sc.nextLine();
        System.out.println("Enter the birth date: ");
        String birthDate = sc.nextLine();
        System.out.println("Enter the government ID: ");
        String governmentId = sc.nextLine();
        System.out.println("Enter the user type: ");
        String userType = sc.nextLine();

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
                System.out.println("Enter the new last name: ");
                String lastName = sc.nextLine();
                System.out.println("Enter the new phone number: ");
                String phoneNumber = sc.nextLine();
                System.out.println("Enter the new password: ");
                String newPassword = sc.nextLine();
                System.out.println("Enter the new confirm password: ");
                String newConfirmPassword = sc.nextLine();
                System.out.println("Enter the new email: ");
                String email = sc.nextLine();
                System.out.println("Enter the new birth date: ");
                String birthDate = sc.nextLine();
                System.out.println("Enter the new government ID: ");
                String governmentId = sc.nextLine();
                System.out.println("Enter the new user type: ");
                String userType = sc.nextLine();

                //wait todo until we have the database

                // Update account in database
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

    };  // Only Admin
    void viewAllPayments(){

    }; 
}
