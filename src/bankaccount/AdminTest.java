package bankaccount;
import user.Admin;
import java.util.ArrayList;
import bankaccount.BankAccount;
import transaction.TransactionManager;
public class AdminTest {
    public static void main(String[] args) {
        // Step 1: Create a new Admin and register
        // Admin admin = new Admin();
        // admin.updateStaffAccount();  // Ensure register() collects first name, last name, and PIN
        TransactionManager transactionManager = new TransactionManager();// Create an empty list of BankAccount
        ArrayList<BankAccount> bankAccounts = new ArrayList<BankAccount>(); // Initialize the list of BankAccount
        transactionManager.deposit(bankAccounts);  // Pass the list to the deposit method

    }
}
