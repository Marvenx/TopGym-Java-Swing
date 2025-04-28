package UI;

import UI.AuthenticationUI;
import UI.CreateAccountInterface;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FitnessClubCenter extends JFrame {

    private BufferedImage backgroundImage;

    public FitnessClubCenter() {
        setTitle("Fitness Club Center");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            backgroundImage = ImageIO.read(new File("C:\\Users\\ASUS TUF\\Downloads\\fitnesscenter-master\\fitnesscenter-master\\Fitness\\src\\images\\abstract-blur-gym-room.jpg")); // Change the path to your image
        } catch (IOException e) {
            e.printStackTrace();
        }
        initComponents();
        setSize(800, 500); // Adjusted size for better visibility
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

            }
        };
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(Box.createVerticalStrut(50), BorderLayout.NORTH);

        JLabel welcomeLabel = new JLabel("Welcome to Fitness Club Center");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 36)); // Increased font size
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(welcomeLabel, BorderLayout.NORTH);
        welcomeLabel.setForeground(new Color(18, 111, 185));

        JPanel contentPanel = new JPanel(new GridLayout(1, 2, 20, 20)); // Adjusted grid layout
        contentPanel.setOpaque(false); // Make content panel transparent
        contentPanel.setBorder(BorderFactory.createEmptyBorder(160, 160, 180, 180)); // Adjusted border

        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 10, 20)); // Adjusted grid layout and spacing
        buttonPanel.setOpaque(false); // Make button panel transparent

        JButton createAccountButton = new JButton("Create Account");
        createAccountButton.setFont(new Font("Arial", Font.BOLD, 18)); // Increased font size
        createAccountButton.setForeground(Color.WHITE);
        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open CreateAccountInterface
                CreateAccountInterface createAccountInterface = new CreateAccountInterface();
                createAccountInterface.setVisible(true);
            }
        });

        JButton loginButton = new JButton("Log In");
        loginButton.setFont(new Font("Arial", Font.BOLD, 18)); // Increased font size
        loginButton.setForeground(Color.WHITE);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open AuthenticationUI
                AuthenticationUI authenticationUI = new AuthenticationUI();
                authenticationUI.setVisible(true);
            }
        });

        // Set background color for buttons
        Color buttonColor = new Color(18, 111, 185);
        createAccountButton.setBackground(buttonColor);
        loginButton.setBackground(buttonColor);

        // Set smaller button size
        Dimension buttonSize = new Dimension(40, 10); // Adjust dimensions as needed
        createAccountButton.setPreferredSize(buttonSize);
        loginButton.setPreferredSize(buttonSize);

        buttonPanel.add(createAccountButton);
        buttonPanel.add(loginButton);

        contentPanel.add(buttonPanel);

        mainPanel.add(contentPanel, BorderLayout.CENTER);

        setContentPane(mainPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new FitnessClubCenter().setVisible(true);
        });
    }
}
