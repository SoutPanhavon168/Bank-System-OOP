package user;

import Interfaces.Authentication;
import database.BankAccountDAO;
import database.CustomerDAO;
import java.util.Scanner;
import user.staff.Staff;

public class BankingApp {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {

            System.out.println("=== Login ===");
            System.out.print("Enter email: ");
            String email = scanner.nextLine();

            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            Authentication userAuth = new Staff(); // Testing Staff login
            User loggedInUser = userAuth.login(email, password);

            if (loggedInUser != null) {
                System.out.println("Welcome, " + loggedInUser.getFirstName() + "!");
                if (loggedInUser instanceof Staff) {
                    System.out.println("Redirecting to Staff Dashboard...");
                    staffDashboard(scanner, (Staff) loggedInUser);
                }
            } else {
                System.out.println("Login failed. Please try again.");
            }
        }
    }

    private static void staffDashboard(Scanner scanner, Staff staff) {
        CustomerDAO customerDAO = new CustomerDAO();
        BankAccountDAO bankAccountDAO = new BankAccountDAO();

        while (true) {
            int choice = scanner.nextInt();
            System.out.println("\n=== Staff Dashboard ===");
            System.out.println("1. View Customer Details");
            System.out.println("2. Update Customer Information");
            System.out.println("3. Freeze/Unfreeze Bank Account");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");

            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    staff.viewCustomerDetails();
                    break;

                case 2:
                    staff.updateCustomerAccount();
                    break;

                case 3:
                    System.out.print("Enter Account ID: ");
                    int accountId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Freeze (F) or Unfreeze (U) account? ");
                    String action = scanner.nextLine().toUpperCase();
                    if (action.equals("F")) {
                        bankAccountDAO.freezeBankAccount(accountId);
                        System.out.println("Account frozen.");
                    } else if (action.equals("U")) {
                        bankAccountDAO.unfreezeBankAccount(accountId);
                        System.out.println("Account unfrozen.");
                    } else {
                        System.out.println("Invalid option.");
                    }
                    break;

                case 4:
                    System.out.println("Logging out...");
                    return;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
