package GUI.customer_gui;

import bankaccount.BankAccount;
import database.BankAccountDAO;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import user.Customer;

public class MainMenuForm extends JFrame {
    private Customer currentCustomer;
    private JPanel mainPanel;
    private JPanel menuPanel;
    private Color brandGreen = new Color(0, 175, 0);
    private Color darkGreen = new Color(0, 125, 0);
    
    // Menu buttons
    private JButton accountsButton;
    private JButton transactionsButton;
    private JButton profileButton;
    private JButton createAccountButton;
    private JButton logoutButton;
    
    public MainMenuForm(Customer customer) {
        this.currentCustomer = customer;
        
        setTitle("Bank App - Customer Dashboard");
        setSize(1024, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        // Load customer accounts
        BankAccountDAO bankAccountDAO = new BankAccountDAO();
        ArrayList<BankAccount> accounts = bankAccountDAO.getBankAccountsByCustomerId(currentCustomer.getCustomerId());
        currentCustomer.setBankAccounts(accounts);
        
        createHeader();
        createMenuPanel();
        createMainPanel();
        
        // Show welcome screen initially
        showWelcomeScreen();
    }
    
    private void createHeader() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(darkGreen);
        headerPanel.setPreferredSize(new Dimension(1024, 80));
        headerPanel.setBorder(new EmptyBorder(10, 20, 10, 20));
        
        JLabel titleLabel = new JLabel("BANK CUSTOMER PORTAL");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        JPanel userInfoPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        userInfoPanel.setOpaque(false);
        
        JLabel customerInfoLabel = new JLabel(currentCustomer.getFirstName() + " " + currentCustomer.getLastName() + " | Customer");
        customerInfoLabel.setFont(new Font("Arial", Font.BOLD, 14));
        customerInfoLabel.setForeground(Color.WHITE);
        userInfoPanel.add(customerInfoLabel);
        
        headerPanel.add(userInfoPanel, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);
    }
    
