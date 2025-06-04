package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class WelcomeFrame extends JFrame {

    private BufferedImage backgroundImage;

    public WelcomeFrame() {
        setTitle("Welcome to TopGym");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        try {
            backgroundImage = ImageIO.read(new File("C:\\Users\\ASUS TUF\\Downloads\\fitnesscenter-master\\fitnesscenter-master\\Fitness\\src\\images\\background.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setOpaque(false);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(100, 150, 50, 150)); // top, left, bottom, right padding

        JLabel welcomeLabel = new JLabel("Welcome to TopGym!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 36));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton chatButton = createStyledButton("Enter Chat Room");
        JButton managerButton = createStyledButton("Manager Portal");

        Dimension buttonSize = new Dimension(250, 50);
        chatButton.setMaximumSize(buttonSize);
        managerButton.setMaximumSize(buttonSize);

        chatButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        managerButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        chatButton.addActionListener(e -> openChatRoom());
        managerButton.addActionListener(e -> openPasswordFrame());

        mainPanel.add(welcomeLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        mainPanel.add(chatButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(managerButton);

        setContentPane(mainPanel);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setBackground(Color.WHITE);
        button.setForeground(new Color(18, 111, 185));
        button.setFocusPainted(false);
        return button;
    }

    private void openChatRoom() {
        try {
            String url = "rmi://127.0.0.1:9001/chat";
            RMI.ChatRemote chat = (RMI.ChatRemote) java.rmi.Naming.lookup(url);
            new RMI.ChatUI(chat).setVisible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to open Chat Room.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openPasswordFrame() {
        new PasswordFrame(this).setVisible(true);
        this.setVisible(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new WelcomeFrame().setVisible(true));
    }
}
