package gaa;

<<<<<<< HEAD
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
=======
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
>>>>>>> b76d39f (5th Commit test cases updated)

public class LoginWindow extends JFrame {

    private static final long serialVersionUID = 1L;

    private final User user = new User();
    private final AuthService auth;
    private final MembershipService svc;

    private JTextField emailField;
    private JPasswordField passField;
    private JLabel statusLabel;
<<<<<<< HEAD
=======
    private JProgressBar strengthBar;
    private JLabel strengthLabel;
    private JCheckBox showPassword;
    private JToggleButton themeToggle; // Light/Dark

    // color palettes
    private Color bgLight = Color.WHITE;
    private Color fgLight = Color.BLACK;
    private Color panelLight = new Color(0xF5F7FA);

    private Color bgDark = new Color(0x2B2B2B);
    private Color fgDark = new Color(0xE6E6E6);
    private Color panelDark = new Color(0x3A3F44);

    private boolean darkMode = false;
>>>>>>> b76d39f (5th Commit test cases updated)

    public LoginWindow() {
        super("GAA Membership - Login");
        this.auth = new AuthService(user);
        this.svc = new MembershipService(user);
        initComponents();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
<<<<<<< HEAD
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

=======
        setSize(550, 350);
        setLocationRelativeTo(null);
    }



