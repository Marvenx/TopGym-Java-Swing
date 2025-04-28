package UI;

import javax.swing.*;
import java.awt.*;

public class PasswordFrame extends JFrame {

    private JPasswordField passwordField;
    private JFrame previousFrame;

    public PasswordFrame(JFrame previousFrame) {
        this.previousFrame = previousFrame;
        setTitle("Manager Login");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(18, 111, 185));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        JLabel label = new JLabel("Enter Manager Password:");
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setForeground(Color.WHITE);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        passwordField = new JPasswordField(15);
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));

        JButton submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Arial", Font.BOLD, 18));
        submitButton.setBackground(Color.WHITE);
        submitButton.setForeground(new Color(18, 111, 185));
        submitButton.setFocusPainted(false);
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        submitButton.addActionListener(e -> checkPassword());

        mainPanel.add(label);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(passwordField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(submitButton);

        setContentPane(mainPanel);
    }

    private void checkPassword() {
        String password = new String(passwordField.getPassword());
        if (password.equals("0000")) {
            openManagerPortal();
        } else {
            JOptionPane.showMessageDialog(this, "Incorrect Password. Try again.", "Access Denied", JOptionPane.ERROR_MESSAGE);
            passwordField.setText("");
        }
    }

    private void openManagerPortal() {
        new FitnessClubCenter().setVisible(true);
        this.dispose();
    }
}
