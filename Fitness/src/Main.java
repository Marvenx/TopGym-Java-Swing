import UI.*;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        BD.MyConnexion.testConnection();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new WelcomeFrame().setVisible(true);
            }
        });
    }
}
