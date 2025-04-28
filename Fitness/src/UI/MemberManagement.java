package UI;

import Dao.MemberDao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class MemberManagement extends JFrame {
    private JLabel totalMembersLabel;
    private JList<String> memberList;
    private DefaultListModel<String> memberListModel;
    private JButton addButton;
    private JButton modifyButton;
    private JButton deleteButton;
    private MemberDao memberDao;

    public MemberManagement() {
        setTitle("Member Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(500, 300); // Set the size of the JFrame
        initComponents();
        loadMemberData(); // Load member data when the UI is initialized
        pack(); // Adjust the frame size based on its content
    }


    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        getContentPane().setBackground(new Color(237, 144, 70));

        // Panel for displaying total number of members
        JPanel totalMembersPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        totalMembersLabel = new JLabel("Total Members: ");

        totalMembersPanel.add(totalMembersLabel);
        mainPanel.add(totalMembersPanel, BorderLayout.NORTH);
        totalMembersLabel.setForeground(Color.white);
        totalMembersPanel.setBackground(new Color(237, 144, 70));

        // Panel for displaying member list
        JPanel memberPanel = new JPanel(new BorderLayout());
        memberListModel = new DefaultListModel<>();
        memberList = new JList<>(memberListModel);
        JScrollPane memberScrollPane = new JScrollPane(memberList);
        memberPanel.add(new JLabel("Members List"), BorderLayout.NORTH);
        memberPanel.add(memberScrollPane, BorderLayout.CENTER);
        totalMembersLabel.setForeground(Color.WHITE); // Set text color to white
        memberPanel.setBackground(new Color(237, 144, 70)); // Set background color

        JLabel memberListLabel = new JLabel("Members List", SwingConstants.CENTER);
        memberListLabel.setForeground(Color.WHITE); // Set text color to white

        // Button panel for member operations
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        addButton = new JButton("Add Member");
        modifyButton = new JButton("Modify");
        deleteButton = new JButton("Delete Member");

        // Set button colors
        Color buttonColor = new Color(237, 144, 70);
        Color textColor = Color.WHITE; // Set text color to white
        addButton.setBackground(buttonColor);
        addButton.setForeground(textColor); // Set text color to white
        modifyButton.setForeground(textColor);
        modifyButton.setBackground(buttonColor);
        deleteButton.setBackground(buttonColor);
        deleteButton.setForeground(textColor); // Set text color to white
        modifyButton.setForeground(textColor); // Set text color to white
        totalMembersLabel.setBackground(buttonColor);
        // Initially disable buttons
        addButton.setEnabled(true); // Enable the add button
        modifyButton.setEnabled(false);
        deleteButton.setEnabled(false);

        // Add action listeners to the buttons
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show the AddMemberInterface when the add button is clicked
                AddMemberInterface addMemberInterface = new AddMemberInterface(MemberManagement.this);
                addMemberInterface.setVisible(true);
            }
        });

        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = memberList.getSelectedIndex();
                if (selectedIndex != -1) {
                    String selectedMemberId = memberList.getSelectedValue().split(":")[0].trim();
                    int memberId = Integer.parseInt(selectedMemberId);

                    // Show the ModifyMemberInterface when the modify button is clicked
                    ModifyMemberInterface modifyMemberInterface = new ModifyMemberInterface(MemberManagement.this, memberId);
                    modifyMemberInterface.setVisible(true);
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = memberList.getSelectedIndex();
                if (selectedIndex != -1) {
                    String selectedMemberId = memberList.getSelectedValue().split(":")[0].trim();
                    int memberId = Integer.parseInt(selectedMemberId);

                    // Show confirmation dialog
                    int option = JOptionPane.showConfirmDialog(MemberManagement.this, "Are you sure you want to delete this member?", "Confirmation", JOptionPane.YES_NO_OPTION);

                    if (option == JOptionPane.YES_OPTION) {
                        // Call deleteMember method from MemberDao
                        int rowsAffected = deleteMember(memberId);
                        if (rowsAffected > 0) {
                            // Reload member data after deletion
                            loadMemberData();
                            JOptionPane.showMessageDialog(MemberManagement.this, "Member deleted successfully.");
                        } else {
                            JOptionPane.showMessageDialog(MemberManagement.this, "Failed to delete member.");
                        }
                    }
                }
            }
        });

        // Add buttons to button panel
        buttonPanel.add(addButton);
        buttonPanel.add(modifyButton);
        buttonPanel.add(deleteButton);

        memberPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(memberPanel, BorderLayout.CENTER);

        // Add ListSelectionListener to member list
        memberList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                if (memberList.getSelectedIndex() != -1) {
                    // Enable buttons if a member is selected
                    addButton.setEnabled(true);
                    modifyButton.setEnabled(true);
                    deleteButton.setEnabled(true);
                } else {
                    // Disable buttons if no member is selected
                    addButton.setEnabled(false);
                    modifyButton.setEnabled(false);
                    deleteButton.setEnabled(false);
                }
            }
        });

        add(mainPanel);
    }

    // Method to load member data from the database
    public void loadMemberData() {
        try {
            if (memberDao == null) {
                // Initialize the MemberDao instance if not already initialized
                memberDao = new MemberDao();
            }

            // Clear the member list model
            memberListModel.clear();

            // Retrieve all members from the database
            ResultSet resultSet = memberDao.getAllMembers();
            int totalMembers = 0;

            while (resultSet.next()) {
                String memberId = resultSet.getString("id");
                String memberName = resultSet.getString("name");
                memberListModel.addElement(memberId + " : " + memberName);
                totalMembers++;
            }

            // Update the total members label
            totalMembersLabel.setText("Total Members: " + totalMembers);

            // Close the ResultSet
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to delete a member
    private int deleteMember(int memberId) {
        int rowsAffected = 0;
        if (memberDao == null) {
            // Initialize the MemberDao instance if not already initialized
            memberDao = new MemberDao();
        }

        // Delete the member from the database
        rowsAffected = memberDao.deleteMember(memberId);
        return rowsAffected;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MemberManagement().setVisible(true);
            }
        });
    }

    public void addNewMemberToList(String name) {
        // Add the new member to the member list model
        memberListModel.addElement(name);


        // Update the total members label
        int totalMembers = memberListModel.size();
        totalMembersLabel.setText("Total Members: " + totalMembers);
    }
}
