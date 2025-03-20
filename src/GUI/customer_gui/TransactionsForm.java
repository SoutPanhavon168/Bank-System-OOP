package GUI.customer_gui;

import bankaccount.BankAccount;
import database.BankAccountDAO;
import database.TransactionDAO;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.FontUIResource;
import transaction.Transaction;
import transaction.TransactionManager;
import user.Customer;

public class TransactionsForm extends JFrame {
    private Customer currentCustomer;
    private JPanel mainPanel;
    private JComboBox<String> accountSelector;
    private TransactionManager transactionManager;
    
    // Colors to match MainMenuForm
    private Color brandGreen = new Color(0, 175, 0);
    private Color darkGreen = new Color(0, 125, 0);
    
    // Transaction buttons
    private JButton depositButton;
    private JButton withdrawButton;
    private JButton transferButton;
    private JButton viewHistoryButton;
    private JButton backButton;

    public TransactionsForm(Customer customer) {
        this.currentCustomer = customer;
        this.transactionManager = new TransactionManager();
        
        setTitle("Bank App - Transactions");
        setSize(1024, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());
        
        createHeader();
        createMainPanel();
        
        // Display the transactions content
        displayTransactionOptions();
    }
    
    private void createHeader() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(darkGreen);
        headerPanel.setPreferredSize(new Dimension(1024, 80));
        headerPanel.setBorder(new EmptyBorder(10, 20, 10, 20));
        
        JLabel titleLabel = new JLabel("BANK TRANSACTION CENTER");
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
    
