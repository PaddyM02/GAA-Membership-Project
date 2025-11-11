package gaa;

import javax.swing.*;
import java.awt.*;

public class LoginWindow extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    private static final String USERNAME = "Jack O'Dea";      
    private static final String PASSWORD = "gaa123";     

    public LoginWindow() {
        setTitle("Login");
        setSize(320, 180);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField();

        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField();

        JButton loginBtn = new JButton("Login");

        loginBtn.addActionListener(e -> {
            String enteredUser = userField.getText().trim();
            String enteredPass = new String(passField.getPassword());

            if (enteredUser.equals(USERNAME) && enteredPass.equals(PASSWORD)) {
                MembershipService svc = new MembershipService();
                Dashboard dash = new Dashboard(svc);
                dash.setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Incorrect username or password",
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(userLabel);
        panel.add(userField);
        panel.add(passLabel);
        panel.add(passField);
        panel.add(new JLabel(""));
        panel.add(loginBtn);

        add(panel);
    }
}