    private void initComponents() {
        // Root panel
        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(new EmptyBorder(10, 10, 10, 10));
        applyTheme(root); // set initial theme

        // TOP: Forgot + Theme toggle
        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        JButton forgot = createRoundedButton("\u2709 Forgot Password"); // ✉
        forgot.addActionListener(e -> doForgot());
        top.add(forgot, BorderLayout.WEST);

        themeToggle = new JToggleButton("\u263C Light"); // sun symbol
        themeToggle.setFocusable(false);
        themeToggle.addActionListener(e -> toggleTheme());
        top.add(themeToggle, BorderLayout.EAST);

        // CENTER: form
        JPanel formHolder = new JPanel(new GridBagLayout());
        formHolder.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Email (longer & dark-themed)
        formHolder.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        emailField = new JTextField("Paddy@example.com");
        emailField.setColumns(20);
        emailField.setPreferredSize(new Dimension(300, 28)); // wider field
        styleTextField(emailField); // applies dark/light theme
        formHolder.add(emailField, gbc);

        // Password
        gbc.gridy++;
        gbc.gridx = 0;
        formHolder.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        passField = new JPasswordField("gaa123", 20);
        passField.setPreferredSize(new Dimension(300, 28));
        styleTextField(passField);
        formHolder.add(passField, gbc);

        // Show password checkbox
        gbc.gridy++;
        gbc.gridx = 1;
        showPassword = new JCheckBox("Show password");
        showPassword.setOpaque(false);
        showPassword.setForeground(getFgColor());
        showPassword.addActionListener(e -> {
            if (showPassword.isSelected()) passField.setEchoChar((char) 0);
            else passField.setEchoChar('\u2022'); // bullet
        });
        formHolder.add(showPassword, gbc);

        // Password strength meter
        gbc.gridy++;
        gbc.gridx = 0;
        strengthLabel = new JLabel("Strength: ");
        strengthLabel.setForeground(getFgColor());
        formHolder.add(strengthLabel, gbc);

        gbc.gridx = 1;
        strengthBar = new JProgressBar(0, 100);
        strengthBar.setValue(0);
        strengthBar.setStringPainted(true);
        strengthBar.setPreferredSize(new Dimension(180, 18));
        formHolder.add(strengthBar, gbc);

        // Listen to password field changes to update meter
        passField.getDocument().addDocumentListener(new SimpleDocumentListener() {
            @Override
            public void update() {
                updateStrengthMeter(new String(passField.getPassword()));
            }
        });

        // BOTTOM: buttons panel (Login / Sign Up) + status
        JPanel bottom = new JPanel(new BorderLayout(6, 6));
        bottom.setOpaque(false);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 6));
        buttons.setOpaque(false);

        JButton loginBtn = createRoundedButton("\uD83D\uDD12 Login"); // lock emoji
        loginBtn.addActionListener(e -> doLogin());

        JButton signupBtn = createRoundedButton("\u2795 Sign Up"); // plus
        signupBtn.addActionListener(e -> doSignUp());

        buttons.add(loginBtn);
        buttons.add(signupBtn);

        // status label
        statusLabel = new JLabel(" ");
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setForeground(getFgColor());

        bottom.add(buttons, BorderLayout.CENTER);
        bottom.add(statusLabel, BorderLayout.SOUTH);

        // Assemble root
        root.add(top, BorderLayout.NORTH);
        root.add(formHolder, BorderLayout.CENTER);
        root.add(bottom, BorderLayout.SOUTH);

        setContentPane(root);
    }

    // ---------------- UI helpers ----------------

    private JButton createRoundedButton(String text) {
        RoundedButton b = new RoundedButton(text);
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setPreferredSize(new Dimension(140, 36));
        applyButtonTheme(b);
        return b;
    }

    private void styleTextField(JComponent tf) {
        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xC8C8C8)),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)
        ));
        tf.setOpaque(true);
        tf.setBackground(getPanelColor()); // dark/light panel background
        tf.setForeground(getFgColor());    // dark/light text
    }

    private void applyTheme(JComponent root) {
        Color bg = getBgColor();
        root.setBackground(bg);
    }

    private void applyButtonTheme(AbstractButton b) {
        if (darkMode) {
            b.setBackground(new Color(0x4A90E2));
            b.setForeground(fgDark);
        } else {
            b.setBackground(new Color(0x1976D2));
            b.setForeground(Color.WHITE);
        }
    }

    private Color getBgColor() {
        return darkMode ? bgDark : bgLight;
    }

    private Color getFgColor() {
        return darkMode ? fgDark : fgLight;
    }

    private Color getPanelColor() {
        return darkMode ? panelDark : panelLight;
    }

    private void toggleTheme() {
        darkMode = !darkMode;
        themeToggle.setText(darkMode ? "\u263E Dark" : "\u263C Light");
        // walk component tree and update colors
        updateComponentTreeUI(this.getContentPane());
        repaint();
    }

    private void updateComponentTreeUI(Component comp) {
        if (comp instanceof JComponent) {
            JComponent jc = (JComponent) comp;
            jc.setBackground(getBgColor());
            jc.setForeground(getFgColor());

            if (jc instanceof JPanel) {
                jc.setBackground(getPanelColor());
            }
            if (jc instanceof JButton) {
                applyButtonTheme((AbstractButton) jc);
            }
            if (jc instanceof JCheckBox) {
                jc.setForeground(getFgColor());
            }
            if (jc instanceof JLabel) {
                jc.setForeground(getFgColor());
            }
        }
        if (comp instanceof Container) {
            for (Component c : ((Container) comp).getComponents()) {
                updateComponentTreeUI(c);
            }
        }
    }

    // ---------------- Actions ----------------

