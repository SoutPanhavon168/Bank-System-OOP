package GUI.staff_gui;

import database.StaffDAO;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import user.staff.Staff;

public class StaffLoginForm extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton cancelButton;
    private Color brandBlue = new Color(0, 102, 204);
    
    public StaffLoginForm() {
        setTitle("Bank Staff Portal - Login");
        setSize(1024, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
        
        initializeComponents();
        addActionListeners();
    }
    
    private void initializeComponents() {
        // Header
        JLabel headerLabel = new JLabel("BANK STAFF PORTAL", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 36));
        headerLabel.setForeground(brandBlue);
        headerLabel.setBounds(262, 100, 500, 50);
        add(headerLabel);
        
        // Login panel
        JPanel loginPanel = new JPanel(null);
        loginPanel.setBounds(312, 200, 400, 350);
        loginPanel.setBorder(BorderFactory.createLineBorder(brandBlue, 2));
        
        JLabel loginLabel = new JLabel("Staff Login", JLabel.CENTER);
        loginLabel.setFont(new Font("Arial", Font.BOLD, 24));
        loginLabel.setForeground(brandBlue);
        loginLabel.setBounds(100, 30, 200, 40);
        loginPanel.add(loginLabel);
        
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        emailLabel.setBounds(50, 100, 100, 30);
        loginPanel.add(emailLabel);
        
        emailField = new JTextField();
        emailField.setFont(new Font("Arial", Font.PLAIN, 16));
        emailField.setBounds(50, 130, 300, 40);
        loginPanel.add(emailField);
        
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordLabel.setBounds(50, 180, 100, 30);
        loginPanel.add(passwordLabel);
        
        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordField.setBounds(50, 210, 300, 40);
        loginPanel.add(passwordField);
        
        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setBounds(50, 280, 140, 40);
        loginButton.setBackground(brandBlue);
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginPanel.add(loginButton);
        
        cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 16));
        cancelButton.setBounds(210, 280, 140, 40);
        cancelButton.setBackground(Color.LIGHT_GRAY);
        cancelButton.setForeground(Color.BLACK);
        cancelButton.setFocusPainted(false);
        loginPanel.add(cancelButton);
        
        add(loginPanel);
        
        // Version label
        JLabel versionLabel = new JLabel("Staff Portal v1.0", JLabel.RIGHT);
        versionLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        versionLabel.setBounds(824, 698, 150, 20);
        add(versionLabel);
    }
    
    private void addActionListeners() {
        loginButton.addActionListener(e -> handleLogin());
        cancelButton.addActionListener(e -> System.exit(0));
        
        // Allow login by pressing Enter in password field
        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    handleLogin();
                }
            }
        });
    }
    
    private void handleLogin() {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both email and password.", 
                                         "Login Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            StaffDAO staffDAO = new StaffDAO();
            Staff staff = staffDAO.verifyCredentials(email, password);
            
            if (staff != null) {
                // Check if the staff is an admin or has manager role
                boolean isAdmin = (email.equals("admin") || staff.getRole() == Staff.StaffRole.MANAGER);
                
                // Show role-based message
                String message = isAdmin ? "Admin Login Successful!" : "Employee Login Successful!";
                JOptionPane.showMessageDialog(this, message, 
                                              "Login Success", JOptionPane.INFORMATION_MESSAGE);
                
                // Open the dashboard with role information
                StaffDashboard dashboard = new StaffDashboard(staff, isAdmin);
                dashboard.setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid email or password.", 
                                             "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Login error: " + ex.getMessage(), 
                                         "System Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> new StaffLoginForm().setVisible(true));
    }
}