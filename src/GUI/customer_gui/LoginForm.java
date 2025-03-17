package GUI.customer_gui;
import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import user.Customer;

public class LoginForm extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private Color brandGreen = new Color(0, 175, 0);
    private Image backgroundImage;

    public LoginForm() {
        setTitle("Bank App - Login");
        setSize(360, 812);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Load background image
        try {
            backgroundImage = ImageIO.read(new File("assets/bank.jpg"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Set custom panel with background image
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        backgroundPanel.setLayout(null);
        setContentPane(backgroundPanel);

        // Create UI components
        JLabel welcomeLabel = new JLabel("Welcome to the bank", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Montserrat", Font.BOLD, 25));
        welcomeLabel.setForeground(Color.RED);
        welcomeLabel.setBounds(30, 50, 300, 30);
        add(welcomeLabel);

        JLabel emailLabel = new JLabel("Email or Phone:");
        emailLabel.setBounds(30, 150, 300, 20);
        add(emailLabel);

        emailField = new JTextField(20);
        emailField.setBounds(30, 180, 300, 40);
        add(emailField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(30, 230, 300, 20);
        add(passwordLabel);

        passwordField = new JPasswordField(20);
        passwordField.setBounds(30, 260, 300, 40);
        add(passwordField);

        loginButton = new JButton("Login");
        loginButton.setBounds(30, 330, 300, 50);
        loginButton.setBackground(brandGreen);
        loginButton.setForeground(Color.WHITE);
        add(loginButton);

        registerButton = new JButton("Register");
        registerButton.setBounds(30, 390, 300, 50);
        registerButton.setBackground(Color.WHITE);
        registerButton.setForeground(brandGreen);
        add(registerButton);

        // Login action
        loginButton.addActionListener(e -> {
            String emailOrPhone = emailField.getText();
            String password = new String(passwordField.getPassword());

            // Simulate the login method from Customer class
            Customer customer = Customer.login(emailOrPhone, password);
            if (customer != null) {
                onLoginSuccessful(customer);  // Call the onLoginSuccessful method
            } else {
                showStyledDialog("Login failed. Please check your credentials.", false);
            }
        });

        // Register action
        registerButton.addActionListener(e -> {
            RegisterForm registerForm = new RegisterForm();
            registerForm.setVisible(true);
            dispose();
        });
    }

    // Define the method that is called after login is successful
    private void onLoginSuccessful(Customer customer) {
        showStyledDialog("Login successful! Welcome, " + customer.getFullName(), true);
        MainMenuForm mainMenuForm = new MainMenuForm(customer);
        mainMenuForm.setVisible(true);
        dispose();
    }

    private void showStyledDialog(String message, boolean isSuccess) {
        JDialog dialog = new JDialog(this, isSuccess ? "Success" : "Error", true);
        dialog.setSize(320, 180);
        dialog.setLayout(new BorderLayout());
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel messageLabel = new JLabel(message, JLabel.CENTER);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(messageLabel, BorderLayout.CENTER);

        JButton okButton = new JButton("OK");
        okButton.setPreferredSize(new Dimension(100, 40));
        okButton.setBackground(isSuccess ? brandGreen : Color.RED);
        okButton.setForeground(Color.WHITE);
        okButton.addActionListener(e -> dialog.dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        dialog.add(panel);
        ((JPanel) dialog.getContentPane()).setBorder(new LineBorder(isSuccess ? brandGreen : Color.RED, 2));

        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginForm().setVisible(true));
    }
}
