package UI;

import Dao.ManagerDAO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthenticationUI extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    public AuthenticationUI() {
        setTitle("Authentication");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 240, 240));

        JLabel welcomeLabel = new JLabel("Log In", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 32));
        welcomeLabel.setForeground(new Color(18, 111, 185));
        mainPanel.add(welcomeLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        usernameField = new JTextField(15);
        passwordField = new JPasswordField(15);

        addField(formPanel, gbc, "Username:", usernameField, 0);
        addField(formPanel, gbc, "Password:", passwordField, 1);

        JButton loginButton = createButton("Login");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        formPanel.add(loginButton, gbc);

        loginButton.addActionListener(new LoginAction());

        mainPanel.add(formPanel, BorderLayout.CENTER);
        add(mainPanel);
    }

    private void addField(JPanel panel, GridBagConstraints gbc, String labelText, JComponent field, int y) {
        gbc.gridx = 0;
        gbc.gridy = y;
        panel.add(new JLabel(labelText), gbc);

        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(18, 111, 185));
        button.setForeground(Color.WHITE);
        return button;
    }

    private class LoginAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(AuthenticationUI.this, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            ManagerDAO managerDAO = new ManagerDAO();
            ResultSet rs = managerDAO.searchUser(username);

            try {
                if (rs.next()) {
                    if (password.equals(rs.getString("password"))) {
                        JOptionPane.showMessageDialog(AuthenticationUI.this, "Login Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        HomePageInterface home = new HomePageInterface();
                        home.setVisible(true);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(AuthenticationUI.this, "Incorrect Password.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(AuthenticationUI.this, "Username not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AuthenticationUI::new);
    }
}
