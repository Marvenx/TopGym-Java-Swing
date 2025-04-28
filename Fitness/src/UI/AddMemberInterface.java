package UI;

import Classes.Member;
import Dao.MemberDao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddMemberInterface extends JFrame {
    private JTextField nameField;
    private JTextField prenameField;
    private JTextField ageField;
    private JTextField idField;
    private JCheckBox paidCheckBox;
    private MemberManagement memberManagement; // Reference to the HomePageUI instance

    public AddMemberInterface(MemberManagement memberManagement) {
        this.memberManagement = memberManagement; // Store the reference to the HomePageUI instance
        setTitle("Add New Member");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        setSize(500, 300);
        pack(); // Adjust the frame size based on its content
        setLocationRelativeTo(null); // Center the frame on the screen
    }



    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(237, 144, 70)); // Set background color of the main panel

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5)); // 5 rows, 2 columns
        formPanel.setBackground(new Color(255, 255, 240)); // Light cream color background

        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField(15);

        JLabel prenameLabel = new JLabel("Prename:");
        prenameField = new JTextField(15);

        JLabel ageLabel = new JLabel("Age:");
        ageField = new JTextField(15);

        JLabel idLabel = new JLabel("ID:");
        idField = new JTextField(15);

        JLabel paidLabel = new JLabel("Paid:");
        paidCheckBox = new JCheckBox();

        JButton addButton = new JButton("Add");
        addButton.setBackground(new Color(237, 144, 70)); // Set background color of the Add button
addButton.setForeground(new Color(255,255,255));
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle add button click
                addMember();
            }
        });

        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(prenameLabel);
        formPanel.add(prenameField);
        formPanel.add(ageLabel);
        formPanel.add(ageField);
        formPanel.add(paidLabel);
        formPanel.add(paidCheckBox);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(addButton, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void addMember() {
        // Retrieve data from fields
        String name = nameField.getText();
        String prename = prenameField.getText();
        int age = Integer.parseInt(ageField.getText());
        boolean paid = paidCheckBox.isSelected();

        // Create a new Member object
        Member newMember = new Member(0, name, prename, age, paid);

        // Add the new member to the database using the MemberDao
        MemberDao memberDao = new MemberDao();
        int rowsAffected = memberDao.addMember(newMember);

        // Display a message based on the result
        if (rowsAffected > 0) {
            JOptionPane.showMessageDialog(this, "Member added successfully!");

            // Notify the HomePageUI instance to add the new member to the list
            memberManagement.addNewMemberToList(name);

            // Close the AddMemberInterface frame
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add member.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Instantiate the HomePageUI and pass it to the AddMemberInterface constructor
                new AddMemberInterface(new MemberManagement()).setVisible(true);
            }
        });
    }
}
