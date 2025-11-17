package gaa;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginWindow extends JFrame {

    private static final long serialVersionUID = 1L;

    private final User user = new User();
    private final AuthService auth;
    private final MembershipService svc;

    private JTextField emailField;
    private JPasswordField passField;
    private JLabel statusLabel;

    public LoginWindow() {
        super("GAA Membership - Login");

        // Create a default demo account
        user.setEmail("member@example.com");
        user.setPasswordHash(PasswordUtils.hash("gaa123"));
        user.setName("Demo User");
        svc = new MembershipService(user);
        auth = new AuthService(user);

        initComponents();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 260);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        JPanel form = new JPanel(new GridLayout(2, 2, 6, 6));
        form.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        form.add(new JLabel("Email:"));
        emailField = new JTextField("Paddy@gmail.com");
        form.add(emailField);

        form.add(new JLabel("Password:"));
        passField = new JPasswordField("gaa123");
        form.add(passField);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton loginBtn = new JButton("Login");
        loginBtn.addActionListener(this::handleLogin);
        buttonPanel.add(loginBtn);

        JButton signupBtn = new JButton("Sign Up");
        signupBtn.addActionListener(this::handleSignUp);
        buttonPanel.add(signupBtn);

        JButton forgotBtn = new JButton("Forgot Password");
        forgotBtn.addActionListener(this::handleForgot);
        buttonPanel.add(forgotBtn);

        statusLabel = new JLabel(" ");
        statusLabel.setForeground(Color.BLUE);
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(form, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        panel.add(statusLabel, BorderLayout.NORTH);
        setContentPane(panel);
    }

    private void handleLogin(ActionEvent e) {
        String email = emailField.getText().trim();
        String password = new String(passField.getPassword());

        if (auth.login(email, password)) {
            statusLabel.setText("Login successful!");
            Dashboard dash = new Dashboard(svc, auth);
            dash.setVisible(true);
            this.dispose();
        } else {
            statusLabel.setText("Login failed. Check your credentials.");
        }
    }

    private void handleSignUp(ActionEvent e) {
        String email = emailField.getText().trim();
        String password = new String(passField.getPassword());

        try {
            auth.signUp(email, password, "New User");
            statusLabel.setText("Sign-up complete! You can now log in.");
        } catch (Exception ex) {
            statusLabel.setText("Sign-up error: " + ex.getMessage());
        }
    }

    private void handleForgot(ActionEvent e) {
        String email = emailField.getText().trim();
        auth.requestPasswordReset(email);
        statusLabel.setText("Password reset email simulated in console.");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginWindow().setVisible(true));
    }
}
