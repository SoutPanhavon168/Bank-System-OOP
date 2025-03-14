package GUI.customer_gui;
import user.Customer;
import javax.swing.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CustomerForm extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JPanel cardPanel;
    private CardLayout cardLayout;

    private Customer currentCustomer;

    public CustomerForm() {
        setTitle("Customer Management");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Login Panel
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(3, 2));

        loginPanel.add(new JLabel("Email or Phone:"));
        emailField = new JTextField();
        loginPanel.add(emailField);

        loginPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        loginPanel.add(passwordField);

        loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String emailOrPhone = emailField.getText();
                String password = new String(passwordField.getPassword());
                currentCustomer = Customer.login(emailOrPhone, password);
                if (currentCustomer != null) {
                    loadCustomerDashboard();
                } else {
                    JOptionPane.showMessageDialog(null, "Login failed. Check your credentials.");
                }
            }
        });
        loginPanel.add(loginButton);

        cardPanel.add(loginPanel, "Login");

        // Dashboard Panel
        JPanel dashboardPanel = new JPanel();
        dashboardPanel.setLayout(new GridLayout(5, 1));

        JButton viewAccountButton = new JButton("View Account");
        viewAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewAccountDetails();
            }
        });
        dashboardPanel.add(viewAccountButton);

        JButton createAccountButton = new JButton("Create Bank Account");
        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createBankAccount();
            }
        });
        dashboardPanel.add(createAccountButton);

        JButton depositButton = new JButton("Deposit");
        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deposit();
            }
        });
        dashboardPanel.add(depositButton);

        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                withdraw();
            }
        });
        dashboardPanel.add(withdrawButton);

        JButton transferButton = new JButton("Transfer");
        transferButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                transfer();
            }
        });
        dashboardPanel.add(transferButton);

        cardPanel.add(dashboardPanel, "Dashboard");

        add(cardPanel);
    }

    // Method to load customer dashboard after successful login
    private void loadCustomerDashboard() {
        cardLayout.show(cardPanel, "Dashboard");
    }

    // Method to view account details
    private void viewAccountDetails() {
        JOptionPane.showMessageDialog(this, "Account details:\n" + currentCustomer.toString());
    }

    // Method to create a bank account
    private void createBankAccount() {
        currentCustomer.createBankAccount();
    }

    // Method to handle deposit
    private void deposit() {
        currentCustomer.deposit();
    }

    // Method to handle withdraw
    private void withdraw() {
        currentCustomer.withdraw();
    }

    // Method to handle transfer
    private void transfer() {
        currentCustomer.transfer();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CustomerForm().setVisible(true);
            }
        });
    }
}
