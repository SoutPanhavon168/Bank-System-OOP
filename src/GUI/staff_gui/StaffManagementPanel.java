package GUI.staff_gui;

import database.StaffDAO;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import user.Admin;
import user.CustomerException;
import user.staff.Staff;
import user.staff.Staff.StaffRole;

public class StaffManagementPanel extends JPanel {
    private Staff currentStaff;
    private Admin admin;
    private JTable staffTable;
    private DefaultTableModel tableModel;
    private Color brandBlue = new Color(0, 102, 204);
    private JTextField searchField;
    private JPanel controlPanel, formPanel;
    private CardLayout cardLayout;
    
    public StaffManagementPanel(Staff currentStaff) {
        this.currentStaff = currentStaff;
        this.admin = new Admin(); // Initialize Admin for operations
        
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        createHeaderPanel();
        createControlPanel();
        createTablePanel();
        createFormPanel();
        
        // Load staff data when panel is created
        loadStaffData();
    }
    
    private void createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(new EmptyBorder(20, 20, 10, 20));
        
        JLabel titleLabel = new JLabel("Staff Management");
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
            // Implement search functionality
            String searchTerm = searchField.getText().trim().toLowerCase();
            if (searchTerm.isEmpty()) {
                loadStaffData(); // Reset to show all
            } else {
                filterStaffData(searchTerm);
            }
        });
        
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        
        headerPanel.add(searchPanel, BorderLayout.EAST);
        
        add(headerPanel, BorderLayout.NORTH);
    }
    
    private void createControlPanel() {
        controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        controlPanel.setBackground(Color.WHITE);
        controlPanel.setBorder(new EmptyBorder(0, 20, 10, 20));
        
        JButton addButton = new JButton("Add Staff");
        JButton editButton = new JButton("Edit Staff");
        JButton removeButton = new JButton("Remove Staff");
        JButton refreshButton = new JButton("Refresh");
        
        // Style buttons
        addButton.setBackground(brandBlue);
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        
        editButton.setBackground(new Color(240, 240, 240));
        removeButton.setBackground(new Color(240, 240, 240));
        refreshButton.setBackground(new Color(240, 240, 240));
        
        // Add action listeners
        addButton.addActionListener(e -> showAddStaffForm());
        
        editButton.addActionListener(e -> {
            int selectedRow = staffTable.getSelectedRow();
            if (selectedRow >= 0) {
                int staffId = Integer.parseInt(staffTable.getValueAt(selectedRow, 0).toString());
                showEditStaffForm(staffId);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Please select a staff member to edit.", 
                    "Selection Required", 
                    JOptionPane.WARNING_MESSAGE);
            }
        });
        
        removeButton.addActionListener(e -> {
            int selectedRow = staffTable.getSelectedRow();
            if (selectedRow >= 0) {
                int staffId = Integer.parseInt(staffTable.getValueAt(selectedRow, 0).toString());
                removeStaff(staffId);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Please select a staff member to remove.", 
                    "Selection Required", 
                    JOptionPane.WARNING_MESSAGE);
            }
        });
        
        refreshButton.addActionListener(e -> loadStaffData());
        
        controlPanel.add(addButton);
        controlPanel.add(editButton);
        controlPanel.add(removeButton);
        controlPanel.add(refreshButton);
        
        add(controlPanel, BorderLayout.NORTH);
    }
    
    private void createTablePanel() {
        // Create table model with columns
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };
        
        tableModel.addColumn("Staff ID");
        tableModel.addColumn("Full Name");
        tableModel.addColumn("Email");
        tableModel.addColumn("Phone");
        tableModel.addColumn("Role");
        
        // Create table
        staffTable = new JTable(tableModel);
        staffTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        staffTable.setRowHeight(25);
        staffTable.getTableHeader().setReorderingAllowed(false);
        staffTable.getTableHeader().setBackground(brandBlue);
        staffTable.getTableHeader().setForeground(Color.WHITE);
        staffTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        
        // Add to scroll pane
        JScrollPane scrollPane = new JScrollPane(staffTable);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void createFormPanel() {
        // Create a panel with CardLayout to switch between different forms
        formPanel = new JPanel();
        cardLayout = new CardLayout();
        formPanel.setLayout(cardLayout);
        formPanel.setVisible(false); // Initially hidden
        
        add(formPanel, BorderLayout.SOUTH);
    }
    
    private void loadStaffData() {
        // Clear existing data
        tableModel.setRowCount(0);
        
        // Get all staff from database using StaffDAO
        StaffDAO staffDAO = new StaffDAO();
        java.util.List<Staff> staffList = staffDAO.getAllStaff();
        
        // Populate table
        for (Staff staff : staffList) {
            tableModel.addRow(new Object[]{
                staff.getStaffId(),
                staff.getLastName() + ", " + staff.getFirstName(),
                staff.getEmail(),
                staff.getPhoneNumber(),
                staff.getRole()
            });
        }
    }
    
    private void filterStaffData(String searchTerm) {
        // Clear existing data
        tableModel.setRowCount(0);
        
        // Get all staff from database using StaffDAO
        StaffDAO staffDAO = new StaffDAO();
        java.util.List<Staff> staffList = staffDAO.getAllStaff();
        
        // Filter and populate table
        for (Staff staff : staffList) {
            String fullName = staff.getLastName() + ", " + staff.getFirstName();
            if (fullName.toLowerCase().contains(searchTerm) || 
                staff.getEmail().toLowerCase().contains(searchTerm) ||
                staff.getRole().toString().toLowerCase().contains(searchTerm)) {
                
                tableModel.addRow(new Object[]{
                    staff.getStaffId(),
                    fullName,
                    staff.getEmail(),
                    staff.getPhoneNumber(),
                    staff.getRole()
                });
            }
        }
    }
    
    private void showAddStaffForm() {
        // Create form panel if it doesn't exist
        JPanel addStaffForm = new JPanel();
        addStaffForm.setLayout(new BorderLayout());
        addStaffForm.setBackground(Color.WHITE);
        addStaffForm.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        JLabel formTitle = new JLabel("Add New Staff");
        formTitle.setFont(new Font("Arial", Font.BOLD, 16));
        formTitle.setForeground(brandBlue);
        
        // Create form fields
        JPanel fieldsPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        fieldsPanel.setBackground(Color.WHITE);
        
        JLabel lastNameLabel = new JLabel("Last Name:");
        JTextField lastNameField = new JTextField(20);
        
        JLabel firstNameLabel = new JLabel("First Name:");
        JTextField firstNameField = new JTextField(20);
        
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(20);
        
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);
        
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        JPasswordField confirmPasswordField = new JPasswordField(20);
        
        JLabel phoneLabel = new JLabel("Phone Number:");
        JTextField phoneField = new JTextField(20);
        
        JLabel birthDateLabel = new JLabel("Birth Date (YYYY-MM-DD):");
        JTextField birthDateField = new JTextField(20);
        
        JLabel govIdLabel = new JLabel("Government ID:");
        JTextField govIdField = new JTextField(20);
        
        JLabel roleLabel = new JLabel("Role:");
        JComboBox<StaffRole> roleCombo = new JComboBox<>(StaffRole.values());
        
        // Add fields to panel
        fieldsPanel.add(lastNameLabel);
        fieldsPanel.add(lastNameField);
        fieldsPanel.add(firstNameLabel);
        fieldsPanel.add(firstNameField);
        fieldsPanel.add(emailLabel);
        fieldsPanel.add(emailField);
        fieldsPanel.add(passwordLabel);
        fieldsPanel.add(passwordField);
        fieldsPanel.add(confirmPasswordLabel);
        fieldsPanel.add(confirmPasswordField);
        fieldsPanel.add(phoneLabel);
        fieldsPanel.add(phoneField);
        fieldsPanel.add(birthDateLabel);
        fieldsPanel.add(birthDateField);
        fieldsPanel.add(govIdLabel);
        fieldsPanel.add(govIdField);
        fieldsPanel.add(roleLabel);
        fieldsPanel.add(roleCombo);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton cancelButton = new JButton("Cancel");
        JButton saveButton = new JButton("Save");
        
        cancelButton.addActionListener(e -> {
            formPanel.setVisible(false);
            revalidate();
            repaint();
        });
        
        saveButton.addActionListener(e -> {
            try {
                // Validate input
                String lastName = lastNameField.getText().trim();
                String firstName = firstNameField.getText().trim();
                String email = emailField.getText().trim();
                String password = new String(passwordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());
                String phoneNumber = phoneField.getText().trim();
                String birthDateStr = birthDateField.getText().trim();
                String governmentId = govIdField.getText().trim();
                StaffRole role = (StaffRole) roleCombo.getSelectedItem();
                
                // Validate required fields
                if (lastName.isEmpty() || firstName.isEmpty() || email.isEmpty() || 
                    password.isEmpty() || confirmPassword.isEmpty() || phoneNumber.isEmpty() || 
                    birthDateStr.isEmpty() || governmentId.isEmpty()) {
                    throw new CustomerException.EmptyFieldException("All fields are required");
                }
                
                // Validate passwords match
                if (!password.equals(confirmPassword)) {
                    throw new CustomerException.PasswordMismatchException();
                }
                
                // Parse birth date
                LocalDate birthDate;
                try {
                    birthDate = LocalDate.parse(birthDateStr);
                    if (birthDate.isAfter(LocalDate.now().minusYears(18))) {
                        throw new CustomerException.UnderageException();
                    }
                } catch (DateTimeParseException ex) {
                    throw new CustomerException.InvalidBirthDateException();
                }
                
                // Create Staff object
                Staff newStaff = new Staff(
                    lastName, firstName, email, password, confirmPassword,
                    phoneNumber, birthDate, governmentId, role
                );
                
                // Save to database
                StaffDAO staffDAO = new StaffDAO();
                staffDAO.saveStaff(newStaff);
                
                JOptionPane.showMessageDialog(this, 
                    "Staff account created successfully!", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                // Refresh table and hide form
                loadStaffData();
                formPanel.setVisible(false);
                
            } catch (CustomerException ex) {
                JOptionPane.showMessageDialog(this, 
                    "Error: " + ex.getMessage(), 
                    "Input Error", 
                    JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                    "An unexpected error occurred: " + ex.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });
        
        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);
        
        // Assemble the form
        addStaffForm.add(formTitle, BorderLayout.NORTH);
        addStaffForm.add(fieldsPanel, BorderLayout.CENTER);
        addStaffForm.add(buttonPanel, BorderLayout.SOUTH);
        
        // Add to card layout
        formPanel.add(addStaffForm, "addStaff");
        cardLayout.show(formPanel, "addStaff");
        formPanel.setVisible(true);
        
        // Refresh layout
        revalidate();
        repaint();
    }
    
    private void showEditStaffForm(int staffId) {
        // First, fetch the staff details
        StaffDAO staffDAO = new StaffDAO();
        Staff staff = staffDAO.getStaffById(staffId);
        
        if (staff == null) {
            JOptionPane.showMessageDialog(this, 
                "Staff member not found.", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Create form panel
        JPanel editStaffForm = new JPanel();
        editStaffForm.setLayout(new BorderLayout());
        editStaffForm.setBackground(Color.WHITE);
        editStaffForm.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        JLabel formTitle = new JLabel("Edit Staff Account");
        formTitle.setFont(new Font("Arial", Font.BOLD, 16));
        formTitle.setForeground(brandBlue);
        
        // Create form with prefilled values
        JPanel fieldsPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        fieldsPanel.setBackground(Color.WHITE);
        
        JLabel idLabel = new JLabel("Staff ID:");
        JTextField idField = new JTextField(String.valueOf(staff.getStaffId()));
        idField.setEditable(false);
        
        JLabel lastNameLabel = new JLabel("Last Name:");
        JTextField lastNameField = new JTextField(staff.getLastName(), 20);
        
        JLabel firstNameLabel = new JLabel("First Name:");
        JTextField firstNameField = new JTextField(staff.getFirstName(), 20);
        
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(staff.getEmail(), 20);
        
        JLabel passwordLabel = new JLabel("New Password (leave blank to keep current):");
        JPasswordField passwordField = new JPasswordField(20);
        
        JLabel confirmPasswordLabel = new JLabel("Confirm New Password:");
        JPasswordField confirmPasswordField = new JPasswordField(20);
        
        JLabel phoneLabel = new JLabel("Phone Number:");
        JTextField phoneField = new JTextField(staff.getPhoneNumber(), 20);
        
        JLabel roleLabel = new JLabel("Role:");
        JComboBox<StaffRole> roleCombo = new JComboBox<>(StaffRole.values());
        roleCombo.setSelectedItem(staff.getRole());
        
        // Add fields to panel
        fieldsPanel.add(idLabel);
        fieldsPanel.add(idField);
        fieldsPanel.add(lastNameLabel);
        fieldsPanel.add(lastNameField);
        fieldsPanel.add(firstNameLabel);
        fieldsPanel.add(firstNameField);
        fieldsPanel.add(emailLabel);
        fieldsPanel.add(emailField);
        fieldsPanel.add(passwordLabel);
        fieldsPanel.add(passwordField);
        fieldsPanel.add(confirmPasswordLabel);
        fieldsPanel.add(confirmPasswordField);
        fieldsPanel.add(phoneLabel);
        fieldsPanel.add(phoneField);
        fieldsPanel.add(roleLabel);
        fieldsPanel.add(roleCombo);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton cancelButton = new JButton("Cancel");
        JButton saveButton = new JButton("Save Changes");
        
        cancelButton.addActionListener(e -> {
            formPanel.setVisible(false);
            revalidate();
            repaint();
        });
        
        saveButton.addActionListener(e -> {
            try {
                // Get updated values
                String lastName = lastNameField.getText().trim();
                String firstName = firstNameField.getText().trim();
                String email = emailField.getText().trim();
                String password = new String(passwordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());
                String phoneNumber = phoneField.getText().trim();
                StaffRole role = (StaffRole) roleCombo.getSelectedItem();
                
                // Validate required fields
                if (lastName.isEmpty() || firstName.isEmpty() || email.isEmpty() || phoneNumber.isEmpty()) {
                    throw new CustomerException.EmptyFieldException("Required fields cannot be empty");
                }
                
                // Check if password is being changed
                if (!password.isEmpty()) {
                    if (!password.equals(confirmPassword)) {
                        throw new CustomerException.PasswordMismatchException();
                    }
                    // Update password in database
                    staffDAO.updateStaffPassword(staffId, password);
                }
                
                // Update other staff details
                staff.setLastName(lastName);
                staff.setFirstName(firstName);
                staff.setEmail(email);
                staff.setPhoneNumber(phoneNumber);
                staff.setRole(role);
                
                // Save to database
                staffDAO.updateStaff(staff);
                
                JOptionPane.showMessageDialog(this, 
                    "Staff account updated successfully!", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                // Refresh table and hide form
                loadStaffData();
                formPanel.setVisible(false);
                
            } catch (CustomerException ex) {
                JOptionPane.showMessageDialog(this, 
                    "Error: " + ex.getMessage(), 
                    "Input Error", 
                    JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                    "An unexpected error occurred: " + ex.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });
        
        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);
        
        // Assemble the form
        editStaffForm.add(formTitle, BorderLayout.NORTH);
        editStaffForm.add(fieldsPanel, BorderLayout.CENTER);
        editStaffForm.add(buttonPanel, BorderLayout.SOUTH);
        
        // Add to card layout
        formPanel.add(editStaffForm, "editStaff");
        cardLayout.show(formPanel, "editStaff");
        formPanel.setVisible(true);
        
        // Refresh layout
        revalidate();
        repaint();
    }
    
    private void removeStaff(int staffId) {
        int choice = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to remove staff member with ID: " + staffId + "?",
                "Confirm Remove",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
        if (choice == JOptionPane.YES_OPTION) {
            try {
                // Pass the staffId to the removeStaffAccount method
                admin.removeStaffAccount(staffId);
                // Or if you prefer to use StaffDAO directly:
                // StaffDAO staffDAO = new StaffDAO();
                // staffDAO.deleteStaff(staffId);
                
                JOptionPane.showMessageDialog(this,
                        "Staff account removed successfully.",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                // Refresh table
                loadStaffData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Error removing staff: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}