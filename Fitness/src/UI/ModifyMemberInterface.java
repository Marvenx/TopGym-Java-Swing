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
        setInterfaceColors();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Modify the Members");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;

        mainPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        mainPanel.add(nameField = new JTextField(20), gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 1;

        mainPanel.add(new JLabel("Prename:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(prenameField = new JTextField(20), gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        mainPanel.add(new JLabel("Age:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(ageField = new JTextField(20), gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        mainPanel.add(new JLabel("Paid:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(paidCheckBox = new JCheckBox(), gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(new Color(248, 193, 60));

        saveButton = new JButton("Save");
        saveButton.setBackground(new Color(255, 255, 240));
        saveButton.setForeground(Color.BLACK);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveMemberData();
            }
        });

        cancelButton = new JButton("Cancel");
        cancelButton.setBackground(new Color(255, 255, 240));
        cancelButton.setForeground(Color.BLACK);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        mainPanel.add(buttonPanel, gbc);

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
                parentFrame.loadMemberData();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update member data.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid data format.");
            e.printStackTrace();
        }
    }

    private void setInterfaceColors() {
        getContentPane().setBackground(new Color(255, 255, 240));

        Color buttonColor = new Color(248, 193, 60);
        Color textColor = Color.WHITE;
        Font buttonFont = new Font("Arial", Font.PLAIN, 14);

        saveButton.setBackground(buttonColor);
        saveButton.setForeground(textColor);
        saveButton.setFont(buttonFont);
        saveButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        cancelButton.setBackground(buttonColor);
        cancelButton.setForeground(textColor);
        cancelButton.setFont(buttonFont);
        cancelButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MemberManagement parentFrame = new MemberManagement();
                int memberId = 123;
                new ModifyMemberInterface(parentFrame, memberId).setVisible(true);
            }
        });
    }
}
