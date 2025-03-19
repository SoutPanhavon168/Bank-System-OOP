package GUI.staff_gui;

import database.TransactionDAO;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import transaction.Transaction;
import user.staff.Staff;

public class TransactionsPanel extends JPanel {
    private Staff currentStaff;
    private TransactionDAO transactionDAO;

    public TransactionsPanel(Staff staff) {
        this.currentStaff = staff;
        this.transactionDAO = new TransactionDAO();  // Initialize TransactionDAO
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Header panel for title
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(new Color(0, 102, 204));

        JLabel titleLabel = new JLabel("Transaction Management", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);

        // Table for transaction details
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new GridLayout(0, 7));  // 7 columns for each transaction field
        tablePanel.setBackground(Color.WHITE);

        // Add header row for table fields
        String[] columnHeaders = {"Transaction ID", "Account", "Amount", "Type", "Status", "Date", "Details"};
        for (String header : columnHeaders) {
            JLabel headerLabel = new JLabel(header, JLabel.CENTER);
            headerLabel.setFont(new Font("Arial", Font.BOLD, 14));
            headerLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            tablePanel.add(headerLabel);
        }

        // Fetch transactions and display them in the table
        List<Transaction> transactions = transactionDAO.getAllTransactions();
        if (transactions.isEmpty()) {
            tablePanel.add(new JLabel("No transactions found.", JLabel.CENTER));
        } else {
            for (Transaction transaction : transactions) {
                // Each transaction row
                tablePanel.add(createTableCell(transaction.getTransactionID()));
                tablePanel.add(createTableCell(String.valueOf(transaction.getBankAccount().getAccountNumber())));
                tablePanel.add(createTableCell("$" + transaction.getAmount()));
                tablePanel.add(createTableCell(transaction.getType().toString()));
                tablePanel.add(createTableCell(transaction.getStatus()));
                tablePanel.add(createTableCell(transaction.getTransactionDate().toString()));
                
                // Button for showing transaction details
                JButton detailsButton = new JButton("Details");
                detailsButton.setFont(new Font("Arial", Font.PLAIN, 12));
                detailsButton.setBackground(new Color(0, 153, 204));
                detailsButton.setForeground(Color.WHITE);
                detailsButton.setFocusPainted(false);
                detailsButton.addActionListener(e -> showTransactionDetails(transaction));
                tablePanel.add(detailsButton);
            }
        }

        JScrollPane scrollPane = new JScrollPane(tablePanel);
        add(scrollPane, BorderLayout.CENTER);
    }

    // Helper method to create a table cell with appropriate alignment and style
    private JLabel createTableCell(String content) {
        JLabel label = new JLabel(content, JLabel.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, 12));
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        label.setPreferredSize(new Dimension(100, 30));
        return label;
    }

    // Method to display detailed information about a specific transaction
    private void showTransactionDetails(Transaction transaction) {
        String details = "<html><b>Transaction ID:</b> " + transaction.getTransactionID() + "<br>" +
                         "<b>Account:</b> " + transaction.getBankAccount().getAccountNumber() + "<br>" +
                         "<b>Amount:</b> $" + transaction.getAmount() + "<br>" +
                         "<b>Type:</b> " + transaction.getType() + "<br>" +
                         "<b>Status:</b> " + transaction.getStatus() + "<br>" +
                         "<b>Date:</b> " + transaction.getTransactionDate() + "<br><br>" +
                         "<b>Details:</b> <i>Transaction Details...</i></html>";

        JOptionPane.showMessageDialog(this, details, "Transaction Details", JOptionPane.INFORMATION_MESSAGE);
    }
}
