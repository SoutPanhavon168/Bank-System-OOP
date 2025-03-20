package user.staff;

import Interfaces.Management;
import java.time.LocalDate;
import user.User;

public class Staff extends User implements Management {
    
    // Additional attributes for Staff
    protected int staffId;
    protected StaffRole role;

    public enum StaffRole {
        MANAGER,
        CUSTOMER_SERVICE,
        TELLER
    }
    
    // Manager classes
    private UserManager userManager;
    private AccountManager accountManager;
    
    public Staff(String lastName, String firstName, String email, String password, String confirmPassword, 
    String phoneNumber, LocalDate birthDate, String governmentId, StaffRole role) {
        super(lastName, firstName, email, password, confirmPassword, phoneNumber, birthDate, governmentId);
        this.staffId = staffId;
        this.role = role;
        
        // Initialize managers
        this.userManager = new UserManager(this);
        this.accountManager = new AccountManager(this);
    }
    
    public Staff(){
        // Initialize managers
        this.userManager = new UserManager(this);
        this.accountManager = new AccountManager(this);
    }

    // Getter and Setter methods for attributes
    public int getStaffId() {
        return staffId;
    }

    public String getPassword(){
        return password;
    }
    
    public String getPosition(){
        return role.toString();
    }
    
    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public String getGovernmentId(){
        return governmentId;
    }

    public StaffRole getRole() {
        return role;
    }

    public void setRole(StaffRole role) {
        this.role = role;
    }

    public String getFullName(){
        return this.getFirstName() + " " + this.getLastName();
    }

    public LocalDate getBirthDate(){
        return birthDate;
    }

    public boolean hasAccess(StaffRole requiredRole){
        return this.role == requiredRole;
    }

    // Management interface implementations - now delegate to the appropriate manager
    @Override
    public void viewCustomerDetails() {
        userManager.viewCustomerDetails();
    }

    @Override
    public Staff login(String email, String password) {
        return userManager.loginStaff(email, password);
    }

    @Override
    public void updateCustomerAccount() {
        userManager.updateCustomerAccount();
    }

    @Override
    public void viewSpecificCustomerDetails(int customerId) {
        userManager.viewSpecificCustomerDetails(customerId);
    }

    @Override
    public void viewAllCustomers() {
        userManager.viewAllCustomers();
    }

    @Override
    public void viewbankAccounts() {
        accountManager.viewBankAccounts();
    }

    @Override
    public void viewSpecificbankAccount(int accountId) {
        accountManager.viewSpecificBankAccount(accountId);
    }

    @Override
    public void viewAllbankAccounts() {
        accountManager.viewAllBankAccounts();
    }

    @Override
    public void freezeAccount(int accountId) {
        accountManager.freezeAccount(accountId);
    }

    @Override
    public void unfreezeAccount(int accountId) {
        accountManager.unfreezeAccount(accountId);
    }
    
    @Override
    public void createBankAccount() {
        accountManager.createBankAccount();
    }

    @Override
    public void deleteBankAccount(int accountId) {
        accountManager.deleteBankAccount(accountId);
    }

    @Override
    public void viewAllTransactions(){
        accountManager.viewAllTransactions();
    }

    @Override
    public void viewSpecificTransaction(String transactionId){
        accountManager.viewSpecificTransaction(transactionId);
    }

    @Override
    public void refundTransaction(String transactionId){
        accountManager.refundTransaction(transactionId);
    }  

    @Override
    public void depositMoney(int accountNumber, double amount){
        accountManager.depositMoney(accountNumber, amount);
    } 

    @Override 
    public void transferMoney(String fromAccountNumber, String toAccountNumber, double amount){
        accountManager.transferMoney(fromAccountNumber, toAccountNumber, amount);
    }

    // Staff-specific methods
    public void viewStaffDetails() {
        userManager.viewStaffDetails();
    }

    @Override
    public String toString(){
        return super.toString() + "\nStaff ID: " + staffId + "\nRole: " + role;
    }
}