    private void createMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(new EmptyBorder(20, 30, 20, 30));
        
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private void displayTransactionOptions() {
        mainPanel.removeAll();
        
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(new EmptyBorder(10, 20, 10, 20));
        
        // Header
        JLabel headerLabel = new JLabel("Banking Transactions", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(brandGreen);
        headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(headerLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Account selector panel
        JPanel selectorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        selectorPanel.setBackground(Color.WHITE);
        selectorPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        
        JLabel selectAccountLabel = new JLabel("Select Account:");
        selectAccountLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        accountSelector = new JComboBox<>();
        updateAccountSelector();
        accountSelector.setPreferredSize(new Dimension(500, 30));
        accountSelector.setFont(new Font("Arial", Font.PLAIN, 14));
        
        selectorPanel.add(selectAccountLabel);
        selectorPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        selectorPanel.add(accountSelector);
        
        contentPanel.add(selectorPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        
        // Transaction buttons panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(4, 1, 0, 15));
        buttonsPanel.setBackground(Color.WHITE);
        buttonsPanel.setBorder(new EmptyBorder(0, 50, 0, 50));
        buttonsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));
        
        // Create transaction buttons
        depositButton = createTransactionButton("Deposit Funds", "deposit.png");
        withdrawButton = createTransactionButton("Withdraw Funds", "withdraw.png");
        transferButton = createTransactionButton("Transfer Funds", "transfer.png");
        viewHistoryButton = createTransactionButton("View Transaction History", "history.png");
        
        // Add buttons to panel
        buttonsPanel.add(depositButton);
        buttonsPanel.add(withdrawButton);
        buttonsPanel.add(transferButton);
        buttonsPanel.add(viewHistoryButton);
        
        contentPanel.add(buttonsPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        
        // Add action listeners
        depositButton.addActionListener(e -> handleDeposit());
        withdrawButton.addActionListener(e -> handleWithdraw());
        transferButton.addActionListener(e -> handleTransfer());
        viewHistoryButton.addActionListener(e -> handleViewHistory());
        
        // Back button panel
        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        backPanel.setBackground(Color.WHITE);
        
        backButton = new JButton("Back to Main Menu");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(darkGreen);
        backButton.setFocusPainted(false);
        backButton.setPreferredSize(new Dimension(200, 40));
        backButton.addActionListener(e -> {
            MainMenuForm mainMenu = new MainMenuForm(currentCustomer);
            mainMenu.setVisible(true);
            dispose();
        });
        
        backPanel.add(backButton);
        
        contentPanel.add(backPanel);
        
        // Add scrolling if needed
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }
    
    private JButton createTransactionButton(String text, String iconName) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(brandGreen);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(300, 50));
        
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
    
    private void updateAccountSelector() {
        accountSelector.removeAllItems();
        ArrayList<BankAccount> accounts = currentCustomer.getBankAccounts();
        
        if (accounts == null || accounts.isEmpty()) {
            accountSelector.addItem("No accounts available");
        } else {
            for (BankAccount account : accounts) {
                accountSelector.addItem("Account #" + account.getAccountNumber() + " - " + account.getAccountType() + " - Balance: $" + String.format("%.2f", account.getBalance()));
            }
        }
    }
    
    private BankAccount getSelectedAccount() {
        int selectedIndex = accountSelector.getSelectedIndex();
        ArrayList<BankAccount> accounts = currentCustomer.getBankAccounts();
        
        if (selectedIndex >= 0 && selectedIndex < accounts.size()) {
            return accounts.get(selectedIndex);
        }
        return null;
    }

    // Handle Deposit
    private void handleDeposit() {
        if (getSelectedAccount() == null) {
            showStyledDialog("Please select an account first", false);
            return;
        }
        if(!getSelectedAccount().isActive()) {
            showStyledDialog("This account is not active. Please activate the account first.", false);
            return;
        }
        
        // Create deposit dialog with clean interface
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.setBackground(Color.WHITE);
        
        // Amount field with panel
        JPanel amountPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        amountPanel.setBackground(Color.WHITE);
        JLabel amountLabel = new JLabel("Enter amount to deposit:");
        amountLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        JTextField amountField = new JTextField(15);
        amountField.setFont(new Font("Arial", Font.PLAIN, 14));
        amountPanel.add(amountLabel);
        amountPanel.add(amountField);
        
        // PIN field with panel
        JPanel pinPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pinPanel.setBackground(Color.WHITE);
        JLabel pinLabel = new JLabel("Enter PIN:");
        pinLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        JPasswordField pinField = new JPasswordField(15);
        pinField.setFont(new Font("Arial", Font.PLAIN, 14));
        pinPanel.add(pinLabel);
        pinPanel.add(pinField);
        
        panel.add(amountPanel);
        panel.add(pinPanel);
        
        // Customized JOptionPane
        UIManager.put("OptionPane.background", Color.WHITE);
        UIManager.put("Panel.background", Color.WHITE);
        UIManager.put("OptionPane.buttonFont", new FontUIResource("Arial", Font.BOLD, 14));
        
        int result = JOptionPane.showConfirmDialog(this, panel, "Deposit Funds", 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            try {
                double amount = Double.parseDouble(amountField.getText());
                String pinText = new String(pinField.getPassword());
                int pin = Integer.parseInt(pinText);
                
                // Verify PIN
                if (pin == getSelectedAccount().getPin()) {
                    // Create transaction
                    Transaction transaction = new Transaction(getSelectedAccount(), "Deposit", amount, "Completed");
                    
                    // Save the transaction using TransactionDAO
                    TransactionDAO transactionDAO = new TransactionDAO();
                    boolean success = transactionDAO.saveTransaction(transaction);
                    
                    if (success) {
                        // Get updated account information
                        BankAccountDAO bankAccountDAO = new BankAccountDAO();
                        BankAccount updatedAccount = bankAccountDAO.getBankAccountById(getSelectedAccount().getAccountNumber());
                        
                        showStyledDialog("Deposit successful! New balance: $" + String.format("%.2f", updatedAccount.getBalance()), true);
                        
                        // Update the customer's account list
                        ArrayList<BankAccount> updatedAccounts = bankAccountDAO.getBankAccountsByCustomerId(currentCustomer.getCustomerId());
                        currentCustomer.setBankAccounts(updatedAccounts);
                        updateAccountSelector(); // Refresh the account selector with updated balances
                    } else {
                        showStyledDialog("Transaction failed. Please try again.", false);
                    }
                } else {
                    showStyledDialog("Incorrect PIN. Transaction cancelled.", false);
                }
            } catch (NumberFormatException e) {
                showStyledDialog("Please enter valid numbers", false);
            } catch (Exception e) {
                showStyledDialog("Error: " + e.getMessage(), false);
            }
        }
    }
    
    // Handle Withdraw
    private void handleWithdraw() {
        if (getSelectedAccount() == null) {
            showStyledDialog("Please select an account first", false);
            return;
        }
        if(!getSelectedAccount().isActive()) {
            showStyledDialog("This account is not active. Please activate the account first.", false);
            return;
        }
        
        // Create withdraw dialog with clean interface
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.setBackground(Color.WHITE);
        
        // Amount field with panel
        JPanel amountPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        amountPanel.setBackground(Color.WHITE);
        JLabel amountLabel = new JLabel("Enter amount to withdraw:");
        amountLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        JTextField amountField = new JTextField(15);
        amountField.setFont(new Font("Arial", Font.PLAIN, 14));
        amountPanel.add(amountLabel);
        amountPanel.add(amountField);
        
        // PIN field with panel
        JPanel pinPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pinPanel.setBackground(Color.WHITE);
        JLabel pinLabel = new JLabel("Enter PIN:");
        pinLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        JPasswordField pinField = new JPasswordField(15);
        pinField.setFont(new Font("Arial", Font.PLAIN, 14));
        pinPanel.add(pinLabel);
        pinPanel.add(pinField);
        
        panel.add(amountPanel);
        panel.add(pinPanel);
        
        // Customized JOptionPane
        UIManager.put("OptionPane.background", Color.WHITE);
        UIManager.put("Panel.background", Color.WHITE);
        UIManager.put("OptionPane.buttonFont", new FontUIResource("Arial", Font.BOLD, 14));
        
        int result = JOptionPane.showConfirmDialog(this, panel, "Withdraw Funds", 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            try {
                double amount = Double.parseDouble(amountField.getText());
                String pinText = new String(pinField.getPassword());
                int pin = Integer.parseInt(pinText);
                
                // Verify PIN
                if (pin == getSelectedAccount().getPin()) {
                    // Check if there are sufficient funds
                    if (amount > getSelectedAccount().getBalance()) {
                        showStyledDialog("Insufficient funds", false);
                        return;
                    }
                    
                    // Create transaction
                    Transaction transaction = new Transaction(getSelectedAccount(), "Withdraw", amount, "Completed");
                    
                    // Save the transaction using TransactionDAO
                    TransactionDAO transactionDAO = new TransactionDAO();
                    boolean success = transactionDAO.saveTransaction(transaction);
                    
                    if (success) {
                        // Get updated account information
                        BankAccountDAO bankAccountDAO = new BankAccountDAO();
                        BankAccount updatedAccount = bankAccountDAO.getBankAccountById(getSelectedAccount().getAccountNumber());
                        
                        showStyledDialog("Withdrawal successful! New balance: $" + String.format("%.2f", updatedAccount.getBalance()), true);
                        
                        // Update the customer's account list
                        ArrayList<BankAccount> updatedAccounts = bankAccountDAO.getBankAccountsByCustomerId(currentCustomer.getCustomerId());
                        currentCustomer.setBankAccounts(updatedAccounts);
                        updateAccountSelector(); // Refresh the account selector with updated balances
                    } else {
                        showStyledDialog("Transaction failed. Please try again.", false);
                    }
                } else {
                    showStyledDialog("Incorrect PIN. Transaction cancelled.", false);
                }
            } catch (NumberFormatException e) {
                showStyledDialog("Please enter valid numbers", false);
            } catch (Exception e) {
                showStyledDialog("Error: " + e.getMessage(), false);
            }
        }
    }

    // Handle Transfer
    private void handleTransfer() {
        if (getSelectedAccount() == null) {
            showStyledDialog("Please select an account first", false);
            return;
        }
        if(!getSelectedAccount().isActive()) {
            showStyledDialog("This account is not active. Please activate the account first.", false);
            return;
        }
    
        // Create transfer dialog with clean interface
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.setBackground(Color.WHITE);
        
        // Recipient account field with panel
        JPanel recipientPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        recipientPanel.setBackground(Color.WHITE);
        JLabel recipientLabel = new JLabel("Recipient Account Number:");
        recipientLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        JTextField recipientAccountField = new JTextField(15);
        recipientAccountField.setFont(new Font("Arial", Font.PLAIN, 14));
        recipientPanel.add(recipientLabel);
        recipientPanel.add(recipientAccountField);
        
        // Amount field with panel
        JPanel amountPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        amountPanel.setBackground(Color.WHITE);
        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        JTextField amountField = new JTextField(15);
        amountField.setFont(new Font("Arial", Font.PLAIN, 14));
        amountPanel.add(amountLabel);
        amountPanel.add(amountField);
        
        // PIN field with panel
        JPanel pinPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pinPanel.setBackground(Color.WHITE);
        JLabel pinLabel = new JLabel("PIN:");
        pinLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        JPasswordField pinField = new JPasswordField(15);
        pinField.setFont(new Font("Arial", Font.PLAIN, 14));
        pinPanel.add(pinLabel);
        pinPanel.add(pinField);
        
        panel.add(recipientPanel);
        panel.add(amountPanel);
        panel.add(pinPanel);
        
        // Customized JOptionPane
        UIManager.put("OptionPane.background", Color.WHITE);
        UIManager.put("Panel.background", Color.WHITE);
        UIManager.put("OptionPane.buttonFont", new FontUIResource("Arial", Font.BOLD, 14));
        
        int result = JOptionPane.showConfirmDialog(this, panel, "Transfer Funds", 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    
        if (result == JOptionPane.OK_OPTION) {
            showStyledDialog("Transfer feature is currently being implemented. Please try again later.", false);
            
            // Note: Implementation for transfer would go here
            // Similar to the commented out code in the original TransactionsForm
        }
    }
    
    // Handle Transaction History
    private void handleViewHistory() {
        showStyledDialog("This feature is not implemented yet", false);
        /* if (getSelectedAccount() == null) {
            showStyledDialog("Please select an account first", false);
            return;
        } */
        /* if(!getSelectedAccount().isActive()) {
            showStyledDialog("This account is not active. Please activate the account first.", false);
            return;
        } */
        List<Transaction> history = transactionManager.viewCurrentUserTransactionHistory(getSelectedAccount().getAccountNumber());
        if (history.isEmpty()) {
            showStyledDialog("No transactions found for this account.", false);
            return;
        }

        
        // Create styled transaction history table
        String[] columnNames = {"Date", "Type", "Amount", "Status"};
        String[][] data = new String[history.size()][4];
        
        for (int i = 0; i < history.size(); i++) {
            Transaction transaction = history.get(i);
            data[i][0] = transaction.getDate();
            // Fix null pointer exception by adding null check
            data[i][1] = transaction.getType() != null ? transaction.getType().toString() : "N/A";
            data[i][2] = String.format("$%.2f", transaction.getAmount());
            data[i][3] = transaction.getStatus();
        }
        
        JTable table = new JTable(data, columnNames);
        table.setFillsViewportHeight(true);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setBackground(brandGreen);
        table.getTableHeader().setForeground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(600, 300));
        
        // Create a panel for the table with a header
        JPanel historyPanel = new JPanel(new BorderLayout());
        historyPanel.setBackground(Color.WHITE);
        JLabel historyHeader = new JLabel("Transaction History", JLabel.CENTER);
        historyHeader.setFont(new Font("Arial", Font.BOLD, 18));
        historyHeader.setForeground(brandGreen);
        historyHeader.setBorder(new EmptyBorder(10, 0, 10, 0));
        historyPanel.add(historyHeader, BorderLayout.NORTH);
        historyPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Customized JOptionPane
        UIManager.put("OptionPane.background", Color.WHITE);
        UIManager.put("Panel.background", Color.WHITE);
        JOptionPane.showMessageDialog(this, historyPanel, "Transaction History", JOptionPane.PLAIN_MESSAGE);
    }
    
    private void showStyledDialog(String message, boolean isSuccess) {
        // Create a custom panel for the message
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 30, 20, 30));
        
        JLabel iconLabel = new JLabel();
        iconLabel.setIcon(isSuccess ? 
                UIManager.getIcon("OptionPane.informationIcon") : 
                UIManager.getIcon("OptionPane.errorIcon"));
        
        JLabel messageLabel = new JLabel(message);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        messageLabel.setBorder(new EmptyBorder(0, 10, 0, 0));
        
        panel.add(iconLabel, BorderLayout.WEST);
        panel.add(messageLabel, BorderLayout.CENTER);
        
        // Customized JOptionPane
        UIManager.put("OptionPane.background", Color.WHITE);
        UIManager.put("Panel.background", Color.WHITE);
        UIManager.put("OptionPane.buttonFont", new FontUIResource("Arial", Font.BOLD, 14));
        
        JOptionPane.showMessageDialog(this, panel, isSuccess ? "Success" : "Notification", 
                JOptionPane.PLAIN_MESSAGE);
    }
}