package GUI.customer_gui;
import user.Customer;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;

public class LoginForm extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private Color brandGreen = new Color(0, 175, 0);

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
        welcomeLabel.setForeground(brandGreen);
        welcomeLabel.setBounds(30, 90, 300, 30);  // Positioned at the top with some margin
        add(welcomeLabel);  // Add the label to the frame

        emailField.setBounds(30, 200, 300, 40);   // Position the email field below the label
        passwordField.setBounds(30, 280, 300, 40); // Position the password field
        loginButton.setBounds(30, 330, 300, 50);   // Position the login button
        registerButton.setBounds(30, 390, 300, 50); // Position the register button

        // Set button colors
        loginButton.setBackground(brandGreen);  // Green background for login button
        loginButton.setForeground(Color.WHITE);  // White text for login button
        registerButton.setBackground(Color.WHITE);  // White background for register button
        registerButton.setForeground(brandGreen);  // Green text for register button

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
                    showStyledDialog("Login successful! Welcome, " + customer.getFullName(), true);
                    // Proceed to the next screen after successful login
                } else {
                    showStyledDialog("Login failed. Please check your credentials.", false);
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

    // Custom method to show styled dialog
    private void showStyledDialog(String message, boolean isSuccess) {
        JDialog dialog = new JDialog(this, isSuccess ? "Success" : "Error", true);
        dialog.setSize(320, 180);
        dialog.setLayout(new BorderLayout());
        dialog.setLocationRelativeTo(this);

        // Create a panel with the message
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create message label
        JLabel messageLabel = new JLabel(message, JLabel.CENTER);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(messageLabel, BorderLayout.CENTER);

        // Create OK button
        JButton okButton = new JButton("OK");
        okButton.setPreferredSize(new Dimension(100, 40));
        okButton.setBackground(isSuccess ? brandGreen : Color.RED);
        okButton.setForeground(Color.WHITE);
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
                if (isSuccess) {
                    // Here you can add code to navigate to the next screen
                }
            }
        });

        // Add button to a panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        // Add panels to dialog
        panel.add(buttonPanel, BorderLayout.SOUTH);
        dialog.add(panel);

        // Make dialog border match the app style
        ((JPanel)dialog.getContentPane()).setBorder(new LineBorder(isSuccess ? brandGreen : Color.RED, 2));

        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LoginForm().setVisible(true);
            }
        });
    }
}