    private void createMenuPanel() {
        menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(brandGreen);
        menuPanel.setPreferredSize(new Dimension(250, 688));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        
        // Create menu buttons
        accountsButton = createMenuButton("My Accounts", "accounts.png");
        transactionsButton = createMenuButton("Transactions", "transactions.png");
        profileButton = createMenuButton("My Profile", "profile.png");
        createAccountButton = createMenuButton("Create Account", "create_account.png");
        logoutButton = createMenuButton("Logout", "logout.png");
        
        // Add buttons to menu panel
        menuPanel.add(accountsButton);
        menuPanel.add(transactionsButton);
        menuPanel.add(profileButton);
        menuPanel.add(createAccountButton);
        menuPanel.add(Box.createVerticalGlue());
        menuPanel.add(logoutButton);
        
        // Add action listeners
        accountsButton.addActionListener(e -> showAccountsPanel());
        transactionsButton.addActionListener(e -> showTransactionsPanel());
        profileButton.addActionListener(e -> showProfilePanel());
        createAccountButton.addActionListener(e -> showCreateAccountPanel());
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
        button.setBackground(brandGreen);
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
                button.setBackground(darkGreen);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(brandGreen);
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
        
        int accountsCount = currentCustomer.getBankAccounts().size();
        
        String welcomeMessage = "<html><div style='text-align: center;'>"
                + "<h1 style='color: #00AF00;'>Welcome, " + currentCustomer.getFirstName() + "!</h1>"
                + "<h2>Bank Customer Portal</h2>"
                + "<p>Current Date: " + java.time.LocalDate.now() + "</p>"
                + "<p>Customer ID: " + currentCustomer.getCustomerId() + "</p>"
                + "<p>Active Accounts: " + accountsCount + "</p>"
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
    
    private void showAccountsPanel() {
        clearMainPanel();
        
        JPanel accountsPanel = new JPanel(new BorderLayout());
        accountsPanel.setBackground(Color.WHITE);
        
        JLabel headerLabel = new JLabel("My Accounts", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(brandGreen);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        accountsPanel.add(headerLabel, BorderLayout.NORTH);
        
        ArrayList<BankAccount> accounts = currentCustomer.getBankAccounts();
        
        // Debug code
        System.out.println("Customer ID: " + currentCustomer.getCustomerId());
        System.out.println("Number of accounts: " + accounts.size());
        
        if (accounts == null || accounts.isEmpty()) {
            JPanel emptyPanel = new JPanel(new BorderLayout());
            emptyPanel.setBackground(Color.WHITE);
            JLabel emptyLabel = new JLabel("You don't have any accounts yet. Create one to get started.", JLabel.CENTER);
            emptyLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            emptyPanel.add(emptyLabel, BorderLayout.CENTER);
            
            JButton createButton = new JButton("Create Account");
            createButton.setFont(new Font("Arial", Font.BOLD, 14));
            createButton.setBackground(brandGreen);
            createButton.setForeground(Color.WHITE);
            createButton.setFocusPainted(false);
            createButton.addActionListener(e -> showCreateAccountPanel());
            
            JPanel buttonPanel = new JPanel();
            buttonPanel.setBackground(Color.WHITE);
            buttonPanel.add(createButton);
            
            emptyPanel.add(buttonPanel, BorderLayout.SOUTH);
            accountsPanel.add(emptyPanel, BorderLayout.CENTER);
        } else {
            JPanel accountsListPanel = new JPanel();
            accountsListPanel.setLayout(new BoxLayout(accountsListPanel, BoxLayout.Y_AXIS));
            accountsListPanel.setBackground(Color.WHITE);
            accountsListPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
            
            for (BankAccount account : accounts) {
                // Debug code
                System.out.println("Processing account: " + account.getAccountNumber());
                
                JPanel accountCard = createAccountCard(account);
                accountsListPanel.add(accountCard);
                accountsListPanel.add(Box.createRigidArea(new Dimension(0, 15)));
            }
            
            JScrollPane scrollPane = new JScrollPane(accountsListPanel);
            scrollPane.setBorder(null);
            scrollPane.getVerticalScrollBar().setUnitIncrement(16);
            
            accountsPanel.add(scrollPane, BorderLayout.CENTER);
        }
        
        mainPanel.add(accountsPanel, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }
    
    private JPanel createAccountCard(BankAccount account) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(brandGreen, 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        
        JLabel accountTypeLabel = new JLabel(account.getAccountType() + " Account");
        accountTypeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        accountTypeLabel.setForeground(brandGreen);
        
        JLabel accountStatusLabel = new JLabel(account.getAccountStatus());
        accountStatusLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        accountStatusLabel.setForeground(account.getAccountStatus().equals("Active") ? brandGreen : Color.RED);
        
        topPanel.add(accountTypeLabel, BorderLayout.WEST);
        topPanel.add(accountStatusLabel, BorderLayout.EAST);
        
        JPanel detailsPanel = new JPanel(new GridLayout(0, 2, 10, 5));
        detailsPanel.setBackground(Color.WHITE);
        
        detailsPanel.add(new JLabel("Account Number:"));
        detailsPanel.add(new JLabel(String.valueOf(account.getAccountNumber())));
        
        detailsPanel.add(new JLabel("Balance:"));
        detailsPanel.add(new JLabel("$" + String.format("%.2f", account.getBalance())));
        
        detailsPanel.add(new JLabel("Created:"));
        detailsPanel.add(new JLabel(account.getCreatedDate() != null ? account.getCreatedDate().toString() : "N/A"));
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton viewDetailsButton = new JButton("View Details");
        viewDetailsButton.setFont(new Font("Arial", Font.BOLD, 12));
        viewDetailsButton.setBackground(brandGreen);
        viewDetailsButton.setForeground(Color.WHITE);
        viewDetailsButton.setFocusPainted(false);
        
        // Updated action listener to use the viewDetails method
        viewDetailsButton.addActionListener(e -> {
            // Create a custom panel for the account details
            JPanel accountDetailsPanel = new JPanel();
            accountDetailsPanel.setLayout(new BoxLayout(accountDetailsPanel, BoxLayout.Y_AXIS));
            accountDetailsPanel.setBackground(Color.WHITE);
            accountDetailsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
            // Add account details to the panel
            accountDetailsPanel.add(createDetailLabel("Account Number:", String.valueOf(account.getAccountNumber())));
            accountDetailsPanel.add(createDetailLabel("Account Name:", account.getAccountName()));
            accountDetailsPanel.add(createDetailLabel("First Name:", account.getFirstName()));
            accountDetailsPanel.add(createDetailLabel("Last Name:", account.getLastName()));
            accountDetailsPanel.add(createDetailLabel("Balance:", "$" + String.format("%.2f", account.getBalance())));
            accountDetailsPanel.add(createDetailLabel("Account Type:", account.getAccountType()));
            accountDetailsPanel.add(createDetailLabel("Account Status:", account.getAccountStatus()));
            accountDetailsPanel.add(createDetailLabel("Customer ID:", String.valueOf(account.getCustomerId())));
            accountDetailsPanel.add(createDetailLabel("Created Date:", account.getCreatedDate() != null ? account.getCreatedDate().toString() : "N/A"));
        
            // Show the styled dialog
            JOptionPane.showMessageDialog(this, accountDetailsPanel, "Account Details", JOptionPane.INFORMATION_MESSAGE);
        });
        
        buttonPanel.add(viewDetailsButton);
        
        card.add(topPanel, BorderLayout.NORTH);
        card.add(detailsPanel, BorderLayout.CENTER);
        card.add(buttonPanel, BorderLayout.SOUTH);
        
        return card;
    }

    
    
    private void showTransactionsPanel() {
        // Create and show the TransactionsForm
        TransactionsForm transactionsForm = new TransactionsForm(currentCustomer);
        transactionsForm.setVisible(true);
        
        // Close the current MainMenuForm
        this.dispose();
    }
    
    private void showProfilePanel() {
        clearMainPanel();
        
        JPanel profilePanel = new JPanel(new BorderLayout());
        profilePanel.setBackground(Color.WHITE);
        profilePanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        
        JLabel headerLabel = new JLabel("Customer Profile", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(brandGreen);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        
        profilePanel.add(headerLabel, BorderLayout.NORTH);
        
        JPanel infoPanel = new JPanel(new GridLayout(0, 2, 10, 15));
        infoPanel.setBackground(Color.WHITE);
        
        addProfileField(infoPanel, "Customer ID:", String.valueOf(currentCustomer.getCustomerId()));
        addProfileField(infoPanel, "First Name:", currentCustomer.getFirstName());
        addProfileField(infoPanel, "Last Name:", currentCustomer.getLastName());
        addProfileField(infoPanel, "Email:", currentCustomer.getEmail());
        addProfileField(infoPanel, "Phone:", currentCustomer.getPhoneNumber());
        
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);
        centerPanel.add(infoPanel, BorderLayout.NORTH);
        
        profilePanel.add(centerPanel, BorderLayout.CENTER);
        
        // Add buttons for profile actions
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actionPanel.setBackground(Color.WHITE);
        
        JButton updateInfoButton = new JButton("Update Information");
        updateInfoButton.setFont(new Font("Arial", Font.BOLD, 14));
        updateInfoButton.setBackground(brandGreen);
        updateInfoButton.setForeground(Color.WHITE);
        updateInfoButton.setFocusPainted(false);
        updateInfoButton.setMargin(new Insets(5, 10, 5, 10));
        
        JButton changePasswordButton = new JButton("Change Password");
        changePasswordButton.setFont(new Font("Arial", Font.BOLD, 14));
        changePasswordButton.setBackground(brandGreen);
        changePasswordButton.setForeground(Color.WHITE);
        changePasswordButton.setFocusPainted(false);
        changePasswordButton.setMargin(new Insets(5, 10, 5, 10));
        
        updateInfoButton.addActionListener(e -> {
            // Update customer information (to be implemented)
            JOptionPane.showMessageDialog(this, 
                "Update information feature is not implemented yet.", 
                "Update Information", 
                JOptionPane.INFORMATION_MESSAGE);
        });
        
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
                // Password change logic (to be implemented)
                JOptionPane.showMessageDialog(this, "Password changed successfully!", 
                                            "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        actionPanel.add(updateInfoButton);
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
    
    private void showCreateAccountPanel() {
        clearMainPanel();
        
        JPanel createAccountPanel = new JPanel(new BorderLayout());
        createAccountPanel.setBackground(Color.WHITE);
        
        JLabel headerLabel = new JLabel("Create New Account", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(brandGreen);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        createAccountPanel.add(headerLabel, BorderLayout.NORTH);
        
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));
        
        JPanel accountTypePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        accountTypePanel.setBackground(Color.WHITE);
        
        JLabel accountTypeLabel = new JLabel("Account Type:");
        accountTypeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        accountTypeLabel.setPreferredSize(new Dimension(150, 30));
        
        String[] accountTypes = {"Savings", "Current", "Checking"};
        JComboBox<String> accountTypeComboBox = new JComboBox<>(accountTypes);
        accountTypeComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        accountTypeComboBox.setPreferredSize(new Dimension(200, 30));
        
        accountTypePanel.add(accountTypeLabel);
        accountTypePanel.add(accountTypeComboBox);
        
        JPanel pinPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pinPanel.setBackground(Color.WHITE);
        
        JLabel pinLabel = new JLabel("PIN (4 digits):");
        pinLabel.setFont(new Font("Arial", Font.BOLD, 14));
        pinLabel.setPreferredSize(new Dimension(150, 30));
        
        JPasswordField pinField = new JPasswordField();
        pinField.setFont(new Font("Arial", Font.PLAIN, 14));
        pinField.setPreferredSize(new Dimension(200, 30));
        
        pinPanel.add(pinLabel);
        pinPanel.add(pinField);
        
        JPanel confirmPinPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        confirmPinPanel.setBackground(Color.WHITE);
        
        JLabel confirmPinLabel = new JLabel("Confirm PIN:");
        confirmPinLabel.setFont(new Font("Arial", Font.BOLD, 14));
        confirmPinLabel.setPreferredSize(new Dimension(150, 30));
        
        JPasswordField confirmPinField = new JPasswordField();
        confirmPinField.setFont(new Font("Arial", Font.PLAIN, 14));
        confirmPinField.setPreferredSize(new Dimension(200, 30));
        
        confirmPinPanel.add(confirmPinLabel);
        confirmPinPanel.add(confirmPinField);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
        
        JButton createButton = new JButton("Create Account");
        createButton.setFont(new Font("Arial", Font.BOLD, 16));
        createButton.setBackground(brandGreen);
        createButton.setForeground(Color.WHITE);
        createButton.setFocusPainted(false);
        createButton.setPreferredSize(new Dimension(200, 40));
        
        createButton.addActionListener(e -> {
            // Get PIN values
            String pin = new String(pinField.getPassword());
            String confirmPin = new String(confirmPinField.getPassword());
            
            // Validate PIN
            if (pin.isEmpty() || !pin.matches("\\d{4}")) {
                JOptionPane.showMessageDialog(this, 
                    "Invalid PIN. Please enter exactly 4 digits.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Validate PIN match
            if (!pin.equals(confirmPin)) {
                JOptionPane.showMessageDialog(this, 
                    "PINs do not match. Please try again.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Create new account
            String accountType = (String) accountTypeComboBox.getSelectedItem();
            BankAccount newAccount = new BankAccount(
                currentCustomer.getCustomerId(), 
                currentCustomer.getFirstName(), 
                currentCustomer.getLastName(), 
                accountType, 
                "Active", 
                Integer.parseInt(pin)
            );
            
            // Generate account number
            newAccount.setAccountNumber(BankAccount.generateAccountNumber());
            
            // Save account to database
            new BankAccountDAO().saveBankAccount(newAccount);
            currentCustomer.getBankAccounts().add(newAccount);
            
            JOptionPane.showMessageDialog(this, 
                "Bank account created successfully!\nYour new account number is: " + newAccount.getAccountNumber(), 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
            
            // Clear form fields
            accountTypeComboBox.setSelectedIndex(0);
            pinField.setText("");
            confirmPinField.setText("");
            
            // Show the accounts panel
            showAccountsPanel();
        });
        
        buttonPanel.add(createButton);
        
        formPanel.add(accountTypePanel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        formPanel.add(pinPanel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        formPanel.add(confirmPinPanel);
        formPanel.add(buttonPanel);
        
        createAccountPanel.add(formPanel, BorderLayout.CENTER);
        
        mainPanel.add(createAccountPanel, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private JPanel createDetailLabel(String label, String value) {
        JPanel detailPanel = new JPanel(new BorderLayout());
        detailPanel.setBackground(Color.WHITE);
        detailPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
    
        JLabel labelComponent = new JLabel(label);
        labelComponent.setFont(new Font("Arial", Font.BOLD, 14));
        labelComponent.setForeground(brandGreen);
    
        JLabel valueComponent = new JLabel(value);
        valueComponent.setFont(new Font("Arial", Font.PLAIN, 14));
        valueComponent.setForeground(Color.BLACK);
    
        detailPanel.add(labelComponent, BorderLayout.WEST);
        detailPanel.add(valueComponent, BorderLayout.EAST);
    
        return detailPanel;
    }
    
    private void handleLogout() {
        int choice = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to logout?", 
                "Confirm Logout", 
                JOptionPane.YES_NO_OPTION);
        
        if (choice == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this, 
                "Thank you for using our banking app!", 
                "Goodbye", 
                JOptionPane.INFORMATION_MESSAGE);
            
            new LoginForm().setVisible(true);
            this.dispose();
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginForm().setVisible(true));
    }
}