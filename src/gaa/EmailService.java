package gaa;

import javax.swing.SwingUtilities;

// Simple simulated email service that prints and can be extended to push messages into UI later
public class EmailService {
    public static void sendEmail(String to, String subject, String body) {
        System.out.println("[Email] To:" + to + " | " + subject + " | " + body);
        // Could be extended to call a UI callback
    }
}
