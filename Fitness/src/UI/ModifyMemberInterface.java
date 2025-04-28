package UI;

import Classes.Member;
import Dao.MemberDao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ModifyMemberInterface extends JFrame {
    private final int memberId;
    private JTextField nameField;
    private JTextField prenameField;
    private JTextField ageField;
    private JCheckBox paidCheckBox;
    private JButton saveButton;
    private JButton cancelButton;

    private MemberManagement parentFrame;

    public ModifyMemberInterface(MemberManagement parentFrame, int memberId) {
        this.parentFrame = parentFrame;
        this.memberId = memberId;

        setTitle("Modify Member");
        setSize(500, 300);
        setLocationRelativeTo(parentFrame);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initComponents();
        loadMemberData();
        setInterfaceColors(); // Set interface colors
    }

    private void initComponents() {
        // Main panel with GridBagLayout
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding around components

        // Title label
        JLabel titleLabel = new JLabel("Modify the Members");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18)); // Set title font
        gbc.gridwidth = 2; // Span two columns
        mainPanel.add(titleLabel, gbc);

        // Reset grid width and increment grid y position
        gbc.gridwidth = 1;
        gbc.gridy++;

        // Name label and field
        mainPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2; // Span two columns
        mainPanel.add(nameField = new JTextField(20), gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 1; // Reset grid width

        // Prename label and field
        mainPanel.add(new JLabel("Prename:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(prenameField = new JTextField(20), gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        // Age label and field
        mainPanel.add(new JLabel("Age:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(ageField = new JTextField(20), gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        // Paid label and checkbox
        mainPanel.add(new JLabel("Paid:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(paidCheckBox = new JCheckBox(), gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        // Button panel with FlowLayout
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0)); // Added horizontal gap of 10 pixels
        buttonPanel.setBackground(new Color(248, 193, 60)); // Set background color of the button panel

        // Save button
        saveButton = new JButton("Save");
        saveButton.setBackground(new Color(255, 255, 240)); // Set background color of the "Save" button
        saveButton.setForeground(Color.BLACK); // Set text color of the "Save" button
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveMemberData();
            }
        });

        // Cancel button
        cancelButton = new JButton("Cancel");
        cancelButton.setBackground(new Color(255, 255, 240)); // Set background color of the "Cancel" button
        cancelButton.setForeground(Color.BLACK); // Set text color of the "Cancel" button
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        // Add buttons to the button panel with space between them
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        // Add button panel to the main panel
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2; // Span two columns
        mainPanel.add(buttonPanel, gbc);

        // Add main panel to the frame
        add(mainPanel);
    }



    private void loadMemberData() {
        try {
            MemberDao memberDao = new MemberDao();
            ResultSet resultSet = memberDao.searchMember(memberId);
            if (resultSet.next()) {
                nameField.setText(resultSet.getString("name"));
                prenameField.setText(resultSet.getString("prename"));
                ageField.setText(String.valueOf(resultSet.getInt("age")));
                paidCheckBox.setSelected(resultSet.getBoolean("paid"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveMemberData() {
        try {
            String name = nameField.getText();
            String prename = prenameField.getText();
            int age = Integer.parseInt(ageField.getText());
            boolean paid = paidCheckBox.isSelected();

            MemberDao memberDao = new MemberDao();
            int rowsAffected = memberDao.updateMember(new Member(memberId, name, prename, age, paid));
            if (rowsAffected > 0) {
                parentFrame.loadMemberData(); // Refresh member list in HomePageUI
                dispose(); // Close the modify interface
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update member data.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid data format.");
            e.printStackTrace();
        }
    }

    private void setInterfaceColors() {
        // Set background color of the main panel
        getContentPane().setBackground(new Color(255, 255, 240));

        // Set button colors and borders
        Color buttonColor = new Color(248, 193, 60); // Button background color
        Color textColor = Color.WHITE; // Button text color
        Font buttonFont = new Font("Arial", Font.PLAIN, 14); // Button font

        // Save button
        saveButton.setBackground(buttonColor);
        saveButton.setForeground(textColor);
        saveButton.setFont(buttonFont);
        saveButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15)); // Add padding to the button

        // Cancel button
        cancelButton.setBackground(buttonColor);
        cancelButton.setForeground(textColor);
        cancelButton.setFont(buttonFont);
        cancelButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15)); // Add padding to the button
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // You need to provide the parentFrame and memberId values here
                MemberManagement parentFrame = new MemberManagement();
                int memberId = 123; // Replace with the actual member ID
                new ModifyMemberInterface(parentFrame, memberId).setVisible(true);
            }
        });
    }
}
