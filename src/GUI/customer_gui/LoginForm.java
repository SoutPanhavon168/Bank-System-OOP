package GUI.customer_gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import user.Customer;

public class LoginForm extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private JButton cancelButton;
    private Color brandGreen = new Color(0, 175, 0);

    public LoginForm() {
        setTitle("Bank Customer Portal - Login");
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
        JLabel headerLabel = new JLabel("BANK CUSTOMER PORTAL", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 36));
        headerLabel.setForeground(brandGreen);
        headerLabel.setBounds(262, 100, 500, 50);
        add(headerLabel);

        // Login panel
        JPanel loginPanel = new JPanel(null);
        loginPanel.setBounds(312, 200, 400, 400); // Increased height to accommodate cancelButton properly
        loginPanel.setBorder(BorderFactory.createLineBorder(brandGreen, 2));

        JLabel loginLabel = new JLabel("Customer Login", JLabel.CENTER);
        loginLabel.setFont(new Font("Arial", Font.BOLD, 24));
        loginLabel.setForeground(brandGreen);
        loginLabel.setBounds(100, 30, 200, 40);
        loginPanel.add(loginLabel);

        JLabel emailLabel = new JLabel("Email or Phone:");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        emailLabel.setBounds(50, 100, 200, 30);
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
        loginButton.setBackground(brandGreen);
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginPanel.add(loginButton);

        registerButton = new JButton("Register");
        registerButton.setFont(new Font("Arial", Font.BOLD, 16));
        registerButton.setBounds(210, 280, 140, 40);
        registerButton.setBackground(Color.WHITE);
        registerButton.setForeground(brandGreen);
        registerButton.setFocusPainted(false);
        loginPanel.add(registerButton);

        // Adjust cancelButton placement to avoid overlap with other buttons
        cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 16));
        cancelButton.setBounds(50, 340, 300, 40); // Adjusted y value to move it further down
        cancelButton.setBackground(Color.LIGHT_GRAY);
        cancelButton.setForeground(Color.BLACK);
        cancelButton.setFocusPainted(false);
        loginPanel.add(cancelButton);

        add(loginPanel);

        // Version label
        JLabel versionLabel = new JLabel("Customer Portal v1.0", JLabel.RIGHT);
        versionLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        versionLabel.setBounds(824, 698, 150, 20);
        add(versionLabel);
    }

    private void addActionListeners() {
        loginButton.addActionListener(e -> handleLogin());
        registerButton.addActionListener(e -> handleRegister());
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
        String emailOrPhone = emailField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (emailOrPhone.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both email/phone and password.",
                    "Login Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Customer customerInstance = new Customer();
            Customer customer = customerInstance.login(emailOrPhone, password);

            if (customer != null) {
                // Call the onLoginSuccessful method
                onLoginSuccessful(customer);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid email/phone or password.",
                        "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Login error: " + ex.getMessage(),
                    "System Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleRegister() {
        // Code to handle registration goes here (opening register form or similar)
        RegisterForm registerForm = new RegisterForm();
        registerForm.setVisible(true);
        dispose();
    }

    // Define the method that is called after login is successful
    private void onLoginSuccessful(Customer customer) {
        JOptionPane.showMessageDialog(this, "Login successful! Welcome, " + customer.getFullName(),
                "Login Success", JOptionPane.INFORMATION_MESSAGE);

        MainMenuForm mainMenuForm = new MainMenuForm(customer);
        mainMenuForm.setVisible(true);
        dispose();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new LoginForm().setVisible(true));
    }
}
