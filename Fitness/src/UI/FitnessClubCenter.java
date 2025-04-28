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
        setTitle("Fitness Club Center - Manager Portal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            backgroundImage = ImageIO.read(new File("C:\\Users\\ASUS TUF\\Downloads\\fitnesscenter-master\\fitnesscenter-master\\Fitness\\src\\images\\fitness-center.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        initComponents();
        setSize(800, 500);
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

        // Add more vertical space at the top
        mainPanel.add(Box.createVerticalStrut(120), BorderLayout.NORTH);

        // Create a panel for the title and description
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);
        textPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0)); // Add padding at bottom

        JLabel welcomeLabel = new JLabel("Welcome to Fitness Club Center");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 36));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeLabel.setForeground(new Color(18, 111, 185));

        // Add manager description label
        JLabel managerLabel = new JLabel("Manager Portal - Member Management System");
        managerLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        managerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        managerLabel.setForeground(Color.WHITE);
        managerLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0)); // Add space above

        textPanel.add(welcomeLabel);
        textPanel.add(managerLabel);
        mainPanel.add(textPanel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new GridLayout(1, 2, 20, 20));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(100, 160, 180, 160)); // Adjusted border

        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 10, 20));
        buttonPanel.setOpaque(false);

        JButton loginButton = createStyledButton("Log In", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AuthenticationUI authenticationUI = new AuthenticationUI();
                authenticationUI.setVisible(true);
            }
        });

        JButton createAccountButton = createStyledButton("Create Account", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateAccountInterface createAccountInterface = new CreateAccountInterface();
                createAccountInterface.setVisible(true);
            }
        });

        buttonPanel.add(loginButton);
        buttonPanel.add(createAccountButton);
        contentPanel.add(buttonPanel);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        setContentPane(mainPanel);
    }

    private JButton createStyledButton(String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(18, 111, 185));
        button.setPreferredSize(new Dimension(200, 50));
        button.setFocusPainted(false);
        button.addActionListener(listener);
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FitnessClubCenter fitnessClubCenter = new FitnessClubCenter();
            fitnessClubCenter.setVisible(true);
        });
    }
}