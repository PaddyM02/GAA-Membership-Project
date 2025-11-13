package gaa;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AuthService {
    private final User user; // demo app is single-user focused for now
    private final Map<String, String> resetTokens = new HashMap<>(); // token -> email
    private final Map<String, LocalDateTime> tokenExpiry = new HashMap<>();

    private static final int MAX_FAILED = 4;

    public AuthService(User user) {
        this.user = user;
    }

    public boolean signUp(String email, String plainPassword, String name) {
        if (email == null || !email.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new IllegalArgumentException("Invalid email");
        }
        if (plainPassword == null || plainPassword.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters");
        }
        user.setEmail(email);
        user.setPasswordHash(PasswordUtils.hash(plainPassword));
        user.setName(name);
        EmailService.sendEmail(email, "Welcome to GAA", "Hello " + name + ", welcome!");
        return true;
    }

    public boolean login(String email, String plainPassword) {
        if (user.isLocked()) {
            System.out.println("Account locked for " + email);
            return false;
        }
        if (!email.equals(user.getEmail())) return false;
        boolean ok = PasswordUtils.verify(plainPassword, user.getPasswordHash());
        if (!ok) {
            user.setFailedLoginAttempts(user.getFailedLoginAttempts() + 1);
            System.out.println("Failed attempts: " + user.getFailedLoginAttempts());
            if (user.getFailedLoginAttempts() >= MAX_FAILED) {
                user.setLocked(true);
                EmailService.sendEmail(user.getEmail(), "Account locked", "Your account has been locked after multiple failed attempts.");
                System.out.println("Account locked due to too many failed attempts.");
            }
            return false;
        }
        // successful login
        user.setFailedLoginAttempts(0);
        System.out.println("Login successful for " + email);
        // Simulate sending 2FA (could be expanded)
        EmailService.sendEmail(email, "2FA code", "Your code is: 123456 (demo)") ;
        return true;
    }

    public void requestPasswordReset(String email) {
        if (!email.equals(user.getEmail())) {
            System.out.println("No such user to reset");
            return;
        }
        String token = UUID.randomUUID().toString();
        resetTokens.put(token, email);
        tokenExpiry.put(token, LocalDateTime.now().plusMinutes(30));
        String link = "https://example.com/reset?token=" + token;
        EmailService.sendEmail(email, "Password reset", "Click here to reset your password (demo): " + link);
        System.out.println("Password reset token generated: " + token);
    }

    public boolean resetPassword(String token, String newPassword) {
        String email = resetTokens.get(token);
        if (email == null) return false;
        LocalDateTime expiry = tokenExpiry.get(token);
        if (expiry == null || expiry.isBefore(LocalDateTime.now())) {
            resetTokens.remove(token);
            tokenExpiry.remove(token);
            return false;
        }
        // apply reset
        user.setPasswordHash(PasswordUtils.hash(newPassword));
        user.setLocked(false);
        user.setFailedLoginAttempts(0);
        resetTokens.remove(token);
        tokenExpiry.remove(token);
        EmailService.sendEmail(email, "Password reset complete", "Your password has been changed.");
        return true;
    }
}
