package UI;

import Dao.ManagerDAO;
import Classes.Manager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthenticationUI extends JFrame {

    public AuthenticationUI() {
        setTitle("Authentication");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(600, 400);
        initComponents();
        pack();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 240, 240));

        JLabel welcomeLabel = new JLabel("Log in");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 36));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setForeground(new Color(18, 111, 185));
        mainPanel.add(welcomeLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(240, 240, 240));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(new Color(18, 111, 185));
        JTextField usernameField = new JTextField(15);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(new Color(18, 111, 185));
        JPasswordField passwordField = new JPasswordField(15);

        JButton loginButton = createButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Check if the username and password are valid
                if (!username.isEmpty() && !password.isEmpty()) {
                    // Create a ManagerDAO instance
                    ManagerDAO managerDAO = new ManagerDAO();
                    // Search for the user in the database
                    ResultSet resultSet = managerDAO.searchUser(username);
                    try {
                        if (resultSet.next()) {
                            // User found, check password
                            String dbPassword = resultSet.getString("password");
                            if (password.equals(dbPassword)) {
                                // Passwords match, login successful
                                JOptionPane.showMessageDialog(AuthenticationUI.this, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                                // Open the home page UI after successful authentication
                                HomePageInterface homePageInterface = new HomePageInterface();
                                homePageInterface.setVisible(true);
                                dispose(); // Close the authentication UI
                            } else {
                                // Passwords don't match
                                JOptionPane.showMessageDialog(AuthenticationUI.this, "Invalid password!", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            // User not found
                            JOptionPane.showMessageDialog(AuthenticationUI.this, "Invalid username!", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    // Username or password is empty
                    JOptionPane.showMessageDialog(AuthenticationUI.this, "Please enter username and password.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(usernameLabel, gbc);
        gbc.gridy++;
        formPanel.add(usernameField, gbc);
        gbc.gridy++;
        formPanel.add(passwordLabel, gbc);
        gbc.gridy++;
        formPanel.add(passwordField, gbc);
        gbc.gridy++;
        formPanel.add(loginButton, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setBackground(new Color(18, 111, 185));
        button.setForeground(Color.WHITE);
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AuthenticationUI().setVisible(true);
            }
        });
    }
}