>>>>>>> b76d39f (5th Commit test cases updated)
    private void doSignUp() {
        try {
            String email = emailField.getText().trim();
            String pass = new String(passField.getPassword());
<<<<<<< HEAD
            auth.signUp(email, pass, "Demo User");
            statusLabel.setText("Signed up: " + email);
        } catch (Exception ex) {
            statusLabel.setText("Sign-up error: " + ex.getMessage());
=======
            auth.signUp(email, pass, "User");
            setStatus("Signed up: " + email, true);
        } catch (Exception ex) {
            setStatus("Sign-up error: " + ex.getMessage(), false);
>>>>>>> b76d39f (5th Commit test cases updated)
        }
    }

    private void doLogin() {
        String email = emailField.getText().trim();
        String pass = new String(passField.getPassword());
<<<<<<< HEAD
        boolean ok = auth.login(email, pass);
        if (ok) {
            // open dashboard
            Dashboard db = new Dashboard(svc, auth);
            db.setVisible(true);
            this.dispose();
        } else {
            statusLabel.setText("Login failed. Attempts: " + user.getFailedLoginAttempts());
=======

        boolean ok = auth.login(email, pass);

        if (ok) {
            setStatus("Login successful", true);
            // proceed to dashboard
            SwingUtilities.invokeLater(() -> {
                Dashboard db = new Dashboard(svc, auth);
                db.setVisible(true);
                this.dispose();
            });
        } else {
            setStatus("Login failed. Attempts: " + user.getFailedLoginAttempts(), false);
>>>>>>> b76d39f (5th Commit test cases updated)
        }
    }

    private void doForgot() {
        String email = emailField.getText().trim();
        auth.requestPasswordReset(email);
<<<<<<< HEAD
        statusLabel.setText("Password reset email (simulated) sent if user exists."); 
    }

=======
        setStatus("Password reset (demo) - check console / email", true);
    }

    private void setStatus(String text, boolean ok) {
        statusLabel.setText(text);
        statusLabel.setForeground(ok ? new Color(0x1B5E20) : new Color(0xB00020));
    }

    // ---------------- Password strength ----------------

    private void updateStrengthMeter(String password) {
        int score = calculatePasswordScore(password);
        strengthBar.setValue(score);
        String desc;
        if (score < 40) {
            desc = "Weak";
            strengthBar.setForeground(new Color(0xD32F2F));
        } else if (score < 75) {
            desc = "Medium";
            strengthBar.setForeground(new Color(0xFBC02D));
        } else {
            desc = "Strong";
            strengthBar.setForeground(new Color(0x388E3C));
        }
        strengthBar.setString(desc + " (" + score + "%)");
        strengthLabel.setText("Strength:");
    }

    private int calculatePasswordScore(String pw) {
        if (pw == null || pw.isEmpty()) return 0;
        int score = 0;
        int len = pw.length();
        if (len >= 8) score += Math.min(40, (len - 7) * 5);
        boolean hasLower = pw.matches(".*[a-z].*");
        boolean hasUpper = pw.matches(".*[A-Z].*");
        boolean hasDigit = pw.matches(".*\\d.*");
        boolean hasSymbol = pw.matches(".*[^a-zA-Z0-9].*");
        int variety = (hasLower ? 1 : 0) + (hasUpper ? 1 : 0) + (hasDigit ? 1 : 0) + (hasSymbol ? 1 : 0);
        score += variety * 15;
        String lower = pw.toLowerCase();
        if (lower.contains("password") || lower.contains("1234") || lower.contains("qwerty")) {
            score = Math.max(10, score - 30);
        }
        return Math.min(100, Math.max(0, score));
    }

    // ---------------- Custom Rounded Button ----------------

    private static class RoundedButton extends JButton {
        private static final long serialVersionUID = 1L;
        private static final int ARC = 18;

        public RoundedButton(String text) {
            super(text);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setOpaque(false);
            setFont(getFont().deriveFont(Font.BOLD));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Color bg = getBackground();
            if (getModel().isPressed()) g2.setColor(bg.darker());
            else if (getModel().isRollover()) g2.setColor(bg.brighter());
            else g2.setColor(bg);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), ARC, ARC);
            FontMetrics fm = g2.getFontMetrics();
            Rectangle r = new Rectangle(0, 0, getWidth(), getHeight());
            int x = (r.width - fm.stringWidth(getText())) / 2;
            int y = (r.height - fm.getHeight()) / 2 + fm.getAscent();
            g2.setColor(getForeground());
            g2.drawString(getText(), x, y);
            g2.dispose();
        }

        @Override
        public Dimension getPreferredSize() {
            Dimension d = super.getPreferredSize();
            d.width += 24;
            d.height = Math.max(d.height, 36);
            return d;
        }
    }

    // ---------------- Simple Document Listener ----------------

    private abstract static class SimpleDocumentListener implements javax.swing.event.DocumentListener {
        public abstract void update();
        @Override public void insertUpdate(javax.swing.event.DocumentEvent e) { update(); }
        @Override public void removeUpdate(javax.swing.event.DocumentEvent e) { update(); }
        @Override public void changedUpdate(javax.swing.event.DocumentEvent e) { update(); }
    }

    // ---------------- Main ----------------

>>>>>>> b76d39f (5th Commit test cases updated)
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginWindow lw = new LoginWindow();
            lw.setVisible(true);
        });
    }
}
