package UI;

import Classes.Course;
import Dao.CourseDao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ModifyCourseInterface extends JFrame {
    private final int courseId;
    private JTextField nameField;
    private JTextField descriptionField;
    private JButton saveButton;
    private JButton cancelButton;

    private CourseManagementInterface parentFrame;

    public ModifyCourseInterface(CourseManagementInterface parentFrame, int courseId) {
        this.parentFrame = parentFrame;
        this.courseId = courseId;

        setTitle("Modify Course");
        setSize(400, 200);
        setLocationRelativeTo(parentFrame);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initComponents();
        loadCourseData();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Panel for the title
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Modify Course");
        titleLabel.setForeground(new Color(248, 193, 60));
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titlePanel.add(titleLabel);

        // Panel for form fields
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();
        JLabel descriptionLabel = new JLabel("Description:");
        descriptionField = new JTextField();
        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(descriptionLabel);
        formPanel.add(descriptionField);

        // Panel for buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        saveButton = new JButton("Save");
        saveButton.setBackground(new Color(248, 193, 60));
        saveButton.setForeground(Color.WHITE);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveCourseData();
            }
        });

        cancelButton = new JButton("Cancel");
        cancelButton.setBackground(new Color(248, 193, 60));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        // Add panels to the main panel
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }



    private void loadCourseData() {
        try {
            CourseDao courseDao = new CourseDao();
            ResultSet resultSet = courseDao.searchCourse(courseId);
            if (resultSet.next()) {
                nameField.setText(resultSet.getString("name"));
                descriptionField.setText(resultSet.getString("description"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveCourseData() {
        String name = nameField.getText();
        String description = descriptionField.getText();

        CourseDao courseDao = new CourseDao();
        int rowsAffected = courseDao.updateCourse(new Course(courseId, name, description));

        if (rowsAffected > 0) {
            parentFrame.loadCourseData();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update course data.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                CourseManagementInterface parentFrame = new CourseManagementInterface();
                int courseId = 123; // Replace with the actual course ID
                new ModifyCourseInterface(parentFrame, courseId).setVisible(true);
            }
        });
    }
}
