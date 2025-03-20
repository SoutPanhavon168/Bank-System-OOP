package GUI.staff_gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import user.staff.Staff;
import user.staff.Staff.StaffRole;

public class StaffDashboard extends JFrame {
    private Staff currentStaff;
    private boolean isAdmin;
    private JPanel mainPanel;
    private JPanel menuPanel;
    private Color brandBlue = new Color(0, 102, 204);
    private Color darkBlue = new Color(0, 51, 153);
    
    // Menu buttons
    private JButton accountsButton;
    private JButton customersButton;
    private JButton transactionsButton;
    private JButton reportsButton;
    private JButton staffManagementButton;
    private JButton systemSettingsButton;
    private JButton profileButton;
    private JButton logoutButton;
    
    public StaffDashboard(Staff staff, boolean isAdmin) {
        this.currentStaff = staff;
        this.isAdmin = isAdmin;
        
        setTitle("Bank Staff Portal - Dashboard");
        setSize(1024, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        createHeader();
        createMenuPanel();
        createMainPanel();
        
        // Show welcome screen initially
        showWelcomeScreen();
    }
    
    private void createHeader() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(darkBlue);
        headerPanel.setPreferredSize(new Dimension(1024, 80));
        headerPanel.setBorder(new EmptyBorder(10, 20, 10, 20));
        
        JLabel titleLabel = new JLabel("BANK STAFF PORTAL");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        JPanel userInfoPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        userInfoPanel.setOpaque(false);
        
        String role = currentStaff.getRole().toString();
        String roleDisplay = isAdmin ? role + " (Admin)" : role;
        
        JLabel staffInfoLabel = new JLabel(currentStaff.getFullName() + " | " + roleDisplay);
        staffInfoLabel.setFont(new Font("Arial", Font.BOLD, 14));
        staffInfoLabel.setForeground(Color.WHITE);
        userInfoPanel.add(staffInfoLabel);
        
        headerPanel.add(userInfoPanel, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);
    }
    
    private void createMenuPanel() {
        menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(brandBlue);
        menuPanel.setPreferredSize(new Dimension(250, 688));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        
        // Create menu buttons based on staff role
        boolean isManager = currentStaff.hasAccess(StaffRole.MANAGER);
        boolean isTeller = currentStaff.hasAccess(StaffRole.TELLER);
        boolean isCustomerService = currentStaff.hasAccess(StaffRole.CUSTOMER_SERVICE);
        
        accountsButton = createMenuButton("Bank Accounts", "accounts.png");
        customersButton = createMenuButton("Customers", "customers.png");
        transactionsButton = createMenuButton("Transactions", "transactions.png");
        reportsButton = createMenuButton("Reports", "reports.png");
        staffManagementButton = createMenuButton("Staff Management", "staff.png");
        systemSettingsButton = createMenuButton("System Settings", "settings.png");
        profileButton = createMenuButton("My Profile", "profile.png");
        logoutButton = createMenuButton("Logout", "logout.png");
        
        // Add buttons based on role permissions
        menuPanel.add(accountsButton);
        menuPanel.add(customersButton);
        menuPanel.add(transactionsButton);
        
        // Reports available to managers
        if (isManager) {
            menuPanel.add(reportsButton);
        }
        
        // Staff management and system settings for admin/manager only
        if (isAdmin) {
            menuPanel.add(staffManagementButton);
            menuPanel.add(systemSettingsButton);
        }
        
        menuPanel.add(Box.createVerticalGlue());
        menuPanel.add(profileButton);
        menuPanel.add(logoutButton);
        
        // Add action listeners
        accountsButton.addActionListener(e -> showAccountsPanel());
        customersButton.addActionListener(e -> showCustomersPanel());
        transactionsButton.addActionListener(e -> showTransactionsPanel());
        
        if (isManager) {
            reportsButton.addActionListener(e -> showReportsPanel());
        }
        
        if (isAdmin) {
            staffManagementButton.addActionListener(e -> showStaffManagementPanel());
            systemSettingsButton.addActionListener(e -> showSystemSettingsPanel());
        }
        
        profileButton.addActionListener(e -> showProfilePanel());
        logoutButton.addActionListener(e -> handleLogout());
        
        add(menuPanel, BorderLayout.WEST);
    }

    
    
