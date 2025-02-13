package Admin;
import java.util.Scanner;
import Interfaces.Authentication;

public class Admin implements Authentication {
    private String firstName;
    private String lastName;    
    private String password;

    public Admin(String firstname, String lastname , String password, String role) {
        this.firstName = firstname;
        this.lastName = lastname;
        this.password = password;
    }
    @Override
    public boolean login(){
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter your firstname: ");
            String InputFirstname = scanner.nextLine();
            System.out.println("Enter your lastname: ");
            String InputLastname = scanner.nextLine();
            System.out.println("Enter your password: ");
            String InputPassword = scanner.nextLine();
            if (this.firstName.equals(InputFirstname) && this.lastName.equals(InputLastname) && this.password.equals(InputPassword)) {   
                return true;
            }else {
                return false;
            }
        }
        @Override
        public boolean register() {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter your firstname: ");
            String InputFirstname = scanner.nextLine();
            System.out.println("Enter your lastname: ");
            String InputLastname = scanner.nextLine();
            System.out.println("Enter your password: ");
            String InputPassword = scanner.nextLine();
    
            System.out.println("New user registered:  " + InputFirstname + " " + InputLastname);
            this.firstName = InputFirstname;
            this.lastName = InputLastname; 
            this.password = InputPassword;
            //write to file
            return true;
        }
        public boolean forgotPassword() {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter your firstname: ");
            String InputFirstname = scanner.nextLine();
            System.out.println("Enter your lastname: ");
            String InputLastname = scanner.nextLine();
            if (this.firstName.equals(InputFirstname) && this.lastName.equals(InputLastname)) {
                System.out.println("Enter your new password: ");
                String Input_newPassword = scanner.nextLine();
                this.password = Input_newPassword;
                System.out.println("Password updated successfully.");
            } else {
                System.out.println("User not found.");
            }
            return true;
        }
    }
