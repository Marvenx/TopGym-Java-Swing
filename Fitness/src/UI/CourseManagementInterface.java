package UI;

import Dao.CourseDao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseManagementInterface extends JFrame {
    private static JLabel totalCoursesLabel;
    private JList<String> courseList;
    private static DefaultListModel<String> courseListModel;
    private JButton addButton;
    private JButton modifyButton;
    private JButton deleteButton;
    private CourseDao courseDao;

    public CourseManagementInterface() {
        setTitle("Course Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(500, 300);
        initComponents();
        loadCourseData(); // Load course data
        pack(); // Adjust the frame size based on its content
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Panel for displaying total number of courses
        JPanel totalCoursesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        totalCoursesLabel = new JLabel("Total Courses: ");
        totalCoursesPanel.setBackground(new Color(237, 144, 70));
        totalCoursesPanel.setForeground(new Color(255,255,255));
        totalCoursesPanel.add(totalCoursesLabel);
        mainPanel.add(totalCoursesPanel, BorderLayout.NORTH);

        // Panel for displaying course list
        JPanel coursePanel = new JPanel(new BorderLayout());
        courseListModel = new DefaultListModel<>();
        courseList = new JList<>(courseListModel);
        JScrollPane courseScrollPane = new JScrollPane(courseList);
        coursePanel.add(new JLabel("Course List"), BorderLayout.NORTH);
        coursePanel.setBackground(new Color(237, 144, 70));
        coursePanel.add(courseScrollPane, BorderLayout.CENTER);

        // Button panel for course operations
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        addButton = new JButton("Add Course");
        addButton.setBackground(new Color(237, 144, 70)); // Set background color
        addButton.setForeground(new Color(255,255,255));
        modifyButton = new JButton("Modify");
        modifyButton.setBackground(new Color(237, 144, 70)); // Set background color
        modifyButton.setForeground(new Color(255,255,255));

        deleteButton = new JButton("Delete Course");
        deleteButton.setBackground(new Color(237, 144, 70)); // Set background color
        deleteButton.setForeground(new Color(255,255,255));

        // Initially disable buttons
        addButton.setEnabled(true); // Enable the add button
        modifyButton.setEnabled(false);
        deleteButton.setEnabled(false);

        // Add action listeners to the buttons
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement the add course functionality
                AddCourseInterface addCourseInterface = new AddCourseInterface(CourseManagementInterface.this);
                addCourseInterface.setVisible(true);
            }
        });

        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement the modify course functionality
                int selectedIndex = courseList.getSelectedIndex();
                if (selectedIndex != -1) {
                    // Extract course ID from the selected item
                    String selectedCourseId = courseList.getSelectedValue().split(":")[0].trim();
                    int courseId = Integer.parseInt(selectedCourseId);

                    // Open the ModifyCourseInterface
                    ModifyCourseInterface modifyCourseInterface = new ModifyCourseInterface(CourseManagementInterface.this, courseId);
                    modifyCourseInterface.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(CourseManagementInterface.this, "Please select a course to modify.");
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement the delete course functionality
                int selectedIndex = courseList.getSelectedIndex();
                if (selectedIndex != -1) {
                    String selectedCourseId = courseList.getSelectedValue().split(":")[0].trim();
                    int courseId = Integer.parseInt(selectedCourseId);

                    // Show confirmation dialog
                    int option = JOptionPane.showConfirmDialog(CourseManagementInterface.this,
                            "Are you sure you want to delete this course?", "Confirmation", JOptionPane.YES_NO_OPTION);

                    if (option == JOptionPane.YES_OPTION) {
                        int rowsAffected = courseDao.deleteCourse(courseId);
                        if (rowsAffected > 0) {
                            // Reload course data after deletion
                            loadCourseData();
                            JOptionPane.showMessageDialog(CourseManagementInterface.this, "Course deleted successfully.");
                        } else {
                            JOptionPane.showMessageDialog(CourseManagementInterface.this, "Failed to delete course.");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(CourseManagementInterface.this, "Please select a course to delete.");
                }
            }
        });

        // Add buttons to button panel
        buttonPanel.add(addButton);
        buttonPanel.add(modifyButton);
        buttonPanel.add(deleteButton);

        coursePanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(coursePanel, BorderLayout.CENTER);

        // Add ListSelectionListener to course list
        courseList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                if (courseList.getSelectedIndex() != -1) {
                    // Enable buttons if a course is selected
                    addButton.setEnabled(true);
                    modifyButton.setEnabled(true);
                    deleteButton.setEnabled(true);
                } else {
                    // Disable buttons if no course is selected
                    addButton.setEnabled(false);
                    modifyButton.setEnabled(false);
                    deleteButton.setEnabled(false);
                }
            }
        });

        add(mainPanel);
    }

    // Method to load course data from the database
    public void loadCourseData() {
        try {
            if (courseDao == null) {
                // Initialize the CourseDao instance if not already initialized
                courseDao = new CourseDao();
            }

            // Clear the course list model
            courseListModel.clear();

            // Retrieve all courses from the database
            ResultSet resultSet = courseDao.getAllCourses();
            int totalCourses = 0;

            while (resultSet.next()) {
                String courseId = resultSet.getString("id");
                String courseName = resultSet.getString("name");
                courseListModel.addElement(courseId + " : " + courseName);
                totalCourses++;
            }

            // Update the total courses label
            totalCoursesLabel.setText("Total Courses: " + totalCourses);

            // Close the ResultSet
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addNewCourseToList(String name) {
        // Add the new member to the member list model
        courseListModel.addElement(name);

        // Update the total members label
        int totalMembers = courseListModel.size();
        totalCoursesLabel.setText("Total Members: " + totalMembers);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CourseManagementInterface().setVisible(true);
            }
        });
    }
}
