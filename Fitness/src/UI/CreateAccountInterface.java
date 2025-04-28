package UI;

import Dao.ManagerDAO;
import Classes.Manager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateAccountInterface extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton createAccountButton;

    public CreateAccountInterface() {
        setTitle("Create Account");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        setSize(600, 400);
        setLocationRelativeTo(null);
    }

    private void initComponents() {

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 240, 240));

        JLabel welcomeLabel = new JLabel("Create Account");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 30));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setForeground(new Color(18, 111, 185));
        mainPanel.add(welcomeLabel, BorderLayout.NORTH);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBackground(new Color(240, 240, 240));

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));

        createAccountButton = new JButton("Create Account");
        createAccountButton.setFont(new Font("Arial", Font.BOLD, 16));
        createAccountButton.setBackground(new Color(18, 111, 185));
        createAccountButton.setForeground(Color.WHITE);
        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (!username.isEmpty() && !password.isEmpty()) {
                    // Create a Manager object with the entered username and password
                    Manager manager = new Manager(username, password);
                    // Create a ManagerDAO instance
                    ManagerDAO managerDAO = new ManagerDAO();
                    // Add the manager to the database
                    int rowsAffected = managerDAO.addUser(manager);
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(CreateAccountInterface.this, "Account created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(CreateAccountInterface.this, "Failed to create account.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(CreateAccountInterface.this, "Please enter username and password.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        formPanel.add(usernameLabel);
        formPanel.add(usernameField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(createAccountButton, BorderLayout.SOUTH);

        add(mainPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CreateAccountInterface().setVisible(true);
        });
    }
}
