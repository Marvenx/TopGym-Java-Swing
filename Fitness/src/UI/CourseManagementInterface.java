package UI;

import Dao.CourseDao;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseManagementInterface extends JFrame {
    private JLabel totalCoursesLabel;
    private JTable courseTable;
    private DefaultTableModel tableModel;
    private JButton addButton;
    private JButton modifyButton;
    private JButton deleteButton;
    private CourseDao courseDao;

    public CourseManagementInterface() {
        setTitle("Course Management");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 350);
        setLocationRelativeTo(null);
        initComponents();
        loadCourseData();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        getContentPane().setBackground(new Color(248, 193, 60));

        // Total Courses Panel
        JPanel totalCoursesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        totalCoursesLabel = new JLabel("Total Courses: 0");
        totalCoursesLabel.setForeground(Color.WHITE);
        totalCoursesPanel.setBackground(new Color(248, 193, 60));
        totalCoursesPanel.add(totalCoursesLabel);
        mainPanel.add(totalCoursesPanel, BorderLayout.NORTH);

        // Table setup
        String[] columnNames = {"ID", "Name", "Description"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        courseTable = new JTable(tableModel);
        courseTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        courseTable.getTableHeader().setReorderingAllowed(false);

// Adjust column widths
        courseTable.getColumnModel().getColumn(0).setPreferredWidth(50);  // ID
        courseTable.getColumnModel().getColumn(1).setPreferredWidth(150); // Name
        courseTable.getColumnModel().getColumn(2).setPreferredWidth(500); // Description

// Center-align ID and Name columns
        courseTable.getColumnModel().getColumn(0).setCellRenderer(new CenterRenderer());
        courseTable.getColumnModel().getColumn(1).setCellRenderer(new CenterRenderer());


        JScrollPane tableScrollPane = new JScrollPane(courseTable);
        tableScrollPane.setPreferredSize(new Dimension(750, 350));

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(new Color(248, 193, 60));
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);
        mainPanel.add(tablePanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        addButton = createButton("Add Course", e -> {
            AddCourseInterface addCourseInterface = new AddCourseInterface(CourseManagementInterface.this);
            addCourseInterface.setVisible(true);
        });

        modifyButton = createButton("Modify", e -> {
            int selectedRow = courseTable.getSelectedRow();
            if (selectedRow != -1) {
                int courseId = (int) tableModel.getValueAt(selectedRow, 0); // Now getting the hidden ID
                ModifyCourseInterface modifyCourseInterface = new ModifyCourseInterface(CourseManagementInterface.this, courseId);
                modifyCourseInterface.setVisible(true);
            }
        });

        deleteButton = createButton("Delete", e -> {
            int selectedRow = courseTable.getSelectedRow();
            if (selectedRow != -1) {
                String courseName = (String) tableModel.getValueAt(selectedRow, 1);

                int option = JOptionPane.showConfirmDialog(
                        CourseManagementInterface.this,
                        "Delete course: " + courseName + "?",
                        "Confirm Deletion",
                        JOptionPane.YES_NO_OPTION
                );

                if (option == JOptionPane.YES_OPTION) {
                    int courseId = (int) tableModel.getValueAt(selectedRow, 0); // Hidden ID
                    int rowsAffected = courseDao.deleteCourse(courseId);
                    if (rowsAffected > 0) {
                        loadCourseData();
                        JOptionPane.showMessageDialog(CourseManagementInterface.this, "Course deleted successfully.");
                    } else {
                        JOptionPane.showMessageDialog(CourseManagementInterface.this, "Failed to delete course.");
                    }
                }
            }
        });

        buttonPanel.add(addButton);
        buttonPanel.add(modifyButton);
        buttonPanel.add(deleteButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Enable/disable buttons based on selection
        courseTable.getSelectionModel().addListSelectionListener(e -> {
            boolean rowSelected = courseTable.getSelectedRow() != -1;
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

    public void loadCourseData() {
        try {
            if (courseDao == null) {
                courseDao = new CourseDao();
            }

            // Clear the table
            tableModel.setRowCount(0);

            // Retrieve all courses from database
            ResultSet resultSet = courseDao.getAllCourses();
            int totalCourses = 0;

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");

                tableModel.addRow(new Object[]{id, name, description});
                totalCourses++;
            }

            totalCoursesLabel.setText("Total Courses: " + totalCourses);
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading course data: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Custom renderer for center-aligned Name column
    private static class CenterRenderer extends DefaultTableCellRenderer {
        public CenterRenderer() {
            setHorizontalAlignment(JLabel.CENTER);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CourseManagementInterface management = new CourseManagementInterface();
            management.setVisible(true);
        });
    }

    public void refreshCourseData() {
        loadCourseData();
    }
}
