package GUI.customer_gui;

import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import javax.swing.*;
import user.Customer;

public class RegisterForm extends JFrame {
    private JTextField lastNameField, firstNameField, phoneField, birthDateField, emailField, govIdField;
    private JPasswordField passwordField, confirmPasswordField;
    private JButton nextButton, backButton, registerButton, prevButton;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private Color brandGreen = new Color(0, 175, 0);

    public RegisterForm() {
        setTitle("Bank Customer Portal - Register");
        setSize(1024, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
        
        initializeComponents();
        addActionListeners();
    }

    private void initializeComponents() {
        JLabel headerLabel = new JLabel("CREATE ACCOUNT", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 36));
        headerLabel.setForeground(brandGreen);
        headerLabel.setBounds(262, 50, 500, 50);
        add(headerLabel);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setBounds(262, 120, 500, 480);
        cardPanel.setBorder(BorderFactory.createLineBorder(brandGreen, 2));

        // Step 1: Basic Information
        JPanel step1Panel = new JPanel(null);
        step1Panel.setBackground(Color.WHITE);

        JLabel step1Label = new JLabel("Step 1: Personal Information", JLabel.CENTER);
        step1Label.setFont(new Font("Arial", Font.BOLD, 20));
        step1Label.setBounds(50, 10, 400, 30);
        step1Panel.add(step1Label);

        String[] labels1 = {"Last Name:", "First Name:", "Phone Number:", "Birth Date (YYYY-MM-DD):"};
        JTextField[] fields1 = {
            lastNameField = new JTextField(), firstNameField = new JTextField(),
            phoneField = new JTextField(), birthDateField = new JTextField()
        };

        int posY = 50;
        for (int i = 0; i < labels1.length; i++) {
            JLabel label = new JLabel(labels1[i]);
            label.setFont(new Font("Arial", Font.PLAIN, 16));
            label.setBounds(50, posY, 200, 30);
            step1Panel.add(label);

            fields1[i].setFont(new Font("Arial", Font.PLAIN, 16));
            fields1[i].setBounds(50, posY + 30, 400, 40);
            step1Panel.add(fields1[i]);

            posY += 70;
        }

        nextButton = new JButton("Next");
        nextButton.setFont(new Font("Arial", Font.BOLD, 16));
        nextButton.setBounds(150, posY + 20, 200, 40);
        nextButton.setBackground(brandGreen);
        nextButton.setForeground(Color.WHITE);
        step1Panel.add(nextButton);

        cardPanel.add(step1Panel, "Step1");

        // Step 2: Contact & Security
        JPanel step2Panel = new JPanel(null);
        step2Panel.setBackground(Color.WHITE);

        JLabel step2Label = new JLabel("Step 2: Contact & Security", JLabel.CENTER);
        step2Label.setFont(new Font("Arial", Font.BOLD, 20));
        step2Label.setBounds(50, 10, 400, 30);
        step2Panel.add(step2Label);

        String[] labels2 = {"Email:", "Government ID:", "Password:", "Confirm Password:"};
        JTextField[] fields2 = {emailField = new JTextField(), govIdField = new JTextField()};

        posY = 50;
        for (int i = 0; i < labels2.length; i++) {
            JLabel label = new JLabel(labels2[i]);
            label.setFont(new Font("Arial", Font.PLAIN, 16));
            label.setBounds(50, posY, 200, 30);
            step2Panel.add(label);

            if (i < 2) {
                fields2[i].setFont(new Font("Arial", Font.PLAIN, 16));
                fields2[i].setBounds(50, posY + 30, 400, 40);
                step2Panel.add(fields2[i]);
            } else {
                JPasswordField passField = (i == 2) ? (passwordField = new JPasswordField()) : (confirmPasswordField = new JPasswordField());
                passField.setFont(new Font("Arial", Font.PLAIN, 16));
                passField.setBounds(50, posY + 30, 400, 40);
                step2Panel.add(passField);
            }
            posY += 70;
        }

        prevButton = new JButton("Previous");
        prevButton.setFont(new Font("Arial", Font.BOLD, 16));
        prevButton.setBounds(50, posY + 20, 180, 40);
        prevButton.setBackground(Color.LIGHT_GRAY);
        prevButton.setForeground(Color.BLACK);
        step2Panel.add(prevButton);

        registerButton = new JButton("Register");
        registerButton.setFont(new Font("Arial", Font.BOLD, 16));
        registerButton.setBounds(270, posY + 20, 180, 40);
        registerButton.setBackground(brandGreen);
        registerButton.setForeground(Color.WHITE);
        step2Panel.add(registerButton);

        cardPanel.add(step2Panel, "Step2");

        add(cardPanel);

        // Back to Login Button
        backButton = new JButton("Back to Login");
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setBounds(262, 620, 500, 40);
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(brandGreen);
        add(backButton);
    }

    private void addActionListeners() {
        nextButton.addActionListener(e -> cardLayout.show(cardPanel, "Step2"));
        prevButton.addActionListener(e -> cardLayout.show(cardPanel, "Step1"));

        registerButton.addActionListener(e -> handleRegistration());
        backButton.addActionListener(e -> {
            new LoginForm().setVisible(true);
            dispose();
        });
    }

    private void handleRegistration() {
        String lastName = lastNameField.getText().trim();
        String firstName = firstNameField.getText().trim();
        String phone = phoneField.getText().trim();
        String birthDate = birthDateField.getText().trim();
        String email = emailField.getText().trim();
        String govId = govIdField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        LocalDate parsedBirthDate;
        try {
            parsedBirthDate = LocalDate.parse(birthDate);
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Invalid birth date format. Use YYYY-MM-DD.", "Registration Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match.", "Registration Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Customer customer = new Customer(lastName, firstName, email, password, confirmPassword, phone, parsedBirthDate, govId);
        customer.register(lastName, firstName, email, password, confirmPassword, phone, parsedBirthDate, govId);
        JOptionPane.showMessageDialog(this, "Registration successful! Please login.", "Success", JOptionPane.INFORMATION_MESSAGE);

        new LoginForm().setVisible(true);
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RegisterForm().setVisible(true));
    }
}
