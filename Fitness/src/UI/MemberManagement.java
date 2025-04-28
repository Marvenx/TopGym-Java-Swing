package UI;

import Dao.MemberDao;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.*;

public class MemberManagement extends JFrame {
    private JLabel totalMembersLabel;
    private JTable memberTable;
    private DefaultTableModel tableModel;
    private JButton addButton;
    private JButton modifyButton;
    private JButton deleteButton;
    private MemberDao memberDao;

    public MemberManagement() {
        setTitle("Member Management");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 350);
        setLocationRelativeTo(null);
        initComponents();
        loadMemberData();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        getContentPane().setBackground(new Color(248, 193, 60));

        JPanel totalMembersPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        totalMembersLabel = new JLabel("Total Members: 0");
        totalMembersLabel.setForeground(Color.WHITE);
        totalMembersPanel.setBackground(new Color(248, 193, 60));
        totalMembersPanel.add(totalMembersLabel);
        mainPanel.add(totalMembersPanel, BorderLayout.NORTH);

        String[] columnNames = {"ID", "Name", "Prename", "Age", "Paid"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        memberTable = new JTable(tableModel);
        memberTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        memberTable.getTableHeader().setReorderingAllowed(false);

        memberTable.getColumnModel().getColumn(0).setCellRenderer(new CenterRenderer());
        memberTable.getColumnModel().getColumn(3).setCellRenderer(new CenterRenderer());

        memberTable.getColumnModel().getColumn(4).setCellRenderer(new BooleanRenderer());

        JScrollPane tableScrollPane = new JScrollPane(memberTable);
        tableScrollPane.setPreferredSize(new Dimension(750, 350));

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(new Color(248, 193, 60));
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);
        mainPanel.add(tablePanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        addButton = createButton("Add Member", e -> {
            AddMemberInterface addMemberInterface = new AddMemberInterface(MemberManagement.this);
            addMemberInterface.setVisible(true);
        });

        modifyButton = createButton("Modify", e -> {
            int selectedRow = memberTable.getSelectedRow();
            if (selectedRow != -1) {
                int memberId = (int) tableModel.getValueAt(selectedRow, 0);
                ModifyMemberInterface modifyMemberInterface = new ModifyMemberInterface(MemberManagement.this, memberId);
                modifyMemberInterface.setVisible(true);
            }
        });

        deleteButton = createButton("Delete", e -> {
            int selectedRow = memberTable.getSelectedRow();
            if (selectedRow != -1) {
                int memberId = (int) tableModel.getValueAt(selectedRow, 0);
                String memberName = (String) tableModel.getValueAt(selectedRow, 1);

                int option = JOptionPane.showConfirmDialog(
                        MemberManagement.this,
                        "Delete member: " + memberName + " (ID: " + memberId + ")?",
                        "Confirm Deletion",
                        JOptionPane.YES_NO_OPTION
                );

                if (option == JOptionPane.YES_OPTION) {
                    int rowsAffected = memberDao.deleteMember(memberId);
                    if (rowsAffected > 0) {
                        loadMemberData();
                        JOptionPane.showMessageDialog(MemberManagement.this, "Member deleted successfully.");
                    } else {
                        JOptionPane.showMessageDialog(MemberManagement.this, "Failed to delete member.");
                    }
                }
            }
        });

        buttonPanel.add(addButton);
        buttonPanel.add(modifyButton);
        buttonPanel.add(deleteButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        memberTable.getSelectionModel().addListSelectionListener(e -> {
            boolean rowSelected = memberTable.getSelectedRow() != -1;
            modifyButton.setEnabled(rowSelected);
            deleteButton.setEnabled(rowSelected);
        });

        add(mainPanel);
    }

    private JButton createButton(String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.setBackground(new Color(248, 193, 60));
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(120, 30));
        button.addActionListener(listener);
        return button;
    }

    public void loadMemberData() {
        try {
            if (memberDao == null) {
                memberDao = new MemberDao();
            }
            tableModel.setRowCount(0);

            ResultSet resultSet = memberDao.getAllMembers();
            int totalMembers = 0;

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String prename = resultSet.getString("prename");
                int age = resultSet.getInt("age");
                boolean paid = resultSet.getBoolean("paid");

                tableModel.addRow(new Object[]{id, name, prename, age, paid});
                totalMembers++;
            }

            totalMembersLabel.setText("Total Members: " + totalMembers);
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading member data: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static class CenterRenderer extends DefaultTableCellRenderer {
        public CenterRenderer() {
            setHorizontalAlignment(JLabel.CENTER);
        }
    }

    private static class BooleanRenderer extends DefaultTableCellRenderer {
        public BooleanRenderer() {
            setHorizontalAlignment(JLabel.CENTER);
        }

        @Override
        protected void setValue(Object value) {
            setText(value == null ? "" : (Boolean)value ? "Yes" : "No");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MemberManagement management = new MemberManagement();
            management.setVisible(true);
        });
    }

    public void refreshMemberData() {
        loadMemberData();
    }
}