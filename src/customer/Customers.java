package customer;

import user.User;
import bankaccount.BankAccount;

import java.time.LocalDate;
import java.util.ArrayList;

public class Customers extends User {
    private ArrayList<BankAccount> bankAccounts;

   public Customers(String lastName, String firstName, String email, String password, String confirmPassword,
                     String phoneNumber, LocalDate birthDate, String governmentId) {
        super(lastName, firstName, email, password, confirmPassword, phoneNumber, birthDate, governmentId);
        this.bankAccounts = new ArrayList<>();
    }
}