package GUI.customer_gui;
import bankaccount.BankAccount;
import database.BankAccountDAO;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import transaction.TransactionManager;

public class TransactionForm extends JFrame {
    private ArrayList<BankAccount> userAccounts;
    private TransactionManager transactionManager;

    // Modify the constructor to accept a list of BankAccounts
    public TransactionForm(ArrayList<BankAccount> userAccounts) {
        this.userAccounts = userAccounts;
        this.transactionManager = new TransactionManager();

        setTitle("Transaction Form");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        JButton depositButton = createGreenButton("Deposit");
        depositButton.addActionListener(e -> handleDeposit());

        JButton withdrawButton = createGreenButton("Withdraw");
        withdrawButton.addActionListener(e -> handleWithdraw());

        JButton transferButton = createGreenButton("Transfer");
        transferButton.addActionListener(e -> handleTransfer());

        JButton viewTransactionsButton = createWhiteButton("View Transactions");
        viewTransactionsButton.addActionListener(e -> handleViewTransactions());

        add(depositButton);
        add(withdrawButton);
        add(transferButton);
        add(viewTransactionsButton);
    }

    private void handleDeposit() {
        transactionManager.deposit(userAccounts);
    }

    private void handleWithdraw() {
        transactionManager.withdraw(userAccounts);
    }

    private void handleTransfer() {
        transactionManager.transfer(userAccounts);
    }

    private void handleViewTransactions() {
        String transactionID = JOptionPane.showInputDialog(this, "Enter Transaction ID to View:");
        if (transactionID != null && !transactionID.isEmpty()) {
            transactionManager.viewSpecificTransaction(transactionID);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid Transaction ID.");
        }
    }

    private JButton createGreenButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(0, 175, 0));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        return button;
    }

    private JButton createWhiteButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(Color.WHITE);
        button.setForeground(new Color(0, 175, 0));
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(0, 175, 0)));
        return button;
    }

    public static void main(String[] args) {
        // Example of passing the list of BankAccounts to the TransactionForm
        BankAccountDAO dao = new BankAccountDAO();
        ArrayList<BankAccount> userAccounts = dao.getBankAccountsByCustomerId(1);  // Fetch accounts for customerId = 1

        TransactionForm transactionForm = new TransactionForm(userAccounts);
        transactionForm.setVisible(true);
    }
}
