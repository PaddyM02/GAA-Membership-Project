package gaa;

<<<<<<< HEAD
import javax.swing.SwingUtilities;

=======
>>>>>>> b76d39f (5th Commit test cases updated)
// Simple simulated email service that prints and can be extended to push messages into UI later
public class EmailService {
    public static void sendEmail(String to, String subject, String body) {
        System.out.println("[Email] To:" + to + " | " + subject + " | " + body);
        // Could be extended to call a UI callback
    }
}
