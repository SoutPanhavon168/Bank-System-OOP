package bankaccount;
import user.Admin;

public class AdminTest {
    public static void main(String[] args) {
        // Step 1: Create a new Admin and register
        Admin admin = new Admin();
        admin.updateStaffAccount();  // Ensure register() collects first name, last name, and PIN
        // TransactionManager transactionManager = new TransactionManager();
        // ArrayList<BankAccount> bankAccounts = new ArrayList<>(); // Create an empty list of BankAccount
        // transactionManager.deposit(bankAccounts);  // Pass the list to the deposit method
        // Step 2: Retrieve admin details including PIN

    }
}
