package UI;

import Classes.Course;
import Dao.CourseDao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddCourseInterface extends JFrame {
    private JTextField nameField;
    private JTextField descriptionField;
    private JTextField idField;
    private final CourseManagementInterface courseManagementInterface;

    public AddCourseInterface(CourseManagementInterface courseManagementInterface) {

        this.courseManagementInterface = courseManagementInterface;
        setTitle("Add New Course");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        setSize(400, 200);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 5, 5));

        JLabel titleLabel = new JLabel("Add New Course");
        titleLabel.setForeground(new Color(237, 140, 70));
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField(15);
        nameField.setForeground(new Color(30, 117, 158));

        JLabel descriptionLabel = new JLabel("Description:");
        descriptionField = new JTextField(15);
        descriptionField.setForeground(new Color(30, 117, 158));
        JLabel idLabel = new JLabel("ID:");
        idField = new JTextField(15);
        idField.setForeground(new Color(30, 117, 158));

        JButton addButton = new JButton("Add");
        addButton.setBackground(new Color(237, 140, 70));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCourse();
            }
        });

        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(descriptionLabel);
        formPanel.add(descriptionField);
        formPanel.add(idLabel);
        formPanel.add(idField);

        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(addButton, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void addCourse() {
        String name = nameField.getText();
        String description = descriptionField.getText();
        int id = Integer.parseInt(idField.getText());

        Course newCourse = new Course(id, name, description);

        CourseDao courseDao = new CourseDao();
        int rowsAffected = courseDao.addCourse(newCourse);

        if (rowsAffected > 0) {
            JOptionPane.showMessageDialog(this, "Course added successfully!");
            courseManagementInterface.loadCourseData();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add course.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AddCourseInterface(new CourseManagementInterface()).setVisible(true);
            }
        });
    }
}
