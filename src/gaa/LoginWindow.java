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
        this.auth = new AuthService(user);
        this.svc = new MembershipService(user);
        initComponents();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 260);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        JPanel p = new JPanel(new BorderLayout(8,8));
        JPanel form = new JPanel(new GridLayout(3,2,6,6));
        form.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));

        form.add(new JLabel("Email:"));
        emailField = new JTextField("member@example.com");
        form.add(emailField);

        form.add(new JLabel("Password:"));
        passField = new JPasswordField("gaa123");
        form.add(passField);

        JButton loginBtn = new JButton("Login");
        loginBtn.addActionListener((ActionEvent e) -> doLogin());
        JButton signupBtn = new JButton("Sign Up (demo)");
        signupBtn.addActionListener((ActionEvent e) -> doSignUp());

        JPanel buttons = new JPanel();
        buttons.add(loginBtn);
        buttons.add(signupBtn);

        JButton forgot = new JButton("Forgot Password");
        forgot.addActionListener((ActionEvent e) -> doForgot());

        statusLabel = new JLabel(" ");
        statusLabel.setForeground(Color.BLUE);

        p.add(form, BorderLayout.CENTER);
        p.add(buttons, BorderLayout.SOUTH);
        p.add(forgot, BorderLayout.NORTH);
        p.add(statusLabel, BorderLayout.PAGE_END);

        setContentPane(p);
    }

    private void doSignUp() {
        try {
            String email = emailField.getText().trim();
            String pass = new String(passField.getPassword());
            auth.signUp(email, pass, "Demo User");
            statusLabel.setText("Signed up: " + email);
        } catch (Exception ex) {
            statusLabel.setText("Sign-up error: " + ex.getMessage());
        }
    }

    private void doLogin() {
        String email = emailField.getText().trim();
        String pass = new String(passField.getPassword());
        boolean ok = auth.login(email, pass);
        if (ok) {
            // open dashboard
            Dashboard db = new Dashboard(svc, auth);
            db.setVisible(true);
            this.dispose();
        } else {
            statusLabel.setText("Login failed. Attempts: " + user.getFailedLoginAttempts());
        }
    }

    private void doForgot() {
        String email = emailField.getText().trim();
        auth.requestPasswordReset(email);
        statusLabel.setText("Password reset email (simulated) sent if user exists."); 
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginWindow lw = new LoginWindow();
            lw.setVisible(true);
        });
    }
}
