package UI;

import Classes.Member;
import Dao.MemberDao;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AddMemberInterface extends JFrame {
    private JTextField nameField;
    private JTextField prenameField;
    private JTextField ageField;
    private JCheckBox paidCheckBox;
    private MemberManagement memberManagement;

    public AddMemberInterface(MemberManagement memberManagement) {
        this.memberManagement = memberManagement;
        setTitle("Add New Member");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(450, 300);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(new EmptyBorder(15, 15, 0, 15));

        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField(15);

        JLabel prenameLabel = new JLabel("Prename:");
        prenameField = new JTextField(15);

        JLabel ageLabel = new JLabel("Age:");
        ageField = new JTextField(15);

        JLabel paidLabel = new JLabel("Paid:");
        paidCheckBox = new JCheckBox();

        JButton addButton = new JButton("Add");
        addButton.setBackground(new Color(248, 193, 60));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.setFont(new Font("Arial", Font.BOLD, 16));
        addButton.addActionListener(this::addMemberAction);

        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(prenameLabel);
        formPanel.add(prenameField);
        formPanel.add(ageLabel);
        formPanel.add(ageField);
        formPanel.add(paidLabel);
        formPanel.add(paidCheckBox);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(addButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void addMemberAction(ActionEvent e) {
        String name = nameField.getText().trim();
        String prename = prenameField.getText().trim();
        String ageText = ageField.getText().trim();
        boolean paid = paidCheckBox.isSelected();

        if (name.isEmpty() || prename.isEmpty() || ageText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int age;
        try {
            age = Integer.parseInt(ageText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid age.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Member newMember = new Member(0, name, prename, age, paid);
        MemberDao memberDao = new MemberDao();
        int result = memberDao.addMember(newMember);

        if (result > 0) {
            JOptionPane.showMessageDialog(this, "Member added successfully!");
            memberManagement.refreshMemberData();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add member.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AddMemberInterface(new MemberManagement()).setVisible(true));
    }
}
