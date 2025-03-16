package GUI.customer_gui;

import bankaccount.BankAccount;
import database.BankAccountDAO;
import database.CustomerDAO;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import user.Customer;

public class MainMenuForm extends JFrame {
    private Customer currentCustomer;
    private JButton accountsButton;
    private JButton transactionsButton;
    private JButton profileButton;
    private JButton logoutButton;
    private Color brandGreen = new Color(0, 175, 0);

    public MainMenuForm(Customer customer) {
        // Fetch customer details from the database
        CustomerDAO customerDAO = new CustomerDAO();
        this.currentCustomer = customerDAO.getCustomerById(customer.getCustomerId());

        // Fetch bank accounts from the database
        BankAccountDAO bankAccountDAO = new BankAccountDAO();
        ArrayList<BankAccount> accounts = bankAccountDAO.getBankAccountsByCustomerId(currentCustomer.getCustomerId());
        currentCustomer.setBankAccounts(accounts); // Update the customer object with fetched accounts

        // Set up the main menu window
        setTitle("Bank App - Main Menu");
        setLayout(null);
        setSize(360, 812);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Add header and subheader
        addHeader("Welcome, " + currentCustomer.getFirstName() + "!");
        addSubheader("Customer ID: " + currentCustomer.getCustomerId(), accounts != null ? accounts.size() : 0);

        // Add menu buttons
        addMenuButtons();

        // Add action listeners
        addActionListeners();
    }

    private void addHeader(String welcomeMessage) {
        JLabel welcomeLabel = new JLabel(welcomeMessage, JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setForeground(brandGreen);
        welcomeLabel.setBounds(30, 30, 300, 40);
        add(welcomeLabel);
    }

    private void addSubheader(String customerInfo, int numAccounts) {
        JLabel accountInfoLabel = new JLabel(customerInfo, JLabel.CENTER);
        accountInfoLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        accountInfoLabel.setBounds(30, 70, 300, 30);
        add(accountInfoLabel);

        JLabel accountsLabel = new JLabel("Active Accounts: " + numAccounts, JLabel.CENTER);
        accountsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        accountsLabel.setBounds(30, 100, 300, 30);
        add(accountsLabel);
    }

    private void addMenuButtons() {
        accountsButton = new JButton("My Accounts");
        transactionsButton = new JButton("Transactions");
        profileButton = new JButton("My Profile");
        logoutButton = new JButton("Logout");

        accountsButton.setBounds(30, 180, 300, 80);
        transactionsButton.setBounds(30, 280, 300, 80);
        profileButton.setBounds(30, 380, 300, 80);
        logoutButton.setBounds(30, 700, 300, 50);

        styleButton(accountsButton, true);
        styleButton(transactionsButton, true);
        styleButton(profileButton, true);
        styleButton(logoutButton, false);

        add(accountsButton);
        add(transactionsButton);
        add(profileButton);
        add(logoutButton);
    }

    private void addActionListeners() {
        accountsButton.addActionListener(e -> handleAccounts());
        transactionsButton.addActionListener(e -> handleTransactions());
        profileButton.addActionListener(e -> handleProfile());
        logoutButton.addActionListener(e -> handleLogout());
    }

    private void styleButton(JButton button, boolean isPrimary) {
        if (isPrimary) {
            button.setBackground(brandGreen);
            button.setForeground(Color.WHITE);
        } else {
            button.setBackground(Color.WHITE);
            button.setForeground(brandGreen);
        }
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBorder(BorderFactory.createLineBorder(brandGreen, 1));
        button.setFocusPainted(false);
    }

    private void handleAccounts() {
        // Fetch bank accounts associated with the current customer
        BankAccountDAO bankAccountDAO = new BankAccountDAO();
        ArrayList<BankAccount> accounts = bankAccountDAO.getBankAccountsByCustomerId(currentCustomer.getCustomerId());

        // Check if the customer has any accounts
        if (accounts == null || accounts.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No accounts found for this customer.", 
                "Accounts", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Build a string to display account details
        StringBuilder accountsInfo = new StringBuilder("Bank Accounts:\n");
        for (BankAccount account : accounts) {
            accountsInfo.append(account.toString()).append("\n");
        }

        // Show the accounts in a dialog
        JOptionPane.showMessageDialog(this, accountsInfo.toString(), 
            "Accounts", JOptionPane.INFORMATION_MESSAGE);
    }

    private void handleTransactions() {
        // Navigate to Transactions Form
        TransactionsForm transactionsForm = new TransactionsForm(currentCustomer);
        transactionsForm.setVisible(true);
        dispose();
    }

    private void handleProfile() {
        // Show a dialog with user information
        String message = "Name: " + currentCustomer.getFirstName() + " " + currentCustomer.getLastName() + "\n" +
                         "Email: " + currentCustomer.getEmail() + "\n" +
                         "Phone: " + currentCustomer.getPhoneNumber();

        JOptionPane.showMessageDialog(this, message, "Profile Information", JOptionPane.INFORMATION_MESSAGE);
    }

    private void handleLogout() {
        // Confirm logout
        int confirm = JOptionPane.showConfirmDialog(this, 
                                                   "Are you sure you want to logout?", 
                                                   "Confirm Logout", 
                                                   JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this, "Thank you for using our banking app!", 
                                          "Goodbye", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        // For testing purposes
        SwingUtilities.invokeLater(() -> {
            try {
                // Simulate fetching a customer from the database
                CustomerDAO customerDAO = new CustomerDAO();
                Customer testCustomer = customerDAO.getCustomerById(2);

                MainMenuForm mainMenu = new MainMenuForm(testCustomer);
                mainMenu.setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "An error occurred: " + e.getMessage(), 
                                              "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}