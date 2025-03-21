package user;
import Interfaces.Authentication;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class User implements Authentication{
    protected int userId;
    protected String lastName;
    protected String firstName;
    protected String email;
    protected String phoneNumber;
    protected String password;
    protected String confirmPassword;
    protected LocalDate birthDate;
    protected String governmentId;
    protected boolean isAdmin;
    protected boolean isStaff;

    protected static List<User> users = new ArrayList<>(); // All users stored here
    

    public User(){} // 

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

    //getters
    public int getUserId(){return userId;}
    public String getLastName(){return lastName;}
    public String getFirstName(){return firstName;}
    public String getEmail(){return email;}
    public String getPhoneNumber(){return phoneNumber;}    
    public LocalDate getBirthDate(){return birthDate;}
    public String getGovernmentId(){return governmentId;}
    public String getMaskedGovernmentId(){
            return "********" + governmentId.substring(8);   
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    //setters 
    public void setEmail(String email){
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    // private as it is only used internally in this class
    public boolean isEmailValid(String email){
        //email regex pattern to check if the email has the correct pattern [a-z, A-Z, 0-9, +, _, ., -]@[a-z, A-Z, 0-9, -]
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        if(email.matches(emailRegex)){
            return true;
        }

        return false;
    }

    public boolean isPhoneNumberValid(String phoneNumber){
        //phone number regex pattern to check if the phone number has the correct pattern [0-9]{10}
        String phoneNumberRegex = "^[0-9]{8,10}$";
        if(phoneNumber.matches(phoneNumberRegex)){
            return true;
        }

        return false;
    }

    protected boolean isGovernmentIdValid(String governmentId){
        //government id regex pattern to check if the government id has the correct pattern [a-z, A-Z, 0-9]{12}
        String governmentIdRegex = "^[0-9]{12}$";
        if(governmentId.matches(governmentIdRegex)){
            return true;
        }

        return false;
    } 

    // method to update password when the user forget password, put on public to allow external access

    // method to check if user is admin, put on public to allow external access
    public boolean isAdmin(){
        return isAdmin;
    }

    public boolean isStaff(){
        return isStaff;
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
