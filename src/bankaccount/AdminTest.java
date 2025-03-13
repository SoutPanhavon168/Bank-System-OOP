package bankaccount;
import user.Admin;
public class AdminTest {
    public static void main(String[] args) {
        // Step 1: Create a new Admin and register
        Admin admin = new Admin();
        admin.admin_login();  // Ensure register() collects first name, last name, and PIN

        // Step 2: Retrieve admin details including PIN

    }
}
