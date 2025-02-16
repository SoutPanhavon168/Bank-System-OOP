package user;
import Interfaces.Authentication;
import java.time.LocalDate;
import java.time.Period;
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class User implements Authentication{
    private int userId;
    private String lastName;
    private String firstName;
    private String email;
    private String phoneNumber;
    private String password;
    private String confirmPassword;
    private LocalDate birthDate;
    private String governmentId;
    private boolean isAdmin;
    private boolean isStaff;

    private static String adminKey = "Admin123";
    private static String staffKey = "Staff123";


    public User(){}

    public User(String lastName, String firstName, String email, String password, String confirmPassword, String phoneNumber, LocalDate birthDate, String governmentId){
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.governmentId = governmentId;
    }

    public User(String phoneNumber, String email, String password){
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
    }

    //getters
    public int getUserId(){return userId;}
    public String getLastName(){return lastName;}
    public String getFirstName(){return firstName;}
    public String getEmail(){return email;}
    public String getPhoneNumber(){return phoneNumber;}    
    public LocalDate getBirthDate(){return birthDate;}
    public String getMaskedGovernmentId(){
            return "********" + governmentId.substring(8);   
    }

    //setters 
    public void setEmail(String email){
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }


    private static List<User> admin = new ArrayList<>();
    private static List<User> staffMembers = new ArrayList<>();
    private static List<User> customers = new ArrayList<>();

    // private as it is only used internally in this class
    public boolean isEmailValid(String email){
        //email regex pattern to check if the email has the correct pattern [a-z, A-Z, 0-9, +, _, ., -]@[a-z, A-Z, 0-9, -]
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        if(email.matches(emailRegex)){
            return true;
        }

        return false;
    }

    // static method to generate user id without creating an object of the class
    private static int generateUserId(){
        //generate user id
        //use the current time in milliseconds and get the last 6 digits
        //then add a random number between 0 and 1000 to ensure even more uniqueness
        Random random = new Random();
        return (int)(System.currentTimeMillis() % 1000000) + random.nextInt(1000);
    }

    public boolean isPhoneNumberValid(String phoneNumber){
        //phone number regex pattern to check if the phone number has the correct pattern [0-9]{10}
        String phoneNumberRegex = "^[0-9]{10}$";
        if(phoneNumber.matches(phoneNumberRegex)){
            return true;
        }

        return false;
    }

    private boolean isGovernmentIdValid(String governmentId){
        //government id regex pattern to check if the government id has the correct pattern [a-z, A-Z, 0-9]{12}
        String governmentIdRegex = "^[0-9]{12}$";
        if(governmentId.matches(governmentIdRegex)){
            return true;
        }

        return false;
    }

    public void viewAccount(){
        
    }

    // Method to create an account, put on public to allow external access
    @Override
    public void register(){

        //create account
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your first name: ");
        firstName = scanner.nextLine();

        System.out.println("Enter your last name: ");
        lastName = scanner.nextLine();

        System.out.println("Enter your email: ");
        email = scanner.nextLine();
        while(!isEmailValid(email)){
            System.out.println("Invalid email. Please enter a valid email: ");
            email = scanner.nextLine();
        }

        System.out.println("Enter your password: ");
        password = scanner.nextLine();
        do {
            System.out.print("Confirm your password: ");
            confirmPassword = scanner.nextLine();
            if (!confirmPassword.equals(password)) {
                System.out.println("Passwords do not match. Try again.");
            }
        } while (!confirmPassword.equals(password));


        do {
            System.out.println("Enter your phone number (e.g., +1234567890 or 1234567890): ");
            phoneNumber = scanner.nextLine();
            if (!isPhoneNumberValid(phoneNumber)) {
                System.out.println("Invalid phone number. Please enter a valid phone number.");
            }
        } while (!isPhoneNumberValid(phoneNumber));

        System.out.println("Enter your birth date (yyyy-mm-dd): ");
        String birthDateString = scanner.nextLine();
        this.birthDate = LocalDate.parse(birthDateString);

       
        do {
            System.out.println("Enter your government ID: ");
            governmentId = scanner.nextLine();
            if (!isGovernmentIdValid(governmentId)) {
                System.out.println("Invalid government ID. Must be at least 6 characters long.");
            }
        } while (!isGovernmentIdValid(governmentId));

        
        System.out.println("Enter the admin or staff key (Press Enter to skip): ");
        String key = scanner.nextLine();
        if (key.equals(adminKey)) {
            isAdmin = true;
            System.out.println("Admin privilege granted.");
        } else if (key.equals(staffKey)) {
            isStaff = true;
            System.out.println("Staff privilege granted.");
        } else {
            isAdmin = false;
            isStaff = false;
        }


        if(isUnderage()){
            System.out.println("You are underage. You cannot create an account.");
            return;
        }

        userId = generateUserId();

        if(isAdmin){
            admin.add(this);
        }
        else if(isStaff){
            staffMembers.add(this);
        }else{
            customers.add(this);
        }

        System.out.println("Account created successfuly, welcome " + firstName + " " + lastName + "!");

        //next after successfully creating an account, write all information to a file

    }

    @Override
    public boolean login(){
        //login
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your email or phone number: ");
        String emailOrPhoneNumber = scanner.nextLine();
        System.out.println("Enter your password: ");
        String password = scanner.nextLine();

        //open file to read all user information and compare to the login input to validate user login

        return true; //placeholder for now
    }


    // method to update password when the user forget password, put on public to allow external access
    public void forgotPassword(){
        //forgot password
    }

    // method to check if user is admin, put on public to allow external access
    public boolean isAdmin(){
        return isAdmin;
    }

    public boolean isStaff(){
        return isStaff;
    }

    //private as it is used internally in the class only
    private int calculateAge(){
        //calculate age logic
        if(birthDate == null){
            return 0;
        }
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    public boolean isUnderage(){
        return calculateAge() < 16;
    }

    @Override
    public String toString() {
    String role = isAdmin ? "Admin" : isStaff ? "Staff" : "Customer";
    return "User ID: " + userId +
           " | Name: " + firstName + " " + lastName +
           " | Email: " + email +
           " | Phone: " + phoneNumber +
           " | Birth Date: " + birthDate +
           " | Role: " + role +
           " | Government ID: " + governmentId;
    }


}
