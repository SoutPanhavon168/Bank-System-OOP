package user;

public class StaffException extends Exception {

    public StaffException(String message) {
        super(message);
    }

    // Nested custom exception classes
    public static class UnauthorizedAccessException extends StaffException {
        public UnauthorizedAccessException() {
            super("Access denied: Insufficient permissions.");
        }
    }

    public static class InvalidStaffRoleException extends StaffException {
        public InvalidStaffRoleException() {
            super("Invalid staff role.");
        }
    }

    public static class CustomerNotFoundException extends StaffException {
        public CustomerNotFoundException() {
            super("Customer not found.");
        }
    }

    public static class InvalidChoiceException extends StaffException {
        public InvalidChoiceException() {
            super("Invalid input choice. Please enter a valid option.");
        }
    }

    public static class InvalidEmailException extends StaffException {
        public InvalidEmailException() {
            super("Invalid email format.");
        }
    }

    public static class InvalidPhoneNumberException extends StaffException {
        public InvalidPhoneNumberException() {
            super("Invalid phone number format.");
        }
    }

    public static class InvalidPasswordException extends StaffException {
        public InvalidPasswordException() {
            super("Invalid password. Please ensure the password meets the required criteria.");
        }
    }

    public static class InvalidAccountIdException extends StaffException {
        public InvalidAccountIdException() {
            super("Invalid account ID. The account does not exist.");
        }
    }


    public static class InvalidLoanIdException extends StaffException {
        public InvalidLoanIdException() {
            super("Invalid loan ID. The loan does not exist.");
        }
    }

    public static class InvalidTransactionException extends StaffException {
        public InvalidTransactionException() {
            super("Invalid transaction data. Please check the input values.");
        }
    }
    
    public static class DatabaseAccessException extends StaffException {
        public DatabaseAccessException(String message, Throwable cause) {
            super(message);
        }
    }

}
