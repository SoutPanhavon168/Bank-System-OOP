package GUI.staff_gui;

import database.TransactionDAO;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import transaction.Transaction;
import user.staff.Staff;


public class TransactionsPanel extends JPanel {
    private Staff currentStaff;
    private TransactionDAO transactionDAO;
    private JTable transactionsTable;
    private DefaultTableModel tableModel;
    private Color brandBlue = new Color(0, 102, 204);
    private JTextField searchField;
    
    public TransactionsPanel(Staff staff) {
        this.currentStaff = staff;
        this.transactionDAO = new TransactionDAO();
        
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        createHeaderPanel();
        createControlPanel();
        createTablePanel();
        
        // Initially load all transactions
        loadTransactionData();
    }
    
    private void createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(new EmptyBorder(20, 20, 10, 20));
        
        JLabel titleLabel = new JLabel("Transaction Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(brandBlue);
        
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setBackground(Color.WHITE);
        
        JLabel searchLabel = new JLabel("Search: ");
        searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        
        searchButton.addActionListener(e -> {
            String searchTerm = searchField.getText().trim().toLowerCase();
            if (searchTerm.isEmpty()) {
                loadTransactionData(); // Reset to show all
            } else {
                filterTransactionData(searchTerm);
            }
        });
        
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        
        headerPanel.add(searchPanel, BorderLayout.EAST);
        
        add(headerPanel, BorderLayout.NORTH);
    }
    
    private void createControlPanel() {
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        controlPanel.setBackground(Color.WHITE);
        controlPanel.setBorder(new EmptyBorder(0, 20, 10, 20));
        
        JButton viewDetailsButton = new JButton("View Details");
        JButton refreshButton = new JButton("Refresh");
        
        // Style buttons
        viewDetailsButton.setBackground(brandBlue);
        viewDetailsButton.setForeground(Color.WHITE);
        viewDetailsButton.setFocusPainted(false);
        
        refreshButton.setBackground(new Color(240, 240, 240));
        
        // Add action listeners
        viewDetailsButton.addActionListener(e -> {
            int selectedRow = transactionsTable.getSelectedRow();
            if (selectedRow >= 0) {
                String transactionId = transactionsTable.getValueAt(selectedRow, 0).toString();
                showTransactionDetails(transactionId);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Please select a transaction to view details.", 
                    "Selection Required", 
                    JOptionPane.WARNING_MESSAGE);
            }
        });
        
        refreshButton.addActionListener(e -> loadTransactionData());
        
        controlPanel.add(viewDetailsButton);
        controlPanel.add(refreshButton);
        
        add(controlPanel, BorderLayout.CENTER);
    }
    
    private void createTablePanel() {
        // Create table model with columns
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };
        
        tableModel.addColumn("Transaction ID");
        tableModel.addColumn("Account Number");
        tableModel.addColumn("Amount");
        tableModel.addColumn("Type");
        tableModel.addColumn("Status");
        tableModel.addColumn("Date");
        
        // Create table
        transactionsTable = new JTable(tableModel);
        transactionsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        transactionsTable.setRowHeight(25);
        transactionsTable.getTableHeader().setReorderingAllowed(false);
        transactionsTable.getTableHeader().setBackground(brandBlue);
        transactionsTable.getTableHeader().setForeground(Color.WHITE);
        transactionsTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        
        // Add to scroll pane
        JScrollPane scrollPane = new JScrollPane(transactionsTable);
        add(scrollPane, BorderLayout.SOUTH);
    }
    
    private void loadTransactionData() {
        // Clear existing data
        tableModel.setRowCount(0);
        
        // Get all transactions from database
        List<Transaction> transactions = transactionDAO.getAllTransactions();
        
        // Populate table
        for (Transaction transaction : transactions) {
            tableModel.addRow(new Object[]{
                transaction.getTransactionID(),
                transaction.getBankAccount().getAccountNumber(),
                "$" + transaction.getAmount(),
                transaction.getType(),
                transaction.getStatus(),
                transaction.getTransactionDate()
            });
        }
    }
    
    private void filterTransactionData(String searchTerm) {
        // Clear existing data
        tableModel.setRowCount(0);
        
        // Get all transactions from database
        List<Transaction> transactions = transactionDAO.getAllTransactions();
        
        // Filter and populate table
        for (Transaction transaction : transactions) {
            if (transaction.getTransactionID().toLowerCase().contains(searchTerm) ||
                String.valueOf(transaction.getBankAccount().getAccountNumber()).contains(searchTerm) ||
                transaction.getType().toString().toLowerCase().contains(searchTerm) ||
                transaction.getStatus().toLowerCase().contains(searchTerm)) {
                
                tableModel.addRow(new Object[]{
                    transaction.getTransactionID(),
                    transaction.getBankAccount().getAccountNumber(),
                    "$" + transaction.getAmount(),
                    transaction.getType(),
                    transaction.getStatus(),
                    transaction.getTransactionDate()
                });
            }
        }
    }
    
    private void showTransactionDetails(String transactionId) {
        // Fetch the specific transaction
        Transaction transaction = transactionDAO.getTransactionById(transactionId);
        
        if (transaction == null) {
            JOptionPane.showMessageDialog(this, 
                "Transaction not found.", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Create a detailed view dialog
        JDialog detailsDialog = new JDialog();
        detailsDialog.setTitle("Transaction Details");
        detailsDialog.setModal(true);
        detailsDialog.setSize(400, 300);
        detailsDialog.setLocationRelativeTo(this);
        
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new GridLayout(0, 2, 10, 10));
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Add transaction details
        detailsPanel.add(new JLabel("Transaction ID:"));
        detailsPanel.add(new JLabel(transaction.getTransactionID()));
        
        detailsPanel.add(new JLabel("Account Number:"));
        detailsPanel.add(new JLabel(String.valueOf(transaction.getBankAccount().getAccountNumber())));
        
        detailsPanel.add(new JLabel("Amount:"));
        detailsPanel.add(new JLabel("$" + transaction.getAmount()));
        
        detailsPanel.add(new JLabel("Type:"));
        detailsPanel.add(new JLabel(transaction.getType().toString()));
        
        detailsPanel.add(new JLabel("Status:"));
        detailsPanel.add(new JLabel(transaction.getStatus()));
        
        detailsPanel.add(new JLabel("Date:"));
        detailsPanel.add(new JLabel(transaction.getTransactionDate().toString()));
        
        // Close button
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> detailsDialog.dispose());
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(closeButton);
        
        // Assemble dialog
        detailsDialog.setLayout(new BorderLayout());
        detailsDialog.add(detailsPanel, BorderLayout.CENTER);
        detailsDialog.add(buttonPanel, BorderLayout.SOUTH);
        
        detailsDialog.setVisible(true);
    }
}
