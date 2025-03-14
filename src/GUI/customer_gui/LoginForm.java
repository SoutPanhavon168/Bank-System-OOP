package GUI.customer_gui;
import user.Customer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginForm extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;

    public LoginForm() {
        setTitle("Bank App - Login");
        setLayout(null);  // Use absolute positioning for manual control
        setSize(360, 812);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);  // Prevent resizing

        // Create components
        emailField = new JTextField(20);
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Login");
        registerButton = new JButton("Register");

        // Set component sizes
        emailField.setPreferredSize(new Dimension(300, 40));
        passwordField.setPreferredSize(new Dimension(300, 40));
        loginButton.setPreferredSize(new Dimension(300, 50));
        registerButton.setPreferredSize(new Dimension(300, 50));

        // Set the positions and sizes manually (controls the gap between components)
        JLabel welcomeLabel = new JLabel("Welcome to Bank 10", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 25));
        welcomeLabel.setForeground(new Color(0,175,0));
        welcomeLabel.setBounds(30, 90, 300, 30);  // Positioned at the top with some margin
        add(welcomeLabel);  // Add the label to the frame
        
        emailField.setBounds(30, 200, 300, 40);   // Position the email field below the label
        passwordField.setBounds(30, 280, 300, 40); // Position the password field
        loginButton.setBounds(30, 330, 300, 50);   // Position the login button
        registerButton.setBounds(30, 390, 300, 50); // Position the register button

        // Set button colors
        loginButton.setBackground(new Color(0,175,0));  // Blue background for login button
        loginButton.setForeground(Color.WHITE);  // White text for login button
        registerButton.setBackground(Color.WHITE);  // White background for register button
        registerButton.setForeground(new Color(0,175,0));  // Blue text for register button

        // Add components to the frame
        add(new JLabel("Email or Phone:")).setBounds(30, 170, 300, 20);
        add(emailField);
        add(new JLabel("Password:")).setBounds(30, 250, 300, 20);
        add(passwordField);
        add(loginButton);
        add(registerButton);

        // Action listener for login
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String emailOrPhone = emailField.getText();
                String password = new String(passwordField.getPassword());

                // Call the login method from Customer class
                Customer customer = Customer.login(emailOrPhone, password);

                if (customer != null) {
                    JOptionPane.showMessageDialog(LoginForm.this, "Login successful! Welcome, " + customer.getFullName());
                    // Proceed to the next screen after successful login
                } else {
                    JOptionPane.showMessageDialog(LoginForm.this, "Login failed. Please check your credentials.");
                }
            }
        });

        // Action listener for registration
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open registration form
                RegisterForm registerForm = new RegisterForm();
                registerForm.setVisible(true);
                dispose();  // Close the current login screen
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LoginForm().setVisible(true);
            }
        });
    }
}
