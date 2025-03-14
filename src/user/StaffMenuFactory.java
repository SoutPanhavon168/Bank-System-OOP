package user;

public class StaffMenuFactory {
    
    public static void displayMenuForRole(Staff staff) {
        switch (staff.getRole()) {
            case MANAGER:
                ManagerMenu managerMenu = new ManagerMenu(staff);
                managerMenu.displayMenu();
                break;
            case CUSTOMER_SERVICE:
                CustomerServiceMenu customerServiceMenu = new CustomerServiceMenu(staff);
                customerServiceMenu.displayMenu();
                break;
            case TELLER:
                TellerMenu tellerMenu = new TellerMenu(staff);
                tellerMenu.displayMenu();
                break;
            default:
                System.out.println("Error: Invalid staff role detected.");
                break;
        }
    }
    
    // Example usage in main application
    public static void main(String[] args) {
        // This would be replaced with actual login logic
        Staff staff = new Staff();
        staff.setRole(Staff.StaffRole.CUSTOMER_SERVICE);
        
        // Display the appropriate menu based on staff role
        displayMenuForRole(staff);
    }
}