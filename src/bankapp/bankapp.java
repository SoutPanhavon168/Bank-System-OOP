package bankapp;
<<<<<<< HEAD
import bankaccount.BankAccount;
import user.Customers;

=======
import bankAccount.*;
>>>>>>> origin/main
import java.time.LocalDate;
import java.util.Scanner;
import user.Customers;

public class bankapp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BankAccount bankAccount = new BankAccount();
        Customers customer = new Customers("Doe", "John", "john@example.com", "password123", "password123", "1234567890", LocalDate.of(1990, 1, 1), "ID123456");
        
        while (true) {
            System.out.println("\n===== Customer Menu =====");
            System.out.println("1. View Account");
            System.out.println("2. Update Account");
            System.out.println("3. Create Bank Account");
            System.out.println("4. Deposit");
            System.out.println("5. Withdraw");
            System.out.println("6. Transfer");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();  // Read choice
            scanner.nextLine(); // Consume the newline left by nextInt()

            switch (choice) {
                case 1:
                    customer.viewOwnAccount();
                    break;
                case 2:
                    customer.updateOwnAccount();
                    break;
                case 3:
                    customer.createBankAccount();
                    break;
                case 4:
                    customer.deposit();
                    break;
                case 5:
                    customer.withdraw();
                    break;
                case 6:
                    customer.transfer();
                    break;
                case 7:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 7.");
            }
        }
    }
}