    private JButton createMenuButton(String text, String iconName) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(230, 50));
        button.setPreferredSize(new Dimension(230, 50));
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(brandBlue);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(0, 20, 0, 0),
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 0, 0, 0)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
            )
        ));
        
        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(darkBlue);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(brandBlue);
            }
        });
        
        return button;
    }
    
    private void createMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private void showWelcomeScreen() {
        clearMainPanel();
        
        JPanel welcomePanel = new JPanel(new BorderLayout());
        welcomePanel.setBackground(Color.WHITE);
        welcomePanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        
        String welcomeMessage = "<html><div style='text-align: center;'>"
                + "<h1 style='color: #0066CC;'>Welcome, " + currentStaff.getFirstName() + "!</h1>"
                + "<h2>Bank Staff Portal</h2>"
                + "<p>Current Date: " + java.time.LocalDate.now() + "</p>"
                + "<p>Staff ID: " + currentStaff.getStaffId() + "</p>"
                + "<p>Role: " + currentStaff.getRole() + "</p>"
                + "<br><p>Please select an option from the menu to get started.</p>"
                + "</div></html>";
        
        JLabel messageLabel = new JLabel(welcomeMessage);
        messageLabel.setHorizontalAlignment(JLabel.CENTER);
        welcomePanel.add(messageLabel, BorderLayout.CENTER);
        
        mainPanel.add(welcomePanel, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }
    
    private void clearMainPanel() {
        mainPanel.removeAll();
        mainPanel.revalidate();
        mainPanel.repaint();
    }
    
    private void handleLogout() {
        int choice = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to logout?", 
                "Confirm Logout", 
                JOptionPane.YES_NO_OPTION);
        
        if (choice == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this, 
                "You have been successfully logged out.", 
                "Logout Successful", 
                JOptionPane.INFORMATION_MESSAGE);
            
            new StaffLoginForm().setVisible(true);
            this.dispose();
        }
    }
    
    private void showAccountsPanel() {
        clearMainPanel();
        
        // Create accounts panel
        AccountsPanel accountsPanel = new AccountsPanel(currentStaff);
        mainPanel.add(accountsPanel, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }
    
    private void showCustomersPanel() {
        clearMainPanel();
        
        // Create customers panel
        CustomersPanel customersPanel = new CustomersPanel(currentStaff);
        mainPanel.add(customersPanel, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }
    
    private void showTransactionsPanel() {
        clearMainPanel();
        
        // Create transactions panel
        TransactionsPanel transactionsPanel = new TransactionsPanel(currentStaff);
        mainPanel.add(transactionsPanel, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }
    
    private void showReportsPanel() {
        clearMainPanel();
        
        // Create reports panel (only for managers)
        if (currentStaff.hasAccess(StaffRole.MANAGER)) {
            JPanel reportsPanel = new JPanel(new BorderLayout());
            reportsPanel.setBackground(Color.WHITE);
            
            JLabel headerLabel = new JLabel("Reports Dashboard", JLabel.CENTER);
            headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
            headerLabel.setForeground(brandBlue);
            headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
            
            reportsPanel.add(headerLabel, BorderLayout.NORTH);
            
            // Sample reports list
            JPanel reportsListPanel = new JPanel(new GridLayout(0, 1, 0, 10));
            reportsListPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
            reportsListPanel.setBackground(Color.WHITE);
            
            String[] reportNames = {
                "Monthly Transaction Summary", 
                "Customer Activity Report",
                "Account Status Report",
                "Staff Performance Metrics",
                "Fraud Detection Report"
            };
            
            for (String report : reportNames) {
                JButton reportButton = new JButton(report);
                reportButton.setFont(new Font("Arial", Font.PLAIN, 16));
                reportButton.setPreferredSize(new Dimension(300, 50));
                reportButton.setBackground(new Color(240, 240, 240));
                reportButton.setFocusPainted(false);
                
                reportButton.addActionListener(e -> 
                    JOptionPane.showMessageDialog(this, 
                        "Report '" + report + "' is being generated...", 
                        "Report Generation", 
                        JOptionPane.INFORMATION_MESSAGE)
                );
                
                reportsListPanel.add(reportButton);
            }
            
            reportsPanel.add(new JScrollPane(reportsListPanel), BorderLayout.CENTER);
            mainPanel.add(reportsPanel, BorderLayout.CENTER);
        } else {
            showAccessDeniedMessage();
        }
        
        mainPanel.revalidate();
        mainPanel.repaint();
    }
    
    private void showStaffManagementPanel() {
        clearMainPanel();
        
        // Create staff management panel (only for admin)
        if (isAdmin) {
            // Create and add the StaffManagementPanel
            StaffManagementPanel staffManagementPanel = new StaffManagementPanel(currentStaff);
            mainPanel.add(staffManagementPanel, BorderLayout.CENTER);
        } else {
            showAccessDeniedMessage();
        }
        
        mainPanel.revalidate();
        mainPanel.repaint();
    }
    
    
    private void showSystemSettingsPanel() {
        clearMainPanel();
        
        // Create system settings panel (only for admin)
        if (isAdmin) {
            // For now, display a placeholder message
            JPanel settingsPanel = new JPanel(new BorderLayout());
            settingsPanel.setBackground(Color.WHITE);
            
            JLabel headerLabel = new JLabel("System Settings", JLabel.CENTER);
            headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
            headerLabel.setForeground(brandBlue);
            headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
            
            settingsPanel.add(headerLabel, BorderLayout.NORTH);
            
            JPanel messagePanel = new JPanel();
            messagePanel.setBackground(Color.WHITE);
            JLabel messageLabel = new JLabel("System Settings feature is not implemented yet.");
            messageLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            messagePanel.add(messageLabel);
            
            settingsPanel.add(messagePanel, BorderLayout.CENTER);
            mainPanel.add(settingsPanel, BorderLayout.CENTER);
        } else {
            showAccessDeniedMessage();
        }
        
        mainPanel.revalidate();
        mainPanel.repaint();
    }
    
    
    private void showProfilePanel() {
        clearMainPanel();
        
        JPanel profilePanel = new JPanel(new BorderLayout());
        profilePanel.setBackground(Color.WHITE);
        profilePanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        
        JLabel headerLabel = new JLabel("Staff Profile", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(brandBlue);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        profilePanel.add(headerLabel, BorderLayout.NORTH);
        
        JPanel infoPanel = new JPanel(new GridLayout(0, 2, 10, 15));
        infoPanel.setBackground(Color.WHITE);
        
        addProfileField(infoPanel, "Staff ID:", String.valueOf(currentStaff.getStaffId()));
        addProfileField(infoPanel, "Full Name:", currentStaff.getFullName());
        addProfileField(infoPanel, "Position:", currentStaff.getPosition());
        addProfileField(infoPanel, "Email:", currentStaff.getEmail());
        addProfileField(infoPanel, "Phone:", currentStaff.getPhoneNumber());
        addProfileField(infoPanel, "Birth Date:", currentStaff.getBirthDate().toString());
        
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);
        centerPanel.add(infoPanel, BorderLayout.NORTH);
        
        profilePanel.add(centerPanel, BorderLayout.CENTER);
        
        // Add change password button
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actionPanel.setBackground(Color.WHITE);
        
        JButton changePasswordButton = new JButton("Change Password");
        changePasswordButton.setFont(new Font("Arial", Font.BOLD, 14));
        changePasswordButton.setBackground(brandBlue);
        changePasswordButton.setForeground(Color.WHITE);
        changePasswordButton.setFocusPainted(false);
        
        changePasswordButton.addActionListener(e -> {
            // Password change dialog
            JPasswordField currentPassword = new JPasswordField();
            JPasswordField newPassword = new JPasswordField();
            JPasswordField confirmPassword = new JPasswordField();
            
            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("Current Password:"));
            panel.add(currentPassword);
            panel.add(new JLabel("New Password:"));
            panel.add(newPassword);
            panel.add(new JLabel("Confirm New Password:"));
            panel.add(confirmPassword);
            
            int result = JOptionPane.showConfirmDialog(this, panel, "Change Password", 
                                                     JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            
            if (result == JOptionPane.OK_OPTION) {
                // Here you would implement the actual password change logic
                JOptionPane.showMessageDialog(this, "Password changed successfully!", 
                                             "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        actionPanel.add(changePasswordButton);
        profilePanel.add(actionPanel, BorderLayout.SOUTH);
        
        mainPanel.add(profilePanel, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }
    
    private void addProfileField(JPanel panel, String label, String value) {
        JLabel labelComponent = new JLabel(label);
        labelComponent.setFont(new Font("Arial", Font.BOLD, 14));
        
        JLabel valueComponent = new JLabel(value);
        valueComponent.setFont(new Font("Arial", Font.PLAIN, 14));
        
        panel.add(labelComponent);
        panel.add(valueComponent);
    }
    
    private void showAccessDeniedMessage() {
        JPanel accessDeniedPanel = new JPanel(new BorderLayout());
        accessDeniedPanel.setBackground(Color.WHITE);
        
        JLabel messageLabel = new JLabel("Access Denied: Insufficient permissions", JLabel.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 18));
        messageLabel.setForeground(Color.RED);
        
        accessDeniedPanel.add(messageLabel, BorderLayout.CENTER);
        mainPanel.add(accessDeniedPanel, BorderLayout.CENTER);
    }
}