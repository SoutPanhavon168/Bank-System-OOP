package GUI.customer_gui;
import user.Customer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class RegisterForm extends JFrame {
    private JTextField lastNameField, firstNameField, emailField, phoneField, govIdField, birthDateField;
    private JPasswordField passwordField, confirmPasswordField;
    private JButton registerButton, backButton;

    public RegisterForm() {
        setTitle("Bank App - Register");
        setLayout(null);  // Use absolute positioning like in LoginForm
        setSize(360, 812);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);  // Prevent resizing

        // Create components
        lastNameField = new JTextField(20);
        firstNameField = new JTextField(20);
        emailField = new JTextField(20);
        phoneField = new JTextField(20);
        govIdField = new JTextField(20);
        birthDateField = new JTextField(20);
        passwordField = new JPasswordField(20);
        confirmPasswordField = new JPasswordField(20);
        registerButton = new JButton("Register");
        backButton = new JButton("Back to Login");

        // Set component sizes
        lastNameField.setPreferredSize(new Dimension(300, 40));
        firstNameField.setPreferredSize(new Dimension(300, 40));
        emailField.setPreferredSize(new Dimension(300, 40));
        phoneField.setPreferredSize(new Dimension(300, 40));
        govIdField.setPreferredSize(new Dimension(300, 40));
        birthDateField.setPreferredSize(new Dimension(300, 40));
        passwordField.setPreferredSize(new Dimension(300, 40));
        confirmPasswordField.setPreferredSize(new Dimension(300, 40));
        registerButton.setPreferredSize(new Dimension(300, 50));
        backButton.setPreferredSize(new Dimension(300, 50));

        // Set button colors
        registerButton.setBackground(new Color(0,175,0));  // Green background
        registerButton.setForeground(Color.WHITE);         // White text
        backButton.setBackground(Color.WHITE);             // White background
        backButton.setForeground(new Color(0,175,0));      // Green text

        // Add header
        JLabel welcomeLabel = new JLabel("Create Account", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 25));
        welcomeLabel.setForeground(new Color(0,175,0));
        welcomeLabel.setBounds(30, 30, 300, 30);
        add(welcomeLabel);

        // Position components with increased spacing to prevent overlapping
        int startY = 70;
        int fieldHeight = 30;
        int labelHeight = 20;
        int fieldSpacing = 17;  // Space between label and field
        int groupSpacing = 10;  // Space between field and next label

        // Calculate total height needed for each group (label + field + spacing)
        int groupHeight = labelHeight + fieldHeight + groupSpacing;

        // Last Name
        add(new JLabel("Last Name:")).setBounds(30, startY, 300, labelHeight);
        lastNameField.setBounds(30, startY + fieldSpacing, 300, fieldHeight);
        add(lastNameField);

        // First Name
        int posY = startY + fieldSpacing + fieldHeight + groupSpacing;
        add(new JLabel("First Name:")).setBounds(30, posY + 10, 300, labelHeight);
        firstNameField.setBounds(30, posY + 10 + fieldSpacing, 300, fieldHeight);
        add(firstNameField);

        // Email
        posY += groupHeight + fieldSpacing;
        add(new JLabel("Email:")).setBounds(30, posY, 300, labelHeight);
        emailField.setBounds(30, posY + fieldSpacing, 300, fieldHeight);
        add(emailField);

        // Phone Number
        posY += groupHeight + fieldSpacing;
        add(new JLabel("Phone Number:")).setBounds(30, posY, 300, labelHeight);
        phoneField.setBounds(30, posY + fieldSpacing, 300, fieldHeight);
        add(phoneField);

        // Birth Date
        posY += groupHeight + fieldSpacing;
        add(new JLabel("Birth Date (YYYY-MM-DD):")).setBounds(30, posY, 300, labelHeight);
        birthDateField.setBounds(30, posY + fieldSpacing, 300, fieldHeight);
        add(birthDateField);

        // Government ID
        posY += groupHeight + fieldSpacing;
        add(new JLabel("Government ID:")).setBounds(30, posY, 300, labelHeight);
        govIdField.setBounds(30, posY + fieldSpacing, 300, fieldHeight);
        add(govIdField);

        // Password
        posY += groupHeight + fieldSpacing;
        add(new JLabel("Password:")).setBounds(30, posY, 300, labelHeight);
        passwordField.setBounds(30, posY + fieldSpacing, 300, fieldHeight);
        add(passwordField);

        // Confirm Password
        posY += groupHeight + fieldSpacing;
        add(new JLabel("Confirm Password:")).setBounds(30, posY, 300, labelHeight);
        confirmPasswordField.setBounds(30, posY + fieldSpacing, 300, fieldHeight);
        add(confirmPasswordField);

        // Buttons
        posY += groupHeight + fieldSpacing;
        registerButton.setBounds(30, posY, 300, 50);
        add(registerButton);
        
        posY += 60;  // More space between buttons
        backButton.setBounds(30, posY, 300, 50);
        add(backButton);

        // Action listener for register
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get the input values
                String lastName = lastNameField.getText();
                String firstName = firstNameField.getText();
                String email = emailField.getText();
                String phone = phoneField.getText();
                String govId = govIdField.getText();
                String birthDate = birthDateField.getText();
                String password = new String(passwordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());

                // Try parsing the birth date
                LocalDate parsedBirthDate = null;
                try {
                    parsedBirthDate = LocalDate.parse(birthDate);
                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(RegisterForm.this, "Invalid birth date format. Please use YYYY-MM-DD.");
                    return;
                }

                // Check password confirmation
                if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(RegisterForm.this, "Passwords do not match.");
                    return;
                }

                // Create and register customer
                Customer customer = new Customer(lastName, firstName, email, password, confirmPassword, phone, parsedBirthDate, govId);
                customer.register(lastName, firstName, email, password, confirmPassword, phone, parsedBirthDate, govId);
                JOptionPane.showMessageDialog(RegisterForm.this, "Registration successful! Please login.");
                dispose(); // Close the register screen after successful registration
                new LoginForm().setVisible(true); // Open login form after registration
            }
        });

        // Action listener for back button
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the current registration screen
                new LoginForm().setVisible(true); // Open login form
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new RegisterForm().setVisible(true);
            }
        });
    }
}