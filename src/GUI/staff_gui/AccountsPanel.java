package GUI.staff_gui;

import bankaccount.BankAccount;
import database.BankAccountDAO;
import database.CustomerDAO;
import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.*;
import user.Customer;
import user.staff.Staff;

public class AccountsPanel extends JPanel {
    private Staff currentStaff;
    private BankAccountDAO bankAccountDAO;
    private JTable accountsTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JButton searchButton;
    private JButton viewButton;
    private JButton freezeButton;
    private JButton unfreezeButton;
    private JButton createButton;
    private JButton deleteButton;
    private Color brandBlue = new Color(0, 102, 204);
    
    public AccountsPanel(Staff staff) {
        this.currentStaff = staff;
        this.bankAccountDAO = new BankAccountDAO();
        
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(Color.WHITE);
        
        createHeaderPanel();
        createTablePanel();
        createButtonPanel();
        
        // Load all accounts initially
        loadAccounts();
    }
    
    private void createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        JLabel titleLabel = new JLabel("Bank Accounts Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(brandBlue);
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setBackground(Color.WHITE);
        
        searchField = new JTextField(15);
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        searchPanel.add(searchField);
        
        searchButton = new JButton("Search");
        searchButton.setFont(new Font("Arial", Font.BOLD, 14));
        searchButton.setBackground(brandBlue);
        searchButton.setForeground(Color.WHITE);
        searchButton.setFocusPainted(false);
        searchButton.addActionListener(e -> searchAccounts());
        searchPanel.add(searchButton);
        
        headerPanel.add(searchPanel, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);
    }
    
    private void createTablePanel() {
        // Create table model with column names
        String[] columnNames = {"Account ID", "Customer ID", "Account Number", "Name", "Type", "Balance", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make cells non-editable
            }
        };
        
        accountsTable = new JTable(tableModel);
        accountsTable.setFont(new Font("Arial", Font.PLAIN, 14));
        accountsTable.setRowHeight(25);
        accountsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        accountsTable.getTableHeader().setReorderingAllowed(false);
        accountsTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        
        // Set column widths
        TableColumnModel columnModel = accountsTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(80);
        columnModel.getColumn(1).setPreferredWidth(80);
        columnModel.getColumn(2).setPreferredWidth(120);
        columnModel.getColumn(3).setPreferredWidth(150);
        columnModel.getColumn(4).setPreferredWidth(100);
        columnModel.getColumn(5).setPreferredWidth(100);
        columnModel.getColumn(6).setPreferredWidth(100);
        
