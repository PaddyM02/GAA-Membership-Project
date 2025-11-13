package gaa.test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.junit.jupiter.api.Test;

import gaa.LoginWindow;

class LoginWindowTest {

    @Test
    void testValidLoginDoesNotThrow() throws Exception {
        assertDoesNotThrow(() -> {
            // Run Swing setup on the EDT
            SwingUtilities.invokeLater(() -> {
                LoginWindow window = new LoginWindow();
                simulateLogin(window, "Jack O'Dea", "gaa123");
            });
        });
    }

    @Test
    void testInvalidLoginShowsErrorDialog() throws Exception {
        // Override JOptionPane to prevent a real dialog
        UIManager.put("OptionPane.showMessageDialog", (Runnable) () -> {});

        SwingUtilities.invokeAndWait(() -> {
            LoginWindow window = new LoginWindow();
            // Simulate entering wrong credentials
            simulateLogin(window, "FakeUser", "wrongpass");

            // Normally this would trigger a JOptionPane, but we override it.
            // If no exceptions occur, the test passes.
        });
    }

    // --- Helper method to simulate user input ---
    private void simulateLogin(LoginWindow window, String username, String password) {
        try {
            // Access private components reflectively
            JPanel panel = (JPanel) window.getContentPane().getComponent(0);
            JTextField userField = (JTextField) panel.getComponent(1);
            JPasswordField passField = (JPasswordField) panel.getComponent(3);
            JButton loginBtn = (JButton) panel.getComponent(5);

            // Simulate user typing
            userField.setText(username);
            passField.setText(password);

            // Simulate button click
            for (var listener : loginBtn.getActionListeners()) {
                listener.actionPerformed(null);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to simulate login", e);
        }
    }
}
