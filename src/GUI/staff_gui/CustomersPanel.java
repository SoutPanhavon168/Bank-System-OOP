package GUI.staff_gui;

import database.CustomerDAO;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.*;
import user.Customer;
import user.staff.Staff;

public class CustomersPanel extends JPanel {
    private Staff currentStaff;
    private CustomerDAO customerDAO;
    private JTable customersTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JButton searchButton;
    private JButton viewButton;
    private JButton deleteButton;
    private Color brandBlue = new Color(0, 102, 204);
    
    public CustomersPanel(Staff staff) {
        this.currentStaff = staff;
        this.customerDAO = new CustomerDAO();  // Initialize CustomerDAO to interact with the database
        
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        createHeaderPanel();
        createTablePanel();
        createButtonPanel();
        
        // Load all customers initially
        loadCustomers();
    }

    private void createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        JLabel titleLabel = new JLabel("Customer Management");
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
        searchButton.addActionListener(e -> searchCustomers());
        searchPanel.add(searchButton);
        
        headerPanel.add(searchPanel, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);
    }

    private void createTablePanel() {
        // Create table model with column names
        String[] columnNames = {"Customer ID", "Name", "Email", "Phone", "Birthdate"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make cells non-editable
            }
        };
        
        customersTable = new JTable(tableModel);
        customersTable.setFont(new Font("Arial", Font.PLAIN, 14));
        customersTable.setRowHeight(25);
        customersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        customersTable.getTableHeader().setReorderingAllowed(false);
        customersTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        
        // Set column widths
        TableColumnModel columnModel = customersTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(80);
        columnModel.getColumn(1).setPreferredWidth(150);
        columnModel.getColumn(2).setPreferredWidth(200);
        columnModel.getColumn(3).setPreferredWidth(120);
        columnModel.getColumn(4).setPreferredWidth(120);
        
        // Add scroll pane
        JScrollPane scrollPane = new JScrollPane(customersTable);
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
        viewButton.addActionListener(e -> viewCustomerDetails());
        buttonPanel.add(viewButton);
        
        deleteButton = new JButton("Delete Customer");
        deleteButton.setFont(new Font("Arial", Font.BOLD, 14));
        deleteButton.setBackground(brandBlue);
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);
        deleteButton.addActionListener(e -> deleteCustomer());
        buttonPanel.add(deleteButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void viewCustomerDetails() {
        int selectedRow = customersTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a customer to view.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int customerId = (int) tableModel.getValueAt(selectedRow, 0);
        Customer customer = customerDAO.getCustomerById(customerId);
        
        if (customer != null) {
            JOptionPane.showMessageDialog(this, "Customer Details:\n" + customer.toString(), "Customer Details", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Customer not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteCustomer() {
        int selectedRow = customersTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a customer to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int customerId = (int) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete customer ID " + customerId + "?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                // Directly call the deleteCustomer method (no need for boolean success variable)
                customerDAO.deleteCustomer(customerId);
                JOptionPane.showMessageDialog(this, "Customer ID " + customerId + " has been deleted.", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadCustomers(); // Reload the customers after deletion
            } catch (Exception e) {
                // Handle any exception that occurs during the deletion
                JOptionPane.showMessageDialog(this, "Failed to delete the customer: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    
    

    private void searchCustomers() {
        String searchTerm = searchField.getText().trim().toLowerCase();
        
        if (searchTerm.isEmpty()) {
            // If search field is empty, load all customers
            loadCustomers();
            return;
        }
        
        List<Customer> allCustomers = customerDAO.getAllCustomers();
        List<Customer> filteredCustomers = new ArrayList<>();
        
        // Filter customers based on search term
        for (Customer customer : allCustomers) {
            if (String.valueOf(customer.getCustomerId()).contains(searchTerm) ||
                customer.getFirstName().toLowerCase().contains(searchTerm) ||
                customer.getLastName().toLowerCase().contains(searchTerm) ||
                customer.getEmail().toLowerCase().contains(searchTerm) ||
                customer.getPhoneNumber().contains(searchTerm)) {
                filteredCustomers.add(customer);
            }
        }
        
        // Clear existing rows
        tableModel.setRowCount(0);
        
        // Populate table with filtered results
        for (Customer customer : filteredCustomers) {
            Object[] row = { 
                customer.getCustomerId(),
                customer.getFirstName() + " " + customer.getLastName(),
                customer.getEmail(),
                customer.getPhoneNumber(),
                customer.getBirthDate()
            };
            tableModel.addRow(row);
        }
        
        if (filteredCustomers.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No customers found matching your search.", "Search Results", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void loadCustomers() {
        List<Customer> customers = customerDAO.getAllCustomers();
        
        // Clear existing rows before loading
        tableModel.setRowCount(0);
        
        for (Customer customer : customers) {
            Object[] row = { 
                customer.getCustomerId(),
                customer.getFirstName() + " " + customer.getLastName(),
                customer.getEmail(),
                customer.getPhoneNumber(),
                customer.getBirthDate()
            };
            tableModel.addRow(row);
        }
    }
}