        // Add scroll pane
        JScrollPane scrollPane = new JScrollPane(accountsTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
        viewButton = new JButton("View Details");
        viewButton.setFont(new Font("Arial", Font.BOLD, 14));
        viewButton.setBackground(brandBlue);
        viewButton.setForeground(Color.WHITE);
        viewButton.setFocusPainted(false);
        viewButton.addActionListener(e -> viewAccountDetails());
        buttonPanel.add(viewButton);
        
        freezeButton = new JButton("Freeze Account");
        freezeButton.setFont(new Font("Arial", Font.BOLD, 14));
        freezeButton.setBackground(brandBlue);
        freezeButton.setForeground(Color.WHITE);
        freezeButton.setFocusPainted(false);
        freezeButton.addActionListener(e -> freezeAccount());
        buttonPanel.add(freezeButton);
        
        unfreezeButton = new JButton("Unfreeze Account");
        unfreezeButton.setFont(new Font("Arial", Font.BOLD, 14));
        unfreezeButton.setBackground(brandBlue);
        unfreezeButton.setForeground(Color.WHITE);
        unfreezeButton.setFocusPainted(false);
        unfreezeButton.addActionListener(e -> unfreezeAccount());
        buttonPanel.add(unfreezeButton);
        
        createButton = new JButton("Create Account");
        createButton.setFont(new Font("Arial", Font.BOLD, 14));
        createButton.setBackground(brandBlue);
        createButton.setForeground(Color.WHITE);
        createButton.setFocusPainted(false);
        createButton.addActionListener(e -> createAccount());
        buttonPanel.add(createButton);
        
        deleteButton = new JButton("Delete Account");
        deleteButton.setFont(new Font("Arial", Font.BOLD, 14));
        deleteButton.setBackground(brandBlue);
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);
        // deleteButton.addActionListener(e -> deleteAccount());
        buttonPanel.add(deleteButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void viewAccountDetails() {
        int selectedRow = accountsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a bank account to view.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        int accountId = (int) tableModel.getValueAt(selectedRow, 0);
        BankAccount bankAccount = bankAccountDAO.getBankAccountById(accountId);
    
        if (bankAccount != null) {
            JOptionPane.showMessageDialog(this, "Account Details:\n" + bankAccount.toString(), "Account Details", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Account not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void freezeAccount() {
        int selectedRow = accountsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a bank account to freeze.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        int accountId = (int) tableModel.getValueAt(selectedRow, 0);
        boolean success = bankAccountDAO.freezeBankAccount(accountId);
    
        if (success) {
            JOptionPane.showMessageDialog(this, "Account ID " + accountId + " has been frozen.", "Success", JOptionPane.INFORMATION_MESSAGE);
            loadAccounts(); // Reload the accounts after freezing
        } else {
            JOptionPane.showMessageDialog(this, "Failed to freeze the account.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void unfreezeAccount() {
        int selectedRow = accountsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a bank account to unfreeze.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        int accountId = (int) tableModel.getValueAt(selectedRow, 0);
        boolean success = bankAccountDAO.unfreezeBankAccount(accountId);
    
        if (success) {
            JOptionPane.showMessageDialog(this, "Account ID " + accountId + " has been unfrozen.", "Success", JOptionPane.INFORMATION_MESSAGE);
            loadAccounts(); // Reload the accounts after unfreezing
        } else {
            JOptionPane.showMessageDialog(this, "Failed to unfreeze the account.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void createAccount() {
        // Get the customer ID first
        JTextField customerIdField = new JTextField();
        Object[] customerMessage = {
            "Enter Customer ID:", customerIdField
        };
        
        int customerOption = JOptionPane.showConfirmDialog(this, customerMessage, "Select Customer", JOptionPane.OK_CANCEL_OPTION);
        
        if (customerOption == JOptionPane.OK_OPTION) {
            try {
                int customerId = Integer.parseInt(customerIdField.getText().trim());
                
                // Get the customer from the database
                CustomerDAO customerDAO = new CustomerDAO();
                Customer customer = customerDAO.getCustomerById(customerId);
                
                if (customer == null) {
                    JOptionPane.showMessageDialog(this, "Customer not found with ID: " + customerId, "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Now create a form to get the account details
                JComboBox<String> accountTypeCombo = new JComboBox<>(new String[]{"Saving", "Current", "Checking"});
                JTextField pinField = new JTextField();
                JTextField initialBalanceField = new JTextField("0.0");
                
                Object[] message = {
                    "Account Type:", accountTypeCombo,
                    "PIN (4 digits):", pinField,
                    "Initial Balance:", initialBalanceField
                };
                
                int option = JOptionPane.showConfirmDialog(this, message, "Create New Account", JOptionPane.OK_CANCEL_OPTION);
                
                if (option == JOptionPane.OK_OPTION) {
                    // Get form values
                    String accountType = (String) accountTypeCombo.getSelectedItem();
                    String pinText = pinField.getText().trim();
                    String balanceText = initialBalanceField.getText().trim();
                    
                    // Validation
                    int pin;
                    try {
                        pin = Integer.parseInt(pinText);
                        if (String.valueOf(pin).length() != 4) {
                            throw new Exception("PIN must be a 4-digit number.");
                        }
                    } catch (NumberFormatException e) {
                        throw new Exception("PIN must be a numeric 4-digit value.");
                    }
                    
                    double balance;
                    try {
                        balance = Double.parseDouble(balanceText);
                        if (balance < 0) {
                            throw new Exception("Initial balance cannot be negative.");
                        }
                    } catch (NumberFormatException e) {
                        throw new Exception("Initial balance must be a valid number.");
                    }
                    
                    // Create the bank account using a modified version of customer's createBankAccount
                    BankAccountDAO bankAccountDAO = new BankAccountDAO();
                    
                    // Create a new bank account
                    BankAccount newAccount = new BankAccount(
                        customer.getFirstName(),
                        customer.getLastName(),
                        accountType,
                        "Active",
                        pin
                    );
                    
                    // Set customer ID
                    newAccount.setCustomerId(customerId);
                    
                    // Generate and set a unique account number
                    int accountNumber = BankAccount.generateAccountNumber();
                    newAccount.setAccountNumber(accountNumber);
                    
                    // Set the initial balance
                    newAccount.setBalance(balance);
                    
                    // Save the bank account - note this method is void
                    bankAccountDAO.saveBankAccount(newAccount);
                    
                    // Show success message based on account being created
                    JOptionPane.showMessageDialog(this, "Account created successfully for customer: " + 
                        customer.getFirstName() + " " + customer.getLastName() +
                        "\nAccount Number: " + newAccount.getAccountNumber(), 
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                    loadAccounts(); // Reload the accounts after creation
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Customer ID must be a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /* private void deleteAccount() {
        int selectedRow = accountsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a bank account to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        int accountId = (int) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete account ID " + accountId + "?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
    
        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = bankAccountDAO.deleteBankAccount(accountId);
    
            if (success) {
                JOptionPane.showMessageDialog(this, "Account ID " + accountId + " has been deleted.", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadAccounts(); // Reload the accounts after deletion
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete the account.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    } */
    
    private void searchAccounts() {
        String searchTerm = searchField.getText().trim().toLowerCase();
        
        if (searchTerm.isEmpty()) {
            // If search field is empty, load all accounts
            loadAccounts();
            return;
        }
        
        List<BankAccount> allAccounts = bankAccountDAO.getAllBankAccounts();
        List<BankAccount> filteredAccounts = new ArrayList<>();
        
        // Filter accounts based on search term
        for (BankAccount account : allAccounts) {
            // Search by account ID, customer ID, account number, name, or account type
            if (String.valueOf(account.getAccountNumber()).contains(searchTerm) ||
                String.valueOf(account.getCustomerId()).contains(searchTerm) ||
                account.getFirstName().toLowerCase().contains(searchTerm) ||
                account.getLastName().toLowerCase().contains(searchTerm) ||
                account.getAccountType().toLowerCase().contains(searchTerm)) {
                filteredAccounts.add(account);
            }
        }
        
        // Clear existing rows
        tableModel.setRowCount(0);
        
        // Populate table with filtered results
        for (BankAccount account : filteredAccounts) {
            Object[] row = { 
                account.getAccountNumber(), // Using account number as the ID since that seems to be the primary key
                account.getCustomerId(),
                account.getAccountNumber(),
                account.getFirstName() + " " + account.getLastName(),
                account.getAccountType(),
                account.getBalance(),
                account.getAccountStatus()
            };
            tableModel.addRow(row);
        }
        
        if (filteredAccounts.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No accounts found matching your search.", "Search Results", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void loadAccounts() {
        List<BankAccount> bankAccounts = bankAccountDAO.getAllBankAccounts();
    
        // Clear existing rows before loading
        tableModel.setRowCount(0);
    
        for (BankAccount account : bankAccounts) {
            Object[] row = { 
                account.getAccountNumber(), // Using account number as the account ID
                account.getCustomerId(),
                account.getAccountNumber(),
                account.getFirstName() + " " + account.getLastName(),
                account.getAccountType(),
                account.getBalance(),
                account.getAccountStatus()
            };
            tableModel.addRow(row);
        }
    }
}