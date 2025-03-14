    package user;

    import bankaccount.BankAccount;
    import database.BankAccountDAO;
    import database.CustomerDAO;
    import java.time.LocalDate;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.Scanner;
    import transaction.TransactionManager;

    public class Customer extends User {

        private int customerId;
        private BankAccount bankAccount;
        public ArrayList<BankAccount> bankAccounts;
        private int pin; // Store the PIN

        public Customer() {
            // Call the super constructor with default values for the User class
            super("", "", "", "", "", "", LocalDate.now(), "");
            this.bankAccounts = new ArrayList<>();
            this.pin = pin; // Default PIN
            this.customerId = getUserId(); // Generate a unique user ID
        }

        public Customer(String lastName, String firstName, String email, String password, String confirmPassword,
                String phoneNumber, LocalDate birthDate, String governmentId) {
            super(lastName, firstName, email, password, confirmPassword, phoneNumber, birthDate, governmentId);
            this.bankAccounts = new ArrayList<>();
            // Generate PIN when a new customer is created
            this.customerId = getUserId();
        }

        public boolean isInputInvalid(String input){
            return input.matches(".*\\d.*");
        }

        public void setPassword(String password) {
            // You can add password validation here (e.g., minimum length, special characters, etc.)
            this.password = password;
        }
        
        public String getPassword() {
            return this.password;
        }

        public int getCustomerId() {
            return this.customerId;
        }

        public void setCustomerId(int customerId) {
        this.customerId = customerId;
        }

        // Method to register a new customer
        public void register(String lastName, String firstName, String email, String password, String confirmPassword, String phoneNumber, LocalDate birthDate, String governmentId) {
            CustomerDAO customerDAO = new CustomerDAO();
        
            try {
                // Validate last name
                if (lastName.isEmpty()) {
                    throw new CustomerException.EmptyFieldException("Last name");
                }
                if (isInputInvalid(lastName)) {
                    throw new CustomerException.InvalidInputException("Last name");
                }
        
                // Validate first name
                if (firstName.isEmpty()) {
                    throw new CustomerException.EmptyFieldException("First name");
                }
                if (isInputInvalid(firstName)) {
                    throw new CustomerException.InvalidInputException("First name");
                }
        
                // Validate email
                if (!isEmailValid(email)) {
                    throw new CustomerException.InvalidEmailException();
                }
        
                // Validate password
                if (!password.equals(confirmPassword)) {
                    throw new CustomerException.PasswordMismatchException();
                }
        
                // Validate phone number
                if (!isPhoneNumberValid(phoneNumber)) {
                    throw new CustomerException.InvalidPhoneNumberException();
                }
        
                // Parse birth date
                // Validate birth date (already provided as LocalDate)
    if (birthDate.isAfter(LocalDate.now().minusYears(16))) {
        throw new CustomerException.UnderageException();
    }
        
                // Validate government ID
                if (governmentId.isEmpty()) {
                    throw new CustomerException.EmptyFieldException("Government ID");
                }
                if (!isGovernmentIdValid(governmentId)) {
                    throw new CustomerException.InvalidGovernmentIdException();
                }
        
                // Check for duplicate customers
                List<Customer> customers = customerDAO.getAllCustomers();
                for (Customer customer : customers) {
                    if (customer.getGovernmentId().equals(governmentId)) {
                        throw new CustomerException.CustomerAlreadyExistsException();
                    }
                    if (customer.getEmail().equals(email)) {
                        throw new CustomerException.CustomerAlreadyExistsException();
                    }
                    if (customer.getPhoneNumber().equals(phoneNumber)) {
                        throw new CustomerException.CustomerAlreadyExistsException();
                    }
                }
        
                // Create new customer and save it
                Customer customer = new Customer(lastName, firstName, email, password, confirmPassword, phoneNumber, birthDate, governmentId);
                customerDAO.saveCustomer(customer); // Store customer in DB
        
            } catch (CustomerException e) {
                System.out.println("Registration failed: " + e.getMessage());
            }
        }
        
        
        
        public String getFullName(){
            return getFirstName() + " " + getLastName();
        }  

        // Method to authenticate PIN
        private boolean authenticatePin(int enteredPin) throws Exception {
            if (enteredPin == this.pin) {
                return true;
            } else {
                throw new Exception("Incorrect PIN.");
            }
        }

        public void viewOwnAccount() {
            // Display the personal information of the customer
            System.out.println("===== Account Details =====");
            System.out.println("User ID: " + getUserId());
            System.out.println("Name: " + getFirstName() + " " + getLastName());
            System.out.println("Email: " + getEmail());
            System.out.println("Phone Number: " + getPhoneNumber());
            System.out.println("Birth Date: " + getBirthDate());
            System.out.println("Government ID: " + getMaskedGovernmentId());
        
            // Display the bank account details
            System.out.println("\n===== Bank Accounts =====");
            if (bankAccounts.isEmpty()) {
                System.out.println("No bank accounts found.");
            } else {
                for (BankAccount account : bankAccounts) {
                    System.out.println(account.toString()); // Assuming `BankAccount` class has a `toString()` method
                }
            }
        }
        System.out.println("Enter your current PIN to change it: ");
            }
        
            switch (choice) {
                case 1:
                    System.out.print("Enter your new email: ");
                    String newEmail = scanner.nextLine();
                    if (isEmailValid(newEmail)) {
                        currentCustomer.setEmail(newEmail); // Update email
                        customerDAO.updateCustomer(currentCustomer); // Save changes
                        System.out.println("Email updated successfully!");
                    } else {
                        System.out.println("Invalid email format.");
                    }
                    break;
                case 2:
                    System.out.print("Enter your new phone number: ");
                    String newPhoneNumber = scanner.nextLine();
                    if (isPhoneNumberValid(newPhoneNumber)) {
                        currentCustomer.setPhoneNumber(newPhoneNumber); // Update phone number
                        customerDAO.updateCustomer(currentCustomer); // Save changes
                        System.out.println("Phone number updated successfully!");
                    } else {
                        System.out.println("Invalid phone number format.");
                    }
                    break;
                case 3:
                    System.out.print("Enter your new password: ");
                    String newPassword = scanner.nextLine();
                    System.out.print("Confirm your new password: ");
                    String confirmPassword = scanner.nextLine();
        
                    if (newPassword.equals(confirmPassword)) {
                        currentCustomer.setPassword(newPassword); // Update password
                        customerDAO.updateCustomer(currentCustomer); // Save changes
                        System.out.println("Password updated successfully!");
                    } else {
                        System.out.println("Passwords do not match. Please try again.");
                    }
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        }
        
        
        
        

        // Method to allow customers to change their PIN
        public void changePin() {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter your current PIN to change it: ");
            String currentPin = scanner.nextLine();
            try {
                if (authenticatePin(Integer.parseInt(currentPin))) {
                    System.out.println("Enter your new PIN: ");
                    int newPin = scanner.nextInt();
                    this.pin = newPin; // Update PIN
                    System.out.println("PIN changed successfully!");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            scanner.close();
        }

        private boolean authenticateWithRetries(Scanner scanner) {
            int attempts = 3;
            CustomerDAO customerDAO = new CustomerDAO();
        
            while (attempts > 0) {
                System.out.print("Enter your email or phone number: ");
                String emailOrPhone = scanner.nextLine();
        
                System.out.print("Enter your password: ");
                String enteredPassword = scanner.nextLine();
        
                try {
                    // Retrieve customer by email or phone
                    Customer customer = customerDAO.getCustomerByEmailOrPhone(emailOrPhone);
        
                    if (customer != null && customer.getPassword().equals(enteredPassword)) {
                        return true; // Authentication successful
                    } else {
                        attempts--;
                        System.out.println("Invalid credentials.");
                        if (attempts > 0) {
                            System.out.println("You have " + attempts + " attempts remaining.");
                        } else {
                            System.out.println("Too many failed attempts. Access denied.");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return false; // Authentication failed
        }
        
        
        
        // Method to view account details and associated bank accounts
        public void viewBankAccountDetails() {
            System.out.println("===== Account Details =====");
            System.out.println("User ID: " + getUserId());
            System.out.println("Name: " + getFirstName() + " " + getLastName());
            System.out.println("Email: " + getEmail());
            System.out.println("Phone Number: " + getPhoneNumber());
            System.out.println("Birth Date: " + getBirthDate());
            System.out.println("Government ID: " + getMaskedGovernmentId());
            System.out.println("Number of Bank Accounts: " + bankAccounts.size());
            System.out.println("\n===== Bank Accounts =====");
            if (bankAccounts.isEmpty()) {
                System.out.println("No bank accounts found.");
            } else {
                for (BankAccount account : bankAccounts) {
                    System.out.println(account.toString());
                }
            }
        }

    
        
        public void updatePassword() {
            Scanner scanner = new Scanner(System.in);
        
            System.out.println("Enter your current password: ");
            String currentPassword = scanner.nextLine();
        
            // Validate the current password
            if (currentPassword.equals(getPassword())) {
                System.out.println("Enter your new password: ");
                String newPassword = scanner.nextLine();
                System.out.println("Confirm your new password: ");
                String confirmPassword = scanner.nextLine();
        
                if (newPassword.equals(confirmPassword)) {
                    // Update the password in the Customer object
                    setPassword(newPassword);
        
                    // Assuming CustomerDAO has a method to update the password in the database
                    CustomerDAO customerDAO = new CustomerDAO();
                    customerDAO.updatePasswordInDatabase(getUserId(), newPassword);
        
                    System.out.println("Password updated successfully!");
                } else {
                    System.out.println("Passwords do not match. Please try again.");
                }
            } else {
                System.out.println("Incorrect current password.");
            }
        
            scanner.close();
        }
        

        public void createBankAccount() {
            Scanner input = new Scanner(System.in); // DO NOT CLOSE THIS SCANNER
            System.out.println("Select an account type");
            System.out.println("1. Saving");
            System.out.println("2. Current");
            System.out.println("3. Checking");
            System.out.print("Select an option -> ");
            int options = input.nextInt();
            input.nextLine(); // Consume the newline
            
            BankAccountDAO bankAccountDAO = new BankAccountDAO();
            String accountType;
            
            switch (options) {
                case 1:
                    accountType = "Saving";
                    break;
                case 2:
                    accountType = "Current";
                    break;
                case 3:
                    accountType = "Checking";
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
                    return; // Exit the method without adding an account
            }

            // Ask for PIN during bank account creation
            System.out.println("Enter your PIN (4 digits): ");
            this.pin = input.nextInt();
            String pinStr = String.valueOf(pin);
            if (pinStr.length() != 4 || !pinStr.matches("\\d+")) {
                System.out.println("Invalid PIN format. Please ensure it is 4 digits.");
                return; // Exit the method if PIN is invalid
            }

            // Confirm PIN
            System.out.println("Confirm your PIN: ");
            int confirmPin = input.nextInt();
            if (pin != confirmPin) {
                System.out.println("PIN mismatch. Please try again.");
                return; // Exit the method if PINs do not match
            }

            // Create a new bank account using the current logged-in customer's information
            BankAccount newAccount = new BankAccount(
                this.customerId,        // Use the current customer's ID
                this.firstName,         // Use the current customer's first name
                this.lastName,          // Use the current customer's last name
                accountType,            // Use the selected account type
                "Active",               // Set status as active
                this.pin                // Use the newly provided PIN
            );
            
            // Generate and set a unique account number using the method in BankAccount
            int accountNumber = BankAccount.generateAccountNumber();
            newAccount.setAccountNumber(accountNumber);
            
            // Save the new bank account to the database
            bankAccountDAO.saveBankAccount(newAccount);
            
            // Add the new account to the customer's list of accounts
            this.bankAccounts.add(newAccount);
            
            System.out.println("Bank account created successfully!");
            System.out.println("Your new account number is: " + newAccount.getAccountNumber());
        }
        
        

        // Add this method to the Customer class in user/Customer.java

    public static Customer login(String emailOrPhone, String password) {
        Scanner scanner = new Scanner(System.in);
        CustomerDAO customerDAO = new CustomerDAO();
        
        try {
            // First, retrieve the customer by their email or phone number
            Customer customer = customerDAO.getCustomerByEmailOrPhone(emailOrPhone);
            
            // If no customer is found with the given email or phone
            if (customer == null) {
                System.out.println("No account found with this email or phone number.");
                return null;
            }
            
            // Verify password
            if (customer.getPassword().equals(password)) {
                System.out.println("Login successful. Welcome, " + customer.getFirstName() + "!");
                
                // Load customer's bank accounts
                BankAccountDAO bankAccountDAO = new BankAccountDAO();
                ArrayList<BankAccount> accounts = bankAccountDAO.getBankAccountsByCustomerId(customer.getCustomerId());
                customer.setBankAccounts(accounts);
                
                return customer;
            } else {
                System.out.println("Incorrect password.");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Login failed: " + e.getMessage());
            return null;
        }
    }

    // Add this setter method to the Customer class
    public void setBankAccounts(ArrayList<BankAccount> accounts) {
        this.bankAccounts = accounts;
    }

        // Methods to handle deposits, withdrawals, and transfers
        public void deposit() {
            // Use the TransactionManager to handle deposit logic
            TransactionManager transactionManager = new TransactionManager();
            transactionManager.deposit(bankAccounts);
        }

        public void withdraw() {
            // Use the TransactionManager to handle withdraw logic
            TransactionManager transactionManager = new TransactionManager();
            transactionManager.withdraw(bankAccounts);
        }

        public void transfer() {
            // Use the TransactionManager to handle transfer logic
            TransactionManager transactionManager = new TransactionManager();
            transactionManager.transfer(bankAccounts);
        }

        public ArrayList<BankAccount> getBankAccounts() {
            return bankAccounts;
        }

        public void setUserId(int int1) {
            // TODO Auto-generated method stub
            this.customerId = int1;
        }

        public int getPin() {
            return this.pin;
        }

        @Override
        public String toString(){
            BankAccountDAO bankAccountDAO = new BankAccountDAO();
            return toString() + "Customer ID: " + customerId + "\n" + bankAccountDAO.getAllBankAccounts();
        }
    }
