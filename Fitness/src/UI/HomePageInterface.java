package UI;

import javax.swing.*;
import RMI.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class HomePageInterface extends JFrame {

    private BufferedImage backgroundImage;

    public HomePageInterface() {
        setTitle("Manager Homepage");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        try {
            backgroundImage = ImageIO.read(new File("C:\\Users\\ASUS TUF\\Downloads\\fitnesscenter-master\\fitnesscenter-master\\Fitness\\src\\images\\fitness2.jpg")); // Change the path to your image
        } catch (IOException e) {
            e.printStackTrace();
        }
        initComponents();
        setSize(800, 600);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        mainPanel.setLayout(new BorderLayout());

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);
        textPanel.setBorder(BorderFactory.createEmptyBorder(60, 0, 10, 0));

        JLabel welcomeLabel = new JLabel("Welcome, Mr Manager");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 36));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeLabel.setForeground(new Color(18, 111, 185));

        textPanel.add(welcomeLabel);
        mainPanel.add(textPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(160, 150, 150, 150));

        JButton createButton = createButton("Member Management");
        createButton.addActionListener(e -> openHomePageUI());

        JButton courseButton = createButton("Course Management");
        courseButton.addActionListener(e -> openCourseManagementInterface());

        JButton chatButton = createButton("Chat Room");
        chatButton.addActionListener(e -> openChatRoom());

        buttonPanel.add(createButton);
        buttonPanel.add(courseButton);
        buttonPanel.add(chatButton);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        setContentPane(mainPanel);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(new Color(18, 111, 185));
        button.setForeground(Color.WHITE);
        return button;
    }

    private void openHomePageUI() {
        MemberManagement memberManagement = new MemberManagement();
        memberManagement.setVisible(true);
    }

    private void openCourseManagementInterface() {
        CourseManagementInterface courseManagementInterface = new CourseManagementInterface();
        courseManagementInterface.setVisible(true);
    }

    private void openChatRoom() {
        try {
            String url = "rmi://127.0.0.1:9001/chat";
            ChatRemote chat = (ChatRemote) java.rmi.Naming.lookup(url);
            new ChatUI(chat).setVisible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to open Chat Room.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HomePageInterface().setVisible(true));
    }
}
