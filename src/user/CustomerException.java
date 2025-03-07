package user;

public class CustomerException extends Exception {

    public CustomerException(String message) {
        super(message);
    }

    // Nested custom exception classes
    public static class EmptyFieldException extends CustomerException {
        public EmptyFieldException(String field) {
            super(field + " cannot be empty.");
        }
    }

    public static class InvalidEmailException extends CustomerException {
        public InvalidEmailException() {
            super("Invalid email format.");
        }
    }

    public static class PasswordMismatchException extends CustomerException {
        public PasswordMismatchException() {
            super("Passwords do not match.");
        }
    }

    public static class InvalidPhoneNumberException extends CustomerException {
        public InvalidPhoneNumberException() {
            super("Invalid phone number format.");
        }
    }

    public static class UnderageException extends CustomerException {
        public UnderageException() {
            super("You must be at least 16 years old to register.");
        }
    }

    public static class InvalidGovernmentIdException extends CustomerException {
        public InvalidGovernmentIdException() {
            super("Invalid government ID format.");
        }
    }
    

    public static class InvalidBirthDateException extends CustomerException {
        public InvalidBirthDateException() {
            super("Invalid birth date format.");
        }
    }

    public static class InvalidInputException extends CustomerException {
        public InvalidInputException(String inputType) {
            super("Invalid input for " + inputType + "Please enter a valid value.");
        }
    }

    public static class DuplicatePhoneNumberException extends CustomerException {
        public DuplicatePhoneNumberException() {
            super("Phone number is already registered.");
        }
    }
    
    public static class DuplicateGovernmentIdException extends CustomerException {
        public DuplicateGovernmentIdException() {
            super("Government ID is already registered.");
        }
    }
    
}
