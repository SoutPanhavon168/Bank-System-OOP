package user;

import Interfaces.Authentication;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class User implements Authentication {
    protected final int userId;
    protected final String lastName;
    protected final String firstName;
    protected final String email;
    protected final String phoneNumber;
    protected final String password;
    protected final LocalDate birthDate;
    protected final String governmentId;
    protected final boolean isAdmin;
    protected final boolean isStaff;

    private static final List<User> users = new ArrayList<>(); // All users stored here

    public User(String lastName, String firstName, String email, String password, String confirmPassword, String phoneNumber, LocalDate birthDate, String governmentId) {
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new IllegalArgumentException("Invalid email format!");
        }
        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("Passwords do not match!");
        }
        if (password.length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters long.");
        }
        if (!phoneNumber.matches("^[0-9]{10}$")) {
            throw new IllegalArgumentException("Phone number must be exactly 10 digits.");
        }
        if (!governmentId.matches("^[0-9]{12}$")) {
            throw new IllegalArgumentException("Government ID must be exactly 12 digits.");
        }
        if (Period.between(birthDate, LocalDate.now()).getYears() < 16) {
            throw new IllegalStateException("User must be 16 or older to register.");
        }

        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.governmentId = governmentId;
        this.userId = generateUserId();

        this.isAdmin = false;
        this.isStaff = false;
        users.add(this);
    }

    // Getters
    public int getUserId() { return userId; }
    public String getLastName() { return lastName; }
    public String getFirstName() { return firstName; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
    public LocalDate getBirthDate() { return birthDate; }
    public String getMaskedGovernmentId() { return "********" + governmentId.substring(8); }
    public boolean isAdmin() { return isAdmin; }
    public boolean isStaff() { return isStaff; }

    @Override
    public String toString() {
        String role = isAdmin ? "Admin" : isStaff ? "Staff" : "Customer";
        return "User ID: " + userId +
               " | Name: " + firstName + " " + lastName +
               " | Email: " + email +
               " | Phone: " + phoneNumber +
               " | Birth Date: " + birthDate +
               " | Role: " + role +
               " | Government ID: " + getMaskedGovernmentId();
    }

    private static int generateUserId() {
        return (int) (System.currentTimeMillis() % 1000000) + new Random().nextInt(1000);
    }
}
