package GUI.customer_gui;

import bankaccount.BankAccount;
import database.BankAccountDAO;
import database.TransactionDAO;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import transaction.Transaction;
import transaction.TransactionManager;
import user.Customer;

public class TransactionsForm extends JFrame {
    private Customer currentCustomer;
    private JComboBox<String> accountSelector;
    private JButton depositButton;
    private JButton withdrawButton;
    private JButton transferButton;
    private JButton viewHistoryButton;
    private JButton backButton;
    private Color brandGreen = new Color(0, 175, 0);
    private TransactionManager transactionManager;

    public TransactionsForm(Customer customer) {
        this.currentCustomer = customer;
        this.transactionManager = new TransactionManager();
        
        setTitle("Bank App - Transactions");
        setLayout(null);
        setSize(360, 812);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        // Header
        JLabel headerLabel = new JLabel("Banking Transactions", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(brandGreen);
        headerLabel.setBounds(30, 30, 300, 40);
        add(headerLabel);
        
        // Account selector
        JLabel selectAccountLabel = new JLabel("Select Account:");
        selectAccountLabel.setBounds(30, 90, 300, 20);
        add(selectAccountLabel);
        
        accountSelector = new JComboBox<>();
        updateAccountSelector();
        accountSelector.setBounds(30, 115, 300, 40);
        add(accountSelector);
        
        // Transaction buttons
        depositButton = new JButton("Deposit");
        withdrawButton = new JButton("Withdraw");
        transferButton = new JButton("Transfer");
        viewHistoryButton = new JButton("Transaction History");
        backButton = new JButton("Back to Main Menu");
        
        // Set positions
        depositButton.setBounds(30, 180, 300, 60);
        withdrawButton.setBounds(30, 250, 300, 60);
        transferButton.setBounds(30, 320, 300, 60);
        viewHistoryButton.setBounds(30, 390, 300, 60);
        backButton.setBounds(30, 700, 300, 50);
        
        // Style buttons
        styleButton(depositButton, true);
        styleButton(withdrawButton, true);
        styleButton(transferButton, true);
        styleButton(viewHistoryButton, true);
        styleButton(backButton, false);
        
        // Add buttons
        add(depositButton);
        add(withdrawButton);
        add(transferButton);
        add(viewHistoryButton);
        add(backButton);
        
        // Action listeners
        depositButton.addActionListener(e -> handleDeposit());
        withdrawButton.addActionListener(e -> handleWithdraw());
        transferButton.addActionListener(e -> handleTransfer());
        viewHistoryButton.addActionListener(e -> handleViewHistory());
        
        backButton.addActionListener(e -> {
            MainMenuForm mainMenu = new MainMenuForm(currentCustomer);
            mainMenu.setVisible(true);
            dispose();
        });
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
    }
    
    private void updateAccountSelector() {
        accountSelector.removeAllItems();
        ArrayList<BankAccount> accounts = currentCustomer.getBankAccounts();
        
        if (accounts == null || accounts.isEmpty()) {
            accountSelector.addItem("No accounts available");
        } else {
            for (BankAccount account : accounts) {
                accountSelector.addItem("Account #" + account.getAccountNumber() + " - " + account.getAccountType());
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
    
    private void handleDeposit() {
        if (getSelectedAccount() == null) {
            showStyledDialog("Please select an account first", false);
            return;
        }
        
        JTextField amountField = new JTextField(10);
        JPasswordField pinField = new JPasswordField(4);
        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        panel.add(new JLabel("Enter amount to deposit:"));
        panel.add(amountField);
        panel.add(new JLabel("Enter PIN:"));
        panel.add(pinField);
        
        int result = JOptionPane.showConfirmDialog(this, panel, "Deposit", 
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
                        
                        showStyledDialog("Deposit successful! New balance: $" + updatedAccount.getBalance(), true);
                        
                        // Update the customer's account list
                        ArrayList<BankAccount> updatedAccounts = bankAccountDAO.getBankAccountsByCustomerId(currentCustomer.getCustomerId());
                        currentCustomer.setBankAccounts(updatedAccounts);
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
    
    private void handleWithdraw() {
        if (getSelectedAccount() == null) {
            showStyledDialog("Please select an account first", false);
            return;
        }
        
        JTextField amountField = new JTextField(10);
        JPasswordField pinField = new JPasswordField(4);
        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        panel.add(new JLabel("Enter amount to withdraw:"));
        panel.add(amountField);
        panel.add(new JLabel("Enter PIN:"));
        panel.add(pinField);
        
        int result = JOptionPane.showConfirmDialog(this, panel, "Withdraw", 
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
                        
                        showStyledDialog("Withdrawal successful! New balance: $" + updatedAccount.getBalance(), true);
                        
                        // Update the customer's account list
                        ArrayList<BankAccount> updatedAccounts = bankAccountDAO.getBankAccountsByCustomerId(currentCustomer.getCustomerId());
                        currentCustomer.setBankAccounts(updatedAccounts);
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
    
    private void handleTransfer() {
        if (getSelectedAccount() == null) {
            showStyledDialog("Please select an account first", false);
            return;
        }
    
        // Create transfer dialog with multiple fields
        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
    
        ButtonGroup transferType = new ButtonGroup();
        JRadioButton ownAccountsRadio = new JRadioButton("Transfer between my accounts");
        JRadioButton otherAccountRadio = new JRadioButton("Transfer to another account");
        transferType.add(ownAccountsRadio);
        transferType.add(otherAccountRadio);
        ownAccountsRadio.setSelected(true);  // Default to own account transfer
    
        JComboBox<String> senderAccountSelector = new JComboBox<>();
        JComboBox<String> recipientAccountSelector = new JComboBox<>();
        ArrayList<BankAccount> accounts = currentCustomer.getBankAccounts();
    
        // Populate sender account selector
        for (BankAccount account : accounts) {
            senderAccountSelector.addItem("Account #" + account.getAccountNumber() + " - " + account.getAccountType());
        }
    
        // Initially populate recipient account list (excluding the selected sender account)
        updateRecipientList(senderAccountSelector, recipientAccountSelector, accounts);
    
        JTextField accountNumberField = new JTextField(10);
        JTextField amountField = new JTextField(10);
        JPasswordField pinField = new JPasswordField(4);
    
        panel.add(new JLabel("Transfer Type:"));
        panel.add(ownAccountsRadio);
        panel.add(otherAccountRadio);
        panel.add(new JLabel("Sender Account:"));
        panel.add(senderAccountSelector);
        panel.add(new JLabel("Recipient Account:"));
        panel.add(recipientAccountSelector);
        panel.add(new JLabel("OR Enter Account Number:"));
        panel.add(accountNumberField);
        panel.add(new JLabel("Enter amount to transfer:"));
        panel.add(amountField);
        panel.add(new JLabel("Enter PIN:"));
        panel.add(pinField);
    
        // Add an ActionListener to update the recipient list immediately when the sender account is changed
        senderAccountSelector.addActionListener(e -> {
            updateRecipientList(senderAccountSelector, recipientAccountSelector, accounts);
        });
    
        // Show only relevant account selectors based on selected transfer type
        ownAccountsRadio.addActionListener(e -> {
            senderAccountSelector.setEnabled(true);
            recipientAccountSelector.setEnabled(true);
            accountNumberField.setEnabled(false);
            
            // Update recipient list when transfer type changes to "own accounts"
            updateRecipientList(senderAccountSelector, recipientAccountSelector, accounts);
        });
    
        otherAccountRadio.addActionListener(e -> {
            senderAccountSelector.setEnabled(false);
            recipientAccountSelector.setEnabled(false);
            accountNumberField.setEnabled(true);
        });
    
        int result = JOptionPane.showConfirmDialog(this, panel, "Transfer Funds",
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
    
                    BankAccount senderAccount = null;
                    BankAccount recipientAccount = null;
                    BankAccountDAO bankAccountDAO = new BankAccountDAO();
    
                    if (ownAccountsRadio.isSelected()) {
                        // Get selected sender and recipient accounts from dropdown
                        int senderIndex = senderAccountSelector.getSelectedIndex();
                        int recipientIndex = recipientAccountSelector.getSelectedIndex();
    
                        // Retrieve selected sender account
                        senderAccount = accounts.get(senderIndex);
    
                        // Retrieve selected recipient account
                        recipientAccount = accounts.get(recipientIndex);
    
                    } else {
                        // Get recipient account from entered account number
                        int recipientAccountNumber = Integer.parseInt(accountNumberField.getText());
                        recipientAccount = bankAccountDAO.getBankAccountById(recipientAccountNumber);
    
                        if (recipientAccount == null) {
                            showStyledDialog("Recipient account not found", false);
                            return;
                        }
    
                        senderAccount = getSelectedAccount(); // Sender account is already selected in this case
                    }
    
                    // Perform transfer
                    TransactionDAO transactionDAO = new TransactionDAO();
                    boolean success = transactionDAO.transferFunds(
                            senderAccount.getAccountNumber(),
                            recipientAccount.getAccountNumber(),
                            amount);
    
                    if (success) {
                        // Get updated account information
                        BankAccount updatedAccount = bankAccountDAO.getBankAccountById(senderAccount.getAccountNumber());
    
                        showStyledDialog("Transfer successful! New balance: $" + updatedAccount.getBalance(), true);
    
                        // Update the customer's account list
                        ArrayList<BankAccount> updatedAccounts = bankAccountDAO.getBankAccountsByCustomerId(currentCustomer.getCustomerId());
                        currentCustomer.setBankAccounts(updatedAccounts);
                    } else {
                        showStyledDialog("Transfer failed. Please try again.", false);
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
    
    private void updateRecipientList(JComboBox<String> senderAccountSelector, JComboBox<String> recipientAccountSelector, ArrayList<BankAccount> accounts) {
        // Clear the existing items in recipient list
        recipientAccountSelector.removeAllItems();
    
        // Get selected sender account number
        int senderAccountNumber = Integer.parseInt(senderAccountSelector.getSelectedItem().toString().split(" ")[1].substring(1));
    
        // Populate recipient account list excluding the sender account
        for (BankAccount account : accounts) {
            if (account.getAccountNumber() != senderAccountNumber) {
                recipientAccountSelector.addItem("Account #" + account.getAccountNumber() + " - " + account.getAccountType());
            }
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    private void handleViewHistory() {
        // Create a new dialog to display transaction history
        JDialog historyDialog = new JDialog(this, "Transaction History", true);
        historyDialog.setSize(500, 500);
        historyDialog.setLayout(new BorderLayout());
        historyDialog.setLocationRelativeTo(this);
        
        // Get transaction history
        TransactionDAO transactionDAO = new TransactionDAO();
        List<Transaction> transactions = transactionDAO.getAllTransactions();
        
        // Filter transactions for current customer if needed
        ArrayList<BankAccount> accounts = currentCustomer.getBankAccounts();
        List<Integer> accountNumbers = new ArrayList<>();
        for (BankAccount account : accounts) {
            accountNumbers.add(account.getAccountNumber());
        }
        
        // Create table model
        // Create table model
    String[] columnNames = {"ID", "Account", "Type", "Amount", "Date", "Status"};
    Object[][] data = new Object[transactions.size()][6];
    
    int rowIndex = 0;
    for (Transaction transaction : transactions) {
        // Only include transactions for this customer's accounts
        if (accountNumbers.contains(transaction.getBankAccount().getAccountNumber())) {
            data[rowIndex][0] = transaction.getTransactionId();
            data[rowIndex][1] = transaction.getBankAccount().getAccountNumber();
            data[rowIndex][2] = transaction.getType();
            data[rowIndex][3] = "$" + transaction.getAmount();
            data[rowIndex][4] = transaction.getTransactionDate();
            data[rowIndex][5] = transaction.getStatus();
            rowIndex++;
        }
    }
    
    // Create the table with the filtered data
    JTable transactionTable = new JTable(data, columnNames);
    JScrollPane scrollPane = new JScrollPane(transactionTable);
    
    // Add table to dialog
    historyDialog.add(scrollPane, BorderLayout.CENTER);
    
    // Add close button
    JButton closeButton = new JButton("Close");
    closeButton.setBackground(brandGreen);
    closeButton.setForeground(Color.WHITE);
    closeButton.addActionListener(e -> historyDialog.dispose());
    
    JPanel buttonPanel = new JPanel();
    buttonPanel.add(closeButton);
    historyDialog.add(buttonPanel, BorderLayout.SOUTH);
    
    // Show dialog
    historyDialog.setVisible(true);
}

private void showStyledDialog(String message, boolean isSuccess) {
    JDialog dialog = new JDialog(this, "Message", true);
    dialog.setSize(300, 150);
    dialog.setLayout(new BorderLayout());
    dialog.setLocationRelativeTo(this);
    
    // Create panel for message
    JPanel messagePanel = new JPanel(new GridBagLayout());
    messagePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
    
    // Create message label
    JLabel messageLabel = new JLabel(message);
    messageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
    
    // Set icon based on status
    if (isSuccess) {
        messageLabel.setForeground(brandGreen);
    } else {
        messageLabel.setForeground(Color.RED);
    }
    
    messagePanel.add(messageLabel);
    dialog.add(messagePanel, BorderLayout.CENTER);
    
    // Add OK button
    JButton okButton = new JButton("OK");
    okButton.setBackground(brandGreen);
    okButton.setForeground(Color.WHITE);
    okButton.addActionListener(e -> dialog.dispose());
    
    JPanel buttonPanel = new JPanel();
    buttonPanel.add(okButton);
    dialog.add(buttonPanel, BorderLayout.SOUTH);
    
    // Show dialog
    dialog.setVisible(true);
}

public static void main(String[] args) {
    // For testing purposes
    SwingUtilities.invokeLater(() -> {
        Customer testCustomer = new Customer();
        testCustomer.setCustomerId(1);
        testCustomer.setFirstName("John");
        testCustomer.setLastName("Doe");
        
        // Load accounts for test customer
        BankAccountDAO bankAccountDAO = new BankAccountDAO();
        ArrayList<BankAccount> accounts = bankAccountDAO.getBankAccountsByCustomerId(testCustomer.getCustomerId());
        testCustomer.setBankAccounts(accounts);
        
        TransactionsForm form = new TransactionsForm(testCustomer);
        form.setVisible(true);
    });
}
}